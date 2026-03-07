package DAO;

import DTO.BaoHanhDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaoHanhDAO implements DAOInterface<BaoHanhDTO> {

    public static BaoHanhDAO getInstance() {
        return new BaoHanhDAO();
    }

    @Override
    public int insert(BaoHanhDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO BaoHanh (MaBH, TenBH, MaHD, MaSP, ThoiHan, NgayBatDau, NgayKetThuc, TrangThai) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaBH());
            pst.setString(2, t.getTenBH());
            pst.setString(3, t.getMaHD());
            pst.setString(4, t.getMaSP());
            pst.setInt(5, t.getThoiHan());
            pst.setDate(6, new java.sql.Date(t.getNgayBatDau().getTime()));
            pst.setDate(7, new java.sql.Date(t.getNgayKetThuc().getTime()));

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(BaoHanhDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE BaoHanh "
                    + "SET TenBH = ?, MaHD = ?, MaSP = ?, ThoiHan = ?, NgayBatDau = ?, NgayKetThuc = ? "
                    + "WHERE MaBH = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getTenBH());
            pst.setString(2, t.getMaHD());
            pst.setString(3, t.getMaSP());
            pst.setInt(4, t.getThoiHan());
            pst.setDate(5, new java.sql.Date(t.getNgayBatDau().getTime()));
            pst.setDate(6, new java.sql.Date(t.getNgayKetThuc().getTime()));
            pst.setString(7, t.getMaBH());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(BaoHanhDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE BaoHanh SET TrangThai = 0 WHERE MaBH = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaBH());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<BaoHanhDTO> selectALL() {
        ArrayList<BaoHanhDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM BaoHanh WHERE TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                BaoHanhDTO bh = new BaoHanhDTO(
                        rs.getString("MaBH"),
                        rs.getString("TenBH"),
                        rs.getString("MaHD"),
                        rs.getString("MaSP"),
                        rs.getInt("ThoiHan"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(bh);
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
    public BaoHanhDTO selectById(BaoHanhDTO t) {
        BaoHanhDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM BaoHanh WHERE MaBH = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaBH());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new BaoHanhDTO(
                        rs.getString("MaBH"),
                        rs.getString("TenBH"),
                        rs.getString("MaHD"),
                        rs.getString("MaSP"),
                        rs.getInt("ThoiHan"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
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
    public ArrayList<BaoHanhDTO> selectByCondition(String condition) {
        ArrayList<BaoHanhDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM BaoHanh WHERE TrangThai = 1 AND " + condition;
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                BaoHanhDTO bh = new BaoHanhDTO(
                        rs.getString("MaBH"),
                        rs.getString("TenBH"),
                        rs.getString("MaHD"),
                        rs.getString("MaSP"),
                        rs.getInt("ThoiHan"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(bh);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public String getLastMaBH() {
        String lastMa = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT TOP 1 MaBH FROM BaoHanh ORDER BY MaBH DESC";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lastMa = rs.getString("MaBH");
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