package GUI.Dialog;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NhanVienDialog extends JDialog {

    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private JTextField txtMa, txtHo, txtTen, txtNgaySinh, txtDiaChi, txtDienThoai, txtLuong;
    private JButton btnSave, btnCancel;
    private String type; 
    private boolean isSuccess = false;

    public NhanVienDialog(Frame parent, boolean modal, String type, NhanVienDTO dto) {
        super(parent, modal);
        this.type = type;
        initComponents();
        
        if (type.equals("Sua") && dto != null) {
            setTitle("Cap Nhat Nhan Vien");
            txtMa.setText(dto.getMaNV());
            txtMa.setEditable(false);
            txtHo.setText(dto.getHo());
            txtTen.setText(dto.getTen());
            txtNgaySinh.setText(dto.getNgaySinh().toString());
            txtDiaChi.setText(dto.getDiaChi());
            txtDienThoai.setText(dto.getDienThoai());
            txtLuong.setText(String.valueOf(dto.getLuongThang()));
        } else {
            setTitle("Them Nhan Vien Moi");
        }
    }

    private void initComponents() {
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel(new GridLayout(7, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelInput.add(new JLabel("Ma Nhan Vien:")); txtMa = new JTextField(); panelInput.add(txtMa);
        panelInput.add(new JLabel("Ho:")); txtHo = new JTextField(); panelInput.add(txtHo);
        panelInput.add(new JLabel("Ten:")); txtTen = new JTextField(); panelInput.add(txtTen);
        panelInput.add(new JLabel("Ngay Sinh (dd/MM/yyyy):")); txtNgaySinh = new JTextField(); panelInput.add(txtNgaySinh);
        panelInput.add(new JLabel("Dia Chi:")); txtDiaChi = new JTextField(); panelInput.add(txtDiaChi);
        panelInput.add(new JLabel("Dien Thoai:")); txtDienThoai = new JTextField(); panelInput.add(txtDienThoai);
        panelInput.add(new JLabel("Luong Thang:")); txtLuong = new JTextField(); panelInput.add(txtLuong);

        add(panelInput, BorderLayout.CENTER);

        JPanel panelButton = new JPanel();
        btnSave = new JButton("Luu");
        btnCancel = new JButton("Huy");
        panelButton.add(btnSave);
        panelButton.add(btnCancel);
        add(panelButton, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            try {
                String ma = txtMa.getText().trim();
                String ho = txtHo.getText().trim();
                String ten = txtTen.getText().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim(), formatter);
                String dc = txtDiaChi.getText().trim();
                String sdt = txtDienThoai.getText().trim();
                double luong = Double.parseDouble(txtLuong.getText().trim());

                if (ma.isEmpty() || ho.isEmpty() || ten.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long nhap du thong tin co ban!");
                    return;
                }

                NhanVienDTO newDto = new NhanVienDTO(ma, ho, ten, ngaySinh, dc, sdt, luong, 1);
                if (type.equals("Them")) {
                    if (nhanVienBUS.add(newDto)) {
                        JOptionPane.showMessageDialog(this, "Them thanh cong!");
                        isSuccess = true; dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Trung ma nhan vien!");
                    }
                } else {
                    if (nhanVienBUS.update(newDto)) {
                        JOptionPane.showMessageDialog(this, "Cap nhat thanh cong!");
                        isSuccess = true; dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cap nhat that bai!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Luong thang phai la mot so hop le!");
            }
        });

        btnCancel.addActionListener(e -> dispose());
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}