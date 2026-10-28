package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Battler;
import com.javarush.games.spaceinvaders.view.shapes.EagleShape;

/** Орёл базы: цель, которую нужно уничтожить. */
public class Eagle extends Battler {
    /** Задержка между кадрами взрыва. */
    private static final int EXPLOSION_FRAME_DELAY = 2;

    /** Создаёт орла. */
    public Eagle() {
        super(0, 0);
        this.hitPoints = 1;
        setStaticView(EagleShape.EAGLE);
    }

    /** Уничтожает орла и запускает анимацию взрыва. */
    @Override
    public void takeHit() {
        if (!isAlive()) return;

        super.takeHit();
        setAnimatedView(Sprite.Loop.ONCE, EXPLOSION_FRAME_DELAY,
                EagleShape.EXPLOSION_1,
                EagleShape.EXPLOSION_2,
                EagleShape.EXPLOSION_3,
                EagleShape.DESTROYED);
    }
}
