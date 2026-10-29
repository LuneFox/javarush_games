package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.view.printer.Printer;
import com.javarush.games.spaceinvaders.view.printer.TextAlign;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;
import com.javarush.games.spaceinvaders.view.shapes.FireballShape;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

/** Стартовый экран с заголовком, героями, подсказкой и короткой сценкой. */
public class StartScreen {
    /** Y строки заголовка. */
    private static final int TITLE_Y = 10;
    /** Y иконок под заголовком. */
    private static final int ICON_Y = 20;
    /** Y подсказки PRESS ENTER. */
    private static final int PRESS_ENTER_Y = 52;
    /** Y строки HI-SCORE. */
    private static final int HI_SCORE_Y = 64;
    /** Y строки с максимальной стадией. */
    private static final int STAGE_INFO_Y = 73;
    /** Y строки копирайта. */
    private static final int COPYRIGHT_Y = 92;
    /** Период мигания подсказки. */
    private static final int BLINK_PERIOD = 10;
    /** Пауза перед появлением танка: 3 секунды при ходе 40 мс. */
    private static final int IDLE_TICKS_BEFORE_TANK = 75;
    /** Скорость танка. */
    private static final double TANK_SPEED = 0.25;
    /** Отступ танка от Марио. */
    private static final int TANK_STOP_OFFSET = 18;
    /** Длительность разворота Марио. */
    private static final int TURN_TICKS = 6;
    /** Скорость огненного шара. */
    private static final double FIREBALL_SPEED = 1.5;
    /** Длительность одного кадра взрыва. */
    private static final int EXPLOSION_FRAME_TICKS = 6;
    /** Количество кадров взрыва. */
    private static final int EXPLOSION_FRAMES = 3;
    /** Кадры взрыва, повёрнутые вместе с танком. */
    private static final int[][] EXPLOSION_1 = rotate90CounterClockwise(TankShape.TANK_KILL_1);
    private static final int[][] EXPLOSION_2 = rotate90CounterClockwise(TankShape.TANK_KILL_2);
    private static final int[][] EXPLOSION_3 = rotate90CounterClockwise(TankShape.TANK_KILL_3);

    /** Этапы короткой сценки. */
    private enum Phase {
        IDLE, TANK_DRIVING, TURNING_TO_TANK, FIREBALL_FLYING, FIREBALL_HIT,
        EXPLODING, TURNING_BACK, FINISHED
    }

    /** Игра, для которой рисуется экран. */
    private final SpaceInvadersGame game;
    /** Иконка стоящего Марио. */
    private final GameObject marioIcon = new GameObject();
    /** Иконка жёлтого танка. */
    private final GameObject tankIcon = new GameObject();
    /** Зелёный танк из сценки. */
    private final GameObject greenTank = new GameObject();
    /** Огненный шар. */
    private final GameObject fireball = new GameObject();
    /** Взрыв танка. */
    private final GameObject explosion = new GameObject();

    /** Счётчик кадров мигания. */
    private int blinkTicks;
    /** Счётчик ожидания перед сценкой. */
    private int idleTicks;
    /** Счётчик текущего этапа. */
    private int phaseTicks;
    /** Текущий этап сценки. */
    private Phase phase = Phase.IDLE;
    /** Направление взгляда Марио в сценке. */
    private boolean marioFacingLeft;
    /** Координата остановки танка. */
    private double tankTargetX;
    /** Координата танка по вертикали. */
    private double tankY;
    /** Видимость участников сценки. */
    private boolean tankVisible;
    private boolean fireballVisible;
    private boolean explosionVisible;
    /** Номер кадра взрыва. */
    private int explosionFrame;

    /** Создаёт стартовый экран. */
    public StartScreen(SpaceInvadersGame game) {
        this.game = game;
        marioIcon.setStaticView(MarioShape.STAND);
        tankIcon.setStaticView(rotate180(BossTankShape.BOSS_TANK_1));
        greenTank.setStaticView(rotate90CounterClockwise(TankShape.TANK_1));
        fireball.setStaticView(FireballShape.FIREBALL_1);
        explosion.setStaticView(EXPLOSION_1);
    }

    /** Сбрасывает сценку при новом показе главного экрана. */
    public void reset() {
        phase = Phase.IDLE;
        idleTicks = 0;
        phaseTicks = 0;
        marioFacingLeft = false;
        marioIcon.setStaticView(MarioShape.STAND);
        tankVisible = false;
        fireballVisible = false;
        explosionVisible = false;
        explosionFrame = 0;
    }

    /** Рисует стартовый экран. */
    public void draw() {
        fillBlack();

        int marioWidth = Printer.width("MARIO");
        int vsWidth = Printer.width("VS");
        int tanksWidth = Printer.width("TANKS");
        int spaceWidth = Printer.width(" ");
        int titleWidth = marioWidth + spaceWidth + vsWidth + spaceWidth + tanksWidth;
        int titleX = (GameField.WIDTH - titleWidth) / 2;
        int vsX = titleX + marioWidth + spaceWidth;
        int tanksX = vsX + vsWidth + spaceWidth;

        marioIcon.setPosition(titleX + (marioWidth - marioIcon.getWidth()) / 2.0, ICON_Y);
        double tankIconY = ICON_Y + (marioIcon.getHeight() - tankIcon.getHeight());
        tankIcon.setPosition(tanksX + (tanksWidth - tankIcon.getWidth()) / 2.0, tankIconY);

        updateAnimation();

        Printer.print("<MARIO>", Color.RED, titleX, TITLE_Y, TextAlign.LEFT, Color.DARKBLUE);
        Printer.print("VS", Color.WHITE, vsX, TITLE_Y, TextAlign.LEFT);
        Printer.print("<TANKS>", Color.YELLOW, tanksX, TITLE_Y, TextAlign.LEFT, Color.DARKGREEN);

        marioIcon.draw(marioFacingLeft ? Mirror.HORIZONTAL : Mirror.NONE);
        tankIcon.draw();
        if (tankVisible) greenTank.draw();
        if (fireballVisible) fireball.draw(Mirror.HORIZONTAL);
        if (explosionVisible) explosion.draw();

        if (blinkTicks++ % BLINK_PERIOD < BLINK_PERIOD / 2) {
            Printer.print("PRESS ENTER", Color.WHITE, GameField.WIDTH, PRESS_ENTER_Y, TextAlign.CENTER);
        }

        if (Score.getTopScore() > 0) {
            Printer.print("HI-SCORE: " + Score.getTopScore(), Color.BLUE,
                    GameField.WIDTH, HI_SCORE_Y, TextAlign.CENTER);
            Printer.print("(STAGE " + StageProgress.getMaxNumber() + ")", Color.BLUE,
                    GameField.WIDTH, STAGE_INFO_Y, TextAlign.CENTER);
        }

        Printer.print("(C) LUNEFOX (2020-2026)", Color.DIMGRAY,
                GameField.WIDTH, COPYRIGHT_Y, TextAlign.CENTER);
    }

    /** Обновляет короткую сценку. */
    private void updateAnimation() {
        switch (phase) {
            case IDLE:
                if (++idleTicks >= IDLE_TICKS_BEFORE_TANK) {
                    startTankDrive();
                }
                break;
            case TANK_DRIVING:
                greenTank.setPosition(greenTank.getX() + TANK_SPEED, tankY);
                if (greenTank.getX() >= tankTargetX) {
                    greenTank.setPosition(tankTargetX, tankY);
                    phase = Phase.TURNING_TO_TANK;
                    phaseTicks = 0;
                    marioFacingLeft = true;
                }
                break;
            case TURNING_TO_TANK:
                if (++phaseTicks >= TURN_TICKS) {
                    phase = Phase.FIREBALL_FLYING;
                    phaseTicks = 0;
                    marioIcon.setStaticView(MarioShape.JUMP);
                    fireballVisible = true;
                    fireball.setPosition(marioIcon.getX() - fireball.getWidth(), tankY - 1);
                }
                break;
            case FIREBALL_FLYING:
                double nextX = fireball.getX() - FIREBALL_SPEED;
                double tankRight = greenTank.getX() + greenTank.getWidth();
                if (nextX <= tankRight) {
                    // Шар не проскакивает мимо: останавливаем его вплотную к танку
                    fireball.setPosition(tankRight, fireball.getY());
                    phase = Phase.FIREBALL_HIT;
                    phaseTicks = 0;
                } else {
                    fireball.setPosition(nextX, fireball.getY());
                }
                break;
            case FIREBALL_HIT:
                if (++phaseTicks >= 1) {
                    startExplosion();
                }
                break;
            case EXPLODING:
                phaseTicks++;
                int frame = Math.min(phaseTicks / EXPLOSION_FRAME_TICKS, EXPLOSION_FRAMES - 1);
                if (frame != explosionFrame) {
                    explosionFrame = frame;
                    explosion.setStaticView(explosionFrame == 0 ? EXPLOSION_1
                            : explosionFrame == 1 ? EXPLOSION_2 : EXPLOSION_3);
                }
                if (phaseTicks >= EXPLOSION_FRAME_TICKS * EXPLOSION_FRAMES) {
                    explosionVisible = false;
                    phase = Phase.TURNING_BACK;
                    phaseTicks = 0;
                    marioFacingLeft = false;
                }
                break;
            case TURNING_BACK:
                if (++phaseTicks >= TURN_TICKS) {
                    phase = Phase.FINISHED;
                }
                break;
            case FINISHED:
                break;
        }
    }

    /** Начинает выезд танка. */
    private void startTankDrive() {
        tankY = marioIcon.getY() + marioIcon.getHeight() - greenTank.getHeight();
        tankTargetX = Math.max(0, marioIcon.getX() - TANK_STOP_OFFSET);
        greenTank.setPosition(-greenTank.getWidth(), tankY);
        tankVisible = true;
        phase = Phase.TANK_DRIVING;
    }

    /** Запускает взрыв танка. */
    private void startExplosion() {
        fireballVisible = false;
        tankVisible = false;
        marioIcon.setStaticView(MarioShape.STAND);
        explosionVisible = true;
        explosionFrame = 0;
        explosion.setPosition(greenTank.getX(), greenTank.getY());
        explosion.setStaticView(EXPLOSION_1);
        phase = Phase.EXPLODING;
        phaseTicks = 0;
    }

    /** Поворачивает спрайт на 180 градусов. */
    private static int[][] rotate180(int[][] shape) {
        int height = shape.length;
        int width = shape[0].length;
        int[][] rotated = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotated[y][x] = shape[height - 1 - y][width - 1 - x];
            }
        }
        return rotated;
    }

    /** Поворачивает спрайт на 90 градусов против часовой стрелки. */
    private static int[][] rotate90CounterClockwise(int[][] shape) {
        int height = shape.length;
        int width = shape[0].length;
        int[][] rotated = new int[width][height];
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                rotated[y][x] = shape[x][width - 1 - y];
            }
        }
        return rotated;
    }

    /** Заливает экран чёрным. */
    private void fillBlack() {
        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                game.getScreen().setPixel(x, y, Color.BLACK);
            }
        }
    }
}
