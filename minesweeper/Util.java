package com.javarush.games.minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public enum Filter {CLOSED, DANGEROUS, MINED, NONE, NUMERABLE, OPEN, SAFE, SUSPECTED}

    public static List<Cell> filterCells(List<Cell> list, Filter filter) {
        List<Cell> result = new ArrayList<>();
        list.forEach(cell -> {
            switch (filter) {
                case CLOSED:
                    if (!cell.isOpen) result.add(cell);
                    break;
                case OPEN:
                    if (cell.isOpen) result.add(cell);
                    break;
                case MINED:
                    if (cell.isMined) result.add(cell);
                    break;
                case DANGEROUS:
                    if (cell.isDangerous()) result.add(cell);
                    break;
                case NUMERABLE:
                    if (cell.isNumerable()) result.add(cell);
                    break;
                case SUSPECTED:
                    if (cell.isSuspected()) result.add(cell);
                    break;
                case SAFE:
                    if (cell.isSafe()) result.add(cell);
                    break;
                case NONE:
                default:
                    result.add(cell);
                    break;
            }
        });
        return result;
    }

    public static int getDifficultyIndex(int difficultyIndex) {
        return (difficultyIndex / 5 - 1);
    }
}
