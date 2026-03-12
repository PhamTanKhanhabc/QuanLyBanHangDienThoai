package GUI.Panel;

import GUI.Component.ButtonToolBar;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCap extends JPanel {
    private NhaCungCapBUS bus = new NhaCungCapBUS();
    private TablePanel tblNhaCungCap;
    
    // Khai báo HeaderRightPanel cho thanh tìm kiếm
    private HeaderRightPanel headerRightPanel;

    public NhaCungCap() {
        initUI();
        loadData();
    }

    private void initUI() {
        // --- 1. SETUP LAYOUT TỔNG THỂ ---
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // --- 2. HEADER PANEL (Chứa Nút chức năng & Thanh tìm kiếm) ---
        JPanel pnlTopBar = new JPanel(new BorderLayout());
        pnlTopBar.setBackground(Color.WHITE);
        
        JPanel pnlTopBarLeftSub = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnlTopBarLeftSub.setBackground(Color.WHITE);
        
        ButtonToolBar btnThem = new ButtonToolBar("Thêm", "icon/add.svg", 80, 60, 14, "ADD_NHACUNGCAP");
        ButtonToolBar btnSua = new ButtonToolBar("Sửa", "icon/update.svg", 80, 60, 14, "EDIT_NHACUNGCAP");
        ButtonToolBar btnXoa = new ButtonToolBar("Xóa", "icon/delete.svg", 80, 60, 14, "DELETE_NHACUNGCAP");
        ButtonToolBar btnInfo = new ButtonToolBar("Chi tiết", "icon/info.svg", 80, 60, 14, "INFO_NHACUNGCAP");
        ButtonToolBar btnImport = new ButtonToolBar("Nhập file", "icon/import.svg", 80, 60, 14, "IMPORT_NHACUNGCAP");
        ButtonToolBar btnExport = new ButtonToolBar("Xuất file", "icon/export.svg", 80, 60, 14, "EXPORT_NHACUNGCAP");
        
        btnThem.setBackground(Color.WHITE);
        btnSua.setBackground(Color.WHITE);
        btnXoa.setBackground(Color.WHITE);
        btnInfo.setBackground(Color.WHITE);
        btnImport.setBackground(Color.WHITE);
        btnExport.setBackground(Color.WHITE);
        
        pnlTopBarLeftSub.add(btnThem);
        pnlTopBarLeftSub.add(btnSua);
        pnlTopBarLeftSub.add(btnXoa);
        pnlTopBarLeftSub.add(btnInfo);
        pnlTopBarLeftSub.add(btnImport);
        pnlTopBarLeftSub.add(btnExport);
        
        headerRightPanel = new HeaderRightPanel();
        
        pnlTopBar.add(pnlTopBarLeftSub, BorderLayout.WEST);
        pnlTopBar.add(headerRightPanel, BorderLayout.EAST);
        
        // --- 3. MAIN PANEL (Chứa Bảng dữ liệu) ---
        JPanel pnlMain = new JPanel(new BorderLayout(10, 0));
        pnlMain.setBackground(Color.WHITE);
        
        String[] headers = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ"};
        tblNhaCungCap = new TablePanel("DANH SÁCH NHÀ CUNG CẤP", headers);
        
        pnlMain.add(tblNhaCungCap, BorderLayout.CENTER);
        
        // --- 4. THÊM VÀO PANEL CHÍNH ---
        this.add(pnlTopBar, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
    }
    
    public void loadData() {
        ArrayList<NhaCungCapDTO> list = bus.getAll();
        
        if (list == null) return;
        
        Object[][] data = new Object[list.size()][4];
        
        for (int i = 0; i < list.size(); i++) {
            NhaCungCapDTO ncc = list.get(i);
            
            // LƯU Ý: Sửa các hàm get... dưới đây cho khớp với thuộc tính trong NhaCungCapDTO của bạn
            /*
            data[i][0] = ncc.getMaNCC();       
            data[i][1] = ncc.getTenNCC();
            data[i][2] = ncc.getSdt();              
            data[i][3] = ncc.getDiaChi();
            */
        }
        
        // tblNhaCungCap.setData(data); // Bỏ comment dòng này khi đã map đúng dữ liệu
    }
}