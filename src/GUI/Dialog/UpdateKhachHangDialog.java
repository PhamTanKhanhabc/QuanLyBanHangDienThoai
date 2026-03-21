package GUI.Dialog;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import GUI.Panel.KhachHangPanel;
import java.awt.*;
import javax.swing.*;

public class UpdateKhachHangDialog extends JDialog {
    private JTextField txtMaKH, txtHo, txtTen, txtSoDT, txtDiaChi;
    private JButton btnSave, btnCancel;
    private KhachHangBUS khBUS = new KhachHangBUS();
    private KhachHangPanel parentPanel;
    private KhachHangDTO currentKH;

    public UpdateKhachHangDialog(Frame parent, boolean modal, KhachHangPanel panel, KhachHangDTO kh) {
        super(parent, modal);
        this.parentPanel = panel;
        this.currentKH = kh;
        initComponents();
        fillData(); 
    }

    private void initComponents() {
        setTitle("Cập nhật thông tin khách hàng");
        setSize(400, 350);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel(" Mã khách hàng:"));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false); 
        add(txtMaKH);

        add(new JLabel(" Họ:"));
        txtHo = new JTextField();
        add(txtHo);

        add(new JLabel(" Tên:"));
        txtTen = new JTextField();
        add(txtTen);

        add(new JLabel(" Số điện thoại:"));
        txtSoDT = new JTextField();
        add(txtSoDT);

        add(new JLabel(" Địa chỉ:"));
        txtDiaChi = new JTextField();
        add(txtDiaChi);

        btnSave = new JButton("Lưu thay đổi");
        btnCancel = new JButton("Hủy bỏ");
        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(e -> btnSaveActionPerformed());
        btnCancel.addActionListener(e -> dispose());
    }

    private void fillData() {
        txtMaKH.setText(currentKH.getMaKH());
        txtHo.setText(currentKH.getHo());
        txtTen.setText(currentKH.getTen());
        txtSoDT.setText(currentKH.getSoDT());
        txtDiaChi.setText(currentKH.getDiaChi());
    }

    private void btnSaveActionPerformed() {
        
        currentKH.setHo(txtHo.getText().trim());
        currentKH.setTen(txtTen.getText().trim());
        currentKH.setSoDT(txtSoDT.getText().trim());
        currentKH.setDiaChi(txtDiaChi.getText().trim());

        
        String result = khBUS.update(currentKH);

        if (result.contains("thành công")) {
            JOptionPane.showMessageDialog(this, result, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            parentPanel.loadTable(khBUS.getAll()); 
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}