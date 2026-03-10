/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.ChuongTrinhKhuyenMaiBUS;
import DTO.ChuongTrinhKhuyenMaiDTO;
import GUI.Panel.KhuyenMaiGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KhuyenMaiFormDialog extends JDialog {

    private final ChuongTrinhKhuyenMaiBUS kmBUS = new ChuongTrinhKhuyenMaiBUS();
    private final KhuyenMaiGUI panel;
    private final ChuongTrinhKhuyenMaiDTO current;

    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtLoai;
    private JTextField txtMoTa;
    private JTextField txtNgayBD;
    private JTextField txtNgayKT;
    private JComboBox<String> cboxTrangThai;

    private final DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter[] inputFormats = {
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("d/M/yyyy"),
        DateTimeFormatter.ISO_LOCAL_DATE
    };

    public KhuyenMaiFormDialog(Frame parent, boolean modal, KhuyenMaiGUI panel, ChuongTrinhKhuyenMaiDTO current) {
        super(parent, modal);
        this.panel = panel;
        this.current = current;
        initComponents();
        fillData();
    }

    private void initComponents() {
        setTitle(current == null ? "Thêm khuyến mãi" : "Cập nhật khuyến mãi");
        setSize(720, 430);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(65, 120, 255));
        header.setPreferredSize(new Dimension(100, 50));
        JLabel lblTitle = new JLabel(
                current == null ? "THÊM CHƯƠNG TRÌNH KHUYẾN MÃI" : "CẬP NHẬT CHƯƠNG TRÌNH KHUYẾN MÃI",
                JLabel.CENTER
        );
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(7, 1, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtLoai = new JTextField();
        txtMoTa = new JTextField();
        txtNgayBD = new JTextField();
        txtNgayKT = new JTextField();
        cboxTrangThai = new JComboBox<>(new String[]{"Đang diễn ra", "Đã kết thúc"});

        txtMa.setEditable(false);

        form.add(createRow("Mã CTKM", txtMa));
        form.add(createRow("Tên CTKM", txtTen));
        form.add(createRow("Loại khuyến mãi", txtLoai));
        form.add(createRow("Mô tả", txtMoTa));
        form.add(createRow("Ngày bắt đầu", txtNgayBD));
        form.add(createRow("Ngày kết thúc", txtNgayKT));
        form.add(createRow("Trạng thái", cboxTrangThai));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottom.setBackground(Color.WHITE);

        JButton btnCancel = new JButton("Hủy");
        JButton btnSave = new JButton(current == null ? "Thêm" : "Cập nhật");

        btnCancel.setPreferredSize(new Dimension(140, 38));
        btnSave.setPreferredSize(new Dimension(140, 38));

        bottom.add(btnCancel);
        bottom.add(btnSave);

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> saveData());

        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel createRow(String label, java.awt.Component input) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(140, 35));

        input.setPreferredSize(new Dimension(500, 35));

        row.add(lbl);
        row.add(input);
        return row;
    }

    private void fillData() {
        if (current == null) {
            txtMa.setText("Tự động tạo");
            txtNgayBD.setText(LocalDate.now().format(displayFormat));
            txtNgayKT.setText(LocalDate.now().plusDays(7).format(displayFormat));
            cboxTrangThai.setSelectedItem("Đang diễn ra");
            return;
        }

        txtMa.setText(safe(current.getMaCTKM()));
        txtTen.setText(safe(current.getTenCTKM()));
        txtLoai.setText(safe(current.getLoaiKhuyenMai()));
        txtMoTa.setText(safe(current.getMoTa()));
        txtNgayBD.setText(formatDate(current.getNgayBatDau()));
        txtNgayKT.setText(formatDate(current.getNgayKetThuc()));
        cboxTrangThai.setSelectedItem(current.getTrangThai() == 1 ? "Đang diễn ra" : "Đã kết thúc");
    }

    private void saveData() {
        String ten = txtTen.getText().trim();
        String loai = txtLoai.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (ten.isEmpty()) {
            showWarn("Tên CTKM không được để trống!");
            txtTen.requestFocus();
            return;
        }

        if (loai.isEmpty()) {
            showWarn("Loại khuyến mãi không được để trống!");
            txtLoai.requestFocus();
            return;
        }

        LocalDate ngayBD;
        LocalDate ngayKT;

        try {
            ngayBD = parseDate(txtNgayBD.getText().trim());
            ngayKT = parseDate(txtNgayKT.getText().trim());
        } catch (IllegalArgumentException ex) {
            showWarn(ex.getMessage());
            return;
        }

        if (ngayKT.isBefore(ngayBD)) {
            showWarn("Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu!");
            txtNgayKT.requestFocus();
            return;
        }

        if (current == null) {
            if (!kmBUS.checkDup(ten)) {
                showWarn("Tên chương trình khuyến mãi đã tồn tại!");
                txtTen.requestFocus();
                return;
            }

            ChuongTrinhKhuyenMaiDTO dto = new ChuongTrinhKhuyenMaiDTO(
                    null,
                    ten,
                    loai,
                    moTa,
                    ngayBD,
                    ngayKT,
                    getTrangThaiValue()
            );

            if (kmBUS.add(dto)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                panel.refreshData();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
            return;
        }

        boolean changedName = !safe(current.getTenCTKM()).equalsIgnoreCase(ten);
        if (changedName && !kmBUS.checkDup(ten)) {
            showWarn("Tên chương trình khuyến mãi đã tồn tại!");
            txtTen.requestFocus();
            return;
        }

        ChuongTrinhKhuyenMaiDTO dto = new ChuongTrinhKhuyenMaiDTO(
                current.getMaCTKM(),
                ten,
                loai,
                moTa,
                ngayBD,
                ngayKT,
                getTrangThaiValue()
        );

        if (kmBUS.update(dto)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            panel.refreshData();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private int getTrangThaiValue() {
        return "Đang diễn ra".equals(String.valueOf(cboxTrangThai.getSelectedItem())) ? 1 : 0;
    }

    private LocalDate parseDate(String text) {
        for (DateTimeFormatter fmt : inputFormats) {
            try {
                return LocalDate.parse(text, fmt);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Ngày phải theo định dạng dd/MM/yyyy hoặc yyyy-MM-dd.");
    }

    private String formatDate(LocalDate date) {
        return date == null ? "" : date.format(displayFormat);
    }

    private String safe(String text) {
        return text == null ? "" : text;
    }

    private void showWarn(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
}
