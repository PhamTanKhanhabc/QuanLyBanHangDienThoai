package GUI.Dialog;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;

public class NhanVienDialog extends JDialog {

    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private JTextField txtMa, txtHo, txtTen, txtNgaySinh, txtDiaChi, txtDienThoai, txtLuong;
    private JButton btnSave, btnCancel;
    private String type; 
    private boolean isSuccess = false;
    
    // Khai báo formatter để tái sử dụng
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public NhanVienDialog(Frame parent, boolean modal, String type, NhanVienDTO dto) {
        super(parent, modal);
        this.type = type;
        initComponents();
        
        if ((type.equals("Sua") || type.equals("Xem")) && dto != null) {
            setTitle(type.equals("Sua") ? "Cap Nhat Nhan Vien" : "Chi Tiet Nhan Vien");
            txtMa.setText(dto.getMaNV());
            txtHo.setText(dto.getHo());
            txtTen.setText(dto.getTen());
            
            // Format LocalDate sang chuỗi dd/MM/yyyy để hiển thị
            if (dto.getNgaySinh() != null) {
                txtNgaySinh.setText(dto.getNgaySinh().format(formatter));
            }
            
            txtDiaChi.setText(dto.getDiaChi());
            txtDienThoai.setText(dto.getDienThoai());
            txtLuong.setText(String.valueOf(dto.getLuongThang()));
            
            // Khóa mã nhân viên khi sửa hoặc xem
            txtMa.setEditable(false);
            
            // Nếu là chế độ xem thì khóa tất cả các trường và ẩn nút Lưu
            if (type.equals("Xem")) {
                txtHo.setEditable(false);
                txtTen.setEditable(false);
                txtNgaySinh.setEditable(false);
                txtDiaChi.setEditable(false);
                txtDienThoai.setEditable(false);
                txtLuong.setEditable(false);
                btnSave.setVisible(false);
                btnCancel.setText("Dong"); // Đổi chữ nút Hủy thành Đóng
            }
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
                String nsStr = txtNgaySinh.getText().trim();
                String dc = txtDiaChi.getText().trim();
                String sdt = txtDienThoai.getText().trim();
                double luong = Double.parseDouble(txtLuong.getText().trim());

                if (ma.isEmpty() || ho.isEmpty() || ten.isEmpty() || nsStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long nhap du thong tin co ban (Ma, Ho, Ten, Ngay Sinh)!");
                    return;
                }

                // Parse chuỗi ngày sinh sang LocalDate
                LocalDate ns = null;
                try {
                    ns = LocalDate.parse(nsStr, formatter);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Ngay sinh khong hop le! Vui long nhap dung dinh dang dd/MM/yyyy.");
                    return;
                }

                // Cập nhật dùng constructor 8 tham số (truyền LocalDate ns và int trangThai = 1)
                NhanVienDTO newDto = new NhanVienDTO(ma, ho, ten, ns, dc, sdt, luong, 1);
                
                if (type.equals("Them")) {
                    if (nhanVienBUS.add(newDto)) {
                        JOptionPane.showMessageDialog(this, "Them thanh cong!");
                        isSuccess = true; dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Trung ma nhan vien!");
                    }
                } else if (type.equals("Sua")) {
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