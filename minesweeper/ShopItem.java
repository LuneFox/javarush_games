package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Picture;

class ShopItem {
    private final MinesweeperGame game;
    public int cost;
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

    public boolean use(Cell cell) {
        switch (this.id) {
            case SHIELD:
                game.countMinesOnField--; // exploded mine isn't a mine anymore

                cell.assignSprite(Bitmap.BOARD_MINE);
                cell.replaceColor(Color.YELLOW, 3);
                cell.draw();
                cell.drawSprite();
                cell.isShielded = true;

                this.deactivate();
                game.shop.restock(game.shop.shield, 1);

                int scoreBefore = game.score;
                game.score = Math.max(game.score - 150 * (game.difficulty / 5), 0);
                game.scoreLost -= scoreBefore - game.score;
                game.setScore(game.score);

                return true;
            case SCANNER:
                if (this.isActivated) {
                    deactivate();
                    game.shop.restock(game.shop.scanner, 1);
                    game.shop.restock(game.shop.miniBomb, 1);
                    game.scanNeighbors(cell.x, cell.y);
                    game.redrawAllCells();
                    return true;
                }
            case BOMB:
                if (isActivated()) {
                    deactivate();
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

    public void activate() {
        this.isActivated = true;
    }

    public void deactivate() {
        this.isActivated = false;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isNotObtainable() {
        return (game.inventory.money < this.cost || this.inStock <= 0 || this.isActivated);
    }
}
