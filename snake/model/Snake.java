package com.javarush.games.snake.model;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.view.Drawable;
import com.javarush.games.snake.view.Sign;
import com.javarush.games.snake.view.impl.GameFieldView;

import java.util.Date;
import java.util.LinkedList;

public class Snake implements Drawable {
    public static final int MAX_HUNGER = 100;
    public static final int FULL_ALMIGHTY_POWER = 301;

    private static final int MIN_X = SnakeGame.PLAYFIELD_MIN_X;
    private static final int MIN_Y = SnakeGame.PLAYFIELD_MIN_Y;
    private static final int MAX_X = SnakeGame.PLAYFIELD_MAX_X;
    private static final int MAX_Y = SnakeGame.PLAYFIELD_MAX_Y;
    private static final int MIN_LENGTH = 3;
    private static final int SEGMENT_TEXT_SIZE = 90;
    private static final int STARVATION_INTERVAL_MS = 300;
    private static final int SEGMENTS_PER_HUNGER_POINT = 5;
    private static final int TAIL_LOSS_SCORE_PENALTY = 5;

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

        availableElements = new LinkedList<>();
        snakeParts = new LinkedList<>();
        almightyPower = FULL_ALMIGHTY_POWER;
        starvingTimestamp = new Date();
        isAlive = true;
        canChangeElement = true;
        learnElement(Element.NEUTRAL);
        setElement(Element.NEUTRAL);
        addInitialParts(x, y, direction);
    }

    public final void learnElement(Element element) {
        if (availableElements.contains(element)) return;
        availableElements.add(element);
    }

    private void setElement(Element element) {
        this.element = element;
        bodyColors = bodyColorsFor(element);
    }

    private Color[] bodyColorsFor(Element element) {
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

    private void addInitialParts(int x, int y, Direction direction) {
        for (int i = 0; i < MIN_LENGTH; i++) {
            snakeParts.addLast(new GameObject(
                    x - direction.getDx() * i,
                    y - direction.getDy() * i));
        }
    }

    @Override
    public void draw(Game target) {
        final String headSign = isAlive ? Sign.getSign(Sign.SNAKE_HEAD) : Sign.getSign(Sign.SNAKE_DEAD);
        final String bodySign = Sign.getSign(Sign.SNAKE_BODY);

        for (int i = snakeParts.size() - 1; i >= 0; i--) {
            final int x = snakeParts.get(i).x;
            final int y = snakeParts.get(i).y;

            if (i == 0) {
                target.setCellValueEx(x, y, bodyColors[1], headSign, Color.WHITE, SEGMENT_TEXT_SIZE);
            } else {
                target.setCellValueEx(x, y, bodyColors[i % 2], bodySign, Color.WHITE, SEGMENT_TEXT_SIZE);
            }
        }
    }

    public void move() {
        newHead = createNewHead();

        checkBorders();
        checkBiteSelf();

        if (!isAlive) return;

        interactWithTerrain();

        if (!isAlive) return;

        snakeParts.addFirst(newHead);
        snakeParts.removeLast();
        applyStarvation();
        canChangeElement = true;
    }

    private GameObject createNewHead() {
        final GameObject head = snakeParts.get(0);
        final GameObject headCandidate = new GameObject(
                head.x + direction.getDx(),
                head.y + direction.getDy());

        if (element == Element.ALMIGHTY) {
            wrapAroundField(headCandidate);
        }

        return headCandidate;
    }

    private void wrapAroundField(GameObject headCandidate) {
        if (headCandidate.x > MAX_X) headCandidate.x = MIN_X;
        if (headCandidate.x < MIN_X) headCandidate.x = MAX_X;
        if (headCandidate.y > MAX_Y) headCandidate.y = MIN_Y;
        if (headCandidate.y < MIN_Y) headCandidate.y = MAX_Y;
    }

    private void checkBorders() {
        if (SnakeGame.isOutOfBounds(newHead.x, newHead.y)) {
            kill();
            game.setGameOverReason(Strings.GAME_OVER_RUNAWAY);
        }
    }

    private void checkBiteSelf() {
        if (element == Element.ALMIGHTY) return;

        // The last segment leaves its cell during this very turn, so it must not be checked.
        for (int i = 0; i < snakeParts.size() - 1; i++) {
            GameObject part = snakeParts.get(i);

            if (part.x == newHead.x && part.y == newHead.y) {
                kill();
                game.setGameOverReason(Strings.GAME_OVER_SELF_BITTEN);
                return;
            }
        }
    }

    public void kill() {
        isAlive = false;
        setElement(Element.DEAD);
    }

    private void interactWithTerrain() {
        if (SnakeGame.isOutOfBounds(newHead.x, newHead.y)) return;

        game.getStage().getTerrain(newHead.x, newHead.y).interact(this);
    }

    private void applyStarvation() {
        if (element == Element.ALMIGHTY) return;
        if (!starvationIntervalPassed()) return;

        starvingTimestamp = new Date();
        hunger += getLength() / SEGMENTS_PER_HUNGER_POINT;

        if (hunger > MAX_HUNGER) {
            loseTail();
            game.getScore().remove(TAIL_LOSS_SCORE_PENALTY);
            hunger = 0;
        }
    }

    private boolean starvationIntervalPassed() {
        return new Date().getTime() - starvingTimestamp.getTime() > STARVATION_INTERVAL_MS;
    }

    public void loseTail() {
        if (snakeParts.size() <= MIN_LENGTH) return;
        snakeParts.removeLast();
    }

    public void eat() {
        hunger = 0;
        elongateTail();
    }

    private void elongateTail() {
        final GameObject tail = snakeParts.getLast();
        snakeParts.addLast(new GameObject(tail.x, tail.y));
        breath++;
    }

    public boolean isHeadTouchingOrb(Orb orb) {
        if (newHead == null) return false;

        return newHead.x == orb.x && newHead.y == orb.y && element != Element.AIR;
    }

    public void switchToElement(Element target) {
        if (!availableElements.contains(target)) {
            availableElements.add(target);
        }

        // The loop always finishes: the target is guaranteed to be in the non-empty list.
        while (availableElements.getFirst() != target) {
            availableElements.addLast(availableElements.removeFirst());
        }

        setElement(target);
        canChangeElement = false;
    }

    public void rotateElement(Direction rotation) {
        if (game.isStopped() || !canChangeElement) return;

        if (rotation == Direction.RIGHT) {
            availableElements.addLast(availableElements.removeFirst());
            setElement(availableElements.getFirst());
        } else if (rotation == Direction.LEFT) {
            setElement(availableElements.getLast());
            availableElements.addFirst(availableElements.removeLast());
        }

        GameFieldView.getInstance().drawElementsPanel();
        draw(game);
    }

    public boolean occupiesCellOf(GameObject object) {
        return snakeParts.stream()
                .anyMatch(part -> part.x == object.x && part.y == object.y);
    }

    public void setDirection(Direction requestedDirection) {
        // These guards prevent reversing into the snake's own body within one turn.
        if (isHorizontalDirection(requestedDirection) && isMovingHorizontally()) return;
        if (isVerticalDirection(requestedDirection) && isMovingVertically()) return;

        this.direction = requestedDirection;
    }

    private boolean isHorizontalDirection(Direction direction) {
        return direction == Direction.LEFT || direction == Direction.RIGHT;
    }

    private boolean isVerticalDirection(Direction direction) {
        return direction == Direction.UP || direction == Direction.DOWN;
    }

    private boolean isMovingVertically() {
        return snakeParts.get(0).x == snakeParts.get(1).x;
    }

    private boolean isMovingHorizontally() {
        return snakeParts.get(0).y == snakeParts.get(1).y;
    }

    public boolean canUseElement(Element element) {
        return availableElements.contains(element);
    }

    public void clearElements() {
        availableElements.clear();
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
        return breath < 0;
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
