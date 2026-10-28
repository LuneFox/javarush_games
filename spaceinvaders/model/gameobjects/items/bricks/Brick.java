package com.javarush.games.spaceinvaders.model.gameobjects.items.bricks;

import com.javarush.games.spaceinvaders.model.LevelLayout;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysics;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysicsBuilder;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

/** Кирпич: подпрыгивает от удара и даёт монету. */
public class Brick extends GameObject implements Movable {
    /** Сдвиг монеты от левого края кирпича. */
    private static final int COIN_OFFSET_X = 2;

    /** Физика подпрыгивания кирпича. */
    private final JumpPhysics bounce;

    /** Создаёт обычный кирпич. */
    public Brick(double x, double y) {
        this(x, y, BrickShape.BRICK);
    }

    /** Создаёт кирпич с заданной картинкой. */
    protected Brick(double x, double y, int[][] image) {
        super(x, y);
        setStaticView(image);
        bounce = new JumpPhysicsBuilder(this)
                .setFloorLevel(LevelLayout.BRICK_ROW_Y)
                .build();
    }

    /** Реагирует на удар Марио снизу. */
    public void bumpIfTouchedBy(Mario mario) {
        if (!mario.isAlive()) return;
        if (!collidesWith(mario, Mirror.of(mario.getFacing()))) return;

        bounce.startJump();
        releaseContent();
    }

    /** Выдаёт содержимое кирпича. */
    protected void releaseContent() {
        if (game.isRoundOver()) return;

        game.addPlayerBullet(BulletFactory.create(BulletType.COIN, getX() + COIN_OFFSET_X, getY()));
    }

    /** Обновляет подпрыгивание кирпича. */
    @Override
    public void move() {
        if (game.isRoundOver()) return;

        bounce.update();
    }
}
