package com.javarush.games.spaceinvaders.model.gameobjects.battlers;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class Boss extends EnemyShip {
    private int frameCount = 0;

    public Boss(double x, double y) {
        super(x, y);
        setAnimatedView(true,
                ObjectShape.BOSS_TANK_1, ObjectShape.BOSS_TANK_2);
        score = 100;
    }

    @Override
    public void nextFrame() {
        frameCount++;
        if (frameCount % 10 == 0 || !isAlive) {
            super.nextFrame();
        }
    }

    @Override
    public Bullet fire() {
        if (!isAlive) {
            return null;
        }
        return new Bullet(x + 3, y + height, Direction.DOWN) {
            {
                setStaticView(ObjectShape.BOSS_TANK_AMMO);
            }
        };
    }

    @Override
    public void kill() {
        if (isAlive) {
            isAlive = false;
            setAnimatedView(false,
                    ObjectShape.BOSS_TANK_KILL_1,
                    ObjectShape.BOSS_TANK_KILL_2,
                    ObjectShape.BOSS_TANK_KILL_3,
                    ObjectShape.BOSS_TANK_KILL_4,
                    ObjectShape.BOSS_TANK_KILL_4);
        }
    }
}
