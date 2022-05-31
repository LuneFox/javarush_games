package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.stages.Map;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class FireTerrain extends Terrain {
    public FireTerrain(int x, int y) {
        super(x, y);
        this.type = TerrainType.FIRE;
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
                final GameObject newHead = snake.getNewHead();
                final int x = newHead.x;
                final int y = newHead.y;
                map.putTerrain(x, y, TerrainType.WOOD);
                map.getTerrain(x, y).isWet = true;
                break;
            default:
                break;
        }
    }
}
