package com.javarush.games.spaceinvaders.gameobjects.item;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.gameobjects.brick.QuestionBrick;
import com.javarush.games.spaceinvaders.shapes.DecoShape;

import java.util.List;

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

    public void verifyTouch(Mario mario, SpaceInvadersGame game) {
        // проверка пересечения Марио и бонуса с учётом отзеркаливания спрайта
        if (mario.getFaceDirection() == com.javarush.games.spaceinvaders.Direction.RIGHT) {
            if (this.isCollision(mario, false)) {
                mario.collect(this);
                this.isCollected = true;
                game.increaseScore(10);
            }
        } else if (mario.getFaceDirection() == Direction.LEFT) {
            if (this.isCollision(mario, true)) {
                mario.collect(this);
                this.isCollected = true;
                game.increaseScore(10);
            }
        }
    }

    public abstract void verifyHit(List<Bullet> bullets);

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
