package com.javarush.games.game2048.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.game2048.Game2048;
import com.javarush.games.game2048.model.Field;
import com.javarush.games.game2048.model.Result;

/**
 * Presentation layer of the game: paints the board background, the info line
 * (turn counter, help hint, version) and shows result/help dialogs.
 */
public class View {
    private final Game2048 game;

    /**
     * @param game the game instance used for rendering
     */
    public View(Game2048 game) {
        this.game = game;
    }

    /**
     * Fills the whole board with the background color.
     */
    public void drawBackground() {
        for (int y = 0; y < Field.SIDE; y++) {
            for (int x = 0; x < Field.SIDE; x++) {
                game.setCellColor(x, y, Color.SADDLEBROWN);
            }
        }
    }

    /**
     * Draws the turn counter, the help hint and the version label.
     */
    public void drawInfo() {
        game.setCellValueEx(6, 0, Color.NONE, "Ходы: " + game.getTurnCount(), Color.LAWNGREEN, 15);
        game.setCellValueEx(0, 0, Color.NONE, "?", Color.WHITE, 75);
        game.setCellValueEx(6, 6, Color.NONE, Game2048.VERSION, Color.YELLOW, 15);
    }

    /**
     * Shows the result dialog — golden for a win, red for a loss — with a hint
     * to press Space to start a new game.
     *
     * @param result  the achieved result
     * @param message the message to display
     */
    public void showResultDialog(Result result, String message) {
        Color color = (result == Result.WIN) ? Color.PALEGOLDENROD : Color.RED;
        game.showMessageDialog(Color.BLACK, message + "\n\n(Пробел - начать заново)", color, 20);
    }

    /**
     * Shows the help dialog with the game rules and controls.
     */
    public void showHelpDialog() {
        String helpMessage = "Эта версия 2048 сделана по мотивам американского бильярда." +
                "\nШары складываются по правилам 2048. Цель - забить шар #8 в последнюю открытую лузу." +
                "\nПравый клик по пустой клетке установит биток, проталкивающий шары в лузы." +
                "\nЧем меньше сделаете ходов, и чем больше очков в лузах, тем больше будет ваш счёт." +
                "\nЕсли в последней лузе не будет шара #8 или если вы забьёте его раньше времени," +
                "\nто проиграете. Клик по лузе высвободит шары, разбив счёт лузы на части." +
                "\nЗажав клавишу ENTER можно продолжительно совершать случайные ходы. Удачи! :)";
        game.showMessageDialog(Color.YELLOW, helpMessage, Color.BLACK, 10);
    }
}
