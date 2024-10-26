package com.example.reversi.talesFactory;
import com.example.reversi.Player;
import javafx.scene.control.Button;

public class WhiteTale implements Tales {
    Button button;
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
    public void setColor(){
        Player currentPlayer = Player.getPlayer();
        button.setStyle("-fx-background-color: " + currentPlayer.getColor());
    }
}
