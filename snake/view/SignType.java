package com.javarush.games.snake.view;

import com.javarush.games.snake.model.Strings;

/** Набор символов. */
public enum SignType {
    KANJI, EMOJI;

    /** Название набора для меню. */
    public String getLocalizedName() {
        return (this == KANJI) ? Strings.SIGN_TYPE_KANJI.get() : Strings.SIGN_TYPE_EMOJI.get();
    }
}
