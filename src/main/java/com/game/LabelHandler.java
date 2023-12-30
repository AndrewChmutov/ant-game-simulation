package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LabelHandler {
    public static JLabel getDefaultLabel() {
        JLabel label = new JLabel();
        label.setForeground(Color.white);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        return label;
   }
}
