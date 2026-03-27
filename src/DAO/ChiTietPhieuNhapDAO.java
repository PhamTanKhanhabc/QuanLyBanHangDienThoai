package DAO;

import DTO.ChiTietPhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAO implements ChiTietInterface<ChiTietPhieuNhapDTO> {

    public static ChiTietPhieuNhapDAO getInstance() {
        return new ChiTietPhieuNhapDAO();
    }
    
    public boolean deleteAllByMaPHN(String maPHN) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();
            String sql = "DELETE FROM ChiTietPhieuNhap WHERE maPHN = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maPHN);
            ketQua = pst.executeUpdate();
            
            pst.close();
            SQLServerConnect.closeConnection(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua > 0;
    }

    @Override
    public int insert(ChiTietPhieuNhapDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();
            String sql = "INSERT INTO ChiTietPhieuNhap (maPHN, maSP, soluong, dongia, thanhtien) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPHN());
            pst.setString(2, t.getMaSP());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setDouble(5, t.getThanhTien());
            ketQua = pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(ChiTietPhieuNhapDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();
            String sql = "UPDATE ChiTietPhieuNhap SET soluong=?, dongia=?, thanhtien=? WHERE maPHN=? AND maSP=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getSoLuong());
            pst.setDouble(2, t.getDonGia());
            pst.setDouble(3, t.getThanhTien());
            pst.setString(4, t.getMaPHN());
            pst.setString(5, t.getMaSP());
            ketQua = pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(ChiTietPhieuNhapDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();
            String sql = "DELETE FROM ChiTietPhieuNhap WHERE maPHN=? AND maSP=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPHN());
            pst.setString(2, t.getMaSP());
            ketQua = pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<ChiTietPhieuNhapDTO> selectALL(String t) {
        ArrayList<ChiTietPhieuNhapDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuNhap WHERE maPHN = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPHN = rs.getString("maPHN");
                String maSP = rs.getString("maSP");
                int soLuong = rs.getInt("soluong");
                double donGia = rs.getDouble("dongia");
                double thanhTien = rs.getDouble("thanhtien");
                ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(maPHN, maSP, soLuong, donGia, thanhTien);
                ketQua.add(ctpn);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<ChiTietPhieuNhapDTO> selectAll() {
        ArrayList<ChiTietPhieuNhapDTO> ketQua = new ArrayList<>();
        try {
            Connection con = SQLServerConnect.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuNhap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPHN = rs.getString("maPHN");
                String maSP = rs.getString("maSP");
                int soLuong = rs.getInt("soluong");
                double donGia = rs.getDouble("dongia");
                double thanhTien = rs.getDouble("thanhtien");
                ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(maPHN, maSP, soLuong, donGia, thanhTien);
                ketQua.add(ctpn);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}