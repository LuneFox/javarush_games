package com.javarush.games.snake.model;

public class Options {
    public enum Language {
        ENGLISH, RUSSIAN;

        public String getDisplayName() {
            return (this == ENGLISH) ? Strings.LANGUAGE_ENGLISH.get() : Strings.LANGUAGE_RUSSIAN.get();
        }
    }

    private static boolean accelerationEnabled;
    private static Language language;

    static {
        accelerationEnabled = true;
        language = Language.ENGLISH;
    }

    public static boolean isAccelerationEnabled() {
        return accelerationEnabled;
    }

    public static void switchAccelerationEnabled() {
        accelerationEnabled = !accelerationEnabled;
    }

    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language language) {
        Options.language = language;
    }

    public static void switchLanguage() {
        language = (language == Language.ENGLISH) ? Language.RUSSIAN : Language.ENGLISH;
    }
}
