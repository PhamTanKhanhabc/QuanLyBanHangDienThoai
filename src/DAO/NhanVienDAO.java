package DAO;

import DTO.NhanVienDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class NhanVienDAO implements DAOInterface<NhanVienDTO> {

    public static NhanVienDAO getInstance() {
        return new NhanVienDAO();
    }

    @Override
    public int insert(NhanVienDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO NhanVien (MaNV, Ho, Ten, NgaySinh, DiaChi, DienThoai, LuongThang, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaNV());
            pst.setString(2, t.getHo());
            pst.setString(3, t.getTen());
            pst.setDate(4, java.sql.Date.valueOf(t.getNgaySinh()));
            pst.setString(5, t.getDiaChi());
            pst.setString(6, t.getDienThoai());
            pst.setDouble(7, t.getLuongThang());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(NhanVienDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE NhanVien SET Ho = ?, Ten = ?, NgaySinh = ?, DiaChi = ?, DienThoai = ?, LuongThang = ? WHERE MaNV = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getHo());
            pst.setString(2, t.getTen());
            pst.setDate(3, java.sql.Date.valueOf(t.getNgaySinh()));
            pst.setString(4, t.getDiaChi());
            pst.setString(5, t.getDienThoai());
            pst.setDouble(6, t.getLuongThang());
            pst.setString(7, t.getMaNV());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(NhanVienDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE NhanVien SET TrangThai = 0 WHERE MaNV = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaNV());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<NhanVienDTO> selectALL() {
        ArrayList<NhanVienDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getDate("NgaySinh").toLocalDate(),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getDouble("LuongThang"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(nv);
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
    public NhanVienDTO selectById(NhanVienDTO t) {
        NhanVienDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaNV());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getDate("NgaySinh").toLocalDate(),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getDouble("LuongThang"),
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
    public ArrayList<NhanVienDTO> selectByCondition(String condition) {
        ArrayList<NhanVienDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1 AND " + condition;
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getDate("NgaySinh").toLocalDate(),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getDouble("LuongThang"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(nv);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public String getLastMaNV() {
        String lastMa = "";
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT TOP 1 MaNV FROM NhanVien ORDER BY MaNV DESC";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lastMa = rs.getString("MaNV");
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