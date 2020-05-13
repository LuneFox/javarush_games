package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.*;

import java.util.*;

/**
 * Neutral snake can eat food
 * Water snake can swim and put out fire
 * Fire snake can set wooden stuff on fire
 * Ground snake uses wormholes
 * Air snake can fly over everything
 */

public class Snake {
    boolean isAlive = true;
    private Color[] bodyColor = new Color[2];
    private LinkedList<Element> elementsAvailable = new LinkedList<>();
    private Element element;
    private List<GameObject> snakeParts;
    private Direction direction = Direction.UP;
    private int breath;
    private SnakeGame game;

    private int hunger;

    private GameObject head;

    public Snake(int x, int y, SnakeGame game) {
        setElement(Element.NEUTRAL);
        elementsAvailable.add(Element.NEUTRAL);
        snakeParts = new ArrayList<>();
        for (int i = 0; i < 3; i++) snakeParts.add(new GameObject(x, y + i));
        breath = snakeParts.size();
        hunger = 0;
        this.game = game;
    }

    void draw(Game game) {
        String snakeHeadSign = isAlive ? Signs.headSign : Signs.deadSign;
        Color snakeSignColor = Color.WHITE;

        if (!isAlive) {
            element = Element.DEAD;
            this.setElement(element);
        }

        for (int i = snakeParts.size() - 1; i >= 0; i--) {
            if (i == 0)
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, bodyColor[1],
                        snakeHeadSign, snakeSignColor, 75);
            else
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, bodyColor[i % 2],
                        Signs.bodySign, snakeSignColor, 75);
        }
    }

    void setDirection(Direction command) { // allows to change direction, but only to another axis
        if (command == Direction.LEFT)
            if (snakeParts.get(0).y == snakeParts.get(1).y)
                return;
        if (command == Direction.RIGHT)
            if (snakeParts.get(0).y == snakeParts.get(1).y)
                return;
        if (command == Direction.UP)
            if (snakeParts.get(0).x == snakeParts.get(1).x)
                return;
        if (command == Direction.DOWN)
            if (snakeParts.get(0).x == snakeParts.get(1).x)
                return;
        this.direction = command;
    }

    void move() {
        if (!isAlive || game.getLifetime() <= 0) return; // return because the snake is simply dead or the game ended
        head = createNewHead();
        if (head.x < 0 || head.x >= SnakeGame.WIDTH || head.y < 4 || head.y >= SnakeGame.HEIGHT) {
            isAlive = false;
            game.setGameOverReason("Snake tried to escape!");
        } else if (checkCollision(head) && !(element == Element.ALMIGHTY)) {
            isAlive = false;
            game.setGameOverReason("Snake bit itself to death!");
        } else {
            nodeInteract(game.getMap().getLayout()[head.y][head.x]);
            if (!isAlive) return; // return because something happened to the snake during interaction with a node

            if (!(element == Element.ALMIGHTY)) hunger += getLength() / 5;
            if (hunger > 100) {
                removeTail();
                game.setScore(-5, true);
                hunger = 0;
            }
            snakeParts.add(0, head); // moving a snake, step 1
            removeTail(); // moving a snake, step 2
        }
    }

    /**
     * Here is what happens when the snake interacts with an orb
     */

    void orbInteract(Orb orb) {
        if (head.x == orb.x && head.y == orb.y && element != Element.AIR) {
            game.setTurnDelay(Math.max((SnakeGame.MAX_TURN_DELAY - (getLength() * 10)), 100));
            breath++;
            hunger = 0;
            orb.isAlive = false;
        }
        if (!orb.isAlive) {
            snakeParts.add(new GameObject(snakeParts.get(snakeParts.size() - 1).x, snakeParts.get(snakeParts.size() - 1).y)); // moving a snake, step 1
        }
    }

    private GameObject createNewHead() { // returns a head candidate next to the current head in the direction
        GameObject head = snakeParts.get(0);
        GameObject newHead;
        switch (direction) {
            case UP:
                newHead = new GameObject(head.x, head.y - 1);
                if (element == Element.ALMIGHTY && newHead.y < 4) newHead = new GameObject(head.x, 31);
                break;
            case LEFT:
                newHead = new GameObject(head.x - 1, head.y);
                if (element == Element.ALMIGHTY && newHead.x < 0) newHead = new GameObject(31, head.y);
                break;
            case DOWN:
                newHead = new GameObject(head.x, head.y + 1);
                if (element == Element.ALMIGHTY && newHead.y > 31) newHead = new GameObject(head.x, 4);
                break;
            case RIGHT:
                newHead = new GameObject(head.x + 1, head.y);
                if (element == Element.ALMIGHTY && newHead.x > 31) newHead = new GameObject(0, head.y);
                break;
            default:
                throw new UnsupportedOperationException("What direction, again?!");
        }
        return newHead;
    }

    private void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    boolean checkCollision(GameObject obj) {
        return (snakeParts.contains(obj));
    }

    /**
     * Here is what happens to the snake when the next node is a certain terrain type, e.g. drown in water
     */

    private void nodeInteract(Node node) {
        if (element == Element.ALMIGHTY) {
            game.decreaseLifetime();
            if (game.getLifetime() <= 0) return;
            if (game.getMap().getLayoutNode(head.x, head.y).getTerrain() != Terrain.VOID) game.setScore(1, true);
            game.getMap().setLayoutNode(head.x, head.y, Terrain.VOID);
            return;
        }
        switch (node.getTerrain()) {
            case WALL:
                if (element != Element.AIR) { // air snake can fly over walls
                    isAlive = false;
                    game.setGameOverReason("Snake hit the wall!");
                    ;
                }
                break;
            case FIELD:
                breath = snakeParts.size();
                break;
            case WOOD:
                breath = snakeParts.size();
                if (element == Element.FIRE) { // fire snake sets wood on fire
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Terrain.FIRE);
                }
                if (element == Element.WATER) { // water snake moistens wood
                    game.getMap().getLayoutNode(getNewHeadX(), getNewHeadY()).resetFireResistance();
                }
                break;
            case WATER:
                if (element == Element.FIRE && getLength() > 3) removeTail();
                if (element != Element.WATER && element != Element.AIR) breath--; // air snake can fly over water
                if (breath == -1) isAlive = false;
                game.setGameOverReason("Snake has drowned!");

                break;
            case FIRE:
                if (element == Element.NEUTRAL || element == Element.EARTH) {
                    isAlive = false; // earth and neutral snakes die
                    game.setGameOverReason("Snake was burned!");
                }
                if (element == Element.WATER) { // water snake extinguishes burning wood
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Terrain.WOOD);
                }
                break;
            case FOREST:
                breath = snakeParts.size();
                if (element == Element.FIRE) { // fire snake sets forest on fire
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Terrain.FIRE);
                } else if (element != Element.AIR) { // air snake can fly over fire
                    isAlive = false;
                    game.setGameOverReason("Snake was eaten by a forest beast!");
                }
                break;
            case WORMHOLE:
                if (element != Element.EARTH && element != Element.AIR) { // only earth snake can use wormholes
                    isAlive = false;
                    game.setGameOverReason("Snake got lost underground!");
                } else if (element == Element.EARTH) {
                    if (node.x == 5 && node.y == 5) {
                        head.x = 13;
                        head.y = 12;
                    } else if (node.x == 13 && node.y == 12) {
                        head.x = 5;
                        head.y = 5;
                    } else if (node.x == 1 && node.y == 30) {
                        head.x = 30;
                        head.y = 5;
                    } else if (node.x == 30 && node.y == 5) {
                        head.x = 1;
                        head.y = 30;
                    }
                }
                break;
            default:
                break;
        }
    }

    private void setElement(Element element) {
        this.element = element;
        switch (element) {
            case NEUTRAL:
                this.bodyColor[0] = Color.DARKOLIVEGREEN;
                this.bodyColor[1] = Color.DARKGREEN;
                break;
            case WATER:
                this.bodyColor[0] = Color.BLUE;
                this.bodyColor[1] = Color.DARKBLUE;
                break;
            case FIRE:
                this.bodyColor[0] = Color.YELLOW;
                this.bodyColor[1] = Color.RED;
                break;
            case EARTH:
                this.bodyColor[0] = Color.DARKORANGE;
                this.bodyColor[1] = Color.BROWN;
                break;
            case AIR:
                this.bodyColor[0] = Color.LIGHTGRAY;
                this.bodyColor[1] = Color.LIGHTSKYBLUE;
                break;
            case DEAD:
                this.bodyColor[0] = Color.DARKSLATEGRAY;
                this.bodyColor[1] = Color.BLACK;
                break;
            case ALMIGHTY:
                this.bodyColor[0] = Color.ORCHID;
                this.bodyColor[1] = Color.DEEPPINK;
                break;
        }
    }

    void swapNextElement() {
        Element movingElement = element; // taking element to move (it's current element)
        elementsAvailable.remove(element); // removing it from list (it's first)
        elementsAvailable.add(movingElement); // instantly adding it to the end
        setElement(elementsAvailable.get(0)); // element that shifted to 0 is a new element
    }

    void swapPreviousElement() {
        Element movingElement = elementsAvailable.get(elementsAvailable.size() - 1); // taking element to move (last)
        elementsAvailable.remove(movingElement); // removing it from list (it was last)
        elementsAvailable.add(0, movingElement); // instantly adding it to the beginning
        setElement(elementsAvailable.get(0)); // making it new element
    }

    int getLength() {
        return snakeParts.size();
    }

    LinkedList<Element> getElementsAvailable() {
        return elementsAvailable;
    }

    private int getNewHeadX() {
        switch (direction) {
            case LEFT:
                return this.snakeParts.get(0).x - 1;
            case RIGHT:
                return this.snakeParts.get(0).x + 1;
            default:
                return this.snakeParts.get(0).x;
        }
    }

    private int getNewHeadY() {
        switch (direction) {
            case UP:
                return this.snakeParts.get(0).y - 1;
            case DOWN:
                return this.snakeParts.get(0).y + 1;
            default:
                return this.snakeParts.get(0).y;
        }
    }

    int getHunger() {
        return hunger;
    }

    Element getElement() {
        return element;
    }
}
