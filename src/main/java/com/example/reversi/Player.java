package com.example.reversi;

public class Player {
    private static Player player;
    // Code smell: String of color, when enum is better suited
    PlayerColor color;

    private Player() {
        this.color = PlayerColor.GREEN;
    }

    public static Player getPlayer() {
        if (player == null) {
            player = new Player();
        }
        return player;
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
