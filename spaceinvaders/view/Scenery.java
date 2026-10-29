package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.EagleBase;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.view.shapes.DecoShape;

/** Фон игры: небо, облака, холмы, кусты и земля. */
public class Scenery implements Drawable {
    /** Последняя строка чёрного космоса. */
    private static final int NIGHT_SKY_LAST_ROW = 60;
    /** Верхняя строка обычного неба. */
    private static final int SKY_TOP_ROW = NIGHT_SKY_LAST_ROW + 1;
    /** Нижняя граница базы с орлом — выше небо в победной сцене не поднимается. */
    private static final int VICTORY_SKY_TOP_ROW = EagleBase.BASE_HEIGHT;
    /** Шаг подъёма неба в победной сцене. */
    private static final int VICTORY_SKY_RISE_STEP = 2;
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
    private static final double CLOUD_SPEED = 0.03;
    /** Скорость дрейфа звёзд за ход. */
    private static final double STAR_SPEED = 0.015;
    /** Позиции звёзд: X, Y, размер и индекс цвета; Y ниже линии кирпичей. */
    private static final int[][] STARS = {
            {6, 72, 1, 0}, {18, 76, 2, 0}, {31, 71, 1, 1}, {44, 79, 1, 2},
            {57, 73, 2, 0}, {69, 81, 1, 1}, {83, 74, 1, 0}, {94, 78, 2, 2},
            {12, 82, 1, 2}, {25, 70, 1, 0}, {39, 77, 2, 1}, {52, 83, 1, 0},
            {64, 72, 1, 2}, {77, 80, 2, 1}, {89, 75, 1, 0}, {4, 79, 2, 2},
            {33, 81, 1, 0}, {61, 76, 1, 1}, {72, 82, 1, 0}, {96, 71, 2, 0},
            {16, 74, 1, 1}, {47, 80, 1, 0}, {79, 77, 1, 2}, {54, 75, 2, 0}
    };
    /** Цвета звёзд: белый, жёлтый и серый. */
    private static final Color[] STAR_COLORS = {Color.WHITE, Color.YELLOW, Color.GRAY};
    /** Начальный X Луны. */
    private static final int MOON_X = 72;
    /** Y Луны. */
    private static final int MOON_Y = 71;
    /** Новые облака, появляющиеся после победы выше кирпичей. */
    private static final int[][] VICTORY_CLOUDS = {
            {6, 20}, {46, 32}, {74, 46}, {24, 52}
    };
    /** Новые звёзды, появляющиеся после победы выше кирпичей. */
    private static final int[][] VICTORY_STARS = {
            {10, 22, 1, 0}, {35, 30, 2, 1}, {60, 20, 1, 2},
            {84, 40, 2, 0}, {20, 50, 1, 1}, {72, 52, 1, 0}
    };
    /** Порядок смены палитр неба по стадиям. */
    private static final SkyTheme[] SKY_THEMES = SkyTheme.values();

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
    /** Спрайт луны. */
    private final GameObject moon = new GameObject();

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
    /** Смещение звёздного поля. */
    private double starOffset;
    /** Верхняя строка неба в победной сцене. */
    private int victorySkyTopRow = SKY_TOP_ROW;
    /** Идёт ли подъём неба после победы. */
    private boolean victorySkyRiseActive;
    /** Заполнило ли небо всю сцену после победы. */
    private boolean victorySkyComplete;

    /** Создаёт сцену и готовит декорации. */
    public Scenery(SpaceInvadersGame game) {
        this.game = game;
        cloud.setStaticView(DecoShape.CLOUD);
        hill.setStaticView(DecoShape.HILL);
        bush.setStaticView(DecoShape.BUSH);
        floorTile.setStaticView(DecoShape.FLOOR);
        moon.setStaticView(DecoShape.MOON);
    }

    /** Сбрасывает смещения слоёв и победный подъём неба при старте нового раунда. */
    public void resetParallax() {
        lastMarioX = game.getMario().getX();
        floorOffset = 0;
        bushesOffset = 0;
        hillsOffset = 0;
        victorySkyTopRow = SKY_TOP_ROW;
        victorySkyRiseActive = false;
        victorySkyComplete = false;
    }

    /** Сообщает, идёт ли победная сцена. */
    public boolean isVictoryScene() {
        return victorySkyRiseActive || victorySkyComplete;
    }

    /** Запускает подъём неба в победной сцене. */
    public void startVictorySkyRise() {
        if (victorySkyRiseActive || victorySkyComplete) return;

        victorySkyRiseActive = true;
        victorySkyComplete = false;
        victorySkyTopRow = SKY_TOP_ROW;
    }

    /** Рисует весь фон. */
    @Override
    public void draw() {
        updateOffsets();
        updateVictorySkyRise();
        SkyTheme theme = currentTheme();
        drawSky(theme);
        if (!victorySkyRiseActive) {
            if (theme == SkyTheme.BLACK) {
                drawNightSky();
                if (victorySkyComplete) drawStars(VICTORY_STARS);
            } else {
                drawClouds();
                if (victorySkyComplete) drawVictoryClouds();
            }
        }
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
        starOffset = wrap(starOffset + STAR_SPEED, GameField.WIDTH);
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
    private void drawSky(SkyTheme theme) {
        boolean victory = victorySkyRiseActive || victorySkyComplete;
        for (int y = 0; y < GameField.HEIGHT; y++) {
            Color skyColor = victory ? victorySkyColorAt(y, theme) : skyColorAt(y, theme);
            for (int x = 0; x < GameField.WIDTH; x++) {
                game.getScreen().setPixel(x, y, skyColor);
            }
        }
    }

    /** Возвращает палитру неба для текущей стадии. */
    private SkyTheme currentTheme() {
        int themeIndex = (StageProgress.getNumber() - 1) % SKY_THEMES.length;
        if (themeIndex < 0) {
            themeIndex += SKY_THEMES.length;
        }
        return SKY_THEMES[themeIndex];
    }

    /** Поднимает небо после победы, пока оно не заполнит чёрную зону. */
    private void updateVictorySkyRise() {
        if (!victorySkyRiseActive || game.isPaused()) return;

        victorySkyTopRow = Math.max(VICTORY_SKY_TOP_ROW, victorySkyTopRow - VICTORY_SKY_RISE_STEP);
        if (victorySkyTopRow == VICTORY_SKY_TOP_ROW) {
            victorySkyRiseActive = false;
            victorySkyComplete = true;
        }
    }

    /** Возвращает цвет неба для строки в победной сцене. */
    private Color victorySkyColorAt(int y, SkyTheme theme) {
        if (y < victorySkyTopRow) return Color.BLACK;

        int row = y - victorySkyTopRow;
        if (row <= 2) return theme.edgeColor;
        if (row <= 5) return theme.upperColor;
        if (row <= 8) return theme.middleColor;
        return theme.lowerColor;
    }

    /** Возвращает цвет неба для строки с учётом палитры. */
    private Color skyColorAt(int y, SkyTheme theme) {
        if (y <= NIGHT_SKY_LAST_ROW) return Color.BLACK;
        // Строки 61–63 — кайма, граничащая с чёрным космосом
        if (y <= TWILIGHT_ROW) return theme.edgeColor;
        if (y >= DAWN_FIRST_ROW && y <= DAWN_LAST_ROW) return theme.upperColor;
        if (y >= MORNING_FIRST_ROW && y <= MORNING_LAST_ROW) return theme.middleColor;
        return theme.lowerColor;
    }

    /** Рисует звёзды и луну на чёрном небе. */
    private void drawNightSky() {
        drawStars(STARS);
        drawMoon();
    }

    /** Рисует новые облака в освободившемся верхнем пространстве. */
    private void drawVictoryClouds() {
        for (int[] cloudPosition : VICTORY_CLOUDS) {
            drawCloudAt(cloudPosition[0], cloudPosition[1], Mirror.NONE);
        }
    }

    /** Рисует звёздное поле. */
    private void drawStars(int[][] stars) {
        for (int[] star : stars) {
            int x = (int) pixel(wrap(star[0] + starOffset, GameField.WIDTH));
            int y = star[1];
            int size = star[2];
            Color color = STAR_COLORS[star[3]];
            for (int dy = 0; dy < size; dy++) {
                for (int dx = 0; dx < size; dx++) {
                    game.getScreen().setPixel(x + dx, y + dy, color);
                }
            }
        }
    }

    /** Рисует луну. */
    private void drawMoon() {
        double x = wrap(MOON_X + starOffset, GameField.WIDTH);
        moon.setPosition(pixel(x), MOON_Y);
        moon.draw();
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

    /** Палитры неба, сменяющиеся по стадиям. */
    private enum SkyTheme {
        /** Оригинальное голубое небо. */
        DAY(Color.MIDNIGHTBLUE, Color.ROYALBLUE, Color.DODGERBLUE, Color.DEEPSKYBLUE),
        /** Глубокое синее небо. */
        BLUE(Color.NAVY, Color.DARKBLUE, Color.MEDIUMBLUE, Color.BLUE),
        /** Красно-оранжевый закат. */
        SUNSET(Color.DARKRED, Color.ORANGERED, Color.ORANGE, Color.GOLD),
        /** Фиолетовая ночь. */
        NIGHT(Color.INDIGO, Color.PURPLE, Color.MEDIUMPURPLE, Color.SLATEBLUE),
        /** Чёрное ночное небо. */
        BLACK(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK),
        /** Розово-голубой рассвет. */
        DAWN(Color.THISTLE, Color.LIGHTPINK, Color.LIGHTSKYBLUE, Color.PALETURQUOISE);

        /** Цвет каймы у чёрного космоса. */
        private final Color edgeColor;
        /** Цвет верхней полосы неба. */
        private final Color upperColor;
        /** Цвет средней полосы неба. */
        private final Color middleColor;
        /** Цвет нижней части неба. */
        private final Color lowerColor;

        /** Создаёт палитру неба. */
        SkyTheme(Color edgeColor, Color upperColor, Color middleColor, Color lowerColor) {
            this.edgeColor = edgeColor;
            this.upperColor = upperColor;
            this.middleColor = middleColor;
            this.lowerColor = lowerColor;
        }
    }
}
