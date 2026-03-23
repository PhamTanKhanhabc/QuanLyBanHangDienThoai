package GUI.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BanHangPanel extends JPanel {

    private JTable tblSanPham, tblGioHang;
    private DefaultTableModel modelSP, modelGio;
    private JTextField txtTim, txtSoLuong, txtTienKhach;
    private JLabel lblTongTien;
    private double tongTien = 0;

    public BanHangPanel() {
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(236, 246, 248));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(taoHeader(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);
    }

    private JPanel taoHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225)),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JLabel lblTitle = new JLabel("BÁN HÀNG NHANH");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel lblSub = new JLabel("Giao diện ngắn gọn, dễ thao tác, đặt ngay dưới trang chủ.");
        lblSub.setForeground(new Color(110, 110, 110));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(lblTitle);
        text.add(lblSub);

        p.add(text, BorderLayout.WEST);
        return p;
    }

    private JPanel taoNoiDung() {
        JPanel main = new JPanel(new GridLayout(1, 2, 12, 12));
        main.setOpaque(false);
        main.add(taoPanelSanPham());
        main.add(taoPanelGioHang());
        return main;
    }

    private JPanel taoPanelSanPham() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder("Sản phẩm"));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setOpaque(false);

        txtTim = new JTextField();
        txtTim.setPreferredSize(new Dimension(220, 32));
        txtSoLuong = new JTextField("1");
        txtSoLuong.setPreferredSize(new Dimension(60, 32));

        JButton btnTim = new JButton("Tìm");
        JButton btnThem = new JButton("Thêm giỏ hàng");
        btnTim.setBackground(new Color(59, 130, 246));
        btnTim.setForeground(Color.WHITE);
        btnThem.setBackground(new Color(16, 185, 129));
        btnThem.setForeground(Color.WHITE);

        top.add(new JLabel("Tìm:"));
        top.add(txtTim);
        top.add(new JLabel("SL:"));
        top.add(txtSoLuong);
        top.add(btnTim);
        top.add(btnThem);

        modelSP = new DefaultTableModel(new String[]{"Mã", "Tên sản phẩm", "Giá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelSP.addRow(new Object[]{"SP001", "iPhone 13", "15.990.000"});
        modelSP.addRow(new Object[]{"SP002", "Samsung S24", "19.490.000"});
        modelSP.addRow(new Object[]{"SP003", "Xiaomi Note 13", "5.790.000"});
        modelSP.addRow(new Object[]{"SP004", "OPPO Reno 11", "9.250.000"});

        tblSanPham = new JTable(modelSP);
        tblSanPham.setRowHeight(28);

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(tblSanPham), BorderLayout.CENTER);

        btnTim.addActionListener(e -> timSanPham());
        btnThem.addActionListener(e -> themGioHang());

        return p;
    }

    private JPanel taoPanelGioHang() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder("Giỏ hàng / Thanh toán"));

        modelGio = new DefaultTableModel(new String[]{"Tên sản phẩm", "SL", "Thành tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblGioHang = new JTable(modelGio);
        tblGioHang.setRowHeight(28);

        JPanel bottom = new JPanel(new GridLayout(4, 2, 8, 8));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.setBackground(Color.WHITE);

        txtTienKhach = new JTextField();
        lblTongTien = new JLabel("0 đ");
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTongTien.setForeground(new Color(220, 38, 38));

        JButton btnTinh = new JButton("Tính tiền");
        JButton btnMoi = new JButton("Làm mới");
        btnTinh.setBackground(new Color(34, 197, 94));
        btnTinh.setForeground(Color.WHITE);
        btnMoi.setBackground(new Color(239, 68, 68));
        btnMoi.setForeground(Color.WHITE);

        bottom.add(new JLabel("Tổng tiền:"));
        bottom.add(lblTongTien);
        bottom.add(new JLabel("Tiền khách đưa:"));
        bottom.add(txtTienKhach);
        bottom.add(btnTinh);
        bottom.add(btnMoi);

        p.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);

        btnTinh.addActionListener(e -> thanhToanTam());
        btnMoi.addActionListener(e -> lamMoi());

        return p;
    }

    private void timSanPham() {
        String key = txtTim.getText().trim().toLowerCase();
        if (key.isEmpty()) return;

        for (int i = 0; i < modelSP.getRowCount(); i++) {
            String ma = modelSP.getValueAt(i, 0).toString().toLowerCase();
            String ten = modelSP.getValueAt(i, 1).toString().toLowerCase();
            if (ma.contains(key) || ten.contains(key)) {
                tblSanPham.setRowSelectionInterval(i, i);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm.");
    }

    private void themGioHang() {
        int row = tblSanPham.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm trước.");
            return;
        }

        try {
            String ten = modelSP.getValueAt(row, 1).toString();
            int sl = Integer.parseInt(txtSoLuong.getText().trim());
            double gia = Double.parseDouble(modelSP.getValueAt(row, 2).toString().replace(".", ""));
            double thanhTien = sl * gia;

            modelGio.addRow(new Object[]{ten, sl, dinhDangTien(thanhTien)});
            tongTien += thanhTien;
            lblTongTien.setText(dinhDangTien(tongTien));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ.");
        }
    }

    private void thanhToanTam() {
        if (modelGio.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống.");
            return;
        }

        try {
            double tienKhach = Double.parseDouble(txtTienKhach.getText().trim().replace(".", ""));
            double tienThua = tienKhach - tongTien;
            JOptionPane.showMessageDialog(this,
                    "Tổng tiền: " + dinhDangTien(tongTien)
                    + "
Tiền thừa: " + dinhDangTien(tienThua)
                    + "

Đây là giao diện mẫu, chưa lưu database.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nhập tiền khách đưa không hợp lệ.");
        }
    }

    private void lamMoi() {
        txtTim.setText("");
        txtSoLuong.setText("1");
        txtTienKhach.setText("");
        tongTien = 0;
        lblTongTien.setText("0 đ");
        modelGio.setRowCount(0);
        tblSanPham.clearSelection();
    }

    private String dinhDangTien(double value) {
        return String.format("%,.0f đ", value).replace(',', '.');
    }
}
