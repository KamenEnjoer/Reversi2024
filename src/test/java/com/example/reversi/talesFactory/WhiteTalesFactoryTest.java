package com.example.reversi.talesFactory;

import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhiteTalesFactoryTest extends ApplicationTest {
    private WhiteTalesFactory whiteTalesFactory;
    private Button mockButton;

    @BeforeEach
    void setUp() {
        // Initialize the WhiteTalesFactory and mock the Button
        whiteTalesFactory = new WhiteTalesFactory();
        mockButton = new Button();
    }

    @Test
    void testNewTaleCreatesWhiteTale() {
        // Given
        int c = 0; // Example column index
        int r = 0; // Example row index

        // When
        Tales tale = whiteTalesFactory.newTale(mockButton);

        // Then
        assertNotNull(tale, "Tale should not be null");
        assertTrue(tale instanceof WhiteTale, "The created tale should be an instance of WhiteTale");

        // Verify the interaction with the Button mock (if needed)
        org.testfx.assertions.api.Assertions.assertThat(mockButton).hasStyle("-fx-background-color: white");
        org.testfx.assertions.api.Assertions.assertThat(mockButton).isDisabled();
    }
}