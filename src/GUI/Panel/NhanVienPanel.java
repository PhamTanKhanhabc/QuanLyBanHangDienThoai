package GUI.Panel;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import GUI.Component.ActionPanel;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Dialog.NhanVienDialog; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Color;
// Bổ sung thư viện cho Import/Export Excel
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NhanVienPanel extends JPanel {

    private NhanVienBUS nhanVienBUS;
    private TablePanel tablePanel;
    private ActionPanel actionPanel;
    private HeaderRightPanel headerRightPanel;
    
    public NhanVienPanel() {
        nhanVienBUS = new NhanVienBUS();
        initComponents();
        addEvents();
        loadDataToTable(nhanVienBUS.getAll());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        actionPanel = new ActionPanel();
        // Cập nhật để hiển thị đủ 6 nút giống VaiTroPanel
        actionPanel.configButtons(new String[]{"add", "update", "delete", "info", "import", "export"}); 
        
        headerRightPanel = new HeaderRightPanel();

        topPanel.add(actionPanel, BorderLayout.WEST);
        topPanel.add(headerRightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] header = {"Ma NV", "Ho", "Ten", "Ngay Sinh", "Dia Chi", "Dien Thoai", "Luong Thang"};
        tablePanel = new TablePanel("DANH SACH NHAN VIEN", header);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadDataToTable(ArrayList<NhanVienDTO> list) {
        Object[][] data = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getMaNV();
            data[i][1] = list.get(i).getHo();
            data[i][2] = list.get(i).getTen();
            data[i][3] = list.get(i).getNgaySinh();
            data[i][4] = list.get(i).getDiaChi();
            data[i][5] = list.get(i).getDienThoai();
            data[i][6] = list.get(i).getLuongThang();
        }
        tablePanel.setData(data);
    }

    private void addEvents() {
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = headerRightPanel.getTxtSearch().getText();
                loadDataToTable(nhanVienBUS.search(text));
            }
        });

        headerRightPanel.getBtnReload().addActionListener(e -> {
            nhanVienBUS.refresh();
            loadDataToTable(nhanVienBUS.getAll());
            headerRightPanel.getTxtSearch().setText("");
        });

        actionPanel.btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            NhanVienDialog dialog = new NhanVienDialog(parentFrame, true, "Them", null);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                nhanVienBUS.refresh();
                loadDataToTable(nhanVienBUS.getAll());
            }
        });

        actionPanel.btnUpdate.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien can sua!");
                return;
            }
            
            String maNV = tablePanel.getTable().getValueAt(row, 0).toString();
            NhanVienDTO dtoToEdit = nhanVienBUS.getAll().get(nhanVienBUS.getIndexById(maNV));
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            NhanVienDialog dialog = new NhanVienDialog(parentFrame, true, "Sua", dtoToEdit);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                nhanVienBUS.refresh();
                loadDataToTable(nhanVienBUS.getAll());
            }
        });

        actionPanel.btnDelete.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien can xoa!");
                return;
            }
            
            String maNV = tablePanel.getTable().getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa nhan vien " + maNV + "?", "Xac nhan", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                NhanVienDTO dto = new NhanVienDTO();
                dto.setMaNV(maNV);
                if (nhanVienBUS.delete(dto)) {
                    JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                    // Đã fix: Thêm dòng refresh để cập nhật lại bảng sau khi xóa
                    nhanVienBUS.refresh();
                    loadDataToTable(nhanVienBUS.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Xoa that bai! (Co the do nhan vien dang co hoa don/tai khoan)");
                }
            }
        });

        // ------------------ CÁC SỰ KIỆN MỚI BỔ SUNG ------------------

        // Sự kiện nút INFO
        actionPanel.btnInfo.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien de xem chi tiet!");
                return;
            }
            String maNV = tablePanel.getTable().getValueAt(row, 0).toString();
            JOptionPane.showMessageDialog(this, "Chuc nang xem thong tin chi tiet cua nhan vien: " + maNV + " dang duoc xay dung!");
        });

        // Sự kiện nút EXPORT (Sử dụng utils.JTableExporter)
        actionPanel.btnExport.addActionListener(e -> {
            utils.JTableExporter.exportJTableToExcel(tablePanel.getTable());
        });

        // Sự kiện nút IMPORT (Đọc 7 cột từ Excel)
        actionPanel.btnImport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chon file Excel de Import Nhan Vien");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX files", "xlsx");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            int userChoice = fileChooser.showOpenDialog(this);
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (FileInputStream fis = new FileInputStream(selectedFile);
                     Workbook workbook = new XSSFWorkbook(fis)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    DataFormatter formatter = new DataFormatter();
                    
                    int successCount = 0;
                    int failCount = 0;

                    // Duyệt từ dòng 1 (bỏ qua dòng 0 là Header)
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            String ma = formatter.formatCellValue(row.getCell(0)).trim();
                            String ho = formatter.formatCellValue(row.getCell(1)).trim();
                            String ten = formatter.formatCellValue(row.getCell(2)).trim();
                            String ngaySinhStr  = formatter.formatCellValue(row.getCell(3)).trim();
                            LocalDate ngaySinh;
                            if (ngaySinhStr.contains("/")) {
                                ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            } else {
                                ngaySinh = LocalDate.parse(ngaySinhStr); // yyyy-MM-dd
                            }
                            String diaChi = formatter.formatCellValue(row.getCell(4)).trim();
                            String dienThoai = formatter.formatCellValue(row.getCell(5)).trim();
                            String luongStr = formatter.formatCellValue(row.getCell(6)).trim();

                            if (!ma.isEmpty() && !ten.isEmpty()) {
                                double luong = 0;
                                try {
                                    if(!luongStr.isEmpty()) luong = Double.parseDouble(luongStr);
                                } catch (NumberFormatException ex) {
                                    luong = 0; // Nếu nhập sai định dạng lương thì mặc định là 0
                                }

                                NhanVienDTO dto = new NhanVienDTO(ma, ho, ten, ngaySinh, diaChi, dienThoai, luong, 1);
                                
                                if (nhanVienBUS.add(dto)) {
                                    successCount++;
                                } else {
                                    failCount++; 
                                }
                            }
                        }
                    }
                    
                    String message = "Import hoan tat!\n" 
                                   + "- Them thanh cong: " + successCount + " dong.\n"
                                   + "- That bai (trung ma hoac loi SQL): " + failCount + " dong.";
                    JOptionPane.showMessageDialog(this, message);
                    
                    // Làm mới dữ liệu
                    nhanVienBUS.refresh();
                    loadDataToTable(nhanVienBUS.getAll());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Loi doc file Excel!\n" + ex.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}