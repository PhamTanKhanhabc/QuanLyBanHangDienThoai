package GUI.Dialog;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class XemPhieuNhap extends JDialog {

    private JTextField txtNhaCungCap;
    private JTextField txtNhanVien;
    
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    private JButton btnDong;
    

    public XemPhieuNhap(Frame owner, boolean modal, String tenNCC, String maNV){
        super(owner, modal);
        initUI(tenNCC, maNV);
    }
    
    public void initUI(String tenNCC, String maNV){
        setTitle("CHI TIẾT PHIẾU NHẬP");
        setSize(850, 600);
        setLocationRelativeTo(getParent());
        
        setLayout(new BorderLayout(10, 10)); 
        getContentPane().setBackground(Color.WHITE);
        
        // ==========================================
        // 1. TOP PANEL
        // ==========================================
        JPanel pnlTop = new JPanel(new BorderLayout()); 
        pnlTop.setBackground(Color.WHITE);
        
        JPanel pnlTopTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTopTitle.setBackground(Color.WHITE);
        
        JLabel lblTopTitle = new JLabel("CHI TIẾT PHIẾU NHẬP");
        lblTopTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTopTitle.setForeground(new Color(65, 120, 255));
        pnlTopTitle.add(lblTopTitle);
        
        JPanel pnlTopInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlTopInfo.setBackground(Color.WHITE);
        pnlTopInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // --- Ô hiển thị Nhà Cung Cấp ---
        pnlTopInfo.add(new JLabel("Nhà Cung Cấp: "));
        txtNhaCungCap = new JTextField(tenNCC); // Điền sẵn tên NCC được truyền vào
        txtNhaCungCap.setPreferredSize(new Dimension(250, 32));
        txtNhaCungCap.setEditable(false); // KHÓA TƯƠNG TÁC
        txtNhaCungCap.setBackground(new Color(245, 245, 245)); // Tô nền xám nhạt để phân biệt với form Thêm
        txtNhaCungCap.setFont(new Font("Roboto", Font.BOLD, 14));
        pnlTopInfo.add(txtNhaCungCap);
        
        // --- Ô hiển thị Nhân Viên ---
        pnlTopInfo.add(new JLabel("Mã nhân viên: "));
        txtNhanVien = new JTextField(maNV); // Điền sẵn mã NV được truyền vào
        txtNhanVien.setPreferredSize(new Dimension(150, 32));
        txtNhanVien.setEditable(false); // KHÓA TƯƠNG TÁC
        txtNhanVien.setBackground(new Color(245, 245, 245));
        txtNhanVien.setFont(new Font("Roboto", Font.BOLD, 14));
        pnlTopInfo.add(txtNhanVien);
        
        pnlTop.add(pnlTopTitle, BorderLayout.NORTH);
        pnlTop.add(pnlTopInfo, BorderLayout.CENTER);
        
        // ==========================================
        // 2. MAIN PANEL (BẢNG DỮ LIỆU)
        // ==========================================
        JPanel pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(0, 20, 20, 20)); 
        
        String[] cols = {
            "STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
        };
        
        chiTietModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(35);
        tblChiTiet.setSelectionBackground(new Color(230, 245, 245));
        tblChiTiet.getTableHeader().setReorderingAllowed(false); // Khóa di chuyển cột
        
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        
        // ==========================================
        // 3. BOTTOM PANEL (CHỈ CHỨA NÚT ĐÓNG)
        // ==========================================
        JPanel pnlMainAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlMainAction.setBackground(Color.WHITE);
        pnlMainAction.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(120, 40));
        btnDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        

        btnDong.addActionListener(e -> dispose());
        
        pnlMainAction.add(btnDong);
        
        // ==========================================
        // 4. THÊM VÀO DIALOG
        // ==========================================
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlMainAction, BorderLayout.SOUTH);
    }
}