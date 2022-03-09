package com.javarush.games.minesweeper.model.shop;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.gui.image.ImageID;
import com.javarush.games.minesweeper.gui.image.Image;

/**
 * Items you can buy in the shop and use in the game.
 */

public class ShopItem {
    private static final MinesweeperGame game = MinesweeperGame.getInstance();
    public int cost;
    public final boolean canExpire;
    public final int effectDuration;
    public int expireMove;
    public int inStock;
    private boolean isActivated;
    public ID id;
    public String name;
    public String description;
    public Image icon;
    public int number;
    public VerticalStatusBar statusBar;

    public enum ID {
        SHIELD, SCANNER, FLAG, SHOVEL, DICE, BOMB
    }

    ShopItem(int slot, int cost, int inStock, Image icon) {
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
                effectDuration = 0;
                break;
            case 1:
                this.id = ID.SCANNER;
                this.name = Strings.ITEM_SCANNER_NAME;
                this.description = Strings.ITEM_SCANNER_DESCRIPTION;
                this.canExpire = false;
                effectDuration = 0;
                break;
            case 2:
                this.id = ID.FLAG;
                this.name = Strings.ITEM_FLAG_NAME;
                this.description = Strings.ITEM_FLAG_DESCRIPTION;
                this.canExpire = false;
                effectDuration = 0;
                break;
            case 3:
                this.id = ID.SHOVEL;
                this.name = Strings.ITEM_SHOVEL_NAME;
                this.description = Strings.ITEM_SHOVEL_DESCRIPTION;
                this.statusBar = new VerticalStatusBar(0, 99, Color.DARKORANGE, this);
                this.canExpire = true;
                effectDuration = 5;
                break;
            case 4:
                this.id = ID.DICE;
                this.name = Strings.ITEM_DICE_NAME;
                this.description = Strings.ITEM_DICE_DESCRIPTION;
                this.statusBar = new VerticalStatusBar(0, 89, Color.GREEN, this);
                this.canExpire = true;
                effectDuration = 3;
                break;
            case 5:
                this.id = ID.BOMB;
                this.name = Strings.ITEM_BOMB_NAME;
                this.description = Strings.ITEM_BOMB_DESCRIPTION;
                this.canExpire = false;
                effectDuration = 0;
                break;
            default:
                canExpire = false;
                effectDuration = 0;
                break;
        }
    }

    public boolean use(Cell cell) {
        switch (this.id) {
            case SHIELD:
                if (this.isActivated) {
                    this.deactivate();
                    cell.setSprite(ImageID.SPR_BOARD_MINE);
                    cell.isShielded = true;
                    game.shop.restock(game.shop.shield, 1);
                    game.player.score.setLostScore(game.player.score.getLostScore() - 150 * (Options.difficulty / 5));
                    game.player.incBrokenShields();
                    return true;
                }
            case SCANNER:
                if (this.isActivated) {
                    this.deactivate();
                    game.shop.restock(game.shop.scanner, 1);
                    game.shop.restock(game.shop.miniBomb, 1);
                    game.scanNeighbors(cell.x, cell.y);
                    return true;
                }
            case BOMB:
                if (isActivated()) {
                    this.deactivate();
                    game.field.dice.hide();
                    game.shop.restock(game.shop.miniBomb, 1);
                    game.shop.restock(game.shop.scanner, 1);
                    game.destroyCell(cell.x, cell.y);
                    game.checkVictory();
                    return true;
                }
            default:
                return false;
        }
    }

    public void expireCheck() {
        if (game.player.getMoves() >= this.expireMove && this.isActivated) {
            PopUpMessage.show(this.name + ": всё");
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
        return (game.player.inventory.money < this.cost);
    }

    public boolean isUnobtainable() {
        return (game.player.inventory.money < this.cost || this.inStock <= 0);
    }

    public static class VerticalStatusBar {
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
