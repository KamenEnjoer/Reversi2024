package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.talesFactory.Tales;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* SOLID violation: Single Responsibility: this class checks directions and validates moves*/
public class CaptureChips implements Moves {
    private final Player player;

    public CaptureChips(Player player) {
        this.player = player;
    }

    /* Code smell: High cyclomatic complexity */
    /* Code smell: c, r naming */
    @Override
    public List<List<Integer>> checkDirections(int column, int row, Tales[][] tales, int grid) {
        List<List<Integer>> listOfChanges = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (MoveValidator.outOfBoundsWithDirection(row, column, j, i, grid)) continue;
                    if (canMove(column + i, row + j, i, j, tales, grid) != null) {
                        listOfChanges.addAll(canMove(column + i, row + j, i, j, tales, grid));
                    }
                }
            }
        }
        return listOfChanges;
    }

    /* Code smell: High cyclomatic complexity */
    /* Code smell: c, r, i, j naming */
    @Override
    public List<List<Integer>> canMove(int column, int row, int directionColumn, int directionRow, Tales[][] tales, int grid) {
        List<List<Integer>> listOfChanges = new ArrayList<>();
        if (MoveValidator.outOfBounds(row, column, directionColumn, directionRow, grid) && ColorComparison.isColorStringEqualToEnemyPlayer(tales[column][row].getColor(), player)) {
            listOfChanges = canMove(column + directionColumn, row + directionRow, directionColumn, directionRow, tales, grid);
            if (listOfChanges != null) listOfChanges.add(List.of(column, row));
            return listOfChanges;
        } else if (ColorComparison.isColorStringEqualToPlayer(tales[column][row].getColor(), player) && ColorComparison.isColorStringEqualToEnemyPlayer(tales[column - directionColumn][row - directionRow].getColor(), player)) {
            return listOfChanges;
        }
        return null;
    }
}
