/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.ChuongTrinhKhuyenMaiBUS;
import DTO.ChuongTrinhKhuyenMaiDTO;
import GUI.Component.ActionPanel;
import GUI.Component.FilterItem;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Dialog.ChiTietKhuyenMaiDialog;
import GUI.Dialog.KhuyenMaiFormDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class KhuyenMaiGUI extends JPanel {

    private final ChuongTrinhKhuyenMaiBUS kmBUS = new ChuongTrinhKhuyenMaiBUS();
    private List<ChuongTrinhKhuyenMaiDTO> allData = new ArrayList<>();

    private ActionPanel actionPanel;
    private HeaderRightPanel headerRightPanel;
    private FilterItem filterLoai;
    private FilterItem filterTrangThai;

    private JTable table;
    private DefaultTableModel model;

    private boolean updatingFilters = false;
    private final DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public KhuyenMaiGUI() {
        initComponents();
        initEvents();
        refreshData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 245));
        setPreferredSize(new Dimension(1130, 800));

        actionPanel = new ActionPanel();
        actionPanel.configButtons(new String[]{"add", "update", "delete", "info"});

        headerRightPanel = new HeaderRightPanel();
        headerRightPanel.getCboxSearch().setModel(new DefaultComboBoxModel<>(
                new String[]{"Tất cả", "Mã", "Tên", "Loại", "Trạng thái"}
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(actionPanel, BorderLayout.WEST);
        headerPanel.add(headerRightPanel, BorderLayout.EAST);

        filterLoai = new FilterItem("Loại khuyến mãi");
        filterTrangThai = new FilterItem("Trạng thái");

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new javax.swing.BoxLayout(filterPanel, javax.swing.BoxLayout.Y_AXIS));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setPreferredSize(new Dimension(220, 0));
        filterPanel.add(filterLoai);
        filterPanel.add(filterTrangThai);

        TablePanel tablePanel = new TablePanel(
                "Danh sách thông tin khuyến mãi",
                new String[]{"STT", "Mã CTKM", "Tên CTKM", "Loại KM", "Mô tả", "Ngày BD", "Ngày KT", "Trạng thái"}
        );

        table = tablePanel.getTable();
        model = (DefaultTableModel) table.getModel();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(38);

        add(headerPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void initEvents() {
        actionPanel.btnAdd.addActionListener(e ->
                new KhuyenMaiFormDialog(getParentFrame(), true, this, null).setVisible(true)
        );

        actionPanel.btnUpdate.addActionListener(e -> {
            ChuongTrinhKhuyenMaiDTO current = getSelectedKhuyenMai();
            if (current == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
                return;
            }
            new KhuyenMaiFormDialog(getParentFrame(), true, this, current).setVisible(true);
        });

        actionPanel.btnDelete.addActionListener(e -> handleDelete());

        actionPanel.btnInfo.addActionListener(e -> {
            ChuongTrinhKhuyenMaiDTO current = getSelectedKhuyenMai();
            if (current == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xem!");
                return;
            }
            new ChiTietKhuyenMaiDialog(getParentFrame(), true, current).setVisible(true);
        });

        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilter();
            }
        });

        headerRightPanel.getCboxSearch().addActionListener(e -> applyFilter());

        headerRightPanel.getBtnReload().addActionListener(e -> {
            headerRightPanel.getTxtSearch().setText("");
            if (headerRightPanel.getCboxSearch().getItemCount() > 0) {
                headerRightPanel.getCboxSearch().setSelectedIndex(0);
            }
            refreshData();
        });

        filterLoai.getComboBox().addActionListener(e -> {
            if (!updatingFilters) {
                applyFilter();
            }
        });

        filterTrangThai.getComboBox().addActionListener(e -> {
            if (!updatingFilters) {
                applyFilter();
            }
        });
    }

    public void refreshData() {
        List<ChuongTrinhKhuyenMaiDTO> data = kmBUS.getAll();
        allData = data == null ? new ArrayList<>() : new ArrayList<>(data);
        reloadFilterData();
        applyFilter();
    }

    private void reloadFilterData() {
        updatingFilters = true;
        try {
            String selectedLoai = getSelectedValue(filterLoai.getComboBox(), "Tất cả");
            String selectedTrangThai = getSelectedValue(filterTrangThai.getComboBox(), "Tất cả");

            filterLoai.getComboBox().removeAllItems();
            filterLoai.addItem("Tất cả");

            Set<String> loaiSet = new LinkedHashSet<>();
            for (ChuongTrinhKhuyenMaiDTO km : allData) {
                String loai = safe(km.getLoaiKhuyenMai()).trim();
                if (!loai.isEmpty()) {
                    loaiSet.add(loai);
                }
            }
            for (String loai : loaiSet) {
                filterLoai.addItem(loai);
            }

            filterTrangThai.getComboBox().removeAllItems();
            filterTrangThai.addItem("Tất cả");
            filterTrangThai.addItem("Đang diễn ra");
            filterTrangThai.addItem("Đã kết thúc");

            setSelectedSafe(filterLoai.getComboBox(), selectedLoai);
            setSelectedSafe(filterTrangThai.getComboBox(), selectedTrangThai);
        } finally {
            updatingFilters = false;
        }
    }

    private void applyFilter() {
        String keyword = headerRightPanel.getTxtSearch().getText().trim().toLowerCase(Locale.ROOT);
        String searchType = getSelectedValue(headerRightPanel.getCboxSearch(), "Tất cả");
        String loai = getSelectedValue(filterLoai.getComboBox(), "Tất cả");
        String trangThai = getSelectedValue(filterTrangThai.getComboBox(), "Tất cả");

        model.setRowCount(0);
        int stt = 1;

        for (ChuongTrinhKhuyenMaiDTO km : allData) {
            if (!matchSearch(km, keyword, searchType)) {
                continue;
            }
            if (!"Tất cả".equals(loai) && !safe(km.getLoaiKhuyenMai()).equalsIgnoreCase(loai)) {
                continue;
            }
            if (!"Tất cả".equals(trangThai) && !getTrangThaiText(km.getTrangThai()).equalsIgnoreCase(trangThai)) {
                continue;
            }

            model.addRow(new Object[]{
                stt++,
                safe(km.getMaCTKM()),
                safe(km.getTenCTKM()),
                safe(km.getLoaiKhuyenMai()),
                safe(km.getMoTa()),
                formatDate(km.getNgayBatDau()),
                formatDate(km.getNgayKetThuc()),
                getTrangThaiText(km.getTrangThai())
            });
        }
    }

    private boolean matchSearch(ChuongTrinhKhuyenMaiDTO km, String keyword, String type) {
        if (keyword.isEmpty()) {
            return true;
        }

        String ma = safe(km.getMaCTKM()).toLowerCase(Locale.ROOT);
        String ten = safe(km.getTenCTKM()).toLowerCase(Locale.ROOT);
        String loai = safe(km.getLoaiKhuyenMai()).toLowerCase(Locale.ROOT);
        String trangThai = getTrangThaiText(km.getTrangThai()).toLowerCase(Locale.ROOT);

        switch (type) {
            case "Mã":
                return ma.contains(keyword);
            case "Tên":
                return ten.contains(keyword);
            case "Loại":
                return loai.contains(keyword);
            case "Trạng thái":
                return trangThai.contains(keyword);
            default:
                return ma.contains(keyword)
                        || ten.contains(keyword)
                        || loai.contains(keyword)
                        || trangThai.contains(keyword);
        }
    }

    private void handleDelete() {
        ChuongTrinhKhuyenMaiDTO current = getSelectedKhuyenMai();
        if (current == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa khuyến mãi " + current.getMaCTKM() + " không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (kmBUS.delete(current)) {
            JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
            refreshData();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại! Có thể dữ liệu đang được liên kết.");
        }
    }

    private ChuongTrinhKhuyenMaiDTO getSelectedKhuyenMai() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }

        String ma = String.valueOf(table.getValueAt(row, 1));
        for (ChuongTrinhKhuyenMaiDTO km : allData) {
            if (safe(km.getMaCTKM()).equalsIgnoreCase(ma)) {
                return km;
            }
        }
        return null;
    }

    private String getSelectedValue(javax.swing.JComboBox<?> comboBox, String defaultValue) {
        Object selected = comboBox == null ? null : comboBox.getSelectedItem();
        return selected == null ? defaultValue : selected.toString();
    }

    private void setSelectedSafe(javax.swing.JComboBox<?> comboBox, String value) {
        if (comboBox == null || comboBox.getItemCount() == 0) {
            return;
        }

        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            if (item != null && item.toString().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
        comboBox.setSelectedIndex(0);
    }

    private Frame getParentFrame() {
        return (Frame) SwingUtilities.getWindowAncestor(this);
    }

    private String safe(String text) {
        return text == null ? "" : text;
    }

    private String formatDate(LocalDate date) {
        return date == null ? "" : date.format(displayFormat);
    }

    private String getTrangThaiText(int trangThai) {
        return trangThai == 1 ? "Đang diễn ra" : "Đã kết thúc";
    }
}
