package GUI.Panel;

import BUS.ChuongTrinhKhuyenMaiBUS;
import DTO.ChuongTrinhKhuyenMaiDTO;
import GUI.Component.ActionPanel;
import GUI.Component.FilterItem;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Dialog.ChiTietKhuyenMaiDialog;
import GUI.Dialog.KhuyenMaiFormDialog;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import utils.JTableExporterPDF;


public class KhuyenMaiGUI extends JPanel {
    private final ChuongTrinhKhuyenMaiBUS kmBUS = new ChuongTrinhKhuyenMaiBUS();
    private List<ChuongTrinhKhuyenMaiDTO> dsKM = new ArrayList<>();

    private ActionPanel actionPanel;
    private HeaderRightPanel headerRightPanel;
    private FilterItem filterLoai, filterTrangThai;
    private JTable table;
    private DefaultTableModel model;

    public KhuyenMaiGUI() {
        initUI();
        initEvents();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 245));

        actionPanel = new ActionPanel();
        actionPanel.configButtons(new String[]{"add", "update", "delete", "info", "import", "export"});

        headerRightPanel = new HeaderRightPanel();
        headerRightPanel.getCboxSearch().setModel(
                new DefaultComboBoxModel<>(new String[]{"Tất cả", "Mã", "Tên", "Loại", "Trạng thái"})
        );
        btnThuocTinh.setBackground(Color.WHITE);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.add(actionPanel, BorderLayout.WEST);
        top.add(headerRightPanel, BorderLayout.EAST);

        filterLoai = new FilterItem("Loại khuyến mãi");
        filterTrangThai = new FilterItem("Trạng thái");

        JPanel leftBox = new JPanel();
        leftBox.setLayout(new BoxLayout(leftBox, BoxLayout.Y_AXIS));
        leftBox.setBackground(Color.WHITE);
        leftBox.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        leftBox.add(filterLoai);
        leftBox.add(Box.createVerticalStrut(12));
        leftBox.add(filterTrangThai);
        leftBox.add(btnThuocTinh);

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(Color.WHITE);
        left.setPreferredSize(new Dimension(220, 0));
        left.add(leftBox, BorderLayout.NORTH);

        TablePanel tablePanel = new TablePanel(
                "DANH SÁCH THÔNG TIN KHUYẾN MÃI",
                new String[]{"STT", "Mã CTKM", "Tên CTKM", "Loại KM", "Mô tả", "Ngày BD", "Ngày KT", "Trạng thái"}
        );

        table = tablePanel.getTable();
        model = (DefaultTableModel) table.getModel();
        table.setRowHeight(38);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(top, BorderLayout.NORTH);
        add(left, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void initEvents() {
        actionPanel.btnAdd.addActionListener(e ->
         new KhuyenMaiFormDialog(getFrame(), true, this, null, kmBUS).setVisible(true)
        );

        actionPanel.btnUpdate.addActionListener(e -> {
        ChuongTrinhKhuyenMaiDTO km = getSelected();
         if (km == null) {
         JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
         return;
        }
         new KhuyenMaiFormDialog(getFrame(), true, this, km, kmBUS).setVisible(true);
        });

        actionPanel.btnDelete.addActionListener(e -> {
            ChuongTrinhKhuyenMaiDTO km = getSelected();
            if (km == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
                return;
            }

            int c = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa " + km.getMaCTKM() + " không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (c == JOptionPane.YES_OPTION) {
                if (kmBUS.delete(km)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        });

        actionPanel.btnInfo.addActionListener(e -> {
            ChuongTrinhKhuyenMaiDTO km = getSelected();
            if (km == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xem!");
                return;
            }
            new ChiTietKhuyenMaiDialog(getFrame(), true, km).setVisible(true);
        });

        actionPanel.btnImport.addActionListener(e -> importCSV());
        actionPanel.btnExport.addActionListener(e -> exportCSV());
        btnThuocTinh.addActionListener(e -> {
            JTable tableToExport = tblNhaCungCap.getTable();
            JTableExporterPDF.exportJTableToPDF(tableToExport);
        });
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                fillTable();
            }
        });

        headerRightPanel.getCboxSearch().addActionListener(e -> fillTable());
        filterLoai.getComboBox().addActionListener(e -> fillTable());
        filterTrangThai.getComboBox().addActionListener(e -> fillTable());

        headerRightPanel.getBtnReload().addActionListener(e -> {
            headerRightPanel.getTxtSearch().setText("");
            headerRightPanel.getCboxSearch().setSelectedIndex(0);
            filterLoai.getComboBox().setSelectedIndex(0);
            filterTrangThai.getComboBox().setSelectedIndex(0);
            loadData();
        });
    }

    public void loadData() {
        dsKM = kmBUS.getAll();
        if (dsKM == null) dsKM = new ArrayList<>();

        filterLoai.getComboBox().removeAllItems();
        filterLoai.addItem("Tất cả");

        Set<String> loaiSet = new LinkedHashSet<>();
        for (ChuongTrinhKhuyenMaiDTO km : dsKM) {
            if (km.getLoaiKhuyenMai() != null && !km.getLoaiKhuyenMai().trim().isEmpty()) {
                loaiSet.add(km.getLoaiKhuyenMai().trim());
            }
        }
        for (String loai : loaiSet) filterLoai.addItem(loai);

        filterTrangThai.getComboBox().removeAllItems();
        filterTrangThai.addItem("Tất cả");
        filterTrangThai.addItem("Đang diễn ra");
        filterTrangThai.addItem("Đã kết thúc");

        fillTable();
    }
    
    public void refreshData() {
    loadData();
    }
    private void fillTable() {
        model.setRowCount(0);

        String text = headerRightPanel.getTxtSearch().getText().trim().toLowerCase();
        String type = getValue(headerRightPanel.getCboxSearch());
        String loaiLoc = getValue(filterLoai.getComboBox());
        String trangThaiLoc = getValue(filterTrangThai.getComboBox());

        int stt = 1;
        for (ChuongTrinhKhuyenMaiDTO km : dsKM) {
            String ma = safe(km.getMaCTKM()).toLowerCase();
            String ten = safe(km.getTenCTKM()).toLowerCase();
            String loai = safe(km.getLoaiKhuyenMai()).toLowerCase();
            String trangThai = getTrangThai(km.getTrangThai()).toLowerCase();

            boolean okSearch = text.isEmpty()
                    || ("Mã".equals(type) && ma.contains(text))
                    || ("Tên".equals(type) && ten.contains(text))
                    || ("Loại".equals(type) && loai.contains(text))
                    || ("Trạng thái".equals(type) && trangThai.contains(text))
                    || ("Tất cả".equals(type) && (ma.contains(text) || ten.contains(text) || loai.contains(text) || trangThai.contains(text)));

            boolean okLoai = "Tất cả".equals(loaiLoc) || safe(km.getLoaiKhuyenMai()).equalsIgnoreCase(loaiLoc);
            boolean okTrangThai = "Tất cả".equals(trangThaiLoc) || getTrangThai(km.getTrangThai()).equalsIgnoreCase(trangThaiLoc);

            if (okSearch && okLoai && okTrangThai) {
                model.addRow(new Object[]{
                        stt++,
                        km.getMaCTKM(),
                        km.getTenCTKM(),
                        km.getLoaiKhuyenMai(),
                        km.getMoTa(),
                        formatDate(km.getNgayBatDau()),
                        formatDate(km.getNgayKetThuc()),
                        getTrangThai(km.getTrangThai())
                });
            }
        }
    }

    private void exportCSV() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để export!");
            return;
        }

        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File file = fc.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }

        try (BufferedWriter w = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            w.write("Mã CTKM,Tên CTKM,Loại KM,Mô tả,Ngày BD,Ngày KT,Trạng thái");
            w.newLine();

            for (int i = 0; i < model.getRowCount(); i++) {
                w.write(
                        csv(model.getValueAt(i, 1)) + "," +
                        csv(model.getValueAt(i, 2)) + "," +
                        csv(model.getValueAt(i, 3)) + "," +
                        csv(model.getValueAt(i, 4)) + "," +
                        csv(model.getValueAt(i, 5)) + "," +
                        csv(model.getValueAt(i, 6)) + "," +
                        csv(model.getValueAt(i, 7))
                );
                w.newLine();
            }

            JOptionPane.showMessageDialog(this, "Export thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export thất bại!");
        }
    }

    private void importCSV() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File file = fc.getSelectedFile();
        int ok = 0, fail = 0;

        try (BufferedReader r = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;
            boolean first = true;

            while ((line = r.readLine()) != null) {
                line = line.replace("\uFEFF", "").trim();
                if (line.isEmpty()) continue;
                if (first) {
                    first = false;
                    if (line.toLowerCase().contains("mã ctkm")) continue;
                }

                List<String> c = splitCSV(line);
                if (c.size() < 7) {
                    fail++;
                    continue;
                }

                try {
                    ChuongTrinhKhuyenMaiDTO km = new ChuongTrinhKhuyenMaiDTO();
                    km.setTenCTKM(c.get(1).trim());
                    km.setLoaiKhuyenMai(c.get(2).trim());
                    km.setMoTa(c.get(3).trim());
                    km.setNgayBatDau(parseDate(c.get(4).trim()));
                    km.setNgayKetThuc(parseDate(c.get(5).trim()));
                    km.setTrangThai(c.get(6).toLowerCase().contains("đang") ? 1 : 0);

                    if (kmBUS.add(km)) ok++;
                    else fail++;
                } catch (Exception ex) {
                    fail++;
                }
            }

            loadData();
            JOptionPane.showMessageDialog(this, "Import xong!\nThành công: " + ok + "\nThất bại: " + fail);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Import thất bại!");
        }
    }

    private List<String> splitCSV(String line) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuote = false;

        for (char ch : line.toCharArray()) {
            if (ch == '"') inQuote = !inQuote;
            else if (ch == ',' && !inQuote) {
                list.add(sb.toString());
                sb.setLength(0);
            } else sb.append(ch);
        }
        list.add(sb.toString());
        return list;
    }

    private String csv(Object o) {
        String s = o == null ? "" : o.toString();
        if (s.contains(",") || s.contains("\"")) {
            s = "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private ChuongTrinhKhuyenMaiDTO getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;

        String ma = String.valueOf(table.getValueAt(row, 1));
        for (ChuongTrinhKhuyenMaiDTO km : dsKM) {
            if (safe(km.getMaCTKM()).equalsIgnoreCase(ma)) return km;
        }
        return null;
    }

    private Frame getFrame() {
        return (Frame) SwingUtilities.getWindowAncestor(this);
    }

    private String getValue(JComboBox<?> cb) {
        Object o = cb.getSelectedItem();
        return o == null ? "Tất cả" : o.toString();
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String formatDate(LocalDate d) {
        return d == null ? "" : d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            try {
                return LocalDate.parse(s);
            } catch (Exception ex) {
                return null;
            }
        }
    }
    
    public ChuongTrinhKhuyenMaiBUS getKmBUS() {
    return kmBUS;
    }
    
    private String getTrangThai(int t) {
        return t == 1 ? "Đang diễn ra" : "Đã kết thúc";
    }
}
