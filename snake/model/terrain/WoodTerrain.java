package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class WoodTerrain extends Terrain {
    public WoodTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.WOOD;
        this.color = Color.SANDYBROWN;
        this.backgroundColor = Color.SADDLEBROWN;
        this.sign = Sign.getSign(Sign.TERRAIN_WOOD);
    }
}
