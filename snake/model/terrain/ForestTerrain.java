package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.view.Sign;

public class ForestTerrain extends Terrain {
    public ForestTerrain(int x, int y) {
        super(x, y, TerrainType.FOREST, Color.LIGHTGREEN, Color.FORESTGREEN, randomForestSign());
    }

    private static Sign randomForestSign() {
        final SnakeGame game = SnakeGame.getInstance();

        return game.getRandomNumber(2) == 1 ? Sign.TERRAIN_FOREST_1 : Sign.TERRAIN_FOREST_2;
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement == Element.AIR) return;

        if (snakeElement == Element.FIRE) {
            final GameObject newHead = snake.getNewHead();
            final Stage stage = game.getStage();

            stage.putTerrain(newHead.x, newHead.y, TerrainType.FIRE);
            return;
        }

        snake.kill();
        game.setGameOverReason(Strings.GAME_OVER_EATEN);
    }

    @Override
    public void applyPassiveEffect() {
        igniteFromAdjacentFire();
    }
}
