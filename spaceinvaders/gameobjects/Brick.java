package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Brick extends GameObject {
    private static final int JUMP_HEIGHT = 2; // максимальное смещение при подпрыгивании
    private int jumpReserve;                  // оставшийся резерв подпрыгивания в пикселях
    private boolean jumped = false;           // флаг, сообщающий о том, что кирпич достиг предела и должен опускаться
    private boolean touched = false;          // флаг, сообщающий о том, что Марио коснулся кирпича

    public Brick(double x, double y) {
        super(x, y);
        setStaticView(ObjectShape.BRICK);
        jumpReserve = JUMP_HEIGHT;
    }

    public void verifyTouch(Mario mario, SpaceInvadersGame game) {
        // проверка пересечения Марио и кирпича с учётом отзеркаливания спрайта
        if (mario.getFaceDirection() == Direction.RIGHT) {
            if (this.isCollision(mario, false)) {
                this.touched = true;
                jump(game);
            }
        } else if (mario.getFaceDirection() == Direction.LEFT) {
            if (this.isCollision(mario, true)) {
                this.touched = true;
                jump(game);
            }
        }
    }

    public void jump(SpaceInvadersGame game) {
        if (touched) { // если Марио дотронулся
            if (!jumped && jumpReserve >= 0) { // если кирпич не прыгал и есть запас для прыжка
                this.y--; // поднимаем кирпич
                jumpReserve--; // уменьшаем запас для прыжка
                if (jumpReserve <= 0) { // когда кончается запас
                    jumped = true; // считаем, что кирпич совершил прыжок до максимальной точки
                    game.addBullet(fire()); // кирпич стреляет монетой
                }
            } else if (jumpReserve < JUMP_HEIGHT) { // иначе (если кирпич подпрыгнул), если запас прыжка не полный
                this.y++; // опускаем кирпич
                jumpReserve++; // восстанавливаем предел
                if (jumpReserve == JUMP_HEIGHT) { // когда предел достигает максимума
                    jumped = false; // кирпич не прыгал и готов прыгать снова
                    touched = false; // его не трогали и можно трогать ещё раз
                }
            }
        }
    }

    public Bullet fire() {
        return new Coin(x + 2, y, Direction.UP);
    }

}
