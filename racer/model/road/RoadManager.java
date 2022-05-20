package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.DeLorean;
import com.javarush.games.racer.model.HitBox;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int UPPER_BORDER = 10;
    public static final int LOWER_BORDER = 90;
    public static final int ROAD_WIDTH = RacerGame.HEIGHT - (UPPER_BORDER) - (RacerGame.HEIGHT - LOWER_BORDER);

    private final List<RoadObject> items = new ArrayList<>();


    // OBJECTS CONTROL

    public void draw() {
        for (RoadObject item : items) {
            item.draw();
        }
    }

    public void move(double boost) {
        for (RoadObject item : items) {
            item.move(item.speed + boost);
        }
        deletePassedItems();
    }

    public void checkCross(DeLorean delorean) {
        for (RoadObject item : items) {
            if (HitBox.isCollision(item, delorean) && delorean.x == 3) {
                switch (item.type) {
                    case PUDDLE:
                        delorean.setSpeed((delorean.getSpeed() / 100) * 95);
                        break;
                    case HOLE:
                        delorean.setSpeed((delorean.getSpeed() / 100) * 80);
                        break;
                    case ENERGY:
                        Energy energy = (Energy) item;
                        if (!energy.isCollected) {
                            if (delorean.getEnergy() < DeLorean.MAX_ENERGY) {
                                delorean.setEnergy(delorean.getEnergy() + DeLorean.MAX_ENERGY / 10);
                            }
                            energy.isCollected = true;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }


    // OBJECTS CREATION AND DELETION

    public void generateNewRoadObjects(RacerGame game, DeLorean deLorean) {
        if (RacerGame.allowCountTime && deLorean.getSpeed() > 0) {
//            generatePuddle(game);
//            generateHole(game);
            generateEnergy(game);
        }
    }

    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        switch (type) {
            case PUDDLE:
                return new Puddle(x, y);
            case HOLE:
                return new Hole(x, y);
            case ENERGY:
                return new Energy(x, y);
            default:
                return null;
        }
    }

    private void addRoadObject(RoadObjectType type, RacerGame game) {
        int x = RacerGame.WIDTH * 2 + RoadObject.getWidth(type);
        int y = game.getRandomNumber(RoadManager.UPPER_BORDER, RoadManager.LOWER_BORDER - RoadObject.getHeight(type));
        RoadObject newItem = createRoadObject(type, x, y);
        for (RoadObject item : items) {
            if (HitBox.isCollisionY(item, newItem)) {
                return;
            }
        }
        items.add(newItem);
    }

    private void generatePuddle(RacerGame game) {
        int x = game.getRandomNumber(100);
        if (x < 10 && !isTwoPuddlesExist()) {
            addRoadObject(RoadObjectType.PUDDLE, game);
        }
    }

    private void generateHole(RacerGame game) {
        int x = game.getRandomNumber(100);
        if (x < 2 && !isHoleExist()) {
            addRoadObject(RoadObjectType.HOLE, game);
        }
    }

    private void generateEnergy(RacerGame game) {
        int x = game.getRandomNumber(100);
        if (x < 15 && !isEnergyExist() && game.delorean.getEnergy() < DeLorean.MAX_ENERGY) {
            addRoadObject(RoadObjectType.ENERGY, game);
        }
    }

    private void deletePassedItems() {
        List<RoadObject> itemsCopy = new ArrayList<>(items);
        for (RoadObject roadObject : itemsCopy) {
            if (roadObject.x + roadObject.getWidth() < 0) {
                items.remove(roadObject);
            }
        }
    }


    // CHECKS

    private boolean isTwoPuddlesExist() {
        int count = 0;
        for (RoadObject ro : items) {
            if (ro.type == RoadObjectType.PUDDLE) {
                count++;
            }
        }
        return (count == 2);
    }

    private boolean isHoleExist() {
        for (RoadObject ro : items) {
            if (ro.type == RoadObjectType.HOLE) {
                return true;
            }
        }
        return false;
    }

    private boolean isEnergyExist() {
        for (RoadObject ro : items) {
            if (ro.type == RoadObjectType.ENERGY) {
                return true;
            }
        }
        return false;
    }
}
