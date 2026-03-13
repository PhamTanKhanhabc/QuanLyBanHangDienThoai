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

public class SuaPhieuNhap extends JDialog {
    private JComboBox<String> cbxNhaCungCap;
    private JComboBox<String> cbxNhanVien;
    
    private JButton btnHuy, btnLuu, btnThemSP;
    
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    
    private String maPhieuNhap; // Lưu mã phiếu nhập đang cần sửa
    
    public SuaPhieuNhap(Frame owner, boolean modal, String maPHN){
        super(owner, modal);
        this.maPhieuNhap = maPHN;
        initUI();
        loadDataLenForm(); // Gọi hàm đổ dữ liệu cũ vào giao diện
    }
    
    public void initUI(){
        setTitle("SỬA PHIẾU NHẬP");
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
        
        JLabel lblTopTitle = new JLabel("CẬP NHẬT PHIẾU NHẬP " + maPhieuNhap);
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
                return col == 1 || col == 2 || col == 3; // Vẫn cho phép sửa Sản phẩm, Số lượng, Đơn giá
            }
        };
        
        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(35);
        tblChiTiet.setSelectionBackground(new Color(230, 245, 245));
        tblChiTiet.getTableHeader().setReorderingAllowed(false); // Khóa kéo thả cột
        
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        
        // ==========================================
        // 3. BOTTOM PANEL (Action Buttons)
        // ==========================================
        JPanel pnlMainAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,10));
        pnlMainAction.setBackground(Color.WHITE);
        pnlMainAction.setBorder(new EmptyBorder(0, 0, 20, 20));
        
        btnThemSP = new JButton("Thêm sản phẩm");
        btnThemSP.setPreferredSize(new Dimension(150,40));
        btnThemSP.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Nút Lưu thay bằng nút Cập nhật cho đúng ngữ cảnh
        btnLuu = new JButton("Cập nhật thay đổi");
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setBackground(new Color(65, 120, 255));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnlMainAction.add(btnHuy);
        pnlMainAction.add(btnThemSP);
        pnlMainAction.add(btnLuu);
        
        // ==========================================
        // 4. THÊM VÀO DIALOG VÀ BẮT SỰ KIỆN
        // ==========================================
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlMainAction, BorderLayout.SOUTH);
        
        // Thoát khi bấm Hủy
        btnHuy.addActionListener(e -> dispose());
        
        // Sự kiện mẫu cho nút Cập nhật
        btnLuu.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Đang tiến hành cập nhật phiếu nhập " + maPhieuNhap + " xuống CSDL...");
            // TODO: Viết logic lấy dữ liệu trên form update xuống SQL tại đây
        });
    }
    
    // ==========================================
    // 5. HÀM ĐỔ DỮ LIỆU CŨ VÀO FORM
    // ==========================================
    private void loadDataLenForm() {
        // TODO: Dùng maPhieuNhap để truy vấn CSDL lấy ra PhieuNhapDTO và danh sách ChiTietPhieuNhapDTO
        
        // VÍ DỤ GIẢ LẬP: Điền sẵn Nhân viên và Nhà cung cấp
        // cbxNhaCungCap.setSelectedItem("Tên nhà cung cấp cũ lấy từ CSDL");
        // cbxNhanVien.setSelectedItem("Mã NV cũ");
        
        // VÍ DỤ GIẢ LẬP: Thêm các dòng chi tiết cũ vào bảng
        // chiTietModel.addRow(new Object[]{1, "iPhone 15 Pro Max", 10, 25000000, 250000000});
        // chiTietModel.addRow(new Object[]{2, "Samsung Galaxy S24 Ultra", 5, 20000000, 100000000});
    }
}