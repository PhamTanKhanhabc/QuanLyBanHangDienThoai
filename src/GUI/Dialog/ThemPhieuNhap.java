package GUI.Dialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import java.util.ArrayList;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;

public class ThemPhieuNhap extends JDialog {
    private JComboBox<String> cbxNhaCungCap;
    private JComboBox<String> cbxNhanVien;
    
    private JButton btnHuy, btnLuu, btnThemSP;
    
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    
    public ThemPhieuNhap(Frame owner, boolean modal){
        super(owner, modal);
        initUI();
    }
    
    public void initUI(){
        setSize(850, 600);
        setLocationRelativeTo(getParent());
        
        setLayout(new BorderLayout(10, 10)); 
        getContentPane().setBackground(Color.WHITE);
        
        // ==========================================
        // 1. TOP PANEL
        // ==========================================
        JPanel pnlTop = new JPanel(new BorderLayout()); 
        pnlTop.setBackground(Color.WHITE);
        
        // 1.1 Top Title
        JPanel pnlTopTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTopTitle.setBackground(Color.WHITE);
        
        JLabel lblTopTitle = new JLabel("THÊM PHIẾU NHẬP");
        lblTopTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTopTitle.setForeground(new Color(65, 120, 255));
        pnlTopTitle.add(lblTopTitle);
        
        // 1.2 Top Info
        JPanel pnlTopInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlTopInfo.setBackground(Color.WHITE);
        pnlTopInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // --- ComboBox Nhà Cung Cấp ---
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
        
        // --- ComboBox Nhân Viên ---
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
        
        // Gắn Title và Info vào pnlTop
        pnlTop.add(pnlTopTitle, BorderLayout.NORTH);
        pnlTop.add(pnlTopInfo, BorderLayout.CENTER);
        
        // ==========================================
        // 2. MAIN PANEL (BẢNG DỮ LIỆU)
        // ==========================================
        JPanel pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(0, 20, 20, 20)); // Tạo lề cho đẹp
        
        String[] cols = {
            "STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
        };
        
        chiTietModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 1 || col == 2 || col == 3;
            }
        };
        
        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(35);
        tblChiTiet.setSelectionBackground(new Color(230, 245, 245));
        
        tblChiTiet.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        
        //Bottom Main Panel (Action)
        JPanel pnlMainAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,10));
        pnlMainAction.setBackground(Color.WHITE);
        pnlMainAction.setBorder(new EmptyBorder(0, 0, 20, 20));
        
        btnThemSP = new JButton("Thêm sản phẩm");
        btnThemSP.setPreferredSize(new Dimension(150,40));
        btnThemSP.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLuu = new JButton("Lưu thông tin");
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setBackground(new Color(65, 120, 255)); // Màu xanh đồng bộ
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnlMainAction.add(btnHuy);
        pnlMainAction.add(btnThemSP);
        pnlMainAction.add(btnLuu);
        // ==========================================
        // 3. THÊM VÀO DIALOG
        // ==========================================
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlMainAction, BorderLayout.SOUTH);
    }
}