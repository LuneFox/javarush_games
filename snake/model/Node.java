package com.javarush.games.snake.model;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.view.Phase;
import com.javarush.games.snake.view.Signs;

import java.util.Date;

/**
 * Interactive tile nodes
 */

public class Node extends GameObject {
    private Terrain terrain;
    private Color color;
    private Color bgColor;
    private String sign;
    private Date wetDate;
    private boolean isMoist; // water snake was here

    public enum Terrain {
        FIELD, WOOD, WATER, FIRE, FOREST, WORMHOLE, MOUNTAIN, WALL, SAND, VOID
    }

    // CONSTRUCTOR

    Node(int x, int y, SnakeGame game, int type) {
        super(x, y, game);
        this.wetDate = new Date();
        this.isMoist = false;
        switch (type) {
            case 0:
                this.terrain = Terrain.FIELD;
                this.color = Color.LIGHTGREEN;
                this.bgColor = Color.PALEGREEN;
                this.sign = Signs.fieldSign;
                break;
            case 1:
                this.terrain = Terrain.WOOD;
                this.color = Color.SANDYBROWN;
                this.bgColor = Color.SADDLEBROWN;
                this.sign = Signs.woodSign;
                break;
            case 2:
                this.terrain = Terrain.WATER;
                this.color = Color.LIGHTBLUE;
                this.bgColor = Color.DEEPSKYBLUE;
                this.sign = Signs.waterSign;
                break;
            case 3:
                this.terrain = Terrain.FIRE;
                this.color = Color.YELLOW;
                this.bgColor = Color.ORANGERED;
                this.sign = Signs.fireSign;
                break;
            case 4:
                this.terrain = Terrain.FOREST;
                this.color = Color.LIGHTGREEN;
                this.bgColor = Color.FORESTGREEN;
                this.sign = (game.getRandomNumber(2) == 1) ? Signs.forestSign1 : Signs.forestSign2;
                break;
            case 5:
                this.terrain = Terrain.WORMHOLE;
                this.color = Color.PURPLE;
                this.bgColor = Color.BLACK;
                this.sign = Signs.wormHoleSign;
                break;
            case 6:
                this.terrain = Terrain.MOUNTAIN;
                this.color = Color.ORANGE;
                this.bgColor = Color.BROWN;
                this.sign = Signs.mountainSign;
                break;
            case 7:
                this.terrain = Terrain.WALL;
                this.color = Color.WHITE;
                this.bgColor = Color.GRAY;
                this.sign = Signs.wallSign;
                break;
            case 8:
                this.terrain = Terrain.SAND;
                this.color = Color.YELLOW;
                this.bgColor = Color.SANDYBROWN;
                this.sign = Signs.sandSign;
                break;
            case 9:
                this.terrain = Terrain.VOID;
                this.color = Color.BLACK;
                this.bgColor = Color.BLACK;
                this.sign = Signs.voidSign;
                break;
            default:
                break;
        }
    }


    //  VISUALS

    public void draw(Game game) {
        this.causeEffect();
        game.setCellValueEx(x, y, bgColor, sign, color, 90);
    }


    // MECHANICS

    private void causeEffect() { // interact with surrounding nodes
        if (Phase.is(Phase.GAME)) {
            if (terrain == Terrain.WOOD || terrain == Terrain.FOREST) {
                setOnFire();
            }
        }
    }

    private boolean hasFireNear() {
        Node neighbor;
        for (int x = this.x - 1; x <= this.x + 1; x++) {
            for (int y = this.y - 1; y <= this.y + 1; y++) {
                if (game.outOfBounds(x, y)) {
                    continue;
                }
                neighbor = (game.getMap().getLayoutNode(x, y));
                if (neighbor.terrain == Terrain.FIRE) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setOnFire() {
        if (hasFireNear()) {
            int igniteDelay;
            igniteDelay = isMoist ? Math.max(game.getSnakeLength() * 500, 1000) : 1000;
            if (new Date().getTime() - wetDate.getTime() > igniteDelay) {
                game.getMap().setLayoutNode(x, y, Terrain.FIRE);
            }
        } else {
            wetDate = new Date();
        }
    }

    // GETTERS

    public Terrain getTerrain() {
        return terrain;
    }

    // SETTERS

    void setMoist(boolean moist) {
        isMoist = moist;
    }
}
