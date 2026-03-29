package GUI.Dialog;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import GUI.Component.TablePanel;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CreateHoaDonDialog extends JDialog {
    private SanPhamBUS spBUS = new SanPhamBUS();
    private HoaDonBUS hdBUS = new HoaDonBUS();
    private KhachHangBUS khBUS = new KhachHangBUS();
    private ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();

    private JTable tableSP, tableGioHang;
    private DefaultTableModel modelSP, modelGioHang;
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtSoLuongMua;
    private JTextField txtSdtKH, txtTenKH;
    private JLabel lblTongTien;
    private JButton btnThemSP, btnTaoHoaDon, btnHuy, btnXoaDong;
    
    private double tongTien = 0;
    private DecimalFormat df = new DecimalFormat("#,### VNĐ");

    public CreateHoaDonDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("Thêm Hóa Đơn Mới");
        setSize(1100, 700);
        setLocationRelativeTo(parent);
        initComponents();
        
        // Tải danh sách sản phẩm từ DB lên bảng
        loadTableSanPham(spBUS.getAll());
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // ======================= TRÁI: DANH SÁCH SẢN PHẨM =======================
        JPanel pnlLeft = new JPanel(new BorderLayout(0, 10));
        pnlLeft.setBackground(Color.WHITE);

        // Form chọn sản phẩm
        JPanel pnlChonSP = new JPanel(new GridBagLayout());
        pnlChonSP.setBackground(Color.WHITE);
        pnlChonSP.setBorder(BorderFactory.createTitledBorder("THÔNG TIN SẢN PHẨM"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaSP = new JTextField(8); txtMaSP.setEditable(false);
        txtTenSP = new JTextField(15); txtTenSP.setEditable(false);
        txtDonGia = new JTextField(10); txtDonGia.setEditable(false);
        txtSoLuongMua = new JTextField("1", 5);
        
        btnThemSP = new JButton("Thêm vào giỏ");
        btnThemSP.setBackground(new Color(0, 102, 204)); 
        btnThemSP.setForeground(Color.WHITE);
        btnThemSP.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0; pnlChonSP.add(new JLabel("Mã SP:"), gbc);
        gbc.gridx = 1; pnlChonSP.add(txtMaSP, gbc);
        gbc.gridx = 2; pnlChonSP.add(new JLabel("Tên SP:"), gbc);
        gbc.gridx = 3; pnlChonSP.add(txtTenSP, gbc);
        gbc.gridx = 4; pnlChonSP.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 5; pnlChonSP.add(txtDonGia, gbc);
        gbc.gridy = 1; gbc.gridx = 0; pnlChonSP.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1; pnlChonSP.add(txtSoLuongMua, gbc);
        gbc.gridx = 5; pnlChonSP.add(btnThemSP, gbc);

        // Bảng Sản phẩm
        TablePanel tpSP = new TablePanel("Kho Sản Phẩm", new String[]{"Mã SP", "Tên SP", "Tồn kho", "Đơn giá"});
        tableSP = tpSP.getTable();
        modelSP = (DefaultTableModel) tableSP.getModel();

        pnlLeft.add(pnlChonSP, BorderLayout.NORTH);
        pnlLeft.add(tpSP, BorderLayout.CENTER);

        // ======================= PHẢI: GIỎ HÀNG & THÔNG TIN HÓA ĐƠN =======================
        JPanel pnlRight = new JPanel(new BorderLayout(0, 10));
        pnlRight.setPreferredSize(new Dimension(450, 0));
        pnlRight.setBackground(Color.WHITE);

        // Bảng Giỏ hàng
        TablePanel tpGioHang = new TablePanel("Danh sách mua", new String[]{"Mã SP", "Tên SP", "SL", "Đơn giá", "Thành tiền"});
        tableGioHang = tpGioHang.getTable();
        modelGioHang = (DefaultTableModel) tableGioHang.getModel();
        
        JPanel pnlXoaGioHang = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlXoaGioHang.setBackground(Color.WHITE);
        btnXoaDong = new JButton("Xóa SP chọn");
        btnXoaDong.setBackground(new Color(255, 102, 102));
        btnXoaDong.setForeground(Color.WHITE);
        btnXoaDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlXoaGioHang.add(btnXoaDong);

        JPanel pnlCartWrapper = new JPanel(new BorderLayout());
        pnlCartWrapper.add(tpGioHang, BorderLayout.CENTER);
        pnlCartWrapper.add(pnlXoaGioHang, BorderLayout.SOUTH);

        // Form Thông tin Hóa đơn
        JPanel pnlThongTin = new JPanel(new GridLayout(0, 2, 10, 15));
        pnlThongTin.setBorder(BorderFactory.createTitledBorder("THÔNG TIN HÓA ĐƠN"));
        pnlThongTin.setBackground(Color.WHITE);

        txtSdtKH = new JTextField();
        txtTenKH = new JTextField();
        
        lblTongTien = new JLabel("0 VNĐ");
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);

        pnlThongTin.add(new JLabel("SĐT Khách hàng:")); pnlThongTin.add(txtSdtKH);
        pnlThongTin.add(new JLabel("Tên Khách hàng:")); pnlThongTin.add(txtTenKH);
        pnlThongTin.add(new JLabel("TỔNG TIỀN:")); pnlThongTin.add(lblTongTien);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.setBackground(Color.WHITE);
        
        btnHuy = new JButton("Hủy Bỏ");
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnTaoHoaDon = new JButton("TẠO HÓA ĐƠN");
        btnTaoHoaDon.setBackground(new Color(0, 102, 204)); 
        btnTaoHoaDon.setForeground(Color.WHITE);
        btnTaoHoaDon.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTaoHoaDon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnlButtons.add(btnHuy);
        pnlButtons.add(btnTaoHoaDon);

        JPanel pnlBottomRight = new JPanel(new BorderLayout());
        pnlBottomRight.add(pnlThongTin, BorderLayout.CENTER);
        pnlBottomRight.add(pnlButtons, BorderLayout.SOUTH);

        pnlRight.add(pnlCartWrapper, BorderLayout.CENTER);
        pnlRight.add(pnlBottomRight, BorderLayout.SOUTH);

        add(pnlLeft, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.EAST);

        addEvents();
    }

    private void addEvents() {
        tableSP.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableSP.getSelectedRow();
                if (row != -1) {
                    txtMaSP.setText(modelSP.getValueAt(row, 0).toString());
                    txtTenSP.setText(modelSP.getValueAt(row, 1).toString());
                    txtDonGia.setText(modelSP.getValueAt(row, 3).toString());
                    txtSoLuongMua.setText("1");
                    txtSoLuongMua.requestFocus();
                }
            }
        });

        btnThemSP.addActionListener(e -> addToCart());

        btnXoaDong.addActionListener(e -> {
            int row = tableGioHang.getSelectedRow();
            if (row != -1) {
                String thanhTienStr = modelGioHang.getValueAt(row, 4).toString()
                                            .replace("VNĐ", "")
                                            .replace(",", "")
                                            .trim();
                tongTien -= Double.parseDouble(thanhTienStr);
                lblTongTien.setText(df.format(tongTien));
                modelGioHang.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong danh sách để xóa!");
            }
        });

        btnTaoHoaDon.addActionListener(e -> taoHoaDon());
        btnHuy.addActionListener(e -> dispose());
    }

    private void addToCart() {
        try {
            if (txtMaSP.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!"); return;
            }
            String ma = txtMaSP.getText();
            String ten = txtTenSP.getText();
            int sl = Integer.parseInt(txtSoLuongMua.getText());
            
            String donGiaStr = txtDonGia.getText().replace("VNĐ", "").replace(",", "").trim();
            double gia = Double.parseDouble(donGiaStr);
            
            // ---> THAY ĐỔI Ở ĐÂY: Sử dụng getIndexById và getByIndex
            int indexSP = spBUS.getIndexById(ma);
            if(indexSP == -1) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy sản phẩm trong hệ thống!");
                return;
            }
            
            SanPhamDTO spCheck = spBUS.getByIndex(indexSP);
            
            if(spCheck != null && sl > spCheck.getSoLuongTon()) {
                JOptionPane.showMessageDialog(this, "Số lượng thêm vượt quá tồn kho hiện tại (" + spCheck.getSoLuongTon() + ")!");
                return;
            }

            boolean isExist = false;
            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                if (modelGioHang.getValueAt(i, 0).toString().equals(ma)) {
                    int oldSl = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                    if((oldSl + sl) > spCheck.getSoLuongTon()) {
                        JOptionPane.showMessageDialog(this, "Tổng số lượng vượt quá tồn kho!");
                        return;
                    }
                    modelGioHang.setValueAt(oldSl + sl, i, 2);
                    double newThanhTien = (oldSl + sl) * gia;
                    modelGioHang.setValueAt(df.format(newThanhTien), i, 4);
                    isExist = true;
                    tongTien += (sl * gia);
                    break;
                }
            }

            if (!isExist) {
                double thanhTien = sl * gia;
                modelGioHang.addRow(new Object[]{ma, ten, sl, df.format(gia), df.format(thanhTien)});
                tongTien += thanhTien;
            }
            
            lblTongTien.setText(df.format(tongTien));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ!");
        }
    }

    private void taoHoaDon() {
        if (modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Danh sách sản phẩm đang trống!"); return;
        }

        String tenKhach = txtTenKH.getText().trim();
        String soDT = txtSdtKH.getText().trim();
        
        // Kiểm tra xem nhân viên đã nhập đủ thông tin chưa
        if (tenKhach.isEmpty() || soDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập SĐT và Tên khách hàng!"); return;
        }

        // ---> THAY ĐỔI Ở ĐÂY: Gọi hàm tự động lấy hoặc tạo mới Khách Hàng
        String maKHThatSu = layHoacThemKhachHang(tenKhach, soDT);
        
        if (maKHThatSu == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không thể tự động tạo Khách hàng mới trong hệ thống!"); return;
        }

        // Tạo Hóa Đơn DTO
        HoaDonDTO hd = new HoaDonDTO("", "NV01", maKHThatSu, LocalDateTime.now(), tongTien, 1);

        if (hdBUS.add(hd)) {
            String maHDChinhThuc = hd.getMaHD();

            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                String maSP = modelGioHang.getValueAt(i, 0).toString();
                int slMua = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                
                double gia = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("VNĐ", "").replace(",", "").trim());
                double tien = Double.parseDouble(modelGioHang.getValueAt(i, 4).toString().replace("VNĐ", "").replace(",", "").trim());
                
                // 1. Lưu CTHD
                cthdBUS.add(new ChiTietHoaDonDTO(maHDChinhThuc, maSP, slMua, gia, tien)); 
                
                // 2. Trừ tồn kho bằng getIndexById
                int indexSP = spBUS.getIndexById(maSP);
                if(indexSP != -1) {
                    SanPhamDTO sp = spBUS.getByIndex(indexSP);
                    sp.setSoLuongTon(sp.getSoLuongTon() - slMua);
                    spBUS.update(sp); 
                }
            }

            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!\nMã hóa đơn: " + maHDChinhThuc);
            this.dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu Hóa Đơn vào CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTableSanPham(ArrayList<SanPhamDTO> list) {
        modelSP.setRowCount(0);
        if(list == null) return;
        for (SanPhamDTO sp : list) {
            modelSP.addRow(new Object[]{
                sp.getMaSp(), 
                sp.getTenSp(), 
                sp.getSoLuongTon(), 
                df.format(sp.getDonGia())
            });
        }
    }
   // Hàm hỗ trợ tự động tìm hoặc tạo khách hàng mới
    // Hàm hỗ trợ tự động tìm hoặc tạo khách hàng mới
    private String layHoacThemKhachHang(String tenDayDu, String soDT) {
        String maKH = khBUS.getMaByInfo(soDT);
        if (maKH != null) {
            return maKH; 
        }

        // --- ĐOẠN ĐÃ ĐƯỢC CẬP NHẬT LẠI LOGIC TÁCH TÊN ---
        String ho = "";
        String ten = tenDayDu;
        int index = tenDayDu.lastIndexOf(" ");
        
        if (index > 0) {
            // Nếu có dấu cách (Ví dụ: "Vũ Khôi") -> Tách bình thường
            ho = tenDayDu.substring(0, index).trim();
            ten = tenDayDu.substring(index + 1).trim();
        } else {
            // Nếu không có dấu cách (Ví dụ chỉ nhập "Khôi") -> Gán Họ mặc định để không bị lỗi
            ho = "Khách"; 
            ten = tenDayDu;
        }
        // ------------------------------------------------

        DTO.KhachHangDTO khMoi = new DTO.KhachHangDTO();
        
        khMoi.setMaKH(khBUS.generateMaKH()); 
        khMoi.setHo(ho);
        khMoi.setTen(ten);
        khMoi.setDiaChi("Chưa cập nhật"); 
        khMoi.setSoDT(soDT);
        khMoi.setTrangThai(1);
        
        String ketQuaThem = khBUS.add(khMoi);
        
        if (ketQuaThem != null && ketQuaThem.toLowerCase().contains("thành công")) {
            return khMoi.getMaKH(); 
        }
        
        JOptionPane.showMessageDialog(this, "Lỗi từ hệ thống khi tạo Khách Hàng:\n" + ketQuaThem, "Lỗi", JOptionPane.ERROR_MESSAGE);
        return null; 
    }
}