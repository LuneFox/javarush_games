package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.view.Sign;

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

    protected TerrainType terrainType;
    protected Color color;
    protected Color backgroundColor;
    protected String sign;
    protected Date becameWetTimeStamp;
    protected boolean isWet; // water snake was here

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
    }

    public void draw(Game game) {
        this.causeEffect();
        game.setCellValueEx(x, y, backgroundColor, sign, color, 90);
    }

    private void causeEffect() { // interact with surrounding nodes
        if (Phase.is(Phase.GAME_FIELD)) {
            if (terrainType == TerrainType.WOOD || terrainType == TerrainType.FOREST) {
                setOnFire();
            }
        }
    }

    private void setOnFire() {
        if (hasFireNear()) {

            int igniteDelay;
            igniteDelay = isWet ? Math.max(game.getSnakeLength() * 500, 1000) : 1000;

            if (new Date().getTime() - becameWetTimeStamp.getTime() > igniteDelay) {
                game.getMap().placeTerrain(x, y, TerrainType.FIRE);
            }

        } else {
            becameWetTimeStamp = new Date();
        }
    }

    private boolean hasFireNear() {
        Terrain neighbor;
        for (int x = this.x - 1; x <= this.x + 1; x++) {
            for (int y = this.y - 1; y <= this.y + 1; y++) {
                if (game.outOfBounds(x, y)) {
                    continue;
                }
                neighbor = (game.getMap().getTerrain(x, y));
                if (neighbor.terrainType == TerrainType.FIRE) {
                    return true;
                }
            }
        }
        return false;
    }

    public TerrainType getType() {
        return terrainType;
    }

    public void setWet(boolean wet) {
        isWet = wet;
    }
}
