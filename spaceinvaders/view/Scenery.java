package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.view.shapes.DecoShape;

/**
 * The background of the game: sky, clouds, hills, bushes and the ground. The decorative objects are
 * owned by this instance, not shared statically.
 */
public class Scenery implements Drawable {
    private static final int NIGHT_SKY_LAST_ROW = 60;
    private static final int HORIZON_ROW = 63;
    private static final int TWILIGHT_ROW = 64;
    private static final int DAWN_FIRST_ROW = 65;
    private static final int DAWN_LAST_ROW = 66;
    private static final int MORNING_FIRST_ROW = 67;
    private static final int MORNING_LAST_ROW = 69;

    private static final int HILLS_COUNT = 2;
    private static final int BUSHES_COUNT = 3;
    private static final int FLOOR_TILES_COUNT = 7;
    private static final int HILLS_OFFSET_X = 14;
    private static final double HILLS_GAP = 1.8;

    private final SpaceInvadersGame game;
    private final GameObject cloud = new GameObject();
    private final GameObject hill = new GameObject();
    private final GameObject bush = new GameObject();
    private final GameObject floorTile = new GameObject();

    public Scenery(SpaceInvadersGame game) {
        this.game = game;
        cloud.setStaticView(DecoShape.CLOUD);
        hill.setStaticView(DecoShape.HILL);
        bush.setStaticView(DecoShape.BUSH);
        floorTile.setStaticView(DecoShape.FLOOR);
    }

    @Override
    public void draw() {
        drawSky();
        drawClouds();
        drawHills();
        drawBushes();
        drawFloor();
    }

    private void drawSky() {
        for (int y = 0; y < GameField.HEIGHT; y++) {
            Color skyColor = skyColorAt(y);
            for (int x = 0; x < GameField.WIDTH; x++) {
                game.getScreen().setPixel(x, y, skyColor);
            }
        }
    }

    private Color skyColorAt(int y) {
        if (y <= NIGHT_SKY_LAST_ROW) return Color.BLACK;
        if (y == HORIZON_ROW) return Color.MIDNIGHTBLUE;
        if (y == TWILIGHT_ROW) return Color.DARKSLATEBLUE;
        if (y == DAWN_FIRST_ROW || y == DAWN_LAST_ROW) return Color.ROYALBLUE;
        if (y >= MORNING_FIRST_ROW && y <= MORNING_LAST_ROW) return Color.DODGERBLUE;
        return Color.DEEPSKYBLUE;
    }

    private void drawClouds() {
        cloud.setPosition(5, 65);
        cloud.draw();
        cloud.setPosition(38, 57);
        cloud.draw();
        cloud.setPosition(81, 72);
        cloud.draw(Mirror.HORIZONTAL);
    }

    private void drawHills() {
        double y = GameField.HEIGHT - DecoShape.FLOOR.length - DecoShape.HILL.length;
        for (int i = 0; i < HILLS_COUNT; i++) {
            hill.setPosition(HILLS_GAP * i * hill.getWidth() + HILLS_OFFSET_X, y);
            hill.draw();
        }
    }

    private void drawBushes() {
        double y = GameField.HEIGHT - DecoShape.FLOOR.length - DecoShape.BUSH.length;
        for (int i = 0; i < BUSHES_COUNT; i++) {
            bush.setPosition(2 * i * bush.getWidth(), y);
            bush.draw();
        }
    }

    private void drawFloor() {
        double y = GameField.HEIGHT - DecoShape.FLOOR.length;
        for (int i = 0; i < FLOOR_TILES_COUNT; i++) {
            floorTile.setPosition(i * floorTile.getWidth(), y);
            floorTile.draw();
        }
    }
}
