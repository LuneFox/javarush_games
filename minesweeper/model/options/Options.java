package com.javarush.games.minesweeper.model.options;

public class Options {
    public static boolean autoBuyFlagsEnabled;
    public static boolean timerEnabled;
    public static int difficulty;

    public static SwitchSelector autoBuyFlagsSelector;
    public static SwitchSelector timerEnabledSelector;
    public static ThemeSelector themeSelector;
    public static DifficultySelector difficultySelector;

    public static void initialize() {
        autoBuyFlagsSelector = new SwitchSelector(85, 35, "вручную", "авто");
        timerEnabledSelector = new SwitchSelector(85, 54, "нет", "да");
        themeSelector = new ThemeSelector(63, 73);
        difficultySelector = new DifficultySelector(49, 16);
    }

    public static void apply() {
        autoBuyFlagsEnabled = autoBuyFlagsSelector.isEnabled();
        timerEnabled = timerEnabledSelector.isEnabled();
        difficulty = difficultySelector.getDifficultySetting();
    }
}
