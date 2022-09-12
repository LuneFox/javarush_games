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
    private final Image background;
    private Image sprite;
    private int countMinedNeighbors;
    private int states;

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
        states = 0;
        setSprite(ImageType.NONE);
    }

    @Override
    public void draw() {
        background.draw();

        if (isFlagged() || isOpen()) {
            sprite.draw();
        }
    }

    public void reveal() {
        setOpen(true);
        setRevealedContent();
    }

    public void setRevealedContent() {
        if (!isOpen()) return;

        setRevealedBackground();
        setRevealedSprite();
    }

    private void setRevealedBackground() {
        background.setMatrix(background.getMatrixFromStorage(ImageType.CELL_OPENED));

        if (isGameOverCause()) setBackgroundColor(Color.RED);
        else if (isShielded()) setBackgroundColor(Color.YELLOW);
        else if (isScanned()) setBackgroundColor(Theme.CELL_SCANNED.getColor());
        else if (isDestroyed()) setBackgroundColor(Color.DARKSLATEGRAY);
        else if (isFlagged()) setBackgroundColor(Color.GREEN); // highlights correct position at game over
        else setBackgroundColor(Theme.CELL_BG_DOWN.getColor());
    }

    private void setRevealedSprite() {
        if (isNumerable()) setSprite(countMinedNeighbors);
        else if (isShop()) setSprite(ImageType.BOARD_SHOP);
        else if (isDestroyed()) setSprite(ImageType.BOARD_DESTROYED);
        else if (isMined()) setSprite(ImageType.BOARD_MINE);
    }

    public void destroy() {
        setDestroyed(true);

        if (isMined()) {
            setMined(false);
            setWasMinedBeforeDestruction(true);
        }

        reveal();
    }

    private void setBackgroundColor(Color color) {
        background.changeColor(color, 1);
    }

    private void setSprite(ImageType imageType) {
        this.sprite = new Image(imageType, x * 10, y * 10);
    }

    private void setSprite(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Sprites numbers must be from 0 to 9");
        }

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
     * Flagged states
     */

    public boolean isMined() {
        return hasState(1);
    }

    public void setMined(boolean set) {
        setState(set, 1);
    }

    public boolean wasMinedBeforeDestruction() {
        return hasState(2);
    }

    public void setWasMinedBeforeDestruction(boolean set) {
        setState(set, 2);
    }

    public boolean isOpen() {
        return hasState(4);
    }

    public void setOpen(boolean set) {
        setState(set, 4);
    }

    public boolean isClosed() {
        return !hasState(4);
    }

    public boolean isFlagged() {
        return hasState(8);
    }

    public void setFlagged(boolean set) {
        setState(set, 8);
        setSprite(isFlagged() ? ImageType.BOARD_FLAG : ImageType.NONE);
    }

    public boolean isNotFlagged() {
        return !hasState(8);
    }

    public boolean isShielded() {
        return hasState(16);
    }

    public void setShielded(boolean set) {
        setState(set, 16);
    }

    public boolean isDestroyed() {
        return hasState(32);
    }

    public void setDestroyed(boolean set) {
        setState(set, 32);
    }

    public boolean isShop() {
        return hasState(64);
    }

    public void setShop(boolean set) {
        setState(set, 64);
    }

    public boolean isScanned() {
        return hasState(128);
    }

    public void setScanned(boolean set) {
        setState(set, 128);
    }

    public boolean isGameOverCause() {
        return hasState(256);
    }

    public void setGameOverCause(boolean set) {
        setState(set, 256);
    }

    private void setState(boolean set, int pos) {
        if (set) {
            states |= pos;
        } else {
            states &= ~pos;
        }
    }

    private boolean hasState(int pos) {
        return (states & pos) != 0;
    }

    /*
     * Combined states
     */

    public boolean isDangerousToOpen() {
        return !isOpen() && isMined();
    }

    public boolean isSafeToOpen() {
        return !(isOpen() || isMined());
    }

    public boolean isScored() {
        return isOpen() && !isMined() && !isDestroyed();
    }

    public boolean isNumerable() {
        return !(isMined() || isDestroyed() || isFlagged() || isShop());
    }

    public boolean cannotBeOpened() {
        return game.isStopped() || isFlagged() || isOpen();
    }

    public boolean isEmpty() {
        return (!isMined() && countMinedNeighbors == 0);
    }

    public boolean isIndestructible() {
        boolean isActivated = isOpen() || isDestroyed();
        boolean isUnableToDestroyFlag = isFlagged() && !game.isFlagExplosionAllowed();
        return game.isStopped() || isActivated || isUnableToDestroyFlag;
    }

    /*
     * Normal getters and setters
     */

    public int getCountMinedNeighbors() {
        return countMinedNeighbors;
    }

    public void setCountMinedNeighbors(int count) {
        this.countMinedNeighbors = count;
    }
}
