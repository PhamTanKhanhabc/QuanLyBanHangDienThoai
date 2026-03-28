/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;
import GUI.Dialog.ThuocTinhHangSanXuatDialog;
import GUI.Dialog.ThuocTinhLoaiSanPhamDialog;
import javax.swing.*;
import java.util.List;
import com.formdev.flatlaf.extras.FlatSVGIcon; 
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class ThuocTinhPanel extends JPanel{
    List<JButton> listItem;
    private JButton btnLoaiSanPham;
    private JButton btnHangSanXuat;
    private JPanel mainPanel;

    public ThuocTinhPanel(){
        initComponents();
        initLayout();
    }
    private void initLayout() {
        listItem = new ArrayList<>();
        listItem.add(btnLoaiSanPham);
        listItem.add(btnHangSanXuat);
        
        // Border radius
        for (JButton item : listItem) {
            item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        }
    }
    private void initComponents(){
        mainPanel = new javax.swing.JPanel();
        btnLoaiSanPham = new javax.swing.JButton();
        btnHangSanXuat = new javax.swing.JButton();
        
        setBackground(new java.awt.Color(230, 240, 255));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout());
        
        mainPanel.setBackground(new java.awt.Color(230, 240, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 240, 255), 40));
        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
        
        btnLoaiSanPham.setPreferredSize(new Dimension(400, 300));
        btnLoaiSanPham.setFont(new java.awt.Font("Roboto Mono Medium", 0, 36));
        btnLoaiSanPham.setForeground(new java.awt.Color(51, 51, 51));
        btnLoaiSanPham.setIcon(new FlatSVGIcon("./icon/category.svg", 64, 64));
        btnLoaiSanPham.setText("LOẠI SẢN PHẨM");
        btnLoaiSanPham.setBorder(null);
        btnLoaiSanPham.setBorderPainted(false);
        btnLoaiSanPham.setFocusPainted(false);
        btnLoaiSanPham.setIconTextGap(16);
        btnLoaiSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiSanPhamActionPerformed(evt);
            }
        });
        mainPanel.add(btnLoaiSanPham);
        
        btnHangSanXuat.setPreferredSize(new Dimension(400, 300));
        btnHangSanXuat.setFont(new java.awt.Font("Roboto Mono Medium", 0, 36));
        btnHangSanXuat.setForeground(new java.awt.Color(51, 51, 51));
        btnHangSanXuat.setIcon(new FlatSVGIcon("./icon/map.svg", 64, 64));
        btnHangSanXuat.setText("HÃNG SẢN XUẤT");
        btnHangSanXuat.setBorder(null);
        btnHangSanXuat.setBorderPainted(false);
        btnHangSanXuat.setFocusPainted(false);
        btnHangSanXuat.setIconTextGap(16);
        btnHangSanXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHangSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHangSanXuatActionPerformed(evt);
            }
        });
        mainPanel.add(btnHangSanXuat);
        
        add(mainPanel, java.awt.BorderLayout.CENTER);
    }
    private void btnLoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {
        ThuocTinhLoaiSanPhamDialog dialog = new ThuocTinhLoaiSanPhamDialog(null, true);
        dialog.setVisible(true);
    }

    private void btnHangSanXuatActionPerformed(java.awt.event.ActionEvent evt) {
        ThuocTinhHangSanXuatDialog dialog = new ThuocTinhHangSanXuatDialog(null, true);
        dialog.setVisible(true);
    }
}
