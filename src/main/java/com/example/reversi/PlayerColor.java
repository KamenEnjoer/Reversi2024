package com.example.reversi;

/* Code smell: String are used instead of enums when string can only take red or green values
* */
public enum PlayerColor {
    RED, GREEN;

    public PlayerColor getEnemyColor() {
        return this == RED ? GREEN : RED;
    }
}
