package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.WormHole;
import com.javarush.games.snake.view.Sign;

/** Червоточина для земляной змейки. */
public class WormholeTerrain extends Terrain {
    /** Создаёт клетку червоточины. */
    public WormholeTerrain(int x, int y) {
        super(x, y, TerrainType.WORMHOLE, Color.PURPLE, Color.BLACK, Sign.TERRAIN_WORMHOLE);
    }

    /** Реагирует на попадание змейки в клетку. */
    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        switch (snake.getElement()) {
            case ALMIGHTY:
            case AIR:
                return;
            case EARTH:
                warp(snake);
                return;
            default:
                snake.kill();
                game.setGameOverReason(Strings.GAME_OVER_LOST);
        }
    }

    /** Переносит змейку между входом и выходом. */
    private void warp(Snake snake) {
        final Stage stage = game.getStage();

        for (WormHole wormHole : stage.getWormHoles()) {
            if (wormHole.isEntranceAt(x, y)) {
                wormHole.warpToExit(snake);
                return;
            }

            if (wormHole.isExitAt(x, y)) {
                wormHole.warpToEntrance(snake);
                return;
            }
        }
    }
}
