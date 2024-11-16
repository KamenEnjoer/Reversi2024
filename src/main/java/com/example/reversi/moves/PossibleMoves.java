package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.talesFactory.Tales;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PossibleMoves implements Moves {

    /* Code smell: High cyclomatic complexity */
    @Override
    public List<List<Integer>> checkDirections(int column, int row, Tales[][] tales, int grid, Player player) {
        List<List<Integer>> chipCanBePlaced = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (outOfBoundsWithDirection(row, column, j, i, grid)) continue;
                    chipCanBePlaced = canMove(column + i, row + j, i, j, tales, grid, player);
                    if (chipCanBePlaced != null) {
                        return chipCanBePlaced;
                    }
                }
            }
        }
        return null;
    }

    /* Code smell: High cyclomatic complexity */
    @Override
    public List<List<Integer>> canMove(int column, int row, int directionColumn, int directionRow, Tales[][] tales, int grid, Player player) {
        String currentPlayerColor = player.getColor().toString().toLowerCase();
        String enemyPlayerColor = player.getEnemyColor().toString().toLowerCase();
        List<List<Integer>> chipCanBePlaced = new ArrayList<>();

        if (outOfBounds(row, column, directionColumn, directionRow, grid) && Objects.equals(tales[column][row].getColor(), enemyPlayerColor)) {
            return canMove(column + directionColumn, row + directionRow, directionColumn, directionRow, tales, grid, player);
        } else if (Objects.equals(tales[column][row].getColor(), currentPlayerColor) && Objects.equals(tales[column - directionColumn][row - directionRow].getColor(), enemyPlayerColor)) {
            chipCanBePlaced.add(List.of(1, 1));
            return chipCanBePlaced;
        }

        return null;
    }

    @Override
    public boolean outOfBoundsWithDirection(int row, int column, int directionRow, int directionCol, int grid) {
        return row + directionRow < 0 || row + directionRow >= grid || column + directionCol < 0 || column + directionCol >= grid;
    }

    @Override
    public boolean outOfBounds(int row, int column, int directionColumn, int directionRow, int grid) {
        return (row + directionRow >= 0 && column + directionColumn >= 0 && row + directionRow <= grid && column + directionColumn <= grid);
    }
}
