package com.javarush.games.moonlander.model.painter;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;
import com.javarush.games.moonlander.model.painter.tools.PaintTool;
import com.javarush.games.moonlander.model.painter.tools.PaintToolManager;
import com.javarush.games.moonlander.model.painter.tools.PaintToolType;

public class Painter implements Drawable {
    static Label exportLabel = new Label("ЭКСПОРТ", Color.WHITE);
    static Label helpLabel = new Label("СПРАВКА", Color.WHITE);
    public UndoManager undoManager;
    public Canvas canvas;
    public ColorPalette colorPalette;
    public PaintTool selectedTool;
    public int primaryColor;
    public int secondaryColor;
    public boolean showNumbers;

    public Painter() {
        this.undoManager = new UndoManager();
        this.colorPalette = new ColorPalette(1, 8, 5, 30);
        this.canvas = new Canvas(8, 8, 32, 32);
        this.selectedTool = PaintToolManager.selectTool(PaintToolType.PENCIL);
        this.primaryColor = Color.YELLOW.ordinal();
        this.secondaryColor = Color.RED.ordinal();
    }

    public void draw() {
        canvas.draw();
        colorPalette.draw();
        undoManager.draw();
        PaintToolManager.drawToolsPanel();
        drawButtons();
    }

    public void useTool(int x, int y, Click click) {
        if (!canvas.checkClickOnEditArea(x, y)) return;
        undoManager.save();
        selectedTool.use(this, x, y, click);
        undoManager.save();
    }

    public void drawButtons() {
        MoonLanderGame game = MoonLanderGame.getInstance();
        // Show numbers
        game.setCellValueEx(colorPalette.posX, colorPalette.posY + colorPalette.height + 1,
                showNumbers ? Color.GREEN : Color.BLACK, "N", Color.WHITE, 90);

        // Export button
        for (int x = 31; x < 40; x++)
            for (int y = 1; y < 4; y++)
                game.setCellValueEx(x, y, Color.DARKBLUE, "");
        exportLabel.draw(game, 32, 2);

        // Help button
        for (int x = 21; x < 30; x++)
            for (int y = 1; y < 4; y++)
                game.setCellValueEx(x, y, Color.INDIGO, "");
        helpLabel.draw(game, 22, 2);

        // Resize buttons
        game.setCellValueEx(8, 4, Color.GRAY, "←", Color.BLACK, 90);
        game.setCellValueEx(10, 4, Color.GRAY, "→", Color.BLACK, 90);
        game.setCellValueEx(9, 4, Color.GRAY, "S", Color.BLACK, 90);
        game.setCellValueEx(9, 3, Color.GRAY, "↑", Color.BLACK, 90);
        game.setCellValueEx(9, 5, Color.GRAY, "↓", Color.BLACK, 90);

        // Move buttons
        game.setCellValueEx(12, 4, Color.GRAY, "←", Color.BLACK, 90);
        game.setCellValueEx(14, 4, Color.GRAY, "→", Color.BLACK, 90);
        game.setCellValueEx(13, 4, Color.GRAY, "M", Color.BLACK, 90);
        game.setCellValueEx(13, 3, Color.GRAY, "↑", Color.BLACK, 90);
        game.setCellValueEx(13, 5, Color.GRAY, "↓", Color.BLACK, 90);

        // Rotate buttons
        game.setCellValueEx(16, 3, Color.GRAY, "←", Color.BLACK, 90);
        game.setCellValueEx(18, 3, Color.GRAY, "→", Color.BLACK, 90);
        game.setCellValueEx(17, 3, Color.GRAY, "R", Color.BLACK, 90);

        // Flip buttons
        game.setCellValueEx(16, 5, Color.GRAY, "H", Color.BLACK, 90);
        game.setCellValueEx(18, 5, Color.GRAY, "V", Color.BLACK, 90);
        game.setCellValueEx(17, 5, Color.GRAY, "↔", Color.BLACK, 90);
    }


    public String getColorsInfo() {
        final String primaryColorName = Color.values()[primaryColor].name();
        final String secondaryColorName = Color.values()[secondaryColor].name();
        final String backgroundColorName = canvas.getBackgroundColor().name();
        return "Основной цвет: " + primaryColorName + " (#" + primaryColor + "), " + Util.COLOR_MAP.getOrDefault(primaryColorName, "#??????") + "\n" +
                "Вторичный цвет: " + secondaryColorName + " (#" + secondaryColor + "), " + Util.COLOR_MAP.getOrDefault(secondaryColorName, "#??????") + "\n" +
                "Прозрачный цвет (фон): " + backgroundColorName +
                " (#" + canvas.getBackgroundColor().ordinal() + "), " + Util.COLOR_MAP.getOrDefault(backgroundColorName, "#??????") +
                "\n\nОсновной цвет привязан к левому клику." +
                "\nВторичный цвет привязан к правому клику." +
                "\nЛевый клик по этой области поменяет цвет фона.";

    }

    public String getHelp() {
        return "Привет! Тут нет игры Moonlander, " +
                "но есть кое-что получше - JavaRush Paint!\n\n" +
                "Здесь ты можешь нарисовать любую ракету и " +
                "вставить её в свою реализацию игры!\n" +
                "Да и не только ракету, а всё, что захочешь :)\n\n" +
                "Правый клик по любому инструменту или кнопке " +
                "напишет, что они делают или за что отвечают.\n" +
                "Как закончишь рисовать шедевр - жми на ЭКСПОРТ!";
    }

    public static void showHelp(String text) {
        MoonLanderGame.getInstance().showMessageDialog(Color.YELLOW, text, Color.BLACK, 10);
    }
}



