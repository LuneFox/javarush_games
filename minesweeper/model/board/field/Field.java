package com.javarush.games.minesweeper.model.board.field;

public class Field {
    final Cell[][] grid;

    public Field() {
        grid = new Cell[10][10];
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                grid[y][x] = new Cell(x, y);
            }
        }
    }
}
