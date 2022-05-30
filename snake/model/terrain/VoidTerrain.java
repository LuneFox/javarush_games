package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class VoidTerrain extends Terrain {
    public VoidTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.VOID;
        this.color = Color.BLACK;
        this.backgroundColor = Color.BLACK;
        this.sign = Sign.getSign(Sign.TERRAIN_VOID);
    }
}
