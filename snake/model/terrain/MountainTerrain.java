package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Гора: смертельна для обычной змейки. */
public class MountainTerrain extends Terrain {
    /** Создаёт клетку горы. */
    public MountainTerrain(int x, int y) {
        super(x, y, TerrainType.MOUNTAIN, Color.ORANGE, Color.BROWN, Sign.TERRAIN_MOUNTAIN);
    }

    /** Реагирует на попадание змейки в клетку. */
    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        if (snake.getElement() == Element.ALMIGHTY) return;

        snake.kill();
        game.setGameOverReason(Strings.GAME_OVER_MOUNTAIN);
    }
}
