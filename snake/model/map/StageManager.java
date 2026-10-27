package com.javarush.games.snake.model.map;

import com.javarush.games.snake.model.map.stages.EmptyStage;
import com.javarush.games.snake.model.map.stages.Stage1;
import com.javarush.games.snake.model.map.stages.Stage2;
import com.javarush.games.snake.model.map.stages.Stage3;
import com.javarush.games.snake.model.map.stages.Tutorial1;
import com.javarush.games.snake.model.map.stages.Tutorial2;
import com.javarush.games.snake.model.map.stages.Tutorial3;
import com.javarush.games.snake.model.map.stages.Tutorial4;
import com.javarush.games.snake.model.map.stages.Tutorial5;
import com.javarush.games.snake.model.map.stages.Tutorial6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Список уровней и текущий выбранный уровень. */
public class StageManager {
    private static final List<Stage> stages;
    private static final Stage emptyStage;
    private static int currentStageIndex;

    static {
        stages = new ArrayList<>();
        stages.addAll(Arrays.asList(
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
        currentStageIndex = 0;
        initializeStages();
    }

    /** Инициализирует все уровни при старте. */
    private static void initializeStages() {
        for (Stage stage : stages) {
            stage.initialize();
        }
        emptyStage.initialize();
    }

    /** Возвращает текущий уровень. */
    public static Stage getCurrentStage() {
        return stages.get(currentStageIndex);
    }

    /** Возвращает пустой уровень для редактора. */
    public static Stage getEmptyStage() {
        return emptyStage;
    }

    /** Выбирает следующий уровень по кругу. */
    public static void selectNextStage() {
        currentStageIndex = (currentStageIndex + 1) % stages.size();
    }

    /** Выбирает предыдущий уровень по кругу. */
    public static void selectPreviousStage() {
        currentStageIndex = (currentStageIndex - 1 + stages.size()) % stages.size();
    }
}
