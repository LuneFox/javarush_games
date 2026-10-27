package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.view.Sign;

public class WoodTerrain extends Terrain {
    public WoodTerrain(int x, int y) {
        super(x, y, TerrainType.WOOD, Color.SANDYBROWN, Color.SADDLEBROWN, Sign.TERRAIN_WOOD);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement != Element.FIRE && snakeElement != Element.WATER) return;

        final GameObject newHead = snake.getNewHead();
        final Stage stage = game.getStage();

        switch (snakeElement) {
            case FIRE:
                stage.putTerrain(newHead.x, newHead.y, TerrainType.FIRE);
                break;
            case WATER:
                stage.getTerrain(newHead.x, newHead.y).makeWet();
                break;
            default:
                break;
        }
    }

    @Override
    public void applyPassiveEffect() {
        igniteFromAdjacentFire();
    }
}
