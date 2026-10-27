package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

import java.util.Date;

/** Вода: не-водяная змейка может утонуть. */
public class WaterTerrain extends Terrain {
    private static final long WAVE_PERIOD_MS = 400;
    private static final int WAVE_WIDTH = 2;
    private static final Color WAVE_COLOR_1 = Color.LIGHTBLUE;
    private static final Color WAVE_COLOR_2 = Color.LIGHTSKYBLUE;

    /** Создаёт клетку воды. */
    public WaterTerrain(int x, int y) {
        super(x, y, TerrainType.WATER, Color.LIGHTBLUE, Color.DEEPSKYBLUE, Sign.TERRAIN_WATER);
    }

    /** Рисует воду с движущимися волнами. */
    @Override
    public void draw(Game target) {
        drawCell(target, waveColor());
    }

    /** Выбирает цвет волны по позиции и времени. */
    private Color waveColor() {
        final long tick = new Date().getTime() / WAVE_PERIOD_MS;
        final int phase = (int) ((x + y + tick) % (WAVE_WIDTH * 2));

        return phase < WAVE_WIDTH ? WAVE_COLOR_1 : WAVE_COLOR_2;
    }

    /** Реагирует на попадание змейки в клетку. */
    @Override
    public void interact(Snake snake) {
        super.interact(snake);

        final Element snakeElement = snake.getElement();

        if (snakeElement == Element.ALMIGHTY) return;
        if (snakeElement == Element.WATER) return;
        if (snakeElement == Element.AIR) return;

        if (snakeElement == Element.FIRE) {
            snake.loseTail();
        }

        snake.reduceBreath();

        if (snake.isDrowned()) {
            snake.kill();
            game.setGameOverReason(Strings.GAME_OVER_DROWNED);
        }
    }
}
