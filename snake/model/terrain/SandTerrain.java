package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class SandTerrain extends Terrain {
    public SandTerrain(int x, int y) {
        super(x, y, TerrainType.SAND, Color.YELLOW, Color.SANDYBROWN, Sign.TERRAIN_SAND);
    }
}
