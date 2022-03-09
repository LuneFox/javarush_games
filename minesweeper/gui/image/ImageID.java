package com.javarush.games.minesweeper.gui.image;

/**
 * Code names for all available graphic pieces in the game.
 * Name prefixes are important because they're used in caching elements to different maps.
 * For example, everything that starts with SYM_ goes to symbols cache.
 */

public enum ImageID {
    NONE,

    CELL_OPENED,
    CELL_CLOSED,
    CELL_DESTROYED,

    SPR_BOARD_0,
    SPR_BOARD_1,
    SPR_BOARD_2,
    SPR_BOARD_3,
    SPR_BOARD_4,
    SPR_BOARD_5,
    SPR_BOARD_6,
    SPR_BOARD_7,
    SPR_BOARD_8,
    SPR_BOARD_9,
    SPR_BOARD_FLAG,
    SPR_BOARD_MINE,

    FLO_LOGO,
    FACE_HAPPY,
    FACE_SAD,

    MENU_ARROW,
    MENU_DIFFICULTY_BAR,
    MENU_SWITCH,
    MENU_SWITCH_RAIL,
    MENU_BUTTON,
    MENU_CUP,
    MENU_THEME_PALETTE,

    SHOP_ITEM_FRAME,
    SHOP_ITEM_FRAME_PRESSED,
    SHOP_ITEM_SHIELD,
    SHOP_ITEM_SCANNER,
    SHOP_ITEM_FLAG,
    SHOP_ITEM_SHOVEL,
    SHOP_ITEM_DICE,
    SHOP_ITEM_BOMB,
    SHOP_COIN,
    SHOP_DICE_1,
    SHOP_DICE_2,
    SHOP_DICE_3,
    SHOP_DICE_4,
    SHOP_DICE_5,
    SHOP_DICE_6,

    WIN_MENU,
    WIN_SHOP_SHOWCASE,
    WIN_MESSAGE,
    WIN_VICTORY,
    WIN_GAME_OVER,
    WIN_SHOP_HEADER_FOOTER,
    WIN_BOARD_TRANSPARENT_FRAME,

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

    ImageID() {
    }

    // Constructor for symbols
    ImageID(char... characters) {
        this.characters = characters;
    }
}
