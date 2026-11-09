package com.javarush.games.minesweeper.gui.image;

/** Типы графических элементов игры. */
public enum ImageType {
    /** Пустое изображение. */
    NONE,

    /** Элемент интерфейса GUI_BACKGROUND. */
    GUI_BACKGROUND,
    /** Элемент интерфейса GUI_BUTTON. */
    GUI_BUTTON,
    /** Элемент интерфейса GUI_SURROUND_FRAME. */
    GUI_SURROUND_FRAME,
    /** Элемент интерфейса GUI_POPUP_MESSAGE. */
    GUI_POPUP_MESSAGE,
    /** Элемент интерфейса GUI_ARROW. */
    GUI_ARROW,
    /** Элемент интерфейса GUI_DIFFICULTY_BAR. */
    GUI_DIFFICULTY_BAR,
    /** Элемент интерфейса GUI_SWITCH_HANDLE. */
    GUI_SWITCH_HANDLE,
    /** Элемент интерфейса GUI_SWITCH_RAIL. */
    GUI_SWITCH_RAIL,
    /** Элемент интерфейса GUI_THEME_PALETTE. */
    GUI_THEME_PALETTE,
    /** Элемент интерфейса GUI_VICTORY_WINDOW. */
    GUI_VICTORY_WINDOW,

    /** Элемент клетки CELL_OPENED. */
    CELL_OPENED,
    /** Элемент клетки CELL_CLOSED. */
    CELL_CLOSED,
    /** Элемент поля BOARD_DESTROYED. */
    BOARD_DESTROYED,

    /** Элемент поля BOARD_0. */
    BOARD_0,
    /** Элемент поля BOARD_1. */
    BOARD_1,
    /** Элемент поля BOARD_2. */
    BOARD_2,
    /** Элемент поля BOARD_3. */
    BOARD_3,
    /** Элемент поля BOARD_4. */
    BOARD_4,
    /** Элемент поля BOARD_5. */
    BOARD_5,
    /** Элемент поля BOARD_6. */
    BOARD_6,
    /** Элемент поля BOARD_7. */
    BOARD_7,
    /** Элемент поля BOARD_8. */
    BOARD_8,
    /** Элемент поля BOARD_9. */
    BOARD_9,
    /** Элемент поля BOARD_FLAG. */
    BOARD_FLAG,
    /** Элемент поля BOARD_MINE. */
    BOARD_MINE,
    /** Элемент поля BOARD_SHOP. */
    BOARD_SHOP,
    /** Элемент поля BOARD_DICE_1. */
    BOARD_DICE_1,
    /** Элемент поля BOARD_DICE_2. */
    BOARD_DICE_2,
    /** Элемент поля BOARD_DICE_3. */
    BOARD_DICE_3,
    /** Элемент поля BOARD_DICE_4. */
    BOARD_DICE_4,
    /** Элемент поля BOARD_DICE_5. */
    BOARD_DICE_5,
    /** Элемент поля BOARD_DICE_6. */
    BOARD_DICE_6,
    /** Элемент поля BOARD_SCANNER_FRAME. */
    BOARD_SCANNER_FRAME,
    /** Элемент поля BOARD_BOMB_FRAME. */
    BOARD_BOMB_FRAME,

    /** Элемент магазина SHOP_HEADER_COIN. */
    SHOP_HEADER_COIN,
    /** Элемент магазина SHOP_HEADER_PANEL. */
    SHOP_HEADER_PANEL,
    /** Элемент магазина SHOP_SHOWCASE_PANEL. */
    SHOP_SHOWCASE_PANEL,
    /** Элемент магазина SHOP_SHOWCASE_FRAME. */
    SHOP_SHOWCASE_FRAME,
    /** Элемент магазина SHOP_SHOWCASE_FRAME_PRESSED. */
    SHOP_SHOWCASE_FRAME_PRESSED,
    /** Элемент магазина SHOP_SHOWCASE_SHIELD. */
    SHOP_SHOWCASE_SHIELD,
    /** Элемент магазина SHOP_SHOWCASE_SCANNER. */
    SHOP_SHOWCASE_SCANNER,
    /** Элемент магазина SHOP_SHOWCASE_FLAG. */
    SHOP_SHOWCASE_FLAG,
    /** Элемент магазина SHOP_SHOWCASE_SHOVEL. */
    SHOP_SHOWCASE_SHOVEL,
    /** Элемент магазина SHOP_SHOWCASE_DICE. */
    SHOP_SHOWCASE_DICE,
    /** Элемент магазина SHOP_SHOWCASE_BOMB. */
    SHOP_SHOWCASE_BOMB,

    /** Картинка PICTURE_MAIN_LOGO. */
    PICTURE_MAIN_LOGO,
    /** Картинка PICTURE_PRIZE_CUP. */
    PICTURE_PRIZE_CUP,
    /** Картинка PICTURE_YELLOW_CAT_SMILE. */
    PICTURE_YELLOW_CAT_SMILE,
    /** Картинка PICTURE_YELLOW_CAT_SAD. */
    PICTURE_YELLOW_CAT_SAD,

    /** Символ шрифта SYM_RU_LETTER_A. */
    SYM_RU_LETTER_A('а', 'a'),
    /** Символ шрифта SYM_RU_LETTER_B. */
    SYM_RU_LETTER_B('б'),
    /** Символ шрифта SYM_RU_LETTER_V. */
    SYM_RU_LETTER_V('в', 'b'),
    /** Символ шрифта SYM_RU_LETTER_G. */
    SYM_RU_LETTER_G('г'),
    /** Символ шрифта SYM_RU_LETTER_D. */
    SYM_RU_LETTER_D('д'),
    /** Символ шрифта SYM_RU_LETTER_YE. */
    SYM_RU_LETTER_YE('е', 'e'),
    /** Символ шрифта SYM_RU_LETTER_YO. */
    SYM_RU_LETTER_YO('ё'),
    /** Символ шрифта SYM_RU_LETTER_J. */
    SYM_RU_LETTER_J('ж'),
    /** Символ шрифта SYM_RU_LETTER_Z. */
    SYM_RU_LETTER_Z('з'),
    /** Символ шрифта SYM_RU_LETTER_I. */
    SYM_RU_LETTER_I('и'),
    /** Символ шрифта SYM_RU_LETTER_IKR. */
    SYM_RU_LETTER_IKR('й'),
    /** Символ шрифта SYM_RU_LETTER_K. */
    SYM_RU_LETTER_K('к', 'k'),
    /** Символ шрифта SYM_RU_LETTER_L. */
    SYM_RU_LETTER_L('л'),
    /** Символ шрифта SYM_RU_LETTER_M. */
    SYM_RU_LETTER_M('м', 'm'),
    /** Символ шрифта SYM_RU_LETTER_N. */
    SYM_RU_LETTER_N('н', 'h'),
    /** Символ шрифта SYM_RU_LETTER_O. */
    SYM_RU_LETTER_O('о', 'o'),
    /** Символ шрифта SYM_RU_LETTER_P. */
    SYM_RU_LETTER_P('п'),
    /** Символ шрифта SYM_RU_LETTER_R. */
    SYM_RU_LETTER_R('р', 'p'),
    /** Символ шрифта SYM_RU_LETTER_S. */
    SYM_RU_LETTER_S('с', 'c'),
    /** Символ шрифта SYM_RU_LETTER_T. */
    SYM_RU_LETTER_T('т', 't'),
    /** Символ шрифта SYM_RU_LETTER_U. */
    SYM_RU_LETTER_U('у'),
    /** Символ шрифта SYM_RU_LETTER_F. */
    SYM_RU_LETTER_F('ф'),
    /** Символ шрифта SYM_RU_LETTER_H. */
    SYM_RU_LETTER_H('х'),
    /** Символ шрифта SYM_RU_LETTER_C. */
    SYM_RU_LETTER_C('ц'),
    /** Символ шрифта SYM_RU_LETTER_CH. */
    SYM_RU_LETTER_CH('ч'),
    /** Символ шрифта SYM_RU_LETTER_SHA. */
    SYM_RU_LETTER_SHA('ш'),
    /** Символ шрифта SYM_RU_LETTER_SCHA. */
    SYM_RU_LETTER_SCHA('щ'),
    /** Символ шрифта SYM_RU_LETTER_SOFT. */
    SYM_RU_LETTER_SOFT('ь'),
    /** Символ шрифта SYM_RU_LETTER_Y. */
    SYM_RU_LETTER_Y('ы'),
    /** Символ шрифта SYM_RU_LETTER_HARD. */
    SYM_RU_LETTER_HARD('ъ'),
    /** Символ шрифта SYM_RU_LETTER_E. */
    SYM_RU_LETTER_E('э'),
    /** Символ шрифта SYM_RU_LETTER_YU. */
    SYM_RU_LETTER_YU('ю'),
    /** Символ шрифта SYM_RU_LETTER_YA. */
    SYM_RU_LETTER_YA('я'),
    /** Символ шрифта SYM_EN_LETTER_D. */
    SYM_EN_LETTER_D('d'),
    /** Символ шрифта SYM_EN_LETTER_F. */
    SYM_EN_LETTER_F('f'),
    /** Символ шрифта SYM_EN_LETTER_G. */
    SYM_EN_LETTER_G('g'),
    /** Символ шрифта SYM_EN_LETTER_I. */
    SYM_EN_LETTER_I('i'),
    /** Символ шрифта SYM_EN_LETTER_J. */
    SYM_EN_LETTER_J('j'),
    /** Символ шрифта SYM_EN_LETTER_L. */
    SYM_EN_LETTER_L('l'),
    /** Символ шрифта SYM_EN_LETTER_N. */
    SYM_EN_LETTER_N('n'),
    /** Символ шрифта SYM_EN_LETTER_Q. */
    SYM_EN_LETTER_Q('q'),
    /** Символ шрифта SYM_EN_LETTER_R. */
    SYM_EN_LETTER_R('r'),
    /** Символ шрифта SYM_EN_LETTER_S. */
    SYM_EN_LETTER_S('s'),
    /** Символ шрифта SYM_EN_LETTER_U. */
    SYM_EN_LETTER_U('u'),
    /** Символ шрифта SYM_EN_LETTER_V. */
    SYM_EN_LETTER_V('v'),
    /** Символ шрифта SYM_EN_LETTER_W. */
    SYM_EN_LETTER_W('w'),
    /** Символ шрифта SYM_EN_LETTER_X. */
    SYM_EN_LETTER_X('x'),
    /** Символ шрифта SYM_EN_LETTER_Y. */
    SYM_EN_LETTER_Y('y'),
    /** Символ шрифта SYM_EN_LETTER_Z. */
    SYM_EN_LETTER_Z('z'),
    /** Символ шрифта SYM_DIGIT_0. */
    SYM_DIGIT_0('0'),
    /** Символ шрифта SYM_DIGIT_1. */
    SYM_DIGIT_1('1'),
    /** Символ шрифта SYM_DIGIT_2. */
    SYM_DIGIT_2('2'),
    /** Символ шрифта SYM_DIGIT_3. */
    SYM_DIGIT_3('3'),
    /** Символ шрифта SYM_DIGIT_4. */
    SYM_DIGIT_4('4'),
    /** Символ шрифта SYM_DIGIT_5. */
    SYM_DIGIT_5('5'),
    /** Символ шрифта SYM_DIGIT_6. */
    SYM_DIGIT_6('6'),
    /** Символ шрифта SYM_DIGIT_7. */
    SYM_DIGIT_7('7'),
    /** Символ шрифта SYM_DIGIT_8. */
    SYM_DIGIT_8('8'),
    /** Символ шрифта SYM_DIGIT_9. */
    SYM_DIGIT_9('9'),
    /** Символ шрифта SYM_SYMBOL_COMMA. */
    SYM_SYMBOL_COMMA(','),
    /** Символ шрифта SYM_SYMBOL_DOT. */
    SYM_SYMBOL_DOT('.'),
    /** Символ шрифта SYM_SYMBOL_COLON. */
    SYM_SYMBOL_COLON(':'),
    /** Символ шрифта SYM_SYMBOL_EXCLAMATION. */
    SYM_SYMBOL_EXCLAMATION('!'),
    /** Символ шрифта SYM_SYMBOL_QUESTION. */
    SYM_SYMBOL_QUESTION('?'),
    /** Символ шрифта SYM_SYMBOL_SPACE. */
    SYM_SYMBOL_SPACE(' '),
    /** Символ шрифта SYM_SYMBOL_DASH. */
    SYM_SYMBOL_DASH('-'),
    /** Символ шрифта SYM_SYMBOL_EQUALS. */
    SYM_SYMBOL_EQUALS('='),
    /** Символ шрифта SYM_SYMBOL_ASTERISK. */
    SYM_SYMBOL_ASTERISK('*'),
    /** Символ шрифта SYM_SYMBOL_SLASH. */
    SYM_SYMBOL_SLASH('/'),
    /** Символ шрифта SYM_SYMBOL_SQ_BRACKET_L. */
    SYM_SYMBOL_SQ_BRACKET_L('['),
    /** Символ шрифта SYM_SYMBOL_SQ_BRACKET_R. */
    SYM_SYMBOL_SQ_BRACKET_R(']'),
    /** Символ шрифта SYM_SYMBOL_NEWLINE. */
    SYM_SYMBOL_NEWLINE('\n');

    /** Символы шрифта. */
    private char[] characters;

    /** Возвращает символы. */
    public char[] getCharacters() {
        return characters;
    }

    /** Создаёт экземпляр ImageType. */
    ImageType() {
    }

    /** Создаёт экземпляр ImageType. */
    ImageType(char... characters) {
        this.characters = characters;
    }
}
