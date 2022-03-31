package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;

import java.util.EnumMap;
import java.util.Map;


public class PaintToolManager {
    private static final Map<PaintToolType, PaintTool> TOOLS;
    private static final MoonLanderGame game;
    private static PaintTool currentTool;
    private static PaintTool previousTool;

    private static final String PENCIL_ICON = "\uD83D\uDD8D️";
    private static final String PICKER_ICON = "\uD83E\uDDEA";
    private static final String FILLER_ICON = "\uD83E\uDEA3";
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
        currentTool = TOOLS.get(type);
        TOOLS.values().forEach(tool -> tool.setAwaitingSecondClick(false));
        return TOOLS.get(type);
    }

    public static PaintTool getTool(PaintToolType type) {
        return TOOLS.get(type);
    }

    private static void rememberPreviousTool() {
        if (game.painter != null) {
            if (game.painter.selectedTool != TOOLS.get(PaintToolType.PICKER)) {
                previousTool = game.painter.selectedTool;
            }
        }
    }

    public static PaintTool getPreviousTool() {
        if (previousTool == null) return TOOLS.get(PaintToolType.PENCIL);
        currentTool = previousTool;
        return previousTool;
    }

    public static void drawToolPanel() {
        Color bgColor;

        bgColor = currentTool == TOOLS.get(PaintToolType.PENCIL) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(1, 1, bgColor, PENCIL_ICON, Color.WHITE, 90);

        bgColor = currentTool == TOOLS.get(PaintToolType.PICKER) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(2, 1, bgColor, PICKER_ICON, Color.WHITE, 90);

        bgColor = currentTool == TOOLS.get(PaintToolType.FILLER) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(3, 1, bgColor, FILLER_ICON, Color.WHITE, 90);

        bgColor = currentTool == TOOLS.get(PaintToolType.REPLACER) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(4, 1, bgColor, REPLACER_ICON, Color.WHITE, 90);

        bgColor = currentTool == TOOLS.get(PaintToolType.ERASER) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(5, 1, bgColor, ERASER_ICON, Color.WHITE, 90);

        bgColor = currentTool == TOOLS.get(PaintToolType.LINE) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(7, 1, bgColor, LINE_ICON, Color.WHITE, 90);
        if (getTool(PaintToolType.LINE).isAwaitingSecondClick()) game.setCellValueEx(7, 0, Color.BLACK, "●", Color.RED, 90);

        bgColor = currentTool == TOOLS.get(PaintToolType.RECTANGLE) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(8, 1, bgColor, RECTANGLE_ICON, Color.WHITE, 90);
        if (getTool(PaintToolType.RECTANGLE).isAwaitingSecondClick()) game.setCellValueEx(8, 0, Color.BLACK, "●", Color.RED, 90);

        bgColor = currentTool == TOOLS.get(PaintToolType.CIRCLE) ? Color.DARKGREEN : Color.NONE;
        game.setCellValueEx(9, 1, bgColor, CIRCLE_ICON, Color.WHITE, 80);
        if (getTool(PaintToolType.CIRCLE).isAwaitingSecondClick()) game.setCellValueEx(9, 0, Color.BLACK, "●", Color.RED, 90);

    }
}
