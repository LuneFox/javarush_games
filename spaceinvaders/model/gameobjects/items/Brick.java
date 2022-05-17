package com.javarush.games.spaceinvaders.model.gameobjects.items;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.CoinBullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.Optional;

public class Brick extends GameObject implements Shooter {
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
        if (!mario.isAlive) return;

        final Mirror mirror = (mario.getFaceDirection() == Direction.RIGHT) ? Mirror.NONE : Mirror.HORIZONTAL;
        if (this.collidesWith(mario, mirror)) {
            this.touched = true;
            jump(game);
        }
    }

    public void jump(SpaceInvadersGame game) {
        if (touched) { // если Марио дотронулся
            if (!jumped && jumpReserve >= 0) { // если кирпич не прыгал и есть запас для прыжка
                this.y--; // поднимаем кирпич
                jumpReserve--; // уменьшаем запас для прыжка
                if (jumpReserve <= 0) { // когда кончается запас
                    jumped = true; // считаем, что кирпич совершил прыжок до максимальной точки
                    if (fire().isPresent()) {
                        game.addPlayerBullet(fire().get()); // кирпич стреляет монетой
                    }
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

    public Optional<Bullet> fire() {
        return Optional.of(new CoinBullet(x + 2, y, Direction.UP));
    }

    public void check(SpaceInvadersGame game) {

    }
}
