package com.javarush.games.minesweeper.model.board.field;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/** Доступ к клеткам поля и их соседям. */
public class FieldDao {
    /** Текущее поле. */
    private Field field;

    /** Создаёт экземпляр FieldDao. */
    public FieldDao() {
        createNewField();
    }

    /** Создаёт новое поле. */
    public void createNewField() {
        field = new Field();
    }

    /** Возвращает клетку по координатам. */
    public Cell getCell(int x, int y) {
        return field.getCell(x, y);
    }

    /** Возвращает клетку по экранным координатам. */
    public Cell getCellByCoordinates(int x, int y) {
        return field.getCell(x / Cell.PIXEL_SIZE, y / Cell.PIXEL_SIZE);
    }

    /** Возвращает все клетки поля. */
    public List<Cell> getAllCells(Predicate<Cell> predicate) {
        List<Cell> result = new ArrayList<>();
        for (Cell cell : getAllCells()) {
            if (predicate.test(cell)) result.add(cell);
        }
        return result;
    }

    /** Возвращает все клетки поля. */
    public List<Cell> getAllCells() {
        return field.getAllCells();
    }

    /** Считает клетки по условию. */
    public int count(Predicate<Cell> predicate) {
        int count = 0;
        for (Cell cell : getAllCells()) {
            if (predicate.test(cell)) count++;
        }
        return count;
    }

    /** Возвращает соседние клетки. */
    public List<Cell> getNeighborCells(Cell centerCell, Predicate<Cell> predicate) {
        return collectArea(centerCell, predicate, false);
    }

    /** Считает соседние клетки по условию. */
    public int countNeighborCells(Cell centerCell, Predicate<Cell> predicate) {
        int count = 0;
        int minX = Math.max(0, centerCell.x - 1);
        int maxX = Math.min(Field.SIZE - 1, centerCell.x + 1);
        int minY = Math.max(0, centerCell.y - 1);
        int maxY = Math.min(Field.SIZE - 1, centerCell.y + 1);

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (x == centerCell.x && y == centerCell.y) continue;
                if (predicate.test(field.getCell(x, y))) count++;
            }
        }
        return count;
    }

    /** Возвращает клетки области 3×3. */
    public List<Cell> getCellsIn3x3area(Cell centerCell, Predicate<Cell> predicate) {
        return collectArea(centerCell, predicate, true);
    }

    /** Собирает клетки области. */
    private List<Cell> collectArea(Cell centerCell, Predicate<Cell> predicate, boolean includeCenter) {
        List<Cell> result = new ArrayList<>(9);
        int minX = Math.max(0, centerCell.x - 1);
        int maxX = Math.min(Field.SIZE - 1, centerCell.x + 1);
        int minY = Math.max(0, centerCell.y - 1);
        int maxY = Math.min(Field.SIZE - 1, centerCell.y + 1);

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (!includeCenter && x == centerCell.x && y == centerCell.y) continue;
                Cell cell = field.getCell(x, y);
                if (predicate.test(cell)) result.add(cell);
            }
        }
        return result;
    }
}
