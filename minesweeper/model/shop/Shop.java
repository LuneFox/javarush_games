package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.items.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Продаёт предметы и управляет складом. */
public class Shop {
    /** Ссылка на игру. */
    private final MinesweeperGame game;
    /** Ячейки витрины магазина. */
    private final List<ShopSlot> showCaseSlots = new ArrayList<>();
    /** Все предметы магазина. */
    private final List<ShopItem> allItems = new ArrayList<>();
    /** Предмет «Щит». */
    private Shield shield;
    /** Предмет «Сканер». */
    private Scanner scanner;
    /** Предмет «Флажок». */
    private Flag flag;
    /** Предмет «Лопата». */
    private Shovel shovel;
    /** Предмет «Кубик». */
    private Dice dice;
    /** Предмет «Бомба». */
    private Bomb bomb;
    /** Предмет для экрана справки. */
    private ShopItem helpDisplayItem;

    /** Создаёт экземпляр Shop. */
    public Shop(MinesweeperGame game) {
        this.game = game;
        createSlots();
    }

    /** Создаёт ячейки витрины магазина. */
    private void createSlots() {
        for (int column = 0; column < 2; column++) {
            for (int row = 0; row < 3; row++) {
                int dx = 5 + 25 * row;
                int dy = 21 + 25 * column;
                showCaseSlots.add(new ShopSlot(10 + dx, 10 + dy));
            }
        }
    }

    /** Сбрасывает состояние в начальное. */
    public void reset() {
        helpDisplayItem = null;
        createNewItems();
        fillSlots();
        giveStarterFlags();
    }

    /** Создаёт новые предметы магазина. */
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

    /** Заполняет витрину предметами. */
    private void fillSlots() {
        for (int i = 0; i < showCaseSlots.size(); i++) {
            showCaseSlots.get(i).setItem(allItems.get(i));
        }
    }

    /** Выдаёт стартовые флажки. */
    private void giveStarterFlags() {
        for (int i = 0; i < 3; i++) {
            give(flag);
        }
    }

    /** Продаёт флажок. */
    public void sellFlag() {
        sell(flag);
    }

    /** Выдаёт предмет из складского запаса. */
    public void give(ShopItem item) {
        if (item.getInStock() > 0) {
            item.removeFromStock();
            game.getPlayer().gainItem(item);
        }
    }

    /** Продаёт предмет игроку. */
    public void sell(ShopItem item) {
        if (game.isStopped() || item == null) return;
        if (item.isActivated() || item.getInStock() <= 0) return;
        if (item.isUnaffordable()) return;

        processItemTransaction(item);
        activateItem(item);
    }

    /** Списывает стоимость и выдаёт предмет. */
    private void processItemTransaction(ShopItem item) {
        item.removeFromStock();

        final Player player = game.getPlayer();
        player.loseMoney(item.getCost());
        player.gainItem(item);
    }

    /** Активирует купленный предмет. */
    private void activateItem(ShopItem item) {
        item.activate();
    }

    /** Предлагает игроку купить флажок. */
    public void offerFlag() {
        if (!Options.getAutoBuyFlagsSelector().isEnabled() && flag.getInStock() > 0) {
            game.showMessage(Text.SHOP_BUY_FLAG.getText());
            game.openShop();
            return;
        }

        if (game.countPlayerFlags() == 0 && flag.getInStock() == 0) {
            game.showMessage(Text.SHOP_NO_FLAGS.getText());
            return;
        }

        if (flag.isUnobtainable()) {
            game.showMessage(Text.SHOP_CANNOT_BUY.getText());
            return;
        }

        game.showMessage(Text.SHOP_FLAG_BOUGHT.getText());
        sellFlag();
    }

    /** Отключает предметы с истёкшим сроком. */
    public void checkExpiredItems() {
        shovel.deactivateIfExpired();
        dice.deactivateIfExpired();
    }

    /** Рисует рамки и полосы предметов. */
    public void drawItemAssets() {
        scanner.drawFrame();
        bomb.drawFrame();
        shovel.drawStatusBar();
        dice.drawStatusBar();
    }

    /** Наводит сканер на клетку. */
    public void aimWithScanner(Cell cell) {
        scanner.aim(cell);
    }

    /** Наводит бомбу на клетку. */
    public void aimWithBomb(Cell cell) {
        bomb.aim(cell);
    }

    /** Выдаёт инструменты разработчика. */
    @DeveloperOption
    public void cheatMoreTools() {
        if (!Options.isDeveloperModeEnabled()) return;
        shovel.cheat99();
        dice.cheat99();
        game.showMessage(Text.DEV_TOOLS_99.getText());
    }

    /** Возвращает ячейки витрины магазина. */
    public List<ShopSlot> getShowCaseSlots() {
        return showCaseSlots;
    }

    /** Возвращает предмет «Щит». */
    public Shield getShield() {
        return shield;
    }

    /** Возвращает предмет «Сканер». */
    public Scanner getScanner() {
        return scanner;
    }

    /** Возвращает предмет «Флажок». */
    public Flag getFlag() {
        return flag;
    }

    /** Возвращает предмет «Лопата». */
    public Shovel getShovel() {
        return shovel;
    }

    /** Возвращает предмет «Кубик». */
    public Dice getDice() {
        return dice;
    }

    /** Возвращает предмет «Бомба». */
    public Bomb getBomb() {
        return bomb;
    }

    /** Возвращает предмет для экрана справки. */
    public ShopItem getHelpDisplayItem() {
        return helpDisplayItem;
    }

    /** Задаёт предмет для экрана справки. */
    public void setHelpDisplayItem(ShopItem helpDisplayItem) {
        this.helpDisplayItem = helpDisplayItem;
    }
}
