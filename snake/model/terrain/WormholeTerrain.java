package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class WormholeTerrain extends Terrain {
    public WormholeTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.WORMHOLE;
        this.color = Color.PURPLE;
        this.backgroundColor = Color.BLACK;
        this.sign = Sign.getSign(Sign.TERRAIN_WORMHOLE);
    }
}
