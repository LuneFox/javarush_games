package com.javarush.games.racer.road;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.PlayerCar;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    private List<RoadObject> items = new ArrayList<>();

    public void draw(Game game) {
        for (RoadObject item : items) {
            item.draw(game);
        }
    }

    public void move(int boost) {
        for (RoadObject item : items) {
            item.move(item.speed + boost);
        }
        deletePassedItems();
    }

    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        switch (type) {
            case THORN:
                return new Thorn(x, y);
            default:
                return null;
        }
    }

    private void addRoadObject(RoadObjectType type, Game game) {
        int x = game.getRandomNumber(FIRST_LANE_POSITION, FOURTH_LANE_POSITION);
        int y = -1 * RoadObject.getHeight(type);

        RoadObject ro = createRoadObject(type, x, y);

        if (ro != null) {
            items.add(ro);
        }

    }

    private boolean isThornExists() {
        for (RoadObject ro : items) {
            if (ro.type == RoadObjectType.THORN) {
                return true;
            }
        }
        return false;
    }

    private void generateThorn(Game game) {
        int x = game.getRandomNumber(100);
        if (x < 10 && !isThornExists()) {
            addRoadObject(RoadObjectType.THORN, game);
        }
    }

    public void generateNewRoadObjects(Game game) {
        generateThorn(game);
    }

    private void deletePassedItems() {
        List<RoadObject> itemsCopy = new ArrayList<>();
        for (RoadObject ro : items) {
            itemsCopy.add(ro);
        }
        for (RoadObject ro : itemsCopy) {
            if (ro.y >= RacerGame.HEIGHT) {
                items.remove(ro);
            }
        }
    }

    public boolean checkCrush(PlayerCar car) {
        for (RoadObject ro : items) {
            if (ro.isCollision(car)) {
                return true;
            }
        }
        return false;
    }
}
