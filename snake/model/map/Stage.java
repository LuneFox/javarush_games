package com.javarush.games.snake.model.map;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.orbs.NeutralOrb;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.terrain.FieldTerrain;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.model.terrain.TerrainType;
import com.javarush.games.snake.model.terrain.WaterTerrain;
import com.javarush.games.snake.model.terrain.WoodTerrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Stage {
    protected static final int[] VOID_ROW = createVoidRow();
    protected static final SnakeGame game = SnakeGame.getInstance();

    private final Strings name;

    protected int[][] terrainCodeMatrix;
    protected Terrain[][] terrainMatrix;
    protected List<Orb> orbs;
    protected List<WormHole> wormHoles;
    protected Coordinate snakeStartPlace;
    protected Direction snakeStartDirection;
    protected String briefingMessage;
    protected String completeMessage;
    protected boolean isClear;

    private Date startTimeStamp;
    private boolean isStarted;

    public Stage() {
        this(Strings.STAGE_UNNAMED);
    }

    public Stage(Strings name) {
        this.name = name;
    }

    private static int[] createVoidRow() {
        int[] row = new int[SnakeGame.SIZE];
        Arrays.fill(row, Terrain.VOID);
        return row;
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
                final int terrainCode = terrainCodeMatrix[y][x];
                terrainMatrix[y][x] = Terrain.create(x, y, terrainCode);
            }
        }
    }

    protected abstract void createOrbs();

    protected abstract void createWormHoles();

    protected abstract void defineSnakeStartingPlace();

    protected abstract void createMessages();

    public abstract boolean isCompleted();

    protected final boolean completeIf(boolean condition) {
        if (!condition) return false;

        isClear = true;
        return true;
    }

    public void start() {
        isStarted = true;
        startTimeStamp = new Date();
    }

    public void showBriefingMessage() {
        game.showMessageDialog(Color.YELLOW, briefingMessage, Color.BLACK, 10);
    }

    public void showCompleteMessage() {
        game.showMessageDialog(Color.YELLOW, completeMessage, Color.BLACK, 10);
    }

    public void putTerrain(int x, int y, TerrainType terrainType) {
        terrainMatrix[y][x] = Terrain.create(x, y, terrainType);
    }

    public Terrain getTerrain(int x, int y) {
        return terrainMatrix[y][x];
    }

    public void spawnNeutralOrb() {
        final Terrain terrain = findTerrainForNeutralOrb();

        if (terrain == null) return;

        orbs.add(Orb.create(terrain.x, terrain.y, Element.NEUTRAL));
    }

    private Terrain findTerrainForNeutralOrb() {
        final Snake snake = game.getSnake();

        List<Terrain> applicableTerrains = Arrays.stream(terrainMatrix)
                .flatMap(Arrays::stream)
                .filter(terrain -> !snake.occupiesCellOf(terrain))
                .filter(this::canHoldNeutralOrb)
                .collect(Collectors.toList());

        if (!snake.canUseElement(Element.WATER) && !snake.canUseElement(Element.ALMIGHTY)) {
            applicableTerrains = applicableTerrains.stream()
                    .filter(terrain -> terrain instanceof FieldTerrain)
                    .collect(Collectors.toList());
        }

        if (applicableTerrains.isEmpty()) return null;

        // getRandomNumber(bound) never returns the bound itself, so the size is passed as is.
        final int random = game.getRandomNumber(applicableTerrains.size());
        return applicableTerrains.get(random);
    }

    private boolean canHoldNeutralOrb(Terrain terrain) {
        return terrain instanceof FieldTerrain
                || terrain instanceof WaterTerrain
                || terrain instanceof WoodTerrain;
    }

    public void collectOrbs(Snake snake) {
        orbs.forEach(orb -> orb.collect(snake));
        orbs.removeIf(Orb::isCollected);
        spawnNeutralOrbIfMissing();
    }

    private void spawnNeutralOrbIfMissing() {
        final boolean hasNeutralOrb = orbs.stream().anyMatch(orb -> orb instanceof NeutralOrb);

        if (!hasNeutralOrb) {
            spawnNeutralOrb();
        }
    }

    public long getStartTimeMillis() {
        return startTimeStamp.getTime();
    }

    public String getName() {
        return name.get();
    }

    public Terrain[][] getTerrainMatrix() {
        return terrainMatrix;
    }

    public List<Orb> getOrbs() {
        return orbs;
    }

    public List<WormHole> getWormHoles() {
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
