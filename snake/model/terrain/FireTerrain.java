package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.view.Sign;

/** Огонь: сжигает змейку и плавит воду. */
public class FireTerrain extends Terrain {
    /** Создаёт клетку огня. */
    public FireTerrain(int x, int y) {
        super(x, y, TerrainType.FIRE, Color.YELLOW, Color.ORANGERED, Sign.TERRAIN_FIRE);
    }

    /** Реагирует на попадание змейки в клетку. */
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
                final Stage stage = game.getStage();
                final GameObject newHead = snake.getNewHead();
                stage.putTerrain(newHead.x, newHead.y, TerrainType.WOOD);
                stage.getTerrain(newHead.x, newHead.y).makeWet();
                break;
            default:
                break;
        }
    }
}
