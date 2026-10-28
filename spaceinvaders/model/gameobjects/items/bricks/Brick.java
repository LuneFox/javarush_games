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

/**
 * A brick jumps up when Mario hits it from below and gives away a coin. The question brick overrides
 * what exactly is given away.
 */
public class Brick extends GameObject implements Movable {
    private static final int COIN_OFFSET_X = 2;

    private final JumpPhysics bounce;

    public Brick(double x, double y) {
        this(x, y, BrickShape.BRICK);
    }

    protected Brick(double x, double y, int[][] image) {
        super(x, y);
        setStaticView(image);
        bounce = new JumpPhysicsBuilder(this)
                .setFloorLevel(LevelLayout.BRICK_ROW_Y)
                .build();
    }

    public void bumpIfTouchedBy(Mario mario) {
        if (!mario.isAlive()) return;
        if (!collidesWith(mario, Mirror.of(mario.getFacing()))) return;

        bounce.startJump();
        releaseContent();
    }

    /**
     * Called when Mario hits the brick from below.
     */
    protected void releaseContent() {
        if (game.isRoundOver()) return;

        game.addPlayerBullet(BulletFactory.create(BulletType.COIN, getX() + COIN_OFFSET_X, getY()));
    }

    @Override
    public void move() {
        if (game.isRoundOver()) return;

        bounce.update();
    }
}
