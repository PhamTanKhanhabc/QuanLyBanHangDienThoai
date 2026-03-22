/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.ThongKe;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author user
 */
public class ThongKeDoanhThuPanel extends JPanel {
    private JTabbedPane tabPane;
    public ThongKeDoanhThuPanel() {
        initComponents();
        initLayout();
    }
    private void initLayout() {

        tabPane.addTab("Thống kê từng ngày", new ThongKeDoanhThuTungNgayTrongThangPanel());
        tabPane.addTab("Thống kê theo tháng", new ThongKeDoanhThuTheoThangPanel());
        tabPane.addTab("Thống kê theo năm", new ThongKeDoanhThuTheoNamPanel());

        this.add(tabPane);
    }
    private void initComponents() {

        tabPane = new javax.swing.JTabbedPane();

        setBackground(new java.awt.Color(230, 245, 245));
        setMinimumSize(new java.awt.Dimension(1130, 800));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 6));

        tabPane.setPreferredSize(new java.awt.Dimension(100, 30));
        add(tabPane, java.awt.BorderLayout.PAGE_START);
    }
    
}
