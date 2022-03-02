package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.view.graphics.Drawable;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains the game field itself and global actions that operate it.
 */

public class Field implements Drawable {
    private static final MinesweeperGame game = MinesweeperGame.getInstance();
    private final Cell[][] field = new Cell[10][10];

    public void create() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Cell(VisualElement.CELL_CLOSED, x, y, false);
                field[y][x].setSprite(VisualElement.NONE);
            }
        }
    }

    public void plantMines() {
        while (countAllCells(Cell.Filter.MINED) < game.difficulty / 1.5) { // fixed number of mines on field
            int x = game.getRandomNumber(10);
            int y = game.getRandomNumber(10);
            if (!field[y][x].isMined && !field[y][x].isOpen) {
                field[y][x] = new Cell(VisualElement.CELL_CLOSED, x, y, true);
                field[y][x].setSprite(VisualElement.SPR_BOARD_MINE);
            }
        }
    }

    public List<Cell> getAllCells(Cell.Filter filter) {
        List<Cell> all = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            all.addAll(Arrays.asList(field[y]));
        }
        return Cell.filterCells(all, filter);
    }

    public int countAllCells(Cell.Filter filter) {
        return getAllCells(filter).size();
    }

    public List<Cell> getNeighborCells(Cell cell, Cell.Filter filter, boolean includeSelf) {
        List<Cell> neighbors = new ArrayList<>();
        for (int y = cell.y - 1; y <= cell.y + 1; y++) {
            for (int x = cell.x - 1; x <= cell.x + 1; x++) {
                if (y < 0 || y >= 10) continue;
                if (x < 0 || x >= 10) continue;
                if (field[y][x] == cell && !includeSelf) continue;  // skip center if not included
                neighbors.add(field[y][x]);
            }
        }
        return Cell.filterCells(neighbors, filter);
    }

    public void enumerate() {
        getAllCells(Cell.Filter.NUMERABLE).forEach(cell -> {
            cell.countMinedNeighbors = getNeighborCells(cell, Cell.Filter.MINED, false).size();
            cell.setSprite(cell.countMinedNeighbors);
        });
    }

    public void revealMines() {
        getAllCells(Cell.Filter.NONE).forEach(cell -> {
            if (cell.isMined) cell.isOpen = true;
        });
    }

    @Override
    public void draw() {
        getAllCells(Cell.Filter.NONE).forEach(Cell::draw);
    }

    public Cell[][] get() {
        return field;
    }
}
