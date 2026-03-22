/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.ThongKe;
import DTO.TaiKhoanDTO;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class ThongKePanel extends JPanel{
    private TaiKhoanDTO tk;
    private JTabbedPane tabPane;
    public ThongKePanel(){
        initComponents();
        initLayout();
    }
    public ThongKePanel(TaiKhoanDTO tk) {
        this.tk = tk;
        initComponents();
        initLayout();
        checkRole();
    }
    //hàm kiểm tra quyền
    private void checkRole() {
        String role = tk.getMaVaiTro();
        
        if (role.equals("nvql") || role.equals("admin")) {
            tabPane.setEnabledAt(1, true);
        } else {
            tabPane.setEnabledAt(1, false);
        }
    }
    private void initLayout() {

        tabPane.addTab("Tổng quan", new ThongKeTongQuanPanel());
        tabPane.addTab("Doanh thu", new ThongKeDoanhThuPanel());
        
        this.add(tabPane);
    }
    private void initComponents() {

           tabPane = new javax.swing.JTabbedPane();

           setBackground(new java.awt.Color(230, 245, 245));
           setPreferredSize(new java.awt.Dimension(1130, 800));
           setLayout(new java.awt.BorderLayout(0, 6));

           tabPane.setPreferredSize(new java.awt.Dimension(100, 30));
           add(tabPane, java.awt.BorderLayout.PAGE_START);
    }
}
