package GUI;

import BUS.NhanVienBUS;
import BUS.VaiTroBUS;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Lớp giao diện đăng nhập hệ thống
 * @author user
 */
public class LogIn extends JFrame {

    // ================== KHAI BÁO THUỘC TÍNH ==================
    private JLabel lblDangNhap, lblImage, lblTieuDe;
    private JPanel pnlMain, pnlBtnLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    // ================== CONSTRUCTOR ==================
    public LogIn() {
        initUI();  // gọi hàm khởi tạo giao diện

        // Gán sẵn tài khoản test
        txtUsername.setText("admin");
        txtPassword.setText("123456");
    }

    // ================== KHỞI TẠO GIAO DIỆN ==================
    private void initUI() {
        // Thiết lập JFrame
        setTitle("Đăng nhập");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Tạo phần hình bên trái
        createImagePanel();

        // ===== Panel chính bên phải =====
        pnlMain = new JPanel();
        pnlMain.setBackground(Color.white);
        pnlMain.setBorder(new EmptyBorder(100, 0, 0, 0));
        pnlMain.setPreferredSize(new Dimension(500, 500));
        pnlMain.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        // Tiêu đề
        lblTieuDe = new JLabel("ĐĂNG NHẬP");
        lblTieuDe.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 20));
        pnlMain.add(lblTieuDe);

        // Panel nhập tài khoản mật khẩu
        JPanel pnlInput = new JPanel();
        pnlInput.setBackground(Color.white);
        pnlInput.setPreferredSize(new Dimension(400, 100));
        pnlInput.setLayout(new GridLayout(2, 1, 0, 15));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        pnlInput.add(txtUsername);
        pnlInput.add(txtPassword);

        pnlMain.add(pnlInput);

        // Tạo nút đăng nhập
        createLoginButton();

        add(pnlMain, BorderLayout.EAST);
    }

    // ================== TẠO PANEL HÌNH ẢNH ==================
    private void createImagePanel() {
        JPanel pnlImage = new JPanel(new BorderLayout());
        pnlImage.setPreferredSize(new Dimension(500, 500));
        pnlImage.setBackground(Color.white);

        lblImage = new JLabel();
        FlatSVGIcon svgIcon = new FlatSVGIcon("img/Login.svg", 500, 500);
        lblImage.setIcon(svgIcon);

        pnlImage.add(lblImage);
        add(pnlImage, BorderLayout.WEST);
    }

    // ================== TẠO NÚT ĐĂNG NHẬP ==================
    private void createLoginButton() {
        lblDangNhap = new JLabel("ĐĂNG NHẬP");
        lblDangNhap.setForeground(Color.white);
        lblDangNhap.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));

        pnlBtnLogin = new JPanel();
        pnlBtnLogin.putClientProperty(FlatClientProperties.STYLE, "arc: 99");
        pnlBtnLogin.setBackground(new Color(65, 120, 255));
        pnlBtnLogin.setPreferredSize(new Dimension(380, 45));
        pnlBtnLogin.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 12));

        pnlBtnLogin.add(lblDangNhap);
        pnlMain.add(pnlBtnLogin);

        // Sự kiện click chuột
        pnlBtnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                checkLogin();
            }
        });

        // Nhấn Enter để đăng nhập
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkLogin();
                }
            }
        });
    }

    // ================== XỬ LÝ ĐĂNG NHẬP ==================
    private void checkLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Kiểm tra rỗng
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        // Kiểm tra trong database
        TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByUser(username);

        if (tk == null) {
            JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại");
        } 
        else if (password.equals(tk.getMatKhau())) {
            // 👉 Lấy tên nhân viên bằng BUS
            NhanVienBUS nvBUS = new NhanVienBUS();
            String tenNV = nvBUS.getHoTen(tk.getMaNhanVien());

            // 👉 Lấy vai trò
            VaiTroBUS vtBUS = new VaiTroBUS();
            String tenVT = vtBUS.getTenVaiTro(tk.getMaVaiTro());

            this.dispose();

            new Main(tenNV, tenVT, tk.getMaVaiTro()).setVisible(true);
        } 
        else {
            JOptionPane.showMessageDialog(this, "Sai mật khẩu");
        }
    }

    // ================== HÀM MAIN ==================
    public static void main(String[] args) {

        // Cài đặt FlatLaf
        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.setup();

        UIManager.put("PasswordField.showRevealButton", true);

        new LogIn().setVisible(true);
    }
}