package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Map;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class WoodTerrain extends Terrain {
    public WoodTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.WOOD;
        this.color = Color.SANDYBROWN;
        this.backgroundColor = Color.SADDLEBROWN;
        this.sign = Sign.getSign(Sign.TERRAIN_WOOD);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;

        final Map map = game.getMap();
        final int headX = snake.getNewHeadX();
        final int headY = snake.getNewHeadY();

        switch (snake.getElement()) {
            case FIRE:
                map.putTerrain(headX, headY, TerrainType.FIRE);
                break;
            case WATER:
                final Terrain terrain = map.getTerrain(headX, headY);
                terrain.makeWet();
                break;
            default:
                break;
        }
    }

    @Override
    public void processPassiveEffects() {
        igniteOnContactWithFire();
    }
}
