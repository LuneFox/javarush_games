package com.javarush.games.snake.model.map.stages;

import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Coordinate;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.WormHole;
import com.javarush.games.snake.model.orbs.AirOrb;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.view.Sign;

public class Tutorial5 extends Stage {
    public Tutorial5() {
        super(Strings.STAGE_TUTORIAL_5);
    }

    @Override
    protected void createTerrainCodeMatrix() {
        terrainCodeMatrix = new int[][]{VOID_ROW, VOID_ROW, VOID_ROW, VOID_ROW,
                {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                {7, 7, 5, 7, 7, 7, 7, 7, 5, 7, 7, 7, 7, 7, 7, 5, 7, 7, 7, 7, 7, 7, 7, 5, 7, 7, 7, 7, 7, 5, 7, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 7},
                {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 7, 7, 5, 7, 0, 0, 7, 7, 5, 7, 0, 0, 0, 7, 5, 7, 7, 0, 0, 0, 7, 5, 7, 7, 0, 0, 7, 5, 7, 7, 0},
                {0, 7, 7, 7, 7, 0, 0, 7, 7, 7, 7, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 7, 7, 7, 7, 0, 0, 7, 7, 7, 7, 0},
                {0, 7, 7, 7, 7, 0, 0, 7, 7, 7, 7, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 7, 7, 7, 7, 0, 0, 7, 7, 7, 7, 0},
                {0, 7, 7, 7, 7, 0, 0, 7, 7, 7, 7, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 7, 7, 7, 7, 0, 0, 7, 7, 7, 7, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
    }

    @Override
    protected void createOrbs() {
        orbs.add(Orb.create(16, 12, Element.AIR));
        orbs.add(Orb.create(15, 22, Element.EARTH));
    }

    @Override
    protected void createWormHoles() {
        wormHoles.add(new WormHole(3, 27, 23, 5));
        wormHoles.add(new WormHole(9, 27, 2, 5));
        wormHoles.add(new WormHole(15, 27, 8, 5));
        wormHoles.add(new WormHole(22, 27, 15, 5));
        wormHoles.add(new WormHole(28, 27, 29, 5));
    }

    @Override
    protected void defineSnakeStartingPlace() {
        snakeStartPlace = new Coordinate(2, 22);
        snakeStartDirection = Direction.RIGHT;
    }

    @Override
    protected void createMessages() {
        briefingMessage = Strings.TUTORIAL_5_BRIEFING.format(getName(),
                Sign.getSign(Sign.ORB_EARTH), Sign.getSign(Sign.TERRAIN_WORMHOLE));
        completeMessage = Strings.TUTORIAL_5_COMPLETE.format(getName());
    }

    @Override
    public boolean isCompleted() {
        if (orbs.stream().noneMatch(orb -> orb instanceof AirOrb)) {
            isClear = true;
            return true;
        }
        return false;
    }
}
