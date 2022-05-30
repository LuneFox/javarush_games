package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Map;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class FireTerrain extends Terrain {
    public FireTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.FIRE;
        this.color = Color.YELLOW;
        this.backgroundColor = Color.ORANGERED;
        this.sign = Sign.getSign(Sign.TERRAIN_FIRE);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;

        switch (snakeElement) {
            case NEUTRAL:
            case EARTH:
                snake.kill();
                game.setGameOverReason(Strings.GAME_OVER_BURNED);
                break;
            case WATER:
                final Map map = game.getMap();
                final int headX = snake.getNewHeadX();
                final int headY = snake.getNewHeadY();
                map.putTerrain(headX, headY, TerrainType.WOOD);
                map.getTerrain(headX, headY).isWet = true;
                break;
            default:
                break;
        }
    }
}
