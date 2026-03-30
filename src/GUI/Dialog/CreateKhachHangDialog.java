package GUI.Dialog;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import GUI.Panel.KhachHangPanel;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import javax.swing.*;

public class CreateKhachHangDialog extends JDialog {
    private KhachHangPanel parentPanel;
    private KhachHangBUS khBUS = new KhachHangBUS();
    
    private JTextField txtMaKH, txtHo, txtTen, txtSDT, txtDiaChi, txtSTT;
    private JButton btnAdd, btnCancel;

    public CreateKhachHangDialog(Frame parent, boolean modal, KhachHangPanel panel) {
        super(parent, modal);
        this.parentPanel = panel;
        initComponents();
        autoGenerateInfo();
    }

    private void initComponents() {
        setTitle("Thêm khách hàng mới");
        setSize(400, 500);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        
        mainPanel.add(new JLabel("STT :"));
        txtSTT = new JTextField();
        txtSTT.setEditable(false); // Không cho sửa STT
        mainPanel.add(txtSTT);

        mainPanel.add(new JLabel("Mã khách hàng:"));
        txtMaKH = new JTextField();
        mainPanel.add(txtMaKH);

        mainPanel.add(new JLabel("Họ khách hàng:"));
        txtHo = new JTextField();
        mainPanel.add(txtHo);

        mainPanel.add(new JLabel("Tên khách hàng:"));
        txtTen = new JTextField();
        mainPanel.add(txtTen);

        mainPanel.add(new JLabel("Số điện thoại:"));
        txtSDT = new JTextField();
        mainPanel.add(txtSDT);

        mainPanel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        mainPanel.add(txtDiaChi);

        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        btnAdd = new JButton("Thêm mới");
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        
        btnCancel = new JButton("Hủy");
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnCancel);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        
        btnAdd.addActionListener(e -> btnAddActionPerformed());
        btnCancel.addActionListener(e -> dispose());
        
        
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).putClientProperty(FlatClientProperties.STYLE, "arc:10");
            }
        }
    }

    private void autoGenerateInfo() {
    int nextSTT = khBUS.getAll().size() + 1;
    txtSTT.setText(String.valueOf(nextSTT));
    String maMoi = khBUS.getNextID(); 
    txtMaKH.setText(maMoi);
    txtMaKH.setEditable(false); 
}

   private void btnAddActionPerformed() {
        String maKH = txtMaKH.getText().trim();
        String ho = txtHo.getText().trim();
        String ten = txtTen.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String sdt = txtSDT.getText().trim(); 

        // ================== BẮT ĐẦU KIỂM TRA LỖI ==================
        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtSDT.requestFocus();
            return; // Dừng lại, không chạy xuống dưới
        }

        // Gọi hàm checkDupSoDT từ KhachHangBUS (Nhớ đảm bảo bạn đã copy hàm này vào BUS nhé)
        if (!khBUS.checkDupSoDT(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại này đã tồn tại trong hệ thống!\nVui lòng nhập số khác.", "Lỗi trùng lặp", JOptionPane.ERROR_MESSAGE);
            txtSDT.selectAll(); // Bôi đen số cũ bị trùng để nhập lại cho nhanh
            txtSDT.requestFocus();
            return; // Dừng lại, không chạy xuống dưới
        }
        // ================== KẾT THÚC KIỂM TRA =====================

        // Nếu vượt qua được các vòng kiểm tra trên, tiến hành lưu bình thường:
        KhachHangDTO kh = new KhachHangDTO(maKH, ho, ten, diaChi, 1, sdt);
        String ketQua = khBUS.add(kh);
        
        if (ketQua.contains("thành công")) {
            JOptionPane.showMessageDialog(this, ketQua, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            // Mẹo nhỏ: Nên ép khởi tạo lại BUS ở đây để chắc chắn loadTable lấy đúng data mới nhất từ Database
            khBUS = new KhachHangBUS(); 
            parentPanel.loadTable(khBUS.getAll()); 
            
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, ketQua, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        }
    }
}