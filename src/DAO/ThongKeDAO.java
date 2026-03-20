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
                SELECT DATEADD(DAY, -6, GETDATE()) AS date
                UNION ALL
                SELECT DATEADD(DAY, 1, date)
                FROM dates
                WHERE date < CAST(GETDATE() AS DATE)
            )
            SELECT 
                dates.date AS ngay,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
            FROM dates
            LEFT JOIN HoaDon ON CONVERT(DATE, HoaDon.thoiGian) = CONVERT(DATE, dates.date)
            LEFT JOIN ChiTietHoaDon ON ChiTietHoaDon.idHD = HoaDon.idHD
            LEFT JOIN Thuoc ON Thuoc.idThuoc = ChiTietHoaDon.idThuoc
            GROUP BY dates.date
            ORDER BY dates.date
        """;

        return queryThongKe(sql);
    }

    // ================== THEO NGÀY (THÁNG + NĂM) ==================
    public List<ThongKeDTO> selectDaysByMonthYear(int month, int year) {

        String sql = String.format("""
            DECLARE @thang INT = %d;
            DECLARE @nam INT = %d;

            DECLARE @ngayString NVARCHAR(10) = 
                CONVERT(NVARCHAR(10), @nam) + '-' + 
                RIGHT('0' + CONVERT(NVARCHAR(2), @thang), 2) + '-01';

            WITH numbers AS (
                SELECT ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1 AS number
                FROM master..spt_values
            )
            SELECT dates.date AS ngay,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
            FROM (
                SELECT DATEADD(DAY, number, @ngayString) AS date
                FROM numbers
                WHERE DATEADD(DAY, number, @ngayString) 
                      <= EOMONTH(@ngayString)
            ) AS dates
            LEFT JOIN HoaDon ON CONVERT(DATE, HoaDon.thoiGian) = CONVERT(DATE, dates.date)
            LEFT JOIN ChiTietHoaDon ON HoaDon.idHD = ChiTietHoaDon.idHD
            LEFT JOIN Thuoc ON Thuoc.idThuoc = ChiTietHoaDon.idThuoc
            GROUP BY dates.date
            ORDER BY dates.date
        """, month, year);

        return queryThongKe(sql);
    }

    // ================== THEO NĂM ==================
    public List<ThongKeTheoNamDTO> selectFromYearToYear(int fromYear, int toYear) {

        String sql = String.format("""
            WITH years(year) AS (
                SELECT %d
                UNION ALL
                SELECT year + 1
                FROM years
                WHERE year < %d
            )
            SELECT 
                years.year AS nam,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
            FROM years
            LEFT JOIN HoaDon ON YEAR(HoaDon.thoiGian) = years.year
            LEFT JOIN ChiTietHoaDon ON HoaDon.idHD = ChiTietHoaDon.idHD
            LEFT JOIN Thuoc ON Thuoc.idThuoc = ChiTietHoaDon.idThuoc
            GROUP BY years.year
            ORDER BY years.year
        """, fromYear, toYear);

        return queryThongKeNam(sql);
    }

    // ================== THEO THÁNG ==================
    public List<ThongKeTheoThangDTO> selectMounthsByYear(int year) {

        String sql = String.format("""
            SELECT 
                months.month AS thang,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                COALESCE(SUM(ChiTietHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
            FROM (VALUES 
                (1),(2),(3),(4),(5),(6),
                (7),(8),(9),(10),(11),(12)
            ) AS months(month)
            LEFT JOIN HoaDon 
                ON MONTH(HoaDon.thoiGian) = months.month 
                AND YEAR(HoaDon.thoiGian) = %d
            LEFT JOIN ChiTietHoaDon ON HoaDon.idHD = ChiTietHoaDon.idHD
            LEFT JOIN Thuoc ON Thuoc.idThuoc = ChiTietHoaDon.idThuoc
            GROUP BY months.month
            ORDER BY months.month
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
