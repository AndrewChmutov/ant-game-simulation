package com.game;

import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;

public class InfoLabel extends InfoComponent {
    String text;
    AtomicInteger toObserve;

    public InfoLabel(String text, AtomicInteger toObserve) {
        this.text = text;
        this.toObserve = toObserve;

        JLabel label = LabelHandler.getDefaultLabel();

        super.setComponent(label);
    }

    @Override
    void updateComponent() {
        ((JLabel)component).setText(text + toObserve.get());
   }
}
