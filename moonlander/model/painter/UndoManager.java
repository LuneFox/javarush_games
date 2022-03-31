package com.javarush.games.moonlander.model.painter;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;

import java.util.LinkedList;

public class UndoManager implements Drawable {
    private static final MoonLanderGame game = MoonLanderGame.getInstance();
    private final LinkedList<Canvas> savedStates = new LinkedList<>();
    private int cursor = 0;

    @Override
    public void draw() {
        ColorPalette colorPalette = game.painter.colorPalette;

        boolean cannotUndo = (cursor == savedStates.size() - 1);
        game.setCellValueEx(colorPalette.posX + 2, colorPalette.posY + colorPalette.height + 1,
                cannotUndo ? Color.BLACK : Color.DARKBLUE, "←",
                cannotUndo ? Color.GRAY : Color.WHITE, 90);

        boolean cannotRedo = (cursor == 0);
        game.setCellValueEx(colorPalette.posX + 4, colorPalette.posY + colorPalette.height + 1,
                cannotRedo ? Color.BLACK : Color.DARKBLUE, "→",
                cannotRedo ? Color.GRAY : Color.WHITE, 90);

        game.setCellValueEx(colorPalette.posX + 3, colorPalette.posY + colorPalette.height + 1,
                Color.BLACK, String.valueOf(savedStates.size()), Color.WHITE);
    }

    public static String getHelpInfo() {
        return "Стрелка влево - отменить действие (UNDO)\n" +
                "Стрелка вправо - повторить действие (REDO)\n" +
                "Число посередине - количество действий\n" +
                "в журнале (макс. 30).\n\n" +
                "Стрелки вправо/влево на клавиатуре\n" +
                "дублируют функционал UNDO / REDO";
    }

    public void undo() {
        do {
            if (cursor >= savedStates.size() - 1) return;
            cursor++;
            game.painter.canvas = savedStates.get(cursor).createClone();
        } while (game.painter.canvas.equals(savedStates.get(cursor - 1)));
    }

    public void redo() {
        do {
            if (cursor <= 0) return;
            cursor--;
            game.painter.canvas = savedStates.get(cursor).createClone();
        } while (game.painter.canvas.equals(savedStates.get(cursor + 1)));
    }

    public void save() {
        removeRedundantSavedStates();

        if (savedStates.isEmpty() || !savedStates.peekFirst().equals(game.painter.canvas)) {
            savedStates.push(game.painter.canvas.createClone());
        }

        if (savedStates.size() > 30) {
            savedStates.removeLast();
        }
    }

    private void removeRedundantSavedStates() {
        while (cursor > 0) {
            savedStates.removeFirst();
            cursor--;
        }
    }
}
