package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int UPPER_BORDER = 10;
    public static final int LOWER_BORDER = 90;
    public static final int ROAD_WIDTH = RacerGame.HEIGHT - (UPPER_BORDER) - (RacerGame.HEIGHT - LOWER_BORDER);

    private final List<RoadObject> roadObjects = new ArrayList<>();


    // OBJECTS CONTROL

    public void draw() {
        for (RoadObject item : roadObjects) {
            item.draw();
        }
    }

    public void move(double boost) {
        for (RoadObject item : roadObjects) {
            item.move(item.speed + boost);
        }
        deletePassedItems();
    }

    public void checkCross(DeLorean delorean) {
        for (RoadObject item : roadObjects) {
            if (HitBox.checkFullOverlap(item, delorean) && delorean.isAtLeftmostPosition()) {
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
            generatePuddle(game);
            generateHole(game);
            generateEnergy(game);
        }
    }

    private void addRoadObject(RoadObjectType type, RacerGame game) {
        RoadObject newRoadObject = RoadObject.create(type);

        int x = RacerGame.WIDTH * 2 + newRoadObject.getWidth();
        int y = game.getRandomNumber(RoadManager.UPPER_BORDER, RoadManager.LOWER_BORDER - newRoadObject.getHeight());

        newRoadObject.setPosition(x, y);

        for (RoadObject roadObject : roadObjects) {
            if (HitBox.checkVerticalOverlap(roadObject, newRoadObject)) {
                return;
            }
        }

        roadObjects.add(newRoadObject);
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
        List<RoadObject> itemsCopy = new ArrayList<>(roadObjects);
        for (RoadObject roadObject : itemsCopy) {
            if (roadObject.x + roadObject.getWidth() < 0) {
                roadObjects.remove(roadObject);
            }
        }
    }


    // CHECKS

    private boolean isTwoPuddlesExist() {
        int count = 0;
        for (RoadObject ro : roadObjects) {
            if (ro.type == RoadObjectType.PUDDLE) {
                count++;
            }
        }
        return (count == 2);
    }

    private boolean isHoleExist() {
        for (RoadObject ro : roadObjects) {
            if (ro.type == RoadObjectType.HOLE) {
                return true;
            }
        }
        return false;
    }

    private boolean isEnergyExist() {
        for (RoadObject ro : roadObjects) {
            if (ro.type == RoadObjectType.ENERGY) {
                return true;
            }
        }
        return false;
    }
}
