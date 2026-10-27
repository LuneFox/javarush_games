package com.javarush.games.snake.model;

import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.terrain.TerrainType;

public enum Strings {
    VERSION("1.21", "1.21"),

    // Main menu
    MAIN_TITLE("ALCHEMY SNAKE", "ЗМЕЙКА АЛХИМИКА"),
    MAIN_SUBTITLE("MASTER OF ELEMENTS", "МАСТЕР СТИХИЙ"),
    MAIN_VERSION("VER ", "ВЕРСИЯ "),

    // Menu entries
    MENU_START("START", "ИГРАТЬ"),
    MENU_OPTIONS("OPTIONS", "НАСТРОЙКИ"),
    MENU_CONTROLS("CONTROLS", "УПРАВЛЕНИЕ"),
    MENU_HELP("HELP", "ПОМОЩЬ"),
    MENU_EDIT("EDIT", "РЕДАКТОР"),
    MENU_MAP("MAP", "КАРТА"),
    MENU_SYMBOLS("SYMBOLS", "СИМВОЛЫ"),
    MENU_ACCELERATION("ACCELERATION", "УСКОРЕНИЕ"),
    MENU_LANGUAGE("LANGUAGE", "ЯЗЫК"),

    // Options
    OPTIONS_TITLE("OPTIONS", "НАСТРОЙКИ"),
    OPTION_ENABLED("ENABLED", "ВКЛЮЧЕНО"),
    OPTION_DISABLED("DISABLED", "ВЫКЛЮЧЕНО"),
    LANGUAGE_ENGLISH("ENGLISH", "АНГЛИЙСКИЙ"),
    LANGUAGE_RUSSIAN("RUSSIAN", "РУССКИЙ"),
    SIGN_TYPE_KANJI("KANJI", "КАНДЗИ"),
    SIGN_TYPE_EMOJI("EMOJI", "ЭМОДЗИ"),

    // Help: game goals
    HELP_GOALS_TITLE("GAME GOALS", "ЦЕЛИ ИГРЫ"),
    HELP_GOALS_WIN("COLLECT TO WIN:", "СОБЕРИТЕ, ЧТОБЫ ПОБЕДИТЬ:"),
    HELP_ORB_WATER("ORB OF WATER", "СФЕРА ВОДЫ"),
    HELP_ORB_FIRE("ORB OF FIRE", "СФЕРА ОГНЯ"),
    HELP_ORB_EARTH("ORB OF EARTH", "СФЕРА ЗЕМЛИ"),
    HELP_ORB_AIR("ORB OF AIR", "СФЕРА ВОЗДУХА"),
    HELP_ORB_POWER("ORB OF POWER", "СФЕРА СИЛЫ"),
    HELP_GOALS_GROW("COLLECT TO GROW:", "СОБЕРИТЕ, ЧТОБЫ РАСТИ:"),
    HELP_ORB_WISDOM("ORB OF WISDOM", "СФЕРА МУДРОСТИ"),

    // Help: snake abilities
    HELP_ABILITIES_TITLE("SNAKE ABILITIES", "ВОЗМОЖНОСТИ ЗМЕИ"),
    HELP_NEUTRAL_NAME("— NEUTRAL SNAKE", "— НЕЙТРАЛЬНАЯ ЗМЕЯ"),
    HELP_NEUTRAL_DESC("NO SPECIAL POWERS", "НЕТ ОСОБЫХ СИЛ"),
    HELP_WATER_NAME("— WATER SNAKE", "— ВОДЯНАЯ ЗМЕЯ"),
    HELP_WATER_DESC("CAN SWIM AND PUT OUT FIRE", "ПЛАВАЕТ, ТУШИТ ОГОНЬ"),
    HELP_FIRE_NAME("— FIRE SNAKE", "— ОГНЕННАЯ ЗМЕЯ"),
    HELP_FIRE_DESC("CAN BURN WOOD, HATES WATER", "СЖИГАЕТ ЛЕС, БОИТСЯ ВОДЫ"),
    HELP_EARTH_NAME("— EARTH SNAKE", "— ЗЕМЛЯНАЯ ЗМЕЯ"),
    HELP_EARTH_DESC("CAN USE WORMHOLES", "ИСПОЛЬЗУЕТ ЧЕРВОТОЧИНЫ"),
    HELP_AIR_NAME("— AIR SNAKE", "— ВОЗДУШНАЯ ЗМЕЯ"),
    HELP_AIR_DESC("FLIES OVER WALLS, CAN'T EAT", "ЛЕТАЕТ СКВОЗЬ СТЕНЫ, НЕ ЕСТ"),
    HELP_ALMIGHTY_NAME("— ALMIGHTY SNAKE", "— ВСЕМОГУЩАЯ ЗМЕЯ"),
    HELP_ALMIGHTY_DESC("INVULNERABLE SPACE EATER", "НЕУЯЗВИМА, ЕСТ ПРОСТРАНСТВО"),

    // Help: terrain types
    HELP_TERRAIN_TITLE("TYPES OF TERRAIN", "ВИДЫ МЕСТНОСТИ"),
    TERRAIN_FIELD("GRASS FIELD", "ТРАВЯНОЕ ПОЛЕ"),
    TERRAIN_WATER("DEEP WATER", "ГЛУБОКАЯ ВОДА"),
    TERRAIN_SAND("DRY SAND", "СУХОЙ ПЕСОК"),
    TERRAIN_FOREST("DENSE FOREST", "ГУСТОЙ ЛЕС"),
    TERRAIN_MOUNTAIN("IMPASSABLE MOUNTAIN", "НЕПРОХОДИМАЯ ГОРА"),
    TERRAIN_WOOD("WOODEN FLOOR", "ДЕРЕВЯННЫЙ ПОЛ"),
    TERRAIN_FIRE("BURNING WOOD", "ГОРЯЩЕЕ ДЕРЕВО"),
    TERRAIN_WALL("TALL WALL", "ВЫСОКАЯ СТЕНА"),
    TERRAIN_WORMHOLE("SECRET WORMHOLE", "ТАЙНАЯ ЧЕРВОТОЧИНА"),
    TERRAIN_VOID("VOID", "ПУСТОТА"),

    // Help: rules and tips (1)
    HELP_TIPS1_TITLE("RULES AND TIPS (1)", "ПРАВИЛА И СОВЕТЫ (1)"),
    HELP_TIPS1_COLLECT("YOU SHOULD COLLECT ALL ORBS", "СОБЕРИТЕ ВСЕ СФЕРЫ"),
    HELP_TIPS1_FAST("AS FAST AS POSSIBLE.", "КАК МОЖНО БЫСТРЕЕ."),
    HELP_TIPS1_EAT("EATING ELEMENTAL ORBS LETS", "СЪЕВ СФЕРУ СТИХИИ,"),
    HELP_TIPS1_USE("YOU USE THEIR ELEMENTS.", "ВЫ ПОЛУЧАЕТЕ ЕЁ СИЛУ."),
    HELP_TIPS1_PICK("PICKING UP AN ELEMENTAL ORB", "ПОДОБРАВ СФЕРУ СТИХИИ,"),
    HELP_TIPS1_SWITCH("SWITCHES YOU TO ITS ELEMENT.", "ВЫ ПЕРЕКЛЮЧАЕТЕСЬ НА НЕЁ."),
    HELP_TIPS1_NOSWAP("YOU CAN'T SWAP ELEMENTS", "НЕЛЬЗЯ МЕНЯТЬ СТИХИЮ,"),
    HELP_TIPS1_WHILE("WHILE EATING A NEW ONE.", "ПОКА ЕДИТЕ НОВУЮ."),
    HELP_TIPS1_TURN("YOU CAN TURN & SWAP ELEMENTS", "МОЖНО ПОВЕРНУТЬ И СМЕНИТЬ"),
    HELP_TIPS1_SLEEP("EVEN WHILE SLEEPING.", "СТИХИЮ ДАЖЕ ВО СНЕ."),

    // Help: rules and tips (2)
    HELP_TIPS2_TITLE("RULES AND TIPS (2)", "ПРАВИЛА И СОВЕТЫ (2)"),
    HELP_TIPS2_LONG("A LONG SNAKE BECOMES", "ДЛИННАЯ ЗМЕЯ СТАНОВИТСЯ"),
    HELP_TIPS2_FASTER("FASTER AND STRONGER.", "БЫСТРЕЕ И СИЛЬНЕЕ."),
    HELP_TIPS2_BUT("BUT A LONGER SNAKE", "НО ДЛИННОЙ ЗМЕЕ"),
    HELP_TIPS2_HUNGRY("WANTS TO EAT MORE OFTEN.", "ХОЧЕТСЯ ЕСТЬ ЧАЩЕ."),
    HELP_TIPS2_TAIL("A HUNGRY SNAKE LOSES ITS TAIL", "ГОЛОДНАЯ ЗМЕЯ ТЕРЯЕТ ХВОСТ"),
    HELP_TIPS2_SHORTER("AND BECOMES A LITTLE SHORTER.", "И СТАНОВИТСЯ КОРОЧЕ."),
    HELP_TIPS2_WATER("A LONGER WATER SNAKE PUTS", "ЧЕМ ДЛИННЕЕ ВОДЯНАЯ ЗМЕЯ,"),
    HELP_TIPS2_EFFICIENT("OUT FIRE MORE EFFICIENTLY.", "ТЕМ ЛУЧШЕ ТУШИТ ОГОНЬ."),
    HELP_TIPS2_FIRE("ALSO, A FIRE SNAKE MELTS", "А ОГНЕННАЯ ЗМЕЯ ТАЕТ"),
    HELP_TIPS2_MELT("WITH EVERY STEP IN THE WATER!", "С КАЖДЫМ ШАГОМ В ВОДЕ!"),

    // Help: controls
    HELP_CONTROLS_TITLE("CONTROLS", "УПРАВЛЕНИЕ"),
    HELP_CONTROL_ARROWS("↑ ↓ ← →       :", "↑ ↓ ← →       :"),
    HELP_CONTROL_HOLD("HOLD DIRECTION:", "ДЕРЖАТЬ КУРС  :"),
    HELP_CONTROL_ENTER("ENTER, R-CLICK:", "ENTER, ПКМ    :"),
    HELP_CONTROL_ESC("ESC,   L-CLICK:", "ESC, ЛКМ      :"),
    HELP_CONTROL_SPACE_GAME("SPACE (GAME)  :", "ПРОБЕЛ (ИГРА) :"),
    HELP_CONTROL_SPACE_OVER("SPACE (G.OVER):", "ПРОБЕЛ (КОНЕЦ):"),
    HELP_CONTROL_DIRECTION("DIRECTION", "НАПРАВЛЕНИЕ"),
    HELP_CONTROL_ACCELERATE("ACCELERATE", "УСКОРЕНИЕ"),
    HELP_CONTROL_NEXT("NEXT ELEMENT", "СЛЕД. СТИХИЯ"),
    HELP_CONTROL_PREV("PREV ELEMENT", "ПРЕД. СТИХИЯ"),
    HELP_CONTROL_SLEEP("SLEEP", "СОН"),
    HELP_CONTROL_BACK("BACK TO MENU", "НАЗАД В МЕНЮ"),
    HELP_PAGE("← PAGE %d/%d →", "← СТР. %d/%d →"),

    // Game field
    HUD_HUNGER("hunger  : ", "голод   : "),
    HUD_STRENGTH("strength: ", "длина   : "),
    HUD_ELEMENT("element : ", "стихия  : "),
    HUD_SCORE("score   : ", "счёт    : "),
    HUD_POWER("power: ", "мощь: "),
    GAME_SLEEPING(" SLEEPING... ", "   СПИТ...   "),

    ELEMENT_NEUTRAL("NEUTRAL", "НЕЙТРАЛЬНАЯ"),
    ELEMENT_WATER("WATER", "ВОДА"),
    ELEMENT_FIRE("FIRE", "ОГОНЬ"),
    ELEMENT_EARTH("EARTH", "ЗЕМЛЯ"),
    ELEMENT_AIR("AIR", "ВОЗДУХ"),
    ELEMENT_ALMIGHTY("ALMIGHTY", "ВСЕМОГУЩАЯ"),
    ELEMENT_DEAD("DEAD", "МЁРТВАЯ"),

    ELEMENT_SHORT_NEUTRAL("N", "Н"),
    ELEMENT_SHORT_WATER("W", "В"),
    ELEMENT_SHORT_FIRE("F", "О"),
    ELEMENT_SHORT_EARTH("E", "З"),
    ELEMENT_SHORT_AIR("A", "ВЗ"),
    ELEMENT_SHORT_ALMIGHTY("S", "ВС"),
    ELEMENT_SHORT_DEAD("D", "М"),

    // Game over
    GAME_OVER_BUMPED("Snake hit the wall!\nOuch, it hurts!",
            "Змея врезалась в стену!\nОй, как больно!"),
    GAME_OVER_DROWNED("Snake has drowned!\nOnly water snakes can swim!",
            "Змея утонула!\nПлавать умеют только водяные змеи!"),
    GAME_OVER_BURNED("Snake has burned!\nExtinguish the fire with the water form!",
            "Змея сгорела!\nТушите огонь в облике водяной змеи!"),
    GAME_OVER_EATEN("Snake was eaten by forest beasts!\nYou can burn forests with fire.",
            "Змею съели лесные звери!\nЛес можно сжигать огнём."),
    GAME_OVER_LOST("Snake got lost underground!\nOnly earth snakes can use wormholes!",
            "Змея заблудилась под землёй!\nЧервоточины доступны только земляным змеям!"),
    GAME_OVER_RUNAWAY("Snake tried to escape!\nAlchemy is not for cowards!",
            "Змея попыталась сбежать!\nАлхимия не для трусов!"),
    GAME_OVER_SELF_BITTEN("Snake bit itself to death!\nAre you THAT hungry?",
            "Змея укусила себя до смерти!\nНеужели вы ТАК голодны?"),
    GAME_OVER_MOUNTAIN("Snake bumped into a mountain!\nAlways avoid them!",
            "Змея врезалась в гору!\nОбходите их стороной!"),
    GAME_OVER_UNKNOWN("Snake died for unknown reasons.",
            "Змея погибла по неизвестной причине."),

    // Stage names
    STAGE_TUTORIAL_1("TUTORIAL 1", "ОБУЧЕНИЕ 1"),
    STAGE_TUTORIAL_2("TUTORIAL 2", "ОБУЧЕНИЕ 2"),
    STAGE_TUTORIAL_3("TUTORIAL 3", "ОБУЧЕНИЕ 3"),
    STAGE_TUTORIAL_4("TUTORIAL 4", "ОБУЧЕНИЕ 4"),
    STAGE_TUTORIAL_5("TUTORIAL 5", "ОБУЧЕНИЕ 5"),
    STAGE_TUTORIAL_6("TUTORIAL 6", "ОБУЧЕНИЕ 6"),
    STAGE_LEVEL_1("STAGE 1", "УРОВЕНЬ 1"),
    STAGE_LEVEL_2("STAGE 2", "УРОВЕНЬ 2"),
    STAGE_LEVEL_3("STAGE 3", "УРОВЕНЬ 3"),
    STAGE_EMPTY("Empty", "ПУСТАЯ"),
    STAGE_UNNAMED("Unnamed", "БЕЗ ИМЕНИ"),

    // Tutorial 1
    TUTORIAL_1_BRIEFING("%s\nAlchemy Snake feeds on Orbs of Wisdom.\nThey look like this: %s\nCollect 3 of them!\n\n~ PRESS SPACE TO START ~",
            "%s\nЗмейка алхимика питается Сферами Мудрости.\nОни выглядят так: %s\nСоберите 3 из них!\n\n~ ПРОБЕЛ — НАЧАТЬ ~"),
    TUTORIAL_1_COMPLETE("%s COMPLETE!\nGOOD JOB!\n\n~ PRESS SPACE TO RETURN ~",
            "%s ПРОЙДЕНО!\nМОЛОДЕЦ!\n\n~ ПРОБЕЛ — В МЕНЮ ~"),

    // Tutorial 2
    TUTORIAL_2_BRIEFING("%s\nLook, it's a river! But our snake can't swim yet.\nIt will drown if it goes fully into the water: %s\nGrow longer, build a bridge and cross the river!\n\n~ PRESS SPACE TO START ~",
            "%s\nСмотрите, это река! Но наша змея пока не умеет плавать.\nОна утонет, если полностью уйдёт под воду: %s\nУдлинитесь, постройте мост и пересеките реку!\n\n~ ПРОБЕЛ — НАЧАТЬ ~"),
    TUTORIAL_2_COMPLETE("%s COMPLETE!\nYOU SUCCESSFULLY CROSSED THE RIVER!\n\n~ PRESS SPACE TO RETURN ~",
            "%s ПРОЙДЕНО!\nВЫ УСПЕШНО ПЕРЕСЕКЛИ РЕКУ!\n\n~ ПРОБЕЛ — В МЕНЮ ~"),

    // Tutorial 3
    TUTORIAL_3_BRIEFING("%s\nLet's learn something about the elements.\nEat the Orb of Water (%s) to be able to swim.\nThe water snake can extinguish fire (%s).\nNow put out the fire on the right side!\n~ PRESS SPACE TO START ~",
            "%s\nДавайте узнаем о стихиях.\nСъешьте Сферу Воды (%s), чтобы научиться плавать.\nВодяная змея может тушить огонь (%s).\nА теперь потушите огонь справа!\n~ ПРОБЕЛ — НАЧАТЬ ~"),
    TUTORIAL_3_COMPLETE("%s COMPLETE!\nEXCELLENT WORK!\n\n~ PRESS SPACE TO RETURN ~",
            "%s ПРОЙДЕНО!\nОТЛИЧНАЯ РАБОТА!\n\n~ ПРОБЕЛ — В МЕНЮ ~"),

    // Tutorial 4
    TUTORIAL_4_BRIEFING("%s\nThe dense forest is guarding the Earth Orb!\nLuckily, we can eat the Fire Orb (%s) to set wood aflame!\nGet rid of the trees (%s) and grab the Earth Orb!\n\n~ PRESS SPACE TO START ~",
            "%s\nГустой лес охраняет Сферу Земли!\nК счастью, мы можем съесть Сферу Огня (%s) и поджечь дерево!\nИзбавьтесь от деревьев (%s) и заберите Сферу Земли!\n\n~ ПРОБЕЛ — НАЧАТЬ ~"),
    TUTORIAL_4_COMPLETE("%s COMPLETE!\nYOU'RE ON FIRE!\n\n~ PRESS SPACE TO RETURN ~",
            "%s ПРОЙДЕНО!\nВЫ В ОГНЕ!\n\n~ ПРОБЕЛ — В МЕНЮ ~"),

    // Tutorial 5
    TUTORIAL_5_BRIEFING("%s\nIsn't there an Air Orb behind the wall?...\nLet's eat the Earth Orb (%s) to be able to enter wormholes!\nBut which wormhole (%s) leads to the Air Orb?...\nWell, let's try and guess!\n\n~ PRESS SPACE TO START ~",
            "%s\nНеужели за стеной Сфера Воздуха?...\nСъешьте Сферу Земли (%s), чтобы уметь входить в червоточины!\nНо какая червоточина (%s) ведёт к Сфере Воздуха?...\nЧто ж, давайте проверим!\n\n~ ПРОБЕЛ — НАЧАТЬ ~"),
    TUTORIAL_5_COMPLETE("%s COMPLETE!\nJust how deep is the rabbit hole?...\n\n~ PRESS SPACE TO RETURN ~",
            "%s ПРОЙДЕНО!\nКак глубока кроличья нора?...\n\n~ ПРОБЕЛ — В МЕНЮ ~"),

    // Tutorial 6
    TUTORIAL_6_BRIEFING("%s\nWhoa! It's the legendary ORB OF POWER... behind the wall again!\nBut there are no wormholes this time...\nHow about eating the Air Orb (%s) to fly over walls?\nPress ESC or ENTER to switch elements and land.\n~ PRESS SPACE TO START ~",
            "%s\nОго! Легендарная СФЕРА СИЛЫ... снова за стеной!\nНо на этот раз червоточин нет...\nМожет, съесть Сферу Воздуха (%s) и перелететь через стену?\nНажмите ESC или ENTER, чтобы сменить стихию и приземлиться.\n~ ПРОБЕЛ — НАЧАТЬ ~"),
    TUTORIAL_6_COMPLETE("%s COMPLETE!\nThat's all for today's lessons.\nNow let's try some real stages!\n\n~ PRESS SPACE TO RETURN ~",
            "%s ПРОЙДЕНО!\nНа сегодня уроков хватит.\nТеперь попробуйте настоящие уровни!\n\n~ ПРОБЕЛ — В МЕНЮ ~"),

    // Regular stages
    LEVEL_BRIEFING("%s\nCollect the Orb of Power!\nBecome the God of Snakes and destroy the realm!\n\n~ PRESS SPACE TO START ~",
            "%s\nСоберите Сферу Силы!\nСтаньте Богом Змей и разрушьте царство!\n\n~ ПРОБЕЛ — НАЧАТЬ ~"),
    LEVEL_COMPLETE("%s COMPLETE!", "%s ПРОЙДЕН!"),
    LAST_LEVEL_COMPLETE("%s COMPLETE!\n\nThis was the last stage... for now!",
            "%s ПРОЙДЕН!\n\nЭто был последний уровень... пока что!"),

    // Debug stage
    EMPTY_BRIEFING("TESTING STAGE", "ТЕСТОВЫЙ УРОВЕНЬ"),
    EMPTY_COMPLETE("TEST COMPLETE", "ТЕСТ ПРОЙДЕН");

    private final String english;
    private final String russian;

    Strings(String english, String russian) {
        this.english = english;
        this.russian = russian;
    }

    public String get() {
        return (Options.getLanguage() == Options.Language.RUSSIAN) ? russian : english;
    }

    public String format(Object... args) {
        final String template = get();
        final StringBuilder result = new StringBuilder();
        int argIndex = 0;

        for (int i = 0; i < template.length(); i++) {
            final char current = template.charAt(i);

            if (current == '%' && i + 1 < template.length() && argIndex < args.length) {
                final char next = template.charAt(i + 1);

                if (next == 's' || next == 'd') {
                    result.append(args[argIndex].toString());
                    argIndex++;
                    i++;
                    continue;
                }
            }

            result.append(current);
        }

        return result.toString();
    }

    public static String getElementName(Element element) {
        switch (element) {
            case NEUTRAL:
                return ELEMENT_NEUTRAL.get();
            case WATER:
                return ELEMENT_WATER.get();
            case FIRE:
                return ELEMENT_FIRE.get();
            case EARTH:
                return ELEMENT_EARTH.get();
            case AIR:
                return ELEMENT_AIR.get();
            case ALMIGHTY:
                return ELEMENT_ALMIGHTY.get();
            case DEAD:
                return ELEMENT_DEAD.get();
            default:
                return element.name();
        }
    }

    public static String getElementShortName(Element element) {
        switch (element) {
            case NEUTRAL:
                return ELEMENT_SHORT_NEUTRAL.get();
            case WATER:
                return ELEMENT_SHORT_WATER.get();
            case FIRE:
                return ELEMENT_SHORT_FIRE.get();
            case EARTH:
                return ELEMENT_SHORT_EARTH.get();
            case AIR:
                return ELEMENT_SHORT_AIR.get();
            case ALMIGHTY:
                return ELEMENT_SHORT_ALMIGHTY.get();
            case DEAD:
                return ELEMENT_SHORT_DEAD.get();
            default:
                return element.name();
        }
    }

    public static String getTerrainName(TerrainType type) {
        switch (type) {
            case FIELD:
                return TERRAIN_FIELD.get();
            case WATER:
                return TERRAIN_WATER.get();
            case SAND:
                return TERRAIN_SAND.get();
            case FOREST:
                return TERRAIN_FOREST.get();
            case MOUNTAIN:
                return TERRAIN_MOUNTAIN.get();
            case WOOD:
                return TERRAIN_WOOD.get();
            case FIRE:
                return TERRAIN_FIRE.get();
            case WALL:
                return TERRAIN_WALL.get();
            case WORMHOLE:
                return TERRAIN_WORMHOLE.get();
            case VOID:
                return TERRAIN_VOID.get();
            default:
                return type.name();
        }
    }
}
