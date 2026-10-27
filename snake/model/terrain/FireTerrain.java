package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.view.Sign;
import com.javarush.games.snake.view.SignType;

import java.util.Date;

/** Огонь: сжигает змейку и плавит воду. */
public class FireTerrain extends Terrain {
    private static final long FLAME_PERIOD_MS = 300;
    private static final int SMALL_FLAME_TEXT_SIZE = 75;

    /** Создаёт клетку огня. */
    public FireTerrain(int x, int y) {
        super(x, y, TerrainType.FIRE, Color.YELLOW, Color.ORANGERED, Sign.TERRAIN_FIRE);
    }

    /** Рисует огонь: кандзи чередуются, эмодзи пульсирует размером. */
    @Override
    public void draw(Game target) {
        final long tick = new Date().getTime() / FLAME_PERIOD_MS;
        final boolean isFirstFrame = tick % 2 == 0;
        final Sign sign = isFirstFrame ? Sign.TERRAIN_FIRE : Sign.TERRAIN_FIRE_2;
        final String label = Sign.getSign(sign);

        if (Sign.getUsedType() == SignType.EMOJI) {
            final int textSize = isFirstFrame ? SMALL_FLAME_TEXT_SIZE : SYMBOL_TEXT_SIZE;
            drawCell(target, label, textSize);
            return;
        }

        drawCell(target, label);
    }

    /** Реагирует на попадание змейки в клетку. */
    @Override
    public void interact(Snake snake) {
        super.interact(snake);
        handleFireContact(snake);
    }

    /** Реагирует на огонь под любой частью тела змейки. */
    @Override
    public void onSnakeContact(Snake snake) {
        handleFireContact(snake);
    }

    /** Применяет последствия касания огня. */
    private void handleFireContact(Snake snake) {
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
                stage.putTerrain(x, y, TerrainType.WOOD);
                stage.getTerrain(x, y).makeWet();
                break;
            default:
                break;
        }
    }
}
