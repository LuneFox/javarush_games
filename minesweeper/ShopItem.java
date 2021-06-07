package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.graphics.Picture;

class ShopItem {
    int cost;
    int expireMove;
    private int count;
    private boolean isActivated;
    ID id;
    String name;
    String description;
    Picture icon;

    ShopItem(int slot, int cost, int count, Picture icon, MinesweeperGame game) {
        this.cost = cost;
        this.count = count;
        this.icon = icon;
        isActivated = false;
        switch (slot) {
            case 0:
                this.id = ID.SHIELD;
                this.name = "Сапёрский щит";
                this.description = "Спасёт от взрыва\nпри открытии мины\nодин раз. Однако вы\n" +
                        "потеряете " + 150 * (game.difficulty / 5) + " очков.";
                break;
            case 1:
                this.id = ID.SCANNER;
                this.name = "Сканер";
                this.description = "Откроет безопасную\nклетку в поле 3*3\nвокруг курсора.\nЕсли таких нет,\n" +
                        "расставит флажки\nнад минами, подарив\nнехватающие.";
                break;
            case 2:
                this.id = ID.FLAG;
                this.name = "Флажок";
                this.description = "Обычный флажок\nдля установки на\nполе. Вы можете\nсэкономить золото,\n" +
                        "не покупая флажки,\nкогда позиция мины\nочевидна.";
                break;
            case 3:
                this.id = ID.SHOVEL;
                this.name = "Золотая лопата";
                this.description = "Следующие 5 шагов\nвы будете получать\nв два раза больше\nзолотых монет.\n" +
                        "Золото добывается\nпри открытии клеток\nс цифрами.";
                break;
            case 4:
                this.id = ID.DICE;
                this.name = "Кубик удачи";
                this.description = "Следующие 3 шага\nвы можете получить\nот 1 до 6 раз больше\nочков. Базовое\n" +
                        "количество зависит\nот сложности игры.";
                break;
            case 5:
                this.id = ID.BOMB;
                this.name = "Мини-бомба";
                this.description = "Бросив бомбочку, вы\nуничтожите закрытую\nклетку на поле.\nЕсли взорвёте мину,\n" +
                        "соседние мины тоже\nвзорвутся по цепи.\nОчков не даёт.";
                break;
            default:
                break;
        }
    }

    public void activate() {
        this.isActivated = true;
    }

    public void deactivate() {
        this.isActivated = false;
    }

    public void removeFromShop() {
        this.count = 0;
    }

    public void restock(int amount) {
        this.count += amount;
    }

    public void sell() {
        this.count--;
    }

    public int getCount(){
        return count;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public enum ID {
        SHIELD, SCANNER, FLAG, SHOVEL, DICE, BOMB
    }
}
