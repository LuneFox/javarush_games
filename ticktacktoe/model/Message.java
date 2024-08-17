package com.javarush.games.ticktacktoe.model;

/**
 * Текст сообщений для отображения в игре
 *
 * @author LuneFox
 */
public class Message {
    /** Версия игры */
    public static final String VERSION = "1.2";
    /** Название игры */
    public static final String GAME_NAME = "<РЕВЕРСИ>";
    /** Сообщение, приглашающее играть за белых в начале игры */
    public static final String CHOOSE_WHITE_SIDE = "Игра за белых - Enter";
    /** Сообщение о победе чёрной стороны */
    public static final String VICTORY_BLACK = "Победили чёрные!";
    /** Сообщение о победе белой стороны */
    public static final String VICTORY_WHITE = "Победили белые!";
    /** Сообщение о победе в случае ничьей */
    public static final String VICTORY_DRAW = "Ничья!";
    /** Сообщение при выборе скорости действий компьютера */
    public static final String COMPUTER_SPEED = "Скорость: ";
}



