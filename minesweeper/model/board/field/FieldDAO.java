package com.javarush.games.minesweeper.model.board.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldDAO {
    private Field field;

    public FieldDAO() {
        createNewField();
    }

    public void createNewField() {
        field = new Field();
    }

    public Cell getCell(int x, int y) {
        return field.grid[y][x];
    }

    public Cell getCellByCoordinates(int x, int y) {
        return field.grid[y / 10][x / 10];
    }

    public int countAllCells(CellFilter filter) {
        return getAllCells(filter).size();
    }

    public List<Cell> getAllCells() {
        return getAllCells(CellFilter.NONE);
    }

    public List<Cell> getAllCells(CellFilter filter) {
        List<Cell> list = new ArrayList<>();
        for (Cell[] cells : field.grid) {
            list.addAll(Arrays.asList(cells));
        }
        return CellFilter.filter(list, filter);
    }

    public List<Cell> getNeighborCells(Cell cell, CellFilter filter) {
        List<Cell> result = getCellsIn3x3area(cell, filter);
        result.remove(cell);
        return result;
    }

    public List<Cell> getCellsIn3x3area(Cell centerCell, CellFilter filter) {
        List<Cell> neighbors = new ArrayList<>();
        for (int y = centerCell.y - 1; y <= centerCell.y + 1; y++) {
            for (int x = centerCell.x - 1; x <= centerCell.x + 1; x++) {
                if (y < 0 || y >= 10) continue;
                if (x < 0 || x >= 10) continue;
                neighbors.add(field.grid[y][x]);
            }
        }
        return CellFilter.filter(neighbors, filter);
    }
}
