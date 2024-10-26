package com.example.reversi.talesFactory;

import javafx.scene.control.Button;

public abstract class TalesFactory {
    public abstract Tales newTale(Button button, int c, int r);
}

