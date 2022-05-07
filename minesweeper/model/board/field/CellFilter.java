package com.javarush.games.minesweeper.model.board.field;

import java.util.ArrayList;
import java.util.List;

public enum CellFilter {
    CLOSED,
    DANGEROUS,
    DESTROYED,
    EMPTY,
    FLAGGED,
    MINED,
    NONE,
    NUMERABLE,
    OPEN,
    SAFE,
    SCORED,
    SUSPECTED;

    public static List<Cell> filter(List<Cell> list, CellFilter filter) {
        List<Cell> result = new ArrayList<>();
        list.forEach(cell -> {
            switch (filter) {
                case CLOSED:
                    if (!cell.isOpen()) result.add(cell);
                    break;
                case DANGEROUS:
                    if (!cell.isOpen() & cell.isMined()) result.add(cell);
                    break;
                case DESTROYED:
                    if (cell.isDestroyed()) result.add(cell);
                    break;
                case EMPTY:
                    if (cell.isEmpty()) result.add(cell);
                    break;
                case FLAGGED:
                    if (cell.isFlagged()) result.add(cell);
                    break;
                case MINED:
                    if (cell.isMined()) result.add(cell);
                    break;
                case NUMERABLE:
                    if (cell.isNumerable()) result.add(cell);
                    break;
                case OPEN:
                    if (cell.isOpen()) result.add(cell);
                    break;
                case SAFE:
                    if (!cell.isOpen() && !cell.isMined()) result.add(cell);
                    break;
                case SCORED:
                    if (cell.isOpen() && !cell.isMined() && !cell.isDestroyed()) result.add(cell);
                    break;
                case SUSPECTED:
                    if (cell.isFlagged() || (cell.isOpen() && cell.isMined())) result.add(cell);
                    break;
                case NONE:
                default:
                    result.add(cell);
                    break;
            }
        });
        return result;
    }
}
