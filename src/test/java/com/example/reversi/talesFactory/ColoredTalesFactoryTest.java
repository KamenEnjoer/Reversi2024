package com.example.reversi.talesFactory;

import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ColoredTalesFactoryTest extends ApplicationTest {

    private ColoredTalesFactory talesFactory;
    private Button button;

    @BeforeEach
    void setUp() {
        // Initialize the factory with the initial color "red" and create a button
        talesFactory = new ColoredTalesFactory("red");
        button = new Button();
    }

    @Test
    void testNewTaleWithInitialColor() {
        // Create a new tale with the initial color "red"
        Tales tale = talesFactory.newTale(button, 0, 0);

        // Check that the created tale is an instance of ColoredTale
        assertTrue(tale instanceof ColoredTale, "The created tale should be an instance of ColoredTale");

        // Verify that the color is set correctly
        assertEquals("red", tale.getColor(), "The color should be 'red' initially");
    }

    @Test
    void testSetColorAndCreateNewTale() {
        // Change the factory's color to "blue"
        talesFactory.setColor("blue");

        // Create a new tale with the updated color "blue"
        Tales tale = talesFactory.newTale(button, 1, 1);

        // Check that the created tale is an instance of ColoredTale
        assertTrue(tale instanceof ColoredTale, "The created tale should be an instance of ColoredTale");

        // Verify that the color is set correctly
        assertEquals("blue", tale.getColor(), "The color should be updated to 'blue'");
    }
}
