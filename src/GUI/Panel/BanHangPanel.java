package GUI.Panel;

import GUI.Component.TablePanel;
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BanHangPanel extends JPanel {

    private JTable tableSP, tableGioHang;
    private DefaultTableModel modelSP, modelGioHang;
    private JTextField txtMaSP, txtTenSP, txtSoLuong, txtTienKhachDua, txtTienThua;
    private JLabel lblTongTien;
    private double tongTien = 0;
    private final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    public BanHangPanel() {
        initComponents();
        fakeData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 245));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(230, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel northPanel = new JPanel(new GridBagLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN SẢN PHẨM"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaSP = new JTextField(10);
        txtMaSP.setEditable(false);
        txtTenSP = new JTextField(20);
        txtTenSP.setEditable(false);
        txtSoLuong = new JTextField("1", 5);

        JButton btnThem = new JButton("THÊM");
        btnThem.setBackground(new Color(0, 150, 136));
        btnThem.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; northPanel.add(new JLabel("Mã SP:"), gbc);
        gbc.gridx = 1; northPanel.add(txtMaSP, gbc);
        gbc.gridx = 2; northPanel.add(new JLabel("Tên SP:"), gbc);
        gbc.gridx = 3; northPanel.add(txtTenSP, gbc);
        gbc.gridx = 4; northPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 5; northPanel.add(txtSoLuong, gbc);
        gbc.gridx = 6; northPanel.add(btnThem, gbc);

        TablePanel tpSP = new TablePanel("Danh sách sản phẩm", new String[]{"STT", "Mã SP", "Tên SP", "Tồn kho", "Đơn giá"});
        tableSP = tpSP.getTable();
        modelSP = (DefaultTableModel) tableSP.getModel();

        JPanel eastPanel = new JPanel(new BorderLayout(5, 5));
        eastPanel.setPreferredSize(new Dimension(420, 0));
        eastPanel.setBackground(Color.WHITE);

        TablePanel tpGioHang = new TablePanel("Giỏ hàng", new String[]{"Mã SP", "Tên SP", "SL", "Đơn giá", "Thành tiền"});
        tableGioHang = tpGioHang.getTable();
        modelGioHang = (DefaultTableModel) tableGioHang.getModel();

        JButton btnXoa = new JButton("XÓA");
        btnXoa.setBackground(new Color(255, 102, 102));
        btnXoa.setForeground(Color.WHITE);

        JPanel pnlXoa = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlXoa.setBackground(Color.WHITE);
        pnlXoa.add(btnXoa);

        JPanel cartWrapper = new JPanel(new BorderLayout());
        cartWrapper.setBackground(Color.WHITE);
        cartWrapper.add(tpGioHang, BorderLayout.CENTER);
        cartWrapper.add(pnlXoa, BorderLayout.SOUTH);

        JPanel payPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        payPanel.setBackground(Color.WHITE);
        payPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        lblTongTien = new JLabel("0 VNĐ", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTongTien.setForeground(Color.RED);
        txtTienKhachDua = new JTextField();
        txtTienThua = new JTextField();
        txtTienThua.setEditable(false);

        payPanel.add(new JLabel("TỔNG CỘNG:"));
        payPanel.add(lblTongTien);
        payPanel.add(new JLabel("Tiền khách đưa:"));
        payPanel.add(txtTienKhachDua);
        payPanel.add(new JLabel("Tiền thừa:"));
        payPanel.add(txtTienThua);

        JButton btnHuy = new JButton("HỦY BỎ");
        btnHuy.setBackground(new Color(255, 82, 82));
        btnHuy.setForeground(Color.WHITE);

        JButton btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setBackground(new Color(46, 204, 113));
        btnThanhToan.setForeground(Color.WHITE);

        JPanel btnGroup = new JPanel(new GridLayout(1, 2, 10, 10));
        btnGroup.setBackground(Color.WHITE);
        btnGroup.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        btnGroup.add(btnHuy);
        btnGroup.add(btnThanhToan);

        JPanel invoiceWrapper = new JPanel(new BorderLayout());
        invoiceWrapper.setBackground(Color.WHITE);
        invoiceWrapper.add(payPanel, BorderLayout.CENTER);
        invoiceWrapper.add(btnGroup, BorderLayout.SOUTH);

        eastPanel.add(cartWrapper, BorderLayout.CENTER);
        eastPanel.add(invoiceWrapper, BorderLayout.SOUTH);

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(tpSP, BorderLayout.CENTER);
        panel.add(eastPanel, BorderLayout.EAST);
        add(panel, BorderLayout.CENTER);

        tableSP.getSelectionModel().addListSelectionListener(e -> {
            int row = tableSP.getSelectedRow();
            if (!e.getValueIsAdjusting() && row != -1) {
                txtMaSP.setText(modelSP.getValueAt(row, 1).toString());
                txtTenSP.setText(modelSP.getValueAt(row, 2).toString());
            }
        });

        btnThem.addActionListener(e -> themVaoGio());
        btnXoa.addActionListener(e -> xoaKhoiGio());
        txtTienKhachDua.addActionListener(e -> tinhTienThua());
        btnThanhToan.addActionListener(e -> thanhToanTam());
        btnHuy.addActionListener(e -> lamMoi());
    }

    private void fakeData() {
        modelSP.addRow(new Object[]{1, "SP001", "iPhone 13", 12, "15,990,000 VNĐ"});
        modelSP.addRow(new Object[]{2, "SP002", "Samsung S24", 8, "19,490,000 VNĐ"});
        modelSP.addRow(new Object[]{3, "SP003", "Xiaomi Note 13", 20, "5,790,000 VNĐ"});
        modelSP.addRow(new Object[]{4, "SP004", "OPPO Reno 11", 15, "9,250,000 VNĐ"});
    }

    private void themVaoGio() {
        int row = tableSP.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm.");
            return;
        }

        try {
            String maSP = modelSP.getValueAt(row, 1).toString();
            String tenSP = modelSP.getValueAt(row, 2).toString();
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            double donGia = Double.parseDouble(modelSP.getValueAt(row, 4).toString().replace(",", "").replace(" VNĐ", ""));
            double thanhTien = soLuong * donGia;

            modelGioHang.addRow(new Object[]{maSP, tenSP, soLuong, df.format(donGia), df.format(thanhTien)});
            tongTien += thanhTien;
            lblTongTien.setText(df.format(tongTien));
            tinhTienThua();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ.");
        }
    }

    private void xoaKhoiGio() {
        int row = tableGioHang.getSelectedRow();
        if (row == -1) return;

        double thanhTien = Double.parseDouble(modelGioHang.getValueAt(row, 4).toString().replace(",", "").replace(" VNĐ", ""));
        tongTien -= thanhTien;
        lblTongTien.setText(df.format(tongTien));
        modelGioHang.removeRow(row);
        tinhTienThua();
    }

    private void tinhTienThua() {
        try {
            String raw = txtTienKhachDua.getText().trim().replace(",", "").replace(" VNĐ", "");
            if (raw.isEmpty()) {
                txtTienThua.setText("");
                return;
            }
            double tienKhach = Double.parseDouble(raw);
            txtTienThua.setText(df.format(tienKhach - tongTien));
        } catch (Exception e) {
            txtTienThua.setText("");
        }
    }

    private void thanhToanTam() {
        if (modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Đây là giao diện bán hàng mẫu, chưa nối database.");
    }

    private void lamMoi() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtSoLuong.setText("1");
        txtTienKhachDua.setText("");
        txtTienThua.setText("");
        tongTien = 0;
        lblTongTien.setText("0 VNĐ");
        modelGioHang.setRowCount(0);
        tableSP.clearSelection();
    }
}
