/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.ThongKe;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import GUI.Component.TablePanel;
import gui.barchart.ModelChart;
import utils.JTableExporter;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author user
 */
public class ThongKeDoanhThuTungNgayTrongThangPanel extends JPanel {
    private final int currentMonth = LocalDate.now().getMonthValue();
    private final int currentYear = LocalDate.now().getYear();
    private List<ThongKeDTO> listTK = new ThongKeBUS().getStatisticDaysByMonthYear(currentMonth, currentYear);

    private JPanel mainPanel;
    private gui.barchart.Chart barChart;
    private TablePanel tablePanel;
    private JPanel filterPanel;
    private JLabel lblMonth;
    private com.toedter.calendar.JMonthChooser monthChooser;
    private JLabel lblYear;
    private com.toedter.components.JSpinField spnYear;
    private JButton btnFilter;
    private JButton btnRefresh;
    private JButton btnExportExcel;
    
    public ThongKeDoanhThuTungNgayTrongThangPanel() {
        initComponents();
        chartLayout();
        loadDataset();
    }
    private void chartLayout() {
        monthChooser.setMonth(currentMonth - 1);
        spnYear.setValue(currentYear);

        barChart.addLegend("Doanh thu", new Color(135, 189, 245));
        barChart.addLegend("Chi phí", new Color(245, 189, 135));
        barChart.addLegend("Lợi nhuận", new Color(139, 225, 196));

        barChart.start();
    }
    private void loadChart() {
        double sum_doanhthu = 0;
        double sum_chiphi = 0;
        double sum_loinhuan = 0;

        for (int day = 0; day < listTK.size(); day++) {
            sum_doanhthu += listTK.get(day).getDoanhThu();
            sum_chiphi += listTK.get(day).getChiPhi();
            sum_loinhuan += listTK.get(day).getLoiNhuan();
            if ((day + 1) % 3 == 0 || day == listTK.size() - 1) {
                int startDay = day - 2;
                if (startDay < 0) {
                    startDay = 0;
                }
                int endDay = day;
                barChart.addData(new ModelChart("Ngày " + (startDay + 1) + " - " + (endDay + 1), new double[]{sum_doanhthu, sum_chiphi, sum_loinhuan}));
                sum_doanhthu = 0;
                sum_chiphi = 0;
                sum_loinhuan = 0;
            }
        }
    }
    private void loadDataset() {
        barChart.clear();
        loadChart();

        Object[][] data = new Object[listTK.size()][4];
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.');

        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (int i = 0; i < listTK.size(); i++) {
            ThongKeDTO e = listTK.get(i);
            data[i][0] = sdf.format(e.getThoiGian());
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
        lblMonth = new JLabel();
        monthChooser = new com.toedter.calendar.JMonthChooser();
        lblYear = new JLabel();
        spnYear = new com.toedter.components.JSpinField();
        btnFilter = new JButton();
        btnRefresh = new JButton();
        btnExportExcel = new JButton();
        
        setBackground(new java.awt.Color(230, 245, 245));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 6));
        
        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        mainPanel.setLayout(new java.awt.BorderLayout(4, 4));
        mainPanel.add(barChart, java.awt.BorderLayout.CENTER);
        
        String[] header = {
            "Thời gian", "Doanh thu", "Chi phí", "Lợi nhuận"
        };

        tablePanel = new TablePanel("Thống kê theo ngày", header);
        
        mainPanel.add(tablePanel, java.awt.BorderLayout.SOUTH);
        
        filterPanel.setBackground(new java.awt.Color(247, 247, 247));
        filterPanel.setPreferredSize(new java.awt.Dimension(1188, 30));
        filterPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 0));
        
        lblMonth.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblMonth.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonth.setText("Tháng");
        lblMonth.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblMonth.setPreferredSize(new java.awt.Dimension(60, 30));
        filterPanel.add(lblMonth);
        
        monthChooser.setPreferredSize(new java.awt.Dimension(130, 26));
        filterPanel.add(monthChooser);
        
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
        return true;
    }
    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {
        int month = monthChooser.getMonth() + 1;
        int year = spnYear.getValue();

        listTK = new ThongKeBUS().getStatisticDaysByMonthYear(month, year);
        loadDataset();
    }
    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {
        JTableExporter.exportJTableToExcel(tablePanel.getTable());
    }
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        monthChooser.setMonth(currentMonth - 1);
        spnYear.setValue(currentYear);

        listTK = new ThongKeBUS().getStatisticDaysByMonthYear(currentMonth, currentYear);
        loadDataset();
    }
}
