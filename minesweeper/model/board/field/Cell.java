package com.javarush.games.minesweeper.model.board.field;

import com.javarush.games.minesweeper.Text;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.HashMap;
import java.util.Map;

/** Клетка поля с состоянием, числом мин и спрайтом. */
public class Cell extends InteractiveObject {
    /**
     * Размер клетки в пикселях.
     */
    public static final int PIXEL_SIZE = 10;
    /** Максимальная цифра на клетке. */
    private static final int MAX_NUMBER = 9;
    /** Спрайты цифр. */
    private static final Map<Integer, ImageType> sprites = new HashMap<>();
    /** Фоновое изображение. */
    private final Image background;
    /** Спрайт клетки. */
    private Image sprite;
    /** Число мин вокруг клетки. */
    private int countMinedNeighbors;
    /** Битовая маска состояний клетки. */
    private int states;

    static {
        createSprites();
    }

    /** Создаёт таблицу спрайтов цифр. */
    private static void createSprites() {
        for (int i = 0; i <= MAX_NUMBER; i++) {
            sprites.put(i, ImageType.valueOf("BOARD_" + i));
        }
    }

    /** Создаёт экземпляр Cell. */
    public Cell(int x, int y) {
        this.background = new Image(ImageType.CELL_CLOSED, x * PIXEL_SIZE, y * PIXEL_SIZE, false);
        this.x = x;
        this.y = y;
        states = 0;
        setSprite(ImageType.NONE);
    }

    /** Рисует объект на экране. */
    @Override
    public void draw() {
        background.draw();

        if (isFlagged() || isOpen()) {
            sprite.draw();
        }
    }

    /** Открывает клетку. */
    public void reveal() {
        setOpen(true);
        setRevealedContent();
    }

    /** Обновляет открытую клетку. */
    public void setRevealedContent() {
        if (!isOpen()) return;

        setRevealedBackground();
        setRevealedSprite();
    }

    /**
     * Перерисовывает закрытую или открытую клетку в актуальных цветах темы.
     */
    public void refreshTheme() {
        if (isOpen()) {
            setRevealedContent();
            return;
        }

        background.setMatrix(background.getMatrixFromStorage(ImageType.CELL_CLOSED));
        setSprite(isFlagged() ? ImageType.BOARD_FLAG : ImageType.NONE);
    }

    /** Обновляет фон открытой клетки. */
    private void setRevealedBackground() {
        background.setMatrix(background.getMatrixFromStorage(ImageType.CELL_OPENED));

        if (isGameOverCause()) setBackgroundColor(Color.RED);
        else if (isShielded()) setBackgroundColor(Color.YELLOW);
        else if (isScanned()) setBackgroundColor(Theme.CELL_SCANNED.getColor());
        else if (isDestroyed()) setBackgroundColor(Color.DARKSLATEGRAY);
        else if (isFlagged()) setBackgroundColor(Color.GREEN); // подсвечивает верную позицию при завершении игры
        else setBackgroundColor(Theme.CELL_BG_DOWN.getColor());
    }

    /** Обновляет спрайт открытой клетки. */
    private void setRevealedSprite() {
        if (canShowNumber()) setSprite(countMinedNeighbors);
        else if (isShop()) setSprite(ImageType.BOARD_SHOP);
        else if (isDestroyed()) setSprite(ImageType.BOARD_DESTROYED);
        else if (isMined()) setSprite(ImageType.BOARD_MINE);
    }

    /** Уничтожает клетку. */
    public void destroy() {
        setDestroyed(true);

        if (isMined()) {
            setMined(false);
            setWasMinedBeforeDestruction(true);
        }

        reveal();
    }

    /** Задаёт цвет фона. */
    private void setBackgroundColor(Color color) {
        background.changeColor(color, 1);
    }

    /** Задаёт спрайт. */
    private void setSprite(ImageType imageType) {
        this.sprite = new Image(imageType, x * PIXEL_SIZE, y * PIXEL_SIZE, false);
    }

    /** Задаёт спрайт. */
    private void setSprite(int number) {
        if (number < 0 || number > MAX_NUMBER) {
            throw new IllegalArgumentException(Text.ERROR_SPRITE_RANGE_PREFIX.getText() + MAX_NUMBER);
        }

        setSprite(sprites.get(number));
    }

    /**
     * Возвращает золото, которое приносит открытие этой клетки.
     */
    public int calculateMoney() {
        if (countMinedNeighbors == 0) return 0;

        if (game.isShovelActivated()) {
            sprite.changeColor(Color.YELLOW, 1);
            return countMinedNeighbors * 2;
        } else {
            return countMinedNeighbors;
        }
    }

    /*
     * Состояния флажка
     */

    /** Сообщает, заминирована ли клетка. */
    public boolean isMined() {
        return hasState(1);
    }

    /** Задаёт признак минирования. */
    public void setMined(boolean set) {
        setState(set, 1);
    }

    /** Сообщает, была ли клетка заминирована до уничтожения. */
    public boolean wasMinedBeforeDestruction() {
        return hasState(2);
    }

    /** Задаёт признак мины до уничтожения. */
    public void setWasMinedBeforeDestruction(boolean set) {
        setState(set, 2);
    }

    /** Сообщает, открыта ли клетка. */
    public boolean isOpen() {
        return hasState(4);
    }

    /** Задаёт признак открытия. */
    public void setOpen(boolean set) {
        setState(set, 4);
    }

    /** Сообщает, закрыта ли клетка. */
    public boolean isClosed() {
        return !hasState(4);
    }

    /** Сообщает, стоит ли на клетке флажок. */
    public boolean isFlagged() {
        return hasState(8);
    }

    /** Задаёт флажок. */
    public void setFlagged(boolean set) {
        setState(set, 8);
        setSprite(isFlagged() ? ImageType.BOARD_FLAG : ImageType.NONE);
    }

    /** Сообщает, свободна ли клетка от флажка. */
    public boolean isNotFlagged() {
        return !hasState(8);
    }

    /** Сообщает, защищена ли клетка щитом. */
    public boolean isShielded() {
        return hasState(16);
    }

    /** Задаёт щит. */
    public void setShielded(boolean set) {
        setState(set, 16);
    }

    /** Сообщает, уничтожена ли клетка. */
    public boolean isDestroyed() {
        return hasState(32);
    }

    /** Задаёт признак уничтожения. */
    public void setDestroyed(boolean set) {
        setState(set, 32);
    }

    /** Сообщает, является ли клетка дверью магазина. */
    public boolean isShop() {
        return hasState(64);
    }

    /** Задаёт признак магазина. */
    public void setShop(boolean set) {
        setState(set, 64);
    }

    /** Сообщает, была ли клетка просканирована. */
    public boolean isScanned() {
        return hasState(128);
    }

    /** Задаёт признак сканирования. */
    public void setScanned(boolean set) {
        setState(set, 128);
    }

    /** Сообщает, стала ли клетка причиной поражения. */
    public boolean isGameOverCause() {
        return hasState(256);
    }

    /** Задаёт признак причины поражения. */
    public void setGameOverCause(boolean set) {
        setState(set, 256);
    }

    /** Задаёт бит состояния. */
    private void setState(boolean set, int pos) {
        if (set) {
            states |= pos;
        } else {
            states &= ~pos;
        }
    }

    /** Проверяет бит состояния. */
    private boolean hasState(int pos) {
        return (states & pos) != 0;
    }

    /*
     * Составные состояния
     */

    /** Сообщает, опасно ли открывать клетку. */
    public boolean isDangerousToOpen() {
        return !isOpen() && isMined();
    }

    /** Сообщает, безопасно ли открывать клетку. */
    public boolean isSafeToOpen() {
        return !(isOpen() || isMined());
    }

    /** Сообщает, приносит ли клетка очки. */
    public boolean isScored() {
        return isOpen() && !isMined() && !isDestroyed();
    }

    /**
     * Сообщает, можно ли показывать на клетке число мин вокруг неё.
     */
    public boolean canShowNumber() {
        return !(isMined() || isDestroyed() || isFlagged() || isShop());
    }

    /**
     * Сообщает, нужно ли пересчитывать число мин вокруг клетки, в том числе под флажком.
     */
    public boolean isCountable() {
        return !(isMined() || isDestroyed() || isShop());
    }

    /** Сообщает, нельзя ли открыть клетку. */
    public boolean cannotBeOpened() {
        return game.isStopped() || isFlagged() || isOpen();
    }

    /** Сообщает, пуста ли клетка. */
    public boolean isEmpty() {
        return (!isMined() && countMinedNeighbors == 0);
    }

    /** Сообщает, нельзя ли уничтожить клетку бомбой. */
    public boolean isIndestructible() {
        boolean isActivated = isOpen() || isDestroyed();
        boolean isUnableToDestroyFlag = isFlagged() && !game.isFlagExplosionAllowed();
        return game.isStopped() || isActivated || isUnableToDestroyFlag;
    }

    /*
     * Обычные геттеры и сеттеры
     */

    /** Возвращает число мин вокруг клетки. */
    public int getCountMinedNeighbors() {
        return countMinedNeighbors;
    }

    /** Задаёт число мин вокруг клетки. */
    public void setCountMinedNeighbors(int count) {
        this.countMinedNeighbors = count;
    }
}
