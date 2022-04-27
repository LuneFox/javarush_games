package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field extends InteractiveObject {
    private final Cell[][] field = new Cell[10][10];

    public void createNewLayout() {
        createEmptyField();
        plantMines();
        applyNumbersToCellsNearMines();
    }

    private void createEmptyField() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Cell(x, y);
            }
        }
    }

    private void plantMines() {
        double numberOfMinesToPlant = Options.difficulty / 1.5;
        while (countAllCells(CellFilter.MINED) < numberOfMinesToPlant) {
            int x = game.getRandomNumber(10);
            int y = game.getRandomNumber(10);
            if (!field[y][x].isMined()) {
                field[y][x] = new Cell(x, y);
                field[y][x].setMined(true);
            }
        }
    }

    void applyNumbersToCellsNearMines() {
        getAllCells(CellFilter.NUMERABLE).forEach(cell -> {
            cell.setCountMinedNeighbors(getNeighborCells(cell, CellFilter.MINED, false).size());
        });
    }

    @Override
    public void draw() {
        getAllCells().forEach(Cell::draw);
    }

    public List<Cell> getAllCells() {
        return getAllCells(CellFilter.NONE);
    }

    public List<Cell> getAllCells(CellFilter filter) {
        List<Cell> list = new ArrayList<>();
        for (Cell[] cells : field) {
            list.addAll(Arrays.asList(cells));
        }
        return CellFilter.filter(list, filter);
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
                if (field[y][x] == cell && !includeSelf) continue;
                neighbors.add(field[y][x]);
            }
        }
        return CellFilter.filter(neighbors, filter);
    }

    public void revealMines() {
        getAllCells().forEach(cell -> {
            if (cell.isMined()) cell.open();
        });
    }

    public Cell[][] getField() {
        return field;
    }

    public Cell getCell(int x, int y) {
        return field[y][x];
    }
}
