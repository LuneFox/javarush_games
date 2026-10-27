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

/** Уровень: карта, орбы, червоточины и условие завершения. */
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

    /** Создаёт безымянный уровень. */
    public Stage() {
        this(Strings.STAGE_UNNAMED);
    }

    /** Создаёт уровень с заданным названием. */
    public Stage(Strings name) {
        this.name = name;
    }

    /** Создаёт строку пустоты для HUD. */
    private static int[] createVoidRow() {
        int[] row = new int[SnakeGame.SIZE];
        Arrays.fill(row, Terrain.VOID);
        return row;
    }

    /** Пересоздаёт карту, орбы, червоточины и сообщения. */
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

    /** Задаёт карту уровня кодами местности. */
    protected abstract void createTerrainCodeMatrix();

    /** Создаёт объекты местности по кодам карты. */
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

    /** Расставляет орбы уровня. */
    protected abstract void createOrbs();

    /** Задаёт червоточины уровня. */
    protected abstract void createWormHoles();

    /** Задаёт стартовую точку и направление змейки. */
    protected abstract void defineSnakeStartingPlace();

    /** Формирует тексты брифинга и завершения. */
    protected abstract void createMessages();

    /** Проверяет условие прохождения уровня. */
    public abstract boolean isCompleted();

    /** Помечает уровень пройденным, если условие выполнено. */
    protected final boolean completeIf(boolean condition) {
        if (!condition) return false;

        isClear = true;
        return true;
    }

    /** Запускает уровень и запоминает время старта. */
    public void start() {
        isStarted = true;
        startTimeStamp = new Date();
    }

    /** Показывает брифинг уровня. */
    public void showBriefingMessage() {
        game.showMessageDialog(Color.YELLOW, briefingMessage, Color.BLACK, 10);
    }

    /** Показывает сообщение о прохождении. */
    public void showCompleteMessage() {
        game.showMessageDialog(Color.YELLOW, completeMessage, Color.BLACK, 10);
    }

    /** Кладёт местность в клетку. */
    public void putTerrain(int x, int y, TerrainType terrainType) {
        terrainMatrix[y][x] = Terrain.create(x, y, terrainType);
    }

    /** Возвращает местность клетки. */
    public Terrain getTerrain(int x, int y) {
        return terrainMatrix[y][x];
    }

    /** Создаёт нейтральный орб в подходящей клетке. */
    public void spawnNeutralOrb() {
        final Terrain terrain = findTerrainForNeutralOrb();

        if (terrain == null) return;

        orbs.add(Orb.create(terrain.x, terrain.y, Element.NEUTRAL));
    }

    /** Ищет свободную клетку для нейтрального орба. */
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

        // getRandomNumber(bound) не возвращает bound, поэтому передаётся размер.
        final int random = game.getRandomNumber(applicableTerrains.size());
        return applicableTerrains.get(random);
    }

    /** Может ли клетка содержать нейтральный орб. */
    private boolean canHoldNeutralOrb(Terrain terrain) {
        return terrain instanceof FieldTerrain
                || terrain instanceof WaterTerrain
                || terrain instanceof WoodTerrain;
    }

    /** Собирает орбы под головой и добавляет новый при необходимости. */
    public void collectOrbs(Snake snake) {
        orbs.forEach(orb -> orb.collect(snake));
        orbs.removeIf(Orb::isCollected);
        spawnNeutralOrbIfMissing();
    }

    /** Создаёт нейтральный орб, если его нет. */
    private void spawnNeutralOrbIfMissing() {
        final boolean hasNeutralOrb = orbs.stream().anyMatch(orb -> orb instanceof NeutralOrb);

        if (!hasNeutralOrb) {
            spawnNeutralOrb();
        }
    }

    /** Время старта уровня в миллисекундах. */
    public long getStartTimeMillis() {
        return startTimeStamp.getTime();
    }

    /** Название уровня на текущем языке. */
    public String getName() {
        return name.get();
    }

    /** Матрица местности уровня. */
    public Terrain[][] getTerrainMatrix() {
        return terrainMatrix;
    }

    /** Орбы уровня. */
    public List<Orb> getOrbs() {
        return orbs;
    }

    /** Червоточины уровня. */
    public List<WormHole> getWormHoles() {
        return wormHoles;
    }

    /** Стартовая точка змейки. */
    public Coordinate getSnakeStartPlace() {
        return snakeStartPlace;
    }

    /** Стартовое направление змейки. */
    public Direction getSnakeStartDirection() {
        return snakeStartDirection;
    }

    /** Запущен ли уровень. */
    public boolean isStarted() {
        return isStarted;
    }

    /** Пройден ли уровень. */
    public boolean isClear() {
        return isClear;
    }
}
