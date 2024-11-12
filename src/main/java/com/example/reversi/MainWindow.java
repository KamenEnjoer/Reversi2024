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
    private GridPane buttonGrid = new GridPane();

    Tales[][] tales = new Tales[grid][grid];
    Player currentPlayer = Player.getPlayer();

    TalesFactory whiteTalesFactory = new WhiteTalesFactory();
    ColoredTalesFactory coloredTalesFactory = new ColoredTalesFactory("green");
    Moves possibleMoves = new PossibleMoves();

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

        // Размещаем сетку в корневом AnchorPane
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

        Moves captureChips = new CaptureChips();
        List<List<Integer>> listOfChanges = captureChips.checkDirections(column, row, tales, grid - 1);

        coloredTalesFactory.setColor(currentPlayer.getColor().toString().toLowerCase());
        ((WhiteTale) tales[column][row]).setDisable(true);
        tales[column][row] = coloredTalesFactory.newTale(pressedButton);

        for (List<Integer> change : listOfChanges) {
            tales[change.get(0)][change.get(1)].setColor();
        }
        changePlayer();
        showPossibleMoves(possibleMoves, false);
        score();
    }

    private void showPossibleMoves(Moves possibleMoves, boolean pass) {
        boolean hasMove = false;
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                if (Objects.equals(tales[i][j].getColor(), "white")) {
                    ((WhiteTale) tales[i][j]).setDisable(true);
                    if (possibleMoves.checkDirections(i, j, tales, grid - 1) != null) {
                        ((WhiteTale) tales[i][j]).setDisable(false);
                        hasMove = true;
                    }
                }
            }
        }
        if (!hasMove) {
            if (pass) {
                gameOver();
            } else {
                changePlayer();
                showPossibleMoves(possibleMoves, true);
            }
        }
    }

    public void score() {
        int red = 0, green = 0;
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                if (Objects.equals(tales[i][j].getColor(), "red")) red++;
                else if (Objects.equals(tales[i][j].getColor(), "green")) green++;
            }
        }
        redScore.setText(String.valueOf(red));
        greenScore.setText(String.valueOf(green));
        if (red + green == grid * grid) gameOver();
    }

    private void gameOver() {
        redTurn.setVisible(true);
        greenTurn.setVisible(true);
        if (Integer.parseInt(redScore.getText()) > Integer.parseInt(greenScore.getText())) {
            redTurn.setText("WINNER");
            greenTurn.setText(":(");
        } else if (Integer.parseInt(redScore.getText()) == Integer.parseInt(greenScore.getText())) {
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
        coloredTalesFactory.setColor(currentPlayer.getColor().toString().toLowerCase());
        tales[4][3] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(4 * grid + 3));
        tales[3][4] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(3 * grid + 4));
        changePlayer();
        coloredTalesFactory.setColor(currentPlayer.getColor().toString().toLowerCase());
        tales[3][3] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(3 * grid + 3));
        tales[4][4] = coloredTalesFactory.newTale((Button) buttonGrid.getChildren().get(4 * grid + 4));

        score();
        showPossibleMoves(possibleMoves, false);
    }

    public void changePlayer() {
        currentPlayer.changePlayer();
        if (currentPlayer.getColor() == PlayerColor.GREEN) {
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
