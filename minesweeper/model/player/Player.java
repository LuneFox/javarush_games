package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;

/** Игрок: очки, инвентарь и количество ходов. */
public class Player {
    /** Ссылка на игру. */
    private final MinesweeperGame game;
    /** Счёт игрока. */
    private final Score score;
    /** Инвентарь игрока. */
    private final Inventory inventory;
    /** Титул игрока. */
    private String title;
    /** Количество ходов. */
    private int moves;
    /** Число использованных щитов. */
    private int brokenShields;

    /** Создаёт экземпляр Player. */
    public Player(MinesweeperGame game) {
        this.game = game;
        this.score = new Score(this);
        this.inventory = new Inventory();
        this.title = "";
    }

    /** Сбрасывает состояние в начальное. */
    public void reset() {
        moves = 0;
        brokenShields = 0;
        score.reset();
        inventory.reset();
    }

    /** Регистрирует ход игрока. */
    public void registerMove() {
        this.moves++;
    }

    /** Увеличивает счётчик использованных щитов. */
    public void increaseBrokenShieldsCounter() {
        this.brokenShields++;
    }

    /** Задаёт титул игрока. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Добавляет предмет в инвентарь. */
    public void gainItem(ShopItem item) {
        inventory.putItem(item);
    }

    /** Убирает предмет из инвентаря. */
    public void loseItem(ShopItem item) {
        inventory.removeItem(item);
    }

    /** Добавляет золото. */
    public void gainMoney(int amount) {
        inventory.putMoney(amount);
    }

    /** Списывает золото. */
    public void loseMoney(int amount) {
        inventory.removeMoney(amount);
    }

    /** Возвращает баланс золота. */
    public int getMoneyBalance() {
        return inventory.getMoney();
    }

    /** Считает флажки в инвентаре. */
    public int countFlags() {
        return inventory.count(game.getShop().getFlag());
    }

    /** Устанавливает 99 золота. */
    @DeveloperOption
    public void cheatMoney() {
        inventory.cheatMoney();
    }

    // Геттеры

    /** Возвращает титул игрока. */
    public String getTitle() {
        return title;
    }

    /** Возвращает счёт игрока. */
    public Score getScore() {
        return score;
    }

    /** Возвращает количество ходов. */
    public int getMoves() {
        return moves;
    }

    /** Возвращает число использованных щитов. */
    public int getBrokenShields() {
        return brokenShields;
    }
}
