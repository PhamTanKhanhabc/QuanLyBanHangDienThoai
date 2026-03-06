/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import java.awt.*;
import javax.swing.*;

public class FilterItem extends JPanel {

    private JLabel lblTitle;
    private JComboBox<String> cboFilter;

    public FilterItem(String title) {
        initComponents(title);
    }

    private void initComponents(String title) {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 80));
        setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblTitle.setPreferredSize(new Dimension(140, 20));

        cboFilter = new JComboBox<>();
        cboFilter.setPreferredSize(new Dimension(170, 40));

        add(lblTitle);
        add(cboFilter);
    }

    public JComboBox<String> getComboBox() {
        return cboFilter;
    }

    public void addItem(String item) {
        cboFilter.addItem(item);
    }

    public String getSelectedItem() {
        return cboFilter.getSelectedItem().toString();
    }
}
