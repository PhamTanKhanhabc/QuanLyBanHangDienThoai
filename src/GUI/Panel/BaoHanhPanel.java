/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import DTO.BaoHanhDTO;
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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.JTableExporter;
import BUS.BaoHanhBUS;
import BUS.HoaDonBUS;
import BUS.SanPhamBUS;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import javax.swing.SwingUtilities;

import GUI.Dialog.CreateBaoHanhDialog;
import GUI.Dialog.UpdateBaoHanhDialog;
import GUI.Dialog.ChiTietBaoHanhDialog;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 *
 * @author user
 */
public class BaoHanhPanel extends JPanel {
    private BaoHanhBUS baoHanhBUS = new BaoHanhBUS();
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private List<BaoHanhDTO> listBH = baoHanhBUS.getAll();
    private List<HoaDonDTO> listHD = hoaDonBUS.getAll();
    private List<SanPhamDTO> listSP = sanPhamBUS.getAll();

    private JPanel headerPanel;
    private HeaderRightPanel headerRightPanel;
    private ActionPanel actionPanel;

    private TablePanel tablePanel;  
    private JTable table;
    private DefaultTableModel modal;
    
    public BaoHanhPanel() {
        initComponents();
        headerLayout();
        loadTable(listBH);
    }
    public BaoHanhPanel(Main main) {
        initComponents();
        headerLayout();
        loadTable(listBH);
    }
    private void initComponents() {
        // ===== 1. KHỞI TẠO COMPONENT =====
        headerPanel = new JPanel();
        headerRightPanel = new HeaderRightPanel();
        actionPanel = new ActionPanel();
        actionPanel.configButtons(new String[]{ "add", "update", "delete", "info", "import", "export" });
        tablePanel = new TablePanel(
            "Danh sách thông tin bảo hành",
            new String[]{
                "STT",
                "Mã bảo hành",
                "Tên bảo hành",
                "Mã hóa đơn",
                "Mã sản phẩm",
                "Thời hạn",
                "Ngày bắt đầu",
                "Ngày kết thúc"
            }
        );
        table = tablePanel.getTable();
        modal = (DefaultTableModel) table.getModel();
        
        setBackground(new Color(230, 245, 245));
        setPreferredSize(new Dimension(1130, 800));
        setLayout(new BorderLayout(0, 0));
        
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setLayout(new BorderLayout());
        
        headerPanel.add(actionPanel, java.awt.BorderLayout.WEST);
        headerPanel.add(headerRightPanel, BorderLayout.EAST);
        
        add(headerPanel, java.awt.BorderLayout.PAGE_START);
        
        add(tablePanel, java.awt.BorderLayout.CENTER);
        
        actionPanel.btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));
        actionPanel.btnUpdate.addActionListener(evt -> btnUpdateActionPerformed(evt));
        actionPanel.btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));
        actionPanel.btnInfo.addActionListener(evt -> btnInfoActionPerformed(evt));
        actionPanel.btnImport.addActionListener(evt -> btnImportActionPerformed(evt));
        actionPanel.btnExport.addActionListener(evt -> btnExportActionPerformed(evt));
        actionPanel.btnThuocTinh.addActionListener(evt -> btnThuocTinhActionPerformed(evt));
        
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        headerRightPanel.getBtnReload().addActionListener(e -> btnReloadActionPerformed(e));
    }
    //Cấu hình giao diện header
    private void headerLayout() {
        List<JButton> listButton = new ArrayList<>();

        listButton.add(actionPanel.btnAdd);
        listButton.add(actionPanel.btnUpdate);
        listButton.add(actionPanel.btnDelete);
        listButton.add(actionPanel.btnInfo);
        listButton.add(actionPanel.btnImport);
        listButton.add(actionPanel.btnExport);
        listButton.add(headerRightPanel.getBtnReload());
        listButton.add(actionPanel.btnThuocTinh);

        // Bo góc
        headerRightPanel.getBtnReload()
        .putClientProperty(FlatClientProperties.STYLE, "arc:15");
    }
    public void loadTable(List<BaoHanhDTO> list) {

        modal.setRowCount(0);
        listBH = list;

        int stt = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (BaoHanhDTO e : listBH) {

            modal.addRow(new Object[]{
                stt++,
                e.getMaBH(),
                sanPhamBUS.getTenSp(e.getMaSP()),
                hoaDonBUS.getTenHoaDon(e.getMaHD()),
                e.getMaSP(),
                e.getThoiHan(),
                sdf.format(e.getNgayBatDau()),
                sdf.format(e.getNgayKetThuc())
            });
        }
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        CreateBaoHanhDialog  dialog = new CreateBaoHanhDialog (
                (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
                true,
                this
        );

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                throw new IndexOutOfBoundsException();
            }

            // lấy sản phẩm từ list
            String maBH = table.getValueAt(row, 1).toString();

            BaoHanhDTO bh = null;

            for (BaoHanhDTO b : listBH) {
                if (b.getMaBH().equals(maBH)) {
                    bh = b;
                    break;
                }
            }

            UpdateBaoHanhDialog  dialog = new UpdateBaoHanhDialog (
                    (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
                    true,
                    this,
                    bh
            );

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng");
        }
    }
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn dòng cần thực hiện!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // LẤY MÃ BẢO HÀNH
        String id = table.getValueAt(row, 1).toString();  

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn xóa dòng này?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            baoHanhBUS = new BaoHanhBUS();
            int index = baoHanhBUS.getIndexById(id);

            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy bảo hành");
                return;
            }

            BaoHanhDTO bh = baoHanhBUS.getByIndex(index);

            boolean check = baoHanhBUS.delete(bh);

            if (check) {
                loadTable(baoHanhBUS.getAll());
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {
        importExcel();
    }
    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {                                          
        JTableExporter.exportJTableToExcel(table);
    }
    private void txtSearchKeyReleased(KeyEvent evt) {

            String search = headerRightPanel.getTxtSearch().getText().toLowerCase().trim();
            String searchType = headerRightPanel.getCboxSearch().getSelectedItem().toString();

            List<BaoHanhDTO> listsearch = baoHanhBUS.search(search, searchType);

            loadTable(listsearch);
        }
        private void btnReloadActionPerformed(ActionEvent evt) {

            headerRightPanel.getTxtSearch().setText("");
            headerRightPanel.getCboxSearch().setSelectedIndex(0);

            loadTable(baoHanhBUS.getAll());
        }
        private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn dòng cần thực hiện!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String maBH = table.getValueAt(row, 1).toString();

        BaoHanhBUS bhBUS = new BaoHanhBUS();
        BaoHanhDTO bh = bhBUS.getByIndex(row);

        ChiTietBaoHanhDialog dialog =
            new ChiTietBaoHanhDialog(
                    (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
                    true,
                    bh
            );

        dialog.setVisible(true);
    }
    private void btnThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Chưa tạo trang Thuộc tính!");
    }
    public void importExcel() {

        JFileChooser jf = new JFileChooser();
        jf.setDialogTitle("Chọn file Excel");

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel Files", "xls", "xlsx", "xlsm");
        jf.setFileFilter(filter);

        int result = jf.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            int error = 0;

            try {

                File file = jf.getSelectedFile();
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);

                XSSFWorkbook workbook = new XSSFWorkbook(bis);
                XSSFSheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    XSSFRow row = sheet.getRow(i);

                    if (row == null) continue;

                    if (row.getCell(0) == null || row.getCell(1) == null) continue;

                    String maBH = row.getCell(0).toString();
                    String tenBH = row.getCell(1).toString();
                    String maHD = row.getCell(2).toString();
                    String maSP = row.getCell(3).toString();

                    int thoiHan = Integer.parseInt(row.getCell(4).toString().split("\\.")[0]);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    Date ngayBatDau = sdf.parse(row.getCell(5).toString());
                    Date ngayKetThuc = sdf.parse(row.getCell(6).toString());

                    if (maBH.isEmpty() || tenBH.isEmpty()) {
                        error++;
                        continue;
                    }

                    BaoHanhDTO bh = new BaoHanhDTO(
                            maBH,
                            tenBH,
                            maHD,
                            maSP,
                            thoiHan,
                            ngayBatDau,
                            ngayKetThuc,
                            1
                    );

                    baoHanhBUS.add(bh);

                }

                loadTable(baoHanhBUS.getAll());

                JOptionPane.showMessageDialog(this,
                        "Import Excel thành công!");

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this,
                        "Lỗi đọc file Excel!");

            }

            if (error > 0) {

                JOptionPane.showMessageDialog(this,
                        "Có " + error + " dòng bị lỗi!");

            }
        }
    }
    
}
