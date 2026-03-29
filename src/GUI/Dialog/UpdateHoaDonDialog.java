package GUI.Dialog;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.SanPhamDTO;
import GUI.Component.TablePanel;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class UpdateHoaDonDialog extends JDialog {
    private SanPhamBUS spBUS = new SanPhamBUS();
    private HoaDonBUS hdBUS = new HoaDonBUS();
    private KhachHangBUS khBUS = new KhachHangBUS();
    private ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();

    private JTable tableSP, tableGioHang;
    private DefaultTableModel modelSP, modelGioHang;
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtSoLuongMua;
    private JTextField txtSdtKH, txtTenKH;
    private JLabel lblTongTien, lblMaHD;
    private JButton btnThemSP, btnLuuThayDoi, btnHuy, btnXoaDong;
    
    private double tongTien = 0;
    private DecimalFormat df = new DecimalFormat("#,### VNĐ");
    private HoaDonDTO hoaDonDangSua; 

    // Constructor nhận vào mã Hóa đơn cần sửa
    public UpdateHoaDonDialog(JFrame parent, boolean modal, String maHD) {
        super(parent, modal);
        setTitle("Cập Nhật Hóa Đơn");
        setSize(1100, 700);
        setLocationRelativeTo(parent);
        
        initComponents();
        loadTableSanPham(spBUS.getAll());
        
        // Tải dữ liệu của hóa đơn cũ lên Giao diện
        loadDataHoaDonCu(maHD);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // ======================= TRÁI: DANH SÁCH SẢN PHẨM =======================
        JPanel pnlLeft = new JPanel(new BorderLayout(0, 10));
        pnlLeft.setBackground(Color.WHITE);

        JPanel pnlChonSP = new JPanel(new GridBagLayout());
        pnlChonSP.setBackground(Color.WHITE);
        pnlChonSP.setBorder(BorderFactory.createTitledBorder("THÔNG TIN SẢN PHẨM"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaSP = new JTextField(8); txtMaSP.setEditable(false);
        txtTenSP = new JTextField(15); txtTenSP.setEditable(false);
        txtDonGia = new JTextField(10); txtDonGia.setEditable(false);
        txtSoLuongMua = new JTextField("1", 5);
        
        btnThemSP = new JButton("Thêm vào HĐ");
        btnThemSP.setBackground(new Color(0, 153, 76)); 
        btnThemSP.setForeground(Color.WHITE);
        btnThemSP.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0; pnlChonSP.add(new JLabel("Mã SP:"), gbc);
        gbc.gridx = 1; pnlChonSP.add(txtMaSP, gbc);
        gbc.gridx = 2; pnlChonSP.add(new JLabel("Tên SP:"), gbc);
        gbc.gridx = 3; pnlChonSP.add(txtTenSP, gbc);
        gbc.gridx = 4; pnlChonSP.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 5; pnlChonSP.add(txtDonGia, gbc);
        gbc.gridy = 1; gbc.gridx = 0; pnlChonSP.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1; pnlChonSP.add(txtSoLuongMua, gbc);
        gbc.gridx = 5; pnlChonSP.add(btnThemSP, gbc);

        TablePanel tpSP = new TablePanel("Kho Sản Phẩm", new String[]{"Mã SP", "Tên SP", "Tồn kho", "Đơn giá"});
        tableSP = tpSP.getTable();
        modelSP = (DefaultTableModel) tableSP.getModel();

        pnlLeft.add(pnlChonSP, BorderLayout.NORTH);
        pnlLeft.add(tpSP, BorderLayout.CENTER);

        // ======================= PHẢI: GIỎ HÀNG & THÔNG TIN HÓA ĐƠN =======================
        JPanel pnlRight = new JPanel(new BorderLayout(0, 10));
        pnlRight.setPreferredSize(new Dimension(450, 0));
        pnlRight.setBackground(Color.WHITE);

        TablePanel tpGioHang = new TablePanel("Chi tiết hóa đơn", new String[]{"Mã SP", "Tên SP", "SL", "Đơn giá", "Thành tiền"});
        tableGioHang = tpGioHang.getTable();
        modelGioHang = (DefaultTableModel) tableGioHang.getModel();
        
        JPanel pnlXoaGioHang = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlXoaGioHang.setBackground(Color.WHITE);
        btnXoaDong = new JButton("Xóa SP chọn");
        btnXoaDong.setBackground(new Color(255, 102, 102));
        btnXoaDong.setForeground(Color.WHITE);
        btnXoaDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlXoaGioHang.add(btnXoaDong);

        JPanel pnlCartWrapper = new JPanel(new BorderLayout());
        pnlCartWrapper.add(tpGioHang, BorderLayout.CENTER);
        pnlCartWrapper.add(pnlXoaGioHang, BorderLayout.SOUTH);

        JPanel pnlThongTin = new JPanel(new GridLayout(0, 2, 10, 15));
        pnlThongTin.setBorder(BorderFactory.createTitledBorder("THÔNG TIN HÓA ĐƠN"));
        pnlThongTin.setBackground(Color.WHITE);

        lblMaHD = new JLabel("HD...");
        lblMaHD.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblMaHD.setForeground(Color.BLUE);
        
        txtSdtKH = new JTextField();
        txtTenKH = new JTextField();
        
        lblTongTien = new JLabel("0 VNĐ");
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);

        pnlThongTin.add(new JLabel("Mã Hóa Đơn:")); pnlThongTin.add(lblMaHD);
        pnlThongTin.add(new JLabel("SĐT Khách hàng:")); pnlThongTin.add(txtSdtKH);
        pnlThongTin.add(new JLabel("Tên Khách hàng:")); pnlThongTin.add(txtTenKH);
        pnlThongTin.add(new JLabel("TỔNG TIỀN:")); pnlThongTin.add(lblTongTien);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.setBackground(Color.WHITE);
        
        btnHuy = new JButton("Hủy Bỏ");
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLuuThayDoi = new JButton("LƯU THAY ĐỔI");
        btnLuuThayDoi.setBackground(new Color(255, 153, 0)); 
        btnLuuThayDoi.setForeground(Color.WHITE);
        btnLuuThayDoi.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLuuThayDoi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pnlButtons.add(btnHuy);
        pnlButtons.add(btnLuuThayDoi);

        JPanel pnlBottomRight = new JPanel(new BorderLayout());
        pnlBottomRight.add(pnlThongTin, BorderLayout.CENTER);
        pnlBottomRight.add(pnlButtons, BorderLayout.SOUTH);

        pnlRight.add(pnlCartWrapper, BorderLayout.CENTER);
        pnlRight.add(pnlBottomRight, BorderLayout.SOUTH);

        add(pnlLeft, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.EAST);

        addEvents();
    }

    private void loadDataHoaDonCu(String maHD) {
        int indexHD = hdBUS.getIndexById(maHD);
        if (indexHD == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu hóa đơn này!");
            dispose();
            return;
        }
        hoaDonDangSua = hdBUS.getByIndex(indexHD);
        lblMaHD.setText(hoaDonDangSua.getMaHD());
        
        int indexKH = khBUS.getIndexById(hoaDonDangSua.getMaKH());
        if(indexKH != -1) {
            KhachHangDTO kh = khBUS.getByIndex(indexKH);
            txtTenKH.setText(kh.getHo() + " " + kh.getTen());
            txtSdtKH.setText(kh.getSoDT());
        }

        ArrayList<ChiTietHoaDonDTO> listCT = cthdBUS.getAllByMaHD(maHD);
        tongTien = 0;
        if(listCT != null) {
            for(ChiTietHoaDonDTO ct : listCT) {
                int indexSP = spBUS.getIndexById(ct.getMaSP());
                String tenSP = (indexSP != -1) ? spBUS.getByIndex(indexSP).getTenSp() : "SP không xác định";
                
                modelGioHang.addRow(new Object[]{
                    ct.getMaSP(), 
                    tenSP, 
                    ct.getSoLuong(), 
                    df.format(ct.getDonGia()), 
                    df.format(ct.getThanhTien())
                });
                tongTien += ct.getThanhTien();
            }
        }
        lblTongTien.setText(df.format(tongTien));
    }

    private void addEvents() {
        tableSP.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableSP.getSelectedRow();
                if (row != -1) {
                    txtMaSP.setText(modelSP.getValueAt(row, 0).toString());
                    txtTenSP.setText(modelSP.getValueAt(row, 1).toString());
                    txtDonGia.setText(modelSP.getValueAt(row, 3).toString());
                    txtSoLuongMua.setText("1");
                    txtSoLuongMua.requestFocus();
                }
            }
        });

        btnThemSP.addActionListener(e -> addToCart());

        btnXoaDong.addActionListener(e -> {
            int row = tableGioHang.getSelectedRow();
            if (row != -1) {
                String thanhTienStr = modelGioHang.getValueAt(row, 4).toString().replace("VNĐ", "").replace(",", "").trim();
                tongTien -= Double.parseDouble(thanhTienStr);
                lblTongTien.setText(df.format(tongTien));
                modelGioHang.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong danh sách để xóa!");
            }
        });

        btnLuuThayDoi.addActionListener(e -> luuThayDoiHoaDon());
        btnHuy.addActionListener(e -> dispose());
    }

    private void addToCart() {
        try {
            if (txtMaSP.getText().isEmpty()) return;
            String ma = txtMaSP.getText();
            String ten = txtTenSP.getText();
            int sl = Integer.parseInt(txtSoLuongMua.getText());
            double gia = Double.parseDouble(txtDonGia.getText().replace("VNĐ", "").replace(",", "").trim());
            
            int soLuongDaMuaCu = 0;
            ArrayList<ChiTietHoaDonDTO> listCT = cthdBUS.getAllByMaHD(hoaDonDangSua.getMaHD());
            if(listCT != null) {
                for(ChiTietHoaDonDTO ct : listCT) {
                    if(ct.getMaSP().equals(ma)) {
                        soLuongDaMuaCu = ct.getSoLuong(); break;
                    }
                }
            }

            int indexSP = spBUS.getIndexById(ma);
            if(indexSP == -1) return;
            SanPhamDTO spCheck = spBUS.getByIndex(indexSP);
            
            // Tồn kho thực tế = Tồn kho trong DB hiện tại + Số lượng SP này đã mua ở Hóa đơn cũ
            int tonKhoThucTe = spCheck.getSoLuongTon() + soLuongDaMuaCu;

            boolean isExist = false;
            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                if (modelGioHang.getValueAt(i, 0).toString().equals(ma)) {
                    int oldSl = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                    if((oldSl + sl) > tonKhoThucTe) {
                        JOptionPane.showMessageDialog(this, "Tổng số lượng vượt quá tồn kho thực tế (" + tonKhoThucTe + ")!");
                        return;
                    }
                    modelGioHang.setValueAt(oldSl + sl, i, 2);
                    double newThanhTien = (oldSl + sl) * gia;
                    modelGioHang.setValueAt(df.format(newThanhTien), i, 4);
                    isExist = true;
                    tongTien += (sl * gia);
                    break;
                }
            }

            if (!isExist) {
                if(sl > tonKhoThucTe) {
                    JOptionPane.showMessageDialog(this, "Số lượng thêm vượt quá tồn kho thực tế (" + tonKhoThucTe + ")!");
                    return;
                }
                double thanhTien = sl * gia;
                modelGioHang.addRow(new Object[]{ma, ten, sl, df.format(gia), df.format(thanhTien)});
                tongTien += thanhTien;
            }
            lblTongTien.setText(df.format(tongTien));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
        }
    }

    // Logic lấy/thêm khách hàng hoàn thiện
    private String layHoacThemKhachHang(String tenDayDu, String soDT) {
        String maKH = khBUS.getMaByInfo(soDT);
        if (maKH != null) return maKH; 

        String ho = "";
        String ten = tenDayDu;
        int index = tenDayDu.lastIndexOf(" ");
        
        if (index > 0) {
            ho = tenDayDu.substring(0, index).trim();
            ten = tenDayDu.substring(index + 1).trim();
        } else {
            ho = "Khách"; 
            ten = tenDayDu;
        }

        KhachHangDTO khMoi = new KhachHangDTO();
        khMoi.setMaKH(khBUS.generateMaKH()); 
        khMoi.setHo(ho);
        khMoi.setTen(ten);
        khMoi.setDiaChi("Chưa cập nhật"); 
        khMoi.setSoDT(soDT);
        khMoi.setTrangThai(1);
        
        String ketQuaThem = khBUS.add(khMoi);
        if (ketQuaThem != null && ketQuaThem.toLowerCase().contains("thành công")) {
            return khMoi.getMaKH(); 
        }
        
        return null; 
    }

    private void luuThayDoiHoaDon() {
        if (modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Hóa đơn không được để trống sản phẩm!"); return;
        }

        String tenKhach = txtTenKH.getText().trim();
        String soDT = txtSdtKH.getText().trim();
        if (tenKhach.isEmpty() || soDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng!"); return;
        }

        String maKHThatSu = layHoacThemKhachHang(tenKhach, soDT);
        if (maKHThatSu == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không thể tự tạo Khách hàng mới!"); return;
        }

        // ================= LOGIC XỬ LÝ TỒN KHO VÀ DB =================
        String maHD = hoaDonDangSua.getMaHD();

        // 1. Phục hồi tồn kho cũ
        ArrayList<ChiTietHoaDonDTO> listCTOld = cthdBUS.getAllByMaHD(maHD);
        if (listCTOld != null) {
            for (ChiTietHoaDonDTO oldCt : listCTOld) {
                int indexSP = spBUS.getIndexById(oldCt.getMaSP());
                if (indexSP != -1) {
                    SanPhamDTO sp = spBUS.getByIndex(indexSP);
                    sp.setSoLuongTon(sp.getSoLuongTon() + oldCt.getSoLuong()); 
                    spBUS.update(sp);
                }
            }
            // 2. Xóa sạch Chi tiết HD cũ trong DB
            cthdBUS.deleteByMaHD(maHD);
        }

        // 3. Trừ tồn kho mới và Thêm Chi tiết HD mới
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            String maSP = modelGioHang.getValueAt(i, 0).toString();
            int slMua = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
            double gia = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("VNĐ", "").replace(",", "").trim());
            double tien = Double.parseDouble(modelGioHang.getValueAt(i, 4).toString().replace("VNĐ", "").replace(",", "").trim());
            
            cthdBUS.add(new ChiTietHoaDonDTO(maHD, maSP, slMua, gia, tien)); 
            
            int indexSP = spBUS.getIndexById(maSP);
            if (indexSP != -1) {
                SanPhamDTO sp = spBUS.getByIndex(indexSP);
                sp.setSoLuongTon(sp.getSoLuongTon() - slMua); 
                spBUS.update(sp);
            }
        }

        // 4. Cập nhật lại HoaDonDTO
        hoaDonDangSua.setMaKH(maKHThatSu);
        hoaDonDangSua.setTongTien(tongTien);
        
        if (hdBUS.update(hoaDonDangSua)) {
            JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật Hóa Đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTableSanPham(ArrayList<SanPhamDTO> list) {
        modelSP.setRowCount(0);
        if(list == null) return;
        for (SanPhamDTO sp : list) {
            modelSP.addRow(new Object[]{ sp.getMaSp(), sp.getTenSp(), sp.getSoLuongTon(), df.format(sp.getDonGia()) });
        }
    }
}