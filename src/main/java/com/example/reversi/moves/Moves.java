package com.example.reversi.moves;

import com.example.reversi.talesFactory.Tales;
import java.util.List;

public interface Moves {

    List<List<Integer>> checkDirections(int c, int r, Tales[][] tales, int grid);

    List<List<Integer>> canMove(int c, int r, int i, int j, Tales[][] tales, int grid);

}
