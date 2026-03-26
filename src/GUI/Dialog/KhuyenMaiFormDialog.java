package GUI.Dialog;

import BUS.ChuongTrinhKhuyenMaiBUS;
import DTO.ChuongTrinhKhuyenMaiDTO;
import GUI.Panel.KhuyenMaiGUI;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KhuyenMaiFormDialog extends JDialog {

    private final KhuyenMaiGUI panel;
    private final ChuongTrinhKhuyenMaiDTO current;
    private final ZoneId zoneId = ZoneId.systemDefault();

    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtLoai;
    private JTextField txtMoTa;
    private JDateChooser dateNgayBD;
    private JDateChooser dateNgayKT;
    private JComboBox<String> cboxTrangThai;
    private final ChuongTrinhKhuyenMaiBUS kmBUS;

    public KhuyenMaiFormDialog(Frame parent, boolean modal,
        KhuyenMaiGUI panel, ChuongTrinhKhuyenMaiDTO current,
        ChuongTrinhKhuyenMaiBUS kmBUS) {
    super(parent, modal);
    this.panel = panel;
    this.current = current;
    this.kmBUS = kmBUS;
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
        dateNgayBD = createDateChooser();
        dateNgayKT = createDateChooser();
        cboxTrangThai = new JComboBox<>(new String[]{"Đang diễn ra", "Đã kết thúc"});

        txtMa.setEditable(false);

        form.add(createRow("Mã CTKM", txtMa));
        form.add(createRow("Tên CTKM", txtTen));
        form.add(createRow("Loại khuyến mãi", txtLoai));
        form.add(createRow("Mô tả", txtMoTa));
        form.add(createRow("Ngày bắt đầu", dateNgayBD));
        form.add(createRow("Ngày kết thúc", dateNgayKT));
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

    private JDateChooser createDateChooser() {
        JDateChooser chooser = new JDateChooser();
        chooser.setDateFormatString("dd/MM/yyyy");
        chooser.setPreferredSize(new Dimension(500, 35));
        return chooser;
    }

    private JPanel createRow(String label, Component input) {
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
            dateNgayBD.setDate(toDate(LocalDate.now()));
            dateNgayKT.setDate(toDate(LocalDate.now().plusDays(7)));
            cboxTrangThai.setSelectedItem("Đang diễn ra");
            return;
        }

        txtMa.setText(safe(current.getMaCTKM()));
        txtTen.setText(safe(current.getTenCTKM()));
        txtLoai.setText(safe(current.getLoaiKhuyenMai()));
        txtMoTa.setText(safe(current.getMoTa()));
        dateNgayBD.setDate(toDate(current.getNgayBatDau()));
        dateNgayKT.setDate(toDate(current.getNgayKetThuc()));
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

        LocalDate ngayBD = getSelectedDate(dateNgayBD, "Ngày bắt đầu không được để trống!");
        if (ngayBD == null) {
            focusDateChooser(dateNgayBD);
            return;
        }

        LocalDate ngayKT = getSelectedDate(dateNgayKT, "Ngày kết thúc không được để trống!");
        if (ngayKT == null) {
            focusDateChooser(dateNgayKT);
            return;
        }

        if (ngayKT.isBefore(ngayBD)) {
            showWarn("Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu!");
            focusDateChooser(dateNgayKT);
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

    private LocalDate getSelectedDate(JDateChooser chooser, String message) {
        Date date = chooser.getDate();
        if (date == null) {
            showWarn(message);
            return null;
        }
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDate();
    }

    private Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    private void focusDateChooser(JDateChooser chooser) {
        if (chooser.getDateEditor() != null && chooser.getDateEditor().getUiComponent() != null) {
            chooser.getDateEditor().getUiComponent().requestFocus();
        }
    }

    private int getTrangThaiValue() {
        return "Đang diễn ra".equals(String.valueOf(cboxTrangThai.getSelectedItem())) ? 1 : 0;
    }

    private String safe(String text) {
        return text == null ? "" : text;
    }

    private void showWarn(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
}
