package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Image;
import com.javarush.games.minesweeper.graphics.Picture;

/**
 * Items you can buy in the shop and use in the game.
 */

class ShopItem {
    private final MinesweeperGame game;
    public int cost;
    public boolean canExpire;
    public int expireMove;
    public int inStock;
    private boolean isActivated;
    public ID id;
    public String name;
    public String description;
    public Picture icon;

    public enum ID {
        SHIELD, SCANNER, FLAG, SHOVEL, DICE, BOMB
    }

    ShopItem(int slot, int cost, int inStock, Picture icon, MinesweeperGame game) {
        this.game = game;
        this.icon = icon;
        this.cost = cost;
        this.inStock = inStock;
        isActivated = false;
        switch (slot) {
            case 0:
                this.id = ID.SHIELD;
                this.name = Strings.ITEM_SHIELD_NAME;
                this.description = Strings.generateNewShieldDescription().toString();
                this.canExpire = false;
                break;
            case 1:
                this.id = ID.SCANNER;
                this.name = Strings.ITEM_SCANNER_NAME;
                this.description = Strings.ITEM_SCANNER_DESCRIPTION;
                this.canExpire = false;
                break;
            case 2:
                this.id = ID.FLAG;
                this.name = Strings.ITEM_FLAG_NAME;
                this.description = Strings.ITEM_FLAG_DESCRIPTION;
                this.canExpire = false;
                break;
            case 3:
                this.id = ID.SHOVEL;
                this.name = Strings.ITEM_SHOVEL_NAME;
                this.description = Strings.ITEM_SHOVEL_DESCRIPTION;
                this.canExpire = true;
                break;
            case 4:
                this.id = ID.DICE;
                this.name = Strings.ITEM_DICE_NAME;
                this.description = Strings.ITEM_DICE_DESCRIPTION;
                this.canExpire = false;
                break;
            case 5:
                this.id = ID.BOMB;
                this.name = Strings.ITEM_BOMB_NAME;
                this.description = Strings.ITEM_BOMB_DESCRIPTION;
                this.canExpire = false;
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
                    cell.assignSprite(Bitmap.BOARD_MINE);
                    cell.replaceColor(Color.YELLOW, 3);
                    cell.draw(Image.Mirror.NO);
                    cell.drawSprite();
                    cell.isShielded = true;
                    game.shop.restock(game.shop.shield, 1);
                    int scoreBefore = game.player.score;
                    game.player.score = Math.max(game.player.score - 150 * (game.difficulty / 5), 0);
                    game.player.scoreLost -= scoreBefore - game.player.score;
                    game.setScore(game.player.score);
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
                    game.shop.restock(game.shop.miniBomb, 1);
                    game.shop.restock(game.shop.scanner, 1);
                    game.destroyCell(cell.x, cell.y);
                    game.redrawAllCells();
                    game.checkVictory();
                    return true;
                }
        }
        return false;
    }

    public void expireCheck() {
        if (game.player.countMoves >= this.expireMove) {
            this.deactivate();
            this.inStock = 1;
        }
    }

    public String remainingMoves() {
        return Integer.toString(expireMove - game.player.countMoves);
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

    public boolean isUnaffordable(){
        return (game.inventory.money < this.cost);
    }

    public boolean isUnobtainable() {
        return (game.inventory.money < this.cost || this.inStock <= 0);
    }
}
