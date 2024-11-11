package com.example.reversi.moves;

import com.example.reversi.Player;
import com.example.reversi.talesFactory.Tales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PossibleMovesTest {

    private PossibleMoves possibleMoves;
    private Tales[][] tales;
    private int grid;

    @BeforeEach
    void setUp() {
        possibleMoves = new PossibleMoves();
        grid = 7;  // Размер сетки 8x8 (индекс от 0 до 7)
        tales = new Tales[8][8];

        // Создаем моки для каждой клетки на доске
        for (int i = 0; i < tales.length; i++) {
            for (int j = 0; j < tales[i].length; j++) {
                tales[i][j] = mock(Tales.class);
            }
        }
    }

    @Test
    void testCheckDirections_NoMovePossible() {
        // Мокируем Player с использованием MockedStatic
        try (MockedStatic<Player> playerMock = mockStatic(Player.class)) {
            Player mockPlayer = mock(Player.class);
            playerMock.when(Player::getPlayer).thenReturn(mockPlayer);
            when(mockPlayer.getColor()).thenReturn("green");
            when(mockPlayer.getEnemyColor()).thenReturn("red");

            when(tales[1][1].getColor()).thenReturn("green");
            when(tales[1][2].getColor()).thenReturn("red");

            List<List<Integer>> result = possibleMoves.checkDirections(1, 4, tales, grid);
            assertNull(result, "Должен быть null, так как нет доступных ходов");
        }
    }

    @Test
    void testCanMove_MovePossible() {
        try (MockedStatic<Player> playerMock = mockStatic(Player.class)) {
            Player mockPlayer = mock(Player.class);
            playerMock.when(Player::getPlayer).thenReturn(mockPlayer);
            when(mockPlayer.getColor()).thenReturn("green");
            when(mockPlayer.getEnemyColor()).thenReturn("red");

            when(tales[3][3].getColor()).thenReturn("red");
            when(tales[3][4].getColor()).thenReturn("green");

            List<List<Integer>> result = possibleMoves.canMove(3, 4, 0, 1, tales, grid);
            assertNotNull(result, "Ожидался доступный ход");
            assertEquals(List.of(1, 1), result.get(0), "Ход должен быть возможен в данной позиции");
        }
    }
}
