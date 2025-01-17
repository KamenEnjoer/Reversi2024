package com.example.reversi.talesFactory;

import javafx.scene.control.Button;

public class ColoredTalesFactory extends TalesFactory {
    String color;
    public ColoredTalesFactory(String color) {
        this.color = color;
    }
    public void setColor(String color){
        this.color = color;
    }

    @Override
    public Tales newTale(Button button, int c, int r) {
        return new ColoredTale(button, c, r, color);
    }
}
