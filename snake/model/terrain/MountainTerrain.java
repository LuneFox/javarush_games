package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class MountainTerrain extends Terrain {
    public MountainTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.MOUNTAIN;
        this.color = Color.ORANGE;
        this.backgroundColor = Color.BROWN;
        this.sign = Sign.getSign(Sign.TERRAIN_MOUNTAIN);
    }
}
