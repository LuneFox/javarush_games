package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.gui.interactive.DifficultySelector;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.gui.interactive.ThemeSelector;

/** Настройки сложности, таймера, темы и сообщений. */
public class Options {
    /** Названия сложностей. */
    private static final String[] DIFFICULTY_NAMES = new String[]{
            "хомячок", "новичок", "любитель",
            "опытный", "эксперт", "отчаянный",
            "безумец", "бессмертный", "чак норрис"
    };

    /** Сообщение о сохранении настроек. */
    public static final String OPTIONS_SAVED = "Сохранено";

    /** Сложность игры. */
    private static int difficulty;
    /** Признак включённого таймера. */
    private static boolean timerEnabled;
    /** Признак режима разработчика. */
    private static boolean developerModeEnabled;
    /** Счётчик кликов до режима разработчика. */
    private static int developerModeCounter;

    /** Селектор сложности. */
    private static final DifficultySelector difficultySelector;
    /** Переключатель авто-покупки флажков. */
    private static final SwitchSelector autoBuyFlagsSelector;
    /** Переключатель режима на время. */
    private static final SwitchSelector timerEnabledSelector;
    /** Переключатель показа сообщений. */
    private static final SwitchSelector displayMessageSelector;
    /** Селектор темы. */
    private static final ThemeSelector themeSelector;

    static {
        difficultySelector = new DifficultySelector(49, 14);
        autoBuyFlagsSelector = new SwitchSelector(85, 32, "вручную", "авто");
        timerEnabledSelector = new SwitchSelector(85, 50, "нет", "да");
        displayMessageSelector = new SwitchSelector(85, 69, "скрывать", "показывать", SwitchSelector.State.ON);
        themeSelector = new ThemeSelector(51, 87);
    }

    /** Применяет настройки. */
    public static void apply() {
        timerEnabled = timerEnabledSelector.isEnabled();
        difficulty = difficultySelector.getDifficultySetting();
    }

    /** Возвращает название сложности. */
    public static String getDifficultyName(int relativeDifficulty) {
        return DIFFICULTY_NAMES[(relativeDifficulty / 5) - 1];
    }

    /** Возвращает сложность игры. */
    public static int getDifficulty() {
        return difficulty;
    }

    /** Сообщает, включён ли режим на время. */
    public static boolean isTimerEnabled() {
        return timerEnabled;
    }

    /** Сообщает, включён ли режим разработчика. */
    public static boolean isDeveloperModeEnabled() {
        return developerModeEnabled;
    }

    /** Включает режим разработчика. */
    public static void setDeveloperModeEnabled(boolean developerModeEnabled) {
        Options.developerModeEnabled = developerModeEnabled;
    }

    /** Возвращает счётчик кликов до режима разработчика. */
    public static int getDeveloperModeCounter() {
        return developerModeCounter;
    }

    /** Задаёт счётчик кликов до режима разработчика. */
    public static void setDeveloperModeCounter(int developerModeCounter) {
        Options.developerModeCounter = developerModeCounter;
    }

    /** Возвращает селектор сложности. */
    public static DifficultySelector getDifficultySelector() {
        return difficultySelector;
    }

    /** Возвращает переключатель авто-покупки флажков. */
    public static SwitchSelector getAutoBuyFlagsSelector() {
        return autoBuyFlagsSelector;
    }

    /** Возвращает переключатель режима на время. */
    public static SwitchSelector getTimerEnabledSelector() {
        return timerEnabledSelector;
    }

    /** Возвращает переключатель показа сообщений. */
    public static SwitchSelector getDisplayMessageSelector() {
        return displayMessageSelector;
    }

    /** Возвращает селектор темы. */
    public static ThemeSelector getThemeSelector() {
        return themeSelector;
    }
}
