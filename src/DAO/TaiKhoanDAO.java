package DAO;

import DTO.TaiKhoanDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaiKhoanDAO implements DAOInterface<TaiKhoanDTO> {

    public static TaiKhoanDAO getInstance() {
        return new TaiKhoanDAO();
    }

    @Override
    public int insert(TaiKhoanDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO TaiKhoan(MaTK, TenDangNhap, MatKhau, MaNV, MaVaiTro, TrangThai) "
                    + "VALUES (?, ?, ?, ?, ?, 1)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaTaiKhoan());
            pst.setString(2, t.getTenDangNhap());
            pst.setString(3, t.getMatKhau());
            pst.setString(4, t.getMaNhanVien());
            pst.setString(5, t.getMaVaiTro());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(TaiKhoanDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE TaiKhoan "
                    + "SET TenDangNhap = ?, MatKhau = ?, MaNV = ?, MaVaiTro = ? "
                    + "WHERE MaTK = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getTenDangNhap());
            pst.setString(2, t.getMatKhau());
            pst.setString(3, t.getMaNhanVien());
            pst.setString(4, t.getMaVaiTro());
            pst.setString(5, t.getMaTaiKhoan());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(TaiKhoanDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE TaiKhoan SET TrangThai = 0 WHERE MaTK = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaTaiKhoan());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<TaiKhoanDTO> selectALL() {
        ArrayList<TaiKhoanDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM TaiKhoan WHERE TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                TaiKhoanDTO tk = new TaiKhoanDTO(
                        rs.getString("MaTK"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("MaNV"),
                        rs.getString("MaVaiTro"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(tk);
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
    public TaiKhoanDTO selectById(TaiKhoanDTO t) {
        TaiKhoanDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM TaiKhoan WHERE MaTK = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaTaiKhoan());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                ketQua = new TaiKhoanDTO(
                        rs.getString("MaTK"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("MaNV"),
                        rs.getString("MaVaiTro"),
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
    public ArrayList<TaiKhoanDTO> selectByCondition(String condition) {
        ArrayList<TaiKhoanDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM TaiKhoan WHERE TrangThai = 1 AND " + condition;

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                TaiKhoanDTO tk = new TaiKhoanDTO(
                        rs.getString("MaTK"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("MaNV"),
                        rs.getString("MaVaiTro"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(tk);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public TaiKhoanDTO selectByUser(String username) {
        TaiKhoanDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                ketQua = new TaiKhoanDTO(
                        rs.getString("MaTK"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("MaNV"),
                        rs.getString("MaVaiTro"),
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
}