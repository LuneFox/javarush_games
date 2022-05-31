package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Map;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class WoodTerrain extends Terrain {
    public WoodTerrain(int x, int y) {
        super(x, y);
        this.type = TerrainType.WOOD;
        this.color = Color.SANDYBROWN;
        this.backgroundColor = Color.SADDLEBROWN;
        this.sign = Sign.getSign(Sign.TERRAIN_WOOD);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;

        final Map map = game.getMap();
        final GameObject newHead = snake.getNewHead();
        final int x = newHead.x;
        final int y = newHead.y;

        switch (snake.getElement()) {
            case FIRE:
                map.putTerrain(x, y, TerrainType.FIRE);
                break;
            case WATER:
                final Terrain terrain = map.getTerrain(x, y);
                terrain.makeWet();
                break;
            default:
                break;
        }
    }

    @Override
    public void processPassiveEffects() {
        igniteOnContactWithFire();
    }
}
