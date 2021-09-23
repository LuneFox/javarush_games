package com.javarush.games.minesweeper.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Code names for all available graphic pieces in the game.
 */

public enum Bitmap {
    NONE,

    CELL_OPENED,
    CELL_CLOSED,
    CELL_DESTROYED,

    SPR_BOARD_1,
    SPR_BOARD_2,
    SPR_BOARD_3,
    SPR_BOARD_4,
    SPR_BOARD_5,
    SPR_BOARD_6,
    SPR_BOARD_7,
    SPR_BOARD_8,
    SPR_BOARD_9,
    SPR_BOARD_0,
    SPR_BOARD_NONE,
    SPR_BOARD_FLAG,
    SPR_BOARD_MINE,

    PIC_LOGO,
    PIC_FACE_HAPPY,
    PIC_FACE_SAD,

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
    WIN_SHOP,
    WIN_VICTORY,
    WIN_GAME_OVER,
    WIN_SHOP_HEADER_FOOTER,
    WIN_BOARD_TRANSPARENT_FRAME,

    BUTTON_OK,
    BUTTON_CLOSE,

    SYM_RU_LETTER_A(new char[]{'а', 'a'}),
    SYM_RU_LETTER_B(new char[]{'б'}),
    SYM_RU_LETTER_V(new char[]{'в', 'b'}),
    SYM_RU_LETTER_G(new char[]{'г'}),
    SYM_RU_LETTER_D(new char[]{'д'}),
    SYM_RU_LETTER_YE(new char[]{'е', 'e'}),
    SYM_RU_LETTER_YO(new char[]{'ё'}),
    SYM_RU_LETTER_J(new char[]{'ж'}),
    SYM_RU_LETTER_Z(new char[]{'з'}),
    SYM_RU_LETTER_I(new char[]{'и'}),
    SYM_RU_LETTER_IKR(new char[]{'й'}),
    SYM_RU_LETTER_K(new char[]{'к', 'k'}),
    SYM_RU_LETTER_L(new char[]{'л'}),
    SYM_RU_LETTER_M(new char[]{'м', 'm'}),
    SYM_RU_LETTER_N(new char[]{'н', 'h'}),
    SYM_RU_LETTER_O(new char[]{'о', 'o'}),
    SYM_RU_LETTER_P(new char[]{'п'}),
    SYM_RU_LETTER_R(new char[]{'р', 'p'}),
    SYM_RU_LETTER_S(new char[]{'с', 'c'}),
    SYM_RU_LETTER_T(new char[]{'т', 't'}),
    SYM_RU_LETTER_U(new char[]{'у'}),
    SYM_RU_LETTER_F(new char[]{'ф'}),
    SYM_RU_LETTER_H(new char[]{'х'}),
    SYM_RU_LETTER_C(new char[]{'ц'}),
    SYM_RU_LETTER_CH(new char[]{'ч'}),
    SYM_RU_LETTER_SHA(new char[]{'ш'}),
    SYM_RU_LETTER_SCHA(new char[]{'щ'}),
    SYM_RU_LETTER_SOFT(new char[]{'ь'}),
    SYM_RU_LETTER_Y(new char[]{'ы'}),
    SYM_RU_LETTER_HARD(new char[]{'ъ'}),
    SYM_RU_LETTER_E(new char[]{'э'}),
    SYM_RU_LETTER_YU(new char[]{'ю'}),
    SYM_RU_LETTER_YA(new char[]{'я'}),
    SYM_EN_LETTER_D(new char[]{'d'}),
    SYM_EN_LETTER_F(new char[]{'f'}),
    SYM_EN_LETTER_G(new char[]{'g'}),
    SYM_EN_LETTER_I(new char[]{'i'}),
    SYM_EN_LETTER_J(new char[]{'j'}),
    SYM_EN_LETTER_L(new char[]{'l'}),
    SYM_EN_LETTER_N(new char[]{'n'}),
    SYM_EN_LETTER_Q(new char[]{'q'}),
    SYM_EN_LETTER_R(new char[]{'r'}),
    SYM_EN_LETTER_S(new char[]{'s'}),
    SYM_EN_LETTER_U(new char[]{'u'}),
    SYM_EN_LETTER_V(new char[]{'v'}),
    SYM_EN_LETTER_W(new char[]{'w'}),
    SYM_EN_LETTER_X(new char[]{'x'}),
    SYM_EN_LETTER_Y(new char[]{'y'}),
    SYM_EN_LETTER_Z(new char[]{'z'}),
    SYM_DIGIT_0(new char[]{'0'}),
    SYM_DIGIT_1(new char[]{'1'}),
    SYM_DIGIT_2(new char[]{'2'}),
    SYM_DIGIT_3(new char[]{'3'}),
    SYM_DIGIT_4(new char[]{'4'}),
    SYM_DIGIT_5(new char[]{'5'}),
    SYM_DIGIT_6(new char[]{'6'}),
    SYM_DIGIT_7(new char[]{'7'}),
    SYM_DIGIT_8(new char[]{'8'}),
    SYM_DIGIT_9(new char[]{'9'}),
    SYM_SYMBOL_COMMA(new char[]{','}),
    SYM_SYMBOL_DOT(new char[]{'.'}),
    SYM_SYMBOL_COLON(new char[]{':'}),
    SYM_SYMBOL_EXCLAMATION(new char[]{'!'}),
    SYM_SYMBOL_QUESTION(new char[]{'?'}),
    SYM_SYMBOL_SPACE(new char[]{' '}),
    SYM_SYMBOL_DASH(new char[]{'-'}),
    SYM_SYMBOL_EQUALS(new char[]{'='}),
    SYM_SYMBOL_ASTERISK(new char[]{'*'}),
    SYM_SYMBOL_SLASH(new char[]{'/'}),
    SYM_SYMBOL_NEWLINE(new char[]{'\n'});

    char[] characters;

    public static List<Bitmap> getBitmapsByPrefixes(String... prefixes) {
        ArrayList<Bitmap> result = new ArrayList<>();
        for (Bitmap bitmap : Bitmap.values()) {
            for (String prefix : prefixes) {
                if (bitmap.name().startsWith(prefix)) result.add(bitmap);
            }
        }
        return result;
    }

    Bitmap() {

    }

    Bitmap(char[] characters) {
        this.characters = characters;
    }
}
