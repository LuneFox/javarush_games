package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.view.Sign;

import java.util.Date;

/** Травяное поле, которое приминается под змейкой. */
public class FieldTerrain extends Terrain {
    private static final long TRAMPLE_DELAY_MS = 1000;
    private static final long STAGE_DURATION_MS = 300;
    private static final String TRAMPLED_SIGN = " ";
    private static final String BENT_SIGN = "v";
    private static final String RECOVERING_SIGN = "w";

    private boolean isTrampled;
    private long lastTouchedMillis;

    /** Создаёт клетку «поле». */
    public FieldTerrain(int x, int y) {
        super(x, y, TerrainType.FIELD, Color.LIGHTGREEN, Color.PALEGREEN, Sign.TERRAIN_FIELD);
    }

    /** Рисует траву в одном из состояний примятости. */
    @Override
    public void draw(Game target) {
        if (!isTrampled) {
            drawDefaultCell(target);
            return;
        }

        final long elapsed = new Date().getTime() - lastTouchedMillis;

        if (elapsed < TRAMPLE_DELAY_MS) {
            drawCell(target, TRAMPLED_SIGN);
        } else if (elapsed < TRAMPLE_DELAY_MS + STAGE_DURATION_MS) {
            drawCell(target, BENT_SIGN);
        } else if (elapsed < TRAMPLE_DELAY_MS + STAGE_DURATION_MS * 2) {
            drawCell(target, RECOVERING_SIGN);
        } else {
            isTrampled = false;
            drawDefaultCell(target);
        }
    }

    /** Запоминает, что змейка находится на траве. */
    @Override
    public void onSnakeThrough() {
        isTrampled = true;
        lastTouchedMillis = new Date().getTime();
    }

    /** Запоминает, что хвост покинул траву. */
    @Override
    public void onSnakeLeft() {
        if (!isTrampled) return;

        lastTouchedMillis = new Date().getTime();
    }
}
