package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class WallTerrain extends Terrain {
    public WallTerrain(int x, int y) {
        super(x, y);
        this.type = TerrainType.WALL;
        this.color = Color.WHITE;
        this.backgroundColor = Color.GRAY;
        this.sign = Sign.getSign(Sign.TERRAIN_WALL);
    }

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
