package DAO;

import DTO.KhachHangDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class KhachHangDAO implements DAOInterface<KhachHangDTO> {

    public static KhachHangDAO getInstance() {
        return new KhachHangDAO();
    }

    @Override
    public int insert(KhachHangDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO KhachHang(MaKH, Ho, Ten, DiaChi, TrangThai) VALUES (?, ?, ?, ?, 1)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaKH());
            pst.setString(2, t.getHo());
            pst.setString(3, t.getTen());
            pst.setString(4, t.getDiaChi());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(KhachHangDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE KhachHang SET Ho = ?, Ten = ?, DiaChi = ? WHERE MaKH = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getHo());
            pst.setString(2, t.getTen());
            pst.setString(3, t.getDiaChi());
            pst.setString(4, t.getMaKH());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(KhachHangDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE KhachHang SET TrangThai = 0 WHERE MaKH = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaKH());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<KhachHangDTO> selectALL() {
        ArrayList<KhachHangDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM KhachHang WHERE TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("DiaChi"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(kh);
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
    public KhachHangDTO selectById(KhachHangDTO t) {
        KhachHangDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM KhachHang WHERE MaKH = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaKH());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("DiaChi"),
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
    public ArrayList<KhachHangDTO> selectByCondition(String condition) {
        ArrayList<KhachHangDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM KhachHang WHERE TrangThai = 1 AND " + condition;

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("DiaChi"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(kh);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public String getLastMaKH() {
        String lastMa = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT TOP 1 MaKH FROM KhachHang ORDER BY MaKH DESC";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lastMa = rs.getString("MaKH");
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