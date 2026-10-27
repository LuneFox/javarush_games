package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

/** Пустота: клетка вне игры. */
public class VoidTerrain extends Terrain {
    /** Создаёт клетку пустоты. */
    public VoidTerrain(int x, int y) {
        super(x, y, TerrainType.VOID, Color.BLACK, Color.BLACK, Sign.TERRAIN_VOID);
    }
}
