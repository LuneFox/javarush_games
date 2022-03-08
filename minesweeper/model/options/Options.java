package com.javarush.games.minesweeper.model.options;

public class Options {
    // Store copies of options apart from corresponding switches to apply them only to a new game
    public static int difficulty;
    public static boolean timerEnabled;

    public static DifficultySelector difficultySelector;
    public static SwitchSelector autoBuyFlagsSelector;
    public static SwitchSelector timerEnabledSelector;
    public static SwitchSelector displayMessageSelector;
    public static ThemeSelector themeSelector;

    public static void initialize() {
        difficultySelector = new DifficultySelector(49, 14);
        autoBuyFlagsSelector = new SwitchSelector(85, 32, "вручную", "авто");
        timerEnabledSelector = new SwitchSelector(85, 50, "нет", "да");
        displayMessageSelector = new SwitchSelector(85, 69, "скрывать", "показывать", true);
        themeSelector = new ThemeSelector(63, 87);
    }

    public static void apply() {
        timerEnabled = timerEnabledSelector.isEnabled();
        difficulty = difficultySelector.getDifficultySetting();
    }
}
