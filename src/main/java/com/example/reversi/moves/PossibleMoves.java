package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.talesFactory.Tales;

import java.util.ArrayList;
import java.util.List;

/* SOLID violation: Single Responsibility: this class handles bounds as well as directions checking and color comparison*/
public class PossibleMoves implements Moves {
    private final Player player;

    public PossibleMoves(Player player) {
        this.player = player;
    }

    /* Code smell: High cyclomatic complexity */
    /* Code smell: c, r naming */
    @Override
    public List<List<Integer>> checkDirections(int column, int row, Tales[][] tales, int grid) {
        List<List<Integer>> chipCanBePlaced = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (MoveValidator.outOfBoundsWithDirection(row, column, j, i, grid)) continue;
                    chipCanBePlaced = canMove(column + i, row + j, i, j, tales, grid);
                    if (chipCanBePlaced != null) {
                        return chipCanBePlaced;
                    }
                }
            }
        }
        return null;
    }

    /* Code smell: High cyclomatic complexity */
    /* Code smell: c, r, i, j naming */
    @Override
    public List<List<Integer>> canMove(int column, int row, int directionColumn, int directionRow, Tales[][] tales, int grid) {
        List<List<Integer>> chipCanBePlaced = new ArrayList<>();

        if (MoveValidator.outOfBounds(row, column, directionColumn, directionRow, grid) && ColorComparison.isColorStringEqualToEnemyPlayer(tales[column][row].getColor(), player)) {
            return canMove(column + directionColumn, row + directionRow, directionColumn, directionRow, tales, grid);
        } else if (ColorComparison.isColorStringEqualToPlayer(tales[column][row].getColor(), player) && ColorComparison.isColorStringEqualToEnemyPlayer(tales[column - directionColumn][row - directionRow].getColor(), player)) {
            chipCanBePlaced.add(List.of(1, 1));
            return chipCanBePlaced;
        }

        return null;
    }
}
