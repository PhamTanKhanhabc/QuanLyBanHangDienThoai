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
    String sql = "INSERT INTO KhachHang(MaKH, Ho, Ten, DiaChi, SoDT, TrangThai) VALUES (?, ?, ?, ?, ?, 1)";
    try (Connection con = SQLServerConnect.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, t.getMaKH());
        pst.setString(2, t.getHo());
        pst.setString(3, t.getTen());
        pst.setString(4, t.getDiaChi());
        pst.setString(5, t.getSoDT()); 

        ketQua = pst.executeUpdate();

    } catch (SQLException ex) {
        System.err.println("Lỗi tại insert KhachHangDAO: " + ex.getMessage());
        ex.printStackTrace();
    }
    return ketQua;
}

    @Override
public int update(KhachHangDTO t) {
    int ketQua = 0;
    String sql = "UPDATE KhachHang SET Ho = ?, Ten = ?, DiaChi = ?, SoDT = ? WHERE MaKH = ? AND TrangThai = 1";
    try (Connection con = SQLServerConnect.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, t.getHo());
        pst.setString(2, t.getTen());
        pst.setString(3, t.getDiaChi());
        pst.setString(4, t.getSoDT()); 
        pst.setString(5, t.getMaKH()); 

        ketQua = pst.executeUpdate();

    } catch (SQLException ex) {
        System.err.println("Lỗi tại update KhachHangDAO: " + ex.getMessage());
        ex.printStackTrace();
    }
    return ketQua;
}

    @Override
    public int delete(KhachHangDTO t) {
        int ketQua = 0;
        String sql = "UPDATE KhachHang SET TrangThai = 0 WHERE MaKH = ? AND TrangThai = 1";
        try (Connection con = SQLServerConnect.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);){
            pst.setString(1, t.getMaKH());
            ketQua = pst.executeUpdate();        
        } catch (SQLException ex) {
            System.err.println("Loi o delete" + ex.getMessage());
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<KhachHangDTO> selectALL() {
        ArrayList<KhachHangDTO> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE TrangThai = 1";
        try (Connection con = SQLServerConnect.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()){
            while (rs.next()) {

                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("DiaChi"),
                        rs.getInt("TrangThai"),
                        rs.getString("SoDT")
                );
                ketQua.add(kh);
            }       
        } catch (SQLException ex) {
            System.err.println("Loi o SelectALL" + ex.getMessage());
            ex.printStackTrace();
        }
        return ketQua;
    }

    @Override
public KhachHangDTO selectById(KhachHangDTO t) {
    KhachHangDTO ketQua = null;
    String sql = "SELECT * FROM KhachHang WHERE MaKH = ? AND TrangThai = 1";
    // Đưa Connection và PreparedStatement vào trong ngoặc tròn của try
    try (Connection con = SQLServerConnect.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, t.getMaKH());
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                ketQua = new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("DiaChi"),
                        rs.getInt("TrangThai"),
                        rs.getString("SoDT")
                );
            }
        }

    } catch (SQLException ex) {
        System.err.println("Lỗi tại selectById KhachHangDAO: " + ex.getMessage());
        ex.printStackTrace();
    }
    return ketQua;
}
    @Override
    public ArrayList<KhachHangDTO> selectByCondition(String condition) {
        ArrayList<KhachHangDTO> ketQua = new ArrayList<>();
         String sql = "SELECT * FROM KhachHang WHERE TrangThai = 1 AND " + condition;
        try (Connection con = SQLServerConnect.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()){
            while (rs.next()) {

                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("DiaChi"),
                        rs.getInt("TrangThai"),
                        rs.getString("SoDT")
                );
                ketQua.add(kh);
            }
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }
        return ketQua;
    }

    public String getLastMaKH() {
    String lastMa = null;
    // Lấy mã KH lớn nhất trong toàn bộ bảng, không dùng WHERE TrangThai=1
    String sql = "SELECT TOP 1 MaKH FROM KhachHang ORDER BY MaKH DESC"; 
    try (Connection con = SQLServerConnect.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            lastMa = rs.getString("MaKH");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lastMa;
}
}