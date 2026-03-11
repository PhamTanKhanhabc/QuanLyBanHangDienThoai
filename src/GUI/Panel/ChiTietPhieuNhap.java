package GUI.Panel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuNhap extends JDialog {
    private String maPhieuNhap;
    
    public ChiTietPhieuNhap(Frame parent, boolean modal, String mpn){
        super(parent, modal);
        this.maPhieuNhap = mpn;
        initUI();
    }
    
    public void initUI(){
        //Thiết lập cửa sổ
        setTitle("Chi tiết phiếu nhập - " + maPhieuNhap);
        setSize(700,500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10,10));
        
        //Panel
        JPanel pnlLeft = new JPanel();
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setPreferredSize(new Dimension(200,0));
        JPanel pnlRight = new JPanel();
        
        add(pnlLeft, BorderLayout.WEST);
        add(pnlRight, BorderLayout.CENTER);
        
    }
}
