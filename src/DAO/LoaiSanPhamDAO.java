package DAO;

import DTO.LoaiSanPhamDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoaiSanPhamDAO implements DAOInterface<LoaiSanPhamDTO> {

    public static LoaiSanPhamDAO getInstance() {
        return new LoaiSanPhamDAO();
    }

    @Override
    public int insert(LoaiSanPhamDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO LoaiSanPham(MaLoai, TenLoai, TrangThai) VALUES (?, ?, 1)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getMaLoai());
            pst.setString(2, t.getTenLoai());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(LoaiSanPhamDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE LoaiSanPham SET TenLoai = ? WHERE MaLoai = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getTenLoai());
            pst.setInt(2, t.getMaLoai());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(LoaiSanPhamDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE LoaiSanPham SET TrangThai = 0 WHERE MaLoai = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getMaLoai());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<LoaiSanPhamDTO> selectALL() {
        ArrayList<LoaiSanPhamDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM LoaiSanPham WHERE TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                LoaiSanPhamDTO lsp = new LoaiSanPhamDTO(
                        rs.getInt("MaLoai"),
                        rs.getString("TenLoai"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(lsp);
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
    public LoaiSanPhamDTO selectById(LoaiSanPhamDTO t) {
        LoaiSanPhamDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM LoaiSanPham WHERE MaLoai = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getMaLoai());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new LoaiSanPhamDTO(
                        rs.getInt("MaLoai"),
                        rs.getString("TenLoai"),
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
    public ArrayList<LoaiSanPhamDTO> selectByCondition(String condition) {
        ArrayList<LoaiSanPhamDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM LoaiSanPham WHERE TrangThai = 1 AND " + condition;
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                LoaiSanPhamDTO lsp = new LoaiSanPhamDTO(
                        rs.getInt("MaLoai"),
                        rs.getString("TenLoai"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(lsp);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public int getLastMaLoai() {
        int lastMa = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT TOP 1 MaLoai FROM LoaiSanPham ORDER BY MaLoai DESC";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lastMa = rs.getInt("MaLoai");
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