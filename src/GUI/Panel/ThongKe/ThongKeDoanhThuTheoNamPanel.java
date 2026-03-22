/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.ThongKe;
import BUS.ThongKeBUS;
import DTO.ThongKeTheoNamDTO;
import GUI.Component.TablePanel;
import gui.barchart.ModelChart;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import utils.JTableExporter;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;
import java.util.Locale;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author user
 */
public class ThongKeDoanhThuTheoNamPanel extends JPanel{
    private final int currentYear = LocalDate.now().getYear();
    private List<ThongKeTheoNamDTO> listTK = new ThongKeBUS().getStatisticFromYearToYear(currentYear - 5, currentYear);
    
    private TablePanel tablePanel;
    private JPanel mainPanel;
    private gui.barchart.Chart barChart;
    private JPanel filterPanel;
    private JLabel lblFromYear;
    private JTextField txtStartYear;
    private JLabel lblToYear;
    private JTextField txtEndYear;
    private JButton btnFilter;
    private JButton btnRefresh;
    private JButton btnExportExcel;
    
    public ThongKeDoanhThuTheoNamPanel() {
        initComponents();
        chartLayout();
        loadDataset();
    }
    private void chartLayout() {
        barChart.addLegend("Doanh thu", new Color(135, 189, 245));
        barChart.addLegend("Chi phí", new Color(245, 189, 135));
        barChart.addLegend("Lợi nhuận", new Color(139, 225, 196));

        barChart.start();
    }
    private void loadChart() {
        for (ThongKeTheoNamDTO e : listTK) {
            barChart.addData(new ModelChart("Năm " + e.getNam(), new double[]{e.getDoanhThu(), e.getChiPhi(), e.getLoiNhuan()}));
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
            ThongKeTheoNamDTO e = listTK.get(i);
            data[i][0] = e.getNam() + "";
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
        lblFromYear = new JLabel();
        txtStartYear = new JTextField();
        lblToYear = new JLabel();
        txtEndYear = new JTextField();
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
            "Năm", "Doanh thu", "Chi phí", "Lợi nhuận"
        };
            
        tablePanel = new TablePanel("Thống kê doanh thu theo năm", header);
        
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        
        filterPanel.setBackground(new java.awt.Color(247, 247, 247));
        filterPanel.setPreferredSize(new java.awt.Dimension(1188, 30));
        filterPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 0));
    
        lblFromYear.setFont(new java.awt.Font("Roboto", 0, 12)); 
        lblFromYear.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFromYear.setText("Từ năm");
        lblFromYear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblFromYear.setPreferredSize(new java.awt.Dimension(60, 30));
        
        filterPanel.add(lblFromYear);
        filterPanel.add(txtStartYear);
        
        lblToYear.setFont(new java.awt.Font("Roboto", 0, 12)); 
        lblToYear.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblToYear.setText("Đến năm");
        lblToYear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblToYear.setPreferredSize(new java.awt.Dimension(60, 30));
        
        filterPanel.add(lblToYear);
        filterPanel.add(txtEndYear);
        
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
                btnReloadActionPerformed(evt);
            }
        });
        filterPanel.add(btnRefresh);
        
        btnExportExcel.setBackground(new java.awt.Color(0, 153, 102));
        btnExportExcel.setForeground(new java.awt.Color(204, 255, 204));
        btnExportExcel.setText("Xuất excel");
        btnExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        filterPanel.add(btnExportExcel);
        
        mainPanel.add(filterPanel, java.awt.BorderLayout.PAGE_START);

        add(mainPanel, java.awt.BorderLayout.CENTER);
    }
    private boolean isValidFilterFields() {
        String startText = txtStartYear.getText().trim();
        String endText = txtEndYear.getText().trim();

        // Kiểm tra rỗng
        if (startText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống!");
            txtStartYear.requestFocus();
            return false;
        }

        if (endText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống!");
            txtEndYear.requestFocus();
            return false;
        }

        int fromYear, toYear;

        try {
            fromYear = Integer.parseInt(startText);
            toYear = Integer.parseInt(endText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số không hợp lệ!");
            txtStartYear.setText("");
            txtEndYear.setText("");
            return false;
        }

        // Kiểm tra khoảng năm (FIX LOGIC)
        if (fromYear <= 1900 || fromYear > currentYear
                || toYear <= 1900 || toYear > currentYear) {
            JOptionPane.showMessageDialog(this, "Số năm phải từ 1900 đến " + currentYear);
            return false;
        }

        // Kiểm tra thứ tự
        if (toYear < fromYear) {
            JOptionPane.showMessageDialog(this, "Năm kết thúc phải >= năm bắt đầu!");
            txtEndYear.requestFocus();
            txtEndYear.selectAll();
            return false;
        }

        // Kiểm tra khoảng cách
        if (toYear - fromYear >= 10) {
            JOptionPane.showMessageDialog(this, "Hai năm không cách nhau quá 10 năm");
            txtStartYear.requestFocus();
            txtStartYear.selectAll();
            return false;
        }

        return true;
    }
    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {
        if (isValidFilterFields()) {
            int fromYear = Integer.parseInt(txtStartYear.getText());
            int toYear = Integer.parseInt(txtEndYear.getText());

            listTK = new ThongKeBUS().getStatisticFromYearToYear(fromYear, toYear);
            loadDataset();
        }
    }
    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
        JTableExporter.exportJTableToExcel(tablePanel.getTable());
    }
    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
        txtStartYear.setText("");
        txtEndYear.setText("");

        listTK = new ThongKeBUS().getStatisticFromYearToYear(currentYear - 5, currentYear);
        loadDataset();
    }
}
