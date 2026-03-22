package gui.barchart;

public class LegendItem extends javax.swing.JPanel {
    private gui.barchart.LabelColor lbColor;
    private javax.swing.JLabel lbName;
    public LegendItem(ModelLegend data) {
        initComponents();
        setOpaque(false);
        lbColor.setBackground(data.getColor());
        lbName.setText(data.getName());
    }

    private void initComponents() {

        lbColor = new gui.barchart.LabelColor();
        lbName = new javax.swing.JLabel();

        lbColor.setText("labelColor1");

        lbName.setForeground(new java.awt.Color(100, 100, 100));
        lbName.setText("Name");

        // Layout panel
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0)); 
        // 6px khoảng cách giữa lbColor và lbName, 0px padding dọc

        // Thêm component
        add(lbColor);
        add(lbName);

        // Nếu muốn padding xung quanh panel
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}
