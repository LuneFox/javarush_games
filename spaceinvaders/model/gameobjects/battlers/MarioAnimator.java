package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;

/** Выбирает и включает позу Марио. */
class MarioAnimator {
    /** Задержка между кадрами ходьбы. */
    private static final int WALK_FRAME_DELAY = 2;
    /** Длительность цикла победной анимации. */
    private static final int VICTORY_CYCLE_FRAMES = 20;

    /** Поза Марио. */
    private enum Pose {
        STANDING, WALKING, JUMPING, BRAKING, DEAD
    }

    /** Анимируемый объект. */
    private final GameObject mario;
    /** Текущая поза. */
    private Pose pose;

    /** Создаёт аниматор для Марио. */
    MarioAnimator(GameObject mario) {
        this.mario = mario;
    }

    /** Ставит Марио. */
    void stand() {
        if (pose == Pose.STANDING) return;
        pose = Pose.STANDING;
        mario.setStaticView(MarioShape.STAND);
    }

    /** Включает ходьбу. */
    void walk() {
        if (pose == Pose.WALKING) return;
        pose = Pose.WALKING;
        mario.setAnimatedView(Sprite.Loop.REPEAT, WALK_FRAME_DELAY,
                MarioShape.WALK_3,
                MarioShape.WALK_2,
                MarioShape.WALK_1,
                MarioShape.WALK_2);
    }

    /** Включает прыжок. */
    void jump() {
        if (pose == Pose.JUMPING) return;
        pose = Pose.JUMPING;
        mario.setStaticView(MarioShape.JUMP);
    }

    /** Включает торможение. */
    void brake() {
        if (pose == Pose.BRAKING) return;
        pose = Pose.BRAKING;
        mario.setStaticView(MarioShape.BRAKE);
    }

    /** Включает смерть. */
    void die() {
        if (pose == Pose.DEAD) return;
        pose = Pose.DEAD;
        mario.setStaticView(MarioShape.DEAD);
    }

    /** Проигрывает кадр победного прыжка. */
    void victoryFrame(int frame) {
        if (frame % VICTORY_CYCLE_FRAMES < VICTORY_CYCLE_FRAMES / 2) {
            jump();
        } else {
            stand();
        }
    }
}
