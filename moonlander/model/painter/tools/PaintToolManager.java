package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;

import java.util.EnumMap;
import java.util.Map;


public class PaintToolManager {
    private static final Map<PaintToolType, PaintTool> TOOLS;
    private static final MoonLanderGame game;
    private static PaintTool selectedTool;
    private static PaintTool previousTool;

    private static final String PENCIL_ICON = "\uD83D\uDD8D️";
    private static final String PICKER_ICON = "\uD83E\uDDEA";
    private static final String FILLER_ICON = "\uD83E\uDD43";
    private static final String REPLACER_ICON = "\uD83C\uDFA8";
    private static final String ERASER_ICON = "\uD83E\uDDFC";
    private static final String LINE_ICON = "/";
    private static final String RECTANGLE_ICON = "□";
    private static final String CIRCLE_ICON = "◯";

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
        drawTool(PaintToolType.PENCIL, PENCIL_ICON, 1, 90);
        drawTool(PaintToolType.PICKER, PICKER_ICON, 2, 90);
        drawTool(PaintToolType.FILLER, FILLER_ICON, 3, 90);
        drawTool(PaintToolType.REPLACER, REPLACER_ICON, 4, 90);
        drawTool(PaintToolType.ERASER, ERASER_ICON, 5, 90);
        drawTool(PaintToolType.LINE, LINE_ICON, 7, 90);
        drawTool(PaintToolType.RECTANGLE, RECTANGLE_ICON, 8, 90);
        drawTool(PaintToolType.CIRCLE, CIRCLE_ICON, 9, 80);
    }

    private static void drawTool(PaintToolType type, String icon, int drawX, int textSize) {
        Color bgColor = (selectedTool == TOOLS.get(type))
                ? Color.DARKGREEN
                : Color.NONE;
        game.setCellValueEx(drawX, 1, bgColor, icon, Color.WHITE, textSize);
    }

    private static void drawExtraMarks() {
        drawSecondClickAwaitMark(PaintToolType.LINE, 7);
        drawSecondClickAwaitMark(PaintToolType.RECTANGLE, 8);
        drawSecondClickAwaitMark(PaintToolType.CIRCLE, 9);
    }

    private static void drawSecondClickAwaitMark(PaintToolType type, int x) {
        if (getTool(type).isAwaitingSecondClick()) {
            game.setCellValueEx(x, 0, Color.BLACK, "●", Color.RED, 90);
        }
    }
}
