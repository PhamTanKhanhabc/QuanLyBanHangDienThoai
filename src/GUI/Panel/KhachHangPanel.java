package GUI.Panel;
import GUI.Dialog.ChiTietKhachHangDialog;
import GUI.Dialog.UpdateKhachHangDialog;
import GUI.Dialog.CreateKhachHangDialog;
import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import GUI.Component.ActionPanel;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Main;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.JTableExporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class KhachHangPanel extends JPanel {

    private KhachHangBUS khBUS = new KhachHangBUS();
    private List<KhachHangDTO> listKH = khBUS.getAll();

    private JPanel headerPanel;
    private HeaderRightPanel headerRightPanel;
    private ActionPanel actionPanel;

    private TablePanel tablePanel;
    private JTable table;
    private DefaultTableModel modal;

    public KhachHangPanel() {
        initComponents();
        loadTable(listKH);
    }

    public KhachHangPanel(Main main) {
        initComponents();
        loadTable(listKH);
    }

    private void initComponents() {
        headerPanel = new JPanel();
        headerRightPanel = new HeaderRightPanel();
        actionPanel = new ActionPanel();
        actionPanel.configButtons(new String[]{"add", "update", "delete", "info", "import", "export"});

       
        tablePanel = new TablePanel(
                "Danh sách khách hàng",
                new String[]{
                    "STT",
                    "Mã khách hàng",
                    "Tên khách hàng",
                    "Số điện thoại",
                    "Địa chỉ"
                }
        );
        table = tablePanel.getTable();
        modal = (DefaultTableModel) table.getModel();

        
        setBackground(new Color(230, 245, 245));
        setLayout(new BorderLayout(0, 0));

        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 70));

        
        headerRightPanel.getBtnReload().putClientProperty(FlatClientProperties.STYLE, "arc:15");

        
        headerPanel.add(actionPanel, BorderLayout.WEST);
        headerPanel.add(headerRightPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.PAGE_START);
        add(tablePanel, BorderLayout.CENTER);

       
        actionPanel.btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));
        actionPanel.btnUpdate.addActionListener(evt -> btnUpdateActionPerformed(evt));
        actionPanel.btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));
        actionPanel.btnInfo.addActionListener(evt -> btnInfoActionPerformed(evt));
        actionPanel.btnImport.addActionListener(evt -> btnImportActionPerformed(evt));
        actionPanel.btnExport.addActionListener(evt -> btnExportActionPerformed(evt));

        
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        
        headerRightPanel.getBtnReload().addActionListener(e -> btnReloadActionPerformed(e));
    }

    public void loadTable(List<KhachHangDTO> list) {
        modal.setRowCount(0);
        int stt = 1;
        for (KhachHangDTO kh : list) {
            modal.addRow(new Object[]{
                stt++,
                kh.getMaKH(),
                kh.getHo() + " " + kh.getTen(),
                kh.getSoDT(),
                kh.getDiaChi()
            });
        }
    }

    private void btnAddActionPerformed(ActionEvent evt) {
       
        CreateKhachHangDialog dialog = new CreateKhachHangDialog(
        (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), 
        true, 
        this
    );
    
    
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
    }

   private void btnUpdateActionPerformed(ActionEvent evt) {
    
    int row = table.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    
    String maKH = table.getValueAt(row, 1).toString().trim();
    KhachHangDTO kh = khBUS.getById(maKH);

    if (kh == null) {
        JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy dữ liệu khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    UpdateKhachHangDialog dialog = new UpdateKhachHangDialog(
        (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), 
        true, 
        this, 
        kh
    );
    
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
}
    

   private void btnDeleteActionPerformed(ActionEvent evt) {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    
    String id = table.getValueAt(row, 1).toString().trim(); 
    
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa khách hàng " + id + " không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
       
        KhachHangDTO kh = khBUS.getById(id);
        
        if (kh == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Dữ liệu khách hàng không tồn tại trong hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        String ketQua = khBUS.delete(kh);
        
        if (ketQua.contains("thành công")) {
            
            loadTable(khBUS.getAll());
            JOptionPane.showMessageDialog(this, ketQua, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, ketQua, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void btnInfoActionPerformed(ActionEvent evt) {
    
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    
    String maKH = table.getValueAt(row, 1).toString().trim();
    KhachHangDTO kh = khBUS.getById(maKH);

    if (kh != null) {
       
        ChiTietKhachHangDialog dialog = new ChiTietKhachHangDialog(
            (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), 
            true, 
            kh
        );
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}

    private void btnImportActionPerformed(ActionEvent evt) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel để nhập Khách Hàng");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File (.xlsx)", "xlsx");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();

                try (FileInputStream fis = new FileInputStream(fileToOpen);
                     Workbook workbook = new XSSFWorkbook(fis)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    int countSuccess = 0;
                    int countError = 0;
                    DataFormatter formatter = new DataFormatter();

                    // Chạy từ dòng 1 (bỏ qua dòng 0 là Tiêu đề cột)
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) continue;

                        try {
                            // Cột Excel tương ứng: 0(STT), 1(Mã), 2(Tên), 3(SĐT), 4(Địa chỉ)
                            String tenDayDu = formatter.formatCellValue(row.getCell(2)).trim();
                            String soDT = formatter.formatCellValue(row.getCell(3)).trim();
                            String diaChi = formatter.formatCellValue(row.getCell(4)).trim();

                            // Bỏ qua dòng trống hoặc thiếu thông tin quan trọng
                            if (tenDayDu.isEmpty() || soDT.isEmpty()) {
                                countError++;
                                continue;
                            }

                            // Kiểm tra xem SĐT này đã có trong hệ thống chưa (tránh trùng lặp)
                            if (khBUS.getMaByInfo(soDT) != null) {
                                countError++; 
                                continue;
                            }

                            // Tách Họ và Tên giống như thuật toán đã làm ở Hóa Đơn
                            String ho = "Khách";
                            String ten = tenDayDu;
                            int lastSpaceIndex = tenDayDu.lastIndexOf(" ");
                            if (lastSpaceIndex > 0) {
                                ho = tenDayDu.substring(0, lastSpaceIndex).trim();
                                ten = tenDayDu.substring(lastSpaceIndex + 1).trim();
                            }

                            // Khởi tạo và gán giá trị cho DTO
                            KhachHangDTO khMoi = new KhachHangDTO();
                            khMoi.setMaKH(khBUS.generateMaKH()); // Gọi hàm tự sinh mã bên BUS
                            khMoi.setHo(ho);
                            khMoi.setTen(ten);
                            khMoi.setSoDT(soDT);
                            khMoi.setDiaChi(diaChi);
                            khMoi.setTrangThai(1);

                            // Lưu xuống DB
                            String resultAdd = khBUS.add(khMoi);
                            if (resultAdd != null && resultAdd.toLowerCase().contains("thành công")) {
                                countSuccess++;
                            } else {
                                countError++;
                            }

                        } catch (Exception ex) {
                            countError++;
                        }
                    }

                    JOptionPane.showMessageDialog(this, 
                            "Nhập file Excel hoàn tất!\n- Thêm mới thành công: " + countSuccess + " khách hàng\n- Bỏ qua (Trùng SĐT hoặc lỗi): " + countError + " dòng", 
                            "Kết quả Import", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Tải lại bảng dữ liệu
                    btnReloadActionPerformed(null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi đọc file Excel. Vui lòng kiểm tra lại định dạng!\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnExportActionPerformed(ActionEvent evt) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn thư mục lưu file Khách Hàng");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File (.xlsx)", "xlsx");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("DanhSachKhachHang");

                // 1. In dòng Tiêu đề (Lấy từ tên cột của JTable)
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    headerRow.createCell(i).setCellValue(table.getColumnName(i));
                }

                // 2. In dữ liệu từng dòng
                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Object value = table.getValueAt(i, j);
                        row.createCell(j).setCellValue(value != null ? value.toString() : "");
                    }
                }

                // 3. Tự động căn chỉnh độ rộng cột cho đẹp
                for (int i = 0; i < table.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                // 4. Lưu file
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
    }
    private void txtSearchKeyReleased(KeyEvent evt) {
        String text = headerRightPanel.getTxtSearch().getText();
        List<KhachHangDTO> result = khBUS.search(text);
        loadTable(result);
    }

    private void btnReloadActionPerformed(ActionEvent evt) {
        headerRightPanel.getTxtSearch().setText("");
        khBUS = new KhachHangBUS(); 
        loadTable(khBUS.getAll());
    }
}