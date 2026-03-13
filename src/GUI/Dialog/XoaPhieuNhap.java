package GUI.Dialog;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class XoaPhieuNhap extends JDialog {
    private JTextField txtNhaCungCap;
    private JTextField txtNhanVien;
    
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    private JButton btnHuy, btnXoa;
    
    private String maPhieuNhap;
    
    public XoaPhieuNhap(Frame owner, boolean modal, String maPHN, String tenNCC, String maNV){
        super(owner, modal);
        this.maPhieuNhap = maPHN; // Lưu lại ID
        initUI(maPHN, maNV);
    }
    
    public void initUI(String maPHN, String maNV){
        setTitle("XÁC NHẬN XÓA PHIẾU NHẬP");
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
        
        // Cảnh báo chữ đỏ cho thao tác Xóa
        JLabel lblTopTitle = new JLabel("XÓA PHIẾU NHẬP");
        lblTopTitle.setFont(new Font("Roboto", Font.BOLD, 22));
        lblTopTitle.setForeground(new Color(220, 53, 69)); // Màu đỏ Danger
        pnlTopTitle.add(lblTopTitle);
        
        JPanel pnlTopInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlTopInfo.setBackground(Color.WHITE);
        pnlTopInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        pnlTopInfo.add(new JLabel("Mã phiếu nhập "));
        txtNhaCungCap = new JTextField(maPHN); 
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
        tblChiTiet.setSelectionBackground(new Color(255, 230, 230)); // Đổi màu bôi đen thành đỏ nhạt cho hợp tone
        tblChiTiet.getTableHeader().setReorderingAllowed(false); 
        
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        
        // ==========================================
        // 3. BOTTOM PANEL (HỦY & XÓA)
        // ==========================================
        JPanel pnlMainAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlMainAction.setBackground(Color.WHITE);
        pnlMainAction.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnXoa = new JButton("Xác nhận Xóa");
        btnXoa.setPreferredSize(new Dimension(150, 40));
        btnXoa.setBackground(new Color(220, 53, 69)); // Màu đỏ
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnlMainAction.add(btnHuy);
        pnlMainAction.add(btnXoa);
        
        // ==========================================
        // 4. THÊM VÀO DIALOG VÀ BẮT SỰ KIỆN
        // ==========================================
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlMainAction, BorderLayout.SOUTH);
        
        // Sự kiện Đóng form
        btnHuy.addActionListener(e -> dispose());
        
        // Sự kiện Xác nhận Xóa
        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Hành động này không thể hoàn tác. Bạn chắc chắn muốn xóa phiếu " + maPhieuNhap + "?", 
                "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // TODO: Gọi hàm bus.delete(maPhieuNhap) ở đây
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                dispose(); // Xóa xong thì đóng form
            }
        });
    }
}