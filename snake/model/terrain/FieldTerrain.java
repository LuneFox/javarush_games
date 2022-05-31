package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class FieldTerrain extends Terrain {
    public FieldTerrain(int x, int y) {
        super(x, y);
        this.type = TerrainType.FIELD;
        this.color = Color.LIGHTGREEN;
        this.backgroundColor = Color.PALEGREEN;
        this.sign = Sign.getSign(Sign.TERRAIN_FIELD);
    }
}
