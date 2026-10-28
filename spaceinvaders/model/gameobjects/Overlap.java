package com.javarush.games.spaceinvaders.model.gameobjects;

/** Форма области пересечения двух спрайтов. */
public enum Overlap {
    /** Пересечение шире, чем выше: контакт сверху или снизу. */
    HORIZONTAL,

    /** Пересечение выше, чем шире: контакт сбоку. */
    VERTICAL,

    /** Пересечение почти квадратное: контакт углом. */
    SQUARE,

    /** Спрайты не соприкасаются. */
    NONE
}
