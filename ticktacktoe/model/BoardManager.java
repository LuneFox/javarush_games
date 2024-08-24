package com.javarush.games.ticktacktoe.model;

import com.javarush.games.ticktacktoe.TicTacToeGame;
import com.javarush.games.ticktacktoe.model.gameobjects.*;
import com.javarush.games.ticktacktoe.model.gameobjects.field.Field;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Менеджер для управления игровыми элементами на поле
 * @author LuneFox
 */
public class BoardManager {

    /** Экземпляр игры */
    private final TicTacToeGame game;
    /** Игровое поле, которым управляет менеджер */
    private final Field field;
    /** Диски, которые лежат на игровом поле */
    private final Disk[][] disks;

    public BoardManager(TicTacToeGame game, Field field) {
        this.game = game;
        this.field = field;
        this.disks = field.getDisks();
    }

    /**
     * Подготовить поле для новой игры
     */
    public void setupNewGame() {
        putStartingDisks();
        markLegalMoves();
    }

    /**
     * Положить четыре диска в центр доски:
     * Белый, Чёрный
     * Чёрный, Белый
     */
    private void putStartingDisks() {
        putStartingDisk(3, 3, Side.WHITE);
        putStartingDisk(4, 4, Side.WHITE);
        putStartingDisk(3, 4, Side.BLACK);
        putStartingDisk(4, 3, Side.BLACK);
    }

    /**
     * Выложить новый диск
     *
     * @param x    координата х на поле
     * @param y    координата y на поле
     * @param side какой стороной вверх лежит диск
     */
    private void putStartingDisk(int x, int y, Side side) {
        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, side);
    }

    /**
     * Выполнить ход игрока.
     * Ничего не делает, если ход неверный, или если сейчас ход компьютера.
     *
     * @param x координата x диска игрока
     * @param y координата y диска игрока
     */
    public void doManualTurn(int x, int y) {
        if (!field.moveIsLegal(x, y)) return;
        if (game.isComputerTurn()) return;

        makeTurn(x, y);
    }

    /**
     * Выполнить ход компьютера.
     * Ничего не делает, если невозможно совершить ход.
     * Совершает оптимальный ход, если такой возможен.
     */
    public void doAutomaticTurn() {
        if (field.noLegalMoves()) return;

        final Move move = getOptimalMove();
        makeTurn(move.getX(), move.getY());
    }

    /**
     * Получить оптимальный ход.
     * В текущем исполнении в первую очередь выбирает угловые клетки, если такие доступны.
     * Выбирает ход случайно из доступных вариантов.
     *
     * @return координаты хода
     */
    private Move getOptimalMove() {
        final ArrayList<LegalMoveMark> bestMoveMarks = getBestMoveMarks();
        final ArrayList<LegalMoveMark> legalMoveMarks = field.getLegalMoveMarks();
        LegalMoveMark optimalMove =
                bestMoveMarks.isEmpty() ?
                        getRandomMark(legalMoveMarks) :
                        getRandomMark(bestMoveMarks);
        return new Move(optimalMove.getBoardX(), optimalMove.getBoardY());
    }

    /**
     * Выбрать угловые метки среди всех меток (найти лучшие ходы)
     *
     * @return список угловых меток
     */
    private ArrayList<LegalMoveMark> getBestMoveMarks() {
        return field.getLegalMoveMarks().stream()
                .filter(legalMoveMark -> (
                        legalMoveMark.getBoardX() == 0 && legalMoveMark.getBoardY() == 0)
                        || (legalMoveMark.getBoardX() == 0 && legalMoveMark.getBoardY() == 7)
                        || (legalMoveMark.getBoardX() == 7 && legalMoveMark.getBoardY() == 0)
                        || (legalMoveMark.getBoardX() == 7 && legalMoveMark.getBoardY() == 7))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Выбрать случайную метку хода из списка.
     *
     * @param marks список меток
     * @return случайная метка
     */
    public LegalMoveMark getRandomMark(ArrayList<LegalMoveMark> marks) {
        int availableMovesNumber = marks.size();
        int randomMove = (int) (Math.random() * availableMovesNumber);
        return marks.get(randomMove);
    }

    /**
     * Процесс выполнения хода любого игрока
     */
    private void makeTurn(int x, int y) {
        game.start();
        putDisk(x, y);
        flipEnemyDisks(x, y);
        game.changePlayer();
        field.placeLastMoveMark(x, y);
        field.checkAvailableMoves();
    }

    /**
     * Поместить диск на игровое поле
     */
    private void putDisk(int x, int y) {
        if (disks[y][x] != null) return;

        Side diskSide = game.getCurrentPlayer();
        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, diskSide);
    }

    /**
     * Перевернуть диски соперника во всех направлениях
     *
     * @param diskX координата X диска, относительного которого переворачиваются остальные
     * @param diskY координата Y диска, относительного которого переворачиваются остальные
     */
    private void flipEnemyDisks(int diskX, int diskY) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) continue;
                flipInDirection(diskX, diskY, x, y);
            }
        }
    }

    /**
     * Перевернуть диски в одном из восьми направлений
     *
     * @param diskX координата X диска, относительного которого переворачиваются остальные
     * @param diskY координата Y диска, относительного которого переворачиваются остальные
     * @param dirX  направление по Х, в котором проверяется линия (-1 влево, 1 вправо)
     * @param dirY  направление по Y, в котором проверяется линия (-1 вверх, 1 вниз)
     */
    private void flipInDirection(int diskX, int diskY, int dirX, int dirY) {
        LinkedList<Disk> line = getLineToFlip(diskX, diskY, dirX, dirY);

        if (!line.isEmpty()) {
            line.forEach(Disk::flip);
        }
    }

    /**
     * Очистить старые метки, расставить новые метки возможных ходов и нарисовать их
     */
    public void markLegalMoves() {
        ArrayList<LegalMoveMark> marks = field.getLegalMoveMarks();
        marks.clear();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (disks[y][x] != null) continue;
                if (checkIfMoveIsLegal(x, y)) {
                    marks.add(new LegalMoveMark(x * 10 + 15, y * 10 + 15));
                }
            }
        }

        marks.forEach(mark -> {
            mark.matchPlayerColor();
            mark.draw();
        });
    }

    /**
     * Проверить, возможен ли ход по заданным координатам поля
     * @return ход возможен
     */
    private boolean checkIfMoveIsLegal(int posX, int posY) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }

                if (checkInDirection(posX, posY, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверить, возможен ли ход в заданной координате поля для определённого направления
     * @param posX координата X потенциального размещения диска
     * @param posY координата Y потенциального размещения диска
     * @param dirX направление по Х, в котором проверяется линия (-1 влево, 1 вправо)
     * @param dirY направление по Y, в котором проверяется линия (-1 вверх, 1 вниз)
     * @return ход возможен (ряд дисков для переворота не пуст)
     */
    private boolean checkInDirection(int posX, int posY, int dirX, int dirY) {
        LinkedList<Disk> line = getLineToFlip(posX, posY, dirX, dirY);
        return !line.isEmpty();
    }

    /**
     * Получить ряд дисков, которые можно перевернуть в заданном направлении.
     * Добавляет чужие диски по одному до тех пор, пока не упрётся в свой диск или в край доски.
     * Если упрётся в свой диск, возвращает собранный ряд. В ином случае возвращает пустой список.
     *
     * @param diskX координата X проверяемой позиции диска
     * @param diskY координата Y проверяемой позиции диска
     * @param dirX  направление по Х, в котором проверяется ряд (-1 влево, 1 вправо)
     * @param dirY  направление по Y, в котором проверяется ряд (-1 вверх, 1 вниз)
     * @return список дисков, которые можно перевернуть
     */
    private LinkedList<Disk> getLineToFlip(int diskX, int diskY, int dirX, int dirY) {
        LinkedList<Disk> lineToFlip = new LinkedList<>();

        diskX += dirX;
        diskY += dirY;

        while (!field.isOutOfBoard(diskX, diskY)) {
            Disk disk = disks[diskY][diskX];

            if (disk == null) {
                break;
            }

            if (disk.getSide() == game.getCurrentPlayer()) {
                return lineToFlip;
            }

            lineToFlip.add(disk);
            diskX += dirX;
            diskY += dirY;
        }

        lineToFlip.clear();
        return lineToFlip;
    }

    /**
     * Контейнер для координат хода, чтобы возвращать сразу пары значений.
     */
    private static class Move {
        private final int x;
        private final int y;

        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
