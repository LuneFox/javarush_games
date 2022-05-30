package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Map;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.WormHole;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class WormholeTerrain extends Terrain {
    public WormholeTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.WORMHOLE;
        this.color = Color.PURPLE;
        this.backgroundColor = Color.BLACK;
        this.sign = Sign.getSign(Sign.TERRAIN_WORMHOLE);
    }

    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement == Element.AIR) return;

        if (snakeElement == Element.EARTH) {
            warp(snake);
            return;
        }

        snake.kill();
        game.setGameOverReason(Strings.GAME_OVER_LOST);
    }

    private void warp(Snake snake) {
        Map map = game.getMap();

        for (WormHole wormHole : map.wormHoles) {
            if (x == wormHole.location.x && y == wormHole.location.y) {
                snake.warpToDestination(wormHole);
            } else if (x == wormHole.destination.x && y == wormHole.destination.y) {
                snake.warpToLocation(wormHole);
            }
        }
    }
}
