package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.view.shapes.DecoShape;

/** Фон игры: небо, облака, холмы, кусты и земля. */
public class Scenery implements Drawable {
    /** Последняя строка чёрного космоса. */
    private static final int NIGHT_SKY_LAST_ROW = 60;
    /** Последняя строка тёмно-синей каймы. */
    private static final int TWILIGHT_ROW = 63;
    /** Первая строка полосы ROYALBLUE. */
    private static final int DAWN_FIRST_ROW = 64;
    /** Последняя строка полосы ROYALBLUE. */
    private static final int DAWN_LAST_ROW = 66;
    /** Первая строка полосы DODGERBLUE. */
    private static final int MORNING_FIRST_ROW = 67;
    /** Последняя строка полосы DODGERBLUE. */
    private static final int MORNING_LAST_ROW = 69;

    /** Количество холмов с запасом для прокрутки. */
    private static final int HILLS_COUNT = 3;
    /** Количество кустов с запасом для прокрутки. */
    private static final int BUSHES_COUNT = 4;
    /** Количество плиток пола с запасом для прокрутки. */
    private static final int FLOOR_TILES_COUNT = 8;
    /** Начальный сдвиг холмов по X. */
    private static final int HILLS_OFFSET_X = 14;
    /** Расстояние между холмами в ширинах холма. */
    private static final double HILLS_GAP = 1.8;

    /** Коэффициент параллакса пола. */
    private static final double FLOOR_PARALLAX_FACTOR = 1.0;
    /** Коэффициент параллакса кустов. */
    private static final double BUSH_PARALLAX_FACTOR = 0.7;
    /** Коэффициент параллакса холмов. */
    private static final double HILL_PARALLAX_FACTOR = 0.4;
    /** Скорость дрейфа облаков за ход. */
    private static final double CLOUD_SPEED = 0.25;

    /** Игра, для которой рисуется фон. */
    private final SpaceInvadersGame game;
    /** Спрайт облака. */
    private final GameObject cloud = new GameObject();
    /** Спрайт холма. */
    private final GameObject hill = new GameObject();
    /** Спрайт куста. */
    private final GameObject bush = new GameObject();
    /** Спрайт плитки пола. */
    private final GameObject floorTile = new GameObject();

    /** X Марио на предыдущем кадре. */
    private double lastMarioX;
    /** Смещение слоя пола. */
    private double floorOffset;
    /** Смещение слоя кустов. */
    private double bushesOffset;
    /** Смещение слоя холмов. */
    private double hillsOffset;
    /** Смещение облаков. */
    private double cloudOffset;

    /** Создаёт сцену и готовит декорации. */
    public Scenery(SpaceInvadersGame game) {
        this.game = game;
        cloud.setStaticView(DecoShape.CLOUD);
        hill.setStaticView(DecoShape.HILL);
        bush.setStaticView(DecoShape.BUSH);
        floorTile.setStaticView(DecoShape.FLOOR);
    }

    /** Сбрасывает смещения слоёв, привязанных к ходу Марио, при старте нового раунда. */
    public void resetParallax() {
        lastMarioX = game.getMario().getX();
        floorOffset = 0;
        bushesOffset = 0;
        hillsOffset = 0;
    }

    /** Рисует весь фон. */
    @Override
    public void draw() {
        updateOffsets();
        drawSky();
        drawClouds();
        drawHills();
        drawBushes();
        drawFloor();
    }

    /** Пересчитывает смещения слоёв по ходу Марио и полёт облаков. */
    private void updateOffsets() {
        double marioX = game.getMario().getX();
        double deltaX = marioX - lastMarioX;
        lastMarioX = marioX;

        if (game.isPaused()) return;

        floorOffset = wrap(floorOffset - deltaX * FLOOR_PARALLAX_FACTOR, floorTile.getWidth());
        bushesOffset = wrap(bushesOffset - deltaX * BUSH_PARALLAX_FACTOR, bushStride());
        hillsOffset = wrap(hillsOffset - deltaX * HILL_PARALLAX_FACTOR, hillStride());
        cloudOffset = wrap(cloudOffset + CLOUD_SPEED, cloudPeriod());
    }

    /** Приводит смещение к диапазону [0, period). */
    private static double wrap(double value, double period) {
        double wrapped = value % period;
        return (wrapped < 0) ? wrapped + period : wrapped;
    }

    /** Возвращает шаг кустов. */
    private double bushStride() {
        return 2 * bush.getWidth();
    }

    /** Возвращает шаг холмов. */
    private double hillStride() {
        return HILLS_GAP * hill.getWidth();
    }

    /** Возвращает период оборота облаков. */
    private double cloudPeriod() {
        return GameField.WIDTH + cloud.getWidth();
    }

    /** Округляет координату до пикселя, чтобы у слоёв не было дробных швов. */
    private static double pixel(double value) {
        return Math.round(value);
    }

    /** Заливает небо. */
    private void drawSky() {
        for (int y = 0; y < GameField.HEIGHT; y++) {
            Color skyColor = skyColorAt(y);
            for (int x = 0; x < GameField.WIDTH; x++) {
                game.getScreen().setPixel(x, y, skyColor);
            }
        }
    }

    /** Возвращает цвет неба для строки. */
    private Color skyColorAt(int y) {
        if (y <= NIGHT_SKY_LAST_ROW) return Color.BLACK;
        // Строки 61–63 — тёмная синяя кайма, граничащая с чёрным космосом
        if (y <= TWILIGHT_ROW) return Color.MIDNIGHTBLUE;
        if (y >= DAWN_FIRST_ROW && y <= DAWN_LAST_ROW) return Color.ROYALBLUE;
        if (y >= MORNING_FIRST_ROW && y <= MORNING_LAST_ROW) return Color.DODGERBLUE;
        return Color.DEEPSKYBLUE;
    }

    /** Рисует облака. */
    private void drawClouds() {
        drawCloudAt(5, 65, Mirror.NONE);
        drawCloudAt(38, 57, Mirror.NONE);
        drawCloudAt(81, 72, Mirror.HORIZONTAL);
    }

    /** Рисует облако с учётом общего смещения. */
    private void drawCloudAt(double baseX, double y, Mirror mirror) {
        double period = cloudPeriod();
        double x = wrap(baseX + cloudOffset + cloud.getWidth(), period) - cloud.getWidth();
        cloud.setPosition(pixel(x), y);
        cloud.draw(mirror);
    }

    /** Рисует холмы. */
    private void drawHills() {
        double y = GameField.HEIGHT - DecoShape.FLOOR.length - DecoShape.HILL.length;
        double stride = hillStride();
        for (int i = 0; i < HILLS_COUNT; i++) {
            hill.setPosition(pixel(hillsOffset - stride + HILLS_OFFSET_X + i * stride), y);
            hill.draw();
        }
    }

    /** Рисует кусты. */
    private void drawBushes() {
        double y = GameField.HEIGHT - DecoShape.FLOOR.length - DecoShape.BUSH.length;
        double stride = bushStride();
        for (int i = 0; i < BUSHES_COUNT; i++) {
            bush.setPosition(pixel(bushesOffset - stride + i * stride), y);
            bush.draw();
        }
    }

    /** Рисует землю. */
    private void drawFloor() {
        double y = GameField.HEIGHT - DecoShape.FLOOR.length;
        double width = floorTile.getWidth();
        for (int i = 0; i < FLOOR_TILES_COUNT; i++) {
            floorTile.setPosition(pixel(floorOffset - width + i * width), y);
            floorTile.draw();
        }
    }
}
