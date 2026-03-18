package GUI.Dialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import DTO.NhaCungCapDTO;

public class XoaNhaCungCapDialog extends JDialog {
    public XoaNhaCungCapDialog(Frame owner, boolean modal, String maNCC, String tenNCC, String sdt, String diaChi) {
        super(owner, modal);
        
        setTitle("Xác Nhận Xóa");
        setSize(600, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new EmptyBorder(15, 0, 0, 0));
        JLabel lblTitle = new JLabel("BẠN CHẮC CHẮN MUỐN XÓA?");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 22));
        lblTitle.setForeground(new Color(220, 53, 69));
        pnlTop.add(lblTitle);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBorder(new EmptyBorder(20, 30, 20, 30));

        Dimension labelSize = new Dimension(130, 40);
        String[] labels = {"Mã NCC:", "Tên nhà cung cấp:", "Số điện thoại:", "Địa chỉ:"};
        String[] values = {maNCC, tenNCC, sdt, diaChi};

        for (int i = 0; i < 4; i++) {
            JPanel row = new JPanel(new BorderLayout(10, 10));
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Roboto", Font.PLAIN, 14));
            lbl.setPreferredSize(labelSize);
            
            JTextField txt = new JTextField(values[i]);
            txt.setEditable(false);
            txt.setBackground(new Color(255, 240, 240)); 
            
            row.add(lbl, BorderLayout.WEST);
            row.add(txt, BorderLayout.CENTER);
            
            pnlCenter.add(row);
            if(i < 3) pnlCenter.add(Box.createVerticalStrut(15));
        }

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnHuy = new JButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton btnXoa = new JButton("Xác nhận Xóa");
        btnXoa.setPreferredSize(new Dimension(150, 40));
        btnXoa.setBackground(new Color(220, 53, 69));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlBottom.add(btnHuy);
        pnlBottom.add(btnXoa);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> dispose());
        
        btnXoa.addActionListener(e -> {
            BUS.NhaCungCapBUS nccBUS = new BUS.NhaCungCapBUS();
            
            // Tìm index dựa vào mã NCC
            int index = nccBUS.getIndexById(maNCC);
            boolean isSuccess = false;
            
            // Nếu tìm thấy, lấy đối tượng DTO ra và thực hiện xóa
            if (index != -1) {
                NhaCungCapDTO nccToDelete = nccBUS.getByIndex(index);
                isSuccess = nccBUS.delete(nccToDelete); 
            }
            
            if (isSuccess) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công nhà cung cấp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại! Nhà cung cấp này có thể đang có dữ liệu ràng buộc trong Phiếu Nhập.", "Lỗi Xóa", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}