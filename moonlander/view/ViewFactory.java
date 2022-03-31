package com.javarush.games.moonlander.view;

import com.javarush.games.moonlander.model.Phase;
import com.javarush.games.moonlander.view.painter.PainterView;

public class ViewFactory {
    public View createView(Phase phase) {
        switch (phase) {
            case PAINTER_EDITOR:
                return new PainterView();
            default:
                return new View() {
                    @Override
                    public void update() {

                    }
                };
        }
    }
}
