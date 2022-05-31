package com.javarush.games.snake.model.map;

import com.javarush.games.snake.model.map.stages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageManager {
    private static final List<Stage> stageList;
    private static final Stage emptyStage;
    private static int currentStage;

    static {
        stageList = new ArrayList<>();
        stageList.addAll(Arrays.asList(
                new Tutorial1(),
                new Tutorial2(),
                new Tutorial3(),
                new Tutorial4(),
                new Tutorial5(),
                new Tutorial6(),
                new Stage1(),
                new Stage2(),
                new Stage3())
        );
        emptyStage = new EmptyStage();
        currentStage = 0;
    }

    public static Stage getCurrentStage() {
        return stageList.get(currentStage);
    }

    public static Stage getEmptyStage() {
        return emptyStage;
    }

    public static void selectNextStage() {
        if (currentStage < stageList.size() - 1) {
            currentStage++;
        } else {
            currentStage = 0;
        }
    }

    public static void selectPreviousStage() {
        if (currentStage > 0) {
            currentStage--;
        } else {
            currentStage = stageList.size() - 1;
        }
    }
}
