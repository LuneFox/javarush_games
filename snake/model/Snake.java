package com.javarush.games.snake.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Signs;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

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
    private Date starveTime;
    private int breath;
    private int hunger;
    public boolean isAlive;
    public boolean canChangeElement;

    // CONSTRUCTOR

    public Snake(int x, int y, SnakeGame game, Direction direction) {
        this.game = game;
        this.direction = direction;
        this.bodyColor = new Color[2];
        this.elementsAvailable = new LinkedList<>();
        this.snakeParts = new ArrayList<>();
        this.hunger = 0;
        this.starveTime = new Date();
        this.isAlive = true;
        this.canChangeElement = true;
        this.elementsAvailable.add(Element.NEUTRAL);
        this.setElement(Element.NEUTRAL);
        this.addParts(x, y, direction, 3);
    }


    // VISUALS

    public void draw() {
        String head = isAlive ? Signs.headSign : Signs.deadSign;
        checkDead();
        drawStripedBody(head);
    }

    private void drawStripedBody(String head) {
        for (int i = snakeParts.size() - 1; i >= 0; i--) {
            if (i == 0) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, bodyColor[1],
                        head, Color.WHITE, 90);
            } else {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, bodyColor[i % 2],
                        Signs.bodySign, Color.WHITE, 90);
            }
        }
    }


    // MECHANICS

    public void move() {
        canChangeElement = true;
        head = createNewHead();
        if (checkGameOver() | checkEscapeBorders() | checkBiteSelf() || isDeadAfterNodeInteraction(head)) {
            return; // check last only if first 3 didn't return true ^
        }
        starve();
        snakeParts.add(0, head);
        removeTail();
    }

    public void interactWithOrb(Orb orb) {
        if (head.x == orb.x && head.y == orb.y && element != Element.AIR) {
            orb.isAlive = false;
            breath++;
            hunger = 0;
            elongateTail();
            game.setTurnDelay();
        }
    }

    private void interactWithNode(Node node) {
        if (almightyInteraction()) { // if elements is ALMIGHTY, does action described inside and returns
            return;
        }

        if (node.getTerrain() != Node.Terrain.WATER) { // take a breath outside water
            breath = snakeParts.size();
        }

        switch (node.getTerrain()) {
            case WALL:
                if (element != Element.AIR) { // air snake can fly over walls
                    isAlive = false;
                    game.setGameOverReason(Strings.GAME_OVER_BUMPED);
                }
                break;

            case WOOD:
                if (element == Element.FIRE) { // fire snake sets wood on fire
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Node.Terrain.FIRE);
                } else if (element == Element.WATER) { // water snake moistens wood
                    game.getMap().getLayoutNode(getNewHeadX(), getNewHeadY()).setMoist(true);
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
                game.setGameOverReason(Strings.GAME_OVER_DROWNED);
                break;

            case FIRE:
                if (element == Element.NEUTRAL || element == Element.EARTH) {
                    isAlive = false; // earth and neutral snakes die
                    game.setGameOverReason(Strings.GAME_OVER_BURNED);
                } else if (element == Element.WATER) { // water snake extinguishes burning wood
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Node.Terrain.WOOD);
                    game.getMap().getLayoutNode(getNewHeadX(), getNewHeadY()).setMoist(true);
                }
                break;

            case FOREST:
                if (element == Element.FIRE) { // fire snake sets forest on fire
                    game.getMap().setLayoutNode(getNewHeadX(), getNewHeadY(), Node.Terrain.FIRE);
                } else if (element != Element.AIR) { // air snake can fly over fire
                    isAlive = false;
                    game.setGameOverReason(Strings.GAME_OVER_EATEN);
                }
                break;

            case WORMHOLE:
                if (element != Element.EARTH && element != Element.AIR) { // only earth snake can use wormholes
                    isAlive = false;
                    game.setGameOverReason(Strings.GAME_OVER_LOST);
                } else if (element == Element.EARTH) {
                    Map map = game.getMap();
                    for (Map.WormHole wormHole : map.wormHoles) {
                        if (node.x == wormHole.location.x && node.y == wormHole.location.y) {
                            head.x = wormHole.destination.x;
                            head.y = wormHole.destination.y;
                        } else if (node.x == wormHole.destination.x && node.y == wormHole.destination.y) {
                            head.x = wormHole.location.x;
                            head.y = wormHole.location.y;
                        }
                    }
                }
                break;

            case MOUNTAIN:
                isAlive = false;
                game.setGameOverReason(Strings.GAME_OVER_MOUNTAIN_COLD);
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

    private void elongateTail() {
        int tail = snakeParts.size() - 1;
        GameObject newTail = new GameObject(snakeParts.get(tail).x, snakeParts.get(tail).y);
        snakeParts.add(newTail);
    }

    private void starve() {
        if (new Date().getTime() - starveTime.getTime() > 300) {
            if (!(element == Element.ALMIGHTY)) {
                hunger += getLength() / 5;
            }
            if (hunger > 100) {
                removeTail();
                game.setScore(-5, true);
                hunger = 0;
            }
            starveTime = new Date();
        }
    }

    public void rotateToNextElement(SnakeGame game) {
        if (canChangeElement) {
            Element movingElement = elementsAvailable.get(0);      // taking element to move (it's current)
            elementsAvailable.remove(elementsAvailable.get(0));    // removing it from list (it's first)
            elementsAvailable.add(movingElement);                  // adding it to the end
            setElement(elementsAvailable.get(0));                  // element that shifted to 0 is a new element
            game.drawElementsPanel();
        }
    }

    public void rotateToPreviousElement(SnakeGame game) {
        if (canChangeElement) {
            int lastElement = elementsAvailable.size() - 1;
            Element movingElement = elementsAvailable.get(lastElement); // taking element to move (last)
            elementsAvailable.remove(movingElement);                    // removing it from list (it was last)
            elementsAvailable.add(0, movingElement);             // instantly adding it to the beginning
            setElement(elementsAvailable.get(0));                       // making it new element
            game.drawElementsPanel();
        }
    }

    public void clearElements() {
        this.elementsAvailable.clear();
    }


    // UTILITIES AND CHECKS

    public boolean canUse(Element element) {
        return (this.elementsAvailable.contains(element));
    }

    private void addParts(int x, int y, Direction direction, int amount) {
        // Used in snake creation
        switch (direction) {
            case UP:
                for (int i = 0; i < amount; i++) {
                    snakeParts.add(new GameObject(x, y + i));
                }
                break;
            case DOWN:
                for (int i = 0; i < amount; i++) {
                    snakeParts.add(new GameObject(x, y - i));
                }
                break;
            case LEFT:
                for (int i = 0; i < amount; i++) {
                    snakeParts.add(new GameObject(x + i, y));
                }
                break;
            case RIGHT:
                for (int i = 0; i < amount; i++) {
                    snakeParts.add(new GameObject(x - i, y));
                }
                break;
        }
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

    public boolean checkCollision(GameObject obj) {
        return (snakeParts.contains(obj));
    }

    private boolean checkEscapeBorders() {
        if (head.x < 0 || head.x >= SnakeGame.SIZE || head.y < 4 || head.y >= SnakeGame.SIZE) {
            isAlive = false;
            game.setGameOverReason(Strings.GAME_OVER_RUNAWAY);
            return true;
        }
        return false;
    }

    private boolean checkBiteSelf() {
        if (checkCollision(head) && !(element == Element.ALMIGHTY)) {
            isAlive = false;
            game.setGameOverReason(Strings.GAME_OVER_SELF_BITTEN);
            return true;
        }
        return false;
    }

    private boolean checkGameOver() {
        return !isAlive || game.getLifetime() <= 0;
    }


    // GETTERS

    public int getHunger() {
        return hunger;
    }

    public Element getElement() {
        return element;
    }

    public LinkedList<Element> getElementsAvailable() {
        return elementsAvailable;
    }

    public int getLength() {
        return snakeParts.size();
    }


    // SETTERS

    public void setDirection(Direction command) { // allows to change direction, but only to another axis
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
