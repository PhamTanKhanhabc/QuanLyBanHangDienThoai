/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.BaoHanhBUS;
import BUS.HoaDonBUS;
import BUS.SanPhamBUS;
import DTO.BaoHanhDTO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import GUI.Panel.BaoHanhPanel;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class UpdateBaoHanhDialog extends JDialog{
    private BaoHanhPanel baoHanhPanel;
    private final BaoHanhBUS bhBUS = new BaoHanhBUS();
    private BaoHanhDTO bh;
    
    private final List<HoaDonDTO> listHD = new HoaDonBUS().getAll();
    private final List<SanPhamDTO> listSP = new SanPhamBUS().getAll();
    
    private JPanel pnlHeader;
    private JLabel lblTitle;

    private JPanel pnlFormWrapper;
    private JPanel pnlFormGrid;

    private JPanel pnlTenBH;
    private JLabel lblTenBH;
    private JTextField txtTenBH;

    private JPanel pnlHoaDon;
    private JLabel lblHoaDon;
    private JComboBox<String> cboxHoaDon;

    private JPanel pnlSanPham;
    private JLabel lblSanPham;
    private JComboBox<String> cboxSanPham;

    private JPanel pnlThoiHan;
    private JLabel lblThoiHan;
    private JTextField txtThoiHan;

    private JPanel pnlNgayBatDau;
    private JLabel lblNgayBatDau;
    private JDateChooser dateNgayBatDau;

    private JPanel pnlNgayKetThuc;
    private JLabel lblNgayKetThuc;
    private JDateChooser dateNgayKetThuc;

    private JPanel pnlButton;
    private JButton btnHuy;
    private JButton btnAdd;
    
    public UpdateBaoHanhDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public UpdateBaoHanhDialog(java.awt.Frame parent, boolean modal, BaoHanhPanel panel, BaoHanhDTO bh) {
        super(parent, modal);
        initComponents();
        this.baoHanhPanel = panel;
        this.bh = bh;
        fillCombobox();
        fillInput();
    }
    private void fillCombobox() {

        for (HoaDonDTO hd : listHD) {
            cboxHoaDon.addItem(hd.getMaHD());
        }

        for (SanPhamDTO sp : listSP) {
            cboxSanPham.addItem(sp.getMaSp());
        }

    }
    private void fillInput() {

        txtTenBH.setText(bh.getTenBH());

        // Hóa đơn
        cboxHoaDon.setSelectedItem(bh.getMaHD());

        // Sản phẩm
        cboxSanPham.setSelectedItem(bh.getMaSP());

        // Thời hạn
        txtThoiHan.setText(String.valueOf(bh.getThoiHan()));

        // Ngày bắt đầu
        if (bh.getNgayBatDau() != null) {
            dateNgayBatDau.setDate(bh.getNgayBatDau());
        }

        // Ngày kết thúc
        if (bh.getNgayKetThuc() != null) {
            dateNgayKetThuc.setDate(bh.getNgayKetThuc());
        }
    }
    private boolean isValidateFields() {

        // Tên bảo hành
        if (txtTenBH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tên bảo hành không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtTenBH.requestFocus();
            return false;
        }
        // Thời hạn
        if (txtThoiHan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Thời hạn bảo hành không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtThoiHan.requestFocus();
            return false;
        } else {
            try {
                int th = Integer.parseInt(txtThoiHan.getText().trim());

                if (th <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Thời hạn phải > 0 tháng!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                    txtThoiHan.requestFocus();
                    return false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Thời hạn phải là số!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                txtThoiHan.requestFocus();
                return false;
            }
        }

        // Ngày bắt đầu
        if (dateNgayBatDau.getDate() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ngày bắt đầu không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            dateNgayBatDau.requestFocus();
            return false;
        }

        // Ngày kết thúc
        if (dateNgayKetThuc.getDate() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ngày kết thúc không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            dateNgayKetThuc.requestFocus();
            return false;
        }

        // Kiểm tra ngày kết thúc > ngày bắt đầu
        if (dateNgayKetThuc.getDate().before(dateNgayBatDau.getDate())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ngày kết thúc phải sau ngày bắt đầu!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            dateNgayKetThuc.requestFocus();
            return false;
        }

        return true;
    }
    private BaoHanhDTO getInputFields() {

        String tenBH = txtTenBH.getText().trim();

        HoaDonDTO hd = listHD.get(cboxHoaDon.getSelectedIndex());
        SanPhamDTO sp = listSP.get(cboxSanPham.getSelectedIndex());

        int thoiHan = Integer.parseInt(txtThoiHan.getText().trim());

        Date ngayBatDau = dateNgayBatDau.getDate();
        Date ngayKetThuc = dateNgayKetThuc.getDate();

        return new BaoHanhDTO(
                bh.getMaBH(),
                tenBH,
                hd.getMaHD(),
                sp.getMaSp(),
                thoiHan,
                ngayBatDau,
                ngayKetThuc,
                1
        );
    }
    private void initComponents() {
        pnlHeader = new JPanel();
        lblTitle = new JLabel();
        
        pnlFormWrapper = new JPanel();
        pnlFormGrid = new JPanel();
        
        pnlTenBH = new JPanel();
        lblTenBH = new JLabel();
        txtTenBH = new JTextField();
        
        pnlHoaDon = new JPanel();
        lblHoaDon = new JLabel();
        cboxHoaDon = new JComboBox<>();
        
        pnlSanPham = new JPanel();
        lblSanPham = new JLabel();
        cboxSanPham = new JComboBox<>();
        
        pnlThoiHan = new JPanel();
        lblThoiHan = new JLabel();
        txtThoiHan = new JTextField();
        
        pnlNgayBatDau = new JPanel();
        lblNgayBatDau = new JLabel();
        dateNgayBatDau = new JDateChooser();
        
        pnlNgayKetThuc = new JPanel();
        lblNgayKetThuc = new JLabel();
        dateNgayKetThuc = new JDateChooser();
        
        pnlButton = new JPanel();
        btnHuy = new JButton();
        btnAdd = new JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 650));
        setSize(1100, 650);
        
        pnlHeader.setBackground(new java.awt.Color(65, 120, 255));
        pnlHeader.setMinimumSize(new java.awt.Dimension(100, 60));
        pnlHeader.setPreferredSize(new java.awt.Dimension(500, 50));
        pnlHeader.setLayout(new java.awt.BorderLayout());

        lblTitle.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("CẬP NHẬT BẢO HÀNH");
        lblTitle.setPreferredSize(new java.awt.Dimension(149, 40));
        pnlHeader.add(lblTitle, java.awt.BorderLayout.CENTER);
        
        add(pnlHeader, java.awt.BorderLayout.NORTH);
        
        pnlFormWrapper.setBackground(new java.awt.Color(255, 255, 255));
        pnlFormWrapper.setPreferredSize(new java.awt.Dimension(650, 550));
        pnlFormWrapper.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 16));

        pnlFormGrid.setBackground(new java.awt.Color(255, 255, 255));
        pnlFormGrid.setPreferredSize(new java.awt.Dimension(650, 500));
        pnlFormGrid.setLayout(new java.awt.GridLayout(6,1,16,8));
        
        pnlTenBH.setBackground(new java.awt.Color(255, 255, 255));
        pnlTenBH.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlTenBH.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblTenBH.setFont(new java.awt.Font("Roboto", 0, 14)); 
        lblTenBH.setText("Tên bảo hành");
        lblTenBH.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlTenBH.add(lblTenBH);
        
        txtTenBH.setFont(new java.awt.Font("Roboto", 0, 14)); 
        txtTenBH.setToolTipText("");
        txtTenBH.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlTenBH.add(txtTenBH);

        pnlFormGrid.add(pnlTenBH);
        
        pnlSanPham.setBackground(new java.awt.Color(255, 255, 255));
        pnlSanPham.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlSanPham.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblSanPham.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSanPham.setText("Sản phẩm");
        lblSanPham.setMaximumSize(new java.awt.Dimension(44, 40));
        lblSanPham.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlSanPham.add(lblSanPham);

        cboxSanPham.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlSanPham.add(cboxSanPham);

        pnlFormGrid.add(pnlSanPham);
        
        pnlHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        pnlHoaDon.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlHoaDon.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblHoaDon.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHoaDon.setText("Hóa đơn");
        lblHoaDon.setMaximumSize(new java.awt.Dimension(44, 40));
        lblHoaDon.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlHoaDon.add(lblHoaDon);

        cboxHoaDon.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlHoaDon.add(cboxHoaDon);

        pnlFormGrid.add(pnlHoaDon);
        
        pnlThoiHan.setBackground(new java.awt.Color(255, 255, 255));
        pnlThoiHan.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlThoiHan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblThoiHan.setFont(new java.awt.Font("Roboto", 0, 14)); 
        lblThoiHan.setText("Tên bảo hành");
        lblThoiHan.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlThoiHan.add(lblThoiHan);
        
        txtThoiHan.setFont(new java.awt.Font("Roboto", 0, 14)); 
        txtThoiHan.setToolTipText("");
        txtThoiHan.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlThoiHan.add(txtThoiHan);

        pnlFormGrid.add(pnlThoiHan);
        
        pnlNgayBatDau.setBackground(new java.awt.Color(255, 255, 255));
        pnlNgayBatDau.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlNgayBatDau.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblNgayBatDau.setFont(new java.awt.Font("Roboto", 0, 14));
        lblNgayBatDau.setText("Ngày bắt đầu");
        lblNgayBatDau.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlNgayBatDau.add(lblNgayBatDau);

        dateNgayBatDau.setFont(new java.awt.Font("Roboto", 0, 14));
        dateNgayBatDau.setPreferredSize(new java.awt.Dimension(300, 40));
        dateNgayBatDau.setDateFormatString("dd/MM/yyyy"); // format ngày
        pnlNgayBatDau.add(dateNgayBatDau);

        pnlFormGrid.add(pnlNgayBatDau);
        
        pnlNgayKetThuc.setBackground(new java.awt.Color(255, 255, 255));
        pnlNgayKetThuc.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlNgayKetThuc.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblNgayKetThuc.setFont(new java.awt.Font("Roboto", 0, 14));
        lblNgayKetThuc.setText("Ngày kết thúc");
        lblNgayKetThuc.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlNgayKetThuc.add(lblNgayKetThuc);

        dateNgayKetThuc.setFont(new java.awt.Font("Roboto", 0, 14));
        dateNgayKetThuc.setPreferredSize(new java.awt.Dimension(300, 40));
        dateNgayKetThuc.setDateFormatString("dd/MM/yyyy"); // format ngày
        pnlNgayKetThuc.add(dateNgayKetThuc);

        pnlFormGrid.add(pnlNgayKetThuc);
        
        pnlFormWrapper.add(pnlFormGrid);
        
        add(pnlFormWrapper, java.awt.BorderLayout.CENTER);
        
        pnlButton.setBackground(new java.awt.Color(255, 255, 255));
        pnlButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 5));
        
        btnHuy.setBackground(new java.awt.Color(255, 102, 102));
        btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); 
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        pnlButton.add(btnHuy);
        
        btnAdd.setBackground(new java.awt.Color(0, 204, 102));
        btnAdd.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("CẬP NHẬT");
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        btnAdd.setFocusable(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(200, 40));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnlButton.add(btnAdd);
        
        add(pnlButton, java.awt.BorderLayout.PAGE_END);
        
        setLocationRelativeTo(null);
    }
    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {                                     
        if (isValidateFields()) {

            BaoHanhDTO e = getInputFields();

            boolean check = bhBUS.update(e);

            if (check) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                baoHanhPanel.loadTable(bhBUS.getAll());
                this.dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

            }
        }
    }
}
