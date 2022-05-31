package com.javarush.games.snake.model.terrain;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.Sign;

public class SandTerrain extends Terrain {
    public SandTerrain(int x, int y) {
        super(x, y);
        this.type = TerrainType.SAND;
        this.color = Color.YELLOW;
        this.backgroundColor = Color.SANDYBROWN;
        this.sign = Sign.getSign(Sign.TERRAIN_SAND);
    }
}
