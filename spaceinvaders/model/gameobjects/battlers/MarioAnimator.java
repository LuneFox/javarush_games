package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;

/**
 * Picks the picture of Mario for his current state. Keeps the last pose, so the sprite is not
 * recreated on every turn.
 */
class MarioAnimator {
    private static final int WALK_FRAME_DELAY = 2;
    private static final int VICTORY_CYCLE_FRAMES = 20;

    private enum Pose {
        STANDING, WALKING, JUMPING, BRAKING, DEAD
    }

    private final GameObject mario;
    private Pose pose;

    MarioAnimator(GameObject mario) {
        this.mario = mario;
    }

    void stand() {
        if (pose == Pose.STANDING) return;
        pose = Pose.STANDING;
        mario.setStaticView(MarioShape.STAND);
    }

    void walk() {
        if (pose == Pose.WALKING) return;
        pose = Pose.WALKING;
        mario.setAnimatedView(Sprite.Loop.REPEAT, WALK_FRAME_DELAY,
                MarioShape.WALK_3,
                MarioShape.WALK_2,
                MarioShape.WALK_1,
                MarioShape.WALK_2);
    }

    void jump() {
        if (pose == Pose.JUMPING) return;
        pose = Pose.JUMPING;
        mario.setStaticView(MarioShape.JUMP);
    }

    void brake() {
        if (pose == Pose.BRAKING) return;
        pose = Pose.BRAKING;
        mario.setStaticView(MarioShape.BRAKE);
    }

    void die() {
        if (pose == Pose.DEAD) return;
        pose = Pose.DEAD;
        mario.setStaticView(MarioShape.DEAD);
    }

    /**
     * Mario celebrates by hopping: the pose switches twice per cycle.
     */
    void victoryFrame(int frame) {
        if (frame % VICTORY_CYCLE_FRAMES < VICTORY_CYCLE_FRAMES / 2) {
            jump();
        } else {
            stand();
        }
    }
}
