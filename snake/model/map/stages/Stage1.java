package com.javarush.games.snake.model.map.stages;

import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Coordinate;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.WormHole;
import com.javarush.games.snake.model.orbs.Orb;

public class Stage1 extends Stage {
    public Stage1() {
        this.name = "STAGE 1";
    }

    @Override
    protected void createTerrainCodeMatrix() {
        terrainCodeMatrix = new int[][]{VOID_ROW, VOID_ROW, VOID_ROW, VOID_ROW,
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
        };
    }

    @Override
    protected void createOrbs() {
        orbs.add(Orb.create(4, 8, Element.WATER));
        orbs.add(Orb.create(27, 27, Element.FIRE));
        orbs.add(Orb.create(15, 18, Element.EARTH));
        orbs.add(Orb.create(15, 9, Element.AIR));
        orbs.add(Orb.create(25, 10, Element.ALMIGHTY));
    }

    @Override
    protected void createWormHoles() {
        wormHoles.add(new WormHole(6, 6, 12, 6));
        wormHoles.add(new WormHole(1, 30, 30, 5));
    }

    @Override
    protected void defineSnakeStartingPlace() {
        snakeStartPlace = new Coordinate(6, 28);
        snakeStartDirection = Direction.UP;
    }

    @Override
    protected void createMessages() {
        briefingMessage = name +
                "\nCollect the Orb of Power!" +
                "\nBecome the God of Snakes and destroy the realm!" +
                "\n\n~ PRESS SPACE TO START ~";
        completeMessage = "STAGE 1 COMPLETE!";
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
