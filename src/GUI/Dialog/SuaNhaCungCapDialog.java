package GUI.Dialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import DTO.NhaCungCapDTO;
import BUS.NhaCungCapBUS;

public class SuaNhaCungCapDialog extends JDialog {
    private JTextField txtMaNCC;
    private JTextField txtTenNCC;
    private JTextField txtSDT;
    private JTextField txtDiaChi;
    private JButton btnLuu, btnHuy;
    
    private String maNCC;

    public SuaNhaCungCapDialog(Frame owner, boolean modal, String maNCC, String tenNCC, String sdt, String diaChi) {
        super(owner, modal);
        this.maNCC = maNCC;
        initUI(tenNCC, sdt, diaChi);
    }

    private void initUI(String tenNCC, String sdt, String diaChi) {
        setTitle("Sửa Nhà Cung Cấp");
        setSize(600, 400); 
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new EmptyBorder(15, 0, 0, 0));
        JLabel lblTitle = new JLabel("SỬA NHÀ CUNG CẤP");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTitle.setForeground(new Color(65, 120, 255));
        pnlTop.add(lblTitle);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBorder(new EmptyBorder(20, 30, 20, 30));

        Dimension labelSize = new Dimension(130, 40); 

        JPanel row0 = new JPanel(new BorderLayout(10, 10)); 
        row0.setBackground(Color.WHITE);
        row0.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblMa = new JLabel("Mã NCC:");
        lblMa.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblMa.setPreferredSize(labelSize);
        txtMaNCC = new JTextField(maNCC);
        txtMaNCC.setEditable(false);
        txtMaNCC.setBackground(new Color(245, 245, 245));
        row0.add(lblMa, BorderLayout.WEST);
        row0.add(txtMaNCC, BorderLayout.CENTER);

        JPanel row1 = new JPanel(new BorderLayout(10, 10)); 
        row1.setBackground(Color.WHITE);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblTen = new JLabel("Tên nhà cung cấp:");
        lblTen.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblTen.setPreferredSize(labelSize);
        txtTenNCC = new JTextField(tenNCC);
        row1.add(lblTen, BorderLayout.WEST);
        row1.add(txtTenNCC, BorderLayout.CENTER);

        JPanel row2 = new JPanel(new BorderLayout(10, 10));
        row2.setBackground(Color.WHITE);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblSDT.setPreferredSize(labelSize);
        txtSDT = new JTextField(sdt);
        row2.add(lblSDT, BorderLayout.WEST);
        row2.add(txtSDT, BorderLayout.CENTER);

        JPanel row3 = new JPanel(new BorderLayout(10, 10));
        row3.setBackground(Color.WHITE);
        row3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblDiaChi.setPreferredSize(labelSize);
        txtDiaChi = new JTextField(diaChi);
        row3.add(lblDiaChi, BorderLayout.WEST);
        row3.add(txtDiaChi, BorderLayout.CENTER);

        pnlCenter.add(row0); pnlCenter.add(Box.createVerticalStrut(15));
        pnlCenter.add(row1); pnlCenter.add(Box.createVerticalStrut(15));
        pnlCenter.add(row2); pnlCenter.add(Box.createVerticalStrut(15));
        pnlCenter.add(row3);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLuu = new JButton("Cập nhật");
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setBackground(new Color(65, 120, 255));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlBottom.add(btnHuy);
        pnlBottom.add(btnLuu);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        
        btnLuu.addActionListener(e -> {
            String tenNCCMoi = txtTenNCC.getText().trim();
            String sdtMoi = txtSDT.getText().trim();
            String diaChiMoi = txtDiaChi.getText().trim();

            if (tenNCCMoi.isEmpty() || sdtMoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Tên và Số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!sdtMoi.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải chứa 10-11 chữ số!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NhaCungCapDTO nccUpdate = new NhaCungCapDTO(maNCC, tenNCCMoi, sdtMoi, diaChiMoi, 1); 

            NhaCungCapBUS nccBUS = new NhaCungCapBUS();
            boolean isSuccess = nccBUS.update(nccUpdate);

            if (isSuccess) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}