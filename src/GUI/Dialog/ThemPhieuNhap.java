package GUI.Dialog;

import BUS.ChiTietPhieuNhapBUS;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;

import java.awt.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ThemPhieuNhap extends JDialog {
    private JComboBox<String> cbxNhaCungCap;
    private JComboBox<String> cbxNhanVien;
    
    private JButton btnHuy, btnLuu, btnThemSP, btnXoaSP;
    private JTextField txtMaSP, txtSoLuong, txtDonGia;
    private JLabel lblTongTien;
    
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    
    private DecimalFormat df = new DecimalFormat("#,###");

    public ThemPhieuNhap(Frame owner, boolean modal){
        super(owner, modal);
        initUI();
    }
    
    public void initUI(){
        setTitle("THÊM PHIẾU NHẬP MỚI");
        setSize(900, 650);
        setLocationRelativeTo(getParent());
        
        setLayout(new BorderLayout(10, 10)); 
        getContentPane().setBackground(Color.WHITE);
        
        // ==========================================
        // 1. TOP PANEL (Thông tin chung)
        // ==========================================
        JPanel pnlTop = new JPanel(new BorderLayout()); 
        pnlTop.setBackground(Color.WHITE);
        
        JPanel pnlTopTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTopTitle.setBackground(Color.WHITE);
        JLabel lblTopTitle = new JLabel("THÊM PHIẾU NHẬP MỚI");
        lblTopTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTopTitle.setForeground(new Color(65, 120, 255));
        pnlTopTitle.add(lblTopTitle);
        
        JPanel pnlTopInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlTopInfo.setBackground(Color.WHITE);
        pnlTopInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        pnlTopInfo.add(new JLabel("Nhà Cung Cấp: "));
        cbxNhaCungCap = new JComboBox<>();
        cbxNhaCungCap.addItem("Chọn nhà cung cấp");
        NhaCungCapBUS nccBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCapDTO> nccList = nccBUS.getAll();
        if (nccList != null){
            for (NhaCungCapDTO tmp : nccList){
                cbxNhaCungCap.addItem(tmp.getTenNCC());
            }
        }
        cbxNhaCungCap.setPreferredSize(new Dimension(200, 32));
        pnlTopInfo.add(cbxNhaCungCap);
        
        pnlTopInfo.add(new JLabel("Mã nhân viên: "));
        cbxNhanVien = new JComboBox<>();
        cbxNhanVien.addItem("Chọn mã nhân viên");
        NhanVienBUS nvBUS = new NhanVienBUS();
        ArrayList<NhanVienDTO> nvList = nvBUS.getAll();
        if(nvList != null){
            for (NhanVienDTO tmp : nvList){
                cbxNhanVien.addItem(tmp.getMaNV());
            }
        }
        cbxNhanVien.setPreferredSize(new Dimension(150, 32));
        pnlTopInfo.add(cbxNhanVien);
        
        pnlTop.add(pnlTopTitle, BorderLayout.NORTH);
        pnlTop.add(pnlTopInfo, BorderLayout.CENTER);
        
        // ==========================================
        // 2. MAIN PANEL (Nhập liệu & Bảng dữ liệu)
        // ==========================================
        JPanel pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(0, 20, 10, 20));
        
        // --- 2.1 Khu vực nhập liệu Sản phẩm mới ---
        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlInput.setBackground(new Color(245, 245, 245));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Nhập thông tin sản phẩm"));

        pnlInput.add(new JLabel("Mã SP:"));
        txtMaSP = new JTextField(10);
        pnlInput.add(txtMaSP);

        pnlInput.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(8);
        pnlInput.add(txtSoLuong);

        pnlInput.add(new JLabel("Đơn giá nhập:"));
        txtDonGia = new JTextField(12);
        pnlInput.add(txtDonGia);

        btnThemSP = new JButton("Thêm vào bảng");
        btnThemSP.setBackground(new Color(40, 167, 69));
        btnThemSP.setForeground(Color.WHITE);
        pnlInput.add(btnThemSP);

        pnlMain.add(pnlInput, BorderLayout.NORTH);
        
        // --- 2.2 Bảng chi tiết ---
        String[] cols = {"STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        chiTietModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // KHÓA SỬA TRỰC TIẾP TRÊN BẢNG
            }
        };
        
        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(35);
        tblChiTiet.setSelectionBackground(new Color(230, 245, 245));
        tblChiTiet.setSelectionForeground(Color.BLACK); 
        tblChiTiet.getTableHeader().setReorderingAllowed(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblChiTiet.getColumnCount(); i++) {
            tblChiTiet.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        pnlMain.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);
        
        // ==========================================
        // 3. BOTTOM PANEL (Action & Tổng tiền)
        // ==========================================
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setBorder(new EmptyBorder(0, 20, 20, 20));

        // Nhãn Tổng tiền bên trái
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Roboto", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);
        pnlBottom.add(lblTongTien, BorderLayout.WEST);
        
        // Cụm nút bên phải
        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlAction.setBackground(Color.WHITE);
        
        btnXoaSP = new JButton("Xóa dòng chọn");
        btnXoaSP.setPreferredSize(new Dimension(130, 40));
        
        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(100, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLuu = new JButton("Lưu thông tin");
        btnLuu.setPreferredSize(new Dimension(140, 40));
        btnLuu.setBackground(new Color(65, 120, 255));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnlAction.add(btnXoaSP);
        pnlAction.add(btnHuy);
        pnlAction.add(btnLuu);
        
        pnlBottom.add(pnlAction, BorderLayout.EAST);
        
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
        
        // ==========================================
        // 4. XỬ LÝ SỰ KIỆN (ACTIONS)
        // ==========================================
        
        btnHuy.addActionListener(e -> dispose());
        
        // Xử lý nút Thêm vào bảng
        btnThemSP.addActionListener(e -> {
            String maSP = txtMaSP.getText().trim();
            String slStr = txtSoLuong.getText().trim();
            String giaStr = txtDonGia.getText().trim();

            if (maSP.isEmpty() || slStr.isEmpty() || giaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã SP, Số lượng và Đơn giá!");
                return;
            }

            try {
                int soLuong = Integer.parseInt(slStr);
                double donGia = Double.parseDouble(giaStr);
                
                if(soLuong <= 0 || donGia < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!");
                    return;
                }

                double thanhTien = soLuong * donGia;

                // Thêm dữ liệu vào bảng
                Object[] row = {
                    chiTietModel.getRowCount() + 1,
                    maSP,
                    soLuong,
                    df.format(donGia),
                    df.format(thanhTien)
                };
                chiTietModel.addRow(row);

                // Dọn dẹp ô nhập và tính lại tổng
                txtMaSP.setText("");
                txtSoLuong.setText("");
                txtDonGia.setText("");
                tinhTongTien();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng và Đơn giá phải là số hợp lệ!");
            }
        });

        // Xử lý nút Xóa dòng đang chọn
        btnXoaSP.addActionListener(e -> {
            int selectedRow = tblChiTiet.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng sản phẩm để xóa!");
                return;
            }
            
            chiTietModel.removeRow(selectedRow);
            
            // Đánh lại số thứ tự
            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                chiTietModel.setValueAt(i + 1, i, 0);
            }
            tinhTongTien();
        });

        // Xử lý nút Lưu xuống Database
        // Xử lý nút Lưu xuống Database
        btnLuu.addActionListener(e -> {
            // 1. Kiểm tra xem có sản phẩm nào trong bảng chưa
            if (chiTietModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Phiếu nhập phải có ít nhất 1 sản phẩm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Lấy thông tin chung từ giao diện
            String maNV = cbxNhanVien.getSelectedItem().toString();
            String tenNCC = cbxNhaCungCap.getSelectedItem().toString();

            if (maNV.equals("Chọn mã nhân viên") || tenNCC.equals("Chọn nhà cung cấp")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ Nhân viên và Nhà cung cấp!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Chuyển Tên NCC thành Mã NCC để lưu vào DB
            String maNCC_DB = "";
            NhaCungCapBUS nccBusTemp = new NhaCungCapBUS();
            for (NhaCungCapDTO ncc : nccBusTemp.getAll()) {
                if (ncc.getTenNCC().equals(tenNCC)) {
                    maNCC_DB = ncc.getMaNCC();
                    break;
                }
            }

            // 4. Tính lại tổng tiền dạng số nguyên thủy (bỏ dấu phẩy)
            double tongTien = 0;
            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                String thanhTienStr = chiTietModel.getValueAt(i, 4).toString().replace(",", "");
                tongTien += Double.parseDouble(thanhTienStr);
            }

            // 5. TẠO PHIẾU NHẬP CHÍNH
            Timestamp ngayLap = new Timestamp(System.currentTimeMillis());
            PhieuNhapDTO pn = new PhieuNhapDTO("", maNV, maNCC_DB, ngayLap, tongTien, 1);
            
            PhieuNhapBUS pnBUS = new PhieuNhapBUS();
            if (!pnBUS.add(pn)) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tạo phiếu nhập chính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Lấy mã phiếu nhập vừa được tự động sinh ra (VD: "PN026")
            String maPNMoi = pn.getMaPHN(); 
            
            // 6. LƯU CHI TIẾT PHIẾU NHẬP VÀ CẬP NHẬT KHO SẢN PHẨM
            ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
            BUS.SanPhamBUS spBUS = new BUS.SanPhamBUS(); // Gọi BUS Sản Phẩm
            boolean checkChiTiet = true;
            
            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                String maSP = chiTietModel.getValueAt(i, 1).toString();
                int soLuong = Integer.parseInt(chiTietModel.getValueAt(i, 2).toString());
                double donGia = Double.parseDouble(chiTietModel.getValueAt(i, 3).toString().replace(",", ""));
                double thanhTien = Double.parseDouble(chiTietModel.getValueAt(i, 4).toString().replace(",", ""));

                ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO(maPNMoi, maSP, soLuong, donGia, thanhTien);
                
                // Thêm chi tiết phiếu nhập
                if (ctpnBUS.add(ct)) { 
                    // TĂNG SỐ LƯỢNG TỒN KHO CỦA SẢN PHẨM
                    ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();
                    if (listSP != null) {
                        for (DTO.SanPhamDTO sp : listSP) {
                            if (sp.getMaSp().equals(maSP)) {
                                // Tính số lượng mới và cập nhật
                                int soLuongMoi = sp.getSoLuongTon() + soLuong;
                                sp.setSoLuongTon(soLuongMoi);
                                spBUS.update(sp);
                                break; 
                            }
                        }
                    }
                } else {
                    checkChiTiet = false;
                }
            }

            // 7. Thông báo kết quả cuối cùng
            if (checkChiTiet) {
                JOptionPane.showMessageDialog(this, "Đã thêm phiếu nhập thành công!\nMã phiếu mới: " + maPNMoi);
                dispose(); // Đóng form Thêm
            } else {
                JOptionPane.showMessageDialog(this, "Phiếu nhập đã tạo nhưng có lỗi khi lưu chi tiết sản phẩm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Hàm tự động cộng dồn cột Thành tiền trên bảng
    private void tinhTongTien() {
        double tong = 0;
        for (int i = 0; i < chiTietModel.getRowCount(); i++) {
            String thanhTienStr = chiTietModel.getValueAt(i, 4).toString().replace(",", "");
            try {
                tong += Double.parseDouble(thanhTienStr);
            } catch (Exception e) {}
        }
        lblTongTien.setText("Tổng tiền: " + df.format(tong) + " VNĐ");
    }
}