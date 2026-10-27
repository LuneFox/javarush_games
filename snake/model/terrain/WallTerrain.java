package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Стена: проходима для воздушной и всемогущей. */
public class WallTerrain extends Terrain {
    /** Создаёт клетку стены. */
    public WallTerrain(int x, int y) {
        super(x, y, TerrainType.WALL, Color.WHITE, Color.GRAY, Sign.TERRAIN_WALL);
    }

    /** Реагирует на попадание змейки в клетку. */
    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.AIR) return;
        if (snakeElement == Element.ALMIGHTY) return;

        snake.kill();
        game.setGameOverReason(Strings.GAME_OVER_BUMPED);
    }
}
