package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.stages.Map;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class ForestTerrain extends Terrain {
    public ForestTerrain(int x, int y) {
        super(x, y);
        this.type = TerrainType.FOREST;
        this.color = Color.LIGHTGREEN;
        this.backgroundColor = Color.FORESTGREEN;
        this.sign = (game.getRandomNumber(2) == 1)
                ? Sign.getSign(Sign.TERRAIN_FOREST_1)
                : Sign.getSign(Sign.TERRAIN_FOREST_2);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement == Element.AIR) return;

        if (snakeElement == Element.FIRE) {
            final Map map = game.getMap();
            final GameObject newHead = snake.getNewHead();
            final int x = newHead.x;
            final int y = newHead.y;

            map.putTerrain(x, y, TerrainType.FIRE);
            return;
        }

        snake.kill();
        game.setGameOverReason(Strings.GAME_OVER_EATEN);
    }

    @Override
    public void processPassiveEffects() {
        igniteOnContactWithFire();
    }
}
