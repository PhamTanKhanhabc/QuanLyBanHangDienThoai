package GUI.Dialog;

import BUS.ChiTietPhieuNhapBUS;
import BUS.NhaCungCapBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NhaCungCapDTO;
import DTO.SanPhamDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;

public class XoaPhieuNhap extends JDialog {
    private JTextField txtNhaCungCap;
    private JTextField txtNhanVien;
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    private JButton btnHuy, btnXoa;
    private String maPhieuNhap;
    
    public XoaPhieuNhap(Frame owner, boolean modal, String maPHN, String maNCC, String maNV){
        super(owner, modal);
        this.maPhieuNhap = maPHN; 
        initUI(maPHN, maNV, maNCC);
        loadChiTietPhieuNhap(maPHN);
    }
    
    public void loadChiTietPhieuNhap(String maPHN){
        chiTietModel.setRowCount(0);
        ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
        ArrayList<ChiTietPhieuNhapDTO> list = ctpnBUS.getAllByMaPN(maPHN);
        
        DecimalFormat df = new DecimalFormat("#,###");
        
        if(list != null){
            int stt = 1;
            for (ChiTietPhieuNhapDTO tmp : list){
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
    
    public void initUI(String maPHN, String maNV, String maNCC){
        setTitle("XÁC NHẬN XÓA PHIẾU NHẬP");
        setSize(850, 600);
        setLocationRelativeTo(getParent());
        
        setLayout(new BorderLayout(10, 10)); 
        getContentPane().setBackground(Color.WHITE);
        
        JPanel pnlTop = new JPanel(new BorderLayout()); 
        pnlTop.setBackground(Color.WHITE);
        
        JPanel pnlTopTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTopTitle.setBackground(Color.WHITE);
        
        JLabel lblTopTitle = new JLabel("XÓA PHIẾU NHẬP " + maPHN);
        lblTopTitle.setFont(new Font("Roboto", Font.BOLD, 22));
        lblTopTitle.setForeground(new Color(220, 53, 69)); 
        pnlTopTitle.add(lblTopTitle);
        
        JPanel pnlTopInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlTopInfo.setBackground(Color.WHITE);
        pnlTopInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        pnlTopInfo.add(new JLabel("Nhà cung cấp: "));
        
        String tenNCC = maNCC;
        NhaCungCapBUS nccBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCapDTO> nccList = nccBUS.getAll();
        if (nccList != null) {
            for (NhaCungCapDTO tmp : nccList) {
                if (tmp.getMaNCC().equals(maNCC)) {
                    tenNCC = tmp.getTenNCC();
                    break;
                }
            }
        }

        txtNhaCungCap = new JTextField(tenNCC); 
        txtNhaCungCap.setPreferredSize(new Dimension(250, 32));
        txtNhaCungCap.setEditable(false); 
        txtNhaCungCap.setBackground(new Color(245, 245, 245)); 
        txtNhaCungCap.setFont(new Font("Roboto", Font.BOLD, 14));
        pnlTopInfo.add(txtNhaCungCap);
        
        pnlTopInfo.add(new JLabel("Mã nhân viên: "));
        txtNhanVien = new JTextField(maNV); 
        txtNhanVien.setPreferredSize(new Dimension(150, 32));
        txtNhanVien.setEditable(false); 
        txtNhanVien.setBackground(new Color(245, 245, 245));
        txtNhanVien.setFont(new Font("Roboto", Font.BOLD, 14));
        pnlTopInfo.add(txtNhanVien);
        
        pnlTop.add(pnlTopTitle, BorderLayout.NORTH);
        pnlTop.add(pnlTopInfo, BorderLayout.CENTER);
        
        JPanel pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(0, 20, 20, 20)); 
        
        String[] cols = {
            "STT", "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
        };
        
        chiTietModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(35);
        tblChiTiet.setSelectionBackground(new Color(255, 230, 230)); 
        tblChiTiet.setSelectionForeground(Color.BLACK); 
        tblChiTiet.getTableHeader().setReorderingAllowed(false); 
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblChiTiet.getColumnCount(); i++) {
            tblChiTiet.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        
        JPanel pnlMainAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlMainAction.setBackground(Color.WHITE);
        pnlMainAction.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnXoa = new JButton("Xác nhận Xóa");
        btnXoa.setPreferredSize(new Dimension(150, 40));
        btnXoa.setBackground(new Color(220, 53, 69)); 
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnlMainAction.add(btnHuy);
        pnlMainAction.add(btnXoa);
        
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlMainAction, BorderLayout.SOUTH);
        
        btnHuy.addActionListener(e -> dispose());
        
        // ================= XỬ LÝ NÚT XÁC NHẬN XÓA =================
        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Hành động này không thể hoàn tác. Kho hàng sẽ bị trừ đi số lượng tương ứng.\nBạn chắc chắn muốn xóa phiếu " + maPhieuNhap + "?", 
                "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // 1. TRỪ SỐ LƯỢNG TỒN KHO CỦA SẢN PHẨM
                ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
                SanPhamBUS spBUS = new SanPhamBUS();
                
                ArrayList<ChiTietPhieuNhapDTO> listChiTiet = ctpnBUS.getAllByMaPN(maPhieuNhap);
                
                if (listChiTiet != null) {
                    ArrayList<SanPhamDTO> listSP = spBUS.getAll();
                    for (ChiTietPhieuNhapDTO ct : listChiTiet) {
                        for (SanPhamDTO sp : listSP) {
                            if (sp.getMaSp().equals(ct.getMaSP())) {
                                // Lấy số lượng đang có TRỪ ĐI số lượng đã nhập trong phiếu này
                                int soLuongMoi = sp.getSoLuongTon() - ct.getSoLuong();
                                
                                // Đảm bảo an toàn không để kho bị âm (nếu lỡ xuất bán rồi mới quay lại xóa phiếu nhập)
                                if (soLuongMoi < 0) soLuongMoi = 0; 
                                
                                sp.setSoLuongTon(soLuongMoi);
                                spBUS.update(sp); // Cập nhật xuống CSDL
                                break;
                            }
                        }
                    }
                }
                
                // 2. CHUYỂN TRẠNG THÁI PHIẾU NHẬP (SOFT DELETE)
                DTO.PhieuNhapDTO pnDelete = new DTO.PhieuNhapDTO();
                pnDelete.setMaPHN(maPhieuNhap);
                
                BUS.PhieuNhapBUS pnBUS = new BUS.PhieuNhapBUS();
                boolean isSuccess = pnBUS.delete(pnDelete);
                
                // 3. THÔNG BÁO KẾT QUẢ
                if (isSuccess) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công phiếu nhập và hoàn trả tồn kho!");
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}