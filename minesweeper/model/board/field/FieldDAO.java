package com.javarush.games.minesweeper.model.board.field;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public List<Cell> getAllCells(Predicate<Cell> predicate) {
        return getAllCells().stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Cell> getAllCells() {
        return Arrays.stream(field.grid).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    public List<Cell> getNeighborCells(Cell centerCell, Predicate<Cell> predicate) {
        return getNeighborCells(centerCell).stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Cell> getNeighborCells(Cell centerCell) {
        return getCellsIn3x3area(centerCell, cell -> !cell.equals(centerCell));
    }

    public List<Cell> getCellsIn3x3area(Cell centerCell, Predicate<Cell> predicate) {
        return getCellsIn3x3area(centerCell).stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Cell> getCellsIn3x3area(Cell centerCell) {
        return getAllCells().stream()
                .filter(cell -> Math.abs(cell.y - centerCell.y) <= 1)
                .filter(cell -> Math.abs(cell.x - centerCell.x) <= 1)
                .collect(Collectors.toList());
    }
}
