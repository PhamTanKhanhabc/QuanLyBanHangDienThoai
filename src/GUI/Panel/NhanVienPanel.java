package GUI.Panel;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import GUI.Component.ActionPanel;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Dialog.NhanVienDialog; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class NhanVienPanel extends JPanel {

    private NhanVienBUS nhanVienBUS;
    private TablePanel tablePanel;
    private ActionPanel actionPanel;
    private HeaderRightPanel headerRightPanel;

    public NhanVienPanel() {
        nhanVienBUS = new NhanVienBUS();
        initComponents();
        addEvents();
        loadDataToTable(nhanVienBUS.getAll());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        actionPanel = new ActionPanel();
        actionPanel.configButtons(new String[]{"add", "update", "delete", "export"}); 
        
        headerRightPanel = new HeaderRightPanel();

        topPanel.add(actionPanel, BorderLayout.WEST);
        topPanel.add(headerRightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] header = {"Ma NV", "Ho", "Ten", "Ngay Sinh", "Dia Chi", "Dien Thoai", "Luong Thang"};
        tablePanel = new TablePanel("DANH SACH NHAN VIEN", header);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadDataToTable(ArrayList<NhanVienDTO> list) {
        Object[][] data = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getMaNV();
            data[i][1] = list.get(i).getHo();
            data[i][2] = list.get(i).getTen();
            data[i][3] = list.get(i).getNgaySinh();
            data[i][4] = list.get(i).getDiaChi();
            data[i][5] = list.get(i).getDienThoai();
            data[i][6] = list.get(i).getLuongThang();
        }
        tablePanel.setData(data);
    }

    private void addEvents() {
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = headerRightPanel.getTxtSearch().getText();
                loadDataToTable(nhanVienBUS.search(text));
            }
        });

        headerRightPanel.getBtnReload().addActionListener(e -> {
            nhanVienBUS.refresh();
            loadDataToTable(nhanVienBUS.getAll());
            headerRightPanel.getTxtSearch().setText("");
        });

        actionPanel.btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            NhanVienDialog dialog = new NhanVienDialog(parentFrame, true, "Them", null);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                nhanVienBUS.refresh();
                loadDataToTable(nhanVienBUS.getAll());
            }
        });

        actionPanel.btnUpdate.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien can sua!");
                return;
            }
            
            String maNV = tablePanel.getTable().getValueAt(row, 0).toString();
            NhanVienDTO dtoToEdit = nhanVienBUS.getAll().get(nhanVienBUS.getIndexById(maNV));
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            NhanVienDialog dialog = new NhanVienDialog(parentFrame, true, "Sua", dtoToEdit);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                nhanVienBUS.refresh();
                loadDataToTable(nhanVienBUS.getAll());
            }
        });

        actionPanel.btnDelete.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien can xoa!");
                return;
            }
            
            String maNV = tablePanel.getTable().getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa nhan vien " + maNV + "?", "Xac nhan", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                NhanVienDTO dto = new NhanVienDTO();
                dto.setMaNV(maNV);
                if (nhanVienBUS.delete(dto)) {
                    JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                    loadDataToTable(nhanVienBUS.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Xoa that bai!");
                }
            }
        });
    }
}