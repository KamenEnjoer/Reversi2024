package com.example.reversi.talesFactory;

import com.example.reversi.Player;
import javafx.scene.control.Button;

// Naming Tale doesn't represent cell
public class ColoredTale implements Tales {
    /* Violation of SOLID Dependency Inversion:
     * Entities must depend on abstractions, not on concretions.
     * It states that the high-level module must not depend on the low-level module,
     * but they should depend on abstractions.
     * */
    Button button;
    int c, r;

    /*
     * Violation of Naming Conventions (c and r)
     * Violation of SOLID Single Responsibility:
     * It is responsible for both UI and logic
     * */
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
