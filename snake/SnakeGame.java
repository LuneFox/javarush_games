package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.javarush.games.snake.Triggers.*;

public class SnakeGame extends Game {
    // Global parameters
    static final int WIDTH = 32;
    static final int HEIGHT = 32;
    static final int MAX_TURN_DELAY = 300;
    private InputEvent ie;
    private final Menu menu = new Menu(this);

    // Game flow parameters
    private String gameOverReason;
    private Date stageStartDate;
    private long points;
    private int turnDelay;
    private int snakeLength;
    private int score;
    private int lifetime;
    private boolean isStopped;
    private boolean isPaused;
    private int stage;
    boolean acceleration;

    // Game objects
    private Snake snake;
    private Map map;
    private Orb neutralOrb;
    private Orb waterOrb;
    private Orb fireOrb;
    private Orb earthOrb;
    private Orb airOrb;
    private Orb almightyOrb;
    private ArrayList<Orb> orbs;

    // SETUP

    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        Signs.set(Graphics.KANJI);
        Screen.set(Screen.Type.MAIN_MENU);
        ie = new InputEvent(this);
        Menu.Selector.setPointer(0);
        menu.displayMain();
        stage = 0;
        acceleration = true;
    }

    final void createGame() {
        // Make new objects
        map = new Map(stage, this);
        snake = new Snake(map.snakeStartPlace.x, map.snakeStartPlace.y, this, map.snakeStartDirection);
        orbs = new ArrayList<>();
        importElementalOrbs();
        createNeutralOrb();

        // Initialize values
        Triggers.reset();
        snakeLength = snake.getLength();
        score = 0;
        lifetime = 301;

        // Launch
        isStopped = false;
        isPaused = false;
        turnDelay = MAX_TURN_DELAY;
        setTurnTimer(turnDelay);
        drawScene();
        stageStartDate = new Date();
        points = 300;
    }


    // GAME END

    private void win() {
        stopTurnTimer();
        drawScene();
        isStopped = true;
        showMessageDialog(Color.YELLOW, Strings.VICTORY + score, Color.GREEN, 27);
    }

    private void gameOver() {
        stopTurnTimer();
        drawScene();
        isStopped = true;
        showMessageDialog(Color.YELLOW, gameOverReason, Color.RED, 27);
    }


    // GAME MECHANICS

    public void onTurn(int step) {
        points = calculatePoints();
        snake.move();
        for (Orb orb : orbs) {
            snake.interactWithOrb(orb);
        }
        processOrb(neutralOrb);
        processOrb(waterOrb);
        processOrb(fireOrb);
        processOrb(earthOrb);
        processOrb(airOrb);
        processOrb(almightyOrb);
        snakeLength = snake.getLength();

        if (!snake.isAlive) {
            gameOver();
        }

        if (lifetime <= 0) {
            win();
        }

        setTurnTimer(turnDelay);
        drawScene();
    }

    private void processOrb(Orb orb) {
        switch (orb.element) {
            case NEUTRAL:
                if (!orb.isAlive) {
                    orbs.remove(orb);
                    createNeutralOrb();
                    score += 5;
                }
                break;
            case WATER:
                if (!orb.isAlive && !waterOrbObtained) {
                    waterOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.WATER);
                    do {
                        snake.rotateToNextElement(this);
                    } while (snake.getElement() != Element.WATER);
                    snake.canChangeElement = false;
                    score += points;
                }
                break;
            case FIRE:
                if (!orb.isAlive && !fireOrbObtained) {
                    fireOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.FIRE);
                    do {
                        snake.rotateToNextElement(this);
                    } while (snake.getElement() != Element.FIRE);
                    snake.canChangeElement = false;
                    score += points;
                }
                break;
            case EARTH:
                if (!orb.isAlive && !earthOrbObtained) {
                    earthOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.EARTH);
                    do {
                        snake.rotateToNextElement(this);
                    } while (snake.getElement() != Element.EARTH);
                    snake.canChangeElement = false;
                    score += points;
                }
                break;
            case AIR:
                if (!orb.isAlive && !airOrbObtained) {
                    airOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.AIR);
                    do {
                        snake.rotateToNextElement(this);
                    } while (snake.getElement() != Element.AIR);
                    snake.canChangeElement = false;
                    score += points;
                }
                break;
            case ALMIGHTY:
                if (!orb.isAlive && !almightyOrbObtained) {
                    almightyOrbObtained = true;
                    menu.selectStageUp();
                    orbs.remove(orb);
                    snake.clearElements();
                    snake.getElementsAvailable().add(Element.ALMIGHTY);
                    do {
                        snake.rotateToNextElement(this);
                    } while (snake.getElement() != Element.ALMIGHTY);
                    snake.canChangeElement = false;
                    score += points;
                }
                break;
            default:
                break;
        }
    }


    // OBJECT CREATIONS

    private void createNeutralOrb() {
        int x;
        int y;
        do {
            x = getRandomNumber(WIDTH);
            y = getRandomNumber(HEIGHT - 4) + 4;
            neutralOrb = new Orb(x, y, Element.NEUTRAL);
        } while (isBadPlaceForOrb(x, y));
        orbs.add(neutralOrb);
    }

    private void importElementalOrbs() {
        waterOrb = map.orbs.get(0);
        fireOrb = map.orbs.get(1);
        earthOrb = map.orbs.get(2);
        airOrb = map.orbs.get(3);
        almightyOrb = map.orbs.get(4);
        orbs.add(waterOrb);
        orbs.add(fireOrb);
        orbs.add(earthOrb);
        orbs.add(airOrb);
        orbs.add(almightyOrb);
    }


    // VISUALS

    private void drawScene() {
        drawMap();
        drawOrbs();
        drawSnake();
        drawInterface();
    }

    private void drawInterface() {
        if (Screen.is(Screen.Type.GAME)) {
            new Message("hunger  : ", Color.CORAL).draw(this, 0, 0);
            new Message("strength: " + (snake.getLength()), Color.WHITE).draw(this, 0, 1);
            new Message("element : " + (snake.getElementsAvailable().get(0)), Color.YELLOW).draw(this, 0, 2);
            new Message("score   : " + score, Color.LIGHTBLUE).draw(this, 0, 3);
            drawElementsPanel();
            drawHungerBar();
            if (lifetime < 301) {
                new Message("power: " + lifetime, Color.CORAL).draw(this, 20, 3);
            }
        }
    }

    private void drawHungerBar() {
        for (int x = 0; x < 20; x++) {
            Color barColor;
            if (100 - snake.getHunger() > 50) {
                barColor = Color.GREEN;
            } else if (100 - snake.getHunger() > 25) {
                barColor = Color.YELLOW;
            } else {
                barColor = Color.RED;
            }
            barColor = ((100 - snake.getHunger()) / 5 <= x ? Color.BLACK : barColor);
            setCellColor(x + 10, 0, barColor);
        }

    }

    void drawMap() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                map.getLayout()[y][x].draw(this);
            }
        }
    }

    private void drawOrbs() {
        for (Orb o : orbs) {
            o.draw(this);
        }
    }

    private void drawSnake() {
        if (Screen.is(Screen.Type.GAME)) {
            snake.draw();
        }
    }

    public void drawElementsPanel() {
        Color textColor;
        Color bgColor;
        for (Element element : Element.values()) {
            textColor = snake.canUse(element) ? Color.WHITE : Color.DARKSLATEGRAY;
            switch (element) {
                case NEUTRAL:
                    bgColor = snake.getElement() == element ? Color.PURPLE : Color.BLACK;
                    setCellValueEx(24, 2, bgColor, "N", textColor, 90);
                    break;
                case WATER:
                    bgColor = snake.getElement() == element ? Color.BLUE : Color.BLACK;
                    setCellValueEx(25, 2, bgColor, "W", textColor, 90);
                    break;
                case FIRE:
                    bgColor = snake.getElement() == element ? Color.RED : Color.BLACK;
                    setCellValueEx(26, 2, bgColor, "F", textColor, 90);
                    break;
                case EARTH:
                    bgColor = snake.getElement() == element ? Color.BROWN : Color.BLACK;
                    setCellValueEx(27, 2, bgColor, "E", textColor, 90);
                    break;
                case AIR:
                    bgColor = snake.getElement() == element ? Color.LIGHTSKYBLUE : Color.BLACK;
                    setCellValueEx(28, 2, bgColor, "A", textColor, 90);
                    break;
                case ALMIGHTY:
                    bgColor = snake.getElement() == element ? Color.ORCHID : Color.BLACK;
                    setCellValueEx(29, 2, bgColor, "S", textColor, 90);
                    break;
                default:
                    break;
            }
        }
    }

    // UTILITY & CHECKS

    public int getSpeed() {
        return Math.max((SnakeGame.MAX_TURN_DELAY - (snake.getLength() * 10)), 100);
    }

    public void pause() {
        isPaused = !isPaused;
        if (isPaused) {
            stopTurnTimer();
            new Message("             ", Color.WHITE).draw(this, 15);
            new Message(" SLEEPING... ", Color.WHITE).draw(this, 16);
            new Message("             ", Color.WHITE).draw(this, 17);
        } else {
            setTurnTimer(turnDelay);
        }
    }

    private long calculatePoints() {
        if (points > 0) {
            long stageTimePassed = (new Date().getTime() - stageStartDate.getTime()) / 1000;
            return Math.max(300 - stageTimePassed, 0);
        } else return 0;
    }

    private boolean isBadPlaceForOrb(int x, int y) {
        if (snake.canUse(Element.WATER) || snake.canUse(Element.ALMIGHTY)) {
            return (snake.checkCollision(neutralOrb)
                    || (map.getLayoutNode(x, y).getTerrain() != Node.Terrain.FIELD
                    && map.getLayoutNode(x, y).getTerrain() != Node.Terrain.WATER
                    && map.getLayoutNode(x, y).getTerrain() != Node.Terrain.WOOD));
        } else {
            return (snake.checkCollision(neutralOrb)
                    || map.getLayoutNode(x, y).getTerrain() != Node.Terrain.FIELD);
        }
    }

    boolean outOfBounds(int x, int y) {
        return (x < 0 || y < 4 || x > WIDTH - 1 || y > HEIGHT - 1);
    }


    // CONTROLS

    @Override
    public void onKeyPress(Key key) {
        ie.keyPress(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        ie.keyRelease(key);
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        ie.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        ie.rightClick(x, y);
    }

    // GETTERS

    int getSnakeLength() {
        return snakeLength;
    }

    int getLifetime() {
        return lifetime;
    }

    boolean isStopped() {
        return isStopped;
    }

    public Snake getSnake() {
        return snake;
    }

    Menu getMenu() {
        return menu;
    }

    int getStage() {
        return stage;
    }

    Map getMap() {
        return map;
    }


    // SETTERS

    void setGameOverReason(String reason) {
        this.gameOverReason = reason;
    }

    void setScore(int score, boolean add) {
        if (add) {
            this.score += score;
        } else {
            this.score = score;
        }
    }

    void setTurnDelay(int turnDelay) {
        // Sets specific turn delay
        this.turnDelay = turnDelay;
    }

    void setTurnDelay() {
        // Sets normal turn delay
        this.turnDelay = getSpeed();
    }

    void setStage(int stage) {
        this.stage = stage;
    }

    void setMap(int stage) {
        this.map = new Map(stage, this);
    }

    // MODIFIERS

    void decreaseLifetime() {
        this.lifetime -= 1;
    }
}
