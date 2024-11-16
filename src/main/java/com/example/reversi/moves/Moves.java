package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.talesFactory.Tales;

import java.util.List;

public interface Moves {

    List<List<Integer>> checkDirections(int column, int row, Tales[][] tales, int grid);

    List<List<Integer>> canMove(int column, int row, int i, int j, Tales[][] tales, int grid);

    boolean outOfBoundsWithDirection(int row, int column, int directionRow, int directionCol, int grid);

    boolean outOfBounds(int row, int column, int directionColumn, int directionRow, int grid);
}
