/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.HangSanXuatBUS;
import BUS.LoaiSanPhamBUS;
import BUS.SanPhamBUS;
import DTO.HangSanXuatDTO;
import DTO.LoaiSanPhamDTO;
import DTO.SanPhamDTO;
import GUI.Panel.SanPhamPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author user
 */
public class ChiTietSanPhamDialog extends JDialog {

    private SanPhamPanel sanPhamPanel;
    private final SanPhamBUS spBUS = new SanPhamBUS();
    private byte[] SanPhamImage;
    private SanPhamDTO sp;
    private String imagePath;

    private final List<LoaiSanPhamDTO> listLSP = new LoaiSanPhamBUS().getAll();
    private final List<HangSanXuatDTO> listHSX = new HangSanXuatBUS().getAll();
    
    private JPanel pnlHeader;
    private JLabel lblTitle;

    private JPanel pnlImageArea;
    private JPanel pnlImagePreview;
    private JLabel lblHinhAnh;

    private JPanel pnlFormWrapper;
    private JPanel pnlFormGrid;

    private JPanel pnlTenSanPham;
    private JLabel lblTenSanPham;
    private JTextField txtTenSanPham;
    
    private JPanel pnlSoLuongTon;
    private JLabel lblSoLuongTon;
    private JTextField txtSoLuongTon;
    
    private JPanel pnlDonGia;
    private JLabel lblDonGia;
    private JTextField txtDonGia;
    
    private JPanel pnlDonViTinh;
    private JLabel lblDonViTinh;
    private JTextField txtDonViTinh;

    private JPanel pnlLoaiSanPham;
    private JLabel lblLoaiSanPham;
    private JComboBox<String> cboxLoaiSanPham;

    private JPanel pnlHangSanXuat;
    private JLabel lblHangSanXuat;
    private JComboBox<String> cboxHangSanXuat;
    

    public ChiTietSanPhamDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public ChiTietSanPhamDialog(java.awt.Frame parent, boolean modal, SanPhamDTO sp) {
        super(parent, modal);
        initComponents();
        this.sp = sp;
        fillCombobox();
        fillInput();
    }
    private void fillCombobox() {
        for (LoaiSanPhamDTO vt : listLSP) {
            cboxLoaiSanPham.addItem(vt.getTenLoai());
        }

        for (HangSanXuatDTO vt : listHSX) {
            cboxHangSanXuat.addItem(vt.getTenHang());
        }
    }
    private void fillInput() {

        txtTenSanPham.setText(sp.getTenSp());

        String imagePath = sp.getHinhAnh();
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon icon = new ImageIcon(
                new ImageIcon(imagePath).getImage().getScaledInstance(
                    300,
                    300,
                    Image.SCALE_SMOOTH
                )
            );
            lblHinhAnh.setIcon(icon);
        }

        txtSoLuongTon.setText(String.valueOf(sp.getSoLuongTon()));
        txtDonGia.setText(String.valueOf((long) sp.getDonGia()));
        txtDonViTinh.setText(sp.getDonViTinh());

        cboxLoaiSanPham.setSelectedItem(sp.getMaLoai());
        cboxHangSanXuat.setSelectedItem(sp.getMaHang());
    }
    private void initComponents() {
        pnlHeader = new JPanel();
        lblTitle = new JLabel();

        pnlImageArea = new JPanel();
        pnlImagePreview = new JPanel();
        lblHinhAnh = new JLabel();

        pnlFormWrapper = new JPanel();
        pnlFormGrid = new JPanel();

        pnlTenSanPham = new JPanel();
        lblTenSanPham = new JLabel();
        txtTenSanPham = new JTextField();
        
        pnlSoLuongTon = new JPanel();
        lblSoLuongTon = new JLabel();
        txtSoLuongTon = new JTextField();
        
        pnlDonGia = new JPanel();
        lblDonGia = new JLabel();
        txtDonGia = new JTextField();
        
        pnlDonViTinh = new JPanel();
        lblDonViTinh = new JLabel();
        txtDonViTinh = new JTextField();
        
        pnlLoaiSanPham = new JPanel();
        lblLoaiSanPham = new JLabel();
        cboxLoaiSanPham = new JComboBox<>();

        pnlHangSanXuat = new JPanel();
        lblHangSanXuat = new JLabel();
        cboxHangSanXuat = new JComboBox<>();

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
        lblTitle.setText("THÔNG TIN SẢN PHẨM");
        lblTitle.setPreferredSize(new java.awt.Dimension(149, 40));
        pnlHeader.add(lblTitle, java.awt.BorderLayout.CENTER);

        add(pnlHeader, java.awt.BorderLayout.NORTH);

        pnlImageArea.setBackground(Color.WHITE);
        pnlImageArea.setPreferredSize(new java.awt.Dimension(400, 100));
        pnlImageArea.setLayout(new BoxLayout(pnlImageArea, BoxLayout.Y_AXIS));

        pnlImagePreview.setBackground(Color.WHITE);
        pnlImagePreview.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
        pnlImagePreview.setPreferredSize(new java.awt.Dimension(300, 300));
        pnlImagePreview.setMaximumSize(new Dimension(300, 300));
        pnlImagePreview.setLayout(new java.awt.BorderLayout());
        pnlImagePreview.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        lblHinhAnh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
        lblHinhAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHinhAnh.setPreferredSize(new java.awt.Dimension(200, 100));
        
        pnlImagePreview.add(lblHinhAnh, java.awt.BorderLayout.CENTER);

        
        
        pnlImageArea.add(Box.createVerticalStrut(50));
        pnlImageArea.add(pnlImagePreview);
        pnlImageArea.add(Box.createVerticalStrut(10));
        
        add(pnlImageArea, BorderLayout.WEST);

        pnlFormWrapper.setBackground(new java.awt.Color(255, 255, 255));
        pnlFormWrapper.setPreferredSize(new java.awt.Dimension(650, 550));
        pnlFormWrapper.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 16));

        pnlFormGrid.setBackground(new java.awt.Color(255, 255, 255));
        pnlFormGrid.setPreferredSize(new java.awt.Dimension(650, 500));
        pnlFormGrid.setLayout(new java.awt.GridLayout(6,1,16,8));

        pnlTenSanPham.setBackground(new java.awt.Color(255, 255, 255));
        pnlTenSanPham.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlTenSanPham.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblTenSanPham.setFont(new java.awt.Font("Roboto", 0, 14)); 
        lblTenSanPham.setText("Tên sản phẩm");
        lblTenSanPham.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlTenSanPham.add(lblTenSanPham);

        txtTenSanPham.setFont(new java.awt.Font("Roboto", 0, 14)); 
        txtTenSanPham.setToolTipText("");
        txtTenSanPham.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlTenSanPham.add(txtTenSanPham);

        pnlFormGrid.add(pnlTenSanPham);
        
        pnlSoLuongTon.setBackground(new java.awt.Color(255, 255, 255));
        pnlSoLuongTon.setPreferredSize(new java.awt.Dimension(500, 40));
        pnlSoLuongTon.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblSoLuongTon.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSoLuongTon.setText("Số lượng tồn");
        lblSoLuongTon.setMaximumSize(new java.awt.Dimension(44, 40));
        lblSoLuongTon.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlSoLuongTon.add(lblSoLuongTon);

        txtSoLuongTon.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSoLuongTon.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlSoLuongTon.add(txtSoLuongTon);

        pnlFormGrid.add(pnlSoLuongTon);
        
        pnlDonGia.setBackground(new java.awt.Color(255, 255, 255));
        pnlDonGia.setPreferredSize(new java.awt.Dimension(500, 40));
        pnlDonGia.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblDonGia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblDonGia.setText("Đơn giá");
        lblDonGia.setMaximumSize(new java.awt.Dimension(44, 40));
        lblDonGia.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlDonGia.add(lblDonGia);

        txtDonGia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDonGia.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlDonGia.add(txtDonGia);

        pnlFormGrid.add(pnlDonGia);
        
        pnlDonViTinh.setBackground(new java.awt.Color(255, 255, 255));
        pnlDonViTinh.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlDonViTinh.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblDonViTinh.setFont(new java.awt.Font("Roboto", 0, 14)); 
        lblDonViTinh.setText("Đơn vị tính");
        lblDonViTinh.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlDonViTinh.add(lblDonViTinh);

        txtDonViTinh.setFont(new java.awt.Font("Roboto", 0, 14)); 
        txtDonViTinh.setToolTipText("");
        txtDonViTinh.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlDonViTinh.add(txtDonViTinh);

        pnlFormGrid.add(pnlDonViTinh);
        
        pnlLoaiSanPham.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoaiSanPham.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlLoaiSanPham.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblLoaiSanPham.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblLoaiSanPham.setText("Danh mục");
        lblLoaiSanPham.setMaximumSize(new java.awt.Dimension(44, 40));
        lblLoaiSanPham.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlLoaiSanPham.add(lblLoaiSanPham);

        cboxLoaiSanPham.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlLoaiSanPham.add(cboxLoaiSanPham);

        pnlFormGrid.add(pnlLoaiSanPham);

        pnlHangSanXuat.setBackground(new java.awt.Color(255, 255, 255));
        pnlHangSanXuat.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlHangSanXuat.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblHangSanXuat.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHangSanXuat.setText("Xuất xứ");
        lblHangSanXuat.setMaximumSize(new java.awt.Dimension(44, 40));
        lblHangSanXuat.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlHangSanXuat.add(lblHangSanXuat);

        cboxHangSanXuat.setPreferredSize(new java.awt.Dimension(300, 40));
        pnlHangSanXuat.add(cboxHangSanXuat);

        pnlFormGrid.add(pnlHangSanXuat);

        pnlFormWrapper.add(pnlFormGrid);
        add(pnlFormWrapper, java.awt.BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
    }
}
