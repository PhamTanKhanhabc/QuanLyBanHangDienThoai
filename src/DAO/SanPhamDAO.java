package DAO;

import DTO.SanPhamDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.List;

public class SanPhamDAO implements DAOInterface<SanPhamDTO> {

    public static SanPhamDAO getInstance() {
        return new SanPhamDAO();
    }

    @Override
    public int insert(SanPhamDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO SanPham(MaSp, TenSp, SoLuongTon, DonGia, DonViTinh, MaLoai, MaHang, TrangThai) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaSp());
            pst.setString(2, t.getTenSp());
            pst.setInt(3, t.getSoLuongTon());
            pst.setDouble(4, t.getDonGia());
            pst.setString(5, t.getDonViTinh());
            pst.setInt(6, t.getMaLoai());
            pst.setInt(7, t.getMaHang());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(SanPhamDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE SanPham "
                    + "SET TenSp = ?, SoLuongTon = ?, DonGia = ?, DonViTinh = ?, MaLoai = ?, MaHang = ? "
                    + "WHERE MaSp = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getTenSp());
            pst.setInt(2, t.getSoLuongTon());
            pst.setDouble(3, t.getDonGia());
            pst.setString(4, t.getDonViTinh());
            pst.setInt(5, t.getMaLoai());
            pst.setInt(6, t.getMaHang());
            pst.setString(7, t.getMaSp());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(SanPhamDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE SanPham SET TrangThai = 0 WHERE MaSp = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaSp());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<SanPhamDTO> selectALL() {
        ArrayList<SanPhamDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM SanPham WHERE TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                SanPhamDTO sp = new SanPhamDTO(
                        rs.getString("MaSp"),
                        rs.getString("TenSp"),
                        rs.getInt("SoLuongTon"),
                        rs.getDouble("DonGia"),
                        rs.getString("DonViTinh"),
                        rs.getInt("MaLoai"),
                        rs.getInt("MaHang"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(sp);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public SanPhamDTO selectById(SanPhamDTO t) {
        SanPhamDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM SanPham WHERE MaSp = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaSp());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new SanPhamDTO(
                        rs.getString("MaSp"),
                        rs.getString("TenSp"),
                        rs.getInt("SoLuongTon"),
                        rs.getDouble("DonGia"),
                        rs.getString("DonViTinh"),
                        rs.getInt("MaLoai"),
                        rs.getInt("MaHang"),
                        rs.getInt("TrangThai")
                );
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<SanPhamDTO> selectByCondition(String condition) {
        ArrayList<SanPhamDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM SanPham WHERE TrangThai = 1 AND " + condition;

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                SanPhamDTO sp = new SanPhamDTO(
                        rs.getString("MaSp"),
                        rs.getString("TenSp"),
                        rs.getInt("SoLuongTon"),
                        rs.getDouble("DonGia"),
                        rs.getString("DonViTinh"),
                        rs.getInt("MaLoai"),
                        rs.getInt("MaHang"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(sp);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public String getLastMaSP() {
        String lastMa = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT TOP 1 MaSp FROM SanPham ORDER BY MaSp DESC";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lastMa = rs.getString("MaSp");
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastMa;
    }

    public List<SanPhamDTO> getSearchTable(String text, String searchType) {
        List<SanPhamDTO> result = new ArrayList<>();

        if (text == null) {
            text = "";
        }

        text = text.trim().toLowerCase();

        for (SanPhamDTO sp : this.selectALL()) {

            String maSp = sp.getMaSp() != null ? sp.getMaSp().toLowerCase() : "";
            String tenSp = sp.getTenSp() != null ? sp.getTenSp().toLowerCase() : "";

            switch (searchType) {
                case "Tất cả":
                    if (maSp.contains(text) || tenSp.contains(text)) {
                        result.add(sp);
                    }
                    break;

                case "Mã":
                    if (maSp.contains(text)) {
                        result.add(sp);
                    }
                    break;

                case "Tên":
                    if (tenSp.contains(text)) {
                        result.add(sp);
                    }
                    break;

                default:
                    result.add(sp);
                    break;
            }
        }

        return result;
    }
}