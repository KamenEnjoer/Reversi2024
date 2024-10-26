package com.example.reversi.talesFactory;

import javafx.scene.control.Button;

public class WhiteTalesFactory extends TalesFactory {
    @Override
    public Tales newTale(Button button, int c, int r) {
        return new WhiteTale(button, c, r);
    }
}
