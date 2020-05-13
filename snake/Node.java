package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.Terrain;

import static com.javarush.games.snake.Triggers.firstLaunch;

/**
 * Interactive tile nodes
 */

class Node extends GameObject {
    private Terrain terrain;
    private Color color;
    private Color bgColor;
    private String sign;
    private int fireResistance;

    Node(int x, int y, SnakeGame game, int type) {
        super(x, y, game);
        resetFireResistance();
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
                this.sign = Signs.forestSign;
                break;
            case 5:
                this.terrain = Terrain.WORMHOLE;
                this.color = Color.PURPLE;
                this.bgColor = Color.BLACK;
                this.sign = Signs.wormHoleSign;
                break;
            case 6:
                this.terrain = Terrain.PIT;
                this.color = Color.GRAY;
                this.bgColor = Color.BLACK;
                this.sign = Signs.pitSign;
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

    void draw(Game game) {
        this.activate();
        game.setCellValueEx(x, y, bgColor, sign, color, 75);
    }

    private void activate() { // interact with surrounding nodes
        if (firstLaunch) return;
        Node activeNode;
        if (terrain == Terrain.FIRE) { // ignite wood
            for (int x = this.x - 1; x <= this.x + 1; x++) {
                for (int y = this.y - 1; y <= this.y + 1; y++) {
                    activeNode = (game.getMap().getLayoutNode(x, y));
                    if (activeNode.terrain == Terrain.WOOD || activeNode.terrain == Terrain.FOREST) {
                        activeNode.fireResistance--;
                        if (activeNode.fireResistance <= 0) game.getMap().setLayoutNode(x, y, Terrain.FIRE);
                    }
                }
            }
        }
    }

    void resetFireResistance() {
        this.fireResistance = (fireResistance < 30) ? 30 : (game.getSnakeLength() * 10 - 50);
        this.fireResistance = (fireResistance > 60) ? 60 : (game.getSnakeLength() * 10 - 50);
    }

    Terrain getTerrain() {
        return terrain;
    }
}
