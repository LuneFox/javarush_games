package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.graphics.VisualElement;
import com.javarush.games.minesweeper.graphics.Image;

/**
 * Items you can buy in the shop and use in the game.
 */

public class ShopItem {
    private final MinesweeperGame game;
    public int cost;
    public boolean canExpire;
    public int expireMove;
    public int inStock;
    private boolean isActivated;
    public ID id;
    public String name;
    public String description;
    public Image icon;
    public int number;
    public int[] shopFramePosition; // x1, x2, y1, y2
    public VerticalStatusBar statusBar;

    public enum ID {
        SHIELD, SCANNER, FLAG, SHOVEL, DICE, BOMB
    }

    ShopItem(int slot, int cost, int inStock, Image icon) {
        this.game = MinesweeperGame.getInstance();
        this.icon = icon;
        this.cost = cost;
        this.number = slot;
        this.inStock = inStock;
        isActivated = false;
        switch (slot) {
            case 0:
                this.id = ID.SHIELD;
                this.name = Strings.ITEM_SHIELD_NAME;
                this.description = Strings.generateNewShieldDescription().toString();
                this.canExpire = false;
                this.shopFramePosition = new int[]{15, 34, 32, 51};
                break;
            case 1:
                this.id = ID.SCANNER;
                this.name = Strings.ITEM_SCANNER_NAME;
                this.description = Strings.ITEM_SCANNER_DESCRIPTION;
                this.canExpire = false;
                this.shopFramePosition = new int[]{40, 59, 32, 51};
                break;
            case 2:
                this.id = ID.FLAG;
                this.name = Strings.ITEM_FLAG_NAME;
                this.description = Strings.ITEM_FLAG_DESCRIPTION;
                this.canExpire = false;
                this.shopFramePosition = new int[]{65, 84, 32, 51};
                break;
            case 3:
                this.id = ID.SHOVEL;
                this.name = Strings.ITEM_SHOVEL_NAME;
                this.description = Strings.ITEM_SHOVEL_DESCRIPTION;
                this.canExpire = true;
                this.shopFramePosition = new int[]{15, 34, 57, 76};
                this.statusBar = new VerticalStatusBar(0, 99, Color.DARKORANGE, this);
                break;
            case 4:
                this.id = ID.DICE;
                this.name = Strings.ITEM_DICE_NAME;
                this.description = Strings.ITEM_DICE_DESCRIPTION;
                this.canExpire = true;
                this.shopFramePosition = new int[]{40, 59, 57, 76};
                this.statusBar = new VerticalStatusBar(0, 89, Color.GREEN, this);
                break;
            case 5:
                this.id = ID.BOMB;
                this.name = Strings.ITEM_BOMB_NAME;
                this.description = Strings.ITEM_BOMB_DESCRIPTION;
                this.canExpire = false;
                this.shopFramePosition = new int[]{65, 84, 57, 76};
                break;
            default:
                break;
        }
    }

    public boolean use(Cell cell) {
        switch (this.id) {
            case SHIELD:
                if (this.isActivated) {
                    this.deactivate();
                    cell.attachSprite(VisualElement.SPR_BOARD_MINE);
                    cell.drawBackground(Color.YELLOW);
                    cell.drawSprite();
                    cell.isShielded = true;
                    game.shop.restock(game.shop.shield, 1);
                    game.player.score.setLostScore(game.player.score.getLostScore() - 150 * (game.difficulty / 5));
                    game.player.incBrokenShields();
                    return true;
                }
            case SCANNER:
                if (this.isActivated) {
                    this.deactivate();
                    game.shop.restock(game.shop.scanner, 1);
                    game.shop.restock(game.shop.miniBomb, 1);
                    game.scanNeighbors(cell.x, cell.y);
                    game.redrawAllCells();
                    return true;
                }
            case BOMB:
                if (isActivated()) {
                    this.deactivate();
                    game.shop.dice.hide();
                    game.shop.restock(game.shop.miniBomb, 1);
                    game.shop.restock(game.shop.scanner, 1);
                    game.destroyCell(cell.x, cell.y);
                    game.redrawAllCells();
                    game.checkVictory();
                    return true;
                }
            default:
                return false;
        }
    }

    public void expireCheck() {
        if (game.player.getMoves() >= this.expireMove && this.isActivated) {
            this.deactivate();
            this.inStock = 1;
        }
    }

    public int remainingMoves() {
        return expireMove - game.player.getMoves();
    }

    public void activate() {
        this.isActivated = true;
    }

    public void deactivate() {
        this.isActivated = false;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isUnaffordable() {
        return (game.inventory.money < this.cost);
    }

    public boolean isUnobtainable() {
        return (game.inventory.money < this.cost || this.inStock <= 0);
    }

    public class VerticalStatusBar {
        int posX;
        int posY;
        Color color;
        ShopItem item;

        VerticalStatusBar(int x, int y, Color color, ShopItem item) {
            this.posX = x;
            this.posY = y;
            this.color = color;
            this.item = item;
        }

        public void draw() {
            for (int i = 0; i < item.remainingMoves() * 2; i += 2) {
                game.display.setCellColor(posX, posY - i, color);
            }
        }
    }
}
