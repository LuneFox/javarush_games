package com.javarush.games.snake.model;

public class Options {
    public enum Language {
        ENGLISH, RUSSIAN;

        public String getDisplayName() {
            return (this == ENGLISH) ? Strings.LANGUAGE_ENGLISH.get() : Strings.LANGUAGE_RUSSIAN.get();
        }
    }

    private static boolean accelerationEnabled = true;
    private static Language language = Language.ENGLISH;

    public static boolean isAccelerationEnabled() {
        return accelerationEnabled;
    }

    public static void toggleAcceleration() {
        accelerationEnabled = !accelerationEnabled;
    }

    public static Language getLanguage() {
        return language;
    }

    public static void toggleLanguage() {
        language = (language == Language.ENGLISH) ? Language.RUSSIAN : Language.ENGLISH;
    }
}
