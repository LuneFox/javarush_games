package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class WallTerrain extends Terrain {
    public WallTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.WALL;
        this.color = Color.WHITE;
        this.backgroundColor = Color.GRAY;
        this.sign = Sign.getSign(Sign.TERRAIN_WALL);
    }
}
