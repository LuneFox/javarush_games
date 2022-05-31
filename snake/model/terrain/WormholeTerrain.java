package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.WormHole;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class WormholeTerrain extends Terrain {
    public WormholeTerrain(int x, int y) {
        super(x, y);
        this.type = TerrainType.WORMHOLE;
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
        final Stage stage = game.getStage();

        for (WormHole wormHole : stage.getWormHoles()) {
            if (wormHole.locationIsAt(x, y)) {
                wormHole.warpToDestination(snake);
            } else if (wormHole.destinationIsAt(x, y)) {
                wormHole.warpToBeginning(snake);
            }
        }
    }
}
