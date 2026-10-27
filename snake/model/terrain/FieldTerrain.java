package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

/** Травяное поле. */
public class FieldTerrain extends Terrain {
    /** Создаёт клетку «поле». */
    public FieldTerrain(int x, int y) {
        super(x, y, TerrainType.FIELD, Color.LIGHTGREEN, Color.PALEGREEN, Sign.TERRAIN_FIELD);
    }
}
