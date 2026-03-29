package GUI.Panel;

import BUS.VaiTroBUS;
import DTO.VaiTroDTO;
import GUI.Component.ActionPanel;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Dialog.VaiTroDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Color;

// Các thư viện bổ sung cho chức năng Import/Export Excel
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;

public class VaiTroPanel extends JPanel {

    private VaiTroBUS vaiTroBUS;
    private TablePanel tablePanel;
    private ActionPanel actionPanel;
    private HeaderRightPanel headerRightPanel;

    public VaiTroPanel() {
        vaiTroBUS = new VaiTroBUS();
        initComponents();
        addEvents();
        loadDataToTable(vaiTroBUS.getAll());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        actionPanel = new ActionPanel();
        // Hiển thị đủ 6 nút theo giao diện chuẩn
        actionPanel.configButtons(new String[] { "add", "update", "delete", "info", "import", "export" });

        headerRightPanel = new HeaderRightPanel();

        topPanel.add(actionPanel, BorderLayout.WEST);
        topPanel.add(headerRightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] header = { "Ma Vai Tro", "Ten Vai Tro" };
        tablePanel = new TablePanel("DANH SACH VAI TRO", header);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadDataToTable(ArrayList<VaiTroDTO> list) {
        Object[][] data = new Object[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getMaVaiTro();
            data[i][1] = list.get(i).getTenVaiTro();
        }
        tablePanel.setData(data);
    }

    private void addEvents() {
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = headerRightPanel.getTxtSearch().getText();
                loadDataToTable(vaiTroBUS.search(text));
            }
        });

        headerRightPanel.getBtnReload().addActionListener(e -> {
            vaiTroBUS.refresh();
            loadDataToTable(vaiTroBUS.getAll());
            headerRightPanel.getTxtSearch().setText("");
        });

        actionPanel.btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            VaiTroDialog dialog = new VaiTroDialog(parentFrame, true, "Them", null);
            dialog.setVisible(true);

            if (dialog.isSuccess()) {
                vaiTroBUS.refresh();
                loadDataToTable(vaiTroBUS.getAll());
            }
        });

        actionPanel.btnUpdate.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon vai tro can sua!");
                return;
            }

            String maVaiTro = tablePanel.getTable().getValueAt(row, 0).toString();
            VaiTroDTO dtoToEdit = vaiTroBUS.getAll().get(vaiTroBUS.getIndexById(maVaiTro));

            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            VaiTroDialog dialog = new VaiTroDialog(parentFrame, true, "Sua", dtoToEdit);
            dialog.setVisible(true);

            if (dialog.isSuccess()) {
                vaiTroBUS.refresh();
                loadDataToTable(vaiTroBUS.getAll());
            }
        });

        actionPanel.btnDelete.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon vai tro can xoa!");
                return;
            }

            String maVaiTro = tablePanel.getTable().getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xoa vai tro " + maVaiTro + " se anh huong den tai khoan. Chac chan xoa?", "Xac nhan",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                VaiTroDTO dto = new VaiTroDTO();
                dto.setMaVaiTro(maVaiTro);
                if (vaiTroBUS.delete(dto)) {
                    JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                    // Đã thêm refresh() để bảng tự làm mới sau khi xóa
                    vaiTroBUS.refresh();
                    loadDataToTable(vaiTroBUS.getAll());
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xoa that bai! (Co the do con tai khoan dang dung vai tro nay)");
                }
            }
        });

        // ------------------ CÁC SỰ KIỆN INFO / IMPORT / EXPORT ------------------

        // Sự kiện nút INFO
        actionPanel.btnInfo.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon vai tro de xem chi tiet!");
                return;
            }

            String maVaiTro = tablePanel.getTable().getValueAt(row, 0).toString();
            VaiTroDTO dtoToView = vaiTroBUS.getAll().get(vaiTroBUS.getIndexById(maVaiTro));

            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            // Gọi VaiTroDialog với tham số "Xem"
            VaiTroDialog dialog = new VaiTroDialog(parentFrame, true, "Xem", dtoToView);
            dialog.setVisible(true);
        });

        // Sự kiện nút EXPORT
        actionPanel.btnExport.addActionListener(e -> {
            utils.JTableExporter.exportJTableToExcel(tablePanel.getTable());
        });

        // Sự kiện nút IMPORT (Đọc file Excel bằng Apache POI và lưu thẳng vào Database)
        actionPanel.btnImport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chon file Excel de Import Vai Tro");
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
                            // Cột 0 là Mã, Cột 1 là Tên
                            String ma = formatter.formatCellValue(row.getCell(0)).trim();
                            String ten = formatter.formatCellValue(row.getCell(1)).trim();

                            if (!ma.isEmpty() && !ten.isEmpty()) {
                                VaiTroDTO dto = new VaiTroDTO(ma, ten, 1);
                                if (vaiTroBUS.add(dto)) {
                                    successCount++;
                                } else {
                                    failCount++;
                                }
                            }
                        }
                    }

                    String message = "Import hoan tat!\n"
                            + "- Them thanh cong: " + successCount + " dong.\n"
                            + "- That bai (trung ma hoac loi): " + failCount + " dong.";
                    JOptionPane.showMessageDialog(this, message);

                    // Làm mới lại bảng sau khi import
                    vaiTroBUS.refresh();
                    loadDataToTable(vaiTroBUS.getAll());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Loi doc file Excel!\n" + ex.getMessage(), "Loi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}