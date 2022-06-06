package com.javarush.games.snake.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.view.Sign;
import com.javarush.games.snake.view.impl.GameFieldView;

import java.util.Date;
import java.util.LinkedList;

public class Snake {
    private static final int MIN_X = 0;
    private static final int MIN_Y = 4;
    private static final int MAX_X = SnakeGame.SIZE - 1;
    private static final int MAX_Y = SnakeGame.SIZE - 1;
    private static final int MIN_LENGTH = 3;

    private final SnakeGame game;
    private final LinkedList<GameObject> snakeParts;
    private final LinkedList<Element> availableElements;
    private Element element;
    private Direction direction;
    private GameObject newHead;
    private Color[] bodyColors;
    private Date starvingTimestamp;
    private int breath;
    private int hunger;
    private int almightyPower;
    private boolean isAlive;
    private boolean canChangeElement;

    public Snake(SnakeGame game, int x, int y, Direction direction) {
        this.game = game;
        this.direction = direction;

        bodyColors = new Color[2];
        availableElements = new LinkedList<>();
        snakeParts = new LinkedList<>();
        hunger = 0;
        almightyPower = 301;
        starvingTimestamp = new Date();
        isAlive = true;
        canChangeElement = true;
        learnElement(Element.NEUTRAL);
        setElement(Element.NEUTRAL);
        addParts(x, y, direction);

        // learnAllElementsForDebug();
    }

    private void learnAllElementsForDebug() {
        learnElement(Element.WATER);
        learnElement(Element.FIRE);
        learnElement(Element.EARTH);
        learnElement(Element.AIR);
        learnElement(Element.ALMIGHTY);
    }

    public void learnElement(Element element) {
        if (availableElements.contains(element)) return;
        availableElements.add(element);
    }

    private void setElement(Element element) {
        this.element = element;
        bodyColors = changeBodyColor(element);
    }

    private Color[] changeBodyColor(Element element) {
        switch (element) {
            case NEUTRAL:
                return new Color[]{Color.DARKOLIVEGREEN, Color.DARKGREEN};
            case WATER:
                return new Color[]{Color.BLUE, Color.DARKBLUE};
            case FIRE:
                return new Color[]{Color.YELLOW, Color.RED};
            case EARTH:
                return new Color[]{Color.DARKORANGE, Color.BROWN};
            case AIR:
                return new Color[]{Color.LIGHTGRAY, Color.LIGHTSKYBLUE};
            case DEAD:
                return new Color[]{Color.DARKSLATEGREY, Color.BLACK};
            case ALMIGHTY:
                return new Color[]{Color.ORCHID, Color.DEEPPINK};
            default:
                return new Color[]{Color.NONE, Color.NONE};
        }
    }

    private void addParts(int x, int y, Direction direction) {
        int dx = 0;
        int dy = 0;

        if (direction == Direction.UP) dy = 1;
        if (direction == Direction.DOWN) dy = -1;
        if (direction == Direction.RIGHT) dx = -1;
        if (direction == Direction.LEFT) dx = 1;

        for (int i = 0; i < MIN_LENGTH; i++) {
            snakeParts.addLast(new GameObject(x + dx * i, y + dy * i));
        }
    }

    public void draw() {
        final String headSign = isAlive ? Sign.getSign(Sign.SNAKE_HEAD) : Sign.getSign(Sign.SNAKE_DEAD);
        final String bodySign = Sign.getSign(Sign.SNAKE_BODY);

        for (int i = snakeParts.size() - 1; i >= 0; i--) {
            final int x = snakeParts.get(i).x;
            final int y = snakeParts.get(i).y;

            if (i == 0) {
                game.setCellValueEx(x, y, bodyColors[1], headSign, Color.WHITE, 90);
            } else {
                game.setCellValueEx(x, y, bodyColors[i % 2], bodySign, Color.WHITE, 90);
            }
        }
    }


    public void move() {
        newHead = createNewHead();

        checkEscapeBorders();
        checkBiteSelf();
        interactWithTerrain();

        if (isAlive) {
            snakeParts.addFirst(newHead);
            snakeParts.removeLast();
            starve();
            canChangeElement = true;
        } else {
            setElement(Element.DEAD);
        }
    }

    private GameObject createNewHead() {
        GameObject head = snakeParts.get(0);
        GameObject newHead;

        int dx = 0;
        int dy = 0;

        if (direction == Direction.UP) dy = -1;
        if (direction == Direction.DOWN) dy = 1;
        if (direction == Direction.RIGHT) dx = 1;
        if (direction == Direction.LEFT) dx = -1;

        newHead = new GameObject(head.x + dx, head.y + dy);

        if (element == Element.ALMIGHTY) {
            if (newHead.x > MAX_X) newHead.x = MIN_X;
            if (newHead.x < MIN_X) newHead.x = MAX_X;
            if (newHead.y > MAX_Y) newHead.y = MIN_Y;
            if (newHead.y < MIN_Y) newHead.y = MAX_Y;
        }

        return newHead;
    }

    private void checkEscapeBorders() {
        if (newHead.x < MIN_X || newHead.x > MAX_X || newHead.y < MIN_Y || newHead.y > MAX_Y) {
            kill();
            game.setGameOverReason(Strings.GAME_OVER_RUNAWAY);
        }
    }

    private void checkBiteSelf() {
        if (element == Element.ALMIGHTY) return;

        if (collidesWithObject(newHead)) {
            kill();
            game.setGameOverReason(Strings.GAME_OVER_SELF_BITTEN);
        }
    }

    public void kill() {
        isAlive = false;
    }

    private void interactWithTerrain() {
        if (SnakeGame.outOfBounds(newHead.x, newHead.y)) return;

        final Stage currentStage = game.getStage();
        Terrain terrain = currentStage.getTerrainMatrix()[newHead.y][newHead.x];
        terrain.interact(this);
    }

    private void starve() {
        if (new Date().getTime() - starvingTimestamp.getTime() <= 300) return;
        if (element == Element.ALMIGHTY) return;

        starvingTimestamp = new Date();
        hunger += getLength() / 5;

        if (hunger > 100) {
            loseTail();
            Score.remove(5);
            hunger = 0;
        }
    }

    public void loseTail() {
        if (snakeParts.size() <= MIN_LENGTH) return;
        snakeParts.removeLast();
    }

    public void eat() {
        hunger = 0;
        elongateTail();
    }

    public void elongateTail() {
        GameObject tail = snakeParts.getLast();
        GameObject newTail = new GameObject(tail.x, tail.y);
        snakeParts.addLast(newTail);
        breath++;
    }

    public boolean headIsNotTouchingOrb(Orb orb) {
        return (newHead.x != orb.x) || (newHead.y != orb.y) || (element == Element.AIR);
    }

    public void forceRotationToElement(Element element) {
        do {
            rotateElement(Direction.RIGHT);
        } while (getElement() != element);

        canChangeElement = false;
    }

    public void rotateElement(Direction direction) {
        if (game.isStopped()) return;
        if (!canChangeElement) return;

        if (direction == Direction.RIGHT) {
            availableElements.addLast(availableElements.removeFirst());
            setElement(availableElements.getFirst());
        } else if (direction == Direction.LEFT) {
            setElement(availableElements.getLast());
            availableElements.addFirst(availableElements.removeLast());
        }

        GameFieldView.getInstance().drawElementsPanel();
        draw();
    }

    public boolean collidesWithObject(GameObject obj) {
        return snakeParts.stream()
                .anyMatch(part -> part.x == obj.x && part.y == obj.y);
    }

    public LinkedList<Element> getAvailableElements() {
        return availableElements;
    }

    public void setDirection(Direction command) {
        // These guard clauses protect from making a self-eating loop within 1 turn by spamming directional keys
        if ((command == Direction.LEFT || command == Direction.RIGHT) && isMovingHorizontally()) return;
        if ((command == Direction.DOWN || command == Direction.UP) && isMovingVertically()) return;

        this.direction = command;
    }

    private boolean isMovingVertically() {
        return snakeParts.get(0).x == snakeParts.get(1).x;
    }

    private boolean isMovingHorizontally() {
        return snakeParts.get(0).y == snakeParts.get(1).y;
    }

    public boolean canUseElement(Element element) {
        return (this.availableElements.contains(element));
    }

    public void clearElements() {
        this.availableElements.clear();
    }

    public void decreaseAlmightyPower() {
        almightyPower--;
    }

    public void reduceBreath() {
        breath--;
    }

    public void takeFullBreath() {
        breath = getLength();
    }

    public int getLength() {
        return snakeParts.size();
    }

    public boolean isDrowned() {
        return breath <= -1;
    }

    public Element getElement() {
        return element;
    }

    public int getHunger() {
        return hunger;
    }

    public int getAlmightyPower() {
        return almightyPower;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public GameObject getNewHead() {
        return newHead;
    }
}