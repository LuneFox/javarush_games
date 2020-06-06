package com.javarush.games.racer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.road.RoadManager;

public class DeLorean extends GameObject {
    public static final double MAX_SPEED = 8.89;
    public static final double MAX_ENERGY = 1.21;
    private static final double MAX_STEER = 2.0;
    private static final double GLOW_POINT = 6.0;
    private static final double BOOST = 0.05;
    private static final double BRAKE = 0.2;
    private static final double DECAY = 0.025;

    private Direction verticalDirection;
    private Direction horizontalDirection;
    private Animation animation;
    private double speed;
    private double acceleration;
    private double energy;

    public DeLorean() {
        super(3, (int) (RacerGame.HEIGHT / 2 - ShapeMatrix.DELOREAN_RUN_0.length / 2 + 8), ShapeMatrix.DELOREAN_RUN_0);
        this.hitBox = new HitBox(9, 6, 20, 28);
        this.verticalDirection = Direction.NONE;
        this.horizontalDirection = Direction.NONE;
        this.speed = 0.0;
        this.acceleration = 0.0;
        this.energy = 0.0;
        animateStopped();
    }

    public void steer() {
        if (this.x != 3) {
            return;
        }
        if (y < RoadManager.UPPER_BORDER - 8) {
            speed = (speed / 100) * 99.5;
            y = RoadManager.UPPER_BORDER - 8;
        } else if (y > RoadManager.LOWER_BORDER - height) {
            speed = (speed / 100) * 99.5;
            y = RoadManager.LOWER_BORDER - height;
        }
        switch (verticalDirection) {
            case UP:
                if (speed > MAX_STEER) {
                    y -= MAX_STEER;
                } else {
                    y -= speed;
                }
                break;
            case DOWN:
                if (speed > MAX_STEER) {
                    y += MAX_STEER;
                } else {
                    y += speed;
                }
                break;
            default:
                break;
        }
    }

    public void gas() {
        if (this.x != 3) {
            return;
        }

        if (speed <= 0 && animation != Animation.STOPPED) {
            animateStopped();
        } else if (speed > 0 && speed < GLOW_POINT && animation != Animation.RUNNING) {
            animateRunning();
        } else if (speed >= GLOW_POINT && animation != Animation.GLOWING) {
            animateGlowing();
        }

        switch (horizontalDirection) {
            case RIGHT:
                acceleration = BOOST * (1 - speed * 0.1);
                speed += acceleration;
                if (speed >= GLOW_POINT - 0.1 && energy < MAX_ENERGY) {
                    speed = GLOW_POINT - 0.1;
                }
                break;
            case LEFT:
                acceleration = BRAKE;
                speed -= acceleration;
                if (speed < 0) {
                    speed = 0;
                }
                break;
            case NONE:
                acceleration = DECAY;
                speed -= acceleration;
                if (speed < 0) {
                    speed = 0;
                }
            default:
                break;
        }
    }

    @Override
    public void draw(RacerGame game) {
        Portal portal = game.getPortal();
        if (speed > MAX_SPEED - 0.09 && !game.isStopped) {
            this.x += 3;
        }
        int shift = (this.x == 3 ? 3 : 6);
        for (int i = 0; i < width - (this.x - shift); i++) {
            for (int j = 0; j < height; j++) {
                int colorIndex = matrix[j][i];
                game.display.setCellColor((int) x + i, (int) y + j, Color.values()[colorIndex]);
            }
        }
        if (this.x > portal.x + 5) {
            speed = 0;
            game.isStopped = true;
        }
    }

    // ANIMATIONS

    public void animateRunning() {
        setAnimation(
                ShapeMatrix.DELOREAN_RUN_0,
                ShapeMatrix.DELOREAN_RUN_1,
                ShapeMatrix.DELOREAN_RUN_2,
                ShapeMatrix.DELOREAN_RUN_3);
        this.animation = Animation.RUNNING;
    }

    public void animateGlowing() {
        setAnimation(
                ShapeMatrix.DELOREAN_GLOW_0,
                ShapeMatrix.DELOREAN_GLOW_1,
                ShapeMatrix.DELOREAN_GLOW_2,
                ShapeMatrix.DELOREAN_GLOW_3);
        this.animation = Animation.GLOWING;
    }

    public void animateStopped() {
        setAnimation(ShapeMatrix.DELOREAN_RUN_0);
        this.animation = Animation.STOPPED;
    }


    // GETTERS AND SETTERS

    public Direction getVerticalDirection() {
        return verticalDirection;
    }

    public Direction getHorizontalDirection() {
        return horizontalDirection;
    }

    public double getSpeed() {
        return speed;
    }

    public void setVerticalDirection(Direction verticalDirection) {
        this.verticalDirection = verticalDirection;
    }

    public void setHorizontalDirection(Direction horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getEnergy() {
        return energy;
    }

    private enum Animation {
        STOPPED, RUNNING, GLOWING
    }
}
