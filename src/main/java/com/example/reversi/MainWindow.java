package com.example.reversi;

import com.example.reversi.moves.CaptureChips;
import com.example.reversi.moves.Moves;
import com.example.reversi.moves.PossibleMoves;
import com.example.reversi.talesFactory.ColoredTalesFactory;
import com.example.reversi.talesFactory.Tales;
import com.example.reversi.talesFactory.TalesFactory;
import com.example.reversi.talesFactory.WhiteTale;
import com.example.reversi.talesFactory.WhiteTalesFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    final int grid = 8;
    final int buttonSize = 50;
    final int spacing = 4;

    @FXML
    public Text redScore;
    public Text greenScore;
    public Text redTurn;
    public Text greenTurn;
    public AnchorPane root;

    /* Code smell: Remove redundant button declarations */
    private GridPane buttonGrid = new GridPane();
    Tales[][] tales = new Tales[grid][grid];
    TalesFactory whiteTalesFactory = new WhiteTalesFactory();
    GameManager gameManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonGrid.setHgap(spacing);
        buttonGrid.setVgap(spacing);

        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                Button button = new Button();
                button.setMinSize(buttonSize, buttonSize);
                button.setOnAction(this::place_a_chip);

                buttonGrid.add(button, j, i);

                tales[i][j] = whiteTalesFactory.newTale(button);
            }
        }

        root.getChildren().add(buttonGrid);
        AnchorPane.setTopAnchor(buttonGrid, 60.0);
        AnchorPane.setLeftAnchor(buttonGrid, 115.0);

        restartGame();
    }

    public void place_a_chip(ActionEvent event) {
        Button pressedButton = (Button) event.getSource();
        Integer column = GridPane.getRowIndex(pressedButton);
        Integer row = GridPane.getColumnIndex(pressedButton);

        if (column == null || row == null) return;

        gameManager.placeAChip(row, column, pressedButton);

        changePlayer();
        score();
        if(gameManager.getGameStatus() != GameStatus.ONGOING) gameOver();
    }

    public void score() {
        redScore.setText(String.valueOf(gameManager.getGreenScore()));
        greenScore.setText(String.valueOf(gameManager.getRedScore()));
    }

    private void gameOver() {
        redTurn.setVisible(true);
        greenTurn.setVisible(true);
        if (gameManager.getGameStatus() == GameStatus.RED) {
            redTurn.setText("WINNER");
            greenTurn.setText(":(");
        } else if (gameManager.getGameStatus() == GameStatus.DRAW) {
            redTurn.setText("DRAW");
            greenTurn.setText("DRAW");
        } else {
            greenTurn.setText("WINNER");
            redTurn.setText(":(");
        }
    }

    public void restartGame() {
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                tales[i][j] = whiteTalesFactory.newTale((Button) buttonGrid.getChildren().get(i * grid + j));
            }
        }
        gameManager = new GameManager(tales, 8, buttonGrid);
        score();
    }

    public void changePlayer() {
        Player player = gameManager.getPlayer();
        if (player.getColor() == PlayerColor.GREEN) {
            redTurn.setVisible(false);
            greenTurn.setVisible(true);
            greenTurn.setText("GREEN TURN");
        } else {
            redTurn.setVisible(true);
            greenTurn.setVisible(false);
            redTurn.setText("RED TURN");
        }
    }
}
