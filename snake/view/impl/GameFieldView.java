package com.javarush.games.snake.view.impl;

import com.javarush.games.snake.view.View;


public class GameFieldView extends View {
    private static GameFieldView instance;

    public static GameFieldView getInstance() {
        if (instance == null) instance = new GameFieldView();
        return instance;
    }

    @Override
    public void update() {

    }
}
