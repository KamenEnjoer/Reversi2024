package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.talesFactory.ColoredTalesFactory;
import com.example.reversi.talesFactory.Tales;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CaptureChipsTest {
    private CaptureChips captureChips;
    private Tales[][] tales;
    private Player player;

    @BeforeEach
    void setUp() {
        captureChips = new CaptureChips();
        int grid = 8; // Assume an 8x8 grid
        tales = new Tales[grid][grid];

        // Initialize Tales mock objects
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                tales[i][j] = mock(Tales.class);
            }
        }

        // Mock Player setup
        player = mock(Player.class);
        when(player.getColor()).thenReturn("green");
        when(player.getEnemyColor()).thenReturn("red");
    }

    @Test
    void testCheckDirections_SimpleValidCapture() {
        // Arrange: Set up a simple capture scenario
        int c = 3, r = 3;
        int grid = 7; // Set boundary grid limit

        // Mock colors for tales to simulate opponent pieces and current player pieces
        when(tales[3][3].getColor()).thenReturn("green");
        when(tales[4][3].getColor()).thenReturn("red");
        when(tales[5][3].getColor()).thenReturn("green");

        // Act: Call checkDirections to get capture moves
        List<List<Integer>> result = captureChips.checkDirections(c, r, tales, grid);

        // Assert: Verify expected capture coordinates
        List<List<Integer>> expectedChanges = List.of(List.of(4, 3));
        assertEquals(expectedChanges, result);
    }

    @Test
    void testCanMove_ChainCapture() {
        // Arrange: Line of opponent pieces followed by player's piece
        int c = 2, r = 3;
        int i = 1, j = 0; // Direction moving right

        when(tales[2][3].getColor()).thenReturn("red");
        when(tales[3][3].getColor()).thenReturn("red");
        when(tales[4][3].getColor()).thenReturn("green");

        // Act: Call canMove to see if it detects this capture chain
        List<List<Integer>> result = captureChips.canMove(c, r, i, j, tales, 7);

        // Assert: Verify the chain of captured coordinates
        List<List<Integer>> expectedChanges = List.of(List.of(3, 3), List.of(2, 3));
        assertEquals(expectedChanges, result);
    }

    @Test
    void testCheckDirections_NoCaptureAtEdge() {
        // Arrange: Player piece at the edge, no valid moves
        int c = 0, r = 0;
        int grid = 7;

        when(tales[0][0].getColor()).thenReturn("WHITE");

        // Act: Call checkDirections to check for any moves
        List<List<Integer>> result = captureChips.checkDirections(c, r, tales, grid);

        // Assert: Expect no valid moves at the edge
        assertTrue(result.isEmpty());
    }

    @Test
    void testCanMove_NoValidMove_ReturnsNull() {
        // Arrange: Set up a situation with no capture opportunities
        int c = 3, r = 3;
        int i = 1, j = 0;

        when(tales[3][3].getColor()).thenReturn("green");
        when(tales[4][3].getColor()).thenReturn("green");

        // Act: Call canMove expecting no captures in this direction
        List<List<Integer>> result = captureChips.canMove(c, r, i, j, tales, 7);

        // Assert: Expect null since no capture move is possible
        assertNull(result);
    }

    @Test
    void testCheckDirections_MultipleDirectionsCapture() {
        // Arrange: Set up scenario with multiple captures in different directions
        int c = 3, r = 3;
        int grid = 7;

        when(tales[3][3].getColor()).thenReturn("green");
        when(tales[4][3].getColor()).thenReturn("red");
        when(tales[5][3].getColor()).thenReturn("green");
        when(tales[3][4].getColor()).thenReturn("red");
        when(tales[3][5].getColor()).thenReturn("green");

        // Act: Call checkDirections
        List<List<Integer>> result = captureChips.checkDirections(c, r, tales, grid);

        // Assert: Verify both directions captured correctly
        List<List<Integer>> expectedChanges = List.of(List.of(3, 4), List.of(4, 3));
        assertEquals(expectedChanges, result);
    }
}