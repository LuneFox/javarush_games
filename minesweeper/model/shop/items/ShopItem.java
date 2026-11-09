package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;

/** Базовый предмет магазина. */
public abstract class ShopItem {
    /** Ссылка на игру. */
    protected final MinesweeperGame game;
    /** Иконка предмета. */
    protected Image icon;
    /** Название. */
    protected String name;
    /** Описание. */
    protected String description;
    /** Стоимость предмета. */
    protected int cost;
    /** Количество предмета на складе. */
    protected int inStock;
    /** Ход окончания эффекта. */
    protected int expirationMove;
    /** Длительность эффекта в ходах. */
    protected int effectDuration;
    /** Признак активности предмета. */
    protected boolean isActivated;

    /** Создаёт экземпляр ShopItem. */
    public ShopItem(MinesweeperGame game) {
        this.game = game;
    }

    /** Активирует предмет. */
    public abstract void activate();

    /** Запускает эффект предмета. */
    protected void lease() {
        if (game.isStopped() || isActivated) return;
        isActivated = true;
        expirationMove = game.getPlayer().getMoves() + effectDuration;
    }

    /** Деактивирует предмет, если срок истёк. */
    public void deactivateIfExpired() {
        if (isExpired()) {
            deactivate();
            putToStock();
            game.showMessage(name + ": всё");
        }
    }

    /** Деактивирует предмет. */
    public void deactivate() {
        isActivated = false;
    }

    /** Сообщает, истёк ли срок. */
    private boolean isExpired() {
        return (game.getPlayer().getMoves() >= expirationMove) && isActivated;
    }

    /** Возвращает текст оставшихся ходов. */
    public String getRemainingMovesText() {
        return expirationMove == 0 ? " " : "" + countRemainingMoves();
    }

    /** Считает оставшиеся ходы действия. */
    public int countRemainingMoves() {
        return expirationMove - game.getPlayer().getMoves();
    }

    /** Сообщает, недоступен ли предмет. */
    public boolean isUnobtainable() {
        return (isUnaffordable() || inStock <= 0);
    }

    /** Сообщает, хватает ли золота на предмет. */
    public boolean isUnaffordable() {
        return (game.getPlayer().getMoneyBalance() < cost);
    }

    /** Возвращает предмет на склад. */
    public void putToStock() {
        inStock++;
    }

    /** Списывает предмет со склада. */
    public void removeFromStock() {
        inStock--;
    }

    /** Очищает склад предмета. */
    public void emptyStock() {
        inStock = 0;
    }

    /** Возвращает количество предмета на складе. */
    public int getInStock() {
        return inStock;
    }

    /** Рисует иконку. */
    public void drawIcon(int x, int y) {
        icon.draw(x, y);
    }

    /** Активирует предмет на 99 ходов. */
    @DeveloperOption
    public void cheat99() {
        activate();
        expirationMove = game.getPlayer().getMoves() + 99;
    }

    // Геттеры, setters, etc.

    /** Возвращает название. */
    public String getName() {
        return name;
    }

    /** Возвращает описание. */
    public String getDescription() {
        return description;
    }

    /** Сообщает, активирован ли предмет. */
    public boolean isActivated() {
        return isActivated;
    }

    /** Возвращает стоимость предмета. */
    public int getCost() {
        return cost;
    }
}
