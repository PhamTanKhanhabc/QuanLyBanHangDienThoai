package GUI.Panel;

import GUI.Component.ButtonToolBar;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;

import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import BUS.PhieuNhapBUS;
import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.Timestamp;

public class PhieuNhap extends JPanel {
    private TablePanel tblPhieuNhap;
    private PhieuNhapBUS bus = new PhieuNhapBUS();

    // Khai báo HeaderRightPanel cho thanh tìm kiếm
    private HeaderRightPanel headerRightPanel;

    // Khai báo các biến bộ lọc ngày tháng ra ngoài để dễ truy cập
    private JDateChooser dateTuNgay;
    private JDateChooser dateDenNgay;

    public PhieuNhap() {
        initUI();
        loadData();
    }

    public void initUI() {
        // --- 1. SETUP LAYOUT TỔNG THỂ ---
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- 2. HEADER PANEL (Chứa Nút chức năng & Thanh tìm kiếm) ---
        JPanel pnlTopBar = new JPanel(new BorderLayout());
        pnlTopBar.setBackground(Color.WHITE);

        // Trái: Chứa các nút thao tác tạo thủ công
        JPanel pnlTopBarLeftSub = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnlTopBarLeftSub.setBackground(Color.WHITE);

        ButtonToolBar btnThem   = new ButtonToolBar("Thêm",    "icon/add.svg",    80, 60, 14, "ADD_PHIEUNHAP");
        ButtonToolBar btnSua    = new ButtonToolBar("Sửa",     "icon/update.svg", 80, 60, 14, "EDIT_PHIEUNHAP");
        ButtonToolBar btnXoa    = new ButtonToolBar("Xóa",     "icon/delete.svg", 80, 60, 14, "DELETE_PHIEUNHAP");
        ButtonToolBar btnInfo   = new ButtonToolBar("Chi tiết","icon/info.svg",   80, 60, 14, "INFO_PHIEUNHAP");
        ButtonToolBar btnImport = new ButtonToolBar("Import",  "icon/import.svg", 80, 60, 14, "IMPORT_PHIEUNHAP");
        ButtonToolBar btnExport = new ButtonToolBar("Export",  "icon/export.svg", 80, 60, 14, "EXPORT_PHIEUNHAP");

        btnThem.setBackground(Color.WHITE);
        btnSua.setBackground(Color.WHITE);
        btnXoa.setBackground(Color.WHITE);
        btnInfo.setBackground(Color.WHITE);
        btnImport.setBackground(Color.WHITE);
        btnExport.setBackground(Color.WHITE);

        pnlTopBarLeftSub.add(btnThem);
        pnlTopBarLeftSub.add(btnSua);
        pnlTopBarLeftSub.add(btnXoa);
        pnlTopBarLeftSub.add(btnInfo);
        pnlTopBarLeftSub.add(btnImport);
        pnlTopBarLeftSub.add(btnExport);

        // Phải: Thanh tìm kiếm dùng HeaderRightPanel
        headerRightPanel = new HeaderRightPanel();

        pnlTopBar.add(pnlTopBarLeftSub, BorderLayout.WEST);
        pnlTopBar.add(headerRightPanel, BorderLayout.EAST);

        // --- 3. MAIN PANEL ---
        JPanel pnlMain = new JPanel(new BorderLayout(10, 0));
        pnlMain.setBackground(Color.WHITE);

        // 3.1. PANEL BỘ LỌC BÊN TRÁI
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setPreferredSize(new Dimension(250, 0));
        pnlLeft.setBackground(Color.WHITE);

        JPanel pnlFilterBox = new JPanel();
        pnlFilterBox.setLayout(new BoxLayout(pnlFilterBox, BoxLayout.Y_AXIS));
        pnlFilterBox.setBackground(Color.WHITE);
        pnlFilterBox.setBorder(new EmptyBorder(20, 10, 10, 10));

        Dimension labelSize = new Dimension(65, 30);

        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setPreferredSize(labelSize);
        dateTuNgay = new JDateChooser();
        dateTuNgay.setDateFormatString("dd/MM/yyyy");
        dateTuNgay.setPreferredSize(new Dimension(140, 25));

        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setPreferredSize(labelSize);
        dateDenNgay = new JDateChooser();
        dateDenNgay.setDateFormatString("dd/MM/yyyy");
        dateDenNgay.setPreferredSize(new Dimension(140, 25));

        ButtonToolBar btnLoc = new ButtonToolBar("Lọc", "icon/filter.svg", 80, 60, 14, "VIEW_PHIEUNHAP");
        btnLoc.setBackground(Color.WHITE);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        row1.setBackground(Color.WHITE);
        row1.add(lblTuNgay);
        row1.add(dateTuNgay);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        row2.setBackground(Color.WHITE);
        row2.add(lblDenNgay);
        row2.add(dateDenNgay);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        row3.setBackground(Color.WHITE);
        row3.add(btnLoc);

        pnlFilterBox.add(row1);
        pnlFilterBox.add(row2);
        pnlFilterBox.add(row3);

        pnlLeft.add(pnlFilterBox, BorderLayout.NORTH);

        String[] headers = {"Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp", "Ngày lập", "Tổng tiền", "Trạng thái"};
        tblPhieuNhap = new TablePanel("DANH SÁCH PHIẾU NHẬP", headers);

        pnlMain.add(pnlLeft, BorderLayout.WEST);
        pnlMain.add(tblPhieuNhap, BorderLayout.CENTER);

        // --- 4. THÊM VÀO PANEL CHÍNH ---
        this.add(pnlTopBar, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);

        // ======================== ACTION CÁC NÚT ========================

        // -------------------- THÊM PHIẾU NHẬP --------------------
        btnThem.addActionListener(e -> {
            GUI.Dialog.ThemPhieuNhap dialog = new GUI.Dialog.ThemPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                true
            );
            dialog.setVisible(true);
            loadData();
        });

        // -------------------- XEM CHI TIẾT PHIẾU NHẬP --------------------
        btnInfo.addActionListener(e -> {
            JTable table = tblPhieuNhap.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một phiếu nhập để xem chi tiết!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNV  = table.getValueAt(selectedRow, 1).toString();
            String maNCC = table.getValueAt(selectedRow, 2).toString();
            String maPHN = table.getValueAt(selectedRow, 0).toString();

            GUI.Dialog.XemPhieuNhap dialog = new GUI.Dialog.XemPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                true,
                maNCC,
                maNV,
                maPHN
            );
            dialog.setVisible(true);
        });

        // -------------------- XÓA PHIẾU NHẬP --------------------
        btnXoa.addActionListener(e -> {
            JTable table = tblPhieuNhap.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một phiếu nhập để xóa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPHN = table.getValueAt(selectedRow, 0).toString();
            String maNV  = table.getValueAt(selectedRow, 1).toString();
            String maNCC = table.getValueAt(selectedRow, 2).toString();

            GUI.Dialog.XoaPhieuNhap dialog = new GUI.Dialog.XoaPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                true,
                maPHN,
                maNCC,
                maNV
            );
            dialog.setVisible(true);
            loadData();
        });

        // -------------------- SỬA PHIẾU NHẬP --------------------
        btnSua.addActionListener(e -> {
            JTable table = tblPhieuNhap.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một phiếu nhập để sửa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPHN = table.getValueAt(selectedRow, 0).toString();
            String maNV  = table.getValueAt(selectedRow, 1).toString();
            String maNCC = table.getValueAt(selectedRow, 2).toString();

            GUI.Dialog.SuaPhieuNhap dialog = new GUI.Dialog.SuaPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                true,
                maPHN,
                maNCC,
                maNV
            );
            dialog.setVisible(true);
            loadData();
        });

        // -------------------- BỘ LỌC NGÀY THÁNG --------------------
        btnLoc.addActionListener(e -> {
            java.util.Date tuNgay  = dateTuNgay.getDate();
            java.util.Date denNgay = dateDenNgay.getDate();

            if (tuNgay != null && denNgay != null && tuNgay.after(denNgay)) {
                JOptionPane.showMessageDialog(this,
                    "'Từ ngày' không thể lớn hơn 'Đến ngày'!",
                    "Lỗi bộ lọc", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<PhieuNhapDTO> filteredList = bus.getByDate(tuNgay, denNgay);
            fillTable(filteredList);
        });

        // -------------------- NHẬP EXCEL (IMPORT) --------------------
        btnImport.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn file Excel để Import");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
                fileChooser.setFileFilter(filter);

                int userSelection = fileChooser.showOpenDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fileChooser.getSelectedFile();

                    FileInputStream fis = new FileInputStream(fileToOpen);
                    Workbook workbook = new XSSFWorkbook(fis);
                    Sheet sheet = workbook.getSheetAt(0);

                    int countSuccess = 0;
                    int countError   = 0;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) continue;

                        try {
                            DataFormatter formatter = new DataFormatter();
                            String maNV  = formatter.formatCellValue(row.getCell(1)).trim();
                            String maNCC = formatter.formatCellValue(row.getCell(2)).trim();

                            Cell cellNgay = row.getCell(3);
                            Timestamp ngayLap = new Timestamp(System.currentTimeMillis());
                            if (cellNgay != null) {
                                if (DateUtil.isCellDateFormatted(cellNgay)) {
                                    ngayLap = new Timestamp(cellNgay.getDateCellValue().getTime());
                                } else {
                                    try {
                                        java.util.Date parsedDate = sdf.parse(formatter.formatCellValue(cellNgay));
                                        ngayLap = new Timestamp(parsedDate.getTime());
                                    } catch (Exception ex) { /* giữ nguyên ngayLap hiện tại */ }
                                }
                            }

                            double tongTien = 0;
                            Cell cellTien = row.getCell(4);
                            if (cellTien != null) {
                                if (cellTien.getCellType() == CellType.NUMERIC) {
                                    tongTien = cellTien.getNumericCellValue();
                                } else {
                                    String tienStr = formatter.formatCellValue(cellTien).replaceAll("[^\\d.]", "");
                                    if (!tienStr.isEmpty()) tongTien = Double.parseDouble(tienStr);
                                }
                            }

                            if (maNV.isEmpty() || maNCC.isEmpty()) continue;

                            PhieuNhapDTO pn = new PhieuNhapDTO("", maNV, maNCC, ngayLap, tongTien, 1);

                            if (bus.add(pn)) {
                                countSuccess++;
                            } else {
                                countError++;
                            }
                        } catch (Exception ex) {
                            countError++;
                        }
                    }

                    workbook.close();
                    fis.close();

                    loadData();

                    String message = String.format(
                        "Import hoàn tất!\n- Thành công: %d phiếu\n- Lỗi/Bỏ qua: %d phiếu\n\n"
                        + "*Lưu ý: Phiếu nhập mới chưa có sản phẩm, hãy bấm 'Sửa' để thêm chi tiết.",
                        countSuccess, countError
                    );
                    JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Lỗi đọc file Excel: Đảm bảo file đúng định dạng .xlsx",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // -------------------- XUẤT EXCEL (EXPORT) --------------------
        btnExport.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
                fileChooser.setFileFilter(filter);

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    if (!filePath.endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }

                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("PhieuNhap");

                    Row headerRow = sheet.createRow(0);
                    String[] headersExcel = {
                        "Mã Phiếu Nhập", "Mã Nhân Viên", "Mã Nhà Cung Cấp",
                        "Ngày Lập", "Tổng Tiền", "Trạng Thái"
                    };
                    for (int i = 0; i < headersExcel.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headersExcel[i]);
                    }

                    ArrayList<PhieuNhapDTO> listPN = bus.getAll();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    int rowNum = 1;
                    if (listPN != null) {
                        for (PhieuNhapDTO pn : listPN) {
                            Row row = sheet.createRow(rowNum++);
                            row.createCell(0).setCellValue(pn.getMaPHN());
                            row.createCell(1).setCellValue(pn.getMaNV());
                            row.createCell(2).setCellValue(pn.getMaNCC());
                            row.createCell(3).setCellValue(sdf.format(pn.getNgay()));
                            row.createCell(4).setCellValue(pn.getTongTien());
                            row.createCell(5).setCellValue(pn.getTrangThai() == 1 ? "Hoàn thành" : "Đã hủy");
                        }
                    }

                    for (int i = 0; i < headersExcel.length; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    try (FileOutputStream out = new FileOutputStream(filePath)) {
                        workbook.write(out);
                    }
                    workbook.close();

                    JOptionPane.showMessageDialog(this,
                        "Xuất file Excel thành công!\n" + filePath,
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi xuất file Excel: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ==================== TÌM KIẾM (SEARCH) VÀ LÀM MỚI (RELOAD) ====================
        JTextField txtSearch   = headerRightPanel.getTxtSearch();
        JComboBox<String> cbxSearch = headerRightPanel.getCboxSearch();
        JButton btnReload      = headerRightPanel.getBtnReload();

        // Ép kiểu ComboBox riêng cho PhieuNhap
        cbxSearch.setModel(new DefaultComboBoxModel<>(new String[]{
            "Tất cả", "Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp"
        }));

        // Bắt sự kiện gõ phím — gọi executeSearch() mỗi khi nội dung thay đổi
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate (javax.swing.event.DocumentEvent e) { executeSearch(); }
            @Override public void removeUpdate (javax.swing.event.DocumentEvent e) { executeSearch(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { executeSearch(); }
        });

        // Bắt sự kiện đổi tiêu chí tìm kiếm
        cbxSearch.addActionListener(e -> executeSearch());

        // Nút Reload: xóa tìm kiếm và bộ lọc ngày, tải lại toàn bộ dữ liệu
        btnReload.addActionListener(e -> {
            txtSearch.setText("");
            cbxSearch.setSelectedIndex(0);
            dateTuNgay.setDate(null);
            dateDenNgay.setDate(null);
            loadData();
        });
    }

    /**
     * Hàm thực thi tìm kiếm.
     * ĐÃ SỬA LỖI: truyền đúng 2 tham số (text, type) khớp với
     * chữ ký search(String text, String type) trong PhieuNhapBUS.
     */
    private void executeSearch() {
        String text = headerRightPanel.getTxtSearch().getText().trim();
        String type = headerRightPanel.getCboxSearch().getSelectedItem().toString();

        // Gọi search với 2 tham số — PhieuNhapBUS.search(String, String)
        ArrayList<PhieuNhapDTO> resultList = bus.search(text, type);
        fillTable(resultList);
    }

    // Hàm đổ dữ liệu tổng quát lên bảng để tái sử dụng
    private void fillTable(ArrayList<PhieuNhapDTO> list) {
        if (list == null) return;

        Object[][] data = new Object[list.size()][6];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DecimalFormat df = new DecimalFormat("#,###.##");

        for (int i = 0; i < list.size(); i++) {
            PhieuNhapDTO pn = list.get(i);
            data[i][0] = pn.getMaPHN();
            data[i][1] = pn.getMaNV();
            data[i][2] = pn.getMaNCC();
            data[i][3] = sdf.format(pn.getNgay());
            data[i][4] = df.format(pn.getTongTien());
            data[i][5] = pn.getTrangThai() == 1 ? "Hoàn thành" : "Đã hủy";
        }

        tblPhieuNhap.setData(data);
    }

    public void loadData() {
        bus = new PhieuNhapBUS(); // Khởi tạo lại BUS để lấy dữ liệu mới nhất
        ArrayList<PhieuNhapDTO> list = bus.getAll();
        fillTable(list);
    }
}
