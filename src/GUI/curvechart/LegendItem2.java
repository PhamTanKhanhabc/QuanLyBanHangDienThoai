package gui.curvechart;

public class LegendItem2 extends javax.swing.JPanel {
    private gui.curvechart.LabelColor2 lbColor;
    private javax.swing.JLabel lbName;
    public LegendItem2(ModelLegend2 data) {
        initComponents();
        setOpaque(false);
        lbColor.setBackground(data.getColor());
        lbColor.setForeground(data.getColorLight());
        lbName.setText(data.getName());
    }

    private void initComponents() {

        lbColor = new gui.curvechart.LabelColor2();
        lbName = new javax.swing.JLabel();

        lbColor.setText("labelColor1");
        lbColor.setPreferredSize(new java.awt.Dimension(16, 16));

        lbName.setForeground(new java.awt.Color(180, 180, 180));
        lbName.setText("Name");

        // Layout panel: FlowLayout ngang, canh trái
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 5));
        // 6px giữa lbColor và lbName, 5px padding dọc giống containerGap()

        // Thêm component
        add(lbColor);
        add(lbName);

        // Optional: padding xung quanh panel
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }                                     
}
