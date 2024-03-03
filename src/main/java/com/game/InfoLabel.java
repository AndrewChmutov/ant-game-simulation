package com.game;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class InfoLabel extends InfoComponent {
    String text;
    AtomicInteger toObserve;

    public InfoLabel(String text) {
        this.text = text;
        JLabel label = LabelHandler.getDefaultLabel();
        label.setText(text);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setComponent(label);
    }

    public InfoLabel(String text, AtomicInteger toObserve) {
        this.text = text;
        this.toObserve = toObserve;

        JLabel label = LabelHandler.getDefaultLabel();
        label.setText(text);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setComponent(label);
    }

    public InfoLabel(String text, BufferedImage image) {
        this.text = text;
        JLabel label = LabelHandler.getDefaultLabel();
        label.setText(text);
        label.setIcon(new ImageIcon(image));

        label.setVerticalTextPosition(JLabel.TOP);
        // Center the text horizontally in relation to the icon
        label.setHorizontalTextPosition(JLabel.CENTER);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setComponent(label);
    }

    public String getText() {
        return text;
    }

    @Override
    public void updateComponent() {
        if (toObserve != null) {
            ((JLabel)component).setText(text + toObserve.get());
        }
   }
}
