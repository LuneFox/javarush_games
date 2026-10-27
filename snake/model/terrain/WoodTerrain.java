package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Деревянный пол: горит и намокает. */
public class WoodTerrain extends Terrain {
    /** Создаёт деревянный пол. */
    public WoodTerrain(int x, int y) {
        super(x, y, TerrainType.WOOD, Color.SANDYBROWN, Color.SADDLEBROWN, Sign.TERRAIN_WOOD);
    }

    /** Реагирует на попадание змейки в клетку. */
    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement != Element.FIRE && snakeElement != Element.WATER) return;

        switch (snakeElement) {
            case FIRE:
                game.getStage().putTerrain(x, y, TerrainType.FIRE);
                break;
            case WATER:
                makeWet();
                break;
            default:
                break;
        }
    }

    /** Поддерживает древесину мокрой под водяной змейкой. */
    @Override
    public void onSnakeContact(Snake snake) {
        if (snake.getElement() == Element.WATER) {
            makeWet();
        }
    }

    /** Применяет пассивный эффект клетки за ход. */
    @Override
    public void applyPassiveEffect() {
        dryOutIfNeeded();
        igniteFromAdjacentFire();
    }
}
