package GUI.Panel;

import BUS.VaiTroBUS;
import DTO.VaiTroDTO;
import GUI.Component.ActionPanel;
import GUI.Component.HeaderRightPanel;
import GUI.Component.TablePanel;
import GUI.Dialog.VaiTroDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class VaiTroPanel extends JPanel {

    private VaiTroBUS vaiTroBUS;
    private TablePanel tablePanel;
    private ActionPanel actionPanel;
    private HeaderRightPanel headerRightPanel;

    public VaiTroPanel() {
        vaiTroBUS = new VaiTroBUS();
        initComponents();
        addEvents();
        loadDataToTable(vaiTroBUS.getAll());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        actionPanel = new ActionPanel();
        actionPanel.configButtons(new String[]{"add", "update", "delete"}); 
        
        headerRightPanel = new HeaderRightPanel();

        topPanel.add(actionPanel, BorderLayout.WEST);
        topPanel.add(headerRightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] header = {"Ma Vai Tro", "Ten Vai Tro"};
        tablePanel = new TablePanel("DANH SACH VAI TRO", header);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadDataToTable(ArrayList<VaiTroDTO> list) {
        Object[][] data = new Object[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getMaVaiTro();
            data[i][1] = list.get(i).getTenVaiTro();
        }
        tablePanel.setData(data);
    }

    private void addEvents() {
        headerRightPanel.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = headerRightPanel.getTxtSearch().getText();
                loadDataToTable(vaiTroBUS.search(text));
            }
        });

        headerRightPanel.getBtnReload().addActionListener(e -> {
            vaiTroBUS.refresh();
            loadDataToTable(vaiTroBUS.getAll());
            headerRightPanel.getTxtSearch().setText("");
        });

        actionPanel.btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            VaiTroDialog dialog = new VaiTroDialog(parentFrame, true, "Them", null);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                vaiTroBUS.refresh();
                loadDataToTable(vaiTroBUS.getAll());
            }
        });

        actionPanel.btnUpdate.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon vai tro can sua!");
                return;
            }
            
            String maVaiTro = tablePanel.getTable().getValueAt(row, 0).toString();
            VaiTroDTO dtoToEdit = vaiTroBUS.getAll().get(vaiTroBUS.getIndexById(maVaiTro));
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            VaiTroDialog dialog = new VaiTroDialog(parentFrame, true, "Sua", dtoToEdit);
            dialog.setVisible(true);
            
            if (dialog.isSuccess()) {
                vaiTroBUS.refresh();
                loadDataToTable(vaiTroBUS.getAll());
            }
        });

        actionPanel.btnDelete.addActionListener(e -> {
            int row = tablePanel.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui long chon vai tro can xoa!");
                return;
            }
            
            String maVaiTro = tablePanel.getTable().getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Xoa vai tro " + maVaiTro + " se anh huong den tai khoan. Chac chan xoa?", "Xac nhan", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                VaiTroDTO dto = new VaiTroDTO();
                dto.setMaVaiTro(maVaiTro);
                if (vaiTroBUS.delete(dto)) {
                    JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                    loadDataToTable(vaiTroBUS.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Xoa that bai! (Co the do con tai khoan dang dung vai tro nay)");
                }
            }
        });
    }
}