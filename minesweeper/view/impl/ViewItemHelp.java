package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.Text;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;
import com.javarush.games.minesweeper.view.View;

/** Экран справки о предмете магазина. */
public class ViewItemHelp extends View {

    /** Кнопка закрытия экрана. */
    private final Button closeButton = new Button(88, 2, Text.BUTTON_CLOSE.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.SHOP);
        }
    };
    /** Фоновое изображение. */
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    /** Создаёт экземпляр ViewItemHelp. */
    public ViewItemHelp(MinesweeperGame game) {
        super(game);
    }

    /** Обновляет состояние. */
    @Override
    public void update() {
        ShopItem displayItem = game.getShop().getHelpDisplayItem();
        background.draw();
        displayItem.drawIcon(5, 10);
        Printer.print("<[" + displayItem.getName() + "]>", Color.YELLOW, 25, 14);
        Printer.print(displayItem.getDescription(), 4, 30);
        closeButton.draw();
        super.update();
    }
}
