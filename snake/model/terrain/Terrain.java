package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Score;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;

import java.util.Arrays;
import java.util.Date;

public abstract class Terrain extends GameObject {
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

    protected TerrainType type;
    protected Color color;
    protected Color backgroundColor;
    protected String sign;
    protected Date startedToDryTimestamp;
    protected boolean isWet;

    public static Terrain create(int x, int y, int type) {
        switch (type) {
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

    protected Terrain(int x, int y) {
        super(x, y);
        preventBurning();
    }

    public void draw(Game game) {
        game.setCellValueEx(x, y, backgroundColor, sign, color, 90);
    }

    public void interact(Snake snake) {
        if (snake.getElement() == Element.ALMIGHTY) {
            snake.decreaseAlmightyPower();
            turnToVoid();
        }

        rechargeSnakeBreath(snake);
    }

    private void turnToVoid() {
        if (this instanceof VoidTerrain) return;
        replaceWithAnotherTerrain(TerrainType.VOID);
        Score.add(1);
    }

    private void rechargeSnakeBreath(Snake snake) {
        if (this instanceof WaterTerrain) return;
        snake.takeFullBreath();
    }

    private void replaceWithAnotherTerrain(TerrainType terrainType) {
        game.getStage().putTerrain(x, y, terrainType);
    }

    public void processPassiveEffects() {
        // Do nothing by default
    }

    protected final void igniteOnContactWithFire() {
        if (hasFireNeighbor()) {
            if (isCompletelyDry()) {
                replaceWithAnotherTerrain(TerrainType.FIRE);
            }
        } else {
            preventBurning();
        }
    }

    private boolean hasFireNeighbor() {
        return Arrays.stream(game.getStage().getTerrainMatrix())
                .flatMap(Arrays::stream)
                .filter(terrain -> Math.abs(terrain.x - this.x) <= 1)
                .filter(terrain -> Math.abs(terrain.y - this.y) <= 1)
                .filter(terrain -> terrain != this)
                .anyMatch(terrain -> terrain instanceof FireTerrain);
    }

    private boolean isCompletelyDry() {
        final Snake snake = game.getSnake();
        long dryingTime = isWet ? Math.max(snake.getLength() * 500, 1000) : 1000;
        long timePassed = new Date().getTime() - startedToDryTimestamp.getTime();
        return timePassed > dryingTime;
    }

    private void preventBurning() {
        startedToDryTimestamp = new Date();
    }

    public void makeWet() {
        isWet = true;
    }

    public TerrainType getType() {
        return type;
    }
}
