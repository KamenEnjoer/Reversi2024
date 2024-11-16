package com.example.reversi;

public class Player {
    // Code smell: String of color, when enum is better suited
    PlayerColor color;

    public Player() {
        this.color = PlayerColor.GREEN;
    }

    public void changePlayer() {
        if (color == PlayerColor.RED) {
            color = PlayerColor.GREEN;
        } else {
            color = PlayerColor.RED;
        }
    }

    public PlayerColor getColor() {
        return color;
    }

    public PlayerColor getEnemyColor() {
        if (color == PlayerColor.RED) return PlayerColor.GREEN;
        else return PlayerColor.RED;
    }
}
