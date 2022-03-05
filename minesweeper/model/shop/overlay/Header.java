package com.javarush.games.minesweeper.model.shop.overlay;

import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.ShopItem;
import com.javarush.games.minesweeper.view.graphics.Cache;
import com.javarush.games.minesweeper.view.graphics.Image;
import com.javarush.games.minesweeper.view.graphics.Printer;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

public class Header extends DrawableObject {
    private Image panel;
    private int displayMoney;
    public Shaker moneyShaker = new Shaker();

    public Header() {
        panel = Cache.get(VisualElement.WIN_SHOP_HEADER_FOOTER);
        this.x = 10;
        this.y = 10;
        this.height = panel.height;
        this.width = panel.width;
    }

    @Override
    public void draw() {
        panel = Cache.get(VisualElement.WIN_SHOP_HEADER_FOOTER);
        // Draw header background
        panel.draw(x, y);
        // Draw number of mines on the field
        Cache.get(VisualElement.SPR_BOARD_MINE).draw(x, y);
        Printer.print("" + game.field.countAllCells(Cell.Filter.DANGEROUS), x + 12, y + 2);
        // Draw number of flags in inventory
        Cache.get(VisualElement.SPR_BOARD_FLAG).draw(x + 29, y + 1);
        Printer.print("" + game.player.inventory.getCount(ShopItem.ID.FLAG), x + 39, y + 2);
        // Draw amount of money on hand
        Cache.get(VisualElement.SHOP_COIN).draw(x + 59, y + 3);
        Printer.print("" + displayMoney, x + 65 + moneyShaker.getShift(), y + 2);
        moneyApproach();
    }

    // Make money on display slowly approach real money
    private void moneyApproach() {
        if (displayMoney < game.player.inventory.money) {
            displayMoney++;
        } else if (displayMoney > game.player.inventory.money) {
            displayMoney--;
        }
    }

    public void setDisplayMoney(int displayMoney) {
        this.displayMoney = displayMoney;
    }
}
