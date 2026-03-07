package DAO;

import DTO.HoaDonDTO;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class HoaDonDAO implements DAOInterface<HoaDonDTO> {

    public static HoaDonDAO getInstance() {
        return new HoaDonDAO();
    }

    @Override
    public int insert(HoaDonDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO HoaDon (MaHD, MaNV, MaKH, NgayLapHD, TongTien, TrangThai) "
                    + "VALUES (?, ?, ?, ?, ?, 1)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaHD());
            pst.setString(2, t.getMaNV());
            pst.setString(3, t.getMaKH());
            pst.setTimestamp(4, Timestamp.valueOf(t.getNgayLapHD()));
            pst.setDouble(5, t.getTongTien());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(HoaDonDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE HoaDon "
                    + "SET MaNV = ?, MaKH = ?, NgayLapHD = ?, TongTien = ? "
                    + "WHERE MaHD = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaNV());
            pst.setString(2, t.getMaKH());
            pst.setTimestamp(3, Timestamp.valueOf(t.getNgayLapHD()));
            pst.setDouble(4, t.getTongTien());
            pst.setString(5, t.getMaHD());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(HoaDonDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE HoaDon SET TrangThai = 0 WHERE MaHD = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaHD());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<HoaDonDTO> selectALL() {
        ArrayList<HoaDonDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM HoaDon WHERE TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                HoaDonDTO hd = new HoaDonDTO(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKH"),
                        rs.getTimestamp("NgayLapHD").toLocalDateTime(),
                        rs.getDouble("TongTien"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(hd);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public HoaDonDTO selectById(HoaDonDTO t) {
        HoaDonDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM HoaDon WHERE MaHD = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaHD());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new HoaDonDTO(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKH"),
                        rs.getTimestamp("NgayLapHD").toLocalDateTime(),
                        rs.getDouble("TongTien"),
                        rs.getInt("TrangThai")
                );
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<HoaDonDTO> selectByCondition(String condition) {
        ArrayList<HoaDonDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM HoaDon WHERE TrangThai = 1 AND " + condition;

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                HoaDonDTO hd = new HoaDonDTO(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKH"),
                        rs.getTimestamp("NgayLapHD").toLocalDateTime(),
                        rs.getDouble("TongTien"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(hd);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public String getLastMaHD() {
        String lastMa = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lastMa = rs.getString("MaHD");
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastMa;
    }
}