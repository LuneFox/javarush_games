package com.javarush.games.ticktacktoe;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import com.javarush.engine.cell.Key;
import com.javarush.games.ticktacktoe.controller.Click;
import com.javarush.games.ticktacktoe.model.BoardManager;
import com.javarush.games.ticktacktoe.model.Computer;
import com.javarush.games.ticktacktoe.model.Message;
import com.javarush.games.ticktacktoe.model.gameobjects.field.Field;
import com.javarush.games.ticktacktoe.model.gameobjects.GameObject;
import com.javarush.games.ticktacktoe.controller.Controller;
import com.javarush.games.ticktacktoe.model.gameobjects.Side;
import com.javarush.games.ticktacktoe.view.*;
import com.javarush.games.ticktacktoe.view.printer.*;

/**
 * Классическая игра "Реверси" ("Отелло")
 *
 * @author LuneFox
 * @version 1.2
 */

public class TicTacToeGame extends Game {

    /** Экземпляр игры */
    private static TicTacToeGame instance;
    /** Ширина игрового поля */
    public static final int WIDTH = 100;
    /** Высота игрового поля */
    public static final int HEIGHT = 100;

    /** Контроллер для считывания команд управления */
    private final Controller controller;
    /** Экран для отрисовки графики */
    private final Display display;
    /** Игровое поле */
    private Field field;
    /** Класс для управления игровыми элементами на поле */
    private BoardManager manager;
    /** Виртуальный соперник - компьютер */
    private final Computer computer;
    /** Сторона, чей сейчас ход (белые или чёрные) */
    private Side currentPlayer;
    /** Флаг, что сейчас ход компьютера */
    private boolean isComputerTurn;
    /** Флаг, что игра уже началась */
    private boolean isStarted;

    public TicTacToeGame(){
        instance = this;
        display = new Display(this);
        controller = new Controller(this);
        computer = new Computer(this);
        GameObject.setGame(this);
    }

    /**
     * Инициализация игры
     */
    @Override
    public void initialize() {
        setupJavaRushGamesEnvironment();
        setupNewGame();
    }

    /**
     * Установка базовых переменных для игрового движка
     */
    private void setupJavaRushGamesEnvironment() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        setTurnTimer(40);
    }

    /**
     * Установка параметров для начала новой игры
     */
    public void setupNewGame() {
        isStarted = false;
        currentPlayer = Side.BLACK;
        isComputerTurn = false;
        field = new Field();
        manager = new BoardManager(this, field);
        manager.setupNewGame();
    }

    /**
     * Передача хода
     */
    public void changePlayer() {
        currentPlayer = Side.flip(currentPlayer);
        isComputerTurn = !isComputerTurn;
        manager.markLegalMoves();
    }

    /**
     * Методы, которые вызываются каждый игровой шаг
     *
     * @param step Счётчик шагов
     */
    @Override
    public void onTurn(int step) {
        field.draw();

        if (isComputerTurn) {
            computer.takeTurn();
        }

        printTextInfo();
        display.draw();
    }

    /**
     * Отображение текстовой информации на игровом поле
     */
    private void printTextInfo() {
        Printer.print(Message.GAME_NAME, Color.LIGHTSLATEGRAY, 1, 0, TextAlign.CENTER);
        Printer.print(Message.VERSION, Color.DARKOLIVEGREEN, 100, 0, TextAlign.RIGHT);
        Printer.print(String.valueOf(field.countDisks(Side.BLACK)), Color.BLACK, 1, 46, TextAlign.LEFT);
        Printer.print(String.valueOf(field.countDisks(Side.WHITE)), Color.WHITE, 100, 46, TextAlign.RIGHT);
        if (!computer.showSpeedSetting()) {
            if (!isStarted) {
                Printer.print(Message.CHOOSE_WHITE_SIDE, Color.SLATEGRAY, 1, 91, TextAlign.CENTER);
            } else if (field.noMovesLeft()) {
                Printer.print(getVictoryMessage(), Color.YELLOW, 1, 91, TextAlign.CENTER);
            }
        }
    }

    /**
     * Подсчитывает количество дисков на поле и возвращает сообщение о победителе
     */
    private String getVictoryMessage() {
        int countBlack = field.countDisks(Side.BLACK);
        int countWhite = field.countDisks(Side.WHITE);

        if (countBlack > countWhite) {
            return Message.VICTORY_BLACK;
        } else if (countWhite > countBlack) {
            return Message.VICTORY_WHITE;
        } else {
            return Message.VICTORY_DRAW;
        }
    }

    /**
     * Устанавливает, что игра началась
     */
    public void start() {
        if (!isStarted) isStarted = true;
    }

    // Управление

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.click(x, y, Click.LEFT);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        controller.click(x, y, Click.RIGHT);
    }

    public void clickOnBoard(int mouseClickX, int mouseClickY, Click click) {
        if (click == Click.LEFT) {
            manager.doManualTurn(Click.toBoard(mouseClickX), Click.toBoard(mouseClickY));
        }
    }


    // Геттеры, сеттеры

    public static TicTacToeGame getInstance() {
        return instance;
    }

    public Display getDisplay() {
        return display;
    }

    public Field getField() {
        return field;
    }

    public Computer getComputer() {
        return computer;
    }

    public Side getCurrentPlayer() {
        return currentPlayer;
    }

    public BoardManager getManager() {
        return manager;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isComputerTurn() {
        return isComputerTurn;
    }

    public void setComputerTurn(boolean computerTurn) {
        isComputerTurn = computerTurn;
    }
}
