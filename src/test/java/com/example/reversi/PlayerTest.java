package com.example.reversi;

import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;
    private Text redText;
    private Text greenText;

    @BeforeEach
    void setUp() {
        // Initialize Player and mock Text components
        player = Player.getPlayer();
        redText = mock(Text.class);
        greenText = mock(Text.class);
    }

    @Test
    void testSingletonInstance() {
        // Ensure Player.getPlayer() always returns the same instance
        Player anotherPlayer = Player.getPlayer();
        assertSame(player, anotherPlayer, "Player.getPlayer() should return the same instance");
    }

    @Test
    void testInitialColor() {
        // Verify the initial color of the player
        assertEquals("green", player.getColor(), "The initial color should be 'green'");
    }

    @Test
    void testChangePlayerToRed() {
        // Change color from green to red and verify the text changes
        player.changePlayer(redText, greenText);

        // Verify that green text is hidden and red text is shown with correct content
        assertEquals("red", player.getColor(), "The color should be 'red' after changePlayer is called");
        verify(redText).setVisible(true);
        verify(greenText).setVisible(false);
        verify(redText).setText("RED TURN");
        player.changePlayer(redText, greenText);
    }

    @Test
    void testChangePlayerToGreen() {
        // Change color to red first
        player.changePlayer(redText, greenText);

        // Change color back to green
        player.changePlayer(redText, greenText);

        // Verify that red text is hidden and green text is shown with correct content
        assertEquals("green", player.getColor(), "The color should be 'green' after changePlayer is called twice");
        verify(greenText).setVisible(true);
        verify(redText).setVisible(false);
        verify(greenText).setText("GREEN TURN");
    }

    @Test
    void testGetEnemyColor() {
        // Verify that enemy color is correct based on the current player color
        player.changePlayer(redText, greenText);
        assertEquals("green", player.getEnemyColor(), "Enemy color should be 'green' when player color is 'red'");

        player.changePlayer(redText, greenText);
        assertEquals("red", player.getEnemyColor(), "Enemy color should be 'red' when player color is 'green'");
    }
}
