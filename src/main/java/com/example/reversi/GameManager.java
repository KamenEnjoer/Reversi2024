package com.example.reversi;

import com.example.reversi.moves.CaptureChips;
import com.example.reversi.moves.Moves;
import com.example.reversi.moves.PossibleMoves;
import com.example.reversi.talesFactory.ColoredTalesFactory;
import com.example.reversi.talesFactory.Tales;
import com.example.reversi.talesFactory.WhiteTale;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Objects;

public class GameManager {
    private Tales[][] tales;
    private final int gridSize;
    private final ColoredTalesFactory coloredTalesFactory;
    private final Player player;
    private final Moves captureChips;
    private final Moves movesCalculator;
    private GameStatus gameStatus;
    private int redScore;
    private int greenScore;

    public GameManager(Tales[][] tales, int gridSize, GridPane buttonGrid) {
        this.tales = tales;
        this.captureChips = new CaptureChips();
        this.gridSize = gridSize;
        this.coloredTalesFactory = new ColoredTalesFactory("green");
        this.player = new Player();
        this.movesCalculator = new PossibleMoves();
        this.gameStatus = GameStatus.ONGOING;
        coloredTalesFactory.setColor(player.getColor().toString().toLowerCase());
        tales[4][3] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(4 * gridSize + 3));
        tales[3][4] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(3 * gridSize + 4));
        player.changePlayer();
        coloredTalesFactory.setColor(player.getColor().toString().toLowerCase());
        tales[3][3] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(3 * gridSize + 3));
        tales[4][4] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(4 * gridSize + 4));
        calculateScore();
        showPossibleMoves(false);
    }

    public void placeAChip(int row, int column, Button pressedButton) {
        List<List<Integer>> listOfChanges = captureChips.checkDirections(column, row, tales, gridSize - 1, player);

        coloredTalesFactory.setColor(player.getColor().toString().toLowerCase());
        ((WhiteTale) tales[column][row]).setDisable(true);
        tales[column][row] = coloredTalesFactory.newTale(pressedButton);

        for (List<Integer> change : listOfChanges) {
            tales[change.get(0)][change.get(1)].setColor(player);
        }
        player.changePlayer();
        showPossibleMoves(false);
        calculateScore();
    }

    private void calculateScore() {
        int redScoreCounter = 0, greenScoreCounter = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (Objects.equals(tales[i][j].getColor(), "red")) redScoreCounter++;
                if (Objects.equals(tales[i][j].getColor(), "green")) greenScoreCounter++;
            }
        }
        redScore = redScoreCounter;
        greenScore = greenScoreCounter;
    }

    private void showPossibleMoves(boolean pass) {
        boolean hasMove = false;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (Objects.equals(tales[i][j].getColor(), "white")) {
                    ((WhiteTale) tales[i][j]).setDisable(true);
                    if (movesCalculator.checkDirections(i, j, tales, gridSize - 1, player) != null) {
                        ((WhiteTale) tales[i][j]).setDisable(false);
                        hasMove = true;
                    }
                }
            }
        }
        calculateGameStatus(hasMove, pass);
    }

    public int getRedScore() {
        return redScore;
    }

    public int getGreenScore() {
        return greenScore;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private void calculateGameStatus(boolean hasValidMoves, boolean pass) {
        if (!hasValidMoves) {
            if (pass) {
                if(greenScore > redScore) {
                    gameStatus = GameStatus.GREEN;
                } else if (redScore > greenScore) {
                    gameStatus = GameStatus.RED;
                } else {
                    gameStatus = GameStatus.DRAW;
                }
            } else {
                player.changePlayer();
                showPossibleMoves(true);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
}
