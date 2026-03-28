/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.LoaiSanPhamBUS;
import DTO.LoaiSanPhamDTO;
import GUI.Component.ActionPanel;
import GUI.Component.TablePanel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class ThuocTinhLoaiSanPhamDialog extends JDialog{
    private LoaiSanPhamBUS LSP_BUS = new LoaiSanPhamBUS();
    
    private TablePanel tablePanel;
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JPanel formPanel;
    private JLabel lblCategoryName;
    private JTextField txtCategoryName;
    private JSeparator separator;
    private ActionPanel actionPanel;
    private JPanel pnlTableContainer;
    private JPanel pnlSearch;
    private JComboBox<String> cboxSearch;
    private JTextField txtSearch;
    private JButton btnReload;
    private JTable table;
    private DefaultTableModel modal;
    
    public ThuocTinhLoaiSanPhamDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        searchLayout();
        loadTable();
    }
    private void searchLayout() {
        btnReload.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");

        String[] searchType = {"Tất cả", "Mã", "Tên"};
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
        cboxSearch.setModel(model);
    }

    public void loadTable(List<LoaiSanPhamDTO> list) {
        Object[][] data = new Object[list.size()][3];
        int stt = 1;

        for (int i = 0; i < list.size(); i++) {
            LoaiSanPhamDTO e = list.get(i);
            data[i][0] = String.valueOf(stt++);
            data[i][1] = e.getMaLoai();
            data[i][2] = e.getTenLoai();
        }

        tablePanel.setData(data);
    } 
    public void loadTable() {
        loadTable(LSP_BUS.getAll());
    }   

    private boolean isValidateFields() {
        if (txtCategoryName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Tên loại sản phẩm không được rỗng!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE
            );
            txtCategoryName.requestFocus();
            return false;
        }
        return true;
    }

    private LoaiSanPhamDTO getInputFields() {
        int ma = LSP_BUS.generateMaLoai();
        String ten = txtCategoryName.getText().trim();

        return new LoaiSanPhamDTO(
                ma,
                ten,
                1 
        );
    }
    private void initComponents() {
        
        // Panels
        headerPanel = new JPanel();
        titleLabel = new JLabel();
        
        mainPanel = new JPanel();
        
        formPanel = new JPanel();
        
        lblCategoryName = new JLabel();
        txtCategoryName = new JTextField();
        
        separator = new JSeparator();
        
        
        actionPanel = new ActionPanel();
        
        pnlTableContainer = new JPanel();
        pnlSearch = new JPanel();
        cboxSearch = new JComboBox<>();
        txtSearch = new JTextField();
        btnReload = new JButton();
        
        
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);       // Thay cho pack()
        this.setLocationRelativeTo(null); // Canh giữa màn hình
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        headerPanel.setBackground(new java.awt.Color(65,120,255));
        headerPanel.setMinimumSize(new java.awt.Dimension(100, 60));
        headerPanel.setPreferredSize(new java.awt.Dimension(500, 50));
        headerPanel.setLayout(new BorderLayout());
        
        titleLabel.setFont(new java.awt.Font("Roboto Medium", 0, 18));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("LOẠI SẢN PHẨM");
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setPreferredSize(new java.awt.Dimension(600, 600));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));
        
        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setPreferredSize(new java.awt.Dimension(500, 40));
        formPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        
        lblCategoryName.setFont(new java.awt.Font("Roboto", 0, 14));
        lblCategoryName.setText("Tên loại sản phẩm");
        lblCategoryName.setMaximumSize(new java.awt.Dimension(44, 40));
        lblCategoryName.setPreferredSize(new java.awt.Dimension(150, 40));
        
        formPanel.add(lblCategoryName);
        
        txtCategoryName.setFont(new java.awt.Font("Roboto", 0, 14));
        txtCategoryName.setPreferredSize(new java.awt.Dimension(330, 40));
        
        formPanel.add(txtCategoryName);
        
        mainPanel.add(formPanel);
        
        separator.setPreferredSize(new java.awt.Dimension(460, 3));
        mainPanel.add(separator);
        
        pnlTableContainer.setPreferredSize(new java.awt.Dimension(500, 300));
        pnlTableContainer.setLayout(new java.awt.BorderLayout());
        
        pnlSearch.setBackground(new java.awt.Color(255, 255, 255));
        pnlSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlSearch.setPreferredSize(new java.awt.Dimension(100, 48));
        pnlSearch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        
        cboxSearch.setToolTipText("");
        cboxSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cboxSearch.setPreferredSize(new java.awt.Dimension(80, 32));
        pnlSearch.add(cboxSearch);
        
        txtSearch.setToolTipText("Tìm kiếm");
        txtSearch.setPreferredSize(new java.awt.Dimension(140, 36));
        txtSearch.setSelectionColor(new java.awt.Color(230, 245, 245));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        pnlSearch.add(txtSearch);
        
        btnReload.setIcon(new FlatSVGIcon("./icon/reload.svg", 24, 24));
        btnReload.setToolTipText("Làm mới");
        btnReload.setBorder(null);
        btnReload.setBorderPainted(false);
        btnReload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReload.setFocusPainted(false);
        btnReload.setFocusable(false);
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReload.setPreferredSize(new java.awt.Dimension(40, 40));
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        pnlSearch.add(btnReload);
        
        pnlTableContainer.add(pnlSearch, java.awt.BorderLayout.PAGE_START);
        
        tablePanel = new TablePanel(
            "Danh sách loại sản phẩm",
            new String[]{
                "STT",
                "Mã loại",
                "Tên loại"
            }
        );
        table = tablePanel.getTable();
        modal = (DefaultTableModel) table.getModel();
        
        pnlTableContainer.add(tablePanel, BorderLayout.CENTER);
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        
        mainPanel.add(pnlTableContainer);

        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        actionPanel.configButtons(new String[]{"add", "update", "delete"});
        
        actionPanel.btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));

        actionPanel.btnUpdate.addActionListener(evt -> btnUpdateActionPerformed(evt));

        actionPanel.btnDelete.addActionListener(evt -> btnRemoveActionPerformed(evt));
        
        add(actionPanel, BorderLayout.PAGE_END);
    }
    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn dòng cần xóa!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn xóa dòng này?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(table.getValueAt(row, 1).toString());

                LoaiSanPhamDTO lsp = new LoaiSanPhamDTO(id, "", 1);

                boolean result = LSP_BUS.delete(lsp);

                if (result) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Có lỗi xảy ra!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (isValidateFields()) {
            LoaiSanPhamDTO lsp = getInputFields();
            LSP_BUS.add(lsp);
            JOptionPane.showMessageDialog(
                this,
                "Thêm thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
            );
            loadTable();
        }
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        DefaultTableModel modal = (DefaultTableModel) table.getModel();
        modal.setRowCount(0);

        String search = txtSearch.getText().toLowerCase().trim();
        String searchType = cboxSearch.getSelectedItem().toString();
        List<LoaiSanPhamDTO> listsearch = LSP_BUS.search(search, searchType);

        int stt = 1;
        for (LoaiSanPhamDTO e : listsearch) {
            modal.addRow(new Object[]{String.valueOf(stt), e.getMaLoai(), e.getTenLoai()});
            stt++;
        }
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        if (isValidateFields()) {
            try {
                int row = table.getSelectedRow();
                
                if (row == -1) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn dòng cần thực hiện!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
                
                int id = Integer.parseInt(table.getValueAt(row, 1).toString());
                String ten = txtCategoryName.getText();
                LoaiSanPhamDTO e = new LoaiSanPhamDTO(id, ten, 1);

                LSP_BUS.update(e);
                    JOptionPane.showMessageDialog(
                    this,
                    "Sửa thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
                );
                this.loadTable();
            } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                    this,
                    "Có lỗi xảy ra!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
        txtCategoryName.setText("");
        txtSearch.setText("");
        cboxSearch.setSelectedIndex(0);
        loadTable();
    }
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tablePanel.getTable().getSelectedRow();
        txtCategoryName.setText(
            tablePanel.getTable().getValueAt(row, 2).toString()
        );
    }
}
