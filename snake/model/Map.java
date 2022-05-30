package com.javarush.games.snake.model;

import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.model.terrain.TerrainType;

import java.util.ArrayList;

/**
 * Contains two-dimensional array of nodes, which are, in fact, interactive tile objects
 */

public class Map {
    private final Terrain[][] terrainMatrix = new Terrain[SnakeGame.SIZE][SnakeGame.SIZE];
    ArrayList<WormHole> wormHoles = new ArrayList<>();
    public ArrayList<Orb> orbs = new ArrayList<>();
    public Coordinate snakeStartPlace;
    public Direction snakeStartDirection;

    public static ArrayList<int[][]> stages = new ArrayList<>();

    static {
        {
            stages.add(new int[][]{
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 4, 4, 4},
                    {2, 2, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 2, 2, 2, 2, 2, 8, 8, 0, 0, 0, 0, 8, 5, 4},
                    {2, 7, 1, 1, 1, 1, 5, 7, 7, 7, 7, 7, 5, 8, 8, 8, 8, 8, 7, 2, 2, 2, 8, 4, 4, 7, 4, 4, 0, 8, 8, 4},
                    {2, 7, 7, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 4, 4, 4, 7, 7, 2, 2, 8, 4, 4, 7, 7, 7, 4, 4, 0, 0, 0},
                    {2, 7, 1, 1, 1, 1, 1, 7, 2, 2, 2, 2, 7, 8, 8, 8, 8, 8, 7, 2, 8, 4, 4, 7, 7, 7, 7, 7, 4, 4, 0, 0},
                    {2, 7, 1, 1, 1, 1, 1, 7, 2, 2, 2, 2, 7, 8, 8, 8, 8, 8, 7, 2, 8, 4, 7, 7, 3, 8, 3, 7, 7, 4, 0, 0},
                    {2, 7, 7, 1, 1, 1, 7, 7, 2, 2, 2, 2, 7, 7, 8, 8, 8, 7, 7, 2, 8, 7, 7, 7, 8, 8, 8, 7, 7, 7, 0, 0},
                    {2, 2, 7, 2, 2, 2, 7, 2, 2, 2, 2, 2, 2, 7, 7, 7, 7, 7, 2, 8, 0, 4, 7, 7, 3, 8, 3, 7, 7, 4, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 4, 4, 7, 7, 7, 7, 7, 4, 4, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 8, 8, 8, 8, 0, 0, 0, 0, 4, 4, 7, 7, 7, 4, 4, 0, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 7, 4, 4, 0, 0, 0, 6},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6},
                    {2, 2, 2, 2, 1, 2, 2, 2, 2, 8, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6},
                    {8, 2, 2, 2, 1, 2, 2, 8, 8, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6},
                    {0, 8, 8, 8, 1, 8, 8, 0, 0, 0, 0, 0, 4, 4, 4, 8, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 6},
                    {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 8, 2, 2, 2, 8, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 8, 3, 8, 8, 8, 3, 3, 3, 8},
                    {0, 0, 8, 8, 0, 0, 0, 8, 2, 2, 2, 8, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 3, 8, 3, 3, 3, 3, 3, 8, 3},
                    {0, 8, 2, 2, 8, 0, 0, 0, 8, 2, 2, 2, 8, 8, 0, 0, 0, 4, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 8},
                    {0, 0, 8, 2, 8, 0, 0, 0, 0, 8, 2, 2, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 8, 8, 8, 3, 3, 8},
                    {0, 0, 0, 8, 0, 0, 0, 0, 0, 8, 2, 2, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 3, 8, 3, 3, 8, 8, 8, 3, 3, 8},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 2, 2, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 3, 8, 3, 3, 8, 8, 8, 3, 3, 3},
                    {4, 8, 8, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3},
                    {4, 5, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 8, 3, 3, 3, 3, 3, 8, 3},
                    {4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 3, 3, 3, 8, 8, 8, 3, 8},
            });
        } // stage 0

        {
            stages.add(new int[][]{
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 8, 8, 8, 8, 5, 8, 8, 8, 8, 0, 0, 0, 8, 8, 8, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 8, 0, 8, 5, 8, 8, 8, 8, 8, 5, 8, 8, 8, 8, 8, 5, 8, 0, 0, 0, 4, 8, 4, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 8, 8, 8, 0, 0, 0, 8, 8, 8, 8, 5, 8, 8, 8, 8, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                    {2, 2, 8, 8, 8, 8, 8, 2, 2, 2, 2, 2, 2, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 2, 2},
                    {2, 8, 8, 0, 0, 0, 8, 8, 2, 2, 2, 2, 8, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 2},
                    {8, 8, 0, 0, 0, 0, 0, 8, 8, 8, 2, 8, 4, 7, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 7, 4, 8},
                    {8, 0, 0, 8, 8, 8, 0, 0, 4, 4, 8, 8, 4, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 2, 7, 4, 8},
                    {8, 0, 0, 8, 5, 8, 0, 4, 4, 4, 4, 8, 8, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 7, 2, 7, 4, 8},
                    {8, 0, 0, 8, 8, 8, 0, 0, 4, 4, 4, 4, 8, 8, 4, 4, 4, 1, 1, 7, 7, 4, 4, 4, 4, 4, 4, 7, 2, 7, 4, 8},
                    {8, 8, 0, 0, 0, 0, 0, 8, 8, 4, 4, 4, 8, 2, 8, 4, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 2, 7, 4, 8},
                    {2, 8, 8, 0, 0, 0, 8, 8, 2, 4, 4, 4, 2, 2, 8, 4, 1, 5, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 7, 4, 8},
                    {2, 2, 8, 8, 8, 8, 8, 2, 2, 4, 4, 4, 2, 2, 8, 4, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 4, 8},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 2, 2, 8, 4, 4, 1, 1, 7, 7, 4, 4, 4, 4, 4, 4, 4, 4, 4, 8, 2},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 2, 2, 2, 8, 4, 4, 4, 4, 4, 4, 8, 8, 8, 8, 8, 8, 8, 8, 2, 2},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 2, 2, 2, 2, 8, 8, 8, 8, 8, 8, 2, 2, 2, 8, 8, 8, 8, 8, 8, 8},
                    {2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 8, 4, 4, 4, 4, 4, 4, 8},
                    {2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 4, 4, 0, 0, 0, 0, 0, 0, 4},
                    {2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 8, 8, 4, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 0, 0, 0, 0, 8, 8, 8, 0, 0, 0},
                    {7, 7, 7, 7, 7, 2, 8, 8, 8, 8, 8, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 4, 0, 0, 0, 0, 8, 5, 8, 0, 0, 0},
                    {7, 4, 8, 4, 7, 2, 8, 8, 8, 8, 8, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 0, 8, 8, 8, 0, 0, 0},
                    {7, 8, 8, 8, 7, 2, 8, 8, 8, 8, 8, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {7, 4, 8, 4, 7, 2, 2, 8, 8, 8, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {7, 7, 7, 7, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            });
        } // stage 1

        {
            stages.add(new int[][]{
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0},
                    {4, 4, 4, 6, 6, 6, 6, 6, 6, 4, 4, 0, 4, 4, 4, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0},
                    {8, 5, 6, 5, 8, 8, 8, 8, 6, 6, 4, 0, 0, 4, 4, 7, 2, 2, 2, 3, 2, 2, 2, 3, 2, 2, 7, 0, 0, 0, 0, 0},
                    {8, 6, 6, 8, 8, 8, 8, 8, 8, 6, 6, 4, 0, 0, 0, 7, 3, 2, 2, 2, 2, 2, 2, 3, 2, 2, 7, 0, 0, 5, 0, 0},
                    {8, 6, 8, 8, 6, 6, 6, 6, 8, 8, 6, 6, 0, 4, 0, 7, 2, 2, 2, 7, 7, 7, 7, 2, 2, 2, 7, 0, 7, 7, 7, 0},
                    {8, 6, 8, 6, 6, 6, 6, 6, 6, 8, 6, 6, 0, 0, 0, 7, 2, 2, 3, 7, 0, 0, 7, 2, 2, 2, 7, 0, 7, 5, 7, 0},
                    {2, 6, 8, 6, 8, 4, 8, 6, 6, 8, 6, 6, 0, 0, 0, 7, 2, 2, 2, 7, 0, 0, 7, 2, 2, 2, 7, 0, 7, 8, 7, 0},
                    {2, 6, 8, 6, 8, 6, 6, 6, 6, 8, 6, 6, 0, 0, 0, 7, 2, 2, 2, 7, 0, 0, 7, 3, 3, 3, 7, 0, 7, 8, 7, 0},
                    {2, 6, 6, 6, 8, 8, 6, 6, 8, 8, 6, 6, 0, 0, 0, 7, 1, 1, 1, 7, 0, 0, 7, 2, 2, 2, 7, 0, 7, 8, 7, 0},
                    {2, 2, 6, 6, 6, 8, 8, 8, 8, 6, 6, 6, 0, 0, 0, 7, 7, 0, 7, 7, 0, 0, 7, 2, 2, 2, 7, 0, 7, 8, 7, 0},
                    {2, 2, 2, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 2, 2, 2, 7, 0, 7, 8, 7, 0},
                    {2, 2, 2, 2, 6, 6, 6, 6, 6, 6, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 3, 3, 3, 7, 0, 7, 8, 7, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 7, 2, 2, 2, 7, 0, 7, 7, 7, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 4, 4, 0, 0, 0, 4, 4, 4, 0, 0, 7, 2, 2, 2, 7, 0, 0, 0, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 4, 4, 4, 0, 0, 0, 4, 4, 4, 0, 0, 7, 3, 3, 3, 7, 0, 4, 4, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 2, 2, 2, 7, 0, 4, 4, 4, 0},
                    {2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 4, 4, 0, 0, 0, 4, 4, 0, 0, 4, 0, 0, 7, 3, 3, 3, 7, 0, 0, 4, 4, 0},
                    {2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 7, 2, 2, 2, 7, 0, 0, 0, 0, 0},
                    {2, 2, 2, 2, 2, 2, 8, 0, 0, 4, 0, 4, 4, 4, 0, 0, 4, 4, 0, 7, 7, 7, 7, 7, 2, 7, 7, 7, 7, 7, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 0, 0},
                    {2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 4, 4, 4, 4, 4, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 1, 7, 4, 0},
                    {2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 4, 4, 4, 4, 4, 7, 2, 2, 2, 2, 2, 7, 8, 8, 8, 8, 8, 8, 8, 7, 7, 8},
                    {2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 4, 4, 4, 4, 7, 2, 2, 2, 2, 2, 8, 8, 8, 8, 8, 8, 8, 5, 7, 5, 8},
                    {2, 2, 2, 2, 2, 2, 2, 8, 0, 0, 0, 4, 4, 4, 4, 7, 2, 2, 2, 2, 2, 7, 8, 8, 8, 8, 8, 8, 8, 7, 7, 8},
                    {8, 2, 2, 2, 2, 2, 8, 0, 0, 4, 0, 0, 4, 4, 4, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 1, 7, 4, 0},
                    {0, 8, 2, 2, 2, 8, 0, 0, 4, 4, 4, 0, 0, 4, 0, 0, 4, 4, 4, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 4, 4},
                    {0, 0, 8, 8, 8, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 4, 4, 4, 4, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 4, 4},
                    {4, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4},
            });
        } // stage 2

        {
            stages.add(new int[][]{
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},

                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            });
        } // empty stage
    }

    // CONSTRUCTOR

    public Map(int stage) {
        int[][] pattern = stages.get(stage);
        for (int y = 0; y < SnakeGame.SIZE; y++) {
            for (int x = 0; x < SnakeGame.SIZE; x++) {
                int terrainType = pattern[y][x];
                terrainMatrix[y][x] = Terrain.create(x, y, terrainType);
            }
        }
        switch (stage) {
            case 0: {
                orbs.add(Orb.create(4, 8, Element.WATER));
                orbs.add(Orb.create(27, 27, Element.FIRE));
                orbs.add(Orb.create(15, 18, Element.EARTH));
                orbs.add(Orb.create(15, 9, Element.AIR));
                orbs.add(Orb.create(25, 10, Element.ALMIGHTY));
                wormHoles.add(new WormHole(6, 6, 12, 6));
                wormHoles.add(new WormHole(1, 30, 30, 5));
                snakeStartPlace = new Coordinate(6, 28);
                snakeStartDirection = Direction.UP;
                break;
            }
            case 1: {
                orbs.add(Orb.create(23, 6, Element.WATER));
                orbs.add(Orb.create(9, 28, Element.FIRE));
                orbs.add(Orb.create(2, 6, Element.EARTH));
                orbs.add(Orb.create(14, 12, Element.AIR));
                orbs.add(Orb.create(2, 29, Element.ALMIGHTY));
                wormHoles.add(new WormHole(14, 7, 4, 14));
                wormHoles.add(new WormHole(8, 5, 27, 27));
                wormHoles.add(new WormHole(5, 6, 17, 17));
                wormHoles.add(new WormHole(11, 6, 17, 6));
                snakeStartPlace = new Coordinate(27, 4);
                snakeStartDirection = Direction.LEFT;
                break;
            }
            case 2: {
                orbs.add(Orb.create(16, 26, Element.WATER));
                orbs.add(Orb.create(23, 28, Element.FIRE));
                orbs.add(Orb.create(29, 11, Element.EARTH));
                orbs.add(Orb.create(12, 25, Element.AIR));
                orbs.add(Orb.create(6, 10, Element.ALMIGHTY));
                wormHoles.add(new WormHole(29, 9, 29, 7));
                wormHoles.add(new WormHole(18, 23, 20, 23));
                wormHoles.add(new WormHole(30, 26, 28, 26));
                wormHoles.add(new WormHole(1, 6, 3, 6));
                snakeStartPlace = new Coordinate(25, 26);
                snakeStartDirection = Direction.LEFT;
                break;
            }
            case 3: {
                orbs.add(Orb.create(1, 6, Element.WATER));
                orbs.add(Orb.create(5, 6, Element.FIRE));
                orbs.add(Orb.create(9, 6, Element.EARTH));
                orbs.add(Orb.create(13, 6, Element.AIR));
                orbs.add(Orb.create(17, 6, Element.ALMIGHTY));
                wormHoles.add(new WormHole(0, 0, 0, 0));
                snakeStartPlace = new Coordinate(10, 10);
                snakeStartDirection = Direction.RIGHT;
                break;
            }
            default:
                break;
        }
    }

    // GETTERS

    public Terrain[][] getTerrainMatrix() {
        return terrainMatrix;
    }

    public Terrain getTerrain(int x, int y) {
        return terrainMatrix[y][x];
    }

    // SETTERS

    public void placeTerrain(int x, int y, TerrainType terrainType) {
        this.terrainMatrix[y][x] = Terrain.create(x, y, terrainType.ordinal());
    }

    public static class Coordinate {
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
