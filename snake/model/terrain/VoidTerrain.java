package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class VoidTerrain extends Terrain {
    public VoidTerrain(int x, int y) {
        super(x, y, TerrainType.VOID, Color.BLACK, Color.BLACK, Sign.TERRAIN_VOID);
    }
}
