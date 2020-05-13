package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.*;

import java.util.ArrayList;
import java.util.Collections;

import static com.javarush.games.snake.Triggers.*;

public class SnakeGame extends Game {
    // Global parameters
    private static final String VERSION = "1.05";
    static final int WIDTH = 32;
    static final int HEIGHT = 32;
    static final int MAX_TURN_DELAY = 300;

    // Game flow parameters
    private String currentTask;
    private String gameOverReason;
    private int turnDelay;
    private int snakeLength;
    private int score;
    private int lifetime;
    private boolean isStopped;

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
        Signs.setSigns(Graphics.KANJI);
        createGame();
    }

    private void createGame() { // reset values for new game
        if (firstLaunch) {
            displayHelp();
        } else {
            score = 0;
            lifetime = 301;
            setScore(score);
            snake = new Snake(2, 27, this);
            snakeLength = snake.getLength();
            map = new Map(Map.pattern, this);
            orbs = new ArrayList<>();
            createNeutralOrb();
            createElementalOrbs();
            Triggers.reset();
            turnDelay = MAX_TURN_DELAY;
            setTurnTimer(turnDelay);
            isStopped = false;
            currentTask = "Reach water orb!";
            drawScene();
        }
    }


    // GAME END

    private void win() {
        stopTurnTimer();
        drawScene();
        isStopped = true;
        showMessageDialog(Color.YELLOW, "YOU WIN!\nScore: " + score, Color.GREEN, 27);
    }

    private void gameOver() {
        stopTurnTimer();
        drawScene();
        isStopped = true;
        showMessageDialog(Color.YELLOW, gameOverReason + "\nScore: " + score, Color.RED, 27);
    }


    // GAME MECHANICS

    public void onTurn(int step) {
        if (firstLaunch) {
            drawScene();
            return;
        }
        snake.move();
        for (Orb o : orbs) snake.orbInteract(o);
        processOrb(neutralOrb);
        processOrb(waterOrb);
        processOrb(fireOrb);
        processOrb(earthOrb);
        processOrb(airOrb);
        processOrb(almightyOrb);
        if (!snake.isAlive) gameOver();
        if (lifetime <= 0) win();
        snakeLength = snake.getLength();
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
                    setScore(score);
                }
                break;
            case WATER:
                if (!orb.isAlive && !waterOrbObtained) {
                    waterOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.WATER);
                    do {
                        snake.swapNextElement();
                    } while (snake.getElement() != Element.WATER);
                    score += 25;
                    setScore(score);
                    currentTask = "Get fire orb!";
                }
                break;
            case FIRE:
                if (!orb.isAlive && !fireOrbObtained) {
                    fireOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.FIRE);
                    do {
                        snake.swapNextElement();
                    } while (snake.getElement() != Element.FIRE);
                    score += 35;
                    setScore(score);
                    currentTask = "Take earth orb!";
                }
                break;
            case EARTH:
                if (!orb.isAlive && !earthOrbObtained) {
                    earthOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.EARTH);
                    do {
                        snake.swapNextElement();
                    } while (snake.getElement() != Element.EARTH);
                    score += 45;
                    setScore(score);
                    currentTask = "Take air, fly!";
                }
                break;
            case AIR:
                if (!orb.isAlive && !airOrbObtained) {
                    airOrbObtained = true;
                    orbs.remove(orb);
                    Collections.sort(snake.getElementsAvailable());
                    snake.getElementsAvailable().add(Element.AIR);
                    do {
                        snake.swapNextElement();
                    } while (snake.getElement() != Element.AIR);
                    score += 55;
                    setScore(score);
                    currentTask = "Become almighty!";
                }
                break;
            case ALMIGHTY:
                if (!orb.isAlive && !almightyOrbObtained) {
                    almightyOrbObtained = true;
                    orbs.remove(orb);
                    snake.getElementsAvailable().clear();
                    snake.getElementsAvailable().add(Element.ALMIGHTY);
                    do {
                        snake.swapNextElement();
                    } while (snake.getElement() != Element.ALMIGHTY);
                    score += 200;
                    setScore(score);
                    currentTask = "Eat! Eat everything!";
                }
                break;
        }
    }


    // OBJECT CREATIONS

    private void createNeutralOrb() {
        int x, y;
        if (snake.getElementsAvailable().contains(Element.WATER)) { // can place orbs on field and water
            do {
                x = getRandomNumber(WIDTH);
                y = getRandomNumber(HEIGHT - 4) + 4;
                neutralOrb = new Orb(x, y, Element.NEUTRAL);
            } while (snake.checkCollision(neutralOrb)
                    || (map.getLayoutNode(x, y).getTerrain() != Terrain.FIELD
                    && map.getLayoutNode(x, y).getTerrain() != Terrain.WATER
                    && map.getLayoutNode(x, y).getTerrain() != Terrain.WOOD));
        } else // can place orbs on field only
            do {
                x = getRandomNumber(WIDTH);
                y = getRandomNumber(HEIGHT - 4) + 4;
                neutralOrb = new Orb(x, y, Element.NEUTRAL);
            } while (snake.checkCollision(neutralOrb)
                    || map.getLayoutNode(x, y).getTerrain() != Terrain.FIELD);
        orbs.add(neutralOrb);
    }

    private void createElementalOrbs() {
        waterOrb = new Orb(3, 7, Element.WATER);
        fireOrb = new Orb(27, 27, Element.FIRE);
        earthOrb = new Orb(16, 18, Element.EARTH);
        airOrb = new Orb(18, 8, Element.AIR);
        almightyOrb = new Orb(24, 9, Element.ALMIGHTY);

        orbs.add(waterOrb);
        orbs.add(fireOrb);
        orbs.add(earthOrb);
        orbs.add(airOrb);
        orbs.add(almightyOrb);
    }


    // VISUALS

    private void drawScene() { // draw terrain, orbs, snake, interface
        for (int x = 0; x < WIDTH; x++) for (int y = 0; y < HEIGHT; y++) map.getLayout()[y][x].draw(this);
        for (Orb o : orbs) o.draw(this);
        if (!firstLaunch) {
            snake.draw(this);
            drawInterface();
        }
    }

    private void drawInterface() {
        new DockMessage("strength: " + (snake.getLength()), Color.WHITE).draw(this, 0, 0);
        new DockMessage("hunger: " + snake.getHunger() + "%", Color.WHITE).draw(this, 19, 0);
        new DockMessage("element : " + (snake.getElementsAvailable().get(0)), Color.YELLOW).draw(this, 0, 1);
        new DockMessage("task    : " + currentTask, Color.LIGHTGREEN).draw(this, 0, 2);
        new DockMessage("score   : " + score, Color.LIGHTBLUE).draw(this, 0, 3);
        if (lifetime < 301) {
            new DockMessage("time: " + lifetime, Color.CORAL).draw(this, 20, 3);
        }
    }

    private void displayHelp() {
        orbs = new ArrayList<>();
        map = new Map(Map.patternBlank, this);
        neutralOrb = new Orb(16, 9, Element.NEUTRAL);
        waterOrb = new Orb(1, 9, Element.WATER);
        fireOrb = new Orb(1, 11, Element.FIRE);
        earthOrb = new Orb(1, 13, Element.EARTH);
        airOrb = new Orb(1, 15, Element.AIR);
        almightyOrb = new Orb(1, 17, Element.ALMIGHTY);
        orbs.add(waterOrb);
        orbs.add(fireOrb);
        orbs.add(earthOrb);
        orbs.add(airOrb);
        orbs.add(almightyOrb);
        orbs.add(neutralOrb);
        drawScene();

        new DockMessage("ALCHEMY SNAKE VER " + VERSION, Color.LIGHTGREEN).draw(this);

        String selector1 = (Signs.currentSetting == Graphics.KANJI ? "■" : "□");
        String selector2 = (Signs.currentSetting == Graphics.EMOJI ? "■" : "□");
        new DockMessage("SELECT ICONS: " + selector1 + " KANJI", Color.SKYBLUE).draw(this, 3);
        new DockMessage(" (UP, DOWN)   " + selector2 + " EMOJI", Color.SKYBLUE).draw(this, 5);

        new DockMessage("COLLECT THESE TO WIN:", Color.YELLOW).draw(this, 7);
        new DockMessage("WATER ORB", Color.WHITE).draw(this, 3, 9);
        new DockMessage("ORB (FOOD)", Color.WHITE).draw(this, 18, 9);
        new DockMessage("FIRE ORB", Color.WHITE).draw(this, 3, 11);
        new DockMessage("EARTH ORB", Color.WHITE).draw(this, 3, 13);
        new DockMessage("AIR ORB", Color.WHITE).draw(this, 3, 15);
        new DockMessage("ALMIGHTY ORB", Color.WHITE).draw(this, 3, 17);
        new DockMessage("CONTROLS:", Color.YELLOW).draw(this, 19);
        new DockMessage("↑ ↓ → ←       : DIRECTION", Color.WHITE).draw(this, 1, 21);
        new DockMessage("ENTER, L-CLICK: NEXT ELEMENT", Color.WHITE).draw(this, 1, 23);
        new DockMessage("ESC,   R-CLICK: PREV ELEMENT", Color.WHITE).draw(this, 1, 25);
        new DockMessage("SPACE         : NEW GAME", Color.WHITE).draw(this, 1, 27);
        new DockMessage("PRESS SPACE TO START", Color.PINK).draw(this, 30);
    }


    // CONTROLS

    @Override
    public void onKeyPress(Key key) {
        if (firstLaunch) {
            switch (key) {
                case SPACE:
                    firstLaunch = false;
                    createGame();
                    break;
                case UP:
                    Signs.setSigns(Graphics.KANJI);
                    displayHelp();
                    break;
                case DOWN:
                    Signs.setSigns(Graphics.EMOJI);
                    displayHelp();
                    break;
                default:
                    break;
            }
        } else if (!isStopped) {
            if (speedUpDelay) {
                turnDelay = Math.max((MAX_TURN_DELAY - (snake.getLength() * 10)), 100);
                Triggers.speedUpDelay = false;
            } else turnDelay = 50;
            switch (key) {
                case UP:
                    snake.setDirection(Direction.UP);
                    break;
                case RIGHT:
                    snake.setDirection(Direction.RIGHT);
                    break;
                case DOWN:
                    snake.setDirection(Direction.DOWN);
                    break;
                case LEFT:
                    snake.setDirection(Direction.LEFT);
                    break;
                case ENTER:
                    snake.swapNextElement();
                    break;
                case ESCAPE:
                    snake.swapPreviousElement();
                    break;
                default:
                    break;
            }
        } else {
            if (key == Key.SPACE) createGame();
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (!firstLaunch) {
            Triggers.speedUpDelay = true;
            turnDelay = Math.max((MAX_TURN_DELAY - (snake.getLength() * 10)), 100);
        }
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (!firstLaunch) snake.swapNextElement();
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        if (!firstLaunch) snake.swapPreviousElement();
    }

    // GETTERS

    int getSnakeLength() {
        return snakeLength;
    }

    int getLifetime() {
        return lifetime;
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
        this.turnDelay = turnDelay;
    }


    // MODIFIERS

    void decreaseLifetime() {
        this.lifetime -= 1;
    }
}
