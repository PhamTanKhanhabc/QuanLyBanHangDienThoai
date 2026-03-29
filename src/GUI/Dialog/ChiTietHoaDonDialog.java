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
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ChiTietHoaDonDialog extends JDialog {

    private HoaDonBUS hdBUS = new HoaDonBUS();
    private KhachHangBUS khBUS = new KhachHangBUS();
    private SanPhamBUS spBUS = new SanPhamBUS();
    private ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();

    private JLabel lblMaHD, lblNgayLap, lblNhanVien, lblKhachHang, lblTongTien;
    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;
    
    private DecimalFormat df = new DecimalFormat("#,### VNĐ");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ChiTietHoaDonDialog(JFrame parent, boolean modal, String maHD) {
        super(parent, modal);
        setTitle("Chi Tiết Hóa Đơn");
        setSize(800, 600);
        setLocationRelativeTo(parent);
        
        initComponents();
        loadData(maHD);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        // ================= HEADER: THÔNG TIN TỔNG QUAN =================
        JPanel pnlHeader = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createTitledBorder("THÔNG TIN HÓA ĐƠN"));

        lblMaHD = new JLabel("Mã Hóa Đơn: ");
        lblNgayLap = new JLabel("Ngày lập: ");
        lblNhanVien = new JLabel("Mã Nhân Viên: ");
        lblKhachHang = new JLabel("Khách Hàng: ");
        lblTongTien = new JLabel("Tổng Tiền: ");
        
        lblMaHD.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblMaHD.setForeground(Color.BLUE);
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);

        pnlHeader.add(lblMaHD);
        pnlHeader.add(lblNgayLap);
        pnlHeader.add(lblNhanVien);
        pnlHeader.add(lblKhachHang);
        pnlHeader.add(lblTongTien);

        // ================= CENTER: BẢNG CHI TIẾT SẢN PHẨM =================
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        
        String[] headers = {"STT", "Mã SP", "Tên Sản Phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        TablePanel tpChiTiet = new TablePanel("Danh Sách Sản Phẩm Đã Mua", headers);
        tblChiTiet = tpChiTiet.getTable();
        modelChiTiet = (DefaultTableModel) tblChiTiet.getModel();
        
        pnlCenter.add(tpChiTiet, BorderLayout.CENTER);

        // ================= BOTTOM: NÚT ĐÓNG =================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setBackground(Color.WHITE);
        
        JButton btnDong = new JButton("Đóng");
        btnDong.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnDong.setPreferredSize(new Dimension(120, 40));
        btnDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDong.addActionListener(e -> dispose()); // Tắt Dialog khi bấm
        
        pnlBottom.add(btnDong);

        // Thêm vào Dialog
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private void loadData(String maHD) {
        // 1. Lấy thông tin Hóa Đơn
        int indexHD = hdBUS.getIndexById(maHD);
        if (indexHD == -1) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy dữ liệu hóa đơn này!");
            dispose();
            return;
        }
        
        HoaDonDTO hd = hdBUS.getByIndex(indexHD);
        lblMaHD.setText("Mã Hóa Đơn: " + hd.getMaHD());
        lblNgayLap.setText("Ngày lập: " + (hd.getNgayLapHD() != null ? hd.getNgayLapHD().format(dtf) : ""));
        lblNhanVien.setText("Mã Nhân Viên: " + hd.getMaNV());
        lblTongTien.setText("Tổng Tiền: " + df.format(hd.getTongTien()));

        // Lấy tên Khách Hàng (Thay vì chỉ hiện mã)
        int indexKH = khBUS.getIndexById(hd.getMaKH());
        if (indexKH != -1) {
            KhachHangDTO kh = khBUS.getByIndex(indexKH);
            lblKhachHang.setText("Khách Hàng: " + kh.getHo() + " " + kh.getTen() + " (" + kh.getSoDT() + ")");
        } else {
            lblKhachHang.setText("Khách Hàng: " + hd.getMaKH());
        }

        // 2. Lấy danh sách Chi Tiết Hóa Đơn và đổ lên bảng
        ArrayList<ChiTietHoaDonDTO> listCT = cthdBUS.getAllByMaHD(maHD);
        modelChiTiet.setRowCount(0);
        
        if (listCT != null) {
            int stt = 1;
            for (ChiTietHoaDonDTO ct : listCT) {
                // Lấy tên sản phẩm từ SanPhamBUS
                int indexSP = spBUS.getIndexById(ct.getMaSP());
                String tenSP = (indexSP != -1) ? spBUS.getByIndex(indexSP).getTenSp() : "Sản phẩm không xác định";

                modelChiTiet.addRow(new Object[]{
                    stt++,
                    ct.getMaSP(),
                    tenSP,
                    ct.getSoLuong(),
                    df.format(ct.getDonGia()),
                    df.format(ct.getThanhTien())
                });
            }
        }
    }
}