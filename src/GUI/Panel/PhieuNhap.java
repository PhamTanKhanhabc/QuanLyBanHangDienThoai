package GUI.Panel;

import GUI.Component.ButtonToolBar;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PhieuNhap extends JPanel {
    private TablePanel tblPhieuNhap;
    private PhieuNhapBUS bus = new PhieuNhapBUS();
    
    // Khai báo HeaderRightPanel cho thanh tìm kiếm
    private HeaderRightPanel headerRightPanel;
    
    public PhieuNhap() {
        initUI();
        loadData();
    }
    
    public void initUI() {
        // --- 1. SETUP LAYOUT TỔNG THỂ ---
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // --- 2. HEADER PANEL (Chứa Nút chức năng & Thanh tìm kiếm) ---
        JPanel pnlTopBar = new JPanel(new BorderLayout());
        pnlTopBar.setBackground(Color.WHITE);
        
        // Trái: Chứa các nút thao tác tạo thủ công
        JPanel pnlTopBarLeftSub = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnlTopBarLeftSub.setBackground(Color.WHITE);
        
        ButtonToolBar btnThem = new ButtonToolBar("Thêm", "icon/add.svg", 80, 60, 14, "ADD_PHIEUNHAP");
        ButtonToolBar btnSua = new ButtonToolBar("Sửa", "icon/update.svg", 80, 60, 14, "EDIT_PHIEUNHAP");
        ButtonToolBar btnXoa = new ButtonToolBar("Xóa", "icon/delete.svg", 80, 60, 14, "DELETE_PHIEUNHAP");
        ButtonToolBar btnInfo = new ButtonToolBar("Chi tiết", "icon/info.svg", 80, 60, 14, "INFO_PHIEUNHAP");
        ButtonToolBar btnImport = new ButtonToolBar("Import", "icon/import.svg", 80, 60, 14, "IMPORT_PHIEUNHAP");
        ButtonToolBar btnExport = new ButtonToolBar("Export", "icon/export.svg", 80, 60, 14, "EXPORT_PHIEUNHAP");
        
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
        
        // Phải: Thanh tìm kiếm dùng HeaderRightPanel
        headerRightPanel = new HeaderRightPanel();
        
        pnlTopBar.add(pnlTopBarLeftSub, BorderLayout.WEST);
        pnlTopBar.add(headerRightPanel, BorderLayout.EAST);
        
        // --- 3. MAIN PANEL ---
        JPanel pnlMain = new JPanel(new BorderLayout(10, 0));
        pnlMain.setBackground(Color.WHITE);
        
        // 3.1. PANEL BỘ LỌC BÊN TRÁI (Dùng BorderLayout để nhốt vào NORTH chống giãn)
        JPanel pnlLeft = new JPanel(new BorderLayout()); 
        pnlLeft.setPreferredSize(new Dimension(250, 0));
        pnlLeft.setBackground(Color.WHITE);
        
        JPanel pnlFilterBox = new JPanel();
        pnlFilterBox.setLayout(new BoxLayout(pnlFilterBox, BoxLayout.Y_AXIS));
        pnlFilterBox.setBackground(Color.WHITE);
        pnlFilterBox.setBorder(new EmptyBorder(20, 10, 10, 10));
        
        Dimension labelSize = new Dimension(65, 30);
        
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setPreferredSize(labelSize);
        JDateChooser dateTuNgay = new JDateChooser();
        dateTuNgay.setDateFormatString("dd/MM/yyyy");
        dateTuNgay.setPreferredSize(new Dimension(140, 25));
        
        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setPreferredSize(labelSize);
        JDateChooser dateDenNgay = new JDateChooser();
        dateDenNgay.setDateFormatString("dd/MM/yyyy");
        dateDenNgay.setPreferredSize(new Dimension(140, 25));

        ButtonToolBar btnLoc = new ButtonToolBar("Lọc", "icon/filter.svg", 80, 60, 14, "VIEW_PHIEUNHAP");
        btnLoc.setBackground(Color.WHITE);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        row1.setBackground(Color.WHITE);
        row1.add(lblTuNgay);
        row1.add(dateTuNgay);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        row2.setBackground(Color.WHITE);
        row2.add(lblDenNgay);
        row2.add(dateDenNgay);
        
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        row3.setBackground(Color.WHITE);
        row3.add(btnLoc);

        pnlFilterBox.add(row1);
        pnlFilterBox.add(row2);
        pnlFilterBox.add(row3);
        
        pnlLeft.add(pnlFilterBox, BorderLayout.NORTH);
        
        String[] headers = {"Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp", "Ngày lập", "Tổng tiền", "Trạng thái"};
        tblPhieuNhap = new TablePanel("DANH SÁCH PHIẾU NHẬP", headers);
        
        pnlMain.add(pnlLeft, BorderLayout.WEST);
        pnlMain.add(tblPhieuNhap, BorderLayout.CENTER);
        
        // --- 4. THÊM VÀO PANEL CHÍNH ---
        this.add(pnlTopBar, BorderLayout.NORTH); 
        this.add(pnlMain, BorderLayout.CENTER);
        
        //ACTION
        btnThem.addActionListener(e -> {
        // Gọi form Dialog lên
            GUI.Dialog.ThemPhieuNhap dialog = new GUI.Dialog.ThemPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true
            );
            dialog.setVisible(true);
        }); 
        
        // Bắt sự kiện khi nhấn nút "Chi tiết"
        btnInfo.addActionListener(e -> {
            // Lấy JTable thực sự đang nằm bên trong TablePanel của bạn
            JTable table = tblPhieuNhap.getTable(); 
            int selectedRow = table.getSelectedRow();
            
            // 1. Kiểm tra xem người dùng đã click chọn dòng nào trên bảng chưa
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Dừng lại, không chạy code bên dưới nữa
            }
            
            // 2. Lấy dữ liệu từ dòng được chọn
            // Nhìn vào mảng headers của bạn: Cột 1 là Mã nhân viên, Cột 2 là Mã NCC
            String maNV = table.getValueAt(selectedRow, 1).toString();
            String maNCC = table.getValueAt(selectedRow, 2).toString();
            
            // LƯU Ý: Do trên bảng hiển thị Mã nhà cung cấp (kiểu số 1, 2, 3...)
            // Nên tôi ghép tạm chuỗi để truyền vào. 
            // Nếu bạn có hàm nccBUS.getTenNhaCungCap(maNCC), bạn có thể dùng để lấy tên cho đẹp!
            String tenNhaCungCap = "Mã NCC: " + maNCC; 

            // 3. Gọi form XemPhieuNhap và ném dữ liệu vào Constructor
            GUI.Dialog.XemPhieuNhap dialog = new GUI.Dialog.XemPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                tenNhaCungCap, 
                maNV
            );
            dialog.setVisible(true);
        });
        
        // Bắt sự kiện khi nhấn nút "Xóa"
        btnXoa.addActionListener(e -> {
            JTable table = tblPhieuNhap.getTable(); 
            int selectedRow = table.getSelectedRow();
            
            // 1. Kiểm tra xem có chọn dòng nào chưa
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; 
            }
            
            // 2. Lấy dữ liệu từ dòng được chọn
            // Cột 0: Mã phiếu nhập (Có chữ PN ở đầu do bạn ghép ở hàm loadData)
            String maPHN = table.getValueAt(selectedRow, 0).toString(); 
            String maNV = table.getValueAt(selectedRow, 1).toString();
            String maNCC = table.getValueAt(selectedRow, 2).toString();
            
            String tenNhaCungCap = maNCC; 

            // 3. Gọi form XoaPhieuNhap lên
            GUI.Dialog.XoaPhieuNhap dialog = new GUI.Dialog.XoaPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                maPHN,
                tenNhaCungCap, 
                maNV
            );
            dialog.setVisible(true);
        });
        
        
        btnSua.addActionListener(e -> {
            JTable table = tblPhieuNhap.getTable(); 
            int selectedRow = table.getSelectedRow();
            
            // Kiểm tra xem đã chọn dòng nào chưa
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; 
            }
            
            // Lấy Mã phiếu nhập (Lưu ý: Nhớ cắt chữ "PN" ở đầu nếu bạn có ghép vào)
            String maPHN = table.getValueAt(selectedRow, 0).toString(); 

            // Gọi form SuaPhieuNhap
            GUI.Dialog.SuaPhieuNhap dialog = new GUI.Dialog.SuaPhieuNhap(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                maPHN
            );
            dialog.setVisible(true);
        });
    }
    
    public void loadData() {
        ArrayList<PhieuNhapDTO> list = bus.getAll();
        
        if (list == null) return;
        
        Object[][] data = new Object[list.size()][6];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        DecimalFormat df = new DecimalFormat("#,###.##"); 
        
        for (int i = 0; i < list.size(); i++) {
            PhieuNhapDTO pn = list.get(i);
            
            data[i][0] = pn.getMaPHN();      
            data[i][1] = pn.getMaNV();
            data[i][2] = pn.getMaNCC();              
            data[i][3] = sdf.format(pn.getNgay());   
            data[i][4] = df.format(pn.getTongTien());
            data[i][5] = pn.getTrangThai() == 1 ? "Hoàn thành" : "Đã hủy";
        }
        
        tblPhieuNhap.setData(data);
        
    }
}