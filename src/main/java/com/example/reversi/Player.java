package com.example.reversi;

/* Code smell: global state of static Player that introduces hidden coupling
* */
public class Player {
    private PlayerColor color;

    public Player(PlayerColor initialColor) {
        this.color = initialColor;
    }

    public Player() {
        this(PlayerColor.GREEN);
    }

    public void switchColor() {
        color = color.getEnemyColor();
    }

    public PlayerColor getColor() {
        return color;
    }

    public PlayerColor getEnemyColor() {
        return color.getEnemyColor();
    }
}
