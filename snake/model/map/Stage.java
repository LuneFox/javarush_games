package com.javarush.games.snake.model.map;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.model.terrain.TerrainType;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Stage {
    protected static final int[] VOID_ROW = new int[32];
    protected static final SnakeGame game = SnakeGame.getInstance();

    protected String name;
    protected int[][] terrainCodeMatrix;
    protected Terrain[][] terrainMatrix;
    protected ArrayList<Orb> orbs;
    protected ArrayList<WormHole> wormHoles;
    protected Coordinate snakeStartPlace;
    protected Direction snakeStartDirection;
    protected String briefingMessage;
    protected String completeMessage;
    protected boolean isStarted;
    protected boolean isClear;

    static {
        Arrays.fill(VOID_ROW, 9);
    }

    public Stage() {
        name = "Unnamed";
    }

    public void initialize() {
        orbs = new ArrayList<>();
        wormHoles = new ArrayList<>();
        createTerrainCodeMatrix();
        renderTerrainMatrix();
        createOrbs();
        createWormHoles();
        defineSnakeStartingPlace();
        createMessages();
        isStarted = false;
    }

    protected abstract void createTerrainCodeMatrix();

    protected void renderTerrainMatrix() {
        final int height = terrainCodeMatrix.length;
        final int width = terrainCodeMatrix[0].length;
        terrainMatrix = new Terrain[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int terrainType = terrainCodeMatrix[y][x];
                terrainMatrix[y][x] = Terrain.create(x, y, terrainType);
            }
        }
    }

    protected abstract void createOrbs();

    protected abstract void createWormHoles();

    protected abstract void defineSnakeStartingPlace();

    protected abstract void createMessages();

    public abstract boolean isCompleted();

    public void start() {
        isStarted = true;
    }

    public void showBriefingMessage() {
        if (isStarted) return;
        game.showMessageDialog(Color.YELLOW, briefingMessage, Color.BLACK, 20);
    }

    public void showCompleteMessage() {
        if (!isClear) return;
        game.showMessageDialog(Color.YELLOW, completeMessage, Color.BLACK, 20);
    }

    public void putTerrain(int x, int y, TerrainType terrainType) {
        terrainMatrix[y][x] = Terrain.create(x, y, terrainType.ordinal());
    }

    public Terrain getTerrain(int x, int y) {
        return terrainMatrix[y][x];
    }

    /*
     * Getters
     */

    public String getName() {
        return name;
    }

    public Terrain[][] getTerrainMatrix() {
        return terrainMatrix;
    }

    public ArrayList<Orb> getOrbs() {
        return orbs;
    }

    public ArrayList<WormHole> getWormHoles() {
        return wormHoles;
    }

    public Coordinate getSnakeStartPlace() {
        return snakeStartPlace;
    }

    public Direction getSnakeStartDirection() {
        return snakeStartDirection;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isClear() {
        return isClear;
    }
}