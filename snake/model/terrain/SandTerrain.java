package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

/** Песок: нейтральная местность. */
public class SandTerrain extends Terrain {
    /** Создаёт клетку песка. */
    public SandTerrain(int x, int y) {
        super(x, y, TerrainType.SAND, Color.YELLOW, Color.SANDYBROWN, Sign.TERRAIN_SAND);
    }
}
