package com.javarush.games.snake.model;

/** Настройки игры: ускорение и язык. */
public class Options {
    /** Язык интерфейса. */
    public enum Language {
        ENGLISH, RUSSIAN;

        /** Возвращает название языка для меню. */
        public String getDisplayName() {
            return (this == ENGLISH) ? Strings.LANGUAGE_ENGLISH.get() : Strings.LANGUAGE_RUSSIAN.get();
        }
    }

    private static boolean accelerationEnabled = true;
    private static Language language = Language.ENGLISH;

    /** Включено ли ускорение. */
    public static boolean isAccelerationEnabled() {
        return accelerationEnabled;
    }

    /** Переключает ускорение. */
    public static void toggleAcceleration() {
        accelerationEnabled = !accelerationEnabled;
    }

    /** Возвращает текущий язык. */
    public static Language getLanguage() {
        return language;
    }

    /** Переключает язык интерфейса. */
    public static void toggleLanguage() {
        language = (language == Language.ENGLISH) ? Language.RUSSIAN : Language.ENGLISH;
    }
}
