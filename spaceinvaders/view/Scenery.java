package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.view.shapes.DecoShape;

import static com.javarush.games.spaceinvaders.SpaceInvadersGame.*;

public class Scenery {
    private final SpaceInvadersGame game;

    private final GameObject cloud = new GameObject(0, 0) {
        {
            setStaticView(DecoShape.CLOUD);
        }
    };

    private final GameObject hill = new GameObject(0, 0) {
        {
            setStaticView(DecoShape.HILL);
        }
    };

    private final GameObject bush = new GameObject(0, 0) {
        {
            setStaticView(DecoShape.BUSH);
        }
    };

    private final GameObject floorTile = new GameObject(0, 0) {
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
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                game.display.drawPixel(x, y, (y > 60) ? Color.DEEPSKYBLUE : Color.BLACK);
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
