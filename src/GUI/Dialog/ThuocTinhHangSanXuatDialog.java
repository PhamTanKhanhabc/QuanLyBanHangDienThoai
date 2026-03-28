/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.HangSanXuatBUS;
import BUS.LoaiSanPhamBUS;
import DTO.HangSanXuatDTO;
import DTO.LoaiSanPhamDTO;
import GUI.Component.ActionPanel;
import GUI.Component.TablePanel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ThuocTinhHangSanXuatDialog extends JDialog{
    
    private HangSanXuatBUS HSX_BUS = new HangSanXuatBUS();
    
    private TablePanel tablePanel;
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JPanel formPanel;
    
    private JLabel lblManufacturerName;
    private JTextField txtManufacturerName;
    
    private JLabel lblAddress;
    private JTextField txtAddress;
    
    private JSeparator separator;
    private ActionPanel actionPanel;
    private JPanel pnlTableContainer;
    private JPanel pnlSearch;
    private JComboBox<String> cboxSearch;
    private JTextField txtSearch;
    private JButton btnReload;
    private JTable table;
    private DefaultTableModel modal;
    
    
    
    
    public ThuocTinhHangSanXuatDialog(java.awt.Frame parent, boolean modal) {
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
    public void loadTable(List<HangSanXuatDTO> list) {
        Object[][] data = new Object[list.size()][4]; 
        int stt = 1;

        for (int i = 0; i < list.size(); i++) {
            HangSanXuatDTO e = list.get(i);
            data[i][0] = String.valueOf(stt++);
            data[i][1] = e.getMaHang();
            data[i][2] = e.getTenHang();
            data[i][3] = e.getDiaChi(); 
        }

        tablePanel.setData(data);
    } 
    public void loadTable() {
        loadTable(HSX_BUS.getAll());
    }   

    private boolean isValidateFields() {
        if (txtManufacturerName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Tên Hãng Sản Xuất không được rỗng!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE
            );
            txtManufacturerName.requestFocus();
            return false;
        }
        if (txtAddress.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Địa chỉ không được rỗng!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE
            );
            txtAddress.requestFocus();
            return false;
        }
        return true;
    }
    private HangSanXuatDTO getInputFields() {
        int ma = HSX_BUS.generateMaHang();
        String ten = txtManufacturerName.getText().trim();
        String diaChi = txtAddress.getText().trim();

        return new HangSanXuatDTO(
                ma,
                ten,
                diaChi,
                1 
        );
    }
    private void initComponents() {
        
        // Panels
        headerPanel = new JPanel();
        titleLabel = new JLabel();
        
        mainPanel = new JPanel();
        
        formPanel = new JPanel();
        
        lblManufacturerName = new JLabel();
        txtManufacturerName = new JTextField();
        
        lblAddress = new JLabel();
        txtAddress = new JTextField();
        
        separator = new JSeparator();
        
        
        actionPanel = new ActionPanel();
        
        pnlTableContainer = new JPanel();
        pnlSearch = new JPanel();
        cboxSearch = new JComboBox<>();
        txtSearch = new JTextField();
        btnReload = new JButton();
        
        this.setLayout(new BorderLayout());
        this.setSize(600, 750);       // Thay cho pack()
        this.setLocationRelativeTo(null); // Canh giữa màn hình
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        headerPanel.setBackground(new java.awt.Color(65,120,255));
        headerPanel.setMinimumSize(new java.awt.Dimension(100, 60));
        headerPanel.setPreferredSize(new java.awt.Dimension(500, 50));
        headerPanel.setLayout(new BorderLayout());
        
        titleLabel.setFont(new java.awt.Font("Roboto Medium", 0, 18));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("HÃNG SẢN XUẤT");
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setPreferredSize(new java.awt.Dimension(600, 600));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));
        
        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setPreferredSize(new java.awt.Dimension(500, 90));
        formPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        
        lblManufacturerName.setFont(new java.awt.Font("Roboto", 0, 14));
        lblManufacturerName.setText("Tên hãng sản xuất");
        lblManufacturerName.setMaximumSize(new java.awt.Dimension(44, 40));
        lblManufacturerName.setPreferredSize(new java.awt.Dimension(150, 40));
        
        formPanel.add(lblManufacturerName);
        
        txtManufacturerName.setFont(new java.awt.Font("Roboto", 0, 14));
        txtManufacturerName.setPreferredSize(new java.awt.Dimension(330, 40));
        
        formPanel.add(txtManufacturerName);
        
        lblAddress.setFont(new java.awt.Font("Roboto", 0, 14));
        lblAddress.setText("Địa chỉ");
        lblAddress.setMaximumSize(new java.awt.Dimension(44, 40));
        lblAddress.setPreferredSize(new java.awt.Dimension(150, 40));
        
        formPanel.add(lblAddress);
        
        txtAddress.setFont(new java.awt.Font("Roboto", 0, 14));
        txtAddress.setPreferredSize(new java.awt.Dimension(330, 40));
        
        formPanel.add(txtAddress);
        
        mainPanel.add(formPanel);
        
        separator.setPreferredSize(new java.awt.Dimension(460, 3));
        mainPanel.add(separator);
        
        pnlTableContainer.setPreferredSize(new java.awt.Dimension(500, 400));
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
            "Danh sách hãng sản xuất",
            new String[]{
                "STT",
                "Mã hãng ",
                "Tên hãng",
                "Địa chỉ"
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

                HangSanXuatDTO hsx = new HangSanXuatDTO(id, "", "", 1);

                boolean result = HSX_BUS.delete(hsx);

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
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        if (isValidateFields()) {
            HangSanXuatDTO hsx = getInputFields();
            System.out.println("DTO DiaChi: " + hsx.getDiaChi());
            System.out.println("GUI DiaChi: " + txtAddress.getText());
            HSX_BUS.add(hsx);
            JOptionPane.showMessageDialog(
                this,
                "Thêm thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.out.println("Mã: " + hsx.getMaHang());
            System.out.println("Tên: " + hsx.getTenHang());
            loadTable();
        }
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        String search = txtSearch.getText().toLowerCase().trim();

        if (search.isEmpty()) {
            loadTable();
            return;
        }

        DefaultTableModel modal = (DefaultTableModel) table.getModel();
        modal.setRowCount(0);

        String searchType = cboxSearch.getSelectedItem().toString();
        List<HangSanXuatDTO> listsearch = HSX_BUS.search(search, searchType);

        int stt = 1;
        for (HangSanXuatDTO e : listsearch) {
            modal.addRow(new Object[]{
                String.valueOf(stt++),
                e.getMaHang(),
                e.getTenHang(),
                e.getDiaChi()
            });
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
                    return;
                }
                
                int id = Integer.parseInt(table.getValueAt(row, 1).toString());
                String ten = txtManufacturerName.getText();
                String diaChi = txtAddress.getText().trim();
                HangSanXuatDTO e = new HangSanXuatDTO(id, ten, diaChi, 1);

                HSX_BUS.update(e);
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
        txtManufacturerName.setText("");
        txtAddress.setText("");
        txtSearch.setText("");
        cboxSearch.setSelectedIndex(0);
        loadTable();
    }
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tablePanel.getTable().getSelectedRow();
        txtManufacturerName.setText(
            tablePanel.getTable().getValueAt(row, 2).toString()
        );
        txtAddress.setText(
            tablePanel.getTable().getValueAt(row, 3).toString()
        );
    }
}
