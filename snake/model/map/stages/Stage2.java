package com.javarush.games.snake.model.map.stages;

import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Coordinate;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.WormHole;
import com.javarush.games.snake.model.orbs.Orb;

import java.util.ArrayList;

public class Stage2 extends Stage {
    public Stage2() {
        name = "Stage 2";
    }

    @Override
    protected void createTerrainCodeMatrix() {
        terrainCodeMatrix = new int[][]{VOID_ROW, VOID_ROW, VOID_ROW, VOID_ROW,
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
        };
    }

    @Override
    protected void createOrbs() {
        orbs.add(Orb.create(23, 6, Element.WATER));
        orbs.add(Orb.create(9, 28, Element.FIRE));
        orbs.add(Orb.create(2, 6, Element.EARTH));
        orbs.add(Orb.create(14, 12, Element.AIR));
        orbs.add(Orb.create(2, 29, Element.ALMIGHTY));
    }

    @Override
    protected void createWormHoles() {
        wormHoles.add(new WormHole(14, 7, 4, 14));
        wormHoles.add(new WormHole(8, 5, 27, 27));
        wormHoles.add(new WormHole(5, 6, 17, 17));
        wormHoles.add(new WormHole(11, 6, 17, 6));
    }

    @Override
    protected void defineSnakeStartingPlace() {
        snakeStartPlace = new Coordinate(27, 4);
        snakeStartDirection = Direction.LEFT;
    }

    @Override
    protected void createMessages() {
        briefingMessage = "STAGE 2\nCollect the Almighty Orb!";
        completeMessage = "STAGE 2 COMPLETE!";
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
