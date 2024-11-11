package com.example.reversi;

import com.example.reversi.moves.*;
import com.example.reversi.talesFactory.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
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

    public Text red_score;
    public Text green_score;
    public Text red_turn;
    public Text green_turn;

    public AnchorPane root;

    final int grid=8;
    Tales[][] tales = new Tales[grid][grid];
    Player currentPlayer = Player.getPlayer();

    TalesFactory whiteTalesFactory = new WhiteTalesFactory();
    ColoredTalesFactory coloredTalesFactory = new ColoredTalesFactory("green");
    Moves possibleMoves = new PossibleMoves();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restartGame();
    }

    public void place_a_chip(ActionEvent event) {
        Button pressed_button = (Button) event.getSource();
        char[] arr = event.getSource().toString().substring(10, event.getSource().toString().length() - 29).toCharArray();
        int c = (int) arr[0];
        int r = (int) arr[1];
        c-=65; r-=49;

        Moves captureChips = new CaptureChips();
        List<List<Integer>> listOfChanges = captureChips.checkDirections(c, r, tales, grid-1);

        coloredTalesFactory.setColor(currentPlayer.getColor());
        ((WhiteTale) tales[c][r]).setDisable(true);
        tales[c][r] = coloredTalesFactory.newTale(pressed_button, c, r);

        for (int i=0; i<listOfChanges.size(); i++){
            System.out.println(listOfChanges.get(i).get(0) + " - " + listOfChanges.get(i).get(1) + " (" + listOfChanges.size() + "/" + listOfChanges.get(i).size() + ")");
            tales[listOfChanges.get(i).get(0)][listOfChanges.get(i).get(1)].setColor();
        }
        currentPlayer.changePlayer(red_turn, green_turn);
        showPossibleMoves(possibleMoves,false);
        score();
    }

    private void showPossibleMoves(Moves possibleMoves, boolean pass) {
        boolean hasMove = false;
        for (int i=0; i<grid; i++){
            for (int j=0; j<grid; j++){
                if(Objects.equals(tales[i][j].getColor(), "white")){
                    ((WhiteTale) tales[i][j]).setDisable(true);
                    if (possibleMoves.checkDirections(i, j, tales, grid-1)!=null){
                        ((WhiteTale) tales[i][j]).setDisable(false);
                        hasMove = true;
                    }
                }
            }
        }
        if (!hasMove)
        {
            if (pass==true){
                gameOver();
            }
            else {
                currentPlayer.changePlayer(red_turn, green_turn);
                System.out.println("MainWindow -> showPossibleMoves -> !hasMove");
                showPossibleMoves(possibleMoves, true);
            }
        }
    }

    public void score(){
        int red=0, green=0;
        for (int i=0; i<grid; i++){
            for (int j=0; j<grid; j++){
                if(Objects.equals(tales[i][j].getColor(), "red")) red++;
                else if(Objects.equals(tales[i][j].getColor(), "green")) green++;
            }
        }
        red_score.setText(String.valueOf(red));
        green_score.setText(String.valueOf(green));
        if (red+green == grid*grid) gameOver();
    }

    private void gameOver() {
        red_turn.setVisible(true);;
        green_turn.setVisible(true);
        if (Integer.parseInt(red_score.getText()) > Integer.parseInt(green_score.getText())){
            red_turn.setText("WINNER");
            green_turn.setText(":(");
        }
        else if(Integer.parseInt(red_score.getText()) == Integer.parseInt(green_score.getText())){
            red_turn.setText("DRAW");
            green_turn.setText("DRAW");
        }
        else {
            green_turn.setText("WINNER");
            red_turn.setText(":(");
        }
        try {
            Thread.currentThread().interrupt();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void restartGame() {
        for (int i=0; i<grid; i++){
            for (int j=0; j<grid; j++){
                String buttonId = "#" + ((char) ((int) i+65)) + (j+1) + "_BUTTON";
                Button button = (Button) root.lookup(buttonId);
                tales[i][j] = whiteTalesFactory.newTale(button, i, j);
            }
        }
        coloredTalesFactory.setColor(currentPlayer.getColor());
        tales[4][3] = coloredTalesFactory.newTale(E4_BUTTON, 4,3);
        tales[3][4] = coloredTalesFactory.newTale(D5_BUTTON, 3,4);
        currentPlayer.changePlayer(red_turn, green_turn);
        coloredTalesFactory.setColor(currentPlayer.getColor());
        tales[3][3] = coloredTalesFactory.newTale(D4_BUTTON, 3,3);
        tales[4][4] = coloredTalesFactory.newTale(E5_BUTTON, 4,4);
        score();
        showPossibleMoves(possibleMoves, false);
    }
}