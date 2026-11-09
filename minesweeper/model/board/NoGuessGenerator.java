package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.board.field.Field;
import com.javarush.games.minesweeper.model.board.field.FieldDao;
import com.javarush.games.minesweeper.model.board.field.MineFieldValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Генерирует поля без необходимости угадывать для сложности не выше средней.
 * Проверяет раскладку логическим решателем и отбрасывает тупиковые варианты.
 */
public class NoGuessGenerator {
    /** Максимальная сложность, для которой включена гарантия. */
    public static final int MAX_DIFFICULTY = 25;
    /** Максимальное число попыток подобрать раскладку. */
    private static final int MAX_ATTEMPTS = 500;
    /** Ссылка на игру. */
    private final MinesweeperGame game;
    /** Объект доступа к клеткам поля. */
    private final FieldDao fieldDao;

    /** Создаёт генератор для текущего поля. */
    public NoGuessGenerator(MinesweeperGame game, FieldDao fieldDao) {
        this.game = game;
        this.fieldDao = fieldDao;
    }

    /** Сообщает, включена ли гарантия для выбранной сложности. */
    public static boolean isEnabled() {
        return Options.getDifficulty() <= MAX_DIFFICULTY;
    }

    /** Пытается создать решаемое поле вокруг первого клика. */
    public boolean generate(int firstX, int firstY, int mineCount) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            boolean[] mines = createMines(firstX, firstY, mineCount);
            if (MineFieldValidator.hasBuriedMine(mines)) continue;
            if (isSolvable(mines, firstX, firstY, mineCount)) {
                apply(mines);
                return true;
            }
        }
        return false;
    }

    /** Создаёт случайную раскладку мин, не задевая область первого клика. */
    private boolean[] createMines(int firstX, int firstY, int mineCount) {
        boolean[] mines = new boolean[Field.SIZE * Field.SIZE];
        int planted = 0;

        while (planted < mineCount) {
            int x = game.getRandomNumber(Field.SIZE);
            int y = game.getRandomNumber(Field.SIZE);
            if (Math.abs(x - firstX) <= 1 && Math.abs(y - firstY) <= 1) continue;

            int index = index(x, y);
            if (!mines[index]) {
                mines[index] = true;
                planted++;
            }
        }

        return mines;
    }

    /** Переносит раскладку на текущее поле. */
    private void apply(boolean[] mines) {
        fieldDao.createNewField();
        for (Cell cell : fieldDao.getAllCells()) {
            cell.setMined(mines[index(cell.x, cell.y)]);
        }
    }

    /** Проверяет, решается ли раскладка логическими правилами. */
    private boolean isSolvable(boolean[] mines, int firstX, int firstY, int mineCount) {
        int[] counts = countMines(mines);
        boolean[] opened = new boolean[Field.SIZE * Field.SIZE];
        boolean[] flagged = new boolean[Field.SIZE * Field.SIZE];
        reveal(index(firstX, firstY), mines, counts, opened);

        while (true) {
            boolean changed = applyBasicRules(mines, counts, opened, flagged);
            if (!changed) changed = applySubsetRule(mines, counts, opened, flagged);
            if (!changed) changed = applyGlobalRule(mines, counts, opened, flagged, mineCount);
            if (!changed) break;
        }

        return isSolved(mines, opened);
    }

    /** Считает число мин вокруг каждой клетки. */
    private int[] countMines(boolean[] mines) {
        int[] counts = new int[Field.SIZE * Field.SIZE];

        for (int y = 0; y < Field.SIZE; y++) {
            for (int x = 0; x < Field.SIZE; x++) {
                int count = 0;
                for (int neighbor : neighbors(index(x, y))) {
                    if (mines[neighbor]) count++;
                }
                counts[index(x, y)] = count;
            }
        }

        return counts;
    }

    /** Открывает клетку и каскад пустых клеток. */
    private void reveal(int index, boolean[] mines, int[] counts, boolean[] opened) {
        if (opened[index] || mines[index]) return;
        opened[index] = true;
        if (counts[index] != 0) return;

        for (int neighbor : neighbors(index)) {
            reveal(neighbor, mines, counts, opened);
        }
    }

    /** Применяет правила «все соседи — мины» и «все соседи безопасны». */
    private boolean applyBasicRules(boolean[] mines, int[] counts, boolean[] opened, boolean[] flagged) {
        for (int index = 0; index < opened.length; index++) {
            if (!opened[index] || mines[index]) continue;

            List<Integer> unknown = unknownNeighbors(index, opened, flagged);
            if (unknown.isEmpty()) continue;

            int remaining = counts[index] - countFlags(index, flagged);
            if (remaining == 0) {
                openAll(unknown, mines, counts, opened);
                return true;
            }
            if (remaining == unknown.size()) {
                flagAll(unknown, flagged);
                return true;
            }
        }

        return false;
    }

    /** Применяет правило разности пересекающихся ограничений. */
    private boolean applySubsetRule(boolean[] mines, int[] counts, boolean[] opened, boolean[] flagged) {
        List<Constraint> constraints = collectConstraints(mines, counts, opened, flagged);

        for (int i = 0; i < constraints.size(); i++) {
            Constraint small = constraints.get(i);
            for (int j = 0; j < constraints.size(); j++) {
                if (i == j) continue;
                Constraint big = constraints.get(j);
                if (small.cells.size() >= big.cells.size()) continue;
                if (!big.cells.containsAll(small.cells)) continue;

                List<Integer> difference = new ArrayList<>(big.cells);
                difference.removeAll(small.cells);
                int differenceMines = big.mines - small.mines;

                if (differenceMines == 0) {
                    openAll(difference, mines, counts, opened);
                    return true;
                }
                if (differenceMines == difference.size()) {
                    flagAll(difference, flagged);
                    return true;
                }
            }
        }

        return false;
    }

    /** Применяет общий лимит мин на поле. */
    private boolean applyGlobalRule(boolean[] mines, int[] counts, boolean[] opened, boolean[] flagged, int mineCount) {
        int flags = 0;
        List<Integer> unknown = new ArrayList<>();

        for (int index = 0; index < opened.length; index++) {
            if (flagged[index]) flags++;
            if (!opened[index] && !flagged[index]) unknown.add(index);
        }

        if (unknown.isEmpty()) return false;

        int remainingMines = mineCount - flags;
        if (remainingMines == 0) {
            openAll(unknown, mines, counts, opened);
            return true;
        }
        if (remainingMines == unknown.size()) {
            flagAll(unknown, flagged);
            return true;
        }

        return false;
    }

    /** Собирает ограничения по открытым числам. */
    private List<Constraint> collectConstraints(boolean[] mines, int[] counts, boolean[] opened, boolean[] flagged) {
        List<Constraint> constraints = new ArrayList<>();

        for (int index = 0; index < opened.length; index++) {
            if (!opened[index] || mines[index]) continue;
            List<Integer> unknown = unknownNeighbors(index, opened, flagged);
            if (unknown.isEmpty()) continue;
            constraints.add(new Constraint(unknown, counts[index] - countFlags(index, flagged)));
        }

        return constraints;
    }

    /** Открывает перечисленные клетки. */
    private void openAll(List<Integer> cells, boolean[] mines, int[] counts, boolean[] opened) {
        for (int cell : cells) {
            reveal(cell, mines, counts, opened);
        }
    }

    /** Помечает перечисленные клетки минами. */
    private void flagAll(List<Integer> cells, boolean[] flagged) {
        for (int cell : cells) {
            flagged[cell] = true;
        }
    }

    /** Возвращает неизвестных соседей клетки. */
    private List<Integer> unknownNeighbors(int index, boolean[] opened, boolean[] flagged) {
        List<Integer> result = new ArrayList<>(8);
        for (int neighbor : neighbors(index)) {
            if (!opened[neighbor] && !flagged[neighbor]) result.add(neighbor);
        }
        return result;
    }

    /** Считает отмеченные мины вокруг клетки. */
    private int countFlags(int index, boolean[] flagged) {
        int count = 0;
        for (int neighbor : neighbors(index)) {
            if (flagged[neighbor]) count++;
        }
        return count;
    }

    /** Сообщает, открыты ли все безопасные клетки. */
    private boolean isSolved(boolean[] mines, boolean[] opened) {
        for (int index = 0; index < opened.length; index++) {
            if (!mines[index] && !opened[index]) return false;
        }
        return true;
    }

    /** Возвращает индексы соседей клетки. */
    private List<Integer> neighbors(int index) {
        int x = index % Field.SIZE;
        int y = index / Field.SIZE;
        List<Integer> result = new ArrayList<>(8);

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int neighborX = x + dx;
                int neighborY = y + dy;
                if (neighborX < 0 || neighborX >= Field.SIZE) continue;
                if (neighborY < 0 || neighborY >= Field.SIZE) continue;
                result.add(index(neighborX, neighborY));
            }
        }

        return result;
    }

    /** Преобразует координаты в индекс массива. */
    private int index(int x, int y) {
        return y * Field.SIZE + x;
    }

    /** Ограничение: среди клеток ровно mines мин. */
    private static class Constraint {
        /** Неизвестные клетки ограничения. */
        private final List<Integer> cells;
        /** Число мин среди них. */
        private final int mines;

        /** Создаёт ограничение. */
        private Constraint(List<Integer> cells, int mines) {
            this.cells = cells;
            this.mines = mines;
        }
    }
}
