package com.example.reversi;

import com.example.reversi.moves.CaptureChips;
import com.example.reversi.moves.Moves;
import com.example.reversi.talesFactory.Tales;
import com.example.reversi.talesFactory.WhiteTalesFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainWindowTest extends ApplicationTest {

    private MainWindow mainWindow;
    private Button buttonMock;
    private Text redScoreMock;
    private Text greenScoreMock;
    private Text redTurnMock;
    private Text greenTurnMock;

    @BeforeEach
    void setUp() {
        mainWindow = new MainWindow();

        // Mock JavaFX components
        buttonMock = mock(Button.class);
        redScoreMock = mock(Text.class);
        greenScoreMock = mock(Text.class);
        redTurnMock = mock(Text.class);
        greenTurnMock = mock(Text.class);

        mainWindow.red_score = redScoreMock;
        mainWindow.green_score = greenScoreMock;
        mainWindow.red_turn = redTurnMock;
        mainWindow.green_turn = greenTurnMock;

        // Mock the `tales` array initialization
        mainWindow.tales = new Tales[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mainWindow.tales[i][j] = mock(Tales.class);
            }
        }
    }

    @Test
    void testScore() {
        // Mock colors in the tales array
        when(mainWindow.tales[0][0].getColor()).thenReturn("red");
        when(mainWindow.tales[0][1].getColor()).thenReturn("green");

        mainWindow.score();

        // Verify that the score Text components were updated
        verify(redScoreMock).setText("1");
        verify(greenScoreMock).setText("1");
    }
}
