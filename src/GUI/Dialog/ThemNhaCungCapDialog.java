package GUI.Dialog;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ThemNhaCungCapDialog extends JDialog {
    private JTextField txtTenNCC;
    private JTextField txtSDT;
    private JTextField txtDiaChi;
    
    private JButton btnLuu, btnHuy;

    public ThemNhaCungCapDialog(Frame owner, boolean modal) {
        super(owner, modal);
        initUI();
    }

    private void initUI() {
        setTitle("Thêm Nhà Cung Cấp");
        setSize(600, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(Color.WHITE);

        // ==========================================
        // 1. TOP PANEL (Tiêu đề)
        // ==========================================
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        JLabel lblTitle = new JLabel("THÊM NHÀ CUNG CẤP");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTitle.setForeground(new Color(65, 120, 255)); // Màu xanh chủ đạo
        pnlTop.add(lblTitle);

        // ==========================================
        // 2. CENTER PANEL (Form nhập liệu co giãn)
        // ==========================================
        JPanel pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBorder(new EmptyBorder(20, 30, 20, 30));


        Dimension labelSize = new Dimension(130, 40); 

        // -- DÒNG 1: Tên nhà cung cấp --
        JPanel row1 = new JPanel(new BorderLayout(10, 10)); 
        row1.setBackground(Color.WHITE);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Khóa chiều cao không cho giãn dọc
        
        JLabel lblTen = new JLabel("Tên nhà cung cấp:");
        lblTen.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblTen.setPreferredSize(labelSize);
        
        txtTenNCC = new JTextField();
        
        row1.add(lblTen, BorderLayout.WEST);
        row1.add(txtTenNCC, BorderLayout.CENTER);

        // -- DÒNG 2: Số điện thoại --
        JPanel row2 = new JPanel(new BorderLayout(10, 10));
        row2.setBackground(Color.WHITE);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblSDT.setPreferredSize(labelSize);
        
        txtSDT = new JTextField();
        
        row2.add(lblSDT, BorderLayout.WEST);
        row2.add(txtSDT, BorderLayout.CENTER);

        // -- DÒNG 3: Địa chỉ --
        JPanel row3 = new JPanel(new BorderLayout(10, 10));
        row3.setBackground(Color.WHITE);
        row3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblDiaChi.setPreferredSize(labelSize);
        
        txtDiaChi = new JTextField();
        
        row3.add(lblDiaChi, BorderLayout.WEST);
        row3.add(txtDiaChi, BorderLayout.CENTER);

       
        pnlCenter.add(row1);
        pnlCenter.add(Box.createVerticalStrut(15)); // Cách nhau 15px
        pnlCenter.add(row2);
        pnlCenter.add(Box.createVerticalStrut(15));
        pnlCenter.add(row3);

        // ==========================================
        // 3. BOTTOM PANEL (Nút bấm)
        // ==========================================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLuu = new JButton("Lưu thông tin");
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setBackground(new Color(65, 120, 255));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlBottom.add(btnHuy);
        pnlBottom.add(btnLuu);

        // ==========================================
        // 4. ADD VÀO DIALOG VÀ BẮT SỰ KIỆN
        // ==========================================
        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        // Đóng form khi bấm Hủy
        btnHuy.addActionListener(e -> dispose());
        

        btnLuu.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Chức năng lưu đang được xây dựng!");
        });
    }
}