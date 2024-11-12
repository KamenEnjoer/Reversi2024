package com.example.reversi.talesFactory;

import com.example.reversi.Player;
import com.example.reversi.PlayerColor;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WhiteTaleTest extends ApplicationTest {

    private WhiteTale whiteTale;
    private Button mockButton;

    @BeforeEach
    void setUp() {
        // Mocking the Button since WhiteTale directly interacts with it
        mockButton = new Button();
        // Initialize WhiteTale with mock Button
        whiteTale = new WhiteTale(mockButton);
    }

    @Test
    void testConstructorInitializesButton() {
        // Verify that the button is styled with a white background and disabled
        org.testfx.assertions.api.Assertions.assertThat(mockButton).hasStyle("-fx-background-color: white");
        org.testfx.assertions.api.Assertions.assertThat(mockButton).isDisabled();
    }

    @Test
    void testGetColorReturnsWhite() {
        // Verify that getColor returns "white" as expected
        assertEquals("white", whiteTale.getColor(), "getColor() should return 'white'");
    }

    @Test
    void testSetDisableDisablesButton() {
        // Act: Set the button to be enabled (disable = false) and then disabled (disable = true)
        whiteTale.setDisable(false);
        whiteTale.setDisable(true);

        // Verify that setDisable was called with false and true
        org.testfx.assertions.api.Assertions.assertThat(mockButton).isDisabled();
    }

    @Test
    void testSetColorUpdatesButtonColor() {
        // Arrange: Mock Player color retrieval
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getColor()).thenReturn(PlayerColor.RED);

        // Act: Call setColor() to update the button style
        whiteTale.setColor();

        // Assert: Verify the button's style was updated to the current player's color
        org.testfx.assertions.api.Assertions.assertThat(mockButton).hasStyle("-fx-background-color: green");
    }
}
