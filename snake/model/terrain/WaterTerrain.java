package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class WaterTerrain extends Terrain {
    public WaterTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.WATER;
        this.color = Color.LIGHTBLUE;
        this.backgroundColor = Color.DEEPSKYBLUE;
        this.sign = Sign.getSign(Sign.TERRAIN_WATER);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement == Element.WATER) return;
        if (snakeElement == Element.AIR) return;

        if (snakeElement == Element.FIRE) {
            snake.removeTail();
        }

        snake.reduceBreath();

        if (snake.isSuffocated()){
            snake.kill();
            game.setGameOverReason(Strings.GAME_OVER_DROWNED);
        }
    }
}
