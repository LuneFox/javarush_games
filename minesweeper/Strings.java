package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.view.View;

/**
 * Most part of the long strings goes here.
 */

public class Strings {
    public static final String VERSION = "1.20";

    public static final String[] RECORDS = new String[]{
            "Лучшие игроки",
            "Dim\nID 2700224", "43263",
            "Pavlo Plynko\nID 28219", "37890",
            "Михаил Васильев\nID 2522974", "37125"
    };

    public static final String[] DIFFICULTY_NAMES = new String[]{
            "хомячок",
            "новичок",
            "любитель",
            "опытный",
            "эксперт",
            "отчаянный",
            "безумец",
            "бессмертный",
            "чак норрис"
    };

    public static final String[] QUOTES = new String[]{
            "Самая взрывная\nголоволомка!",
            "Осторожно, игра\nзаминирована!",
            "Просто бомба!",
            "Здесь не бывает\nкислых мин!",
            "Не собери их все!",
            "Главное - не бомбить",
            "Не приводи детей\nна работу!",
            "В лопате нет\nничего смешного!",
            "Втыкая флаг,\nне задень мину!",
            "Какой идиот\nзакопал цифры?!"
    };


    public static final String[] ABOUT_HEAD = new String[]{
            "Информация",
            "О магазине",
            "Удобства",
            "О счёте",
            "На время",
            "Рекорды",
    };

    public static final String[] ABOUT_BODY = new String[]{
            "В моей версии игры\nесть магазин вещей.\nОни помогут меньше\nполагаться на удачу," +
                    "\nбольше планировать\nкаждый свой ход.\nЦены зависят от\nсложности уровня.",
            "Во время игры\nклавиша [пробел]\nоткроет или закроет\nмагазин.\n" +
                    "\nПравый клик по вещи\nпокажет её описание.",
            "Если около ячейки\nуже стоит равное ей\nколичество флажков,\nправый клик по ней" +
                    "\nоткроет прилегающие\nячейки автоматом,\nаналогично двойному\nклику в Windows.",
            "Чтобы посмотреть\nподробности счёта\nв конце игры,\nнажмите на слово" +
                    "\n[счёт] рядом со\nсмайликом или на\nклавишу [пробел].\n",
            "В игре на время\nуспевайте открывать\nновые ячейки, пока\nшкала вверху экрана" +
                    "\nне закончилась. Если\nвремя выйдет, мины\nвзорвутся! Скорость\nдаёт больше очков.",
            "Чтобы попасть на\nстраницу рекордов,\nсделайте скриншот\nс вашим результатом" +
                    "\nи прикрепите его\nв комментариях.\n\n...и спасибо за игру!",
    };

    public static final String ITEM_SHIELD_NAME = "Сапёрский щит";

    public static StringBuilder generateNewShieldDescription() {
        return new StringBuilder("Спасёт от взрыва\nпри открытии мины\nодин раз. Однако вы\n" +
                "потеряете " + 150 * (MinesweeperGame.getInstance().difficultySetting / 5) + " очков.");
    }

    public static final String ITEM_SCANNER_NAME = "Сканер";
    public static final String ITEM_SCANNER_DESCRIPTION =
            "Откроет безопасную\nклетку в поле 3*3\nвокруг курсора.\nЕсли таких нет,\n" +
                    "расставит флажки\nнад минами, подарив\nнехватающие.";
    public static final String ITEM_FLAG_NAME = "Флажок";
    public static final String ITEM_FLAG_DESCRIPTION =
            "Обычный флажок\nдля установки на\nполе. Вы можете\nсэкономить золото,\n" +
                    "не покупая флажки,\nкогда позиция мины\nочевидна.";
    public static final String ITEM_SHOVEL_NAME = "Золотая лопата";
    public static final String ITEM_SHOVEL_DESCRIPTION =
            "Следующие 5 шагов\nвы будете получать\nв два раза больше\nзолотых монет.\n" +
                    "Золото добывается\nпри открытии клеток\nс цифрами.";
    public static final String ITEM_DICE_NAME = "Кубик удачи";
    public static final String ITEM_DICE_DESCRIPTION =
            "Следующие 3 шага\nвы можете получить\nот 1 до 6 раз больше\nочков. Базовое\n" +
                    "количество зависит\nот сложности игры.";
    public static final String ITEM_BOMB_NAME = "Мини-бомба";
    public static final String ITEM_BOMB_DESCRIPTION =
            "Бросив бомбочку, вы\nуничтожите закрытую\nклетку на поле.\nЕсли взорвёте мину,\n" +
                    "соседние мины тоже\nвзорвутся по цепи.\nОчков не даёт.";


}
