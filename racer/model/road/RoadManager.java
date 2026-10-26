package com.javarush.games.racer.model.road;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.GameObjectManager;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.HitBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Owns everything that makes up the road: the background, the lane dashes and the list of road objects.
 * The drivable strip spans rows 10 to 89 and is 80 cells wide; objects spawn off-screen at x = 200 and
 * are removed once they are fully off the left edge. Implements {@link GameObjectManager} so the game
 * can drive it like any other object manager.
 */
public class RoadManager implements GameObjectManager {
    public static final int UPPER_BORDER = 10;
    public static final int LOWER_BORDER = 90;
    public static final int ROAD_WIDTH = RacerGame.HEIGHT - (UPPER_BORDER) - (RacerGame.HEIGHT - LOWER_BORDER);

    private final RacerGame game;
    private final Road road;
    private final RoadMarkingManager roadMarkingManager;
    private final List<RoadObject> roadObjects = new ArrayList<>();

    /**
     * Creates the road background and the lane-marking manager for the given game; the road starts with
     * no obstacles or pickups.
     *
     * @param game running game instance, used for its display buffer
     */
    public RoadManager(RacerGame game) {
        this.game = game;
        this.road = new Road(game.getDisplay());
        this.roadMarkingManager = new RoadMarkingManager();
    }

    /**
     * Draws the road in layers: background first, then lane markings, then road objects.
     * Everything goes into the display buffer, which the game flushes once per frame.
     */
    public void drawObjects() {
        road.draw();
        roadMarkingManager.drawObjects();
        roadObjects.forEach(GameObject::draw);
    }

    /**
     * Advances every road object left by its own speed plus the car's speed, then drops the objects that
     * are entirely off the left edge. Lane markings are moved separately by the same boost.
     *
     * @param boost car speed in world pixels per frame
     */
    public void moveObjects(double boost) {
        roadObjects.forEach(roadObject -> roadObject.move(roadObject.speed + boost));
        roadObjects.removeIf(roadObject -> roadObject.x + roadObject.getWidth() < 0);
        roadMarkingManager.moveObjects(boost);
    }

    /**
     * Calls {@link RoadObject#onContact(DeLorean)} for every object that fully overlaps the car's hit box,
     * but only while the car is at its leftmost position, i.e. in normal driving mode.
     *
     * @param delorean the player's car
     */
    public void checkOverlapsWithCar(DeLorean delorean) {
        roadObjects.stream().
                filter(roadObject -> HitBox.checkFullOverlap(roadObject, delorean) && delorean.isAtLeftmostPosition())
                .forEach(roadObject -> roadObject.onContact(delorean));
    }

    /**
     * Rolls the random spawn chance for puddles, holes and energy once per frame.
     * Nothing is spawned while the car is stationary or has not covered any distance yet.
     *
     * @param deLorean the player's car; its speed, distance and energy level gate the spawns
     */
    public void generateNewRoadObjects(DeLorean deLorean) {
        if (deLorean.getSpeed() <= 0) return;
        if (deLorean.getDistance() <= 0) return;

        generatePuddle();
        generateHole();
        generateEnergy();
    }

    /**
     * Spawns a puddle with a 10% chance per frame, as long as fewer than two puddles already exist.
     */
    private void generatePuddle() {
        if (twoPuddlesExist()) return;

        if (Math.random() * 100 < 10) {
            addRoadObject(RoadObjectType.PUDDLE);
        }
    }

    /**
     * Tells whether the road currently holds at least two puddles, which is the cap for puddle spawns.
     *
     * @return {@code true} when two or more puddles exist
     */
    private boolean twoPuddlesExist() {
        return roadObjects.stream()
                .filter(roadObject -> roadObject instanceof Puddle)
                .count() >= 2;
    }

    /**
     * Spawns a hole with a 2% chance per frame, but only when no other hole exists.
     */
    private void generateHole() {
        if (holeExists()) return;

        if (Math.random() * 100 < 2) {
            addRoadObject(RoadObjectType.HOLE);
        }
    }

    /**
     * Tells whether at least one hole is currently on the road.
     *
     * @return {@code true} when a hole exists
     */
    private boolean holeExists() {
        return roadObjects.stream()
                .anyMatch(roadObject -> roadObject instanceof Hole);
    }

    /**
     * Spawns energy with a 15% chance per frame, but only when no other energy pickup exists and the
     * car's energy bar is not already full.
     */
    private void generateEnergy() {
        if (energyExists()) return;
        if (game.deloreanHasMaxEnergy()) return;

        if (Math.random() * 100 < 15) {
            addRoadObject(RoadObjectType.ENERGY);
        }
    }

    /**
     * Tells whether an energy pickup is currently on the road.
     *
     * @return {@code true} when energy exists
     */
    private boolean energyExists() {
        return roadObjects.stream()
                .anyMatch(roadObject -> roadObject instanceof Energy);
    }

    /**
     * Creates an object of the given type at a random height and adds it to the list, unless its hit box
     * would overlap an existing object vertically. In that case the candidate is discarded, so objects
     * never share a lane row.
     *
     * @param type kind of object to spawn
     */
    private void addRoadObject(RoadObjectType type) {
        RoadObject newRoadObject = createRoadObjectAtRandomHeight(type);

        for (RoadObject roadObject : roadObjects) {
            if (HitBox.checkVerticalOverlap(roadObject, newRoadObject)) {
                return;
            }
        }

        roadObjects.add(newRoadObject);
    }

    /**
     * Builds a road object off-screen at x = 200 ({@code 2 * WIDTH}) and a random y inside the strip,
     * starting one cell below the upper border and keeping the whole shape within the lower border.
     *
     * @param type kind of object to create
     * @return the newly created and positioned object
     */
    private RoadObject createRoadObjectAtRandomHeight(RoadObjectType type) {
        RoadObject result = RoadObject.create(type);

        double x = RacerGame.WIDTH * 2;
        double y = (Math.random() * (ROAD_WIDTH - result.getHeight() - 1)) + UPPER_BORDER + 1;

        result.setPosition(x, y);
        return result;
    }
}
