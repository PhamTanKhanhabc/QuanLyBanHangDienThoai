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

// Giả định các Dialog này sẽ được bạn tạo tương tự SanPham
// import GUI.Dialog.ChiTietKhachHangDialog;
// import GUI.Dialog.CreateKhachHangDialog;
// import GUI.Dialog.UpdateKhachHangDialog;

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
        
        JOptionPane.showMessageDialog(this, "Chức năng Nhập Excel");
    }

    private void btnExportActionPerformed(ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
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