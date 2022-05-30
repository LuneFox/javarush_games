package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class ForestTerrain extends Terrain {
    public ForestTerrain(int x, int y) {
        super(x, y);
        this.terrainType = TerrainType.FOREST;
        this.color = Color.LIGHTGREEN;
        this.backgroundColor = Color.FORESTGREEN;
        this.sign = (game.getRandomNumber(2) == 1)
                ? Sign.getSign(Sign.TERRAIN_FOREST_1)
                : Sign.getSign(Sign.TERRAIN_FOREST_2);
    }
}
