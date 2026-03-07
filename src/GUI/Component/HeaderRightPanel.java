/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author user
 */
public class HeaderRightPanel extends JPanel {
    private JComboBox<String> cboxSearch;
    private JTextField txtSearch;
    private JButton btnReload;
    public HeaderRightPanel() {
        initComponents();
        config();
    }

    private void initComponents() {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(590, 100));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 16, 24));

        cboxSearch = new JComboBox<>();
        txtSearch = new JTextField();
        btnReload = new JButton();
        
        cboxSearch.setToolTipText(""); 
        cboxSearch.setPreferredSize(new Dimension(100, 40));
        add(cboxSearch);
        
        txtSearch.setToolTipText("Tìm kiếm");
        txtSearch.setPreferredSize(new Dimension(200, 40));
        txtSearch.setSelectionColor(new Color(230,245,245));
        add(txtSearch);

        btnReload.setIcon(new FlatSVGIcon("./icon/reload.svg", 24, 24));
        btnReload.setToolTipText("Làm mới");//
        btnReload.setBorder(null);
        btnReload.setBorderPainted(false);
        btnReload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReload.setFocusPainted(false);
        btnReload.setFocusable(false);
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReload.setPreferredSize(new Dimension(40, 40));
        add(btnReload);
        btnReload.addActionListener(e -> reloadAction());
    }
    private void config() {

        txtSearch.putClientProperty(
                FlatClientProperties.PLACEHOLDER_TEXT,
                "Tìm kiếm..."
        );

        txtSearch.putClientProperty(
                FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("./icon/search.svg", 24, 24)
        );

        String[] searchType = {"Tất cả","Mã","Tên"};
        cboxSearch.setModel(new DefaultComboBoxModel<>(searchType));
    }
    private void reloadAction() {
        txtSearch.setText("");        // xóa nội dung tìm kiếm
        cboxSearch.setSelectedIndex(0); // chọn lại "Tất cả"
    }
    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JComboBox<String> getCboxSearch() {
        return cboxSearch;
    }

    public JButton getBtnReload() {
        return btnReload;
    }
}
