package com.javarush.games.snake.view;

import com.javarush.games.snake.model.Strings;

public enum SignType {
    KANJI, EMOJI;

    public String getLocalizedName() {
        return (this == KANJI) ? Strings.SIGN_TYPE_KANJI.get() : Strings.SIGN_TYPE_EMOJI.get();
    }
}
