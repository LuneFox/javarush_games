package com.javarush.games.minesweeper.model.board;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains the game field itself and global actions that operate it.
 */

public class Field extends InteractiveObject {
    private final Cell[][] field = new Cell[10][10];

    public void createNewLayout() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Cell(ImageType.CELL_CLOSED, x, y, false);
                field[y][x].setSprite(ImageType.NONE);
            }
        }
        plantMines();
        setNumbers();
    }

    private void plantMines() {
        while (countAllCells(CellFilter.MINED) < Options.difficulty / 1.5) { // fixed number of mines on field
            int x = game.getRandomNumber(10);
            int y = game.getRandomNumber(10);
            if (!field[y][x].isMined() && !field[y][x].isOpen()) {
                field[y][x] = new Cell(ImageType.CELL_CLOSED, x, y, true);
                field[y][x].setSprite(ImageType.BOARD_MINE);
            }
        }
    }

    void setNumbers() {
        getAllCells(CellFilter.NUMERABLE).forEach(cell -> {
            cell.setCountMinedNeighbors(getNeighborCells(cell, CellFilter.MINED, false).size());
            cell.setSprite(cell.getCountMinedNeighbors());
        });
    }

    public List<Cell> getAllCells(CellFilter filter) {
        return Cell.filterCells(Arrays.stream(field).flatMap(Arrays::stream).collect(Collectors.toList()), filter);
    }

    public int countAllCells(CellFilter filter) {
        return getAllCells(filter).size();
    }

    public List<Cell> getNeighborCells(Cell cell, CellFilter filter, boolean includeSelf) {
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

    public void revealMines() {
        getAllCells(CellFilter.NONE).forEach(cell -> {
            if (cell.isMined()) {
                cell.open();
                if (cell.isFlagged() && !cell.isDestroyed()) {
                    cell.setSprite(ImageType.BOARD_MINE);
                    cell.setBackgroundColor(Color.GREEN);
                }
            }
        });
    }

    @Override
    public void draw() {
        getAllCells(CellFilter.NONE).forEach(Cell::draw);
    }

    public Cell[][] get() {
        return field;
    }

    public Cell getCell(int x, int y) {
        return field[y][x];
    }
}
