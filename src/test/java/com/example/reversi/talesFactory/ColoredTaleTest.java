package com.example.reversi.talesFactory;

import com.example.reversi.Player;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ColoredTaleTest extends ApplicationTest {

    private ColoredTale coloredTale;
    private Button button;

    @BeforeEach
    void setUp() {
        button = new Button();
        coloredTale = new ColoredTale(button, 0, 0, "red");
    }

    @Test
    void testGetColor() {
        // Verify that the initial color is set correctly
        assertEquals("red", coloredTale.getColor(), "The color should be 'red'");
    }

    @Test
    void testSetColor() {
        // Mock Player for the static getPlayer() call
        try (MockedStatic<Player> playerMock = mockStatic(Player.class)) {
            Player mockPlayer = mock(Player.class);
            when(mockPlayer.getColor()).thenReturn("blue");
            playerMock.when(Player::getPlayer).thenReturn(mockPlayer);

            // Call setColor and verify that the button's color is updated
            coloredTale.setColor();
            assertEquals("blue", coloredTale.getColor(), "The color should be updated to 'blue'");
            assertEquals("-fx-background-color: blue", button.getStyle(), "The button style should be updated");
        }
    }
}
