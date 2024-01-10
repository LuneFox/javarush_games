package com.javarush.games.ticktacktoe.controller;

public enum Click {
    LEFT,
    RIGHT;


    public static int toBoard(int mouseClickPoint) {
        return (mouseClickPoint - 10) / 10;
    }
}


