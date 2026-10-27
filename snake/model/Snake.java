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

/** Змейка: сегменты, стихия, движение и взаимодействие с полем. */
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
    private static final Color SHADOW_COLOR = Color.DIMGRAY;

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

    /** Создаёт змейку из трёх сегментов в заданной точке. */
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

    /** Добавляет стихию в доступные. */
    public final void learnElement(Element element) {
        if (availableElements.contains(element)) return;
        availableElements.add(element);
    }

    /** Делает стихию текущей и обновляет цвета. */
    private void setElement(Element element) {
        this.element = element;
        bodyColors = bodyColorsFor(element);
    }

    /** Возвращает цвета тела для стихии. */
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

    /** Добавляет стартовые сегменты позади головы. */
    private void addInitialParts(int x, int y, Direction direction) {
        for (int i = 0; i < MIN_LENGTH; i++) {
            snakeParts.addLast(new GameObject(
                    x - direction.getDx() * i,
                    y - direction.getDy() * i));
        }
    }

    /** Рисует сегменты змейки в клетках поля. */
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

    /** Рисует тень под летящей змейкой. */
    public void drawShadow(Game target) {
        if (!isFlying()) return;

        for (GameObject part : snakeParts) {
            final int shadowY = part.y + 1;

            if (shadowY > SnakeGame.PLAYFIELD_MAX_Y) continue;

            target.setCellValueEx(part.x, shadowY, SHADOW_COLOR, "");
        }
    }

    /** Делает шаг: голова, проверки, местность и хвост. */
    public void move() {
        newHead = createNewHead();

        checkBorders();
        checkBiteSelf();

        if (!isAlive) return;

        interactWithTerrain();

        if (!isAlive) return;

        snakeParts.addFirst(newHead);
        markTerrainUnderBody();
        final GameObject tail = snakeParts.removeLast();

        if (!isFlying() && !SnakeGame.isOutOfBounds(tail.x, tail.y)) {
            game.getStage().getTerrain(tail.x, tail.y).onSnakeLeft();
        }

        applyStarvation();
        canChangeElement = true;
    }

    /** Отмечает клетки под телом змейки. */
    private void markTerrainUnderBody() {
        if (isFlying()) return;

        for (GameObject part : snakeParts) {
            if (SnakeGame.isOutOfBounds(part.x, part.y)) continue;

            game.getStage().getTerrain(part.x, part.y).onSnakeThrough();
        }
    }

    /** Летает ли змейка (воздушная стихия). */
    private boolean isFlying() {
        return element == Element.AIR;
    }

    /** Создаёт клетку новой головы по направлению. */
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

    /** Переносит голову на противоположный край (всемогущая). */
    private void wrapAroundField(GameObject headCandidate) {
        if (headCandidate.x > MAX_X) headCandidate.x = MIN_X;
        if (headCandidate.x < MIN_X) headCandidate.x = MAX_X;
        if (headCandidate.y > MAX_Y) headCandidate.y = MIN_Y;
        if (headCandidate.y < MIN_Y) headCandidate.y = MAX_Y;
    }

    /** Убивает змейку при выходе за поле. */
    private void checkBorders() {
        if (SnakeGame.isOutOfBounds(newHead.x, newHead.y)) {
            kill();
            game.setGameOverReason(Strings.GAME_OVER_RUNAWAY);
        }
    }

    /** Убивает змейку при укусе собственного тела. */
    private void checkBiteSelf() {
        if (element == Element.ALMIGHTY) return;

        // Последний сегмент покидает клетку в этом же ходу, поэтому его не проверяем.
        for (int i = 0; i < snakeParts.size() - 1; i++) {
            GameObject part = snakeParts.get(i);

            if (part.x == newHead.x && part.y == newHead.y) {
                kill();
                game.setGameOverReason(Strings.GAME_OVER_SELF_BITTEN);
                return;
            }
        }
    }

    /** Помечает змейку мёртвой и меняет её вид. */
    public void kill() {
        isAlive = false;
        setElement(Element.DEAD);
    }

    /** Передаёт клетку под новой головой местности. */
    private void interactWithTerrain() {
        if (SnakeGame.isOutOfBounds(newHead.x, newHead.y)) return;

        game.getStage().getTerrain(newHead.x, newHead.y).interact(this);
    }

    /** Начисляет голод и отнимает хвост при переедании. */
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

    /** Прошёл ли интервал между начислениями голода. */
    private boolean starvationIntervalPassed() {
        return new Date().getTime() - starvingTimestamp.getTime() > STARVATION_INTERVAL_MS;
    }

    /** Убирает последний сегмент, если длина позволяет. */
    public void loseTail() {
        if (snakeParts.size() <= MIN_LENGTH) return;

        final GameObject tail = snakeParts.removeLast();

        if (!isFlying() && !SnakeGame.isOutOfBounds(tail.x, tail.y)) {
            game.getStage().getTerrain(tail.x, tail.y).onSnakeLeft();
        }
    }

    /** Сбрасывает голод и удлиняет змейку. */
    public void eat() {
        hunger = 0;
        elongateTail();
    }

    /** Добавляет сегмент в хвост. */
    private void elongateTail() {
        final GameObject tail = snakeParts.getLast();
        snakeParts.addLast(new GameObject(tail.x, tail.y));
        breath++;
    }

    /** Стоит ли голова на орбе (воздушная змейка не ест). */
    public boolean isHeadTouchingOrb(Orb orb) {
        if (newHead == null) return false;

        return newHead.x == orb.x && newHead.y == orb.y && element != Element.AIR;
    }

    /** Принудительно делает стихию текущей. */
    public void switchToElement(Element target) {
        if (!availableElements.contains(target)) {
            availableElements.add(target);
        }

        // Цикл конечен: целевая стихия гарантированно есть в непустом списке.
        while (availableElements.getFirst() != target) {
            availableElements.addLast(availableElements.removeFirst());
        }

        setElement(target);
        canChangeElement = false;
    }

    /** Переключает стихию влево или вправо. */
    public void rotateElement(Direction rotation) {
        if (game.isStopped() || !canChangeElement) return;

        if (rotation == Direction.RIGHT) {
            availableElements.addLast(availableElements.removeFirst());
            setElement(availableElements.getFirst());
        } else if (rotation == Direction.LEFT) {
            setElement(availableElements.getLast());
            availableElements.addFirst(availableElements.removeLast());
        }

        GameFieldView.getInstance().update();
    }

    /** Занята ли клетка объекта каким-либо сегментом. */
    public boolean occupiesCellOf(GameObject object) {
        return snakeParts.stream()
                .anyMatch(part -> part.x == object.x && part.y == object.y);
    }

    /** Задаёт направление, не разрешая разворот. */
    public void setDirection(Direction requestedDirection) {
        // Эти проверки не дают развернуться в собственное тело за один ход.
        if (isHorizontalDirection(requestedDirection) && isMovingHorizontally()) return;
        if (isVerticalDirection(requestedDirection) && isMovingVertically()) return;

        this.direction = requestedDirection;
    }

    /** Горизонтальное ли направление. */
    private boolean isHorizontalDirection(Direction direction) {
        return direction == Direction.LEFT || direction == Direction.RIGHT;
    }

    /** Вертикальное ли направление. */
    private boolean isVerticalDirection(Direction direction) {
        return direction == Direction.UP || direction == Direction.DOWN;
    }

    /** Движется ли змейка по вертикали. */
    private boolean isMovingVertically() {
        return snakeParts.get(0).x == snakeParts.get(1).x;
    }

    /** Движется ли змейка по горизонтали. */
    private boolean isMovingHorizontally() {
        return snakeParts.get(0).y == snakeParts.get(1).y;
    }

    /** Доступна ли стихия. */
    public boolean canUseElement(Element element) {
        return availableElements.contains(element);
    }

    /** Забывает все стихии. */
    public void clearElements() {
        availableElements.clear();
    }

    /** Тратит единицу силы всемогущей змейки. */
    public void decreaseAlmightyPower() {
        almightyPower--;
    }

    /** Уменьшает запас воздуха. */
    public void reduceBreath() {
        breath--;
    }

    /** Восстанавливает воздух по длине змейки. */
    public void takeFullBreath() {
        breath = getLength();
    }

    /** Возвращает длину змейки. */
    public int getLength() {
        return snakeParts.size();
    }

    /** Закончился ли воздух. */
    public boolean isDrowned() {
        return breath < 0;
    }

    /** Возвращает текущую стихию. */
    public Element getElement() {
        return element;
    }

    /** Возвращает уровень голода. */
    public int getHunger() {
        return hunger;
    }

    /** Возвращает остаток силы. */
    public int getAlmightyPower() {
        return almightyPower;
    }

    /** Жива ли змейка. */
    public boolean isAlive() {
        return isAlive;
    }

    /** Возвращает клетку головы после последнего шага. */
    public GameObject getNewHead() {
        return newHead;
    }
}
