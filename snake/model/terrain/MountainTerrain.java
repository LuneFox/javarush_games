package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class MountainTerrain extends Terrain {
    public MountainTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.MOUNTAIN;
        this.color = Color.ORANGE;
        this.backgroundColor = Color.BROWN;
        this.sign = Sign.getSign(Sign.TERRAIN_MOUNTAIN);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        if (snake.getElement() == Element.ALMIGHTY) return;

        snake.kill();
        game.setGameOverReason(Strings.GAME_OVER_MOUNTAIN_COLD);
    }
}
