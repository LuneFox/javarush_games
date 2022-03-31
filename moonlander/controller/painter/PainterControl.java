package com.javarush.games.moonlander.controller.painter;

import com.javarush.games.moonlander.MoonLanderGame;
import com.javarush.games.moonlander.controller.ControlStrategy;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;
import com.javarush.games.moonlander.model.painter.UndoManager;
import com.javarush.games.moonlander.model.painter.tools.*;

public class PainterControl implements ControlStrategy {
    private final static MoonLanderGame game = MoonLanderGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (x == 1 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.PENCIL);
        } else if (x == 2 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.PICKER);
        } else if (x == 3 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.FILLER);
        } else if (x == 4 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.REPLACER);
        } else if (x == 5 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.ERASER);
        } else if (x == 7 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.LINE);
        } else if (x == 8 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.RECTANGLE);
        } else if (x == 9 && y == 1) {
            game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.CIRCLE);
        } else if (x == 1 && y == 39) {
            game.painter.showNumbers = !game.painter.showNumbers;
        } else if (x == 3 && y == 39) {
            game.painter.undoManager.undo();
        } else if (x == 5 && y == 39) {
            game.painter.undoManager.redo();
        } else if (x == 39 && y == 6) {
            game.painter.canvas.clear();
        } else if (x == 8 && y == 4) {
            game.painter.canvas.resizeEditArea(-1, 0);
        } else if (x == 10 && y == 4) {
            game.painter.canvas.resizeEditArea(1, 0);
        } else if (x == 9 && y == 3) {
            game.painter.canvas.resizeEditArea(0, -1);
        } else if (x == 9 && y == 5) {
            game.painter.canvas.resizeEditArea(0, 1);
        } else if (x == 18 && y == 3) {
            game.painter.canvas.rotateClockWise();
            game.painter.undoManager.save();
        } else if (x == 16 && y == 3) {
            game.painter.canvas.rotateCounterClockWise();
            game.painter.undoManager.save();
        } else if (x == 16 && y == 5) {
            game.painter.canvas.flipHorizontal();
            game.painter.undoManager.save();
        } else if (x == 18 && y == 5) {
            game.painter.canvas.flipVertical();
            game.painter.undoManager.save();
        } else if (x == 13 && y == 5) {
            game.painter.canvas.moveDown();
            game.painter.undoManager.save();
        } else if (x == 13 && y == 3) {
            game.painter.canvas.moveUp();
            game.painter.undoManager.save();
        } else if (x == 12 && y == 4) {
            game.painter.canvas.moveLeft();
            game.painter.undoManager.save();
        } else if (x == 14 && y == 4) {
            game.painter.canvas.moveRight();
            game.painter.undoManager.save();
        } else if (x >= 31 && x < 40 && y >= 1 && y < 4) {
            game.painter.canvas.export();
        } else if (x >= 21 && x < 30 && y >= 1 && y < 4) {
            Painter.showHelp(game.painter.getHelp());
        } else if (x >= 2 && x <= 5 && y >= 3 && y <= 6) {
            game.painter.canvas.changeBackground();
        } else {
            game.painter.useTool(x, y, Click.LEFT);
            game.painter.colorPalette.pickPrimaryColor(x, y);
        }
    }

    @Override
    public void rightClick(int x, int y) {
        if (x == 1 && y == 1) {
            showToolTip(PaintToolType.PENCIL);
        } else if (x == 2 && y == 1) {
            showToolTip(PaintToolType.PICKER);
        } else if (x == 3 && y == 1) {
            showToolTip(PaintToolType.FILLER);
        } else if (x == 4 && y == 1) {
            showToolTip(PaintToolType.REPLACER);
        } else if (x == 5 && y == 1) {
            showToolTip(PaintToolType.ERASER);
        } else if (x == 7 && y == 1) {
            showToolTip(PaintToolType.LINE);
        } else if (x == 8 && y == 1) {
            showToolTip(PaintToolType.RECTANGLE);
        } else if (x == 9 && y == 1) {
            showToolTip(PaintToolType.CIRCLE);
        }  else if (x == 1 && y == 39) {
            Painter.showHelp("Показать / скрыть номера цветов");
        } else if (x >= 3 && x <= 5 && y == 39) {
            Painter.showHelp(UndoManager.getHelpInfo());
        } else if (x == 39 && y == 6) {
            Painter.showHelp("Очистить область");
        } else if (x >= 31 && x < 40 && y >= 1 && y < 4) {
            Painter.showHelp("Экспортировать рисунок в код");
        } else if (x >= 21 && x < 30 && y >= 1 && y < 4) {
            Painter.showHelp(game.painter.getHelp());
        } else if (x >= 2 && x <= 5 && y >= 3 && y <= 6) {
            Painter.showHelp(game.painter.getColorsInfo());
        } else if (x >= 8 && x <= 10 && y >= 3 && y <= 5) {
            Painter.showHelp("Изменить размер изображения");
        } else if (x >= 12 && x <= 14 && y >= 3 && y <= 5) {
            Painter.showHelp("Сдвинуть изображение\n\nОсторожно! Всё, что уезжает за рамки - стирается!");
        } else if (x >= 16 && x <= 18 && y == 3) {
            Painter.showHelp("Повернуть изображение\n\nВправо - по часовой\nВлево - против часовой");
        } else if (x >= 16 && x <= 18 && y == 5) {
            Painter.showHelp("Отразить изображение\n\nH - по горизонтали\nV - по вертикали");
        } else {
            game.painter.useTool(x, y, Click.RIGHT);
            game.painter.colorPalette.pickSecondaryColor(x, y);
        }
    }

    @Override
    public void pressRight() {
        game.painter.undoManager.redo();
    }

    @Override
    public void pressLeft() {
        game.painter.undoManager.undo();
    }

    @Override
    public void pressEnter() {
        game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.PENCIL);
    }

    @Override
    public void pressSpace() {
        game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.PICKER);
    }

    @Override
    public void pressEsc() {
        game.painter.selectedTool = PaintToolManager.selectTool(PaintToolType.ERASER);
    }


    private void showToolTip(PaintToolType type) {
        Painter.showHelp(PaintToolManager.getTool(type).getDescription());
    }
}
