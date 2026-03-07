package GUI.Component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class ButtonToolBar extends JButton {

    private String permission;

    public ButtonToolBar(String text, String iconPath, int width, int height, int fontSize, String permission) {
        this.permission = permission;
        initComponent(text, iconPath, width, height, fontSize);
    }

    private void initComponent(String text, String iconPath, int width, int height, int fontSize) {

        setFont(new Font("Roboto", Font.BOLD, fontSize));
        setIcon(new FlatSVGIcon(iconPath, 24, 24));
        setText(text);

        setBorder(null);
        setBorderPainted(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);

        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);

        setPreferredSize(new Dimension(width, height));
    }

    public String getPermission() {
        return permission;
    }
}

