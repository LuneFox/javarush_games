package com.javarush.games.racer.road;

import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int UPPER_BORDER = 10;
    public static final int LOWER_BORDER = 90;
    public static final int ROAD_WIDTH = RacerGame.HEIGHT - (UPPER_BORDER) - (RacerGame.HEIGHT - LOWER_BORDER);

    private List<RoadObject> items = new ArrayList<>();


    // OBJECTS CONTROL

    public void draw(RacerGame game) {
        for (RoadObject item : items) {
            item.draw(game);
        }
    }

    public void move(double boost) {
        for (RoadObject item : items) {
            item.move(item.speed + boost);
        }
        deletePassedItems();
    }


    // OBJECTS CREATION

    public void generateNewRoadObjects(RacerGame game) {
        generatePuddle(game);
    }

    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        switch (type) {
            case PUDDLE:
                return new Puddle(x, y);
            default:
                return null;
        }
    }

    private void addRoadObject(RoadObjectType type, RacerGame game) {
        int x = RacerGame.WIDTH * 2 + RoadObject.getWidth(type);
        int y = game.getRandomNumber(RoadManager.UPPER_BORDER, RoadManager.LOWER_BORDER - RoadObject.getHeight(type));
        RoadObject ro = createRoadObject(type, x, y);
        if (ro != null) {
            items.add(ro);
        }
    }

    private void generatePuddle(RacerGame game) {
        int x = game.getRandomNumber(100);
        if (x < 10 && !isTwoPuddlesExist()) {
            addRoadObject(RoadObjectType.PUDDLE, game);
        }
    }

    private void deletePassedItems() {
        List<RoadObject> itemsCopy = new ArrayList<>(items);
        for (RoadObject ro : itemsCopy) {
            if (ro.x + ro.width < 0) {
                items.remove(ro);
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
}
