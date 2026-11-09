package com.javarush.games.minesweeper.model.board.field;

/** Проверяет, есть ли на поле мина, окружённая только другими минами. */
public final class MineFieldValidator {
    /** Утилитарный класс. */
    private MineFieldValidator() {
    }

    /** Сообщает, есть ли мина без безопасных соседей. */
    public static boolean hasBuriedMine(FieldDao fieldDao) {
        for (Cell cell : fieldDao.getAllCells()) {
            if (!cell.isMined()) continue;
            if (fieldDao.countNeighborCells(cell, neighbor -> !neighbor.isMined()) == 0) return true;
        }
        return false;
    }

    /** Сообщает, есть ли мина без безопасных соседей в раскладке. */
    public static boolean hasBuriedMine(boolean[] mines) {
        for (int index = 0; index < mines.length; index++) {
            if (!mines[index]) continue;
            if (hasOnlyMinedNeighbors(index, mines)) return true;
        }
        return false;
    }

    /** Проверяет соседей клетки раскладки. */
    private static boolean hasOnlyMinedNeighbors(int index, boolean[] mines) {
        int x = index % Field.SIZE;
        int y = index / Field.SIZE;

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int neighborX = x + dx;
                int neighborY = y + dy;
                if (neighborX < 0 || neighborX >= Field.SIZE) continue;
                if (neighborY < 0 || neighborY >= Field.SIZE) continue;
                if (!mines[neighborY * Field.SIZE + neighborX]) return false;
            }
        }

        return true;
    }
}
