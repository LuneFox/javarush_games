package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Вода: не-водяная змейка может утонуть. */
public class WaterTerrain extends Terrain {
    /** Создаёт клетку воды. */
    public WaterTerrain(int x, int y) {
        super(x, y, TerrainType.WATER, Color.LIGHTBLUE, Color.DEEPSKYBLUE, Sign.TERRAIN_WATER);
    }

    /** Реагирует на попадание змейки в клетку. */
    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement == Element.WATER) return;
        if (snakeElement == Element.AIR) return;

        if (snakeElement == Element.FIRE) {
            snake.loseTail();
        }

        snake.reduceBreath();

        if (snake.isDrowned()) {
            snake.kill();
            game.setGameOverReason(Strings.GAME_OVER_DROWNED);
        }
    }
}
