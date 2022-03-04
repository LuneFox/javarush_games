package com.javarush.games.minesweeper.model.options;

public class Options {
    public static SwitchSelector autoBuyFlagsSelector;
    public static SwitchSelector timerEnabledSelector;
    public static ThemeSelector themeSelector;
    public static DifficultySelector difficultySelector;

    public static void initialize() {
        autoBuyFlagsSelector = new SwitchSelector();
        timerEnabledSelector = new SwitchSelector();
        themeSelector = new ThemeSelector();
        difficultySelector = new DifficultySelector();
    }
}
