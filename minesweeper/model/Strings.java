package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.model.Options;

/**
 * Some long strings are stored here.
 */

public class Strings {
    public static final String VERSION = "1.21";

    public static final String[] RECORDS = new String[]{
            "<Лучшие игроки>",
            "Dim\nID 2700224", "43263",
            "Pavlo Plynko\nID 28219", "37890",
            "Михаил Васильев\nID 2522974", "37125"};

    public static final String[] DIFFICULTY_NAMES = new String[]{
            "хомячок", "новичок", "любитель",
            "опытный", "эксперт", "отчаянный",
            "безумец", "бессмертный", "чак норрис"

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

    public static final String ITEM_SHIELD_NAME = "Сапёрский щит";

    public static StringBuilder generateNewShieldDescription() {
        return new StringBuilder("Спасёт от взрыва\nпри открытии мины\nодин раз. Однако вы\n" +
                "потеряете " + 150 * (Options.difficulty / 5) + " очков.");
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

    public static final String OPTIONS_SAVED = "Сохранено";
}