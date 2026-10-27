package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

/** Игровое поле и HUD. */
public class GameFieldView extends View {
    private static final int HUNGER_BAR_X = 10;
    private static final int HUNGER_BAR_Y = 0;
    private static final int HUNGER_BAR_WIDTH = 20;
    private static final int HUNGER_POINTS_PER_SEGMENT = 5;
    private static final int HUNGER_GREEN_ABOVE = 50;
    private static final int HUNGER_YELLOW_ABOVE = 25;
    private static final int ELEMENTS_PANEL_Y = 2;
    private static final int LABEL_TEXT_SIZE = 90;
    private static final int WIDE_LABEL_TEXT_SIZE = 60;
    private static final int POWER_LABEL_X = 20;
    private static final int POWER_LABEL_Y = 3;
    private static final int SLEEPING_LABEL_Y = 16;
    private static final String SLEEPING_LABEL_BORDER = "             ";

    private static GameFieldView instance;

    /** Возвращает единственный экземпляр. */
    public static GameFieldView getInstance() {
        if (instance == null) instance = new GameFieldView();
        return instance;
    }

    /** Обновляет поле, тень, орбы, змейку и HUD. */
    @Override
    public void update() {
        drawField();
        drawSnakeShadow();
        drawOrbs();
        drawSnake();
        drawInterface();

        if (game.isPaused()) {
            drawSleepingLabel();
        }
    }

    /** Рисует тень под летящей змейкой. */
    private void drawSnakeShadow() {
        game.getSnake().drawShadow(game);
    }

    /** Рисует все клетки поля. */
    public void drawField() {
        for (int x = 0; x < SnakeGame.SIZE; x++) {
            for (int y = 0; y < SnakeGame.SIZE; y++) {
                game.getStage().getTerrain(x, y).draw(game);
            }
        }
    }

    /** Рисует орбы. */
    private void drawOrbs() {
        game.getStage().getOrbs().forEach(orb -> orb.draw(game));
    }

    /** Рисует змейку. */
    private void drawSnake() {
        game.getSnake().draw(game);
    }

    /** Рисует HUD. */
    private void drawInterface() {
        final Snake snake = game.getSnake();

        Message.print(0, 0, Strings.HUD_HUNGER.get(), Color.CORAL);
        Message.print(0, 1, Strings.HUD_STRENGTH.get() + snake.getLength(), Color.WHITE);
        Message.print(0, 2, Strings.HUD_ELEMENT.get() + Strings.getElementName(snake.getElement()), Color.YELLOW);
        Message.print(0, 3, Strings.HUD_SCORE.get() + game.getScore().get(), Color.LIGHTBLUE);

        drawElementsPanel();
        drawHungerBar();

        if (snake.getAlmightyPower() < Snake.FULL_ALMIGHTY_POWER) {
            Message.print(POWER_LABEL_X, POWER_LABEL_Y, Strings.HUD_POWER.get() + snake.getAlmightyPower(), Color.CORAL);
        }
    }

    /** Рисует шкалу голода. */
    private void drawHungerBar() {
        final int hungerLeft = Snake.MAX_HUNGER - game.getSnake().getHunger();

        for (int segment = 0; segment < HUNGER_BAR_WIDTH; segment++) {
            final Color color = isBarSegmentEmpty(hungerLeft, segment)
                    ? Color.BLACK
                    : hungerColor(hungerLeft);
            game.setCellColor(HUNGER_BAR_X + segment, HUNGER_BAR_Y, color);
        }
    }

    /** Пуст ли сегмент шкалы голода. */
    private boolean isBarSegmentEmpty(int hungerLeft, int segment) {
        return hungerLeft / HUNGER_POINTS_PER_SEGMENT <= segment;
    }

    /** Цвет шкалы по остатку голода. */
    private Color hungerColor(int hungerLeft) {
        if (hungerLeft > HUNGER_GREEN_ABOVE) return Color.GREEN;
        if (hungerLeft > HUNGER_YELLOW_ABOVE) return Color.YELLOW;
        return Color.RED;
    }

    /** Рисует панель стихий. */
    public void drawElementsPanel() {
        final Snake snake = game.getSnake();
        final Element currentElement = snake.getElement();

        for (Element element : Element.values()) {
            final int x = elementPanelX(element);

            if (x < 0) continue;

            final Color textColor = snake.canUseElement(element) ? Color.WHITE : Color.DARKSLATEGRAY;
            final Color backgroundColor = currentElement == element ? elementColor(element) : Color.BLACK;
            final String label = Strings.getElementShortName(element);
            final int labelSize = label.length() > 1 ? WIDE_LABEL_TEXT_SIZE : LABEL_TEXT_SIZE;

            game.setCellValueEx(x, ELEMENTS_PANEL_Y, backgroundColor, label, textColor, labelSize);
        }
    }

    /** X-позиция стихии на панели. */
    private int elementPanelX(Element element) {
        switch (element) {
            case NEUTRAL:
                return 24;
            case WATER:
                return 25;
            case FIRE:
                return 26;
            case EARTH:
                return 27;
            case AIR:
                return 28;
            case ALMIGHTY:
                return 29;
            default:
                return -1;
        }
    }

    /** Цвет стихии на панели. */
    private Color elementColor(Element element) {
        switch (element) {
            case NEUTRAL:
                return Color.PURPLE;
            case WATER:
                return Color.BLUE;
            case FIRE:
                return Color.RED;
            case EARTH:
                return Color.BROWN;
            case AIR:
                return Color.LIGHTSKYBLUE;
            case ALMIGHTY:
                return Color.ORCHID;
            default:
                return Color.BLACK;
        }
    }

    /** Показывает надпись «спит». */
    public void drawSleepingLabel() {
        Message.print(-1, SLEEPING_LABEL_Y - 1, SLEEPING_LABEL_BORDER, Color.WHITE);
        Message.print(-1, SLEEPING_LABEL_Y, Strings.GAME_SLEEPING.get(), Color.WHITE);
        Message.print(-1, SLEEPING_LABEL_Y + 1, SLEEPING_LABEL_BORDER, Color.WHITE);
    }
}
