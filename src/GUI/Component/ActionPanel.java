/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import GUI.Component.ButtonToolBar;
/**
 *
 * @author user
 */
public class ActionPanel extends JPanel {
    public ButtonToolBar btnAdd;
    public ButtonToolBar btnUpdate;
    public ButtonToolBar btnDelete;
    public ButtonToolBar btnInfo;
    public ButtonToolBar btnImport;
    public ButtonToolBar btnExport;
    public ButtonToolBar btnThuocTinh;
    public ActionPanel(){
        setBackground(new java.awt.Color(255,255,255));
        setLayout(new FlowLayout(FlowLayout.LEFT, 4, 8));
        
        btnAdd = new ButtonToolBar("THÊM", "./icon/add.svg", 75, 75, 13, "add");
        btnUpdate = new ButtonToolBar("SỬA", "./icon/update.svg", 75, 75, 13, "update");
        btnDelete = new ButtonToolBar("XÓA", "./icon/delete.svg", 75, 75, 13, "delete");
        btnInfo = new ButtonToolBar("INFO", "./icon/info.svg", 75, 75, 13, "info");
        btnImport = new ButtonToolBar("IMPORT", "./icon/import.svg", 85, 75, 13, "import");
        btnExport = new ButtonToolBar("EXPORT", "./icon/export.svg", 85, 75, 13, "export");
        btnThuocTinh = new ButtonToolBar("THUỘC TÍNH", "./icon/menu.svg", 100, 90, 10, "attribute");
        
        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnInfo);
        add(btnImport);
        add(btnExport);
        add(btnThuocTinh);
        
        // Bo góc
        JButton[] list = {btnAdd, btnUpdate, btnDelete, btnInfo, btnImport, btnExport, btnThuocTinh};
        for(JButton b : list){
            b.putClientProperty(FlatClientProperties.STYLE,"arc:15");
        }
    }
    public void configButtons(String[] buttons) {

        btnAdd.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnInfo.setVisible(false);
        btnImport.setVisible(false);
        btnExport.setVisible(false);
        btnThuocTinh.setVisible(false);

        for (String b : buttons) {
            switch (b) {
                case "add": btnAdd.setVisible(true); break;
                case "update": btnUpdate.setVisible(true); break;
                case "delete": btnDelete.setVisible(true); break;
                case "info": btnInfo.setVisible(true); break;
                case "import": btnImport.setVisible(true); break;
                case "export": btnExport.setVisible(true); break;
                case "attribute": btnThuocTinh.setVisible(true); break;
            }
        }
    }
}
