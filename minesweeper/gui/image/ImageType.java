package com.javarush.games.minesweeper.gui.image;

/**
 * Code names for all available graphic pieces in the game.
 * Name prefixes are important because they're used in caching elements to different maps.
 * For example, everything that starts with SYM_ goes to symbols cache.
 */

public enum ImageType {
    NONE,

    GUI_BACKGROUND,
    GUI_BUTTON,
    GUI_SURROUND_FRAME,
    GUI_POPUP_MESSAGE,
    GUI_ARROW,
    GUI_DIFFICULTY_BAR,
    GUI_SWITCH_HANDLE,
    GUI_SWITCH_RAIL,
    GUI_THEME_PALETTE,
    GUI_VICTORY_WINDOW,

    CELL_OPENED,
    CELL_CLOSED,
    CELL_DESTROYED,

    BOARD_0,
    BOARD_1,
    BOARD_2,
    BOARD_3,
    BOARD_4,
    BOARD_5,
    BOARD_6,
    BOARD_7,
    BOARD_8,
    BOARD_9,
    BOARD_FLAG,
    BOARD_MINE,
    BOARD_SHOP,
    BOARD_DICE_1,
    BOARD_DICE_2,
    BOARD_DICE_3,
    BOARD_DICE_4,
    BOARD_DICE_5,
    BOARD_DICE_6,

    SHOP_HEADER_COIN,
    SHOP_HEADER_PANEL,
    SHOP_SHOWCASE_PANEL,
    SHOP_SHOWCASE_FRAME,
    SHOP_SHOWCASE_FRAME_PRESSED,
    SHOP_SHOWCASE_SHIELD,
    SHOP_SHOWCASE_SCANNER,
    SHOP_SHOWCASE_FLAG,
    SHOP_SHOWCASE_SHOVEL,
    SHOP_SHOWCASE_DICE,
    SHOP_SHOWCASE_BOMB,

    PICTURE_MAIN_LOGO,
    PICTURE_PRIZE_CUP,
    PICTURE_YELLOW_CAT_SMILE,
    PICTURE_YELLOW_CAT_SAD,

    SYM_RU_LETTER_A('а', 'a'),
    SYM_RU_LETTER_B('б'),
    SYM_RU_LETTER_V('в', 'b'),
    SYM_RU_LETTER_G('г'),
    SYM_RU_LETTER_D('д'),
    SYM_RU_LETTER_YE('е', 'e'),
    SYM_RU_LETTER_YO('ё'),
    SYM_RU_LETTER_J('ж'),
    SYM_RU_LETTER_Z('з'),
    SYM_RU_LETTER_I('и'),
    SYM_RU_LETTER_IKR('й'),
    SYM_RU_LETTER_K('к', 'k'),
    SYM_RU_LETTER_L('л'),
    SYM_RU_LETTER_M('м', 'm'),
    SYM_RU_LETTER_N('н', 'h'),
    SYM_RU_LETTER_O('о', 'o'),
    SYM_RU_LETTER_P('п'),
    SYM_RU_LETTER_R('р', 'p'),
    SYM_RU_LETTER_S('с', 'c'),
    SYM_RU_LETTER_T('т', 't'),
    SYM_RU_LETTER_U('у'),
    SYM_RU_LETTER_F('ф'),
    SYM_RU_LETTER_H('х'),
    SYM_RU_LETTER_C('ц'),
    SYM_RU_LETTER_CH('ч'),
    SYM_RU_LETTER_SHA('ш'),
    SYM_RU_LETTER_SCHA('щ'),
    SYM_RU_LETTER_SOFT('ь'),
    SYM_RU_LETTER_Y('ы'),
    SYM_RU_LETTER_HARD('ъ'),
    SYM_RU_LETTER_E('э'),
    SYM_RU_LETTER_YU('ю'),
    SYM_RU_LETTER_YA('я'),
    SYM_EN_LETTER_D('d'),
    SYM_EN_LETTER_F('f'),
    SYM_EN_LETTER_G('g'),
    SYM_EN_LETTER_I('i'),
    SYM_EN_LETTER_J('j'),
    SYM_EN_LETTER_L('l'),
    SYM_EN_LETTER_N('n'),
    SYM_EN_LETTER_Q('q'),
    SYM_EN_LETTER_R('r'),
    SYM_EN_LETTER_S('s'),
    SYM_EN_LETTER_U('u'),
    SYM_EN_LETTER_V('v'),
    SYM_EN_LETTER_W('w'),
    SYM_EN_LETTER_X('x'),
    SYM_EN_LETTER_Y('y'),
    SYM_EN_LETTER_Z('z'),
    SYM_DIGIT_0('0'),
    SYM_DIGIT_1('1'),
    SYM_DIGIT_2('2'),
    SYM_DIGIT_3('3'),
    SYM_DIGIT_4('4'),
    SYM_DIGIT_5('5'),
    SYM_DIGIT_6('6'),
    SYM_DIGIT_7('7'),
    SYM_DIGIT_8('8'),
    SYM_DIGIT_9('9'),
    SYM_SYMBOL_COMMA(','),
    SYM_SYMBOL_DOT('.'),
    SYM_SYMBOL_COLON(':'),
    SYM_SYMBOL_EXCLAMATION('!'),
    SYM_SYMBOL_QUESTION('?'),
    SYM_SYMBOL_SPACE(' '),
    SYM_SYMBOL_DASH('-'),
    SYM_SYMBOL_EQUALS('='),
    SYM_SYMBOL_ASTERISK('*'),
    SYM_SYMBOL_SLASH('/'),
    SYM_SYMBOL_SQ_BRACKET_L('['),
    SYM_SYMBOL_SQ_BRACKET_R(']'),
    SYM_SYMBOL_NEWLINE('\n');

    private char[] characters;

    public char[] getCharacters() {
        return characters;
    }

    ImageType() {
    }

    // Constructor for symbols
    ImageType(char... characters) {
        this.characters = characters;
    }
}
