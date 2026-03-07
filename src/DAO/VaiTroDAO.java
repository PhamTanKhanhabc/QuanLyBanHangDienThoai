package DAO;

import DTO.VaiTroDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VaiTroDAO implements DAOInterface<VaiTroDTO> {

    public static VaiTroDAO getInstance() {
        return new VaiTroDAO();
    }

    @Override
    public int insert(VaiTroDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO VaiTro(MaVaiTro, TenVaiTro, TrangThai) VALUES (?, ?, 1)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaVaiTro());
            pst.setString(2, t.getTenVaiTro());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(VaiTroDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE VaiTro SET TenVaiTro = ? WHERE MaVaiTro = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getTenVaiTro());
            pst.setString(2, t.getMaVaiTro());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(VaiTroDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE VaiTro SET TrangThai = 0 WHERE MaVaiTro = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaVaiTro());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<VaiTroDTO> selectALL() {
        ArrayList<VaiTroDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM VaiTro WHERE TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                VaiTroDTO vt = new VaiTroDTO(
                        rs.getString("MaVaiTro"),
                        rs.getString("TenVaiTro"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(vt);
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
    public VaiTroDTO selectById(VaiTroDTO t) {
        VaiTroDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM VaiTro WHERE MaVaiTro = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaVaiTro());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new VaiTroDTO(
                        rs.getString("MaVaiTro"),
                        rs.getString("TenVaiTro"),
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
    public ArrayList<VaiTroDTO> selectByCondition(String condition) {
        ArrayList<VaiTroDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM VaiTro WHERE TrangThai = 1 AND " + condition;

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                VaiTroDTO vt = new VaiTroDTO(
                        rs.getString("MaVaiTro"),
                        rs.getString("TenVaiTro"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(vt);
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