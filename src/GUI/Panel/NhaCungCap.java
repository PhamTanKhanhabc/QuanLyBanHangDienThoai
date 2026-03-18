package GUI.Panel;

import GUI.Component.ButtonToolBar;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

// Thư viện xử lý File và Excel (Apache POI)
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhaCungCap extends JPanel {
    private NhaCungCapBUS bus = new NhaCungCapBUS();
    private TablePanel tblNhaCungCap;
    
    // Khai báo HeaderRightPanel cho thanh tìm kiếm
    private HeaderRightPanel headerRightPanel;

    public NhaCungCap() {
        initUI();
        loadData();
    }

    private void initUI() {
        // --- 1. SETUP LAYOUT TỔNG THỂ ---
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // --- 2. HEADER PANEL (Chứa Nút chức năng & Thanh tìm kiếm) ---
        JPanel pnlTopBar = new JPanel(new BorderLayout());
        pnlTopBar.setBackground(Color.WHITE);
        
        JPanel pnlTopBarLeftSub = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnlTopBarLeftSub.setBackground(Color.WHITE);
        
        ButtonToolBar btnThem = new ButtonToolBar("Thêm", "icon/add.svg", 80, 60, 14, "ADD_NHACUNGCAP");
        ButtonToolBar btnSua = new ButtonToolBar("Sửa", "icon/update.svg", 80, 60, 14, "EDIT_NHACUNGCAP");
        ButtonToolBar btnXoa = new ButtonToolBar("Xóa", "icon/delete.svg", 80, 60, 14, "DELETE_NHACUNGCAP");
        ButtonToolBar btnInfo = new ButtonToolBar("Chi tiết", "icon/info.svg", 80, 60, 14, "INFO_NHACUNGCAP");
        ButtonToolBar btnImport = new ButtonToolBar("Nhập file", "icon/import.svg", 80, 60, 14, "IMPORT_NHACUNGCAP");
        ButtonToolBar btnExport = new ButtonToolBar("Xuất file", "icon/export.svg", 80, 60, 14, "EXPORT_NHACUNGCAP");
        
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
        
        headerRightPanel = new HeaderRightPanel();
        
        pnlTopBar.add(pnlTopBarLeftSub, BorderLayout.WEST);
        pnlTopBar.add(headerRightPanel, BorderLayout.EAST);
        
        // --- 3. MAIN PANEL (Chứa Bảng dữ liệu) ---
        JPanel pnlMain = new JPanel(new BorderLayout(10, 0));
        pnlMain.setBackground(Color.WHITE);
        
        String[] headers = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ"};
        tblNhaCungCap = new TablePanel("DANH SÁCH NHÀ CUNG CẤP", headers);
        tblNhaCungCap.getTable().getTableHeader().setReorderingAllowed(false);
        
        pnlMain.add(tblNhaCungCap, BorderLayout.CENTER);
        
        // --- 4. THÊM VÀO PANEL CHÍNH ---
        this.add(pnlTopBar, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
        
        // ======================== ACTION CÁC NÚT CƠ BẢN ========================
        
        // --- Thêm ---
        btnThem.addActionListener(e -> {
            GUI.Dialog.ThemNhaCungCapDialog dialog = new GUI.Dialog.ThemNhaCungCapDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true
            );
            dialog.setVisible(true);
            loadData();
        });

        // --- Sửa ---
        btnSua.addActionListener(e -> {
            JTable table = tblNhaCungCap.getTable();
            int row = table.getSelectedRow();
            
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String ma = table.getValueAt(row, 0).toString();
            String ten = table.getValueAt(row, 1).toString();
            String sdt = table.getValueAt(row, 2).toString();
            String diaChi = table.getValueAt(row, 3).toString();

            GUI.Dialog.SuaNhaCungCapDialog dialog = new GUI.Dialog.SuaNhaCungCapDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), true, ma, ten, sdt, diaChi
            );
            dialog.setVisible(true);
            
            loadData();
        });

        // --- Xem Chi Tiết ---
        btnInfo.addActionListener(e -> {
            JTable table = tblNhaCungCap.getTable();
            int row = table.getSelectedRow();
            
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String ma = table.getValueAt(row, 0).toString();
            String ten = table.getValueAt(row, 1).toString();
            String sdt = table.getValueAt(row, 2).toString();
            String diaChi = table.getValueAt(row, 3).toString();

            GUI.Dialog.XemNhaCungCapDialog dialog = new GUI.Dialog.XemNhaCungCapDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), true, ma, ten, sdt, diaChi
            );
            dialog.setVisible(true);
        });

        // --- Xóa ---
        btnXoa.addActionListener(e -> {
            JTable table = tblNhaCungCap.getTable();
            int row = table.getSelectedRow();
            
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String ma = table.getValueAt(row, 0).toString();
            String ten = table.getValueAt(row, 1).toString();
            String sdt = table.getValueAt(row, 2).toString();
            String diaChi = table.getValueAt(row, 3).toString();

            GUI.Dialog.XoaNhaCungCapDialog dialog = new GUI.Dialog.XoaNhaCungCapDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), true, ma, ten, sdt, diaChi
            );
            dialog.setVisible(true);
            loadData();
        });
        
        // --- Nhập Excel (Import) ---
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

                    NhaCungCapBUS nccBUS = new NhaCungCapBUS();
                    int countSuccess = 0;
                    int countError = 0;

                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) continue; 

                        try {
                            Cell cellMa = row.getCell(0);
                            Cell cellTen = row.getCell(1);
                            Cell cellSDT = row.getCell(2);
                            Cell cellDiaChi = row.getCell(3);

                            DataFormatter formatter = new DataFormatter();
                            String maNCC = formatter.formatCellValue(cellMa).trim();
                            String tenNCC = formatter.formatCellValue(cellTen).trim();
                            String sdt = formatter.formatCellValue(cellSDT).trim();
                            String diaChi = formatter.formatCellValue(cellDiaChi).trim();

                            if (maNCC.isEmpty()) continue;

                            NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, sdt, diaChi, 1);
                            
                            if (nccBUS.add(ncc)) { 
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

                    String message = String.format("Import hoàn tất!\n- Thành công: %d dòng\n- Thất bại / Trùng mã: %d dòng", countSuccess, countError);
                    JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi đọc file Excel: Đảm bảo file đúng định dạng .xlsx", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Xuất Excel (Export) ---
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
                    Sheet sheet = workbook.createSheet("NhaCungCap");

                    Row headerRow = sheet.createRow(0);
                    String[] headersExcel = {"Mã NCC", "Tên Nhà Cung Cấp", "Số Điện Thoại", "Địa Chỉ"};
                    for (int i = 0; i < headersExcel.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headersExcel[i]);
                    }

                    NhaCungCapBUS nccBUS = new NhaCungCapBUS();
                    ArrayList<NhaCungCapDTO> listNCC = nccBUS.getAll();

                    int rowNum = 1;
                    if (listNCC != null) {
                        for (NhaCungCapDTO ncc : listNCC) {
                            Row row = sheet.createRow(rowNum++);
                            row.createCell(0).setCellValue(ncc.getMaNCC());
                            row.createCell(1).setCellValue(ncc.getTenNCC());
                            row.createCell(2).setCellValue(ncc.getSDT());
                            row.createCell(3).setCellValue(ncc.getDiaChi());
                        }
                    }

                    for (int i = 0; i < headersExcel.length; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    try (FileOutputStream out = new FileOutputStream(filePath)) {
                        workbook.write(out);
                    }
                    workbook.close();

                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!\n" + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ======================== TÌM KIẾM & LÀM MỚI ========================
        
        JTextField txtSearch = headerRightPanel.getTxtSearch();
        JComboBox<String> cboxSearch = headerRightPanel.getCboxSearch();
        JButton btnReload = headerRightPanel.getBtnReload();

        // 1. Gõ phím tới đâu lọc tới đó (Real-time search)
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchData();
            }
        });

        // 2. Đổi tiêu chí tìm kiếm ở ComboBox -> Tự động lọc lại
        cboxSearch.addActionListener(e -> {
            searchData();
        });

        // 3. Nút Làm Mới (Refresh) -> Ép tải lại toàn bộ Database mới nhất
        btnReload.addActionListener(e -> {
            loadData(); // Trong file HeaderRightPanel đã code sẵn chức năng xóa trắng Textbox và reset ComboBox rồi.
        });
    }
    
    // ======================== HÀM XỬ LÝ DỮ LIỆU ========================

    // Hàm load toàn bộ dữ liệu (có ép đọc lại Database)
    public void loadData() {
        bus = new NhaCungCapBUS(); 
        
        ArrayList<NhaCungCapDTO> list = bus.getAll();
        
        if (list == null) return;
        
        Object[][] data = new Object[list.size()][4];
        
        for (int i = 0; i < list.size(); i++) {
            NhaCungCapDTO ncc = list.get(i);

            data[i][0] = ncc.getMaNCC();       
            data[i][1] = ncc.getTenNCC();
            data[i][2] = ncc.getSDT();              
            data[i][3] = ncc.getDiaChi();
        }
        tblNhaCungCap.setData(data);
    }

    // Hàm lọc dữ liệu nội bộ dựa vào ô tìm kiếm
    public void searchData() {
        // Lấy giá trị từ các ô nhập liệu của HeaderRightPanel
        String text = headerRightPanel.getTxtSearch().getText().trim().toLowerCase();
        String type = headerRightPanel.getCboxSearch().getSelectedItem().toString();
        
        // Lấy list gốc
        ArrayList<NhaCungCapDTO> listAll = bus.getAll();
        ArrayList<NhaCungCapDTO> listFilter = new ArrayList<>();
        
        if (listAll != null) {
            for (NhaCungCapDTO ncc : listAll) {
                boolean match = false;
                
                // Tiêu chí lọc dựa theo 3 Option được quy định sẵn ở HeaderRightPanel ("Tất cả", "Mã", "Tên")
                if (type.equals("Tất cả")) {
                    if (ncc.getMaNCC().toLowerCase().contains(text) || ncc.getTenNCC().toLowerCase().contains(text)) {
                        match = true;
                    }
                } else if (type.equals("Mã")) {
                    if (ncc.getMaNCC().toLowerCase().contains(text)) {
                        match = true;
                    }
                } else if (type.equals("Tên")) {
                    if (ncc.getTenNCC().toLowerCase().contains(text)) {
                        match = true;
                    }
                }
                
                if (match) {
                    listFilter.add(ncc);
                }
            }
        }
        
        // Đổ mảng dữ liệu đã lọc lên bảng
        Object[][] data = new Object[listFilter.size()][4];
        for (int i = 0; i < listFilter.size(); i++) {
            NhaCungCapDTO ncc = listFilter.get(i);
            data[i][0] = ncc.getMaNCC();       
            data[i][1] = ncc.getTenNCC();
            data[i][2] = ncc.getSDT();              
            data[i][3] = ncc.getDiaChi();
        }
        tblNhaCungCap.setData(data);
    }
}