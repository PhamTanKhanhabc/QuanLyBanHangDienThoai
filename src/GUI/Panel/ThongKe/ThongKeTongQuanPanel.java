/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel.ThongKe;

import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import GUI.Component.TablePanel;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import gui.curvechart.CurveChart;
import gui.curvechart.ModelChart2;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
/**
 *
 * @author user
 */
public class ThongKeTongQuanPanel extends JPanel{
    
    private final List<ThongKeDTO> listTK = new ThongKeBUS().getStatistic7DaysAgo();
    
    private CurveChart curveChart;

    private JLabel lblProductIcon;
    private JLabel lblCustomerIcon;
    private JLabel lblEmployeeIcon;

    private JLabel lblProductTitle;
    private JLabel lblCustomerTitle;
    private JLabel lblEmployeeTitle;

    private JLabel lblTotalProduct;
    private JLabel lblTotalCustomer;
    private JLabel lblTotalEmployee;

    private JLabel lblChartTitle;

    private JPanel pnlSummary;
    private JPanel pnlProduct;
    private JPanel pnlCustomer;
    private JPanel pnlEmployee;

    private JPanel pnlProductIcon;
    private JPanel pnlCustomerIcon;
    private JPanel pnlEmployeeIcon;

    private JPanel pnlProductInfo;
    private JPanel pnlCustomerInfo;
    private JPanel pnlEmployeeInfo;

    private JPanel pnlChart;
    private JPanel pnlChartHeader;
    private JPanel pnlTable;
    
    private TablePanel tablePanel;
    
    public ThongKeTongQuanPanel() {
        initComponents();
        initChart();
        initHeader();
        tableLayout();
    }
    private void initHeader() {
        int tongSanPham = new SanPhamBUS().getTongSanPham();
        lblTotalProduct.setText(String.valueOf(tongSanPham));
        
        int tongKH = new KhachHangBUS().getSoLuongKH();
        lblTotalCustomer.setText(String.valueOf(tongKH));

        int tongNV = new NhanVienBUS().getSoLuongNV();
        lblTotalEmployee.setText(String.valueOf(tongNV));
    }
    private void initChart() {
        lblChartTitle.setText("thống kê doanh thu 7 ngày gần nhất".toUpperCase());

        curveChart.addLegend("Doanh thu", new Color(54, 4, 143), new Color(104, 49, 200));
        curveChart.addLegend("Chi phí", new Color(211, 84, 0), new Color(230, 126, 34));
        curveChart.addLegend("Lợi nhuận", new Color(22, 163, 74), new Color(34, 197, 94));

        loadDataChart();

        curveChart.start();
    }
    public void loadDataChart() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (ThongKeDTO e : listTK) {
            curveChart.addData(new ModelChart2(sdf.format(e.getThoiGian()), new double[]{e.getDoanhThu(), e.getChiPhi(), e.getLoiNhuan()}));
        }
    }
    private void tableLayout() {
        String[] header = {"STT", "Thời gian", "Doanh thu", "Chi phí", "Lợi nhuận"};

        tablePanel = new TablePanel("Thống kê doanh thu", header);

        pnlTable.removeAll();
        pnlTable.add(tablePanel, BorderLayout.CENTER);

        loadTable(listTK);
    }
    public void loadTable(List<ThongKeDTO> list) {
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        Object[][] data = new Object[list.size()][5];
        
        int i = 0;
        int stt = 1;
        
        for (ThongKeDTO e : list) {
            data[i][0] = stt;
            data[i][1] = sdf.format(e.getThoiGian());
            data[i][2] = formatter.format(e.getDoanhThu()) + " đ";
            data[i][3] = formatter.format(e.getChiPhi()) + " đ";
            data[i][4] = formatter.format(e.getLoiNhuan()) + " đ";

            i++;
            stt++;
        }
        tablePanel.setData(data);
    }
    private void initComponents() {
        pnlSummary = new JPanel();
        pnlProduct  = new JPanel();
        pnlCustomer = new JPanel();
        pnlEmployee = new JPanel();

        pnlProductIcon  = new JPanel();
        pnlCustomerIcon = new JPanel();
        pnlEmployeeIcon = new JPanel();

        pnlProductInfo  = new JPanel();
        pnlCustomerInfo = new JPanel();
        pnlEmployeeInfo = new JPanel();

        pnlChart = new JPanel();
        pnlChartHeader = new JPanel();
        pnlTable = new JPanel();

        lblProductIcon  = new JLabel();
        lblCustomerIcon = new JLabel();
        lblEmployeeIcon = new JLabel();

        lblProductTitle  = new JLabel();
        lblCustomerTitle = new JLabel();
        lblEmployeeTitle = new JLabel();

        lblTotalProduct  = new JLabel();
        lblTotalCustomer = new JLabel();
        lblTotalEmployee = new JLabel();

        lblChartTitle = new JLabel();

        curveChart = new CurveChart();

        setBackground(new java.awt.Color(230, 245, 245));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 6));
        
        pnlSummary.setBackground(new java.awt.Color(230, 245, 245));
        pnlSummary.setPreferredSize(new java.awt.Dimension(100, 110));
        pnlSummary.setLayout(new java.awt.GridLayout(1, 3, 16, 8));
        
        pnlProduct.setBackground(new java.awt.Color(255, 255, 255));
        pnlProduct.setPreferredSize(new java.awt.Dimension(370, 100));
        pnlProduct.setLayout(new java.awt.BorderLayout());
        
        pnlProductIcon.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductIcon.setPreferredSize(new java.awt.Dimension(120, 100));
        pnlProductIcon.setLayout(new java.awt.BorderLayout());
        
        lblProductIcon.setBackground(new Color(255,255,255));
        lblProductIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblProductIcon.setIcon(new FlatSVGIcon("./icon/product_52.svg"));
        lblProductIcon.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        lblProductIcon.setHorizontalTextPosition(SwingConstants.CENTER);

        pnlProductIcon.add(lblProductIcon, BorderLayout.CENTER);

        pnlProduct.add(pnlProductIcon, BorderLayout.WEST);
        
        pnlProductInfo.setBackground(new Color(255,255,255));
        pnlProductInfo.setPreferredSize(new Dimension(120,100));
        pnlProductInfo.setLayout(new BoxLayout(pnlProductInfo, BoxLayout.Y_AXIS));
        pnlProductInfo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        
        lblTotalProduct.setFont(new java.awt.Font("Roboto Mono", 1, 36)); // NOI18N
        lblTotalProduct.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalProduct.setText("50");
        lblTotalProduct.setPreferredSize(new java.awt.Dimension(100, 16));
        
        lblProductTitle.setFont(new Font("Roboto", Font.ITALIC, 14));
        lblProductTitle.setForeground(new Color(51,51,51));
        lblProductTitle.setText("Sản phẩm hiện có trong kho");
        
        pnlProductInfo.add(lblTotalProduct);
        pnlProductInfo.add(lblProductTitle);
        
        pnlProduct.add(pnlProductInfo, BorderLayout.CENTER);
        pnlSummary.add(pnlProduct);
        
        pnlCustomer.setBackground(new Color(255,255,255));
        pnlCustomer.setPreferredSize(new Dimension(370,100));
        pnlCustomer.setLayout(new BorderLayout());

        pnlCustomerIcon.setBackground(new Color(255,255,255));
        pnlCustomerIcon.setPreferredSize(new Dimension(120,100));
        pnlCustomerIcon.setLayout(new BorderLayout());

        lblCustomerIcon.setBackground(new Color(255,255,255));
        lblCustomerIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblCustomerIcon.setIcon(new FlatSVGIcon("./icon/customer_52.svg"));
        lblCustomerIcon.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        lblCustomerIcon.setHorizontalTextPosition(SwingConstants.CENTER);

        pnlCustomerIcon.add(lblCustomerIcon, BorderLayout.CENTER);

        pnlCustomer.add(pnlCustomerIcon, BorderLayout.WEST);

        pnlCustomerInfo.setBackground(new Color(255,255,255));
        pnlCustomerInfo.setPreferredSize(new Dimension(120,100));
        pnlCustomerInfo.setLayout(new BoxLayout(pnlCustomerInfo, BoxLayout.Y_AXIS));
        pnlCustomerInfo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        
        lblTotalCustomer.setFont(new Font("Roboto Mono", Font.BOLD, 36));
        lblTotalCustomer.setForeground(new Color(51,51,51));
        lblTotalCustomer.setText("50");
        lblTotalCustomer.setPreferredSize(new Dimension(100,16));
        
        
        lblCustomerTitle = new JLabel("Khách hàng từ trước đến nay");
        lblCustomerTitle.setFont(new Font("Roboto", Font.ITALIC, 14));
        lblCustomerTitle.setForeground(new Color(51, 51, 51));
        
        lblTotalCustomer.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCustomerTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCustomerInfo.add(lblTotalCustomer);
        pnlCustomerInfo.add(lblCustomerTitle);
        
        pnlCustomer.add(pnlCustomerInfo, BorderLayout.CENTER);
        
        pnlSummary.add(pnlCustomer);
        
        pnlEmployee.setBackground(Color.WHITE);
        pnlEmployee.setPreferredSize(new Dimension(370,100));
        pnlEmployee.setLayout(new BorderLayout());
        
        pnlEmployeeIcon.setBackground(Color.WHITE);
        pnlEmployeeIcon.setPreferredSize(new Dimension(120,100));
        pnlEmployeeIcon.setLayout(new BorderLayout());
        
        lblEmployeeIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmployeeIcon.setIcon(new FlatSVGIcon("./icon/man_52.svg"));
        lblEmployeeIcon.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        
        pnlEmployeeIcon.add(lblEmployeeIcon, BorderLayout.CENTER);
        
        pnlEmployeeInfo.setBackground(Color.WHITE);
        pnlEmployeeInfo.setLayout(new BoxLayout(pnlEmployeeInfo, BoxLayout.Y_AXIS));
        pnlEmployeeInfo.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        
        lblTotalEmployee.setFont(new Font("Roboto Mono", Font.BOLD, 36));
        lblTotalEmployee.setForeground(new Color(51,51,51));
        lblTotalEmployee.setText("50");
        
        lblEmployeeTitle.setText("Nhân viên đang hoạt động");
        lblEmployeeTitle.setFont(new Font("Roboto", Font.ITALIC, 14));
        lblEmployeeTitle.setForeground(new Color(51,51,51));
        
        lblTotalEmployee.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEmployeeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        pnlEmployeeInfo.add(lblTotalEmployee);
        pnlEmployeeInfo.add(lblEmployeeTitle);
        
        pnlEmployee.add(pnlEmployeeIcon, BorderLayout.WEST);
        pnlEmployee.add(pnlEmployeeInfo, BorderLayout.CENTER);
        
        pnlSummary.add(pnlEmployee);
        
        add(pnlSummary, BorderLayout.PAGE_START);
        
        pnlChart.setLayout(new BorderLayout());
        pnlChart.add(curveChart, BorderLayout.CENTER);
        
        pnlChartHeader.setBackground(Color.WHITE);
        pnlChartHeader.setPreferredSize(new Dimension(100, 30));
        pnlChartHeader.setLayout(new BorderLayout());
        
        lblChartTitle.setText("Thống kê");
        lblChartTitle.setFont(new Font("Roboto", Font.BOLD, 16));
        lblChartTitle.setHorizontalAlignment(SwingConstants.CENTER);

        pnlChartHeader.add(lblChartTitle, BorderLayout.CENTER);
        
        pnlChart.add(pnlChartHeader, BorderLayout.NORTH);
        
        add(pnlChart, BorderLayout.CENTER);
        
        pnlTable.setBackground(new Color(230,245,245));
        pnlTable.setPreferredSize(new Dimension(100,280));
        pnlTable.setLayout(new BorderLayout());
        
        add(pnlTable, BorderLayout.SOUTH);
    }
}
