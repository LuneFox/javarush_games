package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;
import com.javarush.games.moonlander.model.painter.tools.impl.*;

import java.util.EnumMap;
import java.util.Map;

public class PaintToolManager {
    private static final Map<PaintToolType, PaintTool> TOOLS;
    private static final MoonLanderGame game;
    private static PaintTool selectedTool;
    private static PaintTool previousTool;

    static {
        game = MoonLanderGame.getInstance();
        TOOLS = new EnumMap<>(PaintToolType.class);
        TOOLS.put(PaintToolType.PENCIL, new PencilTool());
        TOOLS.put(PaintToolType.FILLER, new FillerTool());
        TOOLS.put(PaintToolType.REPLACER, new ReplacerTool());
        TOOLS.put(PaintToolType.PICKER, new PickerTool());
        TOOLS.put(PaintToolType.ERASER, new EraserTool());
        TOOLS.put(PaintToolType.LINE, new LineTool());
        TOOLS.put(PaintToolType.RECTANGLE, new RectangleTool());
        TOOLS.put(PaintToolType.CIRCLE, new CircleTool());
    }

    private PaintToolManager() {

    }

    public static PaintTool selectTool(PaintToolType type) {
        rememberPreviousTool();
        selectedTool = TOOLS.get(type);
        TOOLS.values().forEach(tool -> tool.setAwaitingSecondClick(false));
        return TOOLS.get(type);
    }

    public static PaintTool getTool(PaintToolType type) {
        return TOOLS.get(type);
    }

    private static void rememberPreviousTool() {
        if (game.painter == null) return;
        if (game.painter.selectedTool != TOOLS.get(PaintToolType.PICKER)) {
            previousTool = game.painter.selectedTool;
        }
    }

    public static PaintTool getPreviousTool() {
        if (previousTool == null) return TOOLS.get(PaintToolType.PENCIL);
        selectedTool = previousTool;
        return previousTool;
    }

    public static void drawToolsPanel() {
        drawTools();
        drawExtraMarks();
    }

    private static void drawTools() {
        drawToolIcon(PaintToolType.PENCIL, 1);
        drawToolIcon(PaintToolType.PICKER, 2);
        drawToolIcon(PaintToolType.FILLER, 3);
        drawToolIcon(PaintToolType.REPLACER, 4);
        drawToolIcon(PaintToolType.ERASER, 5);
        drawToolIcon(PaintToolType.LINE, 7);
        drawToolIcon(PaintToolType.RECTANGLE, 8);
        drawToolIcon(PaintToolType.CIRCLE, 9);
    }

    private static void drawToolIcon(PaintToolType type, int drawX) {
        final PaintTool tool = TOOLS.get(type);
        Color bgColor = (selectedTool == tool)
                ? Color.DARKGREEN
                : Color.NONE;
        game.setCellValueEx(drawX, 1, bgColor, tool.getIcon(), Color.WHITE, 80);
    }

    private static void drawExtraMarks() {
        drawSecondClickAwaitMark(PaintToolType.LINE, 7);
        drawSecondClickAwaitMark(PaintToolType.RECTANGLE, 8);
        drawSecondClickAwaitMark(PaintToolType.CIRCLE, 9);
    }

    private static void drawSecondClickAwaitMark(PaintToolType type, int x) {
        if (getTool(type).isAwaitingSecondClick()) {
            game.setCellValueEx(x, 0, Color.BLACK, "●", Color.RED, 80);
        }
    }
}
