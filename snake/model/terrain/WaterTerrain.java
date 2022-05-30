package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class WaterTerrain extends Terrain {
    public WaterTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.WATER;
        this.color = Color.LIGHTBLUE;
        this.backgroundColor = Color.DEEPSKYBLUE;
        this.sign = Sign.getSign(Sign.TERRAIN_WATER);
    }
}
