package GUI.Panel;

import DAO.SQLServerConnect;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class BanHangPanel extends JPanel {

    private final DecimalFormat df = new DecimalFormat("#,### VNĐ");
    private final List<SanPham> dsSanPham = new ArrayList<>();
    private String maNhanVien = "NV01";

    private JTextField txtSoDT;
    private JTextField txtTenKhach;
    private JTextField txtTim;
    private JComboBox<String> cboLoai;
    private JComboBox<String> cboHang;

    private JPanel pnlSanPham;
    private JTable tblHoaDon;
    private DefaultTableModel modelHoaDon;
    private JLabel lblTongHoaDon;
    private JLabel lblTongThanhToan;

    public BanHangPanel() {
        initUI();
        loadLoai();
        loadHang();
        loadSanPham();
    }

    public void setMaNhanVien(String maNhanVien) {
        if (maNhanVien != null && !maNhanVien.trim().isEmpty()) {
            this.maNhanVien = maNhanVien.trim();
        }
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 10));
        setBackground(new Color(245, 246, 248));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JLabel title = new JLabel("TẤN KHÁNH STORE");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JLabel user = new JLabel("Người bán hàng");
        user.setFont(new Font("Arial", Font.BOLD, 18));
        user.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel role = new JLabel("Quản lý cửa hàng");
        role.setFont(new Font("Arial", Font.BOLD, 14));
        role.setForeground(new Color(220, 0, 0));
        role.setAlignmentX(Component.RIGHT_ALIGNMENT);

        right.add(user);
        right.add(Box.createVerticalStrut(4));
        right.add(role);

        p.add(title, BorderLayout.WEST);
        p.add(right, BorderLayout.EAST);
        return p;
    }

    private JPanel createBody() {
        JPanel body = new JPanel(new BorderLayout(10, 10));
        body.setOpaque(false);

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 0));
        top.setOpaque(false);
        top.add(createThongTinKhach());
        top.add(createBoLoc());

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 0));
        center.setOpaque(false);
        center.add(createHoaDonPanel());
        center.add(createSanPhamPanel());

        body.add(top, BorderLayout.NORTH);
        body.add(center, BorderLayout.CENTER);
        return body;
    }

    private JPanel createThongTinKhach() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(12, 16, 12, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtSoDT = new JTextField();
        txtTenKhach = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        p.add(new JLabel("Số điện thoại"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(txtSoDT, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        p.add(new JLabel("Tên khách hàng"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(txtTenKhach, gbc);

        JButton btnTim = createButton("Tìm", new Color(245, 245, 245));
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 2; gbc.weightx = 0;
        p.add(btnTim, gbc);
        btnTim.addActionListener(e -> timKhachHang());

        return wrapSection("Thông tin khách hàng", p);
    }

    private JPanel createBoLoc() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(12, 16, 12, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTim = new JTextField();
        cboLoai = new JComboBox<>(new String[]{"Tất cả"});
        cboHang = new JComboBox<>(new String[]{"Tất cả"});

        JButton btnLamMoi = createButton("Làm mới", new Color(245, 245, 245));
        JButton btnApDung = createButton("Áp dụng", new Color(245, 245, 245));

        gbc.gridx = 0; gbc.gridy = 0;
        p.add(new JLabel("Tên sản phẩm"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(txtTim, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        p.add(new JLabel("Loại"), gbc);
        gbc.gridx = 3;
        p.add(cboLoai, gbc);

        gbc.gridx = 4;
        p.add(new JLabel("Hãng"), gbc);
        gbc.gridx = 5;
        p.add(cboHang, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        p.add(btnLamMoi, gbc);
        gbc.gridx = 2;
        p.add(btnApDung, gbc);

        btnApDung.addActionListener(e -> loadSanPham());
        btnLamMoi.addActionListener(e -> {
            txtTim.setText("");
            cboLoai.setSelectedIndex(0);
            cboHang.setSelectedIndex(0);
            loadSanPham();
        });

        return wrapSection("Bộ lọc", p);
    }

    private JPanel createHoaDonPanel() {
        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBackground(Color.WHITE);

        modelHoaDon = new DefaultTableModel(new Object[]{"Mã", "Tên", "Giá", "SL", "Tổng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblHoaDon = new JTable(modelHoaDon);
        tblHoaDon.setRowHeight(28);
        tblHoaDon.setFont(new Font("Arial", Font.PLAIN, 14));

        JTableHeader header = tblHoaDon.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tblHoaDon);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235)));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(new EmptyBorder(5, 8, 8, 8));

        JPanel left = new JPanel();
        left.setBackground(Color.WHITE);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        lblTongHoaDon = new JLabel("Tổng hóa đơn : 0 VNĐ");
        lblTongHoaDon.setFont(new Font("Arial", Font.BOLD, 14));

        lblTongThanhToan = new JLabel("Tổng thanh toán : 0 VNĐ");
        lblTongThanhToan.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongThanhToan.setForeground(new Color(0, 128, 0));

        left.add(lblTongHoaDon);
        left.add(Box.createVerticalStrut(10));
        left.add(lblTongThanhToan);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(Color.WHITE);

        JButton btnXoa = createButton("Xóa dòng", new Color(245, 245, 245));
        JButton btnHuy = createButton("Hủy", new Color(245, 245, 245));
        JButton btnThanhToan = createButton("Thanh toán", new Color(240, 240, 240));

        right.add(btnXoa);
        right.add(btnHuy);
        right.add(btnThanhToan);

        btnXoa.addActionListener(e -> xoaDong());
        btnHuy.addActionListener(e -> lamMoiHoaDon());
        btnThanhToan.addActionListener(e -> thanhToan());

        bottom.add(left, BorderLayout.WEST);
        bottom.add(right, BorderLayout.EAST);

        content.add(scroll, BorderLayout.CENTER);
        content.add(bottom, BorderLayout.SOUTH);
        return wrapSection("Thông tin hóa đơn", content);
    }

    private JPanel createSanPhamPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);

        pnlSanPham = new JPanel(new GridLayout(0, 4, 12, 12));
        pnlSanPham.setBackground(Color.WHITE);
        pnlSanPham.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(pnlSanPham);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        content.add(scroll, BorderLayout.CENTER);
        return wrapSection("Danh sách sản phẩm", content);
    }

    private JPanel wrapSection(String title, JPanel content) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        titleBar.setBackground(new Color(222, 232, 242));
        titleBar.add(new JLabel(title));

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        body.add(content, BorderLayout.CENTER);

        wrapper.add(titleBar, BorderLayout.NORTH);
        wrapper.add(body, BorderLayout.CENTER);
        return wrapper;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        return btn;
    }

    private JPanel createCard(SanPham sp) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(150, 180));

        JLabel img = new JLabel("📦", SwingConstants.CENTER);
        img.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ten = new JLabel(sp.tenSp);
        ten.setFont(new Font("Arial", Font.PLAIN, 13));
        ten.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel gia = new JLabel(df.format((long) sp.donGia));
        gia.setFont(new Font("Arial", Font.BOLD, 16));
        gia.setForeground(new Color(220, 0, 0));
        gia.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel loai = new JLabel(sp.tenLoai);
        loai.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ton = new JLabel("Tồn kho: " + sp.soLuongTon);
        ton.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(5));
        card.add(img);
        card.add(Box.createVerticalStrut(10));
        card.add(ten);
        card.add(Box.createVerticalStrut(8));
        card.add(gia);
        card.add(Box.createVerticalStrut(6));
        card.add(loai);
        card.add(Box.createVerticalStrut(4));
        card.add(ton);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                themVaoHoaDon(sp);
            }
        });

        return card;
    }

    private void loadLoai() {
        cboLoai.removeAllItems();
        cboLoai.addItem("Tất cả");
        try (Connection con = SQLServerConnect.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT TenLoai FROM LoaiSanPham WHERE TrangThai = 1 ORDER BY TenLoai");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cboLoai.addItem(rs.getString(1));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không tải được loại sản phẩm.");
        }
    }

    private void loadHang() {
        cboHang.removeAllItems();
        cboHang.addItem("Tất cả");
        try (Connection con = SQLServerConnect.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT TenHang FROM HangSanXuat WHERE TrangThai = 1 ORDER BY TenHang");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cboHang.addItem(rs.getString(1));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không tải được hãng sản phẩm.");
        }
    }

    private void loadSanPham() {
        dsSanPham.clear();
        pnlSanPham.removeAll();

        String sql = "SELECT sp.MaSp, sp.TenSp, sp.SoLuongTon, sp.DonGia, l.TenLoai, h.TenHang "
                + "FROM SanPham sp "
                + "JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai "
                + "JOIN HangSanXuat h ON sp.MaHang = h.MaHang "
                + "WHERE sp.TrangThai = 1 "
                + "AND (? = N'Tất cả' OR l.TenLoai = ?) "
                + "AND (? = N'Tất cả' OR h.TenHang = ?) "
                + "AND sp.TenSp LIKE ? "
                + "ORDER BY sp.TenSp";

        try (Connection con = SQLServerConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String loai = String.valueOf(cboLoai.getSelectedItem());
            String hang = String.valueOf(cboHang.getSelectedItem());
            String tim = "%" + txtTim.getText().trim() + "%";

            ps.setString(1, loai);
            ps.setString(2, loai);
            ps.setString(3, hang);
            ps.setString(4, hang);
            ps.setString(5, tim);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SanPham sp = new SanPham();
                    sp.maSp = rs.getString("MaSp");
                    sp.tenSp = rs.getString("TenSp");
                    sp.soLuongTon = rs.getInt("SoLuongTon");
                    sp.donGia = rs.getDouble("DonGia");
                    sp.tenLoai = rs.getString("TenLoai");
                    sp.tenHang = rs.getString("TenHang");
                    dsSanPham.add(sp);
                    pnlSanPham.add(createCard(sp));
                }
            }

            if (dsSanPham.isEmpty()) {
                pnlSanPham.add(new JLabel("Không có sản phẩm phù hợp."));
            }

            pnlSanPham.revalidate();
            pnlSanPham.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không tải được sản phẩm từ database.\n" + e.getMessage());
        }
    }

    private void themVaoHoaDon(SanPham sp) {
        if (sp.soLuongTon <= 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng.");
            return;
        }

        for (int i = 0; i < modelHoaDon.getRowCount(); i++) {
            if (sp.maSp.equals(modelHoaDon.getValueAt(i, 0).toString())) {
                int sl = Integer.parseInt(modelHoaDon.getValueAt(i, 3).toString()) + 1;
                if (sl > sp.soLuongTon) {
                    JOptionPane.showMessageDialog(this, "Vượt quá tồn kho.");
                    return;
                }
                modelHoaDon.setValueAt(sl, i, 3);
                modelHoaDon.setValueAt(df.format((long) (sl * sp.donGia)), i, 4);
                capNhatTongTien();
                return;
            }
        }

        modelHoaDon.addRow(new Object[]{
            sp.maSp,
            sp.tenSp,
            df.format((long) sp.donGia),
            1,
            df.format((long) sp.donGia)
        });
        capNhatTongTien();
    }

    private void xoaDong() {
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa.");
            return;
        }
        modelHoaDon.removeRow(row);
        capNhatTongTien();
    }

    private void capNhatTongTien() {
        long tong = 0;
        for (int i = 0; i < modelHoaDon.getRowCount(); i++) {
            tong += toLong(modelHoaDon.getValueAt(i, 4).toString());
        }
        lblTongHoaDon.setText("Tổng hóa đơn : " + df.format(tong));
        lblTongThanhToan.setText("Tổng thanh toán : " + df.format(tong));
    }

    private void timKhachHang() {
        String sdt = txtSoDT.getText().trim();
        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập số điện thoại khách hàng.");
            return;
        }

        String sql = "SELECT TOP 1 Ho, Ten FROM KhachHang WHERE SoDT = ? AND TrangThai = 1";
        try (Connection con = SQLServerConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txtTenKhach.setText((rs.getString("Ho") == null ? "" : rs.getString("Ho") + " ") + rs.getString("Ten"));
                } else {
                    JOptionPane.showMessageDialog(this, "Chưa có khách này, bạn có thể nhập tên mới.");
                    txtTenKhach.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không tìm được khách hàng.");
        }
    }

    private void thanhToan() {
        if (modelHoaDon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đang trống.");
            return;
        }

        String tenKhach = txtTenKhach.getText().trim();
        String soDT = txtSoDT.getText().trim();
        if (tenKhach.isEmpty() || soDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập đủ thông tin khách hàng.");
            return;
        }

        Connection con = null;
        try {
            con = SQLServerConnect.getConnection();
            con.setAutoCommit(false);

            String maKH = layHoacThemKhachHang(con, tenKhach, soDT);
            String maHD = taoMaMoi(con, "HoaDon", "MaHD", "HD");
            long tongTien = toLong(lblTongThanhToan.getText().replace("Tổng thanh toán : ", ""));

            String sqlHD = "INSERT INTO HoaDon(MaHD, MaNV, MaKH, NgayLapHD, TongTien, TrangThai) VALUES(?,?,?,?,?,1)";
            try (PreparedStatement ps = con.prepareStatement(sqlHD)) {
                ps.setString(1, maHD);
                ps.setString(2, maNhanVien);
                ps.setString(3, maKH);
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                ps.setLong(5, tongTien);
                ps.executeUpdate();
            }

            String sqlCT = "INSERT INTO ChiTietHoaDon(MaHD, MaSP, SL, DG_Ban, ThanhTien) VALUES(?,?,?,?,?)";
            String sqlUpdateSP = "UPDATE SanPham SET SoLuongTon = SoLuongTon - ? WHERE MaSp = ?";

            try (PreparedStatement psCT = con.prepareStatement(sqlCT);
                 PreparedStatement psSP = con.prepareStatement(sqlUpdateSP)) {

                for (int i = 0; i < modelHoaDon.getRowCount(); i++) {
                    String maSP = modelHoaDon.getValueAt(i, 0).toString();
                    long donGia = toLong(modelHoaDon.getValueAt(i, 2).toString());
                    int sl = Integer.parseInt(modelHoaDon.getValueAt(i, 3).toString());
                    long thanhTien = toLong(modelHoaDon.getValueAt(i, 4).toString());

                    psCT.setString(1, maHD);
                    psCT.setString(2, maSP);
                    psCT.setInt(3, sl);
                    psCT.setLong(4, donGia);
                    psCT.setLong(5, thanhTien);
                    psCT.addBatch();

                    psSP.setInt(1, sl);
                    psSP.setString(2, maSP);
                    psSP.addBatch();
                }
                psCT.executeBatch();
                psSP.executeBatch();
            }

            con.commit();
            JOptionPane.showMessageDialog(this, "Thanh toán thành công. Mã hóa đơn: " + maHD);
            lamMoiHoaDon();
            loadSanPham();
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
            }
            JOptionPane.showMessageDialog(this, "Thanh toán thất bại.\n" + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    private String layHoacThemKhachHang(Connection con, String tenDayDu, String soDT) throws SQLException {
        String sqlFind = "SELECT TOP 1 MaKH FROM KhachHang WHERE SoDT = ? AND TrangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sqlFind)) {
            ps.setString(1, soDT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("MaKH");
            }
        }

        String maKH = taoMaMoi(con, "KhachHang", "MaKH", "KH");
        String ho = "";
        String ten = tenDayDu;
        int index = tenDayDu.lastIndexOf(" ");
        if (index > 0) {
            ho = tenDayDu.substring(0, index).trim();
            ten = tenDayDu.substring(index + 1).trim();
        }

        String sqlInsert = "INSERT INTO KhachHang(MaKH, Ho, Ten, DiaChi, SoDT, TrangThai) VALUES(?,?,?,?,?,1)";
        try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
            ps.setString(1, maKH);
            ps.setString(2, ho);
            ps.setString(3, ten);
            ps.setString(4, "");
            ps.setString(5, soDT);
            ps.executeUpdate();
        }
        return maKH;
    }

    private String taoMaMoi(Connection con, String table, String column, String prefix) throws SQLException {
        String sql = "SELECT TOP 1 " + column + " FROM " + table + " WHERE " + column + " LIKE ? ORDER BY " + column + " DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                int next = 1;
                if (rs.next()) {
                    String last = rs.getString(1).replace(prefix, "");
                    next = Integer.parseInt(last) + 1;
                }
                return String.format("%s%03d", prefix, next);
            }
        }
    }

    private void lamMoiHoaDon() {
        modelHoaDon.setRowCount(0);
        txtSoDT.setText("");
        txtTenKhach.setText("");
        capNhatTongTien();
    }

    private long toLong(String s) {
        return Long.parseLong(s.replace("VNĐ", "").replace(",", "").trim());
    }

    private static class SanPham {
        String maSp;
        String tenSp;
        int soLuongTon;
        double donGia;
        String tenLoai;
        String tenHang;
    }
}
