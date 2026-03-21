package GUI.Dialog;

import DTO.KhachHangDTO;
import java.awt.*;
import javax.swing.*;

public class ChiTietKhachHangDialog extends JDialog {
    private KhachHangDTO kh;
    private JTextField txtMaKH, txtHoTen, txtSoDT, txtDiaChi, txtTrangThai;
    private JButton btnClose;

    public ChiTietKhachHangDialog(Frame parent, boolean modal, KhachHangDTO kh) {
        super(parent, modal);
        this.kh = kh;
        initComponents();
        fillData();
    }

    private void initComponents() {
        setTitle("Thông tin chi tiết khách hàng");
        setSize(450, 400);
        setLayout(new BorderLayout(10, 10));
        
        
        JPanel pnlContent = new JPanel(new GridLayout(5, 2, 10, 15));
        pnlContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        txtMaKH = createReadOnlyTextField();
        txtHoTen = createReadOnlyTextField();
        txtSoDT = createReadOnlyTextField();
        txtDiaChi = createReadOnlyTextField();
        txtTrangThai = createReadOnlyTextField();

        
        pnlContent.add(new JLabel("Mã khách hàng:"));
        pnlContent.add(txtMaKH);
        
        pnlContent.add(new JLabel("Họ và tên:"));
        pnlContent.add(txtHoTen);
        
        pnlContent.add(new JLabel("Số điện thoại:"));
        pnlContent.add(txtSoDT);
        
        pnlContent.add(new JLabel("Địa chỉ:"));
        pnlContent.add(txtDiaChi);
        
        pnlContent.add(new JLabel("Trạng thái hệ thống:"));
        pnlContent.add(txtTrangThai);

        
        btnClose = new JButton("Đóng");
        btnClose.setPreferredSize(new Dimension(100, 40));
        btnClose.addActionListener(e -> dispose());
        
        JPanel pnlBottom = new JPanel();
        pnlBottom.add(btnClose);

        add(pnlContent, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    
    private JTextField createReadOnlyTextField() {
        JTextField tf = new JTextField();
        tf.setEditable(false);
        tf.setBackground(new Color(245, 245, 245)); 
        tf.setBorder(BorderFactory.createCompoundBorder(
            tf.getBorder(), 
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        return tf;
    }

    private void fillData() {
        txtMaKH.setText(kh.getMaKH());
        txtHoTen.setText(kh.getHo() + " " + kh.getTen());
        txtSoDT.setText(kh.getSoDT());
        txtDiaChi.setText(kh.getDiaChi());
        txtTrangThai.setText(kh.getTrangThai() == 1 ? "Đang hoạt động" : "Đã khóa/Xóa");
    }
}