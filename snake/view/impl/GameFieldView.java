package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Score;
import com.javarush.games.snake.model.Snake;
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
        drawMap();
        drawOrbs();
        drawSnake();
        drawInterface();
    }

    public void drawMap() {
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

        Message.print(0, 0, "hunger  : ", Color.CORAL);
        Message.print(0, 1, "strength: " + snakeLength, Color.WHITE);
        Message.print(0, 2, "element : " + currentElement, Color.YELLOW);
        Message.print(0, 3, "score   : " + score, Color.LIGHTBLUE);

        drawElementsPanel();
        drawHungerBar();

        if (snake.getAlmightyPower() >= 301) return;

        Message.print(20, 3, "power: " + snake.getAlmightyPower(), Color.CORAL);
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

            switch (element) {
                case NEUTRAL:
                    backgroundColor = snakeElement == element ? Color.PURPLE : Color.BLACK;
                    game.setCellValueEx(24, 2, backgroundColor, "N", textColor, 90);
                    break;
                case WATER:
                    backgroundColor = snakeElement == element ? Color.BLUE : Color.BLACK;
                    game.setCellValueEx(25, 2, backgroundColor, "W", textColor, 90);
                    break;
                case FIRE:
                    backgroundColor = snakeElement == element ? Color.RED : Color.BLACK;
                    game.setCellValueEx(26, 2, backgroundColor, "F", textColor, 90);
                    break;
                case EARTH:
                    backgroundColor = snakeElement == element ? Color.BROWN : Color.BLACK;
                    game.setCellValueEx(27, 2, backgroundColor, "E", textColor, 90);
                    break;
                case AIR:
                    backgroundColor = snakeElement == element ? Color.LIGHTSKYBLUE : Color.BLACK;
                    game.setCellValueEx(28, 2, backgroundColor, "A", textColor, 90);
                    break;
                case ALMIGHTY:
                    backgroundColor = snakeElement == element ? Color.ORCHID : Color.BLACK;
                    game.setCellValueEx(29, 2, backgroundColor, "S", textColor, 90);
                    break;
                default:
                    break;
            }
        }
    }

    public void drawSleepingLabel() {
        Color color = Color.WHITE;
        Message.print(-1, 15, "             ", color);
        Message.print(-1, 16, " SLEEPING... ", color);
        Message.print(-1, 17, "             ", color);
    }
}
