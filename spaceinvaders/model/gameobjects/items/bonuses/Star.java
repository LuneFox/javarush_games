package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;


import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.Date;
import java.util.List;

public class Star extends Bonus {
    public Star(double x, double y) {
        super(x, y);
        setStaticView(ObjectShape.STAR);
        overheadIcon = new GameObject() {
            {
                setStaticView(ObjectShape.STAR_OVERHEAD_ICON);
            }
        };
    }

    @Override
    public void verifyHit(List<Bullet> bullets) {
        bullets.forEach(bullet -> {
            if (collidesWith(bullet, Mirror.HORIZONTAL) && bulletCollisionCooledDown(bullet)) {
                bullet.inverseDirection();
                bullet.canKillEnemies = true;
                bullet.lastCollisionDate = new Date();
            }
        });
    }

    @Override
    public void consume() {
        game.flash.show();
        Score.add(game.enemyBullets.size() * 5);
        game.enemyBullets.clear();
    }

    private boolean bulletCollisionCooledDown(Bullet bullet) {
        return new Date().getTime() - bullet.lastCollisionDate.getTime() > 250;
    }
}
