package GUI.Dialog;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class XoaNhaCungCapDialog extends JDialog {
    public XoaNhaCungCapDialog(Frame owner, boolean modal, String maNCC, String tenNCC, String sdt, String diaChi) {
        super(owner, modal);
        
        setTitle("Xác Nhận Xóa");
        setSize(600, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(Color.WHITE);

        // TOP (Cảnh báo đỏ)
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new EmptyBorder(15, 0, 0, 0));
        JLabel lblTitle = new JLabel("BẠN CHẮC CHẮN MUỐN XÓA?");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 22));
        lblTitle.setForeground(new Color(220, 53, 69)); // Đỏ
        pnlTop.add(lblTitle);

        // CENTER (Khóa 100%)
        JPanel pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBorder(new EmptyBorder(20, 30, 20, 30));

        Dimension labelSize = new Dimension(130, 40);
        String[] labels = {"Mã NCC:", "Tên nhà cung cấp:", "Số điện thoại:", "Địa chỉ:"};
        String[] values = {maNCC, tenNCC, sdt, diaChi};

        for (int i = 0; i < 4; i++) {
            JPanel row = new JPanel(new BorderLayout(10, 10));
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Roboto", Font.PLAIN, 14));
            lbl.setPreferredSize(labelSize);
            
            JTextField txt = new JTextField(values[i]);
            txt.setEditable(false);
            txt.setBackground(new Color(255, 240, 240)); // Nền đỏ nhạt cảnh báo
            
            row.add(lbl, BorderLayout.WEST);
            row.add(txt, BorderLayout.CENTER);
            
            pnlCenter.add(row);
            if(i < 3) pnlCenter.add(Box.createVerticalStrut(15));
        }

        // BOTTOM
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton btnXoa = new JButton("Xác nhận Xóa");
        btnXoa.setPreferredSize(new Dimension(150, 40));
        btnXoa.setBackground(new Color(220, 53, 69)); // Đỏ
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlBottom.add(btnHuy);
        pnlBottom.add(btnXoa);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        btnXoa.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Đã gọi lệnh xóa mã: " + maNCC);
            dispose();
        });
    }
}