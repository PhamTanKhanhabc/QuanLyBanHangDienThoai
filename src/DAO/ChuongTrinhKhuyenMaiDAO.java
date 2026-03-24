package DAO;

import DTO.ChuongTrinhKhuyenMaiDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChuongTrinhKhuyenMaiDAO implements DAOInterface<ChuongTrinhKhuyenMaiDTO> {

    @Override
    public int insert(ChuongTrinhKhuyenMaiDTO t) {
        int result = 0;
        String sql = "INSERT INTO ChuongTrinhKhuyenMai VALUES (?,?,?,?,?,?,?)";
        try {
            Connection con = SQLServerConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, t.getMaCTKM());
            ps.setNString(2, t.getTenCTKM());
            ps.setNString(3, t.getLoaiKhuyenMai());
            ps.setNString(4, t.getMoTa());
            ps.setDate(5, java.sql.Date.valueOf(t.getNgayBatDau()));
            ps.setDate(6, java.sql.Date.valueOf(t.getNgayKetThuc()));
            ps.setInt(7, t.getTrangThai());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(ChuongTrinhKhuyenMaiDTO t) {
        int result = 0;
        String sql = "UPDATE ChuongTrinhKhuyenMai SET TenCTKM=?, LoaiKhuyenMai=?, MoTa=?, NgayBatDau=?, NgayKetThuc=?, TrangThai=? WHERE MaCTKM=?";
        try {
            Connection con = SQLServerConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setNString(1, t.getTenCTKM());
            ps.setNString(2, t.getLoaiKhuyenMai());
            ps.setNString(3, t.getMoTa());
            ps.setDate(4, java.sql.Date.valueOf(t.getNgayBatDau()));
            ps.setDate(5, java.sql.Date.valueOf(t.getNgayKetThuc()));
            ps.setInt(6, t.getTrangThai());
            ps.setString(7, t.getMaCTKM());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(ChuongTrinhKhuyenMaiDTO t) {
         int result = 0;
         Connection con = null;

         String sqlDeleteKMHD = "DELETE FROM ChiTietKhuyenMaiHoaDon WHERE MaCTKM = ?";
         String sqlDeleteKMSP = "DELETE FROM ChiTietKhuyenMaiSanPham WHERE MaCTKM = ?";
         String sqlDeleteCTKM = "DELETE FROM ChuongTrinhKhuyenMai WHERE MaCTKM = ?";

       try {
        con = SQLServerConnect.getConnection();
        con.setAutoCommit(false);

        PreparedStatement ps1 = con.prepareStatement(sqlDeleteKMHD);
        ps1.setString(1, t.getMaCTKM());
        ps1.executeUpdate();

        PreparedStatement ps2 = con.prepareStatement(sqlDeleteKMSP);
        ps2.setString(1, t.getMaCTKM());
        ps2.executeUpdate();

        PreparedStatement ps3 = con.prepareStatement(sqlDeleteCTKM);
        ps3.setString(1, t.getMaCTKM());
        result = ps3.executeUpdate();

        con.commit();
      } catch (SQLException e) {
          try {
            if (con != null) con.rollback();
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
          e.printStackTrace();
      } finally {
           try {
            if (con != null) {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return result;
    }

    @Override
    public ArrayList<ChuongTrinhKhuyenMaiDTO> selectALL() {
        ArrayList<ChuongTrinhKhuyenMaiDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ChuongTrinhKhuyenMai";

        try {
            Connection con = SQLServerConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ChuongTrinhKhuyenMaiDTO ctkm = new ChuongTrinhKhuyenMaiDTO(
                        rs.getString("MaCTKM"),
                        rs.getNString("TenCTKM"),
                        rs.getNString("LoaiKhuyenMai"),
                        rs.getNString("MoTa"),
                        rs.getDate("NgayBatDau").toLocalDate(),
                        rs.getDate("NgayKetThuc").toLocalDate(),
                        rs.getInt("TrangThai")
                );
                list.add(ctkm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public ArrayList<ChuongTrinhKhuyenMaiDTO> selectByCondition(String condition) {
        ArrayList<ChuongTrinhKhuyenMaiDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM ChuongTrinhKhuyenMai WHERE " + condition;

        try {
            // Bước 1: Kết nối CSDL
            Connection con = SQLServerConnect.getConnection();

            // Bước 2: Tạo PreparedStatement
            PreparedStatement ps = con.prepareStatement(sql);

            // Bước 3: Thực thi
            ResultSet rs = ps.executeQuery();

            // Bước 4: Duyệt ResultSet
            while (rs.next()) {
                ChuongTrinhKhuyenMaiDTO ctkm = new ChuongTrinhKhuyenMaiDTO(
                        rs.getString("MaCTKM"),
                        rs.getNString("TenCTKM"),
                        rs.getNString("LoaiKhuyenMai"),
                        rs.getNString("MoTa"),
                        rs.getDate("NgayBatDau").toLocalDate(),
                        rs.getDate("NgayKetThuc").toLocalDate(),
                        rs.getInt("TrangThai")
                );

                list.add(ctkm);
            }

            // Bước 5: Đóng kết nối
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public ChuongTrinhKhuyenMaiDTO selectById(ChuongTrinhKhuyenMaiDTO t) {
        ChuongTrinhKhuyenMaiDTO ketQua = null;

        String sql = "SELECT * FROM ChuongTrinhKhuyenMai WHERE MaCTKM = ?";

        try {
            // Bước 1: Kết nối CSDL
            Connection con = SQLServerConnect.getConnection();

            // Bước 2: Tạo PreparedStatement
            PreparedStatement ps = con.prepareStatement(sql);

            // Bước 3: Gán giá trị cho ?
            ps.setString(1, t.getMaCTKM());

            // Bước 4: Thực thi
            ResultSet rs = ps.executeQuery();

            // Bước 5: Duyệt ResultSet
            if (rs.next()) {
                ketQua = new ChuongTrinhKhuyenMaiDTO(
                        rs.getString("MaCTKM"),
                        rs.getNString("TenCTKM"),
                        rs.getNString("LoaiKhuyenMai"),
                        rs.getNString("MoTa"),
                        rs.getDate("NgayBatDau").toLocalDate(),
                        rs.getDate("NgayKetThuc").toLocalDate(),
                        rs.getInt("TrangThai")
                );
            }

            // Bước 6: Đóng kết nối
            SQLServerConnect.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }
    public String getLastMaCTKM() {
        String lastMa = null;
        String sql = "SELECT TOP 1 MaCTKM FROM ChuongTrinhKhuyenMai ORDER BY MaCTKM DESC";

        try {
            Connection con = SQLServerConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lastMa = rs.getString("MaCTKM");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastMa;
    }
}
