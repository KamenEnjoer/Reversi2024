package com.example.reversi.talesFactory;

import javafx.scene.control.Button;

public class WhiteTalesFactory extends TalesFactory {
    @Override
    public Tales newTale(Button button) {
        return new WhiteTale(button);
    }
}
