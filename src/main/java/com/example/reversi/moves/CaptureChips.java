package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.talesFactory.Tales;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CaptureChips implements Moves {
    /* Code smell: High cyclomatic complexity */
    @Override
    public List<List<Integer>> checkDirections(int column, int row, Tales[][] tales, int grid) {
        List<List<Integer>> listOfChanges = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (outOfBoundsWithDirection(row, column, j, i, grid)) continue;
                    if (canMove(column + i, row + j, i, j, tales, grid) != null) {
                        listOfChanges.addAll(canMove(column + i, row + j, i, j, tales, grid));
                    }
                }
            }
        }
        return listOfChanges;
    }

    /* Code smell: High cyclomatic complexity */
    @Override
    public List<List<Integer>> canMove(int column, int row, int directionColumn, int directionRow, Tales[][] tales, int grid) {
        Player currentPlayer = Player.getPlayer();
        String currentPlayerColor = currentPlayer.getColor().toString().toLowerCase();
        String enemyPlayerColor = currentPlayer.getEnemyColor().toString().toLowerCase();
        List<List<Integer>> listOfChanges = new ArrayList<>();
        if (outOfBounds(row, column, directionColumn, directionRow, grid) && Objects.equals(tales[column][row].getColor(), enemyPlayerColor)) {
            listOfChanges = canMove(column + directionColumn, row + directionRow, directionColumn, directionRow, tales, grid);
            if (listOfChanges != null)
                listOfChanges.add(List.of(column, row));
            return listOfChanges;
        } else if (Objects.equals(tales[column][row].getColor(), currentPlayerColor) && Objects.equals(tales[column - directionColumn][row - directionRow].getColor(), enemyPlayerColor)) {
            return listOfChanges;
        }

        return null;
    }

    @Override
    public boolean outOfBoundsWithDirection(int row, int column, int directionRow, int directionCol, int grid) {
        return row + directionRow < 0 || row + directionRow >= grid - 1 || column + directionCol < 0 || column + directionCol >= grid - 1;
    }

    @Override
    public boolean outOfBounds(int row, int column, int directionColumn, int directionRow, int grid) {
        return (row + directionRow >= 0 && column + directionColumn >= 0 && row + directionRow <= grid && column + directionColumn <= grid);
    }

}
