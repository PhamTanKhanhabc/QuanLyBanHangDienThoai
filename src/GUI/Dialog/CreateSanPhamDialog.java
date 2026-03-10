/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;
import java.awt.*;
import javax.swing.border.LineBorder;
import BUS.HangSanXuatBUS;
import BUS.LoaiSanPhamBUS;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import BUS.SanPhamBUS;
import DTO.HangSanXuatDTO;
import DTO.LoaiSanPhamDTO;
import DTO.SanPhamDTO;
import GUI.Panel.SanPhamPanel;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

/**
 *
 * @author 
 */
public class CreateSanPhamDialog extends javax.swing.JDialog {
    private SanPhamPanel sanPhamPanel;
    private final SanPhamBUS spBUS = new SanPhamBUS();
    private byte[] SanPhamImage;

    private final List<LoaiSanPhamDTO> listLSP = new LoaiSanPhamBUS().getAll();
    private final List<HangSanXuatDTO> listHSX = new HangSanXuatBUS().getAll();
    
    private JPanel pnlHeader;
    private JLabel lblTitle;

    private JPanel pnlImageArea;
    private JPanel pnlImagePreview;
    private JLabel lblHinhAnh;
    private JButton btnChooseImage;

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
    
    private JPanel pnlButton;
    private JButton btnHuy;
    private JButton btnAdd;

    public CreateSanPhamDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillCombobox(); 
    }

    public CreateSanPhamDialog(java.awt.Frame parent, boolean modal, SanPhamPanel panel) {
        super(parent, modal);
        this.sanPhamPanel = panel;
        initComponents();
        fillCombobox();
    }

    private void fillCombobox() {
        for (LoaiSanPhamDTO vt : listLSP) {
            cboxLoaiSanPham.addItem(vt.getTenLoai());
        }

        for (HangSanXuatDTO vt : listHSX) {
            cboxHangSanXuat.addItem(vt.getTenHang());
        }
    }
    //Hàm kiểm tra dữ liệu người dùng nhập
    private boolean isValidateFields() {
        if (txtTenSanPham.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tên sản phẩm không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtTenSanPham.requestFocus();
            return false;
        }

        if (SanPhamImage == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hình ảnh không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            btnChooseImage.requestFocus();
            return false;
        }
        if (txtSoLuongTon.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Số lượng không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtSoLuongTon.requestFocus();
            return false;
        } else {
            try {
                int sl = Integer.parseInt(txtSoLuongTon.getText().trim());

                if (sl < 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Số lượng phải >= 0!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                    txtSoLuongTon.requestFocus();
                    return false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Số lượng phải là số!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                txtSoLuongTon.requestFocus();
                return false;
            }
        }
        if (txtDonGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Đơn giá không được rỗng!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtDonGia.requestFocus();
            return false;
        } else {
            try {
                int dg = Integer.parseInt(txtDonGia.getText().trim());

                if (dg < 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Đơn giá phải >= 0!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                    txtDonGia.requestFocus();
                    return false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Đơn giá phải là số!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                txtDonGia.requestFocus();
                return false;
            }
        }
        if (txtDonViTinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Đơn vị tính không được để trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtDonViTinh.requestFocus();
            return false;
        }
        return true;
    }
    //Hàm lấy dữ liệu người dùng nhập
    private SanPhamDTO getInputFields() {
        String maSp = spBUS.generateMaSP();
        String tenSp = txtTenSanPham.getText().trim();
        String hinhAnh = lblHinhAnh.getText(); 
        int soLuongTon = Integer.parseInt(txtSoLuongTon.getText());
        double donGia = Double.parseDouble(txtDonGia.getText().trim());
        String donViTinh = txtDonViTinh.getText().trim();; 
        LoaiSanPhamDTO lsp = listLSP.get(cboxLoaiSanPham.getSelectedIndex());
        HangSanXuatDTO hsx = listHSX.get(cboxHangSanXuat.getSelectedIndex());
        return new SanPhamDTO(
                maSp,
                tenSp,
                hinhAnh,
                soLuongTon,
                donGia,
                donViTinh,
                lsp.getMaLoai(),
                hsx.getMaHang(),
                1
        );
    }

    private void initComponents() {
        pnlHeader = new JPanel();
        lblTitle = new JLabel();

        pnlImageArea = new JPanel();
        pnlImagePreview = new JPanel();
        lblHinhAnh = new JLabel();
        btnChooseImage = new JButton();

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
        lblTitle.setText("THÊM SẢN PHẨM");
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

        btnChooseImage.setText("Chọn ảnh");
        btnChooseImage.setPreferredSize(new java.awt.Dimension(100, 30));
        btnChooseImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChooseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseImageActionPerformed(evt);
            }
        });
        
        pnlImageArea.add(Box.createVerticalStrut(50));
        pnlImageArea.add(pnlImagePreview);
        pnlImageArea.add(Box.createVerticalStrut(10));
        pnlImageArea.add(btnChooseImage);
        
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
        btnAdd.setText("THÊM");
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
            SanPhamDTO e = getInputFields();

            boolean check = spBUS.add(e);

            if (check) {
                JOptionPane.showMessageDialog(
                        this,
                        "Thêm thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                sanPhamPanel.loadTable(spBUS.getAll());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Thêm thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void btnChooseImageActionPerformed(java.awt.event.ActionEvent evt) {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            String filename = selectedFile.getAbsolutePath();

            ImageIcon imageIcon = new ImageIcon(
                    new ImageIcon(filename)
                            .getImage()
                            .getScaledInstance(lblHinhAnh.getWidth(),
                                    lblHinhAnh.getHeight(),
                                    Image.SCALE_SMOOTH)
            );

            lblHinhAnh.setIcon(imageIcon);

            try {
                FileInputStream fis = new FileInputStream(new File(filename));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                byte[] buf = new byte[1024];
                int readNum;

                while ((readNum = fis.read(buf)) != -1) {
                    bos.write(buf, 0, readNum);
                }

                SanPhamImage = bos.toByteArray();

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không tìm thấy file!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi khi đọc file!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
