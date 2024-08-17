package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

import java.util.ArrayList;

/**
 * Игровое поле с фишками.
 * Отрисовка и проверка состояния (без управления элементами).
 *
 * @author LuneFox
 */
public class Field extends GameObject {
    /** Клетка поля для отрисовки */
    private final static GameObject CELL = new GameObject();
    /** Стол - область за клетками для отрисовки */
    private final static GameObject TABLE = new GameObject();
    /** Массив из игровых дисков */
    private final Disk[][] disks;
    /** Маркеры, которыми помечаются допустимые для хода клетки */
    private final ArrayList<LegalMoveMark> legalMoveMarks;
    /** Маркер для последнего выложенного диска */
    private final LastMoveMark lastMoveMark;
    /** Состояние поля, когда ни одной стороне невозможно совершить ход */
    private boolean noMovesLeft;


    static {
        CELL.setStaticView(Shape.FIELD_CELL_SHAPE);
        TABLE.setStaticView(Shape.TABLE);
    }

    public Field() {
        this.disks = new Disk[8][8];
        legalMoveMarks = new ArrayList<>();
        lastMoveMark = new LastMoveMark();
        noMovesLeft = false;
    }

    @Override
    public void draw() {
        TABLE.draw();
        drawBoard();
        drawDisks();
        drawMoveMarks();
    }

    /**
     * Отрисовка клетки 64 раза (получается доска 8х8, которая лежит по центру)
     */
    private static void drawBoard() {
        for (int y = 1; y < 9; y++) {
            for (int x = 1; x < 9; x++) {
                CELL.setPosition(x * 10, y * 10);
                CELL.draw();
            }
        }
    }

    /**
     * Отрисовка всех выложенных дисков
     */
    private void drawDisks() {
        getAllDisks().forEach(GameObject::draw);
    }

    /**
     * Отрисовка маркеров допустимых ходов
     */
    private void drawMoveMarks() {
        legalMoveMarks.forEach(GameObject::draw);
        lastMoveMark.draw();
    }

    /**
     * Установить метку в клетку, куда был совершён последний ход
     *
     * @param x положение клетки по горизонтали
     * @param y положение клетки по вертикали
     */
    public void placeLastMoveMark(int x, int y) {
        lastMoveMark.setPosition(x * 10 + 15, y * 10 + 14);
    }

    /**
     * Проверить, доступен ли ход.
     * Ход доступен, если запрашиваемые координаты хода совпадают с координатами одной из меток доступных ходов.
     *
     * @param moveX потенциальный X хода
     * @param moveY потенциальный Y хода
     * @return ход доступен
     */
    public boolean moveIsLegal(int moveX, int moveY) {
        for (LegalMoveMark legalMoveMark : legalMoveMarks) {
            if (legalMoveMark.getBoardX() == moveX && legalMoveMark.getBoardY() == moveY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет наличие доступных ходов.
     * Если текущий игрок не может ходить, передаёт ход сопернику.
     * Если и соперник не может ходить, устанавливает, что ходов не осталось.
     */
    public void checkAvailableMoves() {
        if (legalMoveMarks.isEmpty()) {
            game.changePlayer();
            if (legalMoveMarks.isEmpty()) {
                noMovesLeft = true;
            }
        }
    }

    /**
     * Посчитать общее количество дисков одного игрока на столе.
     *
     * @param side сторона игрока
     * @return количество дисков
     */
    public int countDisks(Side side) {
        return (int) getAllDisks().stream().filter(disk -> disk.getSide() == side).count();
    }

    /**
     * Получить список всех дисков на столе.
     *
     * @return все диски
     */
    private ArrayList<Disk> getAllDisks() {
        ArrayList<Disk> result = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (disks[y][x] != null) {
                    result.add(disks[y][x]);
                }
            }
        }
        return result;
    }

    /**
     * Проверяет переданные координаты на принадлежность доске
     *
     * @param x горизонталь
     * @param y вертикаль
     * @return координаты принадлежат клетке доски
     */
    public boolean isOutOfBoard(int x, int y) {
        return (x < 0 || x > 7 || y < 0 || y > 7);
    }

    /**
     * Оба игрока не могут сделать ход?
     *
     * @return не осталось ходов
     */
    public boolean noMovesLeft() {
        return noMovesLeft;
    }

    /**
     * Текущий игрок не может сделать ход?
     *
     * @return нет доступных ходов
     */
    public boolean noLegalMoves() {
        return legalMoveMarks.isEmpty();
    }

    public Disk[][] getDisks() {
        return disks;
    }

    public ArrayList<LegalMoveMark> getLegalMoveMarks() {
        return legalMoveMarks;
    }
}