package GUI.Dialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import java.util.ArrayList;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;
import BUS.ChiTietPhieuNhapBUS;
import DTO.ChiTietPhieuNhapDTO;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;

public class SuaPhieuNhap extends JDialog {
    private JComboBox<String> cbxNhaCungCap;
    private JComboBox<String> cbxNhanVien;
    private JButton btnHuy, btnLuu, btnThemSP, btnXoaSP;
    
    // Các ô nhập liệu sản phẩm mới
    private JTextField txtMaSP, txtSoLuong, txtDonGia;
    private JLabel lblTongTien;
    
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    private String maPhieuNhap;
    
    private DecimalFormat df = new DecimalFormat("#,###");

    public SuaPhieuNhap(Frame owner, boolean modal, String maPHN, String maNCC, String maNV) {
        super(owner, modal);
        this.maPhieuNhap = maPHN;
        initUI(maNCC, maNV);
        loadChiTietPhieuNhap(maPHN);
        tinhTongTien(); // Tính tổng tiền lần đầu khi load
    }

    public void initUI(String maNCC, String maNV) {
        setTitle("SỬA PHIẾU NHẬP");
        setSize(900, 650);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // ================= TOP PANEL (Thông tin chung) =================
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(Color.WHITE);

        JPanel pnlTopTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTopTitle.setBackground(Color.WHITE);
        JLabel lblTopTitle = new JLabel("CẬP NHẬT PHIẾU NHẬP " + maPhieuNhap);
        lblTopTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTopTitle.setForeground(new Color(65, 120, 255));
        pnlTopTitle.add(lblTopTitle);

        JPanel pnlTopInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlTopInfo.setBackground(Color.WHITE);

        pnlTopInfo.add(new JLabel("Nhà Cung Cấp: "));
        cbxNhaCungCap = new JComboBox<>();
        NhaCungCapBUS nccBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCapDTO> nccList = nccBUS.getAll();
        String tenNCC_CanChon = "";
        if (nccList != null) {
            for (NhaCungCapDTO tmp : nccList) {
                cbxNhaCungCap.addItem(tmp.getTenNCC());
                if (tmp.getMaNCC().equals(maNCC)) {
                    tenNCC_CanChon = tmp.getTenNCC();
                }
            }
        }
        if (!tenNCC_CanChon.isEmpty()) cbxNhaCungCap.setSelectedItem(tenNCC_CanChon);
        cbxNhaCungCap.setPreferredSize(new Dimension(200, 32));
        pnlTopInfo.add(cbxNhaCungCap);

        pnlTopInfo.add(new JLabel("Mã nhân viên: "));
        cbxNhanVien = new JComboBox<>();
        NhanVienBUS nvBUS = new NhanVienBUS();
        ArrayList<NhanVienDTO> nvList = nvBUS.getAll();
        if (nvList != null) {
            for (NhanVienDTO tmp : nvList) {
                cbxNhanVien.addItem(tmp.getMaNV());
            }
        }
        cbxNhanVien.setSelectedItem(maNV);
        cbxNhanVien.setPreferredSize(new Dimension(150, 32));
        pnlTopInfo.add(cbxNhanVien);

        pnlTop.add(pnlTopTitle, BorderLayout.NORTH);
        pnlTop.add(pnlTopInfo, BorderLayout.CENTER);

        // ================= MAIN PANEL (Nhập liệu + Bảng) =================
        JPanel pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(10, 20, 10, 20));

        // --- Khu vực nhập liệu Sản phẩm mới ---
        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlInput.setBackground(new Color(245, 245, 245));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Nhập thông tin sản phẩm cần thêm"));

        pnlInput.add(new JLabel("Mã SP:"));
        txtMaSP = new JTextField(12);
        pnlInput.add(txtMaSP);

        pnlInput.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(8);
        pnlInput.add(txtSoLuong);

        pnlInput.add(new JLabel("Đơn giá:"));
        txtDonGia = new JTextField(12);
        pnlInput.add(txtDonGia);

        btnThemSP = new JButton("Thêm vào bảng");
        btnThemSP.setBackground(new Color(40, 167, 69));
        btnThemSP.setForeground(Color.WHITE);
        pnlInput.add(btnThemSP);

        pnlMain.add(pnlInput, BorderLayout.NORTH);

        // --- Bảng chi tiết (Khóa Read-only) ---
        String[] cols = {"STT", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        chiTietModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // KHÓA TOÀN BỘ BẢNG
            }
        };

        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(35);
        tblChiTiet.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblChiTiet.getColumnCount(); i++) {
            tblChiTiet.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        pnlMain.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);

        // ================= BOTTOM PANEL (Tổng tiền + Nút chức năng) =================
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setBorder(new EmptyBorder(0, 20, 20, 20));

        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Roboto", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);
        pnlBottom.add(lblTongTien, BorderLayout.WEST);

        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlAction.setBackground(Color.WHITE);

        btnXoaSP = new JButton("Xóa dòng chọn");
        btnXoaSP.setPreferredSize(new Dimension(130, 40));
        
        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(100, 40));

        btnLuu = new JButton("Lưu thay đổi");
        btnLuu.setPreferredSize(new Dimension(130, 40));
        btnLuu.setBackground(new Color(65, 120, 255));
        btnLuu.setForeground(Color.WHITE);

        pnlAction.add(btnXoaSP);
        pnlAction.add(btnHuy);
        pnlAction.add(btnLuu);
        
        pnlBottom.add(pnlAction, BorderLayout.EAST);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        // ================= XỬ LÝ SỰ KIỆN =================
        btnHuy.addActionListener(e -> dispose());

        btnThemSP.addActionListener(e -> {
            String maSP_Input = txtMaSP.getText().trim();
            String slStr = txtSoLuong.getText().trim();
            String giaStr = txtDonGia.getText().trim();

            if (maSP_Input.isEmpty() || slStr.isEmpty() || giaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã SP, Số lượng và Đơn giá!");
                return;
            }

            try {
                int soLuong = Integer.parseInt(slStr);
                double donGia = Double.parseDouble(giaStr);
                
                if(soLuong <= 0 || donGia < 0){
                    JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!");
                    return;
                }
                
                double thanhTien = soLuong * donGia;

                Object[] row = {
                    chiTietModel.getRowCount() + 1,
                    maSP_Input,
                    soLuong,
                    df.format(donGia),
                    df.format(thanhTien)
                };
                chiTietModel.addRow(row);

                txtMaSP.setText("");
                txtSoLuong.setText("");
                txtDonGia.setText("");
                tinhTongTien();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng và Đơn giá phải là số hợp lệ!");
            }
        });

        btnXoaSP.addActionListener(e -> {
            int selectedRow = tblChiTiet.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
                return;
            }
            chiTietModel.removeRow(selectedRow);
            
            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                chiTietModel.setValueAt(i + 1, i, 0);
            }
            tinhTongTien(); 
        });

        // ==================== LOGIC LƯU SỬA ĐỔI ====================
        btnLuu.addActionListener(e -> {
            if (chiTietModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Phiếu nhập phải có ít nhất 1 sản phẩm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNV_Update = cbxNhanVien.getSelectedItem().toString();
            String tenNCC = cbxNhaCungCap.getSelectedItem().toString();

            if (maNV_Update.equals("Chọn mã nhân viên") || tenNCC.equals("Chọn nhà cung cấp")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ Nhân viên và Nhà cung cấp!");
                return;
            }

            String maNCC_Update = "";
            NhaCungCapBUS nccBUS_tmp = new NhaCungCapBUS();
            for (NhaCungCapDTO ncc : nccBUS_tmp.getAll()) {
                if (ncc.getTenNCC().equals(tenNCC)) {
                    maNCC_Update = ncc.getMaNCC();
                    break;
                }
            }

            double tongTien = 0;
            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                String thanhTienStr = chiTietModel.getValueAt(i, 4).toString().replace(",", "");
                tongTien += Double.parseDouble(thanhTienStr);
            }

            // 1. CẬP NHẬT PHIẾU NHẬP CHÍNH
            BUS.PhieuNhapBUS pnBUS = new BUS.PhieuNhapBUS();
            DTO.PhieuNhapDTO pnOld = null;
            
            for (DTO.PhieuNhapDTO p : pnBUS.getAll()) {
                if (p.getMaPHN().equals(maPhieuNhap)) {
                    pnOld = p; 
                    break;
                }
            }

            if (pnOld != null) {
                pnOld.setMaNV(maNV_Update);
                pnOld.setMaNCC(maNCC_Update);
                pnOld.setTongTien(tongTien);
                
                if (!pnBUS.update(pnOld)) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin phiếu nhập chính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // ==============================================================
            // 2. LOGIC ĐỒNG BỘ TỒN KHO & CHI TIẾT
            // ==============================================================
            ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
            SanPhamBUS spBUS = new SanPhamBUS();
            
            // BƯỚC A: HOÀN TRẢ TỒN KHO CŨ
            ArrayList<ChiTietPhieuNhapDTO> listOldDetails = ctpnBUS.getAllByMaPN(maPhieuNhap);
            if (listOldDetails != null) {
                ArrayList<SanPhamDTO> listSP = spBUS.getAll();
                for (ChiTietPhieuNhapDTO ctOld : listOldDetails) {
                    for (SanPhamDTO sp : listSP) {
                        if (sp.getMaSp().equals(ctOld.getMaSP())) {
                            int slMoi = sp.getSoLuongTon() - ctOld.getSoLuong();
                            if (slMoi < 0) slMoi = 0; // Chống âm kho
                            sp.setSoLuongTon(slMoi);
                            spBUS.update(sp);
                            break;
                        }
                    }
                }
            }

            // BƯỚC B: XÓA SẠCH CHI TIẾT CŨ
            ctpnBUS.deleteAllByMaPHN(maPhieuNhap);

            // BƯỚC C: THÊM CHI TIẾT MỚI & CỘNG VÀO TỒN KHO
            boolean checkChiTiet = true;
            ArrayList<SanPhamDTO> listSP_Moi = spBUS.getAll(); // Lấy lại list mới nhất cho chắc
            
            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                String maSP_Item = chiTietModel.getValueAt(i, 1).toString();
                int soLuong = Integer.parseInt(chiTietModel.getValueAt(i, 2).toString());
                double donGia = Double.parseDouble(chiTietModel.getValueAt(i, 3).toString().replace(",", ""));
                double thanhTien = Double.parseDouble(chiTietModel.getValueAt(i, 4).toString().replace(",", ""));

                ChiTietPhieuNhapDTO ctNew = new ChiTietPhieuNhapDTO(maPhieuNhap, maSP_Item, soLuong, donGia, thanhTien);
                
                if (ctpnBUS.add(ctNew)) { 
                    // Cộng số lượng vào kho
                    for (SanPhamDTO sp : listSP_Moi) {
                        if (sp.getMaSp().equals(maSP_Item)) {
                            int slThemMoi = sp.getSoLuongTon() + soLuong;
                            sp.setSoLuongTon(slThemMoi);
                            spBUS.update(sp);
                            break;
                        }
                    }
                } else {
                    checkChiTiet = false;
                }
            }

            if (checkChiTiet) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật phiếu nhập và đồng bộ kho hàng thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công phiếu nhập chính, nhưng có lỗi ở chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void loadChiTietPhieuNhap(String maPHN) {
        chiTietModel.setRowCount(0);
        ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
        ArrayList<ChiTietPhieuNhapDTO> list = ctpnBUS.getAllByMaPN(maPHN);

        if (list != null) {
            int stt = 1;
            for (ChiTietPhieuNhapDTO tmp : list) {
                Object[] row = {
                    stt++,
                    tmp.getMaSP(),
                    tmp.getSoLuong(),
                    df.format(tmp.getDonGia()),
                    df.format(tmp.getSoLuong() * tmp.getDonGia())
                };
                chiTietModel.addRow(row);
            }
        }
    }

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