package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.view.shapes.DecoShape;

import static com.javarush.games.spaceinvaders.SpaceInvadersGame.*;

public class Scenery implements Drawable {
    private final SpaceInvadersGame game;

    private static final GameObject cloud = new GameObject() {
        {
            setStaticView(DecoShape.CLOUD);
        }
    };
    private static final GameObject hill = new GameObject() {
        {
            setStaticView(DecoShape.HILL);
        }
    };
    private static final GameObject bush = new GameObject() {
        {
            setStaticView(DecoShape.BUSH);
        }
    };
    private static final GameObject floorTile = new GameObject() {
        {
            setStaticView(DecoShape.FLOOR);
        }
    };

    public Scenery(SpaceInvadersGame game) {
        this.game = game;
    }

    public void draw() {
        drawSky();
        drawClouds();
        drawHills();
        drawBushes();
        drawFloor();
    }


    private void drawSky() {
        Color skyColor;
        for (int y = 0; y < HEIGHT; y++) {
            skyColor = Color.DEEPSKYBLUE;
            if (y <= 60) skyColor = Color.BLACK;
            if (y == 63) skyColor = Color.MIDNIGHTBLUE;
            if (y == 64) skyColor = Color.DARKSLATEBLUE;
            if (y == 65 || y == 66) skyColor = Color.ROYALBLUE;
            if (y == 67 || y == 68 || y == 69) skyColor = Color.DODGERBLUE;

            for (int x = 0; x < WIDTH; x++) {
                game.getDisplay().drawPixel(x, y, skyColor);
            }
        }
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
        for (int i = 0; i < 2; i++) {
            hill.setPosition(1.8 * i * hill.getWidth() + 14, HEIGHT - DecoShape.FLOOR.length - DecoShape.HILL.length);
            hill.draw();
        }
    }

    private void drawBushes() {
        for (int i = 0; i < 3; i++) {
            bush.setPosition(2 * i * bush.getWidth(), HEIGHT - DecoShape.FLOOR.length - DecoShape.BUSH.length);
            bush.draw();
        }
    }

    private void drawFloor() {
        for (int i = 0; i < 7; i++) {
            floorTile.setPosition(i * floorTile.getWidth(), HEIGHT - DecoShape.FLOOR.length);
            floorTile.draw();
        }
    }
}
