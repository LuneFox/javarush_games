package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Score;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

import java.util.List;

public class GameFieldView extends View {
    private static GameFieldView instance;

    public static GameFieldView getInstance() {
        if (instance == null) instance = new GameFieldView();
        return instance;
    }

    @Override
    public void update() {
        drawField();
        drawOrbs();
        drawSnake();
        drawInterface();
    }

    public void drawField() {
        for (int x = 0; x < SnakeGame.SIZE; x++) {
            for (int y = 0; y < SnakeGame.SIZE; y++) {
                game.getStage().getTerrainMatrix()[y][x].draw(game);
            }
        }
    }

    private void drawOrbs() {
        final Stage stage = game.getStage();
        List<Orb> orbs = stage.getOrbs();
        orbs.forEach(orb -> orb.draw(game));
    }

    private void drawSnake() {
        game.getSnake().draw();
    }

    private void drawInterface() {
        final Snake snake = game.getSnake();
        final int snakeLength = snake.getLength();
        final Element currentElement = game.getSnake().getAvailableElements().get(0);
        final long score = Score.get();

        Message.print(0, 0, Strings.HUD_HUNGER.get(), Color.CORAL);
        Message.print(0, 1, Strings.HUD_STRENGTH.get() + snakeLength, Color.WHITE);
        Message.print(0, 2, Strings.HUD_ELEMENT.get() + Strings.getElementName(currentElement), Color.YELLOW);
        Message.print(0, 3, Strings.HUD_SCORE.get() + score, Color.LIGHTBLUE);

        drawElementsPanel();
        drawHungerBar();

        if (snake.getAlmightyPower() >= 301) return;

        Message.print(20, 3, Strings.HUD_POWER.get() + snake.getAlmightyPower(), Color.CORAL);
    }

    private void drawHungerBar() {
        for (int x = 0; x < 20; x++) {
            Color barColor;

            if (100 - game.getSnake().getHunger() > 50) {
                barColor = Color.GREEN;
            } else if (100 - game.getSnake().getHunger() > 25) {
                barColor = Color.YELLOW;
            } else {
                barColor = Color.RED;
            }

            barColor = ((100 - game.getSnake().getHunger()) / 5 <= x) ? Color.BLACK : barColor;

            game.setCellColor(x + 10, 0, barColor);
        }
    }

    public void drawElementsPanel() {
        final Element snakeElement = game.getSnake().getElement();

        for (Element element : Element.values()) {

            final Color textColor = game.getSnake().canUseElement(element) ? Color.WHITE : Color.DARKSLATEGRAY;
            Color backgroundColor;
            int x;

            switch (element) {
                case NEUTRAL:
                    backgroundColor = snakeElement == element ? Color.PURPLE : Color.BLACK;
                    x = 24;
                    break;
                case WATER:
                    backgroundColor = snakeElement == element ? Color.BLUE : Color.BLACK;
                    x = 25;
                    break;
                case FIRE:
                    backgroundColor = snakeElement == element ? Color.RED : Color.BLACK;
                    x = 26;
                    break;
                case EARTH:
                    backgroundColor = snakeElement == element ? Color.BROWN : Color.BLACK;
                    x = 27;
                    break;
                case AIR:
                    backgroundColor = snakeElement == element ? Color.LIGHTSKYBLUE : Color.BLACK;
                    x = 28;
                    break;
                case ALMIGHTY:
                    backgroundColor = snakeElement == element ? Color.ORCHID : Color.BLACK;
                    x = 29;
                    break;
                default:
                    continue;
            }

            final String label = Strings.getElementShortName(element);
            final int labelSize = (label.length() > 1) ? 60 : 90;
            game.setCellValueEx(x, 2, backgroundColor, label, textColor, labelSize);
        }
    }

    public void drawSleepingLabel() {
        Color color = Color.WHITE;
        Message.print(-1, 15, "             ", color);
        Message.print(-1, 16, Strings.GAME_SLEEPING.get(), color);
        Message.print(-1, 17, "             ", color);
    }
}
