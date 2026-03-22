/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ThongKeDTO;
import DTO.ThongKeTheoNamDTO;
import DTO.ThongKeTheoThangDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    // ================== 7 NGÀY GẦN NHẤT ==================
    public List<ThongKeDTO> select7DaysAgo() {
        String sql = """
            WITH dates AS (
                SELECT DATEADD(DAY, -6, CAST(GETDATE() AS DATE)) AS ngay
                UNION ALL
                SELECT DATEADD(DAY, 1, ngay)
                FROM dates
                WHERE ngay < CAST(GETDATE() AS DATE)
            )
            SELECT 
                dates.ngay,
                COALESCE(SUM(cthd.SL * cthd.DG_Ban), 0) AS doanhthu,
                COALESCE(SUM(cthd.SL * latest.dongia), 0) AS chiphi
            FROM dates
            LEFT JOIN HoaDon hd 
                ON CAST(hd.NgayLapHD AS DATE) = dates.ngay
            LEFT JOIN ChiTietHoaDon cthd 
                ON cthd.MaHD = hd.MaHD
            OUTER APPLY (
                SELECT TOP 1 ctpn.dongia
                FROM ChiTietPhieuNhap ctpn
                JOIN PhieuNhap pn ON pn.maPHN = ctpn.maPHN
                WHERE ctpn.maSP = cthd.MaSP
                AND pn.ngay <= hd.NgayLapHD   
                ORDER BY pn.ngay DESC
            ) AS latest
            GROUP BY dates.ngay
            ORDER BY dates.ngay;
        """;

        return queryThongKe(sql);
    }

    // ================== THEO NGÀY (THÁNG + NĂM) ==================
    public List<ThongKeDTO> selectDaysByMonthYear(int month, int year) {

        String sql = String.format("""
            DECLARE @thang INT = %d;
            DECLARE @nam INT = %d;
            
            DECLARE @startDate DATE = DATEFROMPARTS(@nam, @thang, 1);
            
            WITH numbers AS (
                SELECT ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1 AS number
                FROM master..spt_values
            ),
            dates AS (
                SELECT DATEADD(DAY, number, @startDate) AS ngay
                FROM numbers
                WHERE DATEADD(DAY, number, @startDate) < DATEADD(MONTH, 1, @startDate)
            )
            
            SELECT 
                d.ngay,
            
                -- Doanh thu
                COALESCE(SUM(cthd.SL * cthd.DG_Ban), 0) AS doanhthu,
            
                -- Chi phí (giá nhập mới nhất)
                COALESCE(SUM(cthd.SL * latest.dongia), 0) AS chiphi
            
            FROM dates d
            
            LEFT JOIN HoaDon hd 
                ON hd.NgayLapHD >= d.ngay
                AND hd.NgayLapHD < DATEADD(DAY, 1, d.ngay)
            
            LEFT JOIN ChiTietHoaDon cthd 
                ON cthd.MaHD = hd.MaHD
            
            -- Lấy giá nhập mới nhất theo sản phẩm
            OUTER APPLY (
                SELECT TOP 1 ctpn.dongia
                FROM ChiTietPhieuNhap ctpn
                JOIN PhieuNhap pn ON pn.maPHN = ctpn.maPHN
                WHERE ctpn.maSP = cthd.MaSP
                AND pn.ngay <= hd.NgayLapHD   
                ORDER BY pn.ngay DESC
            ) AS latest
            
            GROUP BY d.ngay
            ORDER BY d.ngay;
        """, month, year);

        return queryThongKe(sql);
    }

    // ================== THEO NĂM ==================
    public List<ThongKeTheoNamDTO> selectFromYearToYear(int fromYear, int toYear) {

        String sql = String.format("""
            DECLARE @start_year INT = %d;
            DECLARE @end_year INT = %d;

            WITH years(year) AS (
                SELECT @start_year
                UNION ALL
                SELECT year + 1
                FROM years
                WHERE year < @end_year
            )
            SELECT 
                years.year AS nam,

                -- Doanh thu
                COALESCE(SUM(cthd.SL * cthd.DG_Ban), 0) AS doanhthu,

                -- Chi phí (giá nhập mới nhất)
                COALESCE(SUM(cthd.SL * latest.dongia), 0) AS chiphi

            FROM years

            LEFT JOIN HoaDon hd 
                ON YEAR(hd.NgayLapHD) = years.year
            LEFT JOIN ChiTietHoaDon cthd 
                ON cthd.MaHD = hd.MaHD
            OUTER APPLY (
                SELECT TOP 1 ctpn.dongia
                FROM ChiTietPhieuNhap ctpn
                JOIN PhieuNhap pn ON pn.maPHN = ctpn.maPHN
                WHERE ctpn.maSP = cthd.MaSP
                AND pn.ngay <= hd.NgayLapHD   
                ORDER BY pn.ngay DESC
            ) AS latest
            GROUP BY years.year
            ORDER BY years.year
        """, fromYear, toYear);

        return queryThongKeNam(sql);
    }

    // ================== THEO THÁNG ==================
    public List<ThongKeTheoThangDTO> selectMounthsByYear(int year) {

        String sql = String.format("""
            DECLARE @year INT = %d;
            
            SELECT 
                m.month AS thang,
            
                -- Doanh thu
                COALESCE(SUM(cthd.SL * cthd.DG_Ban), 0) AS doanhthu,
            
                -- Chi phí (giá nhập mới nhất)
                COALESCE(SUM(cthd.SL * latest.dongia), 0) AS chiphi
            
            FROM (
                VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12)
            ) AS m(month)
            
            LEFT JOIN HoaDon hd 
                ON hd.NgayLapHD >= DATEFROMPARTS(@year, m.month, 1)
                AND hd.NgayLapHD < DATEADD(MONTH, 1, DATEFROMPARTS(@year, m.month, 1))
            
            LEFT JOIN ChiTietHoaDon cthd 
                ON cthd.MaHD = hd.MaHD
            
            -- Lấy giá nhập mới nhất theo sản phẩm
            OUTER APPLY (
                SELECT TOP 1 ctpn.dongia
                FROM ChiTietPhieuNhap ctpn
                JOIN PhieuNhap pn ON pn.maPHN = ctpn.maPHN
                WHERE ctpn.maSP = cthd.MaSP
                AND pn.ngay <= hd.NgayLapHD   
                ORDER BY pn.ngay DESC
            ) AS latest
            
            GROUP BY m.month
            ORDER BY m.month;
        """, year);

        return queryThongKeThang(sql);
    }

    // ================== QUERY CHUNG ==================
    private List<ThongKeDTO> queryThongKe(String sql) {
        List<ThongKeDTO> list = new ArrayList<>();

        try (Connection conn = SQLServerConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ThongKeDTO e = new ThongKeDTO();
                e.setThoiGian(rs.getDate("ngay"));
                e.setDoanhThu(rs.getDouble("doanhthu"));
                e.setChiPhi(rs.getDouble("chiphi"));
                list.add(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private List<ThongKeTheoNamDTO> queryThongKeNam(String sql) {
        List<ThongKeTheoNamDTO> list = new ArrayList<>();

        try (Connection conn = SQLServerConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ThongKeTheoNamDTO e = new ThongKeTheoNamDTO();
                e.setNam(rs.getInt("nam"));
                e.setDoanhThu(rs.getDouble("doanhthu"));
                e.setChiPhi(rs.getDouble("chiphi"));
                list.add(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private List<ThongKeTheoThangDTO> queryThongKeThang(String sql) {
        List<ThongKeTheoThangDTO> list = new ArrayList<>();

        try (Connection conn = SQLServerConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ThongKeTheoThangDTO e = new ThongKeTheoThangDTO();
                e.setThang(rs.getInt("thang"));
                e.setDoanhThu(rs.getDouble("doanhthu"));
                e.setChiPhi(rs.getDouble("chiphi"));
                list.add(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
