package com.javarush.games.moonlander.view.painter;


import com.javarush.games.moonlander.MoonLanderGame;
import com.javarush.games.moonlander.view.View;

public class PainterView extends View {
    @Override
    public void update() {
        MoonLanderGame.getInstance().painter.draw();
    }
}
