package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.items.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sells various items.
 */

public class Shop {
    private final MinesweeperGame game;
    private final List<ShopSlot> showCaseSlots = new ArrayList<>();
    private final List<ShopItem> allItems = new ArrayList<>();
    private Shield shield;
    private Scanner scanner;
    private Flag flag;
    private Shovel shovel;
    private Dice dice;
    private Bomb bomb;
    private ShopItem helpDisplayItem;

    public Shop(MinesweeperGame game) {
        this.game = game;
        createSlots();
    }

    private void createSlots() {
        for (int column = 0; column < 2; column++) {
            for (int row = 0; row < 3; row++) {
                int dx = 5 + 25 * row;
                int dy = 21 + 25 * column;
                ShopSlot slot = new ShopSlot(10 + dx, 10 + dy);
                showCaseSlots.add(slot);
                Phase.getView(Phase.SHOP).linkObject(slot);
            }
        }
    }

    public void reset() {
        createNewItems();
        fillSlots();
        giveStarterFlags();
    }

    private void createNewItems() {
        allItems.clear();
        shield = new Shield(game);
        scanner = new Scanner(game);
        flag = new Flag(game);
        shovel = new Shovel(game);
        dice = new Dice(game);
        bomb = new Bomb(game);

        allItems.addAll(Arrays.asList(shield, scanner, flag, shovel, dice, bomb));
    }

    private void fillSlots() {
        for (int i = 0; i < showCaseSlots.size(); i++) {
            showCaseSlots.get(i).setItem(allItems.get(i));
        }
    }

    private void giveStarterFlags() {
        for (int i = 0; i < 3; i++) {
            give(flag);
        }
    }

    public void sellFlag() {
        sell(flag);
    }

    public void give(ShopItem item) {
        if (item.getInStock() > 0) {
            item.removeFromStock();
            game.getPlayer().gainItem(item);
        }
    }

    public void sell(ShopItem item) {
        if (item == null) return;
        processItemTransaction(item);
        activateItem(item);
    }

    private void processItemTransaction(ShopItem item) {
        item.removeFromStock();

        final Player player = game.getPlayer();
        player.loseMoney(item.getCost());
        player.gainItem(item);
    }

    private void activateItem(ShopItem item) {
        item.activate();
    }

    public void offerFlag() {
        if (!Options.autoBuyFlagsSelector.isEnabled() && flag.getInStock() > 0) {
            PopUpMessage.show("Купите флажок!");
            Phase.setActive(Phase.SHOP);
            return;
        }

        if (game.countPlayerFlags() == 0 && flag.getInStock() == 0) {
            PopUpMessage.show("Флажки кончились");
            return;
        }

        if (flag.isUnobtainable()) {
            PopUpMessage.show("Невозможно купить!");
            return;
        }

        PopUpMessage.show("Куплен флажок");
        sellFlag();
    }

    public void checkExpiredItems() {
        shovel.deactivateIfExpired();
        dice.deactivateIfExpired();
    }

    public void drawItemAssets() {
        scanner.drawFrame();
        bomb.drawFrame();
        shovel.drawStatusBar();
        dice.drawStatusBar();
    }

    public void aimWithScanner(Cell cell) {
        scanner.aim(cell);
    }

    public void aimWithBomb(Cell cell) {
        bomb.aim(cell);
    }

    @DeveloperOption
    public void cheatMoreTools() {
        if (!Options.developerModeEnabled) return;
        shovel.cheat99();
        dice.cheat99();
        PopUpMessage.show("DEV: 99 TOOLS");
    }

    public List<ShopSlot> getShowCaseSlots() {
        return showCaseSlots;
    }

    public Shield getShield() {
        return shield;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Flag getFlag() {
        return flag;
    }

    public Shovel getShovel() {
        return shovel;
    }

    public Dice getDice() {
        return dice;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public ShopItem getHelpDisplayItem() {
        return helpDisplayItem;
    }

    public void setHelpDisplayItem(ShopItem helpDisplayItem) {
        this.helpDisplayItem = helpDisplayItem;
    }
}
