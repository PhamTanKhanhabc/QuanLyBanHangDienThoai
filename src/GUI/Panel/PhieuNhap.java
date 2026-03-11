package GUI.Panel;

import GUI.Component.ButtonToolBar; // Nhớ import class này vào nhé
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import BUS.PhieuNhapBUS;
import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import GUI.Component.*;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PhieuNhap extends JPanel {
    private TablePanel tblPhieuNhap;
    private PhieuNhapBUS bus = new PhieuNhapBUS();
    
    public PhieuNhap(){
        initUI();
        loadData();
    }
    
    public void initUI(){
        Color defaultGray = UIManager.getColor("Panel.background");
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10,10,10,10));
        
        //PANEL
        JPanel pnlTopBar = new JPanel();
        pnlTopBar.setLayout(new BorderLayout());
        
        JPanel pnlTopBarLeftSub = new JPanel();
        pnlTopBarLeftSub.setLayout(new FlowLayout());

        JPanel pnlTopBarRightSub = new JPanel();
        pnlTopBarRightSub.setLayout(new FlowLayout());
        
        JPanel pnlMain = new JPanel();
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(10,0,10,0));
        pnlMain.setLayout(new BorderLayout(10, 0));
        
        JPanel pnlMainContent = new JPanel();
        JPanel pnlMainRight = new JPanel();
        pnlMainRight.setPreferredSize(new Dimension(250, 0));
        
        pnlTopBar.add(pnlTopBarLeftSub, BorderLayout.WEST);
        pnlTopBar.add(pnlTopBarRightSub, BorderLayout.EAST);
        pnlMain.add(pnlMainRight, BorderLayout.WEST);
        pnlMain.add(pnlMainContent, BorderLayout.CENTER);
        
        this.add(pnlTopBar, BorderLayout.NORTH); 
        this.add(pnlMain, BorderLayout.CENTER);
        
        // --- TOP BAR PANEL IMPLEMENTS ---
        
        // 1. Left Sub (Thay thế toàn bộ FontIcon và JButton cũ bằng ButtonToolBar)
        ButtonToolBar btnTopBarThem = new ButtonToolBar("Thêm", "icon/add.svg", 80, 60, 14, "ADD_PHIEUNHAP");
        ButtonToolBar btnTopBarSua = new ButtonToolBar("Sửa", "icon/update.svg", 80, 60, 14, "EDIT_PHIEUNHAP");
        ButtonToolBar btnTopBarXoa = new ButtonToolBar("Xóa", "icon/delete.svg", 80, 60, 14, "DELETE_PHIEUNHAP");
        ButtonToolBar btnTopBarXuatFile = new ButtonToolBar("Xuất file", "icon/export.svg", 80, 60, 14, "EXPORT_PHIEUNHAP");
        
        // Đặt màu nền trắng cho các nút ở Top Bar để tiệp với nền của panel chứa chúng
        btnTopBarThem.setBackground(defaultGray);
        btnTopBarSua.setBackground(defaultGray);
        btnTopBarXoa.setBackground(defaultGray);
        btnTopBarXuatFile.setBackground(defaultGray);


        
        pnlTopBarLeftSub.add(btnTopBarThem);
        pnlTopBarLeftSub.add(btnTopBarSua);
        pnlTopBarLeftSub.add(btnTopBarXoa);
        pnlTopBarLeftSub.add(btnTopBarXuatFile);
        
        // 2. Right Sub
        JTextField txtTopBarTimKiem = new JTextField("Nhập mã phiếu nhập");
        txtTopBarTimKiem.setPreferredSize(new Dimension(200, 32));
        
        ButtonToolBar btnTopBarLamMoi = new ButtonToolBar("", "icon/reload.svg", 80, 60, 14, "VIEW_PHIEUNHAP");
        btnTopBarLamMoi.setBackground(Color.WHITE);
        pnlTopBarRightSub.add(txtTopBarTimKiem);
        pnlTopBarRightSub.add(btnTopBarLamMoi);
        
        
        // --- MAIN PANEL IMPLEMENTS ---
        
        // MAIN RIGHT
        pnlMainRight.setLayout(new BorderLayout());
        
        JPanel pnlMainRightFilter = new JPanel();
        JPanel pnlMainRightInfo = new JPanel();

        // MAIN RIGHT FILTER
        pnlMainRightFilter.setLayout(new BoxLayout(pnlMainRightFilter,BoxLayout.Y_AXIS));
        pnlMainRightFilter.setBorder(new EmptyBorder(20,10,10,10));
        
        
        // Nút Lọc
        ButtonToolBar btnMainFilterLoc = new ButtonToolBar("Lọc", "icon/filter.svg", 80, 60, 14, "VIEW_PHIEUNHAP");
        btnTopBarLamMoi.setBackground(defaultGray);
        btnMainFilterLoc.setBackground(defaultGray);
        Dimension labelSize = new Dimension(65, 30); // Ép nhãn rộng bằng nhau
        
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setPreferredSize(labelSize);
        JDateChooser dateTuNgay = new JDateChooser();
        dateTuNgay.setDateFormatString("dd/MM/yyyy");
        dateTuNgay.setPreferredSize(new Dimension(140,25));
        
        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setPreferredSize(labelSize);
        JDateChooser dateDenNgay = new JDateChooser();
        dateDenNgay.setDateFormatString("dd/MM/yyyy");
        dateDenNgay.setPreferredSize(new Dimension(140,25));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        row1.add(lblTuNgay);
        row1.add(dateTuNgay);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        row2.add(lblDenNgay);
        row2.add(dateDenNgay);
        
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        row3.add(btnMainFilterLoc);

        pnlMainRightFilter.add(row1);
        pnlMainRightFilter.add(row2);
        pnlMainRightFilter.add(row3);
        
        pnlMainRight.add(pnlMainRightFilter, BorderLayout.NORTH);
        pnlMainRight.add(pnlMainRightInfo, BorderLayout.CENTER);
        
        pnlMainContent.setLayout(new BorderLayout());
        
        
        //MAIN CONTENT
        
        String[] headers = {"Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp", "Ngày lập", "Tổng tiền", "Trạng thái"};
        tblPhieuNhap = new TablePanel("DANH SÁCH PHIẾU NHẬP", headers);
        pnlMainContent.add(tblPhieuNhap, BorderLayout.CENTER);
    }
    
    public void loadData(){
        ArrayList<PhieuNhapDTO> list = bus.getAll();
        
        Object[][] data = new Object[list.size()][6];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Định dạng ngày
        DecimalFormat df = new DecimalFormat("#,###.##"); // Định dạng tiền tệ (VD: 150,000,000)
        
        // 4. Đổ dữ liệu từ List sang mảng
        for (int i = 0; i < list.size(); i++) {
            PhieuNhapDTO pn = list.get(i);
            
            data[i][0] = "PN" + pn.getMaPHN();       // Thêm chữ PN cho giống thiết kế của bạn
            data[i][1] = pn.getMaNV();
            data[i][2] = pn.getMaNCC();              
            data[i][3] = sdf.format(pn.getNgay());   // Đổi Timestamp thành chuỗi ngày tháng
            data[i][4] = df.format(pn.getTongTien());// Đổi Double thành chuỗi tiền tệ
            data[i][5] = pn.getTrangThai() == 1 ? "Hoàn thành" : "Đã hủy";
        }
        
        // 5. Cập nhật dữ liệu lên bảng
        tblPhieuNhap.setData(data);
    }
            
}