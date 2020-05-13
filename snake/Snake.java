package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.*;

import java.util.*;

/**
 * Class for snake and its elemental abilities
 */

public class Snake {
    private SnakeGame game;
    private Element element;
    private Direction direction;
    private GameObject head;
    private Color[] bodyColor;
    private ArrayList<GameObject> snakeParts;
    private LinkedList<Element> elementsAvailable;
    private int breath;
    private int hunger;
    boolean isAlive = true;

    // CONSTRUCTOR

    public Snake(int x, int y, SnakeGame game) {
        // Properties of a fresh snake
        this.game = game;
        this.direction = Direction.UP;
        this.bodyColor = new Color[2];
        this.elementsAvailable = new LinkedList<>();
        this.snakeParts = new ArrayList<>();
        this.setElement(Element.NEUTRAL);
        this.elementsAvailable.add(Element.NEUTRAL);
        this.hunger = 0;

        // Adding 3 body parts, starting with head and going down vertically
        for (int i = 0; i < 3; i++) {
            snakeParts.add(new GameObject(x, y + i));
        }

        // Snake has as much breath as its length
        breath = snakeParts.size();
    }


    // VISUALS

    void draw() {
        String head = isAlive ? Signs.headSign : Signs.deadSign;
        checkDead();
        drawStripedBody(head);
    }

    private void drawStripedBody(String head) {
        for (int i = snakeParts.size() - 1; i >= 0; i--) {
            if (i == 0) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, bodyColor[1],
                        head, Color.WHITE, 75);
            } else {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, bodyColor[i % 2],
                        Signs.bodySign, Color.WHITE, 75);
            }
        }
    }


    // MECHANICS

    void move() {
        head = createNewHead();
        if (checkGameOver() | checkEscapeBorders() | checkBiteSelf() || isDeadAfterNodeInteraction(head)) {
            return; // check last only if first 3 didn't return true ^
        }
        increaseHunger();
        snakeParts.add(0, head);
        removeTail();
    }

    void interactWithOrb(Orb orb) {
        if (head.x == orb.x && head.y == orb.y && element != Element.AIR) {
            game.setTurnDelay();
            breath++;
            hunger = 0;
            orb.isAlive = false;

            // Elongate tail
            int tail = snakeParts.size() - 1;
            GameObject newTail = new GameObject(snakeParts.get(tail).x, snakeParts.get(tail).y);
            snakeParts.add(newTail);
        }
    }

    private void interactWithNode(Node node) {
        if (almightyInteraction()) {
            return;
        }
        switch (node.getTerrain()) {
            case WALL:
                if (element != Element.AIR) { // air snake can fly over walls
                    isAlive = false;
                    game.setGameOverReason("Snake hit the wall!");
                }
                break;

            case FIELD:
                breath = snakeParts.size();
                break;

            case WOOD:
                breath = snakeParts.size();
                if (element == Element.FIRE) { // fire snake sets wood on fire
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Node.Terrain.FIRE);
                }
                if (element == Element.WATER) { // water snake moistens wood
                    game.getMap().getLayoutNode(getNewHeadX(), getNewHeadY()).resetFireResistance();
                }
                break;

            case WATER:
                if (element == Element.FIRE && getLength() > 3) {
                    removeTail();
                }
                if (element != Element.WATER && element != Element.AIR) {
                    breath--;
                }
                if (breath == -1) {
                    isAlive = false;
                }
                game.setGameOverReason("Snake has drowned!");
                break;

            case FIRE:
                if (element == Element.NEUTRAL || element == Element.EARTH) {
                    isAlive = false; // earth and neutral snakes die
                    game.setGameOverReason("Snake was burned!");
                }
                if (element == Element.WATER) { // water snake extinguishes burning wood
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Node.Terrain.WOOD);
                }
                break;

            case FOREST:
                breath = snakeParts.size();
                if (element == Element.FIRE) { // fire snake sets forest on fire
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Node.Terrain.FIRE);
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

    private boolean isDeadAfterNodeInteraction(GameObject head) {
        Node node = game.getMap().getLayout()[head.y][head.x];
        interactWithNode(node);
        return (!isAlive);
    }

    private boolean almightyInteraction() {
        if (element == Element.ALMIGHTY) {
            game.decreaseLifetime();
            if (game.getLifetime() <= 0) {
                return true;
            }
            if (game.getMap().getLayoutNode(head.x, head.y).getTerrain() != Node.Terrain.VOID) {
                game.setScore(1, true);
            }
            game.getMap().setLayoutNode(head.x, head.y, Node.Terrain.VOID);
            return true;
        }
        return false;
    }

    private GameObject createNewHead() { // returns a head candidate next to the current head in the direction
        GameObject head = snakeParts.get(0);
        GameObject newHead;
        switch (direction) {
            case UP:
                newHead = new GameObject(head.x, head.y - 1);
                if (element == Element.ALMIGHTY && newHead.y < 4) {
                    newHead = new GameObject(head.x, 31);
                }
                break;
            case LEFT:
                newHead = new GameObject(head.x - 1, head.y);
                if (element == Element.ALMIGHTY && newHead.x < 0) {
                    newHead = new GameObject(31, head.y);
                }
                break;
            case DOWN:
                newHead = new GameObject(head.x, head.y + 1);
                if (element == Element.ALMIGHTY && newHead.y > 31) {
                    newHead = new GameObject(head.x, 4);
                }
                break;
            case RIGHT:
                newHead = new GameObject(head.x + 1, head.y);
                if (element == Element.ALMIGHTY && newHead.x > 31) {
                    newHead = new GameObject(0, head.y);
                }
                break;
            default:
                throw new UnsupportedOperationException("What direction, again?!");
        }
        return newHead;
    }

    private void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    private void increaseHunger() {
        if (!(element == Element.ALMIGHTY)) {
            hunger += getLength() / 5;
        }
        if (hunger > 100) {
            removeTail();
            game.setScore(-5, true);
            hunger = 0;
        }
    }

    void rotateToNextElement() {
        Element movingElement = element;      // taking element to move (it's current)
        elementsAvailable.remove(element);    // removing it from list (it's first)
        elementsAvailable.add(movingElement); // adding it to the end
        setElement(elementsAvailable.get(0)); // element that shifted to 0 is a new element
    }

    void rotateToPreviousElement() {
        int lastElement = elementsAvailable.size() - 1;
        Element movingElement = elementsAvailable.get(lastElement); // taking element to move (last)
        elementsAvailable.remove(movingElement);                    // removing it from list (it was last)
        elementsAvailable.add(0, movingElement);             // instantly adding it to the beginning
        setElement(elementsAvailable.get(0));                       // making it new element
    }


    // UTILITIES AND CHECKS

    boolean canUse(Element element) {
        return (this.elementsAvailable.contains(element));
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

    private void checkDead() {
        if (!isAlive) {
            element = Element.DEAD;
            this.setElement(element);
        }
    }

    boolean checkCollision(GameObject obj) {
        return (snakeParts.contains(obj));
    }

    private boolean checkEscapeBorders() {
        if (head.x < 0 || head.x >= SnakeGame.WIDTH || head.y < 4 || head.y >= SnakeGame.HEIGHT) {
            isAlive = false;
            game.setGameOverReason("Snake tried to escape!");
            return true;
        }
        return false;
    }

    private boolean checkBiteSelf() {
        if (checkCollision(head) && !(element == Element.ALMIGHTY)) {
            isAlive = false;
            game.setGameOverReason("Snake bit itself to death!");
            return true;
        }
        return false;
    }

    private boolean checkGameOver() {
        return !isAlive || game.getLifetime() <= 0;
    }


    // GETTERS

    int getHunger() {
        return hunger;
    }

    Element getElement() {
        return element;
    }

    LinkedList<Element> getElementsAvailable() {
        return elementsAvailable;
    }

    int getLength() {
        return snakeParts.size();
    }


    // SETTERS

    void setDirection(Direction command) { // allows to change direction, but only to another axis
        switch (command) {
            case LEFT:
            case RIGHT:
                if (snakeParts.get(0).y == snakeParts.get(1).y) {
                    return;
                }
                this.direction = command;
                break;
            case UP:
            case DOWN:
                if (snakeParts.get(0).x == snakeParts.get(1).x) {
                    return;
                }
                this.direction = command;
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
            default:
                this.bodyColor[0] = Color.BLACK;
                this.bodyColor[1] = Color.BLACK;
                break;
        }
    }
}
