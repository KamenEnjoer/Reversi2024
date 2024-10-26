package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.Test;
import com.example.reversi.talesFactory.Tales;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.addAll;

public class PossibleMoves implements Moves{

    @Override
    public List<List<Integer>> checkDirections(int c, int r, Tales[][] tales, int grid){
        List<List <Integer>> chipCanBePlaced = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (c==0 && i==-1) continue;
                    if (c==grid && i==1) continue;
                    if (r==0 && j==-1) continue;
                    if (r==grid && j==1) continue;
                    chipCanBePlaced=canMove(c+i, r+j, i, j, tales, grid);
                    if (chipCanBePlaced!=null) {
                        return chipCanBePlaced;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<List<Integer>> canMove(int c, int r, int i, int j, Tales[][] tales, int grid){
        Player currentPlayer = Player.getPlayer();
        List<List <Integer>> chipCanBePlaced = new ArrayList<>();
        Test.outOfBounds(r, 0, grid, "(PossibleMoves);");
        Test.outOfBounds(c, 0, grid, "(PossibleMoves);");

        if ((r+j >= 0 && c+i >= 0 && r+j <= grid && c+i <= grid) && Objects.equals(tales[c][r].getColor(), currentPlayer.getEnemyColor())) {
            return canMove(c+i, r+j, i, j, tales, grid);
        } else if (Objects.equals(tales[c][r].getColor(), currentPlayer.getColor()) && Objects.equals(tales[c-i][r-j].getColor(), currentPlayer.getEnemyColor())) {
            chipCanBePlaced.add(List.of(1, 1));
            return chipCanBePlaced;
        }

        return null;
    }
}
