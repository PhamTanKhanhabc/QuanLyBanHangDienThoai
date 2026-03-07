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

    @Override
    public int insert(ChiTietPhieuNhapDTO t) {
        int ketQua = 0;
        try {
            Connection con = SQLServerConnect.getConnection();
            
            String sql = "INSERT INTO ChiTietPhieuNhap (maPHN, maSP, dongia, thanhtien) VALUES (?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, t.getMaPHN());
            pst.setString(2, t.getMaSP());
            pst.setDouble(3, t.getDonGia());
            pst.setDouble(4, t.getThanhTien());

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

            String sql = "UPDATE ChiTietPhieuNhap SET dongia=?, thanhtien=? WHERE maPHN=? AND maSP=?";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setDouble(1, t.getDonGia());
            pst.setDouble(2, t.getThanhTien());
            
            pst.setInt(3, t.getMaPHN());
            pst.setString(4, t.getMaSP());

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
            
            pst.setInt(1, t.getMaPHN());
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
            
            pst.setInt(1, Integer.parseInt(t));
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int maPHN = rs.getInt("maPHN");
                String maSP = rs.getString("maSP");
                double donGia = rs.getDouble("dongia");
                double thanhTien = rs.getDouble("thanhtien");
                
                ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(maPHN, maSP, donGia, thanhTien);
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
                int maPHN = rs.getInt("maPHN");
                String maSP = rs.getString("maSP");
                double donGia = rs.getDouble("dongia");
                double thanhTien = rs.getDouble("thanhtien");

                ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(maPHN, maSP, donGia, thanhTien);
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