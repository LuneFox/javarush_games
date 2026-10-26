package com.javarush.games.racer.view.printer;

/**
 * Glyphs of the hand-made pixel font. Each constant owns a bitmap (rows of indices into
 * {@code Color.values()}, where 0 is transparent) and one or more characters that select it, so
 * Russian letters and their Latin lookalikes share the same glyph.
 */
enum Symbol {
    RUS_A(makeArray(4, "0000000001101001111110011001"), 'а', 'a'),
    RUS_B(makeArray(4, "0000000011111000111010011110"), 'б'),
    RUS_V(makeArray(4, "0000000011101001111010011110"), 'в', 'b'),
    RUS_G(makeArray(3, "000000111100100100100"), 'г'),
    RUS_D(makeArray(5, "0000000000001100101001010010101111110001"), 'д'),
    RUS_YE(makeArray(4, "0000000011111000111010001111"), 'е', 'e'),
    RUS_YO(makeArray(4, "1010000011111000111010001111"), 'ё'),
    RUS_J(makeArray(5, "00000000001010110101011101010110101"), 'ж'),
    RUS_Z(makeArray(4, "0000000011100001011000011110"), 'з'),
    RUS_I(makeArray(4, "0000000010011001101111011001"), 'и'),
    RUS_IKR(makeArray(4, "0110000010011001101111011001"), 'й'),
    RUS_K(makeArray(4, "0000000010011010110010101001"), 'к', 'k'),
    RUS_L(makeArray(4, "0000000000110101010101011001"), 'л'),
    RUS_M(makeArray(5, "00000000001000111011101011000110001"), 'м', 'm'),
    RUS_N(makeArray(4, "0000000010011001111110011001"), 'н', 'h'),
    RUS_O(makeArray(4, "0000000001101001100110010110"), 'о', 'o'),
    RUS_P(makeArray(4, "0000000011111001100110011001"), 'п'),
    RUS_R(makeArray(4, "0000000011101001111010001000"), 'р', 'p'),
    RUS_S(makeArray(4, "0000000001111000100010000111"), 'с', 'c'),
    RUS_T(makeArray(3, "000000111010010010010"), 'т', 't'),
    RUS_U(makeArray(4, "0000000010011001011100011110"), 'у'),
    RUS_F(makeArray(5, "00000000000111010101101010111000100"), 'ф'),
    RUS_H(makeArray(4, "0000000010011001011010011001"), 'х'),
    RUS_C(makeArray(5, "0000000000100101001010010100101111100001"), 'ц'),
    RUS_CH(makeArray(4, "0000000010011001011100010001"), 'ч'),
    RUS_SHA(makeArray(5, "00000000001010110101101011010111111"), 'ш'),
    RUS_SCHA(makeArray(6, "000000000000101010101010101010101010111111000001"), 'щ'),
    RUS_SOFT(makeArray(4, "0000000010001000111010011110"), 'ь'),
    RUS_Y(makeArray(6, "000000000000100001100001111001100101111001"), 'ы'),
    RUS_HARD(makeArray(5, "00000000001100001000011100100101110"), 'ъ'),
    RUS_E(makeArray(4, "0000000011100001011100011110"), 'э'),
    RUS_YU(makeArray(6, "000000000000100110101001111001101001100110"), 'ю'),
    RUS_YA(makeArray(4, "0000000001111001011101011001"), 'я'),

    ENG_D(makeArray(4, "0000000011101001100110011110"), 'd'),
    ENG_F(makeArray(4, "0000000011111000111010001000"), 'f'),
    ENG_G(makeArray(4, "0000000001111000101110010110"), 'g'),
    ENG_I(makeArray(3, "000000111010010010111"), 'i'),
    ENG_J(makeArray(4, "0000000001110010001010100100"), 'j'),
    ENG_L(makeArray(3, "000000100100100100111"), 'l'),
    ENG_N(makeArray(4, "0000000010011001110110111001"), 'n'),
    ENG_Q(makeArray(4, "0000000001101001100110100101"), 'q'),
    ENG_R(makeArray(4, "0000000011101001111010101001"), 'r'),
    ENG_S(makeArray(4, "0000000001111000011000011110"), 's'),
    ENG_U(makeArray(4, "0000000010011001100110010110"), 'u'),
    ENG_V(makeArray(5, "00000000001000110001100010101000100"), 'v'),
    ENG_W(makeArray(5, "00000000001000110001101011101110001"), 'w'),
    ENG_X(makeArray(5, "00000000001000101010001000101010001"), 'x'),
    ENG_Y(makeArray(5, "00000000001000101010001000010000100"), 'y'),
    ENG_Z(makeArray(4, "0000000011110001011010001111"), 'z'),

    DIG_0(makeArray(3, "000000111101101101111"), '0'),
    DIG_1(makeArray(3, "000000110010010010111"), '1'),
    DIG_2(makeArray(3, "000000111001111100111"), '2'),
    DIG_3(makeArray(3, "000000111001111001111"), '3'),
    DIG_4(makeArray(3, "000000101101111001001"), '4'),
    DIG_5(makeArray(3, "000000111100111001111"), '5'),
    DIG_6(makeArray(3, "000000111100111101111"), '6'),
    DIG_7(makeArray(3, "000000111001001001001"), '7'),
    DIG_8(makeArray(3, "000000111101111101111"), '8'),
    DIG_9(makeArray(3, "000000111101111001111"), '9'),

    SYM_COMMA(makeArray(2, "0000000000010110"), ','),
    SYM_DOT(makeArray(1, "0000001"), '.'),
    SYM_COLON(makeArray(1, "0001010"), ':'),
    SYM_EXCLAMATION(makeArray(1, "0011101"), '!'),
    SYM_QUESTION(makeArray(4, "0000000001100001011000000100"), '?'),
    SYM_SPACE(makeArray(1, "000"), ' '),
    SYM_DASH(makeArray(2, "00000000110000"), '-'),
    SYM_EQUALS(makeArray(2, "00000011001100"), '='),
    SYM_ASTERISK(makeArray(3, "000000000101010101000"), '*'),
    SYM_SLASH(makeArray(3, "000001001010010100100"), '/'),
    SYM_SQ_BRACKET_L(makeArray(2, "0011101010101011"), '['),
    SYM_SQ_BRACKET_R(makeArray(2, "0011010101010111"), ']'),
    SYM_NEWLINE(makeArray(1, "0"), '\n'),
    SYM_APOSTROPHE(makeArray(1, "00110000"), '\''),
    SYM_QUOTE(makeArray(3, "000000101101"), '\"');

    private final char[] chars;
    private final int[][] matrix;

    /**
     * Stores the bitmap and the characters that select this glyph.
     *
     * @param matrix pixel rows, 0 for transparent and 1 for the drawn colour
     * @param chars characters that resolve to this glyph; the first is usually the Russian letter
     */
    Symbol(int[][] matrix, char... chars) {
        this.matrix = matrix;
        this.chars = chars;
    }

    /**
     * Decodes a flat string of decimal digits into a row-major glyph matrix, one digit per pixel.
     * The height is inferred as {@code string.length() / width}, and exactly {@code width * height}
     * digits are read, so trailing digits of a string whose length is not a multiple of the width
     * are silently ignored. No validation is done: a string shorter than the width yields an empty
     * matrix, which later breaks width lookups.
     *
     * @param width number of columns
     * @param string digits in row-major order, each in the range 0-9
     * @return decoded matrix of {@code string.length() / width} rows and {@code width} columns
     */
    private static int[][] makeArray(int width, String string) {
        final int CHAR_AND_INT_DIFFERENCE = 48;
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

    /**
     * Returns the characters that map to this glyph.
     *
     * @return the backing character array, not a copy
     */
    char[] getChars() {
        return chars;
    }

    /**
     * Returns the glyph bitmap.
     *
     * @return the backing matrix, not a copy, with the first index as row
     */
    int[][] getMatrix() {
        return matrix;
    }
}
