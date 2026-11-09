package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Effect;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageCreator;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.view.View;

/** Анимированная кнопка с настраиваемым размером. */
public abstract class Button extends InteractiveObject {
    /** Начальный кадр анимации нажатия. */
    private static final int PRESS_ANIMATION_START_FRAME = 0;
    /** Кадр отпускания кнопки. */
    private static final int PRESS_ANIMATION_RELEASE_FRAME = 5;
    /** Последний кадр анимации нажатия. */
    private static final int PRESS_ANIMATION_FINISH_FRAME = 7;
    /** Высота по умолчанию. */
    private static final int DEFAULT_HEIGHT = 9;
    /** Отступ по умолчанию. */
    private static final int DEFAULT_MARGIN = 3;
    /** Смещение по умолчанию. */
    private static final int DEFAULT_OFFSET = 2;
    /** Текущий кадр анимации нажатия. */
    private static int pressAnimationCurrentFrame = PRESS_ANIMATION_FINISH_FRAME + 1;

    /** Изображение отпущенной кнопки. */
    private final Image unpressedBody;
    /** Изображение нажатой кнопки. */
    private final Image pressedBody;
    /** Цвет текста кнопки. */
    private final Color labelColor = Color.WHITE;
    /** Текст кнопки. */
    private String labelText;
    /** Сдвиг текста кнопки. */
    private int labelOffset;
    /** Нажата ли кнопка. */
    private boolean isPressed;

    /** Создаёт экземпляр Button. */
    public Button(int posX, int posY, String text, View view) {
        super(posX, posY);
        this.labelText = text;
        this.width = Printer.calculateWidth(text) + DEFAULT_MARGIN;
        this.height = DEFAULT_HEIGHT;
        this.labelOffset = DEFAULT_OFFSET;
        this.unpressedBody = createBody(posX, posY, Effect.SHADOW, Effect.STROKE);
        this.pressedBody = createBody(posX, posY, Effect.STROKE);
        this.linkView(view);
    }

    /** Создаёт экземпляр Button. */
    public Button(int posX, int posY, int width, int height, String text, View view) {
        super(posX, posY);
        this.labelText = text;
        this.width = width;
        this.height = height;
        this.labelOffset = ((width - Printer.calculateWidth(text)) / 2) + 1;
        this.unpressedBody = createBody(posX, posY, Effect.SHADOW, Effect.STROKE);
        this.pressedBody = createBody(posX, posY, Effect.STROKE);
        this.linkView(view);
    }

    /** Создаёт изображение кнопки. */
    private Image createBody(int posX, int posY, Effect... effects) {

        Image body = new Image(ImageType.GUI_BUTTON, posX, posY) {
            /** Возвращает матрицу из хранилища. */
            @Override
            public int[][] getMatrixFromStorage(ImageType imageType) {
                return ImageCreator.createFrame(Button.this.width, Button.this.height, effects);
            }
        };

        body.setColors(new Color[]{Color.NONE, Theme.BUTTON_BG.getColor(), Color.BLACK, Theme.BUTTON_BORDER.getColor()});
        return body;
    }

    /** Рисует объект на экране. */
    public void draw() {
        if (pressAnimationCurrentFrame >= PRESS_ANIMATION_RELEASE_FRAME) {
            release();
        }

        int drawX = (isPressed) ? (x + 1) : x;
        int drawY = (isPressed) ? (y + 1) : y;

        drawStateDependentBody(drawX, drawY);
        printLabel(drawX, drawY);
    }

    /** Рисует тело кнопки по состоянию. */
    private void drawStateDependentBody(int drawX, int drawY) {
        if (isPressed) {
            this.pressedBody.draw(drawX, drawY);
        } else {
            this.unpressedBody.draw(drawX, drawY);
        }
    }

    /** Печатает подпись. */
    private void printLabel(int drawX, int drawY) {
        Printer.print(labelText, labelColor, drawX + labelOffset, drawY);
    }

    /** Обрабатывает левый клик. */
    public void onLeftClick() {
        press();
    }

    /** Заменяет текст кнопки. */
    public void replaceText(int width, String label) {
        int textLength = Printer.calculateWidth(label);
        this.labelText = label;
        this.labelOffset = (width == 0) ? DEFAULT_OFFSET : ((width - textLength) / 2) + 1;
    }

    /** Нажимает кнопку. */
    private void press() {
        isPressed = true;
        pressAnimationCurrentFrame = PRESS_ANIMATION_START_FRAME;
    }

    /** Отпускает кнопку. */
    private void release() {
        isPressed = false;
    }

    /** Сообщает, закончилась ли анимация кнопки. */
    public static boolean isAnimationFinished() {
        return pressAnimationCurrentFrame > PRESS_ANIMATION_FINISH_FRAME;
    }

    /** Продолжает анимацию кнопки. */
    public static void continueAnimation() {
        if (pressAnimationCurrentFrame <= PRESS_ANIMATION_FINISH_FRAME) {
            pressAnimationCurrentFrame++;
        }
    }

    /** Возвращает текст кнопки. */
    public String getLabelText() {
        return labelText;
    }
}
