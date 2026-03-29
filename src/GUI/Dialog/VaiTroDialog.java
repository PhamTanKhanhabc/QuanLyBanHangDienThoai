package GUI.Dialog;

import BUS.VaiTroBUS;
import DTO.VaiTroDTO;
import java.awt.*;
import javax.swing.*;

public class VaiTroDialog extends JDialog {

    private VaiTroBUS vaiTroBUS = new VaiTroBUS();
    private JTextField txtMa, txtTen;
    private JButton btnSave, btnCancel;
    private String type; 
    private boolean isSuccess = false; 

    public VaiTroDialog(Frame parent, boolean modal, String type, VaiTroDTO dto) {
        super(parent, modal);
        this.type = type;
        initComponents();
        
        // Bổ sung điều kiện xử lý cho trạng thái "Xem"
        if ((type.equals("Sua") || type.equals("Xem")) && dto != null) {
            setTitle(type.equals("Sua") ? "Cap Nhat Vai Tro" : "Chi Tiet Vai Tro");
            txtMa.setText(dto.getMaVaiTro());
            txtMa.setEditable(false); 
            txtTen.setText(dto.getTenVaiTro());
            
            // Nếu là chế độ xem, khóa toàn bộ thao tác và ẩn nút Lưu
            if (type.equals("Xem")) {
                txtTen.setEditable(false);
                btnSave.setVisible(false);
                btnCancel.setText("Dong");
            }
        } else {
            setTitle("Them Vai Tro Moi");
        }
    }

    private void initComponents() {
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelInput.add(new JLabel("Ma Vai Tro:"));
        txtMa = new JTextField();
        panelInput.add(txtMa);

        panelInput.add(new JLabel("Ten Vai Tro:"));
        txtTen = new JTextField();
        panelInput.add(txtTen);

        add(panelInput, BorderLayout.CENTER);

        JPanel panelButton = new JPanel();
        btnSave = new JButton("Luu");
        btnCancel = new JButton("Huy");

        panelButton.add(btnSave);
        panelButton.add(btnCancel);
        add(panelButton, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            String ma = txtMa.getText().trim();
            String ten = txtTen.getText().trim();
            
            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
                return;
            }

            VaiTroDTO newDto = new VaiTroDTO(ma, ten, 1);
            if (type.equals("Them")) {
                if (vaiTroBUS.add(newDto)) {
                    JOptionPane.showMessageDialog(this, "Them thanh cong!");
                    isSuccess = true;
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Them that bai (Trung ma hoac ten)!");
                }
            } else if (type.equals("Sua")) { // Cập nhật lại logic chỗ này cho chặt chẽ hơn
                if (vaiTroBUS.update(newDto)) {
                    JOptionPane.showMessageDialog(this, "Cap nhat thanh cong!");
                    isSuccess = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cap nhat that bai!");
                }
            }
        });

        btnCancel.addActionListener(e -> dispose());
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}