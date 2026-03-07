/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author user
 */

import DTO.ChiTietHoaDonDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietHoaDonDAO implements ChiTietInterface<ChiTietHoaDonDTO> {

    public static ChiTietHoaDonDAO getInstance() {
        return new ChiTietHoaDonDAO();
    }

    @Override
    public int insert(ChiTietHoaDonDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "INSERT INTO ChiTietHoaDon (MaHD, MaSP, SL, DG_Ban, ThanhTien) "
                       + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaHD());
            pst.setString(2, t.getMaSP());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setDouble(5, t.getThanhTien());

            ketQua = pst.executeUpdate();

            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(ChiTietHoaDonDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "UPDATE ChiTietHoaDon "
                       + "SET SL = ?, DG_Ban = ?, ThanhTien = ? "
                       + "WHERE MaHD = ? AND MaSP = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getSoLuong());
            pst.setDouble(2, t.getDonGia());
            pst.setDouble(3, t.getThanhTien());
            pst.setString(4, t.getMaHD());
            pst.setString(5, t.getMaSP());

            ketQua = pst.executeUpdate();

            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(ChiTietHoaDonDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "DELETE FROM ChiTietHoaDon WHERE MaHD = ? AND MaSP = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, t.getMaHD());
            pst.setString(2, t.getMaSP());

            ketQua = pst.executeUpdate();

            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // selectALL(String maHD) -> lấy tất cả chi tiết theo mã hóa đơn
    @Override
    public ArrayList<ChiTietHoaDonDTO> selectALL(String maHD) {
        ArrayList<ChiTietHoaDonDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM ChiTietHoaDon WHERE MaHD = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maHD);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maHDValue = rs.getString("MaHD");
                String maSP = rs.getString("MaSP");
                int sl = rs.getInt("SL");
                double dgBan = rs.getDouble("DG_Ban");
                double thanhTien = rs.getDouble("ThanhTien");

                ChiTietHoaDonDTO ct = new ChiTietHoaDonDTO(
                        maHDValue, maSP, sl, dgBan, thanhTien);

                ketQua.add(ct);
            }

            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    public ArrayList<ChiTietHoaDonDTO> selectAll() {
        ArrayList<ChiTietHoaDonDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();

            String sql = "SELECT * FROM ChiTietHoaDon";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("MaHD");
                String maSP = rs.getString("MaSP");
                int sl = rs.getInt("SL");
                double dgBan = rs.getDouble("DG_Ban");
                double thanhTien = rs.getDouble("ThanhTien");

                ChiTietHoaDonDTO ct = new ChiTietHoaDonDTO(
                        maHD, maSP, sl, dgBan, thanhTien);

                ketQua.add(ct);
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

