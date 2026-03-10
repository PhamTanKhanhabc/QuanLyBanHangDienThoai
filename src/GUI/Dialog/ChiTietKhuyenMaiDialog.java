/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.ChiTietKhuyenMaiHoaDonBUS;
import BUS.ChiTietKhuyenMaiSanPhamBUS;
import DTO.ChiTietKhuyenMaiHoaDonDTO;
import DTO.ChiTietKhuyenMaiSanPhamDTO;
import DTO.ChuongTrinhKhuyenMaiDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ChiTietKhuyenMaiDialog extends JDialog {

    private final ChuongTrinhKhuyenMaiDTO km;
    private final DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ChiTietKhuyenMaiDialog(Frame parent, boolean modal, ChuongTrinhKhuyenMaiDTO km) {
        super(parent, modal);
        this.km = km;
        initComponents();
    }

    private void initComponents() {
        setTitle("Chi tiết khuyến mãi");
        setSize(900, 620);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(65, 120, 255));
        header.setPreferredSize(new Dimension(100, 50));
        JLabel lblTitle = new JLabel("CHI TIẾT CHƯƠNG TRÌNH KHUYẾN MÃI", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new java.awt.GridLayout(3, 2, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(infoItem("Mã CTKM", safe(km.getMaCTKM())));
        infoPanel.add(infoItem("Tên CTKM", safe(km.getTenCTKM())));
        infoPanel.add(infoItem("Loại khuyến mãi", safe(km.getLoaiKhuyenMai())));
        infoPanel.add(infoItem("Trạng thái", getTrangThaiText(km.getTrangThai())));
        infoPanel.add(infoItem("Ngày bắt đầu", formatDate(km.getNgayBatDau())));
        infoPanel.add(infoItem("Ngày kết thúc", formatDate(km.getNgayKetThuc())));

        JTextArea txtMoTa = new JTextArea(safe(km.getMoTa()));
        txtMoTa.setEditable(false);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setBorder(BorderFactory.createTitledBorder("Mô tả"));

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBackground(Color.WHITE);
        top.add(infoPanel, BorderLayout.NORTH);
        top.add(new JScrollPane(txtMoTa), BorderLayout.CENTER);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab(
                "Chi tiết theo sản phẩm",
                createTablePanel(
                        new String[]{"STT", "Mã sản phẩm", "% giảm", "Số tiền giảm", "SL tối đa"},
                        new ChiTietKhuyenMaiSanPhamBUS(km.getMaCTKM()).getAll(),
                        true
                )
        );
        tabs.addTab(
                "Chi tiết theo hóa đơn",
                createTablePanel(
                        new String[]{"STT", "Giá trị tối thiểu", "% giảm", "Số tiền giảm", "Giảm tối đa"},
                        new ChiTietKhuyenMaiHoaDonBUS(km.getMaCTKM()).getAll(),
                        false
                )
        );

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottom.setBackground(Color.WHITE);
        bottom.add(btnClose);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBackground(Color.WHITE);
        center.add(top, BorderLayout.NORTH);
        center.add(tabs, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel infoItem(String title, String value) {
        JPanel p = new JPanel(new BorderLayout(4, 4));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        p.add(new JLabel(" " + title), BorderLayout.NORTH);
        p.add(new JLabel(" " + value), BorderLayout.CENTER);
        return p;
    }

    private JPanel createTablePanel(String[] headers, List<?> list, boolean sanPhamMode) {
        DefaultTableModel model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        int stt = 1;
        if (list != null) {
            for (Object obj : list) {
                if (sanPhamMode) {
                    ChiTietKhuyenMaiSanPhamDTO x = (ChiTietKhuyenMaiSanPhamDTO) obj;
                    model.addRow(new Object[]{
                        stt++,
                        safe(x.getMaSanPham()),
                        numberText(x.getPhanTramGiam()),
                        numberText(x.getSoTienGiam()),
                        x.getSoLuongToiDa() == null ? "" : x.getSoLuongToiDa()
                    });
                } else {
                    ChiTietKhuyenMaiHoaDonDTO x = (ChiTietKhuyenMaiHoaDonDTO) obj;
                    model.addRow(new Object[]{
                        stt++,
                        numberText(x.getGiaTriToiThieu()),
                        numberText(x.getPhanTramGiam()),
                        numberText(x.getSoTienGiam()),
                        numberText(x.getGiamToiDa())
                    });
                }
            }
        }

        JTable table = new JTable(model);
        table.setRowHeight(34);

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        if (model.getRowCount() == 0) {
            JLabel empty = new JLabel("Chưa có dữ liệu chi tiết.", JLabel.CENTER);
            empty.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            p.add(empty, BorderLayout.NORTH);
        }

        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private String safe(String text) {
        return text == null ? "" : text;
    }

    private String numberText(Number number) {
        return number == null ? "" : String.valueOf(number);
    }

    private String formatDate(LocalDate date) {
        return date == null ? "" : date.format(displayFormat);
    }

    private String getTrangThaiText(int trangThai) {
        return trangThai == 1 ? "Đang diễn ra" : "Đã kết thúc";
    }
}
