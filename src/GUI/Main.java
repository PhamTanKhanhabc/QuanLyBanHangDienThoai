/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import GUI.Panel.KhuyenMaiGUI;
import GUI.Panel.BaoHanhPanel;
import GUI.Panel.SanPhamPanel;
import GUI.Panel.NhaCungCap;
import GUI.Panel.NhanVienPanel;
import GUI.Panel.PhieuNhap;
import GUI.Panel.TaiKhoanPanel;
import GUI.Panel.VaiTroPanel;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
/**
 *
 * @author user
 */
public class Main extends JFrame {
    private JPanel mainContent;
    private JPanel sidebar;
    private JPanel infoPanel;
    
    private JButton btnTrangChu;
    private JButton btnSanPham;
    private JButton btnBaoHanh;
    private JButton btnNhaCungCap;
    private JButton btnPhieuNhap;
    private JButton btnHoaDon;
    private JButton btnKhachHang;
    private JButton btnKhuyenMai;
    private JButton btnNhanVien;
    private JButton btnTaiKhoan;
    private JButton btnVaiTro;
    private JButton btnThongKe;
    private JButton btnLogout;
    
    private List<JButton> menuItems;
    
    private final Color ACTIVE_COLOR = new Color(65, 120, 255);
    
    public Main(){
        initUI();
    }
    private void initUI(){
        setTitle("Quản lý cửa hàng bán hàng điện thoại");
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        createMainContent(); //Phần nội dung bên phải
        createLeftSide(); //Phần thanh bên trái

        setVisible(true);
    }
    private void createMainContent(){
        mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(new Color(245, 245, 245));
        add(mainContent, BorderLayout.CENTER);
    }
    private void createLeftSide(){
        JPanel leftContainer = new JPanel();
        leftContainer.setLayout(new BorderLayout());
        leftContainer.setPreferredSize(new Dimension(250, 800));
        leftContainer.setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 1, new Color(220, 220, 220)
        ));
        add(leftContainer, BorderLayout.WEST);
        
        createInfoPanel(); //Thông tin người dùng phía trên
        createSidebar(); //Phần nút điều hướng
        
        leftContainer.add(infoPanel, BorderLayout.NORTH);
        leftContainer.add(sidebar, BorderLayout.CENTER);
    }
    private void createInfoPanel(){
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new BorderLayout());
        
        JLabel lblAvatar = new JLabel("👤", SwingConstants.CENTER);
        lblAvatar.setPreferredSize(new Dimension(80, 80));
        lblAvatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30)); // tăng size
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        
        JLabel lblName = new JLabel("Nguyễn Văn A");
        JLabel lblRole = new JLabel("Quản lý");
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(lblName);
        textPanel.add(lblRole);
        
        infoPanel.add(lblAvatar, BorderLayout.WEST);
        infoPanel.add(textPanel, BorderLayout.CENTER);
    }
    private void createSidebar(){
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(Color.WHITE);
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); //Sắp xếp theo chiều dọc
        menuPanel.setBackground(Color.WHITE);
        
        btnTrangChu = createMenuButton("Trang chủ");
        btnSanPham = createMenuButton("Sản phẩm");
        btnBaoHanh = createMenuButton("Bảo hành");
        btnNhaCungCap = createMenuButton("Nhà cung cấp");
        btnPhieuNhap = createMenuButton("Phiếu nhập");
        btnHoaDon = createMenuButton("Hóa đơn");
        btnKhachHang = createMenuButton("Khách hàng");
        btnKhuyenMai = createMenuButton("Khuyến mãi");
        btnNhanVien = createMenuButton("Nhân viên");
        btnTaiKhoan = createMenuButton("Tài khoản");
        btnVaiTro = createMenuButton("Vai trò");
        btnThongKe = createMenuButton("Thống kê");
        
        
        menuPanel.add(btnTrangChu);   
        menuPanel.add(btnSanPham);
        menuPanel.add(btnBaoHanh);
        menuPanel.add(btnNhaCungCap);
        menuPanel.add(btnPhieuNhap);
        menuPanel.add(btnHoaDon);
        menuPanel.add(btnKhachHang);
        menuPanel.add(btnKhuyenMai);
        menuPanel.add(btnNhanVien);
        menuPanel.add(btnTaiKhoan);
        menuPanel.add(btnVaiTro);
        menuPanel.add(btnThongKe);
        
        btnLogout = createMenuButton("Đăng xuất");
        btnLogout.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        
        sidebar.add(menuPanel, BorderLayout.CENTER);
        sidebar.add(btnLogout, BorderLayout.SOUTH);
        
        menuItems = new ArrayList<>();
        menuItems.add(btnTrangChu);
        menuItems.add(btnSanPham);
        menuItems.add(btnBaoHanh);
        menuItems.add(btnNhaCungCap);
        menuItems.add(btnPhieuNhap);
        menuItems.add(btnHoaDon);
        menuItems.add(btnKhachHang);
        menuItems.add(btnKhuyenMai);
        menuItems.add(btnNhanVien);
        menuItems.add(btnTaiKhoan);
        menuItems.add(btnVaiTro);
        menuItems.add(btnThongKe);
        
        // Mặc định chọn trang đầu
        setPanel(new JLabel("Trang chủ", SwingConstants.CENTER));
        setActive(btnTrangChu);
        
        // Trang chủ
        btnTrangChu.addActionListener(e -> {
            setPanel(new JLabel("Trang chủ", SwingConstants.CENTER));
            setActive(btnTrangChu);
        });

        // Sản phẩm
        btnSanPham.addActionListener(e -> {
            SanPhamPanel sanPhamPanel = new SanPhamPanel();
            setPanel(sanPhamPanel);
            setActive(btnSanPham);
        });

        // Bảo hành
        btnBaoHanh.addActionListener(e -> {
            BaoHanhPanel baoHanhPanel = new BaoHanhPanel();
            setPanel(baoHanhPanel);
            setActive(btnBaoHanh);
        });

        // Nhà cung cấp
        btnNhaCungCap.addActionListener(e -> {
            setPanel(new NhaCungCap());
            setActive(btnNhaCungCap);
        });

        // Phiếu nhập
        btnPhieuNhap.addActionListener(e -> {
            setPanel(new PhieuNhap());
            setActive(btnPhieuNhap);
        });

        // Hóa đơn
        btnHoaDon.addActionListener(e -> {
            setPanel(new JLabel("Trang hóa đơn", SwingConstants.CENTER));
            setActive(btnHoaDon);
        });

        // Khách hàng
        btnKhachHang.addActionListener(e -> {
            setPanel(new JLabel("Trang khách hàng", SwingConstants.CENTER));
            setActive(btnKhachHang);
        });
        // Khuyến mãi
        btnKhuyenMai.addActionListener(e -> {
            KhuyenMaiGUI khuyenMaiPanel = new KhuyenMaiGUI();
            setPanel(khuyenMaiPanel);
            setActive(btnKhuyenMai);
        });

        // Nhân viên
        btnNhanVien.addActionListener(e -> {
            NhanVienPanel nhanVienPanel = new NhanVienPanel();
            setPanel(nhanVienPanel);
            setActive(btnNhanVien);
        });

        // Tài khoản
        btnTaiKhoan.addActionListener(e -> {
            TaiKhoanPanel taiKhoanPanel = new TaiKhoanPanel();
            setPanel(taiKhoanPanel);
            setActive(btnTaiKhoan);
        });

        // Vai trò
        btnVaiTro.addActionListener(e -> {
            VaiTroPanel vaiTroPanel = new VaiTroPanel();
            setPanel(vaiTroPanel);
            setActive(btnVaiTro);
        });

        // Thống kê
        btnThongKe.addActionListener(e -> {
            setPanel(new JLabel("Trang thống kê", SwingConstants.CENTER));
            setActive(btnThongKe);
        });
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn đăng xuất?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();          // Đóng cửa sổ hiện tại
                new LogIn().setVisible(true);   // Mở form Login
            }
        });
    }
    private JButton createMenuButton(String text){
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT); //căn trái vị trí nút
        btn.setFocusPainted(false); //ẩn viền focus
        btn.setBorderPainted(false); //Ẩn toàn bộ viền nút
        btn.setBackground(Color.WHITE);
        btn.setHorizontalAlignment(SwingConstants.LEFT); //Căn trái nội dung bên trong nút
        btn.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); //con trỏ hình bàn tay
        
        return btn;
    }
    private void setPanel(Component comp){
        mainContent.removeAll();
        mainContent.add(comp, BorderLayout.CENTER);
        mainContent.revalidate();
        mainContent.repaint();
    }
    private void setActive(JButton selectedBtn){
        for(JButton btn : menuItems){
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }

        selectedBtn.setBackground(ACTIVE_COLOR);
        selectedBtn.setForeground(Color.WHITE);
    }
}

