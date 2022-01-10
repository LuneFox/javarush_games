package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.graphics.*;

import java.util.LinkedList;

/**
 * Shows the OPTIONS screen.
 */

public final class ViewOptions extends View {

    public static final int CLICKED_ARROW_DURATION = 2;
    public static int clickedArrowTimeoutL;
    public static int clickedArrowTimeoutR;

    private static final int SWITCH_LEFTMOST_POSITION = 85;
    private static final int SWITCH_RIGHTMOST_POSITION = 93;
    private int switchFlagsPosition = SWITCH_LEFTMOST_POSITION;
    private int switchTimePosition = SWITCH_LEFTMOST_POSITION;

    private final static int TEXT_PADDING_L = 2;
    private final static int SUB_TEXT_SHIFT = 8;
    private final static int DIFF_ANCHOR = 16;
    private final static int FLAG_ANCHOR = 35;
    private final static int TIME_ANCHOR = 54;
    private final static int THEME_ANCHOR = 73;
    private final static int THEME_PADDING_L = 63;

    public final Area difficultyDownArea = new Area(49, 53, DIFF_ANCHOR + 1, DIFF_ANCHOR + 7);
    public final Area difficultyUpArea = new Area(93, 97, DIFF_ANCHOR + 1, DIFF_ANCHOR + 7);
    public final Area autoBuyFlagsArea = new Area(SWITCH_LEFTMOST_POSITION, SWITCH_RIGHTMOST_POSITION + 4, FLAG_ANCHOR + 1, FLAG_ANCHOR + 7);
    public final Area switchGameTimerArea = new Area(SWITCH_LEFTMOST_POSITION, SWITCH_RIGHTMOST_POSITION + 4, TIME_ANCHOR + 1, TIME_ANCHOR + 7);
    public final Area redThemeArea = new Area(THEME_PADDING_L, THEME_PADDING_L + 10, THEME_ANCHOR, THEME_ANCHOR + 10);
    public final Area greenThemeArea = new Area(THEME_PADDING_L + 12, THEME_PADDING_L + 22, THEME_ANCHOR, THEME_ANCHOR + 10);
    public final Area blueThemeArea = new Area(THEME_PADDING_L + 24, THEME_PADDING_L + 34, THEME_ANCHOR, THEME_ANCHOR + 10);

    public int difficultySetting;
    public boolean timerEnabledSetting;

    public ViewOptions(MinesweeperGame game) {
        this.game = game;
        this.difficultySetting = game.difficulty;
        this.timerEnabledSetting = game.timer.enabled;
        this.screen = Screen.OPTIONS;
    }

    @Override
    public void display() {
        super.display();
        Image arrowButton = IMAGES_CACHE.get(VisualElement.MENU_ARROW);
        Image switchButton = IMAGES_CACHE.get(VisualElement.MENU_SWITCH);
        Image switchRail = IMAGES_CACHE.get(VisualElement.MENU_SWITCH_RAIL);
        Image themePalette = IMAGES_CACHE.get(VisualElement.MENU_THEME_PALETTE);
        String difficultyName = Strings.DIFFICULTY_NAMES[Util.getDifficultyIndex(difficultySetting)];


        IMAGES_CACHE.get(VisualElement.WIN_MENU).draw();
        game.print("настройки", Color.YELLOW, -1, 2);

        game.print("сложность", TEXT_PADDING_L, DIFF_ANCHOR - 1);
        arrowButton.drawAt((clickedArrowTimeoutL-- > 0) ? 48 : 49, DIFF_ANCHOR, Image.Mirror.HORIZONTAL);
        arrowButton.drawAt((clickedArrowTimeoutR-- > 0) ? 94 : 93, DIFF_ANCHOR, Image.Mirror.NONE);

        displayDifficultyBar();
        game.print(difficultyName, Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 93, DIFF_ANCHOR + SUB_TEXT_SHIFT, true);

        game.print("покупка флажков", TEXT_PADDING_L, FLAG_ANCHOR - 1);
        switchRail.drawAt(SWITCH_LEFTMOST_POSITION, FLAG_ANCHOR + 2);
        if (game.shop.autoBuyFlagsEnabled) {
            if (switchFlagsPosition < SWITCH_RIGHTMOST_POSITION) switchFlagsPosition++;
            switchButton.replaceColor(Color.GREEN, 1);
            switchButton.drawAt(switchFlagsPosition, FLAG_ANCHOR);
            game.print("авто", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 93, FLAG_ANCHOR + SUB_TEXT_SHIFT, true);
        } else {
            if (switchFlagsPosition > SWITCH_LEFTMOST_POSITION) switchFlagsPosition--;
            switchButton.replaceColor(Color.RED, 1);
            switchButton.drawAt(switchFlagsPosition, FLAG_ANCHOR);
            game.print("вручную", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 91, FLAG_ANCHOR + SUB_TEXT_SHIFT, true);
        }


        game.print("игра на время", TEXT_PADDING_L, TIME_ANCHOR - 1);
        switchRail.drawAt(SWITCH_LEFTMOST_POSITION, TIME_ANCHOR + 2);
        if (timerEnabledSetting) {
            if (switchTimePosition < SWITCH_RIGHTMOST_POSITION) switchTimePosition++;
            switchButton.replaceColor(Color.GREEN, 1);
            switchButton.drawAt(switchTimePosition, TIME_ANCHOR);
            game.print("да", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 93, TIME_ANCHOR + SUB_TEXT_SHIFT, true);
        } else {
            if (switchTimePosition > SWITCH_LEFTMOST_POSITION) switchTimePosition--;
            switchButton.replaceColor(Color.RED, 1);
            switchButton.drawAt(switchTimePosition, TIME_ANCHOR);
            game.print("нет", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 94, TIME_ANCHOR + SUB_TEXT_SHIFT, true);

        }

        game.print("тема: " + Theme.getCurrentName(), TEXT_PADDING_L, THEME_ANCHOR);

        themePalette.replaceColor(Color.RED, 1);
        themePalette.replaceColor(Theme.getCurrentNumber() == 0 ? Color.YELLOW : Color.BLACK, 3);
        themePalette.drawAt(THEME_PADDING_L, THEME_ANCHOR);
        themePalette.replaceColor(Color.GREEN, 1);
        themePalette.replaceColor(Theme.getCurrentNumber() == 1 ? Color.YELLOW : Color.BLACK, 3);
        themePalette.drawAt(THEME_PADDING_L + 12, THEME_ANCHOR);
        themePalette.replaceColor(Theme.getCurrentNumber() == 2 ? Color.YELLOW : Color.BLACK, 3);
        themePalette.replaceColor(Color.BLUE, 1);
        themePalette.drawAt(THEME_PADDING_L + 24, THEME_ANCHOR);

        BUTTONS_CACHE.get(Button.ButtonID.BACK).draw();
    }


    private void displayDifficultyBar() {
        LinkedList<Image> bars = new LinkedList<>();
        for (int i = 0; i < difficultySetting / 5; i++) {
            Image bar = new Image(VisualElement.MENU_DIFFICULTY_BAR, (i * 4) + 56, DIFF_ANCHOR);
            if (i > 1) bar.replaceColor(Color.YELLOW, 1);
            if (i > 3) bar.replaceColor(Color.ORANGE, 1);
            if (i > 5) bar.replaceColor(Color.RED, 1);
            bars.add(bar);
        }
        bars.forEach(Image::draw);
    }

    public void changeDifficulty(boolean harder) {
        if (harder && difficultySetting < 45) {
            difficultySetting += 5;
        } else if (!harder && difficultySetting > 5) {
            difficultySetting -= 5;
        }
        display();
    }

    public void switchAutoBuyFlags() {
        game.shop.autoBuyFlagsEnabled = !game.shop.autoBuyFlagsEnabled;
        display();
    }

    public void switchGameTimer() {
        timerEnabledSetting = !timerEnabledSetting;
        display();
    }

    public void animateLeftArrow() {
        clickedArrowTimeoutL = CLICKED_ARROW_DURATION;
    }

    public void animateRightArrow() {
        clickedArrowTimeoutR = CLICKED_ARROW_DURATION;
    }
}
