package GUI.Panel;

import GUI.Component.ButtonToolBar; // Nhớ import ButtonToolBar
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import DAO.NhaCungCapDAO;
import java.util.ArrayList;
import GUI.Component.TablePanel;
public class NhaCungCap extends JPanel {
    private NhaCungCapBUS bus = new NhaCungCapBUS();
    private TablePanel tblNhaCungCap;
    

    public NhaCungCap() {
        initUI();
        
    }

    private void initUI() {
        
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10,10,10,10));
        
        // PANEL
        // Để các panel này tự nhiên thì mặc định nền của chúng sẽ là màu xám (Panel.background)
        JPanel pnlTop = new JPanel();
        JPanel pnlTopLeft = new JPanel();
        JPanel pnlTopRight = new JPanel();
        
        JPanel pnlMain = new JPanel();
        JPanel pnlMainLeft = new JPanel();
        JPanel pnlMainRight = new JPanel();
        
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        
        // TOP PANEL
        pnlTop.setLayout(new BorderLayout());
        pnlTop.add(pnlTopLeft, BorderLayout.WEST);
        pnlTop.add(pnlTopRight, BorderLayout.EAST);
        
        // Lấy màu nền xám mặc định của hệ thống
        Color defaultGray = UIManager.getColor("Panel.background");
        
        // --- LEFT ---
        // Sử dụng ButtonToolBar thay cho JButton + FontIcon
        ButtonToolBar btnTopThem = new ButtonToolBar("Thêm", "icon/add.svg", 80, 60, 14, "ADD_NHACUNGCAP");
        ButtonToolBar btnTopSua = new ButtonToolBar("Sửa", "icon/update.svg", 80, 60, 14, "EDIT_NHACUNGCAP");
        ButtonToolBar btnTopXoa = new ButtonToolBar("Xóa", "icon/delete.svg", 80, 60, 14, "DELETE_NHACUNGCAP");
        ButtonToolBar btnTopXuatFile = new ButtonToolBar("Xuất file", "icon/export.svg", 80, 60, 14, "EXPORT_NHACUNGCAP");
        
        // Chỉnh nền các nút thành màu xám cho chìm vào pnlTopLeft
        btnTopThem.setBackground(defaultGray);
        btnTopSua.setBackground(defaultGray);
        btnTopXoa.setBackground(defaultGray);
        btnTopXuatFile.setBackground(defaultGray);
        
        pnlTopLeft.add(btnTopThem);
        pnlTopLeft.add(btnTopSua);
        pnlTopLeft.add(btnTopXoa);
        pnlTopLeft.add(btnTopXuatFile);
                
        // --- RIGHT ---
        JTextField txtTimKiem = new JTextField("Nhập mã nhà cung cấp");
        txtTimKiem.setPreferredSize(new Dimension(200,32));
        
        ButtonToolBar btnTopLamMoi = new ButtonToolBar("", "icon/reload.svg", 80, 60, 14, "VIEW_NHACUNGCAP");
        btnTopLamMoi.setBackground(defaultGray); // Chỉnh màu xám
        
        pnlTopRight.add(txtTimKiem);
        pnlTopRight.add(btnTopLamMoi);
            
        // --- MAIN PANEL ---
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setLayout(new BorderLayout(10,0));
        pnlMain.setBorder(new EmptyBorder(10,0,0,0));
        
        pnlMain.add(pnlMainLeft, BorderLayout.WEST);
        pnlMain.add(pnlMainRight, BorderLayout.CENTER);

        // Main Left
        pnlMainLeft.setPreferredSize(new Dimension(250,0));
        pnlMainLeft.setBorder(new EmptyBorder(0,10,0,0));
        pnlMainLeft.setBackground(defaultGray); // Bạn có thể linh hoạt set màu trắng hoặc xám tùy thiết kế
        
    }
    
  
}