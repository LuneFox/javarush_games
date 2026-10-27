package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Drawable;
import com.javarush.games.snake.view.Sign;

import java.util.Date;

/** Клетка местности и её реакция на змейку. */
public abstract class Terrain extends GameObject implements Drawable {
    public static final int FIELD = 0;
    public static final int WOOD = 1;
    public static final int WATER = 2;
    public static final int FIRE = 3;
    public static final int FOREST = 4;
    public static final int WORMHOLE = 5;
    public static final int MOUNTAIN = 6;
    public static final int WALL = 7;
    public static final int SAND = 8;
    public static final int VOID = 9;

    private static final int SYMBOL_TEXT_SIZE = 90;
    private static final int POINTS_PER_DESTROYED_TERRAIN = 1;
    private static final long DRYING_TIME_MS = 1000;
    private static final long WET_DRYING_TIME_PER_SEGMENT_MS = 500;

    private final TerrainType type;
    private final Color color;
    private final Color backgroundColor;
    private final String sign;

    private Date startedToDryTimestamp;
    private boolean isWet;

    /** Создаёт местность по коду или типу. */
    public static Terrain create(int x, int y, TerrainType type) {
        return create(x, y, type.ordinal());
    }

    /** Создаёт местность по коду или типу. */
    public static Terrain create(int x, int y, int terrainCode) {
        // Коды местности соответствуют порядку объявления TerrainType.
        switch (terrainCode) {
            case FIELD:
                return new FieldTerrain(x, y);
            case WOOD:
                return new WoodTerrain(x, y);
            case WATER:
                return new WaterTerrain(x, y);
            case FIRE:
                return new FireTerrain(x, y);
            case FOREST:
                return new ForestTerrain(x, y);
            case WORMHOLE:
                return new WormholeTerrain(x, y);
            case MOUNTAIN:
                return new MountainTerrain(x, y);
            case WALL:
                return new WallTerrain(x, y);
            case SAND:
                return new SandTerrain(x, y);
            case VOID:
            default:
                return new VoidTerrain(x, y);
        }
    }

    /** Создаёт клетку местности с заданным видом. */
    protected Terrain(int x, int y, TerrainType type, Color color, Color backgroundColor, Sign symbol) {
        super(x, y);
        this.type = type;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.sign = Sign.getSign(symbol);
        restartDryingTimer();
    }

    /** Рисует клетку местности. */
    @Override
    public void draw(Game target) {
        target.setCellValueEx(x, y, backgroundColor, sign, color, SYMBOL_TEXT_SIZE);
    }

    /** Реагирует на попадание змейки в клетку. */
    public void interact(Snake snake) {
        if (snake.getElement() == Element.ALMIGHTY) {
            snake.decreaseAlmightyPower();
            turnToVoid();
        }

        refillSnakeBreath(snake);
    }

    /** Превращает клетку в пустоту и даёт очко. */
    private void turnToVoid() {
        if (this instanceof VoidTerrain) return;

        replaceWith(TerrainType.VOID);
        game.getScore().add(POINTS_PER_DESTROYED_TERRAIN);
    }

    /** Восстанавливает воздух змейки (кроме воды). */
    private void refillSnakeBreath(Snake snake) {
        if (this instanceof WaterTerrain) return;

        snake.takeFullBreath();
    }

    /** Заменяет клетку другой местностью. */
    private void replaceWith(TerrainType terrainType) {
        game.getStage().putTerrain(x, y, terrainType);
    }

    /** Применяет пассивный эффект клетки за ход. */
    public void applyPassiveEffect() {
        // По умолчанию ничего не делает
    }

    /** Поджигает клетку от соседнего огня. */
    protected final void igniteFromAdjacentFire() {
        if (!hasAdjacentFire()) {
            restartDryingTimer();
            return;
        }

        if (isDryEnoughToBurn()) {
            replaceWith(TerrainType.FIRE);
        }
    }

    /** Есть ли рядом огонь. */
    private boolean hasAdjacentFire() {
        final Terrain[][] matrix = game.getStage().getTerrainMatrix();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                final int neighborX = x + dx;
                final int neighborY = y + dy;

                if (neighborX < 0 || neighborY < 0) continue;
                if (neighborY >= matrix.length || neighborX >= matrix[neighborY].length) continue;

                if (matrix[neighborY][neighborX] instanceof FireTerrain) return true;
            }
        }

        return false;
    }

    /** Достаточно ли клетка высохла для огня. */
    private boolean isDryEnoughToBurn() {
        final long dryingTime = isWet
                ? Math.max(game.getSnake().getLength() * WET_DRYING_TIME_PER_SEGMENT_MS, DRYING_TIME_MS)
                : DRYING_TIME_MS;
        final long timePassed = new Date().getTime() - startedToDryTimestamp.getTime();

        return timePassed > dryingTime;
    }

    /** Начинает отсчёт высыхания заново. */
    private void restartDryingTimer() {
        startedToDryTimestamp = new Date();
    }

    /** Делает клетку мокрой и сбрасывает таймер. */
    public void makeWet() {
        isWet = true;
        restartDryingTimer();
    }

    /** Возвращает тип местности. */
    public TerrainType getType() {
        return type;
    }
}
