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
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    final int grid = 8;
    @FXML
    public Button A1_BUTTON, A2_BUTTON, A3_BUTTON, A4_BUTTON, A5_BUTTON, A6_BUTTON, A7_BUTTON, A8_BUTTON;
    @FXML
    public Button B1_BUTTON, B2_BUTTON, B3_BUTTON, B4_BUTTON, B5_BUTTON, B6_BUTTON, B7_BUTTON, B8_BUTTON;
    @FXML
    public Button C1_BUTTON, C2_BUTTON, C3_BUTTON, C4_BUTTON, C5_BUTTON, C6_BUTTON, C7_BUTTON, C8_BUTTON;
    @FXML
    public Button D1_BUTTON, D2_BUTTON, D3_BUTTON, D4_BUTTON, D5_BUTTON, D6_BUTTON, D7_BUTTON, D8_BUTTON;
    @FXML
    public Button E1_BUTTON, E2_BUTTON, E3_BUTTON, E4_BUTTON, E5_BUTTON, E6_BUTTON, E7_BUTTON, E8_BUTTON;
    @FXML
    public Button F1_BUTTON, F2_BUTTON, F3_BUTTON, F4_BUTTON, F5_BUTTON, F6_BUTTON, F7_BUTTON, F8_BUTTON;
    @FXML
    public Button G1_BUTTON, G2_BUTTON, G3_BUTTON, G4_BUTTON, G5_BUTTON, G6_BUTTON, G7_BUTTON, G8_BUTTON;
    @FXML
    public Button H1_BUTTON, H2_BUTTON, H3_BUTTON, H4_BUTTON, H5_BUTTON, H6_BUTTON, H7_BUTTON, H8_BUTTON;
    /* Code smell: inconsistent variable naming practice
     * snake_case instead of camelCase
     * */
    public Text redScore;
    public Text greenScore;
    public Text redTurn;
    public Text greenTurn;
    public AnchorPane root;
    Tales[][] tales = new Tales[grid][grid];
    Player currentPlayer = Player.getPlayer();

    TalesFactory whiteTalesFactory = new WhiteTalesFactory();
    ColoredTalesFactory coloredTalesFactory = new ColoredTalesFactory("green");
    Moves possibleMoves = new PossibleMoves();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restartGame();
    }

    public int convertUppercaseLetterToInt(char character) {
        return (int) character - (int) 'A';
    }

    public int convertCharNumberToInt(char character) {
        return (int) character - (int) '1';
    }

    public void place_a_chip(ActionEvent event) {
        Button pressed_button = (Button) event.getSource();
        char[] arr = event.getSource().toString().substring(10, event.getSource().toString().length() - 29).toCharArray();
        /* Code smell: Hardcoded integers */
        int column = convertUppercaseLetterToInt(arr[0]);
        int row = convertCharNumberToInt(arr[1]);

        Moves captureChips = new CaptureChips();
        List<List<Integer>> listOfChanges = captureChips.checkDirections(column, row, tales, grid - 1);

        coloredTalesFactory.setColor(currentPlayer.getColor().toString().toLowerCase());
        ((WhiteTale) tales[column][row]).setDisable(true);
        tales[column][row] = coloredTalesFactory.newTale(pressed_button);

        for (int i = 0; i < listOfChanges.size(); i++) {
            System.out.println(listOfChanges.get(i).get(0) + " - " + listOfChanges.get(i).get(1) + " (" + listOfChanges.size() + "/" + listOfChanges.get(i).size() + ")");
            tales[listOfChanges.get(i).get(0)][listOfChanges.get(i).get(1)].setColor();
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
            /* Code smell: redudant equality*/
            if (pass) {
                gameOver();
            } else {
                changePlayer();
                System.out.println("MainWindow -> showPossibleMoves -> !hasMove");
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
        ;
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
        try {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void restartGame() {
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                String buttonId = "#" + ((char) ((int) i + 65)) + (j + 1) + "_BUTTON";
                Button button = (Button) root.lookup(buttonId);
                tales[i][j] = whiteTalesFactory.newTale(button);
            }
        }
        coloredTalesFactory.setColor(currentPlayer.getColor().toString().toLowerCase());
        tales[4][3] = coloredTalesFactory.newTale(E4_BUTTON);
        tales[3][4] = coloredTalesFactory.newTale(D5_BUTTON);
        changePlayer();
        coloredTalesFactory.setColor(currentPlayer.getColor().toString().toLowerCase());
        tales[3][3] = coloredTalesFactory.newTale(D4_BUTTON);
        tales[4][4] = coloredTalesFactory.newTale(E5_BUTTON);

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