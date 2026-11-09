package com.javarush.games.minesweeper.model.board.field;

import java.util.ArrayList;
import java.util.List;

/** Сетка 10×10 и кэш всех клеток. */
public class Field {
    /**
     * Количество клеток по каждой стороне поля.
     */
    public static final int SIZE = 10;

    /** Сетка клеток поля. */
    private final Cell[][] grid;
    /** Кэш всех клеток поля. */
    private final List<Cell> allCells;

    /** Создаёт экземпляр Field. */
    public Field() {
        grid = new Cell[SIZE][SIZE];
        allCells = new ArrayList<>(SIZE * SIZE);
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Cell cell = new Cell(x, y);
                grid[y][x] = cell;
                allCells.add(cell);
            }
        }
    }

    /** Возвращает клетку по координатам. */
    Cell getCell(int x, int y) {
        return grid[y][x];
    }

    /** Возвращает все клетки поля. */
    List<Cell> getAllCells() {
        return allCells;
    }
}
