package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;

/** Переключатель с двумя состояниями. */
public class SwitchSelector extends InteractiveObject {
    /** Состояние. */
    private State state;
    /** Направляющая переключателя. */
    private final Image rail;
    /** Ручка переключателя. */
    private final Image handle;
    /** Левая граница переключателя. */
    private final int leftStopper;
    /** Правая граница переключателя. */
    private final int rightStopper;
    /** Подпись выключенного состояния. */
    private final String labelOff;
    /** Подпись включённого состояния. */
    private final String labelOn;

    /** Состояние переключателя. */
    public enum State {
        /** Включено. */
        ON,
        /** Выключено. */
        OFF
    }

    /** Создаёт экземпляр SwitchSelector. */
    public SwitchSelector(int x, int y, String labelOff, String labelOn) {
        super(x, y);

        this.rail = new Image(ImageType.GUI_SWITCH_RAIL, x, y + 2);
        this.handle = new Image(ImageType.GUI_SWITCH_HANDLE, x, y);

        this.width = rail.width;
        this.height = handle.height;

        this.leftStopper = x;
        this.rightStopper = x + rail.width - handle.width;

        this.labelOff = labelOff;
        this.labelOn = labelOn;

        this.state = State.OFF;
    }

    /** Создаёт экземпляр SwitchSelector. */
    public SwitchSelector(int x, int y, String labelOff, String labelOn, State state) {
        this(x, y, labelOff, labelOn);
        this.state = state;

        if (state == State.ON) {
            handle.setPosition(rightStopper, y);
        }
    }

    /** Рисует объект на экране. */
    @Override
    public void draw() {
        moveHandle();
        drawSwitch();
        printLabel();
    }

    /** Двигает ручку переключателя. */
    private void moveHandle() {
        if (state == State.ON && handle.x < rightStopper) {
            handle.x++;
        } else if (state == State.OFF && handle.x > leftStopper) {
            handle.x--;
        }
    }

    /** Рисует переключатель. */
    private void drawSwitch() {
        rail.draw();
        final int HANDLE_BODY_COLOR = 1;
        handle.changeColor(state == State.ON ? Color.GREEN : Color.RED, HANDLE_BODY_COLOR);
        handle.draw();
    }

    /** Печатает подпись. */
    private void printLabel() {
        String label = (state == State.ON) ? labelOn : labelOff;
        Color textColor = Theme.MAIN_MENU_QUOTE_FRONT.getColor();
        Printer.print(label, textColor, (Display.SIZE - 2), (y + height + 1), Printer.Align.RIGHT);
    }

    /** Обрабатывает левый клик. */
    public void onLeftClick() {
        state = (state == State.ON) ? State.OFF : State.ON;
    }

    /** Сообщает, включён ли переключатель. */
    public boolean isEnabled() {
        return state == State.ON;
    }
}
