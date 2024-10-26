package com.example.reversi;

import com.example.reversi.talesFactory.Tales;
import javafx.scene.text.Text;

import java.util.Objects;

public class Player {
    private static Player player;
    String color;

    private Player() {
        this.color = "green";
    }

    public static Player getPlayer() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    public void changePlayer(Text red, Text green) {
        if (Objects.equals(color, "red")) {
            color = "green";
            red.setVisible(false);
            green.setVisible(true);
            green.setText("GREEN TURN");
        }
        else {
            color = "red";
            red.setVisible(true);
            green.setVisible(false);
            red.setText("RED TURN");
        }
    }

    public String getColor(){
        return color;
    }

    public Object getEnemyColor() {
        if (Objects.equals(color, "red")) return "green";
        else return "red";
    }
}
