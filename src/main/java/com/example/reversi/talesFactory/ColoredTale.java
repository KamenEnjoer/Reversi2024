package com.example.reversi.talesFactory;

import com.example.reversi.Player;
import javafx.scene.control.Button;

public class ColoredTale implements Tales {
    Button button;
    int c, r;

    public ColoredTale(Button button, int c, int r, String color) {
        button.setStyle("-fx-background-color: " + color);
        button.setDisable(true);
        this.button = button;
        this.c = c;
        this.r = r;
    }

    @Override
    public String getColor() {
        return button.getStyle().substring(22);
    }

    @Override
    public void setColor() {
        Player currentPlayer = Player.getPlayer();
        button.setStyle("-fx-background-color: " + currentPlayer.getColor());
    }
}
