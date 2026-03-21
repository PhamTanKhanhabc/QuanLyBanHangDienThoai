package GUI.Panel;

import BUS.KhachHangBUS;
import BUS.HoaDonBUS;
import BUS.SanPhamBUS;
import BUS.ChiTietHoaDonBUS;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import DTO.ChiTietHoaDonDTO;
import GUI.Component.TablePanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HoaDonPanel extends JPanel {
    private SanPhamBUS spBUS = new SanPhamBUS();
    private HoaDonBUS hdBUS = new HoaDonBUS();
    private KhachHangBUS khBUS = new KhachHangBUS();
    private ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();
    private JTable tableSP, tableGioHang;
    private DefaultTableModel modelSP, modelGioHang;
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtSoLuongMua;
    private JTextField txtMaHD, txtSdtKH, txtTenKH, txtTienKhachDua, txtTienThua;
    private JLabel lblTongTien;
    private JButton btnThemSP, btnInHD, btnHuy, btnXoaDong;
    private double tongTien = 0;
    private JTable tableLichSuHD, tableChiTietHD;
    private DefaultTableModel modelLichSuHD, modelChiTietHD;
    private JLabel lblLS_MaHD, lblLS_TongTien;
    private DecimalFormat df = new DecimalFormat("#,### VNĐ");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public HoaDonPanel() {
        initComponents();
        loadTableSanPham(spBUS.getAll());
        loadTableLichSuHD(hdBUS.getAll());
        txtMaHD.setText(hdBUS.generateMaHD()); 
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 245));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.addTab("🛒 TẠO HÓA ĐƠN (BÁN HÀNG)", createTabBanHang());
        tabbedPane.addTab("📜 LỊCH SỬ HÓA ĐƠN", createTabLichSu());

        add(tabbedPane, BorderLayout.CENTER);
    }
    private JPanel createTabBanHang() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(230, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel northPanel = new JPanel(new GridBagLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN SẢN PHẨM"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaSP = new JTextField(10); txtMaSP.setEditable(false);
        txtTenSP = new JTextField(20); txtTenSP.setEditable(false);
        txtDonGia = new JTextField(10); txtDonGia.setEditable(false);
        txtSoLuongMua = new JTextField("1", 5);
        btnThemSP = new JButton("THÊM", new FlatSVGIcon("icon/add.svg",24,24)); 
        btnThemSP.setBackground(new Color(0, 150, 136));
        btnThemSP.setForeground(Color.WHITE);

        gbc.gridx = 0; northPanel.add(new JLabel("Mã SP:"), gbc);
        gbc.gridx = 1; northPanel.add(txtMaSP, gbc);
        gbc.gridx = 2; northPanel.add(new JLabel("Tên SP:"), gbc);
        gbc.gridx = 3; northPanel.add(txtTenSP, gbc);
        gbc.gridx = 4; northPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 5; northPanel.add(txtSoLuongMua, gbc);
        gbc.gridx = 6; northPanel.add(btnThemSP, gbc);

        
        TablePanel tpSP = new TablePanel("Danh sách sản phẩm", new String[]{"STT", "Mã SP", "Tên SP", "Tồn kho", "Đơn giá"});
        tableSP = tpSP.getTable();
        modelSP = (DefaultTableModel) tableSP.getModel();

       
        JPanel eastPanel = new JPanel(new BorderLayout(5, 5));
        eastPanel.setPreferredSize(new Dimension(420, 0));
        eastPanel.setBackground(Color.WHITE);

        
        TablePanel tpGioHang = new TablePanel("Giỏ hàng", new String[]{"Mã SP", "Tên SP", "SL", "Đơn giá", "Thành tiền"});
        tableGioHang = tpGioHang.getTable();
        modelGioHang = (DefaultTableModel) tableGioHang.getModel();
        
        JPanel pnlXoa = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlXoa.setBackground(Color.WHITE);
        btnXoaDong = new JButton();
        btnXoaDong.setIcon(new FlatSVGIcon("icon/delete.svg", 24, 24)); 
        btnXoaDong.setBackground(new Color(255, 102, 102));
        pnlXoa.add(btnXoaDong);

        JPanel cartWrapper = new JPanel(new BorderLayout());
        cartWrapper.add(tpGioHang, BorderLayout.CENTER);
        cartWrapper.add(pnlXoa, BorderLayout.SOUTH);

        
        JPanel invoiceWrapper = new JPanel(new BorderLayout());
        invoiceWrapper.setBackground(Color.WHITE);
        
        JPanel payPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        payPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        payPanel.setBackground(Color.WHITE);

        txtMaHD = new JTextField(); txtMaHD.setEditable(false);
        txtSdtKH = new JTextField();
        txtTenKH = new JTextField();
        lblTongTien = new JLabel("0 VNĐ", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 22)); lblTongTien.setForeground(Color.RED);
        txtTienKhachDua = new JTextField();
        txtTienThua = new JTextField(); txtTienThua.setEditable(false);

        payPanel.add(new JLabel("Mã Hóa Đơn:")); payPanel.add(txtMaHD);
        payPanel.add(new JLabel("SĐT Khách hàng:")); payPanel.add(txtSdtKH);
        payPanel.add(new JLabel("Tên Khách hàng:")); payPanel.add(txtTenKH);
        payPanel.add(new JLabel("TỔNG CỘNG:")); payPanel.add(lblTongTien);
        payPanel.add(new JLabel("Tiền khách đưa:")); payPanel.add(txtTienKhachDua);
        payPanel.add(new JLabel("Tiền thừa:")); payPanel.add(txtTienThua);

        JPanel btnGroup = new JPanel(new GridLayout(1, 2, 10, 10));
        btnGroup.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        btnGroup.setBackground(Color.WHITE);
        btnHuy = new JButton("HỦY BỎ"); btnHuy.setBackground(new Color(255, 82, 82)); btnHuy.setForeground(Color.WHITE);
        btnInHD = new JButton("IN HÓA ĐƠN"); btnInHD.setBackground(new Color(46, 204, 113)); btnInHD.setForeground(Color.WHITE);
        btnGroup.add(btnHuy); btnGroup.add(btnInHD);

        invoiceWrapper.add(payPanel, BorderLayout.CENTER);
        invoiceWrapper.add(btnGroup, BorderLayout.SOUTH);

        eastPanel.add(cartWrapper, BorderLayout.CENTER);
        eastPanel.add(invoiceWrapper, BorderLayout.SOUTH);

        
        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(tpSP, BorderLayout.CENTER);
        panel.add(eastPanel, BorderLayout.EAST);

        // --- EVENTS TAB 1 ---
        tableSP.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableSP.getSelectedRow();
                if (row != -1) {
                    txtMaSP.setText(modelSP.getValueAt(row, 1).toString());
                    txtTenSP.setText(modelSP.getValueAt(row, 2).toString());
                    txtDonGia.setText(modelSP.getValueAt(row, 4).toString());
                }
            }
        });

        btnThemSP.addActionListener(e -> addToCart());
        
        btnXoaDong.addActionListener(e -> {
            int row = tableGioHang.getSelectedRow();
            if (row != -1) {
                String value = modelGioHang.getValueAt(row, 4).toString().replace(",", "").replace(" VNĐ", "");
                tongTien -= Double.parseDouble(value);
                lblTongTien.setText(df.format(tongTien));
                modelGioHang.removeRow(row);
                tinhTienThua(); 
            }
        });

        txtTienKhachDua.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { tinhTienThua(); }
        });

        btnInHD.addActionListener(e -> thanhToan());
        btnHuy.addActionListener(e -> resetForm());

        return panel;
    }

    private JPanel createTabLichSu() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(230, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        TablePanel tpLS = new TablePanel("Danh sách Hóa Đơn", new String[]{"STT", "Mã Hóa Đơn", "Mã NV", "Mã KH", "Ngày Lập", "Tổng Tiền"});
        tableLichSuHD = tpLS.getTable();
        modelLichSuHD = (DefaultTableModel) tableLichSuHD.getModel();
        panel.add(tpLS, BorderLayout.CENTER);

        
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setPreferredSize(new Dimension(450, 0)); 
        rightPanel.setBackground(Color.WHITE);

        TablePanel tpCTHD = new TablePanel("Chi tiết sản phẩm", new String[]{"STT", "Mã SP", "Số lượng", "Đơn giá", "Thành tiền"});
        tableChiTietHD = tpCTHD.getTable();
        modelChiTietHD = (DefaultTableModel) tableChiTietHD.getModel();

        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        infoPanel.setBackground(Color.WHITE);
        lblLS_MaHD = new JLabel("Hóa Đơn: Chưa chọn", SwingConstants.CENTER);
        lblLS_MaHD.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblLS_TongTien = new JLabel("0 VNĐ", SwingConstants.CENTER);
        lblLS_TongTien.setFont(new Font("SansSerif", Font.BOLD, 24)); lblLS_TongTien.setForeground(Color.RED);
        
        infoPanel.add(lblLS_MaHD);
        infoPanel.add(new JLabel("TỔNG THÀNH TIỀN:", SwingConstants.CENTER));
        infoPanel.add(lblLS_TongTien);

        rightPanel.add(tpCTHD, BorderLayout.CENTER);
        rightPanel.add(infoPanel, BorderLayout.SOUTH);
        panel.add(rightPanel, BorderLayout.EAST);

       
        tableLichSuHD.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableLichSuHD.getSelectedRow();
                if (row != -1) {
                    String maHD = modelLichSuHD.getValueAt(row, 1).toString();
                    lblLS_MaHD.setText("Hóa Đơn: " + maHD);
                    lblLS_TongTien.setText(modelLichSuHD.getValueAt(row, 5).toString());
                    loadTableChiTietHoaDon(maHD);
                }
            }
        });

        return panel;
    }

   
    private void addToCart() {
        try {
            if(txtMaSP.getText().isEmpty()) return;
            String ma = txtMaSP.getText();
            String ten = txtTenSP.getText();
            int sl = Integer.parseInt(txtSoLuongMua.getText());
            double gia = Double.parseDouble(txtDonGia.getText().replace(",", "").replace(" VNĐ", ""));
            double thanhTien = sl * gia;

            modelGioHang.addRow(new Object[]{ma, ten, sl, df.format(gia), df.format(thanhTien)});
            tongTien += thanhTien;
            lblTongTien.setText(df.format(tongTien));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!");
        }
    }

    private void tinhTienThua() {
        try {
            double khachDua = Double.parseDouble(txtTienKhachDua.getText());
            txtTienThua.setText(df.format(khachDua - tongTien));
        } catch (Exception e) { txtTienThua.setText(""); }
    }

    private void thanhToan() {
        if (modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!"); return;
        }

        String maKHThatSu = khBUS.getMaByInfo(txtSdtKH.getText().trim());
        if (maKHThatSu == null) maKHThatSu = khBUS.getMaByInfo(txtTenKH.getText().trim());

        if (maKHThatSu == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!"); return;
        }

       
        HoaDonDTO hd = new HoaDonDTO(txtMaHD.getText().trim(), "NV01", maKHThatSu, LocalDateTime.now(), tongTien, 1);

        if (hdBUS.add(hd)) {
            
            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                String maSP = modelGioHang.getValueAt(i, 0).toString();
                int sl = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                double gia = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace(",", "").replace(" VNĐ", ""));
                double tien = Double.parseDouble(modelGioHang.getValueAt(i, 4).toString().replace(",", "").replace(" VNĐ", ""));
                
                cthdBUS.add(new ChiTietHoaDonDTO(hd.getMaHD(), maSP, sl, gia, tien)); 
            }

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            resetForm();
            
            
            loadTableLichSuHD(hdBUS.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu Database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        modelGioHang.setRowCount(0);
        tongTien = 0; lblTongTien.setText("0 VNĐ");
        txtTienKhachDua.setText(""); txtTienThua.setText("");
        txtSdtKH.setText(""); txtTenKH.setText("");
        txtMaHD.setText(hdBUS.generateMaHD());
    }
    public void loadTableSanPham(List<SanPhamDTO> list) {
        modelSP.setRowCount(0);
        int stt = 1;
        for (SanPhamDTO sp : list) 
            modelSP.addRow(new Object[]{stt++, sp.getMaSp(), sp.getTenSp(), sp.getSoLuongTon(), df.format(sp.getDonGia())});
    }

    public void loadTableLichSuHD(ArrayList<HoaDonDTO> list) {
        modelLichSuHD.setRowCount(0);
        int stt = 1;
        for (HoaDonDTO hd : list) 
            modelLichSuHD.addRow(new Object[]{stt++, hd.getMaHD(), hd.getMaNV(), hd.getMaKH(), hd.getNgayLapHD().format(dtf), df.format(hd.getTongTien())});
    }

    private void loadTableChiTietHoaDon(String maHD) {
        modelChiTietHD.setRowCount(0);
        ArrayList<ChiTietHoaDonDTO> listCT = cthdBUS.getAllByMaHD(maHD);
        int stt = 1;
        for (ChiTietHoaDonDTO ct : listCT) 
            modelChiTietHD.addRow(new Object[]{stt++, ct.getMaSP(), ct.getSoLuong(), df.format(ct.getDonGia()), df.format(ct.getThanhTien())});
    }
}