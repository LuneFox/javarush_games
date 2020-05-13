package com.javarush.games.snake;

class Triggers {
    static boolean waterOrbObtained = false;
    static boolean fireOrbObtained = false;
    static boolean earthOrbObtained = false;
    static boolean airOrbObtained = false;
    static boolean almightyOrbObtained = false;
    static boolean speedUpDelay = true;

    static void reset() {
        waterOrbObtained = false;
        fireOrbObtained = false;
        earthOrbObtained = false;
        airOrbObtained = false;
        almightyOrbObtained = false;
    }
}
