package com.javarush.games.spaceinvaders.gameobjects.item;

import com.javarush.games.racer.Direction;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.gameobjects.brick.QuestionBrick;
import com.javarush.games.spaceinvaders.shapes.DecoShape;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public abstract class Bonus extends GameObject {
    public boolean isCollected;

    private QuestionBrick brick;
    private int ejectHeight = 8;
    private int jumpCounter;
    private int dx;
    private boolean ejected;
    private boolean jumped;
    private boolean pushed;

    public Bonus(double x, double y, QuestionBrick brick) {
        super(x, y);
        this.brick = brick;
        if (x < 50) {
            dx = 1;
        } else {
            dx = -1;
        }
    }

    private void eject() {
        if (ejectHeight > 0) {
            y--;
            ejectHeight--;
        } else {
            ejected = true;
        }
    }

    private void jump() {
        if (jumped || !ejected) {
            return;
        }

        if (isCollision(brick, false)) {
            pushed = true;
        }

        if (pushed) {
            if (jumpCounter < 4) {
                y--;
                jumpCounter++;
            } else if (jumpCounter < 8) {
                y++;
                jumpCounter++;
            } else if (jumpCounter == 8) {
                jumped = true;
            }
        }
    }

    public void move() {
        if (!ejected) {
            eject();
        } else if (!jumped) {
            jump();
        } else {
            if (dx > 0 && x > 38 || dx < 0 && x < 52) {
                y += 2;
                if (y > SpaceInvadersGame.HEIGHT - DecoShape.FLOOR.length - this.height) {
                    y = SpaceInvadersGame.HEIGHT - DecoShape.FLOOR.length - this.height;
                }
            }
            x += dx;
        }
    }
}
