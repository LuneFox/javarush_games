package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Drawable;
import com.javarush.games.snake.view.Sign;

import java.util.Date;

/** Собираемая сфера со стихией и мигающей подсветкой. */
public abstract class Orb extends GameObject implements Drawable {
    private static final int SYMBOL_TEXT_SIZE = 90;
    private static final int BLINK_INTERVAL_MS = 250;
    private static final int WISDOM_SCORE_DIVIDER = 10;

    private final Element element;
    private final String sign;
    private final Color backgroundColorOn;
    private final Color backgroundColorOff;

    private Date blinkTimeStamp;
    private boolean blinkState;
    private boolean isCollected;

    /** Создаёт сферу нужной стихии. */
    public static Orb create(int x, int y, Element element) {
        switch (element) {
            case WATER:
                return new WaterOrb(x, y);
            case FIRE:
                return new FireOrb(x, y);
            case EARTH:
                return new EarthOrb(x, y);
            case AIR:
                return new AirOrb(x, y);
            case ALMIGHTY:
                return new AlmightyOrb(x, y);
            default:
                return new NeutralOrb(x, y);
        }
    }

    /** Создаёт сферу с заданным видом. */
    protected Orb(int x, int y, Element element, Color backgroundColorOn, Color backgroundColorOff, Sign symbol) {
        super(x, y);
        this.element = element;
        this.backgroundColorOn = backgroundColorOn;
        this.backgroundColorOff = backgroundColorOff;
        this.sign = Sign.getSign(symbol);
        blinkTimeStamp = new Date();
    }

    /** Рисует сферу и переключает её мигание. */
    @Override
    public void draw(Game target) {
        if (isCollected) return;

        updateBlinkState();
        final Color backgroundColor = blinkState ? backgroundColorOn : backgroundColorOff;
        target.setCellValueEx(x, y, backgroundColor, sign, Color.WHITE, SYMBOL_TEXT_SIZE);
    }

    /** Переключает состояние мигания по таймеру. */
    private void updateBlinkState() {
        if (new Date().getTime() - blinkTimeStamp.getTime() <= BLINK_INTERVAL_MS) return;

        blinkState = !blinkState;
        blinkTimeStamp = new Date();
    }

    /** Подбирает сферу, если голова на ней. */
    public final void collect(Snake snake) {
        if (!snake.isHeadTouchingOrb(this)) return;

        isCollected = true;

        snake.eat();
        game.getScore().add(calculateScore());
        game.applyNormalTurnDelay();
        applyEffect(snake);
    }

    /** Считает очки за сферу. */
    private long calculateScore() {
        final long bonus = game.calculateOrbBonus();
        return element == Element.NEUTRAL ? bonus / WISDOM_SCORE_DIVIDER : bonus;
    }

    /** Применяет эффект стихии к змейке. */
    protected void applyEffect(Snake snake) {
        if (element == Element.NEUTRAL) return;

        snake.learnElement(element);
        snake.switchToElement(element);
    }

    /** Подобрана ли сфера. */
    public boolean isCollected() {
        return isCollected;
    }
}
