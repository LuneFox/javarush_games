package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject {


    public Ship(double x, double y) {
        super(x, y);
    }


    // -------- BASIC ACTIONS

    public Bullet fire() {
        return null;
    }

    public void kill() {
        isAlive = false;
    }
}
