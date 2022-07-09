package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;

public interface PaintTool {
    void use(Painter painter, int x, int y, Click click);

    String getDescription();

    String getIcon();

    default boolean isAwaitingSecondClick() {
        return false;
    }

    default void setAwaitingSecondClick(boolean isAwaitingSecondClick) {

    }
}
