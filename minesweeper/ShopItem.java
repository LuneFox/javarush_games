package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.graphics.Picture;

class ShopItem {
    int cost;
    int count;
    int expireMove;
    boolean isActivated;
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
                this.description =
                        "Спасёт от взрыва\n" +
                                "при открытии мины\n" +
                                "один раз. Однако вы\n" +
                                "потеряете " + 150 * (game.difficulty / 5) + " очков.";
                break;
            case 1:
                this.id = ID.SCANNER;
                this.name = "Сканер";
                this.description =
                        "Откроет безопасную\n" +
                                "клетку в поле 3*3\n" +
                                "вокруг курсора.\n" +
                                "Если таких нет,\n" +
                                "расставит флажки\n" +
                                "над минами, подарив\n" +
                                "нехватающие.";
                break;
            case 2:
                this.id = ID.FLAG;
                this.name = "Флажок";
                this.description =
                        "Обычный флажок\n" +
                                "для установки на\n" +
                                "поле. Вы можете\n" +
                                "сэкономить золото,\n" +
                                "не покупая флажки,\n" +
                                "когда позиция мины\n" +
                                "очевидна.";
                break;
            case 3:
                this.id = ID.SHOVEL;
                this.name = "Золотая лопата";
                this.description =
                        "Следующие 5 шагов\n" +
                                "вы будете получать\n" +
                                "в два раза больше\n" +
                                "золотых монет.\n" +
                                "Золото добывается\n" +
                                "при открытии клеток\n" +
                                "с цифрами.";
                break;
            case 4:
                this.id = ID.DICE;
                this.name = "Кубик удачи";
                this.description =
                        "Следующие 3 шага\n" +
                                "вы можете получить\n" +
                                "от 1 до 6 раз больше\n" +
                                "очков. Базовое\n" +
                                "количество зависит\n" +
                                "от сложности игры.";
                break;
            case 5:
                this.id = ID.BOMB;
                this.name = "Мини-бомба";
                this.description =
                        "Бросив бомбочку, вы\n" +
                                "уничтожите закрытую\n" +
                                "клетку на поле.\n" +
                                "Если взорвёте мину,\n" +
                                "соседние мины тоже\n" +
                                "взорвутся по цепи.\n" +
                                "Очков не даёт.";
                break;
            default:
                break;
        }
    }

    public enum ID {
        SHIELD, SCANNER, FLAG, SHOVEL, DICE, BOMB
    }
}
