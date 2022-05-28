package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.snake.controller.Controller;
import com.javarush.games.snake.model.*;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;
import com.javarush.games.snake.view.SignType;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.Phase;

import java.util.ArrayList;
import java.util.Date;


public class SnakeGame extends Game {
    // Global parameters
    private static SnakeGame instance;
    public static final int SIZE = 32;
    private static final int MAX_TURN_DELAY = 300;
    private Controller controller;
    public Menu menu;

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
    public boolean isAccelerationEnabled;
    public boolean needShortPauseBeforeSpeedUp;

    // Game objects
    public Snake snake;
    public Map map;
    public Orb neutralOrb;
    public ArrayList<Orb> orbs;

    // SETUP

    public void initialize() {
        showGrid(false);
        setScreenSize(SIZE, SIZE);
        SnakeGame.instance = this;
        controller = new Controller();
        Sign.setUsedType(SignType.KANJI);

        menu = new Menu();
        MenuSelector.setPointerPosition(0);
        menu.displayMain();
        stage = 0;
        isAccelerationEnabled = true;
    }

    public final void createGame() {
        // Make new objects
        map = new Map(stage, this);
        snake = new Snake(map.snakeStartPlace.x, map.snakeStartPlace.y, this, map.snakeStartDirection);
        orbs = new ArrayList<>();
        importElementalOrbs();
        createNeutralOrb();

        // Initialize values
        snakeLength = snake.getLength();
        score = 0;
        lifetime = 301;

        // Launch
        isStopped = false;
        isPaused = false;
        needShortPauseBeforeSpeedUp = false;
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

        ArrayList<Orb> orbsCopy = new ArrayList<>(orbs);
        orbsCopy.forEach(orb -> {
            snake.interactWithOrb(orb);
            processOrb(orb);
        });

        if (!snake.isAlive) gameOver();
        if (lifetime <= 0) win();
        setTurnTimer(turnDelay);
        drawScene();
    }

    private void processOrb(Orb orb) {
        if (orb.isAlive) return;
        if (orb.element == Element.NEUTRAL) {
            orbs.remove(orb);
            createNeutralOrb();
            score += points / 10;
        } else {
            if (!orb.isObtained) {
                orb.isObtained = true;
                orbs.remove(orb);
                if (orb.element == Element.ALMIGHTY) {
                    snake.clearElements();
                    menu.selectStageUp();
                }
                snake.getElementsAvailable().add(orb.element);
                do {
                    System.out.println("omg");
                    snake.rotateToNextElement(this);
                } while (snake.getElement() != orb.element);
                snakeLength = snake.getLength();
                snake.canChangeElement = false;
                score += points;
            }
        }
    }


    // OBJECT CREATIONS

    private void createNeutralOrb() {
        int x;
        int y;
        do {
            x = getRandomNumber(SIZE);
            y = getRandomNumber(SIZE - 4) + 4;
            neutralOrb = new Orb(x, y, Element.NEUTRAL);
        } while (isBadPlaceForOrb(x, y));
        orbs.add(neutralOrb);
    }

    private void importElementalOrbs() {
        Orb waterOrb = map.orbs.get(0);
        Orb fireOrb = map.orbs.get(1);
        Orb earthOrb = map.orbs.get(2);
        Orb airOrb = map.orbs.get(3);
        Orb almightyOrb = map.orbs.get(4);
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
        if (Phase.is(Phase.GAME_FIELD)) {
            Message.print(0, 0, "hunger  : ", Color.CORAL);
            Message.print(0, 1, "strength: " + (snake.getLength()), Color.WHITE);
            Message.print(0, 2, "element : " + (snake.getElementsAvailable().get(0)), Color.YELLOW);
            Message.print(0, 3, "score   : " + score, Color.LIGHTBLUE);
            drawElementsPanel();
            drawHungerBar();
            if (lifetime < 301) {
                Message.print(20, 3, "power: " + lifetime, Color.CORAL);
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

    public void drawMap() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
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
        if (Phase.is(Phase.GAME_FIELD)) {
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
            Message.print(-1, 15, "             ", Color.WHITE);
            Message.print(-1, 16, " SLEEPING... ", Color.WHITE);
            Message.print(-1, 17, "             ", Color.WHITE);
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

    public boolean outOfBounds(int x, int y) {
        return (x < 0 || y < 4 || x > SIZE - 1 || y > SIZE - 1);
    }


    // GETTERS

    public int getSnakeLength() {
        return snakeLength;
    }

    public int getLifetime() {
        return lifetime;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public Snake getSnake() {
        return snake;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getStage() {
        return stage;
    }

    public Map getMap() {
        return map;
    }


    // SETTERS

    public void setGameOverReason(String reason) {
        this.gameOverReason = reason;
    }

    public void setScore(int score, boolean modify) {
        if (modify) {
            this.score += score;
        } else {
            this.score = score;
        }
    }

    public void setTurnDelay(int turnDelay) {
        // Sets specific turn delay
        this.turnDelay = turnDelay;
    }

    public void setTurnDelay() {
        // Sets normal turn delay
        this.turnDelay = getSpeed();
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setMap(int stage) {
        this.map = new Map(stage, this);
    }

    // MODIFIERS

    public void decreaseLifetime() {
        this.lifetime -= 1;
    }

    public static SnakeGame getInstance() {
        return instance;
    }

    /*
     * Controls
     */

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        controller.rightClick(x, y);
    }
}
