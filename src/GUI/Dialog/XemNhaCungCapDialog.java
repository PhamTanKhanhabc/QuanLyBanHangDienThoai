package GUI.Dialog;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class XemNhaCungCapDialog extends JDialog {
    public XemNhaCungCapDialog(Frame owner, boolean modal, String maNCC, String tenNCC, String sdt, String diaChi) {
        super(owner, modal);
        
        setTitle("Chi Tiết Nhà Cung Cấp");
        setSize(600, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(Color.WHITE);

        // TOP
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new EmptyBorder(15, 0, 0, 0));
        JLabel lblTitle = new JLabel("THÔNG TIN CHI TIẾT");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTitle.setForeground(new Color(65, 120, 255));
        pnlTop.add(lblTitle);

        // CENTER
        JPanel pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBorder(new EmptyBorder(20, 30, 20, 30));

        Dimension labelSize = new Dimension(130, 40);
        String[] labels = {"Mã NCC:", "Tên nhà cung cấp:", "Số điện thoại:", "Địa chỉ:"};
        String[] values = {maNCC, tenNCC, sdt, diaChi};

        // Dùng vòng lặp tạo 4 dòng cho lẹ
        for (int i = 0; i < 4; i++) {
            JPanel row = new JPanel(new BorderLayout(10, 10));
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Roboto", Font.PLAIN, 14));
            lbl.setPreferredSize(labelSize);
            
            JTextField txt = new JTextField(values[i]);
            txt.setEditable(false);
            txt.setBackground(new Color(245, 245, 245));
            txt.setFont(new Font("Roboto", Font.BOLD, 14)); // In đậm chữ cho dễ nhìn
            
            row.add(lbl, BorderLayout.WEST);
            row.add(txt, BorderLayout.CENTER);
            
            pnlCenter.add(row);
            if(i < 3) pnlCenter.add(Box.createVerticalStrut(15));
        }

        // BOTTOM
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(120, 40));
        btnDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDong.addActionListener(e -> dispose());
        pnlBottom.add(btnDong);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
    }
}