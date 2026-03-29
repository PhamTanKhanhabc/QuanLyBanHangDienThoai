package GUI.Panel;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;
import GUI.Component.ButtonToolBar;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import DTO.SanPhamDTO;
import DTO.ChiTietHoaDonDTO;
import BUS.ChiTietHoaDonBUS;
import BUS.SanPhamBUS;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HoaDonPanel extends JPanel {
    private HoaDonBUS hdBUS = new HoaDonBUS();
    private TablePanel tblHoaDon;
    
    // Khai báo HeaderRightPanel cho thanh tìm kiếm
    private HeaderRightPanel headerRightPanel;

    private DecimalFormat df = new DecimalFormat("#,### VNĐ");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public HoaDonPanel() {
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
        
        // Sử dụng ButtonToolBar giống hệt form Nhà Cung Cấp
        ButtonToolBar btnThem = new ButtonToolBar("Thêm", "icon/add.svg", 80, 60, 14, "ADD_HOADON");
        ButtonToolBar btnSua = new ButtonToolBar("Sửa", "icon/update.svg", 80, 60, 14, "EDIT_HOADON");
        ButtonToolBar btnXoa = new ButtonToolBar("Xóa", "icon/delete.svg", 80, 60, 14, "DELETE_HOADON");
        ButtonToolBar btnInfo = new ButtonToolBar("Info", "icon/info.svg", 80, 60, 14, "INFO_HOADON");
        ButtonToolBar btnExport = new ButtonToolBar("Export", "icon/export.svg", 80, 60, 14, "EXPORT_HOADON");
        
        btnThem.setBackground(Color.WHITE);
        btnSua.setBackground(Color.WHITE);
        btnXoa.setBackground(Color.WHITE);
        btnInfo.setBackground(Color.WHITE);
        btnExport.setBackground(Color.WHITE);
        
        pnlTopBarLeftSub.add(btnThem);
        pnlTopBarLeftSub.add(btnSua);
        pnlTopBarLeftSub.add(btnXoa);
        pnlTopBarLeftSub.add(btnInfo);
        pnlTopBarLeftSub.add(btnExport);
        
        // Cài đặt HeaderRightPanel (Thanh tìm kiếm)
        headerRightPanel = new HeaderRightPanel();
        
        // Thiết lập các item cho ComboBox tìm kiếm (Tùy chỉnh lại tùy theo HeaderRightPanel của bạn)
        JComboBox<String> cboxSearch = headerRightPanel.getCboxSearch();
        cboxSearch.removeAllItems();
        cboxSearch.addItem("Tất cả");
        cboxSearch.addItem("Mã HĐ");
        cboxSearch.addItem("Mã KH");
        cboxSearch.addItem("Mã NV");

        pnlTopBar.add(pnlTopBarLeftSub, BorderLayout.WEST);
        pnlTopBar.add(headerRightPanel, BorderLayout.EAST);
        
        // --- 3. MAIN PANEL (Chứa Bảng dữ liệu) ---
        JPanel pnlMain = new JPanel(new BorderLayout(10, 0));
        pnlMain.setBackground(Color.WHITE);
        
        String[] headers = {"STT", "Mã Hóa Đơn", "Mã NV", "Mã KH", "Ngày Lập", "Tổng Tiền"};
        tblHoaDon = new TablePanel("DANH SÁCH HÓA ĐƠN", headers);
        tblHoaDon.getTable().getTableHeader().setReorderingAllowed(false);
        
        pnlMain.add(tblHoaDon, BorderLayout.CENTER);
        
        // --- 4. THÊM VÀO PANEL CHÍNH ---
        this.add(pnlTopBar, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
        
        // ======================== ACTION CÁC NÚT CƠ BẢN ========================
        
        btnThem.addActionListener(e -> {
            GUI.Dialog.CreateHoaDonDialog dialog = new GUI.Dialog.CreateHoaDonDialog(
        (JFrame) SwingUtilities.getWindowAncestor(this), 
        true // Đặt là true để bắt buộc người dùng thao tác xong mới quay lại form chính
    );
    dialog.setVisible(true);
    
    // Refresh lại bảng Hóa Đơn sau khi thanh toán xong
    loadData();
        });

        btnSua.addActionListener(e -> {
    JTable table = tblHoaDon.getTable();
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Lấy Mã HD từ bảng danh sách (thường là cột số 1)
    String maHD = table.getValueAt(row, 1).toString();
    
    // Mở Dialog cập nhật và truyền mã HD vào
    GUI.Dialog.UpdateHoaDonDialog dialog = new GUI.Dialog.UpdateHoaDonDialog(
        (JFrame) SwingUtilities.getWindowAncestor(this), 
        true, 
        maHD 
    );
    dialog.setVisible(true);
    
    // Cập nhật lại danh sách sau khi đóng form sửa
    loadData();
});

        btnInfo.addActionListener(e -> {
            JTable table = tblHoaDon.getTable();
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Cột 1 chứa Mã Hóa Đơn
            String maHD = table.getValueAt(row, 1).toString();
            
            // Gọi form Chi Tiết
            GUI.Dialog.ChiTietHoaDonDialog dialog = new GUI.Dialog.ChiTietHoaDonDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                maHD
            );
            dialog.setVisible(true);
        });

        btnXoa.addActionListener(e -> {
            JTable table = tblHoaDon.getTable();
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Lấy mã Hóa Đơn từ dòng được chọn (Cột 1 là Mã HD)
            String maHD = table.getValueAt(row, 1).toString();
            
            // 1. Hỏi xác nhận
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Bạn có chắc chắn muốn xóa hóa đơn " + maHD + " không?\nLưu ý: Số lượng sản phẩm của hóa đơn này sẽ được hoàn trả lại vào kho.", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();
                SanPhamBUS spBUS = new SanPhamBUS();
                
                // 2. Phục hồi tồn kho trước khi xóa
                ArrayList<ChiTietHoaDonDTO> listCT = cthdBUS.getAllByMaHD(maHD);
                if (listCT != null) {
                    for (ChiTietHoaDonDTO ct : listCT) {
                        int indexSP = spBUS.getIndexById(ct.getMaSP());
                        if (indexSP != -1) {
                            SanPhamDTO sp = spBUS.getByIndex(indexSP);
                            // Cộng trả lại kho
                            sp.setSoLuongTon(sp.getSoLuongTon() + ct.getSoLuong()); 
                            spBUS.update(sp);
                        }
                    }
                    // 3. Xóa toàn bộ Chi tiết hóa đơn trong DB
                    cthdBUS.deleteByMaHD(maHD);
                }
                
                // 4. Xóa Hóa đơn (Chạy hàm Soft Delete UPDATE TrangThai = 0 của bạn)
                int indexHD = hdBUS.getIndexById(maHD);
                if (indexHD != -1) {
                    HoaDonDTO hdXoa = hdBUS.getByIndex(indexHD);
                    
                    if (hdBUS.delete(hdXoa)) {
                        JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                        // Tải lại dữ liệu lên bảng
                        loadData(); 
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn từ Cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        

        btnExport.addActionListener(e -> {
            try {
                // 1. Mở cửa sổ chọn nơi lưu file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn nơi lưu file Hóa Đơn");
                
                // Chỉ cho phép lưu định dạng Excel
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File (.xlsx)", "xlsx");
                fileChooser.setFileFilter(filter);
                
                int userSelection = fileChooser.showSaveDialog(this);
                
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    
                    // Tự động thêm đuôi .xlsx nếu người dùng quên gõ
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }
                    
                    // 2. Tạo Workbook và Sheet bằng Apache POI
                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("DanhSachHoaDon");
                    
                    JTable table = tblHoaDon.getTable();
                    
                    // 3. Tạo dòng tiêu đề (Header)
                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        headerRow.createCell(i).setCellValue(table.getColumnName(i));
                    }
                    
                    // 4. Đổ dữ liệu từ bảng giao diện vào file Excel
                    for (int i = 0; i < table.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            Object value = table.getValueAt(i, j);
                            row.createCell(j).setCellValue(value != null ? value.toString() : "");
                        }
                    }
                    
                    // Tự động căn chỉnh độ rộng cột cho đẹp mắt (Tùy chọn)
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        sheet.autoSizeColumn(i);
                    }
                    
                    // 5. Ghi file ra ổ cứng
                    try (FileOutputStream out = new FileOutputStream(filePath)) {
                        workbook.write(out);
                    }
                    workbook.close();
                    
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!\nĐã lưu tại: " + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ======================== TÌM KIẾM & LÀM MỚI ========================
        
        JTextField txtSearch = headerRightPanel.getTxtSearch();
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
            loadData(); 
        });
    }
    
    // ======================== HÀM XỬ LÝ DỮ LIỆU ========================

    // Hàm load toàn bộ dữ liệu (có ép đọc lại Database)
    public void loadData() {
        hdBUS = new HoaDonBUS(); 
        ArrayList<HoaDonDTO> list = hdBUS.getAll();
        
        if (list == null) return;
        
        Object[][] data = new Object[list.size()][6];
        
        for (int i = 0; i < list.size(); i++) {
            HoaDonDTO hd = list.get(i);

            data[i][0] = i + 1; // STT
            data[i][1] = hd.getMaHD();       
            data[i][2] = hd.getMaNV();
            data[i][3] = hd.getMaKH();              
            data[i][4] = hd.getNgayLapHD() != null ? hd.getNgayLapHD().format(dtf) : "";
            data[i][5] = df.format(hd.getTongTien());
        }
        tblHoaDon.setData(data); // Đổ mảng Object[][] vào TablePanel
    }

    // Hàm lọc dữ liệu nội bộ dựa vào ô tìm kiếm
    public void searchData() {
        String text = headerRightPanel.getTxtSearch().getText().trim().toLowerCase();
        String type = headerRightPanel.getCboxSearch().getSelectedItem().toString();
        
        ArrayList<HoaDonDTO> listAll = hdBUS.getAll();
        ArrayList<HoaDonDTO> listFilter = new ArrayList<>();
        
        if (listAll != null) {
            for (HoaDonDTO hd : listAll) {
                boolean match = false;
                
                if (type.equals("Tất cả")) {
                    if (hd.getMaHD().toLowerCase().contains(text) || 
                        hd.getMaKH().toLowerCase().contains(text) || 
                        hd.getMaNV().toLowerCase().contains(text)) {
                        match = true;
                    }
                } else if (type.equals("Mã HĐ")) {
                    if (hd.getMaHD().toLowerCase().contains(text)) {
                        match = true;
                    }
                } else if (type.equals("Mã KH")) {
                    if (hd.getMaKH().toLowerCase().contains(text)) {
                        match = true;
                    }
                } else if (type.equals("Mã NV")) {
                    if (hd.getMaNV().toLowerCase().contains(text)) {
                        match = true;
                    }
                }
                
                if (match) {
                    listFilter.add(hd);
                }
            }
        }
        
        // Đổ mảng dữ liệu đã lọc lên bảng
        Object[][] data = new Object[listFilter.size()][6];
        for (int i = 0; i < listFilter.size(); i++) {
            HoaDonDTO hd = listFilter.get(i);
            
            data[i][0] = i + 1; 
            data[i][1] = hd.getMaHD();       
            data[i][2] = hd.getMaNV();
            data[i][3] = hd.getMaKH();              
            data[i][4] = hd.getNgayLapHD() != null ? hd.getNgayLapHD().format(dtf) : "";
            data[i][5] = df.format(hd.getTongTien());
        }
        tblHoaDon.setData(data);
    }
}