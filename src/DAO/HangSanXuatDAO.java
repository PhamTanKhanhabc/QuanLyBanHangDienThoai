    package DAO;

    import DTO.HangSanXuatDTO;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;

    public class HangSanXuatDAO implements DAOInterface<HangSanXuatDTO> {

        public static HangSanXuatDAO getInstance() {
            return new HangSanXuatDAO();
        }

        @Override
        public int insert(HangSanXuatDTO t) {
            int ketQua = 0;
            try {
                Connection con = SQLServerConnect.getConnection();

                String sql = "INSERT INTO HangSanXuat(MaHang, TenHang, DiaChi, TrangThai) VALUES (?, ?, ?, 1)";

                PreparedStatement pst = con.prepareStatement(sql);

                pst.setInt(1, t.getMaHang());
                pst.setString(2, t.getTenHang());
                pst.setString(3, t.getDiaChi());

                ketQua = pst.executeUpdate();

                pst.close();
                SQLServerConnect.closeConnection(con);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return ketQua;
        }

        @Override
        public int update(HangSanXuatDTO t) {
            int ketQua = 0;
            try {
                Connection con = SQLServerConnect.getConnection();

                String sql = "UPDATE HangSanXuat SET TenHang = ?, DiaChi = ? WHERE MaHang = ? AND TrangThai = 1";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, t.getTenHang());
                pst.setString(2, t.getDiaChi());
                pst.setInt(3, t.getMaHang());   

                ketQua = pst.executeUpdate();

                pst.close();
                SQLServerConnect.closeConnection(con);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return ketQua;
        }

        @Override
        public int delete(HangSanXuatDTO t) {
            int ketQua = 0;
            try {
                Connection con = SQLServerConnect.getConnection();

                String sql = "UPDATE HangSanXuat SET TrangThai = 0 WHERE MaHang = ? AND TrangThai = 1";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setInt(1, t.getMaHang());

                ketQua = pst.executeUpdate();

                pst.close();
                SQLServerConnect.closeConnection(con);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return ketQua;
        }

        @Override
        public ArrayList<HangSanXuatDTO> selectALL() {
            ArrayList<HangSanXuatDTO> ketQua = new ArrayList<>();
            try {
                Connection con = SQLServerConnect.getConnection();

                String sql = "SELECT * FROM HangSanXuat WHERE TrangThai = 1";
                PreparedStatement pst = con.prepareStatement(sql);

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {

                    HangSanXuatDTO hsx = new HangSanXuatDTO(
                            rs.getInt("MaHang"),
                            rs.getString("TenHang"),
                            rs.getString("DiaChi"),
                            rs.getInt("TrangThai")
                    );

                    ketQua.add(hsx);
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
        public HangSanXuatDTO selectById(HangSanXuatDTO t) {
            HangSanXuatDTO ketQua = null;
            try {
                Connection con = SQLServerConnect.getConnection();

                String sql = "SELECT * FROM HangSanXuat WHERE MaHang = ? AND TrangThai = 1";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setInt(1, t.getMaHang());

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {

                    ketQua = new HangSanXuatDTO(
                            rs.getInt("MaHang"),
                            rs.getString("TenHang"),
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
        public ArrayList<HangSanXuatDTO> selectByCondition(String condition) {
            ArrayList<HangSanXuatDTO> ketQua = new ArrayList<>();
            try {
                Connection con = SQLServerConnect.getConnection();

                String sql = "SELECT * FROM HangSanXuat WHERE TrangThai = 1 AND " + condition;
                PreparedStatement pst = con.prepareStatement(sql);

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {

                    HangSanXuatDTO hsx = new HangSanXuatDTO(
                            rs.getInt("MaHang"),
                            rs.getString("TenHang"),
                            rs.getString("DiaChi"),
                            rs.getInt("TrangThai")
                    );

                    ketQua.add(hsx);
                }

                rs.close();
                pst.close();
                SQLServerConnect.closeConnection(con);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return ketQua;
        }

        public int getLastMaHang() {
            int lastMa = 0;
            try {
                Connection con = SQLServerConnect.getConnection();

                String sql = "SELECT TOP 1 MaHang FROM HangSanXuat ORDER BY MaHang DESC";
                PreparedStatement pst = con.prepareStatement(sql);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    lastMa = rs.getInt("MaHang");
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