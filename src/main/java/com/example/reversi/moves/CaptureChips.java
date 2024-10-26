package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.Test;
import com.example.reversi.talesFactory.Tales;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CaptureChips implements Moves{
    @Override
    public List<List<Integer>> checkDirections(int c, int r, Tales[][] tales, int grid){
        List<List <Integer>> listOfChanges = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (c==0 && i==-1) continue;
                    if (c==grid && i==1) continue;
                    if (r==0 && j==-1) continue;
                    if (r==grid && j==1) continue;
                    if (canMove(c+i, r+j, i, j, tales, grid)!=null) {
                        listOfChanges.addAll(canMove(c+i, r+j, i, j, tales, grid));
                    }
                }
            }
        }
        Test.notNull(listOfChanges, "List of changes in " + c + "-" + r + " is null; (CaptureChips Class)");
        return listOfChanges;
    }

    @Override
    public List<List<Integer>> canMove(int c, int r, int i, int j, Tales[][] tales, int grid){
        Player currentPlayer = Player.getPlayer();
        List<List <Integer>> listOfChanges = new ArrayList<>();
        if ((r+j >= 0 && c+i >= 0 && r+j <= grid && c+i <= grid) && Objects.equals(tales[c][r].getColor(), currentPlayer.getEnemyColor())) {
            listOfChanges = canMove(c+i, r+j, i, j, tales, grid);
            if(listOfChanges!=null)
                listOfChanges.add(List.of(c, r));
            return listOfChanges;
        } else if (Objects.equals(tales[c][r].getColor(), currentPlayer.getColor()) && Objects.equals(tales[c-i][r-j].getColor(), currentPlayer.getEnemyColor())) {
            return listOfChanges;
        }

        return null;
    }

}
