package com.javarush.games.minesweeper;

public class Strings {
    public static final String VERSION = "1.11";
    public static int currentAboutPage = 0;

    public static final String[] RECORDS = new String[]{
            "Лучшие игроки",
            "Pavlo Plynko", "37890",
            "Станислав\nНикитин", "35955",
            "Михаил Васильев", "35775"
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

    public static String[] getAbout() {
        switch (currentAboutPage) {
            case 0:
                return new String[]{
                        "Информация",
                        "В моей версии игры\nесть магазин вещей.\nОн поможет меньше\nполагаться на удачу," +
                                "\nбольше планировать.\n\nЖмите далее, чтобы\nузнать больше."};
            case 1:
                return new String[]{
                        "О магазине",
                        "Во время игры\nклавиша пробел\nоткроет или закроет\nмагазин.\n" +
                                "\nПравый клик по вещи\nпокажет её описание."
                };
            case 2:
                return new String[]{
                        "Удобства",
                        "Если около ячейки\nуже стоит нужное\nколичество флажков,\nправый клик по ней" +
                                "\nоткроет прилегающие\nячейки автоматом.\n\nпрекрасно же?"
                };
            case 3:
                return new String[]{
                        "О счёте",
                        "Чтобы посмотреть\nподробности счёта\nв конце игры,\nнажмите на слово" +
                                "\n-счёт- рядом со\nсмайликом.\n\n"
                };
            case 4:
                return new String[]{
                        "Рекорды",
                        "Чтобы попасть на\nстраницу рекордов,\nсделайте скриншот\nс вашим результатом" +
                                "\nи прикрепите его\nв комментариях.\n\nВсё просто!"
                };

            case 5:
                return new String[]{
                        "Конец",
                        "Мне пока больше\nнечего сказать.\nЧестное слово!\n" +
                                "\nНажмёте далее -\nвернётесь в начало.\n\n...и спасибо за игру!"
                };
        }
        return new String[]{"", ""};
    }

    public static final String ITEM_SHIELD_NAME = "Сапёрский щит";

    public static StringBuilder generateNewShieldDescription() {
        return new StringBuilder("Спасёт от взрыва\nпри открытии мины\nодин раз. Однако вы\n" +
                "потеряете " + 150 * (MinesweeperGame.getDifficulty() / 5) + " очков.");
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
