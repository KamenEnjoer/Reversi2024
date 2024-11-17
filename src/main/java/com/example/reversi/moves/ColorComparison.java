package com.example.reversi.moves;

import com.example.reversi.Player;

public class ColorComparison {
    public static boolean isColorStringEqualToPlayer(String color, Player player) {
        return color.equals(player.getColor().toString().toLowerCase());
    }

    public static boolean isColorStringEqualToEnemyPlayer(String color, Player player) {
        return color.equals(player.getEnemyColor().toString().toLowerCase());
    }
}
