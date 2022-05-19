package com.javarush.games.racer.view.printer;

public class SymbolDataStorage {
    public static final int CHAR_AND_INT_DIFFERENCE = 48;

    public static int[][] getData(Symbol symbol) {
        switch (symbol) {
            case SYM_RU_LETTER_A:
                return makeArray(4, "0000000001101001111110011001");
            case SYM_RU_LETTER_B:
                return makeArray(4, "0000000011111000111010011110");
            case SYM_RU_LETTER_V:
                return makeArray(4, "0000000011101001111010011110");
            case SYM_RU_LETTER_G:
                return makeArray(3, "000000111100100100100");
            case SYM_RU_LETTER_D:
                return makeArray(5, "0000000000001100101001010010101111110001");
            case SYM_RU_LETTER_YE:
                return makeArray(4, "0000000011111000111010001111");
            case SYM_RU_LETTER_YO:
                return makeArray(4, "1010000011111000111010001111");
            case SYM_RU_LETTER_J:
                return makeArray(5, "00000000001010110101011101010110101");
            case SYM_RU_LETTER_Z:
                return makeArray(4, "0000000011100001011000011110");
            case SYM_RU_LETTER_I:
                return makeArray(4, "0000000010011001101111011001");
            case SYM_RU_LETTER_IKR:
                return makeArray(4, "0110000010011001101111011001");
            case SYM_RU_LETTER_K:
                return makeArray(4, "0000000010011010110010101001");
            case SYM_RU_LETTER_L:
                return makeArray(4, "0000000000110101010101011001");
            case SYM_RU_LETTER_M:
                return makeArray(5, "00000000001000111011101011000110001");
            case SYM_RU_LETTER_N:
                return makeArray(4, "0000000010011001111110011001");
            case SYM_RU_LETTER_O:
                return makeArray(4, "0000000001101001100110010110");
            case SYM_RU_LETTER_P:
                return makeArray(4, "0000000011111001100110011001");
            case SYM_RU_LETTER_R:
                return makeArray(4, "0000000011101001111010001000");
            case SYM_RU_LETTER_S:
                return makeArray(4, "0000000001111000100010000111");
            case SYM_RU_LETTER_T:
                return makeArray(3, "000000111010010010010");
            case SYM_RU_LETTER_U:
                return makeArray(4, "0000000010011001011100011110");
            case SYM_RU_LETTER_F:
                return makeArray(5, "00000000000111010101101010111000100");
            case SYM_RU_LETTER_H:
                return makeArray(4, "0000000010011001011010011001");
            case SYM_RU_LETTER_C:
                return makeArray(5, "0000000000100101001010010100101111100001");
            case SYM_RU_LETTER_CH:
                return makeArray(4, "0000000010011001011100010001");
            case SYM_RU_LETTER_SHA:
                return makeArray(5, "00000000001010110101101011010111111");
            case SYM_RU_LETTER_SCHA:
                return makeArray(6, "000000000000101010101010101010101010111111000001");
            case SYM_RU_LETTER_SOFT:
                return makeArray(4, "0000000010001000111010011110");
            case SYM_RU_LETTER_Y:
                return makeArray(6, "000000000000100001100001111001100101111001");
            case SYM_RU_LETTER_HARD:
                return makeArray(5, "00000000001100001000011100100101110");
            case SYM_RU_LETTER_E:
                return makeArray(4, "0000000011100001011100011110");
            case SYM_RU_LETTER_YU:
                return makeArray(6, "000000000000100110101001111001101001100110");
            case SYM_RU_LETTER_YA:
                return makeArray(4, "0000000001111001011101011001");
            case SYM_EN_LETTER_D:
                return makeArray(4, "0000000011101001100110011110");
            case SYM_EN_LETTER_F:
                return makeArray(4, "0000000011111000111010001000");
            case SYM_EN_LETTER_G:
                return makeArray(4, "0000000001111000101110010110");
            case SYM_EN_LETTER_I:
                return makeArray(3, "000000111010010010111");
            case SYM_EN_LETTER_J:
                return makeArray(4, "0000000001110010001010100100");
            case SYM_EN_LETTER_L:
                return makeArray(3, "000000100100100100111");
            case SYM_EN_LETTER_N:
                return makeArray(4, "0000000010011001110110111001");
            case SYM_EN_LETTER_Q:
                return makeArray(4, "0000000001101001100110100101");
            case SYM_EN_LETTER_R:
                return makeArray(4, "0000000011101001111010101001");
            case SYM_EN_LETTER_S:
                return makeArray(4, "0000000001111000011000011110");
            case SYM_EN_LETTER_U:
                return makeArray(4, "0000000010011001100110010110");
            case SYM_EN_LETTER_V:
                return makeArray(5, "00000000001000110001100010101000100");
            case SYM_EN_LETTER_W:
                return makeArray(5, "00000000001000110001101011101110001");
            case SYM_EN_LETTER_X:
                return makeArray(5, "00000000001000101010001000101010001");
            case SYM_EN_LETTER_Y:
                return makeArray(5, "00000000001000101010001000010000100");
            case SYM_EN_LETTER_Z:
                return makeArray(4, "0000000011110001011010001111");
            case SYM_SYMBOL_DOT:
                return makeArray(1, "0000001");
            case SYM_SYMBOL_COMMA:
                return makeArray(2, "0000000000010110");
            case SYM_SYMBOL_COLON:
                return makeArray(1, "0001010");
            case SYM_SYMBOL_EXCLAMATION:
                return makeArray(1, "0011101");
            case SYM_SYMBOL_QUESTION:
                return makeArray(4, "0000000001100001011000000100");
            case SYM_SYMBOL_SLASH:
                return makeArray(3, "000001001010010100100");
            case SYM_SYMBOL_DASH:
                return makeArray(2, "00000000110000");
            case SYM_SYMBOL_EQUALS:
                return makeArray(2, "00000011001100");
            case SYM_SYMBOL_ASTERISK:
                return makeArray(3, "000000000101010101000");
            case SYM_DIGIT_0:
                return makeArray(3, "000000111101101101111");
            case SYM_DIGIT_1:
                return makeArray(3, "000000110010010010111");
            case SYM_DIGIT_2:
                return makeArray(3, "000000111001111100111");
            case SYM_DIGIT_3:
                return makeArray(3, "000000111001111001111");
            case SYM_DIGIT_4:
                return makeArray(3, "000000101101111001001");
            case SYM_DIGIT_5:
                return makeArray(3, "000000111100111001111");
            case SYM_DIGIT_6:
                return makeArray(3, "000000111100111101111");
            case SYM_DIGIT_7:
                return makeArray(3, "000000111001001001001");
            case SYM_DIGIT_8:
                return makeArray(3, "000000111101111101111");
            case SYM_DIGIT_9:
                return makeArray(3, "000000111101111001111");
            case SYM_SYMBOL_SQ_BRACKET_L:
                return makeArray(2, "0011101010101011");
            case SYM_SYMBOL_SQ_BRACKET_R:
                return makeArray(2, "0011010101010111");
            case SYM_SYMBOL_APOSTROPHE:
                return makeArray(1, "00110000");
            case SYM_SYMBOL_QUOTE:
                return makeArray(3, "000000101101");
            case SYM_SYMBOL_SPACE:
                return makeArray(1, "000");
            default:
                return new int[1][1];

        }
    }

    public static int[][] makeArray(int width, String string) {
        int height = string.length() / width;
        int[][] result = new int[height][width];
        char[] symbols = string.toCharArray();
        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = symbols[index++] - CHAR_AND_INT_DIFFERENCE;
            }
        }

        return result;
    }
}
