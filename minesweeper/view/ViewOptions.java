package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;
import com.javarush.games.minesweeper.graphics.Image;
import com.javarush.games.minesweeper.graphics.Picture;

import java.util.LinkedList;

/**
 * Shows the OPTIONS screen.
 */

public final class ViewOptions extends View {

    public final Area difficultyDownArea = new Area(new int[]{49, 53, 22, 28});
    public final Area difficultyUpArea = new Area(new int[]{93, 97, 22, 28});
    public final Area autoBuyFlagsArea = new Area(new int[]{80, 91, 41, 47});
    public final Area switchGameTimerArea = new Area(new int[]{80, 91, 64, 70});

    public int difficultySetting;
    public boolean timerEnabledSetting;

    public ViewOptions(MinesweeperGame game) {
        this.game = game;
        this.difficultySetting = game.difficulty;
        this.timerEnabledSetting = game.timer.enabled;
        this.screenType = Screen.ScreenType.OPTIONS;
    }

    @Override
    public void display() {
        super.display();
        Image arrowButton = IMAGES.get(Bitmap.MENU_ARROW);
        Image switchButton = IMAGES.get(Bitmap.MENU_SWITCH);
        Image switchRail = IMAGES.get(Bitmap.MENU_SWITCH_RAIL);
        String difficultyName = Strings.DIFFICULTY_NAMES[Util.getDifficultyIndex(difficultySetting)];


        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        game.print("настройки", Color.YELLOW, 28, 2);

        game.print("сложность", 2, 20);
        arrowButton.drawAt(93, 21, Image.Mirror.NONE);
        arrowButton.drawAt(49, 21, Image.Mirror.HORIZONTAL);
        displayDifficultyBar();
        game.print(difficultyName, Color.SALMON, 93, 29, true);

        game.print("покупка\nфлажков", 2, 40);
        switchRail.drawAt(80, 42);
        if (game.shop.autoBuyFlagsEnabled) {
            switchButton.replaceColor(Color.GREEN, 1);
            switchButton.drawAt(88, 40);
            game.print("авто", Color.SALMON, 93, 48, true);
        } else {
            switchButton.replaceColor(Color.RED, 1);
            switchButton.drawAt(80, 40);
            game.print("вручную", Color.SALMON, 91, 48, true);

        }

        game.print("игра на время", 2, 63);
        switchRail.drawAt(80, 65);
        if (timerEnabledSetting) {
            switchButton.replaceColor(Color.GREEN, 1);
            switchButton.drawAt(88, 63);
            game.print("да", Color.SALMON, 93, 71, true);
        } else {
            switchButton.replaceColor(Color.RED, 1);
            switchButton.drawAt(80, 63);
            game.print("нет", Color.SALMON, 94, 71, true);

        }

        BUTTONS.get(Button.ButtonID.BACK).draw();
    }


    private void displayDifficultyBar() {
        LinkedList<Picture> bars = new LinkedList<>();
        for (int i = 0; i < difficultySetting / 5; i++) {
            Picture bar = new Picture(Bitmap.MENU_DIFFICULTY_BAR, game, (i * 4) + 56, 21);
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
}
