/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.ThongKe;

import BUS.ThongKeBUS;
import DTO.ThongKeTheoThangDTO;
import GUI.Component.TablePanel;
import gui.barchart.ModelChart;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utils.JTableExporter;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author user
 */
public class ThongKeDoanhThuTheoThangPanel extends JPanel {
    private final int currentYear = LocalDate.now().getYear();
    private List<ThongKeTheoThangDTO> listTK = new ThongKeBUS().getStatisticMonthByYear(currentYear);

    private TablePanel tablePanel;
    private JPanel mainPanel;
    private gui.barchart.Chart barChart;
    private JPanel filterPanel;
    private JLabel lblYear;
    private com.toedter.components.JSpinField spnYear;
    private JButton btnFilter;
    private JButton btnRefresh;
    private JButton btnExportExcel;


    public ThongKeDoanhThuTheoThangPanel () {
        initComponents();
        chartLayout();  
        loadDataset();
    }
    private void chartLayout() {
        spnYear.setValue(currentYear);
        
        barChart.addLegend("Doanh thu", new Color(135, 189, 245));
        barChart.addLegend("Chi phí", new Color(245, 189, 135));
        barChart.addLegend("Lợi nhuận", new Color(139, 225, 196));

        barChart.start();
    }
    private void loadChart() {
        for (ThongKeTheoThangDTO e : listTK) {
            barChart.addData(new ModelChart("Tháng " + e.getThang(), new double[]{e.getDoanhThu(), e.getChiPhi(), e.getLoiNhuan()}));
        }
    }
    private void loadDataset() {
        barChart.clear();
        loadChart();

        Object[][] data = new Object[listTK.size()][4];
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.');

        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        
        for (int i = 0; i < listTK.size(); i++) {
            ThongKeTheoThangDTO e = listTK.get(i);
            data[i][0] = e.getThang() + "";
            data[i][1] = formatter.format(e.getDoanhThu()) + " đ";
            data[i][2] = formatter.format(e.getChiPhi()) + " đ";
            data[i][3] = formatter.format(e.getLoiNhuan()) + " đ";
        }

        tablePanel.setData(data);

        barChart.start();
    }
    private void initComponents() {
        mainPanel = new JPanel();
        barChart = new gui.barchart.Chart();
        filterPanel = new JPanel();
        lblYear = new JLabel();
        spnYear = new com.toedter.components.JSpinField();
        btnFilter = new JButton();
        btnRefresh = new JButton();
        btnExportExcel = new JButton();
        
        setBackground(new java.awt.Color(230, 245, 245));
        setMinimumSize(new java.awt.Dimension(1130, 800));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 6));
        
        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        mainPanel.setLayout(new java.awt.BorderLayout(4, 4));
        mainPanel.add(barChart, java.awt.BorderLayout.CENTER);
        
        String[] header = {
            "Tháng", "Doanh thu", "Chi phí", "Lợi nhuận"
        };

        tablePanel = new TablePanel("Thống kê doanh thu theo tháng", header);
        
        mainPanel.add(tablePanel, java.awt.BorderLayout.SOUTH);

        filterPanel.setBackground(new java.awt.Color(247, 247, 247));
        filterPanel.setPreferredSize(new java.awt.Dimension(1188, 30));
        filterPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 0));
        
        lblYear.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblYear.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblYear.setText("Năm");
        lblYear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblYear.setPreferredSize(new java.awt.Dimension(40, 30));
        filterPanel.add(lblYear);
        
        spnYear.setPreferredSize(new java.awt.Dimension(80, 26));
        filterPanel.add(spnYear);
        
        btnFilter.setBackground(new java.awt.Color(51, 153, 255));
        btnFilter.setForeground(new java.awt.Color(204, 255, 255));
        btnFilter.setText("Thống kê");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        filterPanel.add(btnFilter);
        
        btnRefresh.setText("Làm mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        filterPanel.add(btnRefresh);
        
        btnExportExcel.setBackground(new java.awt.Color(0, 153, 102));
        btnExportExcel.setForeground(new java.awt.Color(204, 255, 204));
        btnExportExcel.setText("Xuất excel");
        btnExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelActionPerformed(evt);
            }
        });
        filterPanel.add(btnExportExcel);
        
        mainPanel.add(filterPanel, java.awt.BorderLayout.PAGE_START);
        
        add(mainPanel, java.awt.BorderLayout.CENTER);
    }
    private boolean isValidFilterFields() {
        int year = spnYear.getValue();

        try {
            if (year <= 1900 || year > currentYear) {
                JOptionPane.showMessageDialog(this, 
                    "Số năm phải từ 1900 đến " + currentYear);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Số không hợp lệ!");
            return false;
        }

        return true;
    }
    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {
        if (isValidFilterFields()) {
            int year = spnYear.getValue();

            listTK = new ThongKeBUS().getStatisticMonthByYear(year);
            loadDataset();
        }
    }
    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {
        JTableExporter.exportJTableToExcel(tablePanel.getTable());
    }
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        spnYear.setValue(currentYear);

        listTK = new ThongKeBUS().getStatisticMonthByYear(currentYear);
        loadDataset();
    }
}
