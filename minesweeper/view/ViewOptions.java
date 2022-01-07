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

    private final static int TEXT_LEFT_PADDING = 2;
    private final static int DIFF_OPTION_Y = 16;
    private final static int FLAG_OPTION_Y = 35;
    private final static int TIME_OPTION_Y = 54;
    private final static int SUB_TEXT_SHIFT_Y = 8;
    private final static int THEME_TEXT_Y = 73;
    private final static int THEME_PALETTE_X = 63;
    private final static int THEME_PALETTE_Y = 73;

    public final Area difficultyDownArea = new Area(49, 53, DIFF_OPTION_Y + 1, DIFF_OPTION_Y + 7);
    public final Area difficultyUpArea = new Area(93, 97, DIFF_OPTION_Y + 1, DIFF_OPTION_Y + 7);
    public final Area autoBuyFlagsArea = new Area(SWITCH_LEFTMOST_POSITION, SWITCH_RIGHTMOST_POSITION + 4, FLAG_OPTION_Y + 1, FLAG_OPTION_Y + 7);
    public final Area switchGameTimerArea = new Area(SWITCH_LEFTMOST_POSITION, SWITCH_RIGHTMOST_POSITION + 4, TIME_OPTION_Y + 1, TIME_OPTION_Y + 7);
    public final Area redThemeArea = new Area(THEME_PALETTE_X, THEME_PALETTE_X + 10, THEME_PALETTE_Y, THEME_PALETTE_Y + 10);
    public final Area greenThemeArea = new Area(THEME_PALETTE_X + 12, THEME_PALETTE_X + 22, THEME_PALETTE_Y, THEME_PALETTE_Y + 10);
    public final Area blueThemeArea = new Area(THEME_PALETTE_X + 24, THEME_PALETTE_X + 34, THEME_PALETTE_Y, THEME_PALETTE_Y + 10);

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
        game.print("настройки", Color.YELLOW, 28, 2);

        game.print("сложность", TEXT_LEFT_PADDING, DIFF_OPTION_Y - 1);
        arrowButton.drawAt((clickedArrowTimeoutL-- > 0) ? 48 : 49, DIFF_OPTION_Y, Image.Mirror.HORIZONTAL);
        arrowButton.drawAt((clickedArrowTimeoutR-- > 0) ? 94 : 93, DIFF_OPTION_Y, Image.Mirror.NONE);

        displayDifficultyBar();
        game.print(difficultyName, Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 93, DIFF_OPTION_Y + SUB_TEXT_SHIFT_Y, true);

        game.print("покупка флажков", TEXT_LEFT_PADDING, FLAG_OPTION_Y - 1);
        switchRail.drawAt(SWITCH_LEFTMOST_POSITION, FLAG_OPTION_Y + 2);
        if (game.shop.autoBuyFlagsEnabled) {
            if (switchFlagsPosition < SWITCH_RIGHTMOST_POSITION) switchFlagsPosition++;
            switchButton.replaceColor(Color.GREEN, 1);
            switchButton.drawAt(switchFlagsPosition, FLAG_OPTION_Y);
            game.print("авто", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 93, FLAG_OPTION_Y + SUB_TEXT_SHIFT_Y, true);
        } else {
            if (switchFlagsPosition > SWITCH_LEFTMOST_POSITION) switchFlagsPosition--;
            switchButton.replaceColor(Color.RED, 1);
            switchButton.drawAt(switchFlagsPosition, FLAG_OPTION_Y);
            game.print("вручную", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 91, FLAG_OPTION_Y + SUB_TEXT_SHIFT_Y, true);
        }


        game.print("игра на время", TEXT_LEFT_PADDING, TIME_OPTION_Y - 1);
        switchRail.drawAt(SWITCH_LEFTMOST_POSITION, TIME_OPTION_Y + 2);
        if (timerEnabledSetting) {
            if (switchTimePosition < SWITCH_RIGHTMOST_POSITION) switchTimePosition++;
            switchButton.replaceColor(Color.GREEN, 1);
            switchButton.drawAt(switchTimePosition, TIME_OPTION_Y);
            game.print("да", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 93, TIME_OPTION_Y + SUB_TEXT_SHIFT_Y, true);
        } else {
            if (switchTimePosition > SWITCH_LEFTMOST_POSITION) switchTimePosition--;
            switchButton.replaceColor(Color.RED, 1);
            switchButton.drawAt(switchTimePosition, TIME_OPTION_Y);
            game.print("нет", Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 94, TIME_OPTION_Y + SUB_TEXT_SHIFT_Y, true);

        }


        switch (Theme.get()) {
            case 0:
                game.print("тема: ссср", TEXT_LEFT_PADDING, THEME_TEXT_Y);
                break;
            case 1:
                game.print("тема: мята", TEXT_LEFT_PADDING, THEME_TEXT_Y);
                break;
            case 2:
                game.print("тема: небо", TEXT_LEFT_PADDING, THEME_TEXT_Y);
                break;
        }

        themePalette.replaceColor(Color.RED, 1);
        themePalette.replaceColor(Theme.get() == 0 ? Color.YELLOW : Color.WHITE, 3);
        themePalette.drawAt(THEME_PALETTE_X, THEME_PALETTE_Y);
        themePalette.replaceColor(Color.GREEN, 1);
        themePalette.replaceColor(Theme.get() == 1 ? Color.YELLOW : Color.WHITE, 3);
        themePalette.drawAt(THEME_PALETTE_X + 12, THEME_PALETTE_Y);
        themePalette.replaceColor(Theme.get() == 2 ? Color.YELLOW : Color.WHITE, 3);
        themePalette.replaceColor(Color.BLUE, 1);
        themePalette.drawAt(THEME_PALETTE_X + 24, THEME_PALETTE_Y);

        BUTTONS_CACHE.get(Button.ButtonID.BACK).draw();
    }


    private void displayDifficultyBar() {
        LinkedList<Image> bars = new LinkedList<>();
        for (int i = 0; i < difficultySetting / 5; i++) {
            Image bar = new Image(VisualElement.MENU_DIFFICULTY_BAR, (i * 4) + 56, DIFF_OPTION_Y);
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
