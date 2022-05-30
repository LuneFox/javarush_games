package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class FireTerrain extends Terrain {
    public FireTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.FIRE;
        this.color = Color.YELLOW;
        this.backgroundColor = Color.ORANGERED;
        this.sign = Sign.getSign(Sign.TERRAIN_FIRE);
    }
}
