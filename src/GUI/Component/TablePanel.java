/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {

    private DefaultTableModel modal;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel titlePanel;
    private JLabel lblTable;

    public TablePanel(String title, String[] header) {
        initComponents();
        tableLayout(title, header);
    }

    private void initComponents() {

        setBackground(new Color(243,243,243));
        setLayout(new BorderLayout());

        table = new JTable();
        scrollPane = new JScrollPane();
        titlePanel = new JPanel();
        lblTable = new JLabel();

        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);

        scrollPane.setViewportView(table);

        titlePanel.setBackground(new Color(65,120,255));
        titlePanel.setPreferredSize(new Dimension(500,40));
        titlePanel.setLayout(new BorderLayout());

        lblTable.setFont(new Font("Roboto Medium",0,18));
        lblTable.setForeground(new java.awt.Color(255, 255, 255));
        lblTable.setForeground(Color.WHITE);
        lblTable.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(lblTable, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void tableLayout(String title, String[] header) {

        lblTable.setText(title.toUpperCase());

        modal = new DefaultTableModel(null, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(modal);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    public void setData(Object[][] data) {

        modal.setRowCount(0);

        for (Object[] row : data) {
            modal.addRow(row);
        }
    }
    public void loadTable(Object[][] data) {
        modal.setRowCount(0);

        for (Object[] row : data) {
            modal.addRow(row);
        }
    }
    public JTable getTable(){
        return table;
    }
}
