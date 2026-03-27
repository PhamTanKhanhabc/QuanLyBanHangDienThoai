package GUI.Dialog;

import BUS.ChiTietPhieuNhapBUS;
import BUS.NhaCungCapBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NhaCungCapDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;

public class XemPhieuNhap extends JDialog {

    private JTextField txtNhaCungCap;
    private JTextField txtNhanVien;
    private DefaultTableModel chiTietModel;
    private JTable tblChiTiet;
    private JButton btnDong;

    public XemPhieuNhap(Frame owner, boolean modal, String maNCC, String maNV, String maPHN){
        super(owner, modal);
        initUI(maNCC, maNV, maPHN);
        loadChiTietPhieuNhap(maPHN);
    }
    
    public void initUI(String maNCC, String maNV, String maPHN){
        setTitle("CHI TIẾT PHIẾU NHẬP");
        setSize(850, 600);
        setLocationRelativeTo(getParent());
        
        setLayout(new BorderLayout(10, 10)); 
        getContentPane().setBackground(Color.WHITE);
        
        JPanel pnlTop = new JPanel(new BorderLayout()); 
        pnlTop.setBackground(Color.WHITE);
        
        JPanel pnlTopTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTopTitle.setBackground(Color.WHITE);
        
        JLabel lblTopTitle = new JLabel("CHI TIẾT PHIẾU NHẬP " + maPHN);
        lblTopTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTopTitle.setForeground(new Color(65, 120, 255));
        pnlTopTitle.add(lblTopTitle);
        
        JPanel pnlTopInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlTopInfo.setBackground(Color.WHITE);
        pnlTopInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        pnlTopInfo.add(new JLabel("Nhà Cung Cấp: "));
        
        String tenNCC = maNCC;
        NhaCungCapBUS nccBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCapDTO> nccList = nccBUS.getAll();
        if (nccList != null) {
            for (NhaCungCapDTO tmp : nccList) {
                if (tmp.getMaNCC().equals(maNCC)) {
                    tenNCC = tmp.getTenNCC();
                    break;
                }
            }
        }

        txtNhaCungCap = new JTextField(tenNCC);
        txtNhaCungCap.setPreferredSize(new Dimension(250, 32));
        txtNhaCungCap.setEditable(false);
        txtNhaCungCap.setBackground(new Color(245, 245, 245));
        txtNhaCungCap.setFont(new Font("Roboto", Font.BOLD, 14));
        pnlTopInfo.add(txtNhaCungCap);
        
        pnlTopInfo.add(new JLabel("Mã nhân viên: "));
        txtNhanVien = new JTextField(maNV);
        txtNhanVien.setPreferredSize(new Dimension(150, 32));
        txtNhanVien.setEditable(false);
        txtNhanVien.setBackground(new Color(245, 245, 245));
        txtNhanVien.setFont(new Font("Roboto", Font.BOLD, 14));
        pnlTopInfo.add(txtNhanVien);
        
        pnlTop.add(pnlTopTitle, BorderLayout.NORTH);
        pnlTop.add(pnlTopInfo, BorderLayout.CENTER);
        
        JPanel pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(0, 20, 20, 20)); 
        
        String[] cols = {
            "STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
        };
        
        chiTietModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(35);
        tblChiTiet.setSelectionBackground(new Color(230, 245, 245));
        tblChiTiet.setSelectionForeground(Color.BLACK); 
        tblChiTiet.getTableHeader().setReorderingAllowed(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblChiTiet.getColumnCount(); i++) {
            tblChiTiet.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        
        JPanel pnlMainAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlMainAction.setBackground(Color.WHITE);
        pnlMainAction.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(120, 40));
        btnDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnDong.addActionListener(e -> dispose());
        
        pnlMainAction.add(btnDong);
        
        add(pnlTop, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlMainAction, BorderLayout.SOUTH);
    }
    
    public void loadChiTietPhieuNhap(String maPHN){
        chiTietModel.setRowCount(0);
        ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
        ArrayList<ChiTietPhieuNhapDTO> list = ctpnBUS.getAllByMaPN(maPHN);
        
        DecimalFormat df = new DecimalFormat("#,###");
        
        if(list != null){
            int stt = 1;
            for (ChiTietPhieuNhapDTO tmp : list){
                Object[] row = {
                    stt++,
                    tmp.getMaSP(), 
                    tmp.getSoLuong(),
                    df.format(tmp.getDonGia()),                  
                    df.format(tmp.getSoLuong() * tmp.getDonGia())  
                };
                chiTietModel.addRow(row);
            }    
        }
    }
}