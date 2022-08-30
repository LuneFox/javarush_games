package com.javarush.games.minesweeper.model.board.field;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.HashMap;
import java.util.Map;

public class Cell extends InteractiveObject {
    private static final Map<Integer, ImageType> sprites = new HashMap<>();
    private boolean isMined;
    private boolean wasMinedBeforeDestruction;
    private boolean isOpen;
    private boolean isScanned;
    private boolean isShielded;
    private boolean isGameOverCause;
    private boolean isFlagged;
    private boolean isDestroyed;
    private boolean isShop;

    private int countMinedNeighbors;
    private final Image background;
    private Image sprite;

    static {
        createSprites();
    }

    private static void createSprites() {
        for (int i = 0; i < 10; i++) {
            sprites.put(i, ImageType.valueOf("BOARD_" + i));
        }
    }

    public Cell(int x, int y) {
        this.background = new Image(ImageType.CELL_CLOSED, x * 10, y * 10);
        this.x = x;
        this.y = y;
        setSprite(ImageType.NONE);
    }

    @Override
    public void draw() {
        background.draw();

        if (isFlagged || isOpen) {
            sprite.draw();
        }
    }

    public void open() {
        isOpen = true;
        setGraphicsForOpenedState();
    }

    public void setGraphicsForOpenedState() {
        if (!isOpen) return;

        selectBackgroundForOpenedState();
        selectSpriteForOpenedState();
    }

    private void selectBackgroundForOpenedState() {
        background.setMatrix(background.getMatrixFromStorage(ImageType.CELL_OPENED));

        if (isGameOverCause) setBackgroundColor(Color.RED);
        else if (isShielded) setBackgroundColor(Color.YELLOW);
        else if (isScanned) setBackgroundColor(Theme.CELL_SCANNED.getColor());
        else if (isDestroyed) setBackgroundColor(Color.DARKSLATEGRAY);
        else if (isFlagged) setBackgroundColor(Color.GREEN); // flagged cells get opened only when mines are revealed
        else setBackgroundColor(Theme.CELL_BG_DOWN.getColor());
    }

    private void selectSpriteForOpenedState() {
        if (isNumerable()) setSprite(countMinedNeighbors);
        else if (isShop) setSprite(ImageType.BOARD_SHOP);
        else if (isDestroyed) setSprite(ImageType.BOARD_DESTROYED);
        else if (isMined) setSprite(ImageType.BOARD_MINE);
    }

    public void destroy() {
        isDestroyed = true;
        if (isMined) {
            isMined = false;
            wasMinedBeforeDestruction = true;
        }
        open();
    }

    private void setBackgroundColor(Color color) {
        background.changeColor(color, 1);
    }

    private void setSprite(ImageType imageType) {
        this.sprite = new Image(imageType, x * 10, y * 10);
    }

    private void setSprite(int number) {
        if (number < 0 || number > 9) throw new IllegalArgumentException("Sprites numbers must be from 0 to 9");
        this.sprite = new Image(sprites.get(number), x * 10, y * 10);
    }

    public int produceMoney() {
        if (countMinedNeighbors == 0) return 0;

        if (game.shovelIsActivated()) {
            sprite.changeColor(Color.YELLOW, 1);
            return countMinedNeighbors * 2;
        } else {
            return countMinedNeighbors;
        }
    }

    /*
     * States
     */

    public boolean isMined() {
        return isMined;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isClosed() {
        return !isOpen;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isNotFlagged() {
        return !isFlagged;
    }

    public boolean isDangerousToOpen() {
        return !isOpen && isMined;
    }

    public boolean isSafeToOpen() {
        return !isOpen && !isMined;
    }

    public boolean isScored() {
        return isOpen && !isMined && !isDestroyed;
    }

    public boolean isShielded() {
        return isShielded;
    }

    public boolean isShop() {
        return isShop;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean isEmpty() {
        return (!isMined && countMinedNeighbors == 0);
    }

    public boolean isNumerable() {
        return !(isMined || isDestroyed || isFlagged || isShop);
    }

    public boolean cannotBeOpened() {
        return game.isStopped() || isFlagged || isOpen;
    }

    public boolean isIndestructible() {
        boolean isActivated = isOpen || isDestroyed;
        boolean isUnableToDestroyFlag = isFlagged && !game.isFlagExplosionAllowed();
        return game.isStopped() || isActivated || isUnableToDestroyFlag;
    }

    public boolean wasMinedBeforeDestruction() {
        return wasMinedBeforeDestruction;
    }

    /*
     * Getters, setters
     */

    public int getCountMinedNeighbors() {
        return countMinedNeighbors;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }

    public void setCountMinedNeighbors(int countMinedNeighbors) {
        this.countMinedNeighbors = countMinedNeighbors;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public void setShielded(boolean shielded) {
        isShielded = shielded;
    }

    public void setGameOverCause(boolean gameOverCause) {
        isGameOverCause = gameOverCause;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
        setSprite(isFlagged ? ImageType.BOARD_FLAG : ImageType.NONE);
    }

    public void setShop(boolean shop) {
        isShop = shop;
    }
}
