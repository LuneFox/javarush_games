package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.gui.interactive.DifficultySelector;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.gui.interactive.ThemeSelector;

public class Options {
    public static final String[] DIFFICULTY_NAMES = new String[]{
            "хомячок", "новичок", "любитель",
            "опытный", "эксперт", "отчаянный",
            "безумец", "бессмертный", "чак норрис"
    };
    public static final String OPTIONS_SAVED = "Сохранено";

    public static int difficulty;
    public static boolean timerEnabled;
    public static boolean developerModeEnabled;
    public static int developerModeCounter;

    public static final DifficultySelector difficultySelector;
    public static final SwitchSelector autoBuyFlagsSelector;
    public static final SwitchSelector timerEnabledSelector;
    public static final SwitchSelector displayMessageSelector;
    public static final ThemeSelector themeSelector;

    static {
        difficultySelector = new DifficultySelector(49, 14);
        autoBuyFlagsSelector = new SwitchSelector(85, 32, "вручную", "авто");
        timerEnabledSelector = new SwitchSelector(85, 50, "нет", "да");
        displayMessageSelector = new SwitchSelector(85, 69, "скрывать", "показывать", true);
        themeSelector = new ThemeSelector(51, 87);
    }

    public static void apply() {
        timerEnabled = timerEnabledSelector.isEnabled();
        difficulty = difficultySelector.getDifficultySetting();
    }
}
