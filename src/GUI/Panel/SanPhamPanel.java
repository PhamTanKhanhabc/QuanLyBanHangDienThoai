/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.HangSanXuatBUS;
import BUS.LoaiSanPhamBUS;
import BUS.SanPhamBUS;
import DAO.HangSanXuatDAO;
import DAO.LoaiSanPhamDAO;
import DAO.SanPhamDAO;
import DTO.HangSanXuatDTO;
import DTO.LoaiSanPhamDTO;
import DTO.SanPhamDTO;
import GUI.Component.ActionPanel;
import GUI.Main;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import GUI.Component.FilterItem;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Component.ButtonToolBar;
import GUI.Dialog.ChiTietSanPhamDialog;
import GUI.Dialog.CreateSanPhamDialog;
import gui.dialog.UpdateSanPhamDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import utils.JTableExporter;
/**
 *
 * @author user
 */
public class SanPhamPanel extends JPanel{
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private LoaiSanPhamBUS loaiBUS = new LoaiSanPhamBUS();
    private HangSanXuatBUS hangBUS = new HangSanXuatBUS();

    private List<SanPhamDTO> listSp = sanPhamBUS.getAll();
    private List<HangSanXuatDTO> listHSX = hangBUS.getAll();
    private List<LoaiSanPhamDTO> listLSP = loaiBUS.getAll();


    private JPanel headerPanel;
    private HeaderRightPanel headerRightPanel;
    private ActionPanel actionPanel;

    private TablePanel tablePanel;  
    private JTable table;
    private DefaultTableModel modal;

    private JPanel filterPanel;
    private FilterItem filterLoai;
    private FilterItem filterHang;


    public SanPhamPanel() {
        initComponents();
        headerLayout();
        fillCombobox();
        loadTable(listSp);
    }

    public SanPhamPanel(Main main) {
        initComponents();
        headerLayout();
        fillCombobox();
        loadTable(listSp);
    }
    private void initComponents() {
        // ===== 1. KHỞI TẠO COMPONENT =====
        headerPanel = new JPanel();
        headerRightPanel = new HeaderRightPanel();
        actionPanel = new ActionPanel();
        actionPanel.configButtons(new String[]{ "add", "update", "delete", "info", "import", "export" });
        tablePanel = new TablePanel(
            "Danh sách thông tin sản phẩm",
            new String[]{
                "STT",
                "Mã Sản Phẩm",
                "Tên Sản Phẩm",
                "Số lượng tồn",
                "Đơn giá",
                "Đơn vị tính",
                "Loại sản phẩm",
                "Hãng sản xuất"
            }
        );
        table = tablePanel.getTable();
        modal = (DefaultTableModel) table.getModel();

        filterPanel = new JPanel();
        filterLoai = new FilterItem("Loại sản phẩm");
        filterHang = new FilterItem("Hãng sản xuất");
        
        // ===== 2. CÀI ĐẶT LAYOUT - STYLE =====
        setBackground(new Color(230, 245, 245));
        setPreferredSize(new Dimension(1130, 800));
        setLayout(new BorderLayout(0, 0));
        
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setLayout(new BorderLayout());
        
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setPreferredSize(new Dimension(220, 0));
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
            
        // ===== 3. ADD COMPONENT =====
        headerPanel.add(actionPanel, java.awt.BorderLayout.WEST);
        headerPanel.add(headerRightPanel, BorderLayout.EAST);
        
        add(headerPanel, java.awt.BorderLayout.PAGE_START);
        
        filterPanel.add(filterLoai);
        filterPanel.add(filterHang);

        add(filterPanel, BorderLayout.WEST);

        add(tablePanel, java.awt.BorderLayout.CENTER);
        
        // ===== 4. EVENT LISTENER =====

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
        
        filterLoai.getComboBox().addActionListener(e -> loadTable(getListFilter()));
        filterHang.getComboBox().addActionListener(e -> loadTable(getListFilter()));
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
    public void loadTable(List<SanPhamDTO> list) {
        modal.setRowCount(0);
        listSp = list;
        int stt = 1;

        DecimalFormat df = new DecimalFormat("#,###", new DecimalFormatSymbols(new Locale("vi", "VN")));
        for (SanPhamDTO e : listSp) {
            modal.addRow(new Object[]{
                stt++,                               // STT
                e.getMaSp(),                           // Mã sản phẩm
                e.getTenSp(),                     // Tên sản phẩm
                e.getSoLuongTon(),                   // Số lượng tồn
                df.format(e.getDonGia()),     // Đơn giá
                e.getDonViTinh(),           // Đơn vị tính
                loaiBUS.getTenLoaiSp(e.getMaLoai()),
                hangBUS.getTenHang(e.getMaHang())              // Hãng sản xuất
            });
        }
    }
    private void fillCombobox() {
        filterLoai.addItem("Tất cả");
        for (LoaiSanPhamDTO e : listLSP) {
            filterLoai.addItem(e.getTenLoai());
        }

        filterHang.addItem("Tất cả");
        for (HangSanXuatDTO e : listHSX) {
            filterHang.addItem(e.getTenHang());
        }
    }
    //Hàm lấy danh sách sản phẩm theo điều kiện lọc
    private List<SanPhamDTO> getListFilter() {

        Object loaiObj = filterLoai.getComboBox().getSelectedItem();
        Object hangObj = filterHang.getComboBox().getSelectedItem();

        if (loaiObj == null || hangObj == null) {
            return sanPhamBUS.getAll();
        }

        String tenLoai = filterLoai.getSelectedItem();
        String tenHang = filterHang.getSelectedItem();

        int maLoai = -1;    
        int maHang = -1;

        if (!tenLoai.equals("Tất cả")) {
            maLoai = loaiBUS.getMaByTen(tenLoai);
        }

        if (!tenHang.equals("Tất cả")) {
            maHang = hangBUS.getMaByTen(tenHang);
        }

        return sanPhamBUS.getFilterTable(maLoai, maHang);
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        CreateSanPhamDialog dialog = new CreateSanPhamDialog(
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
            String maSp = table.getValueAt(row, 1).toString();

            SanPhamDTO sp = null;

            for (SanPhamDTO s : listSp) {
                if (s.getMaSp().equals(maSp)) {
                    sp = s;
                    break;
                }
            }

            UpdateSanPhamDialog dialog = new UpdateSanPhamDialog(
                    (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
                    true,
                    this,
                    sp
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

        // LẤY MÃ SẢN PHẨM
        String id = table.getValueAt(row, 1).toString();  

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn xóa dòng này?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            sanPhamBUS = new SanPhamBUS(); // reload BUS
            int index = sanPhamBUS.getIndexById(id);

            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm");
                return;
            }

            SanPhamDTO sp = sanPhamBUS.getByIndex(index);

            boolean check = sanPhamBUS.delete(sp);

            if (check) {
                loadTable(sanPhamBUS.getAll());
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

        List<SanPhamDTO> listsearch = sanPhamBUS.search(search, searchType);

        loadTable(listsearch);
    }
    private void btnReloadActionPerformed(ActionEvent evt) {

        headerRightPanel.getTxtSearch().setText("");
        headerRightPanel.getCboxSearch().setSelectedIndex(0);

        filterLoai.getComboBox().setSelectedIndex(0);
        filterHang.getComboBox().setSelectedIndex(0);

        loadTable(sanPhamBUS.getAll()); 
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

        String maSP = table.getValueAt(row, 0).toString();

        SanPhamBUS spBUS = new SanPhamBUS();
        SanPhamDTO sp = spBUS.getByIndex(row); // hàm lấy sản phẩm theo mã

        ChiTietSanPhamDialog dialog =
            new ChiTietSanPhamDialog(
                    (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
                    true,
                    sp
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
                    
                    if(row.getCell(0) == null || row.getCell(1) == null) continue;
                    String maSp = row.getCell(0).toString();
                    String tenSp = row.getCell(1).toString();
                    int soLuong = Integer.parseInt(row.getCell(2).toString().split("\\.")[0]);
                    double donGia = Double.parseDouble( row.getCell(3).toString().replace(",", "") );
                    String donViTinh = row.getCell(4).toString();

                    String tenLoai = row.getCell(5).toString();
                    String tenHang = row.getCell(6).toString();

                    int maLoai = loaiBUS.getMaByTen(tenLoai);
                    int maHang = hangBUS.getMaByTen(tenHang);

                    if (maSp.isEmpty() || tenSp.isEmpty()) {
                        error++;
                        continue;
                    }

                    SanPhamDTO sp = new SanPhamDTO(
                        maSp,
                        tenSp,
                        "",          // hinhAnh
                        soLuong,
                        donGia,
                        donViTinh,
                        maLoai,
                        maHang,
                        1            // trangThai
                    );

                    sanPhamBUS.add(sp);

                }

                loadTable(sanPhamBUS.getAll());

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
