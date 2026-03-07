package DAO;

import DTO.NhaCungCapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhaCungCapDAO implements DAOInterface<NhaCungCapDTO> {

    public static NhaCungCapDAO getInstance() {
        return new NhaCungCapDAO();
    }

    @Override
    public int insert(NhaCungCapDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO NhaCungCap (maNCC, tenNCC, sdt, diachi, TrangThai) VALUES (?, ?, ?, ?, 1)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getMaNCC());
            pst.setString(2, t.getTenNCC());
            pst.setString(3, t.getSDT());
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
    public int update(NhaCungCapDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE NhaCungCap SET tenNCC = ?, sdt = ?, diachi = ? WHERE maNCC = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getTenNCC());
            pst.setString(2, t.getSDT());
            pst.setString(3, t.getDiaChi());
            pst.setInt(4, t.getMaNCC());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(NhaCungCapDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE NhaCungCap SET TrangThai = 0 WHERE maNCC = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getMaNCC());

            ketQua = pst.executeUpdate();

            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<NhaCungCapDTO> selectALL() {
        ArrayList<NhaCungCapDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM NhaCungCap WHERE TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                NhaCungCapDTO ncc = new NhaCungCapDTO(
                        rs.getInt("maNCC"),
                        rs.getString("tenNCC"),
                        rs.getString("sdt"),
                        rs.getString("diachi"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(ncc);
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
    public NhaCungCapDTO selectById(NhaCungCapDTO t) {
        NhaCungCapDTO ketQua = null;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM NhaCungCap WHERE maNCC = ? AND TrangThai = 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getMaNCC());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                ketQua = new NhaCungCapDTO(
                        rs.getInt("maNCC"),
                        rs.getString("tenNCC"),
                        rs.getString("sdt"),
                        rs.getString("diachi"),
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
    public ArrayList<NhaCungCapDTO> selectByCondition(String condition) {
        ArrayList<NhaCungCapDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM NhaCungCap WHERE TrangThai = 1 AND " + condition;

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                NhaCungCapDTO ncc = new NhaCungCapDTO(
                        rs.getInt("maNCC"),
                        rs.getString("tenNCC"),
                        rs.getString("sdt"),
                        rs.getString("diachi"),
                        rs.getInt("TrangThai")
                );

                ketQua.add(ncc);
            }

            rs.close();
            pst.close();
            SQLServerConnect.closeConnection(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public int getLastMaNCC() {
        int lastMa = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT TOP 1 maNCC FROM NhaCungCap ORDER BY maNCC DESC";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lastMa = rs.getInt("maNCC");
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