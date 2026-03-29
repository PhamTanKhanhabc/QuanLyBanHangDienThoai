package GUI.Dialog;

import BUS.TaiKhoanBUS;
import BUS.NhanVienBUS;
import BUS.VaiTroBUS;
import DTO.TaiKhoanDTO;
import DTO.NhanVienDTO;
import DTO.VaiTroDTO;
import java.awt.*;
import javax.swing.*;

public class TaiKhoanDialog extends JDialog {

    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private VaiTroBUS vaiTroBUS = new VaiTroBUS();

    private JTextField txtMaTK, txtTenDN, txtMatKhau;
    private JComboBox<String> cboNhanVien, cboVaiTro;
    private JButton btnSave, btnCancel;
    private String type; 
    private boolean isSuccess = false;

    public TaiKhoanDialog(Frame parent, boolean modal, String type, TaiKhoanDTO dto) {
        super(parent, modal);
        this.type = type;
        initComponents();
        loadComboBoxData();
        
        // Thêm điều kiện type.equals("Xem") vào đây
        if ((type.equals("Sua") || type.equals("Xem")) && dto != null) {
            setTitle(type.equals("Sua") ? "Cap Nhat Tai Khoan" : "Chi Tiet Tai Khoan");
            txtMaTK.setText(dto.getMaTaiKhoan());
            txtMaTK.setEditable(false);
            txtTenDN.setText(dto.getTenDangNhap());
            txtMatKhau.setText(dto.getMatKhau());
            
            cboNhanVien.setSelectedItem(dto.getMaNhanVien());
            cboVaiTro.setSelectedItem(dto.getMaVaiTro());
            
            // Nếu là chế độ xem, khóa toàn bộ thao tác và ẩn nút Lưu
            if (type.equals("Xem")) {
                txtTenDN.setEditable(false);
                txtMatKhau.setEditable(false);
                cboNhanVien.setEnabled(false);
                cboVaiTro.setEnabled(false);
                btnSave.setVisible(false);
                btnCancel.setText("Dong");
            }
        } else {
            setTitle("Them Tai Khoan Moi");
        }
    }

    private void loadComboBoxData() {
        for (NhanVienDTO nv : nhanVienBUS.getAll()) {
            cboNhanVien.addItem(nv.getMaNV());
        }
        for (VaiTroDTO vt : vaiTroBUS.getAll()) {
            cboVaiTro.addItem(vt.getMaVaiTro());
        }
    }

    private void initComponents() {
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelInput.add(new JLabel("Ma Tai Khoan:")); txtMaTK = new JTextField(); panelInput.add(txtMaTK);
        panelInput.add(new JLabel("Ten Dang Nhap:")); txtTenDN = new JTextField(); panelInput.add(txtTenDN);
        panelInput.add(new JLabel("Mat Khau:")); txtMatKhau = new JTextField(); panelInput.add(txtMatKhau);
        
        panelInput.add(new JLabel("Ma Nhan Vien:")); cboNhanVien = new JComboBox<>(); panelInput.add(cboNhanVien);
        panelInput.add(new JLabel("Ma Vai Tro:")); cboVaiTro = new JComboBox<>(); panelInput.add(cboVaiTro);

        add(panelInput, BorderLayout.CENTER);

        JPanel panelButton = new JPanel();
        btnSave = new JButton("Luu");
        btnCancel = new JButton("Huy");
        panelButton.add(btnSave);
        panelButton.add(btnCancel);
        add(panelButton, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            String ma = txtMaTK.getText().trim();
            String user = txtTenDN.getText().trim();
            String pass = txtMatKhau.getText().trim();
            String maNV = (String) cboNhanVien.getSelectedItem();
            String maVT = (String) cboVaiTro.getSelectedItem();

            if (ma.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long nhap du thong tin!");
                return;
            }

            TaiKhoanDTO newDto = new TaiKhoanDTO(ma, user, pass, maNV, maVT, 1);
            if (type.equals("Them")) {
                if (taiKhoanBUS.add(newDto)) {
                    JOptionPane.showMessageDialog(this, "Them thanh cong!");
                    isSuccess = true; dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Loi: Trung ma hoac ten dang nhap!");
                }
            } else if (type.equals("Sua")) {
                if (taiKhoanBUS.update(newDto)) {
                    JOptionPane.showMessageDialog(this, "Cap nhat thanh cong!");
                    isSuccess = true; dispose();
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