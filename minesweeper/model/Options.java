package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.gui.interactive.DifficultySelector;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.gui.interactive.ThemeSelector;

public class Options {
    private static final String[] DIFFICULTY_NAMES = new String[]{
            "хомячок", "новичок", "любитель",
            "опытный", "эксперт", "отчаянный",
            "безумец", "бессмертный", "чак норрис"
    };

    public static final String OPTIONS_SAVED = "Сохранено";

    private static int difficulty;
    private static boolean timerEnabled;
    private static boolean developerModeEnabled;
    private static int developerModeCounter;

    private static final DifficultySelector difficultySelector;
    private static final SwitchSelector autoBuyFlagsSelector;
    private static final SwitchSelector timerEnabledSelector;
    private static final SwitchSelector displayMessageSelector;
    private static final ThemeSelector themeSelector;

    static {
        difficultySelector = new DifficultySelector(49, 14);
        autoBuyFlagsSelector = new SwitchSelector(85, 32, "вручную", "авто");
        timerEnabledSelector = new SwitchSelector(85, 50, "нет", "да");
        displayMessageSelector = new SwitchSelector(85, 69, "скрывать", "показывать", SwitchSelector.State.ON);
        themeSelector = new ThemeSelector(51, 87);
    }

    public static void apply() {
        timerEnabled = timerEnabledSelector.isEnabled();
        difficulty = difficultySelector.getDifficultySetting();
    }

    public static String getDifficultyName(int relativeDifficulty) {
        return DIFFICULTY_NAMES[(relativeDifficulty / 5) - 1];
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static boolean isTimerEnabled() {
        return timerEnabled;
    }

    public static boolean isDeveloperModeEnabled() {
        return developerModeEnabled;
    }

    public static void setDeveloperModeEnabled(boolean developerModeEnabled) {
        Options.developerModeEnabled = developerModeEnabled;
    }

    public static int getDeveloperModeCounter() {
        return developerModeCounter;
    }

    public static void setDeveloperModeCounter(int developerModeCounter) {
        Options.developerModeCounter = developerModeCounter;
    }

    public static DifficultySelector getDifficultySelector() {
        return difficultySelector;
    }

    public static SwitchSelector getAutoBuyFlagsSelector() {
        return autoBuyFlagsSelector;
    }

    public static SwitchSelector getTimerEnabledSelector() {
        return timerEnabledSelector;
    }

    public static SwitchSelector getDisplayMessageSelector() {
        return displayMessageSelector;
    }

    public static ThemeSelector getThemeSelector() {
        return themeSelector;
    }
}
