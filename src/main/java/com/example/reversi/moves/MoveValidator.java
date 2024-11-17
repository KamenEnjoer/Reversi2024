package com.example.reversi.moves;

public class MoveValidator {
    public static boolean outOfBoundsWithDirection(int row, int column, int directionRow, int directionCol, int grid) {
        return row + directionRow < 0 || row + directionRow >= grid || column + directionCol < 0 || column + directionCol >= grid;
    }

    public static boolean outOfBounds(int row, int column, int directionColumn, int directionRow, int grid) {
        return (row + directionRow >= 0 && column + directionColumn >= 0 && row + directionRow <= grid && column + directionColumn <= grid);
    }
}
