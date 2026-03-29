package GUI.Panel; 

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import GUI.Component.ActionPanel;
import GUI.Component.FilterItem;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Dialog.TaiKhoanDialog; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Color;

// Bổ sung thư viện cho Import Excel
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;

public class TaiKhoanPanel extends JPanel {

    private TaiKhoanBUS taiKhoanBUS;
    private TablePanel tablePanel;
    private ActionPanel actionPanel;
    private HeaderRightPanel headerRightPanel;
    private FilterItem filterVaiTro;

    public TaiKhoanPanel() {
        taiKhoanBUS = new TaiKhoanBUS();
        initComponents();
        addEvents();
        loadDataToTable(taiKhoanBUS.getAll());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(Color.WHITE);
        
        actionPanel = new ActionPanel();
        // Cấu hình đủ bộ 6 nút giống hệt NhanVienPanel
        actionPanel.configButtons(new String[]{"add", "update", "delete", "info", "import", "export"}); 
        
        headerRightPanel = new HeaderRightPanel();

        topHeader.add(actionPanel, BorderLayout.WEST);
        topHeader.add(headerRightPanel, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        filterPanel.setBackground(Color.WHITE);
        
        filterVaiTro = new FilterItem("Loc theo Vai Tro");
        filterVaiTro.addItem("Tat ca");
        filterVaiTro.addItem("Q01"); 
        filterVaiTro.addItem("Q02");
        
        filterPanel.add(filterVaiTro);

        topPanel.add(topHeader, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        String[] header = {"Ma TK", "Ten Dang Nhap", "Mat Khau", "Ma Nhan Vien", "Ma Vai Tro"};
        tablePanel = new TablePanel("DANH SACH TAI KHOAN", header);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadDataToTable(ArrayList<TaiKhoanDTO> list) {
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getMaTaiKhoan();
            data[i][1] = list.get(i).getTenDangNhap();
            data[i][2] = list.get(i).getMatKhau();
            data[i][3] = list.get(i).getMaNhanVien();
            data[i][4] = list.get(i).getMaVaiTro();
        }
        tablePanel.setData(data); 
    }

    private void addEvents() {
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = headerRightPanel.getTxtSearch().getText();
                loadDataToTable(taiKhoanBUS.search(text));
            }
        });

        headerRightPanel.getBtnReload().addActionListener(e -> {
            taiKhoanBUS.refresh();
            loadDataToTable(taiKhoanBUS.getAll());
            filterVaiTro.getComboBox().setSelectedIndex(0); 
            headerRightPanel.getTxtSearch().setText("");
        });

        filterVaiTro.getComboBox().addActionListener(e -> {
            String role = filterVaiTro.getSelectedItem();
            if (role.equals("Tat ca")) {
                loadDataToTable(taiKhoanBUS.getAll());
            } else {
                ArrayList<TaiKhoanDTO> filteredList = new ArrayList<>();
                for (TaiKhoanDTO dto : taiKhoanBUS.getAll()) {
                    if (dto.getMaVaiTro().equalsIgnoreCase(role)) {
                        filteredList.add(dto);
                    }
                }
                loadDataToTable(filteredList);
            }
        });

        actionPanel.btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            TaiKhoanDialog dialog = new TaiKhoanDialog(parentFrame, true, "Them", null);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                taiKhoanBUS.refresh();
                loadDataToTable(taiKhoanBUS.getAll());
            }
        });

        actionPanel.btnUpdate.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon tai khoan can sua tren bang!");
                return;
            }
            
            String maTK = tablePanel.getTable().getValueAt(row, 0).toString();
            TaiKhoanDTO dtoToEdit = taiKhoanBUS.getAll().get(taiKhoanBUS.getIndexById(maTK));
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            TaiKhoanDialog dialog = new TaiKhoanDialog(parentFrame, true, "Sua", dtoToEdit);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                taiKhoanBUS.refresh();
                loadDataToTable(taiKhoanBUS.getAll());
            }
        });

        actionPanel.btnDelete.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon tai khoan can xoa!");
                return;
            }
            
            String maTK = tablePanel.getTable().getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac chan muon xoa tai khoan " + maTK + " khong?", "Xac nhan xoa", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                TaiKhoanDTO dto = new TaiKhoanDTO();
                dto.setMaTaiKhoan(maTK); 
                
                if (taiKhoanBUS.delete(dto)) {
                    JOptionPane.showMessageDialog(this, "Xoa tai khoan thanh cong!");
                    taiKhoanBUS.refresh(); 
                    loadDataToTable(taiKhoanBUS.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Xoa that bai!");
                }
            }
        });

        // Chức năng Xem chi tiết (Info)
        actionPanel.btnInfo.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon tai khoan de xem chi tiet!");
                return;
            }
            
            String maTK = tablePanel.getTable().getValueAt(row, 0).toString();
            TaiKhoanDTO dtoToView = taiKhoanBUS.getAll().get(taiKhoanBUS.getIndexById(maTK));
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            TaiKhoanDialog dialog = new TaiKhoanDialog(parentFrame, true, "Xem", dtoToView);
            dialog.setVisible(true);
        });

        // Chức năng Xuất Excel (Export)
        actionPanel.btnExport.addActionListener(e -> {
            utils.JTableExporter.exportJTableToExcel(tablePanel.getTable());
        });

        // Chức năng Nhập Excel (Import)
        actionPanel.btnImport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chon file Excel de Import Tai Khoan");
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

                    // Duyệt từ dòng 1 (bỏ qua Header) để lấy 5 cột dữ liệu
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            String maTK = formatter.formatCellValue(row.getCell(0)).trim();
                            String tenDN = formatter.formatCellValue(row.getCell(1)).trim();
                            String matKhau = formatter.formatCellValue(row.getCell(2)).trim();
                            String maNV = formatter.formatCellValue(row.getCell(3)).trim();
                            String maVT = formatter.formatCellValue(row.getCell(4)).trim();

                            if (!maTK.isEmpty() && !tenDN.isEmpty()) {
                                TaiKhoanDTO dto = new TaiKhoanDTO(maTK, tenDN, matKhau, maNV, maVT, 1);
                                
                                if (taiKhoanBUS.add(dto)) {
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
                    
                    taiKhoanBUS.refresh();
                    loadDataToTable(taiKhoanBUS.getAll());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Loi doc file Excel!\n" + ex.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}