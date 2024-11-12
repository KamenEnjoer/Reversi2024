package com.example.reversi.talesFactory;

import com.example.reversi.Player;
import javafx.scene.control.Button;

// Code smell: naming Tale doesn't represent cell
public class WhiteTale implements Tales {
    /* Violation of SOLID Dependency Inversion:
     * Entities must depend on abstractions, not on concretions.
     * It states that the high-level module must not depend on the low-level module,
     * but they should depend on abstractions.
     * */
    Button button;

    /*
     * Code smell: (c and r variable naming)
     * Violation of SOLID Single Responsibility:
     * It is responsible for both UI and logic
     * */
    public WhiteTale(Button button, int c, int r) {
        button.setStyle("-fx-background-color: white");
        button.setDisable(true);
        this.button = button;
    }

    @Override
    public String getColor() {
        return "white";
    }


    public void setDisable(Boolean disable) {
        button.setDisable(disable);
    }

    @Override
    public void setColor() {
        Player currentPlayer = Player.getPlayer();
        button.setStyle("-fx-background-color: " + currentPlayer.getColor());
    }
}
