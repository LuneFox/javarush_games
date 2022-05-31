package com.javarush.games.snake.model.map.stages;

import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Coordinate;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.WormHole;
import com.javarush.games.snake.model.orbs.Orb;

import java.util.ArrayList;

public class Stage3 extends Stage {
    public Stage3() {
        name = "Stage 3";
    }

    @Override
    protected void createTerrainCodeMatrix() {
        terrainCodeMatrix = new int[][]{VOID_ROW, VOID_ROW, VOID_ROW, VOID_ROW,
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
        };
    }

    @Override
    protected void createOrbs() {
        orbs.add(Orb.create(16, 26, Element.WATER));
        orbs.add(Orb.create(23, 28, Element.FIRE));
        orbs.add(Orb.create(29, 11, Element.EARTH));
        orbs.add(Orb.create(12, 25, Element.AIR));
        orbs.add(Orb.create(6, 10, Element.ALMIGHTY));
    }

    @Override
    protected void createWormHoles() {
        wormHoles.add(new WormHole(29, 9, 29, 7));
        wormHoles.add(new WormHole(18, 23, 20, 23));
        wormHoles.add(new WormHole(30, 26, 28, 26));
        wormHoles.add(new WormHole(1, 6, 3, 6));
    }

    @Override
    protected void defineSnakeStartingPlace() {
        snakeStartPlace = new Coordinate(25, 26);
        snakeStartDirection = Direction.LEFT;
    }

    @Override
    protected void createMessages() {
        briefingMessage = "STAGE 3\nCollect the Orb of Power!" +
                "\nBecome the God of Snakes and destroy the realm!" +
                "\n\n~ PRESS SPACE TO START ~";
        completeMessage = "STAGE 3 COMPLETE!" +
                "\n\nThis was the last stage... for now!";
    }

    @Override
    public boolean isCompleted() {
        Snake snake = game.getSnake();
        if (snake.getAlmightyPower() <= 0) {
            isClear = true;
            return true;
        }
        return false;
    }
}
