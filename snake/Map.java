package com.javarush.games.snake;

import com.javarush.games.snake.enums.Direction;
import com.javarush.games.snake.enums.Element;

import java.util.ArrayList;

/**
 * Contains two-dimensional array of nodes, which are, in fact, interactive tile objects
 */

class Map {
    private Node[][] layout = new Node[SnakeGame.HEIGHT][SnakeGame.WIDTH];
    ArrayList<WormHole> wormHoles = new ArrayList<>();
    ArrayList<Orb> orbs = new ArrayList<>();
    Coordinate snakeStartPlace;
    Direction snakeStartDirection;
    private SnakeGame game;

    private static ArrayList<int[][]> stages = new ArrayList<>();

    static {
        stages.add(new int[][]{
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {7, 7, 7, 7, 7, 7, 7, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8}, // y = 4
                {7, 1, 1, 1, 1, 5, 7, 2, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 5, 8},
                {7, 1, 1, 1, 1, 1, 7, 2, 2, 8, 0, 0, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8},
                {7, 1, 1, 1, 1, 1, 7, 2, 2, 2, 8, 0, 7, 8, 8, 8, 4, 8, 8, 8, 7, 0, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0},
                {7, 1, 1, 1, 1, 1, 7, 2, 2, 2, 8, 0, 7, 8, 8, 8, 4, 8, 8, 8, 7, 0, 7, 4, 4, 4, 7, 0, 0, 0, 0, 0},
                {7, 7, 1, 1, 1, 7, 7, 2, 2, 2, 8, 0, 7, 8, 8, 8, 4, 8, 8, 8, 7, 0, 7, 4, 8, 4, 7, 0, 0, 0, 0, 0},
                {7, 7, 1, 1, 1, 7, 7, 2, 2, 2, 8, 0, 7, 8, 8, 8, 4, 4, 4, 4, 7, 0, 7, 4, 4, 4, 7, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 7, 8, 8, 8, 8, 8, 8, 8, 7, 0, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 7, 5, 8, 8, 8, 8, 8, 8, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 2, 2, 1, 2, 2, 2, 2, 8, 8, 0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 2, 2, 1, 2, 8, 8, 8, 8, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0},
                {0, 8, 8, 1, 8, 8, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 8, 4, 4, 4, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 8, 8, 8, 1, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 0, 0, 0, 1, 8, 1, 1, 1, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 2, 2, 2, 2, 8, 0, 0, 1, 1, 1, 0, 0, 0, 0, 3, 3, 8, 8, 8, 3, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 2, 2, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 8, 8, 8, 3, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 2, 2, 2, 2, 8, 0, 0, 0, 1, 1, 1, 1, 0, 0, 3, 3, 8, 8, 8, 3, 3, 0},
                {8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 0, 0, 0, 0, 1, 8, 8, 1, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0},
                {8, 5, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0},
                {8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}  //y = 31
        });
    }

    // CONSTRUCTOR

    Map(int stage, SnakeGame game) {
        this.game = game;
        int[][] pattern = stages.get(stage);
        for (int y = 0; y < SnakeGame.HEIGHT; y++) {
            for (int x = 0; x < SnakeGame.WIDTH; x++) {
                layout[y][x] = new Node(x, y, game, pattern[y][x]);
            }
        }

        switch (stage) {
            case 0: {
                Orb waterOrb = new Orb(3, 7, Element.WATER);
                Orb fireOrb = new Orb(27, 27, Element.FIRE);
                Orb earthOrb = new Orb(16, 18, Element.EARTH);
                Orb airOrb = new Orb(18, 8, Element.AIR);
                Orb almightyOrb = new Orb(24, 9, Element.ALMIGHTY);
                orbs.add(waterOrb);
                orbs.add(fireOrb);
                orbs.add(earthOrb);
                orbs.add(airOrb);
                orbs.add(almightyOrb);
                wormHoles.add(new WormHole(5,5,13,12));
                wormHoles.add(new WormHole(1,30,30,5));
                snakeStartPlace = new Coordinate(2, 27);
                snakeStartDirection = Direction.LEFT;
            }
            default:
                break;
        }
    }

    // GETTERS

    Node[][] getLayout() {
        return layout;
    }

    Node getLayoutNode(int x, int y) {
        return layout[y][x];
    }

    // SETTERS

    void setLayoutNode(int x, int y, Node.Terrain terrain) {
        this.layout[y][x] = new Node(x, y, game, terrain.ordinal());
    }

    static class Coordinate {
        public int x;
        public int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class WormHole {
        Coordinate location;
        Coordinate destination;

        WormHole(int xLoc, int yLoc, int xDest, int yDest) {
            this.location = new Coordinate(xLoc, yLoc);
            this.destination = new Coordinate(xDest, yDest);
        }
    }
}
