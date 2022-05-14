package com.javarush.games.spaceinvaders.model.gameobjects.items;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.view.shapes.DecoShape;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.Date;
import java.util.List;

public class QuestionBrick extends Brick {
    private Bonus bonus;
    private boolean isOpened;
    private boolean hasItem;

    public QuestionBrick(double x, double y) {
        super(x, y);
        this.setStaticView(ObjectShape.QUESTION_BRICK_EMPTY);
        hasItem = false;
        isOpened = true;
    }

    @Override
    public void verifyTouch(Mario mario, SpaceInvadersGame game) {
        super.verifyTouch(mario, game);
    }

    @Override
    public Bullet fire() {
        open();
        return null;
    }

    private void open() {
        this.isOpened = true;
        setStaticView(ObjectShape.QUESTION_BRICK_EMPTY);
    }

    public void ejectItem(SpaceInvadersGame game) {
        if (isOpened && hasItem) {
            Bonus bonus = generateItem();
            this.bonus = bonus;
            game.addBonus(bonus);
            hasItem = false;
        }
    }

    private Bonus generateItem() {
        double random = Math.random();
        if (random < 0.5) {
            return new Mushroom(x, y);
        } else {
            return new Star(x, y + 1);
        }
    }

    public void putItem() {
        double random = Math.random();
        if (random < 0.005 && isOpened && !hasItem) {
            if (bonus == null || bonus.isCollected) {
                this.bonus = null;
                hasItem = true;
                isOpened = false;
                setStaticView(ObjectShape.QUESTION_BRICK);
            }
        }
    }

    @Override
    public void check(SpaceInvadersGame game) {
        putItem();
        ejectItem(game);
    }

    // BONUSES

    public abstract class Bonus extends GameObject {
        private int ejectHeight = 8;
        private int jumpCounter;
        private int dx;
        public boolean isCollected;
        private boolean isEjected;
        private boolean isJumped;
        private boolean isPushed;

        public Bonus(double x, double y) {
            super(x, y);
            if (x < 50) {
                dx = 1;
            } else {
                dx = -1;
            }
        }

        public void verifyTouch(Mario mario, SpaceInvadersGame game) {
            // проверка пересечения Марио и бонуса с учётом отзеркаливания спрайта
            if (mario.getFaceDirection() == Direction.RIGHT) {
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
                isEjected = true;
            }
        }

        private void jump() {
            if (isJumped || !isEjected) {
                return;
            }

            if (isCollision(QuestionBrick.this, false)) {
                isPushed = true;
            }

            if (isPushed) {
                if (jumpCounter < 4) {
                    y--;
                    jumpCounter++;
                } else if (jumpCounter < 8) {
                    y++;
                    jumpCounter++;
                } else if (jumpCounter == 8) {
                    isJumped = true;
                }
            }
        }

        public void move() {
            if (!isEjected) {
                eject();
            } else if (!isJumped) {
                jump();
            } else {
                if (dx > 0 && x > 38 || dx < 0 && x < 52) {
                    y += 3;
                    if (y > SpaceInvadersGame.HEIGHT - DecoShape.FLOOR.length - this.height) {
                        y = SpaceInvadersGame.HEIGHT - DecoShape.FLOOR.length - this.height;
                    }
                }
                x += dx;
            }
        }
    }

    public class Mushroom extends Bonus {

        public Mushroom(double x, double y) {
            super(x, y);
            int[][] sprite = new int[ObjectShape.MUSHROOM.length][ObjectShape.MUSHROOM[0].length];
            for (int matrixY = 0; matrixY < ObjectShape.MUSHROOM.length; matrixY++) {
                for (int matrixX = 0; matrixX < ObjectShape.MUSHROOM[0].length; matrixX++) {
                    sprite[matrixY][matrixX] = ObjectShape.MUSHROOM[matrixY][matrixX];
                }
            }
            setStaticView(sprite);
        }

        @Override
        public void verifyHit(List<Bullet> bullets) {
            bullets.forEach(bullet -> {
                int[] point = isCollisionWithCoords(bullet, false);
                if (point != null) {
                    bullet.kill();
                    this.matrix[point[1]][point[0]] = 0;
                }
            });
        }
    }

    public class Star extends Bonus {
        public Star(double x, double y) {
            super(x, y);
            setStaticView(ObjectShape.STAR);
        }

        @Override
        public void verifyHit(List<Bullet> bullets) {
            bullets.forEach(bullet -> {
                if (isCollision(bullet, true) && new Date().getTime() - bullet.collisionDate.getTime() > 500) {
                    bullet.changeDirection();
                    bullet.deadlyForEnemies = true;
                    bullet.collisionDate = new Date();
                }
            });
        }
    }
}
