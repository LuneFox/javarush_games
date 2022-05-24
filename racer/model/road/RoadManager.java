package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.GameObjectManager;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager implements GameObjectManager {
    public static final int UPPER_BORDER = 10;
    public static final int LOWER_BORDER = 90;
    public static final int ROAD_WIDTH = RacerGame.HEIGHT - (UPPER_BORDER) - (RacerGame.HEIGHT - LOWER_BORDER);

    private final RacerGame game;
    private final List<RoadObject> roadObjects = new ArrayList<>();

    public RoadManager(RacerGame game) {
        this.game = game;
    }

    public void drawObjects() {
        roadObjects.forEach(GameObject::draw);
    }

    public void moveObjects(double boost) {
        roadObjects.forEach(roadObject -> roadObject.move(roadObject.speed + boost));
        roadObjects.removeIf(roadObject -> roadObject.x + roadObject.getWidth() < 0);
    }

    public void checkOverlapsWithCar(DeLorean delorean) {
        roadObjects.stream().
                filter(roadObject -> HitBox.checkFullOverlap(roadObject, delorean) && delorean.isAtLeftmostPosition())
                .forEach(roadObject -> roadObject.onContact(delorean));
    }

    public void generateNewRoadObjects(DeLorean deLorean) {
        if (deLorean.getSpeed() <= 0) return;
        if (deLorean.getDistance() <= 0) return;

        generatePuddle();
        generateHole();
        generateEnergy();
    }

    private void generatePuddle() {
        if (twoPuddlesExist()) return;

        if (Math.random() * 100 < 10) {
            addRoadObject(RoadObjectType.PUDDLE);
        }
    }

    private boolean twoPuddlesExist() {
        return roadObjects.stream()
                .filter(roadObject -> roadObject instanceof Puddle)
                .count() >= 2;
    }

    private void generateHole() {
        if (holeExists()) return;

        if (Math.random() * 100 < 2) {
            addRoadObject(RoadObjectType.HOLE);
        }
    }

    private boolean holeExists() {
        return roadObjects.stream()
                .anyMatch(roadObject -> roadObject instanceof Hole);
    }

    private void generateEnergy() {
        if (energyExists()) return;
        if (game.deloreanHasMaxEnergy()) return;

        if (Math.random() * 100 < 15) {
            addRoadObject(RoadObjectType.ENERGY);
        }
    }

    private boolean energyExists() {
        return roadObjects.stream()
                .anyMatch(roadObject -> roadObject instanceof Energy);
    }

    private void addRoadObject(RoadObjectType type) {
        RoadObject newRoadObject = createRoadObjectAtRandomHeight(type);

        for (RoadObject roadObject : roadObjects) {
            if (HitBox.checkVerticalOverlap(roadObject, newRoadObject)) {
                return;
            }
        }

        roadObjects.add(newRoadObject);
    }

    private RoadObject createRoadObjectAtRandomHeight(RoadObjectType type) {
        RoadObject result = RoadObject.create(type);

        double x = RacerGame.WIDTH * 2;
        double y = (Math.random() * (ROAD_WIDTH - result.getHeight() - 1)) + UPPER_BORDER + 1;

        result.setPosition(x, y);
        return result;
    }
}
