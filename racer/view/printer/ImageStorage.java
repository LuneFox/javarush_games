package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;

import static com.javarush.engine.cell.Color.*;
import static com.javarush.games.racer.view.printer.ImageCreator.*;

public class ImageStorage {
    private Color[] colors;
    private final int[][] data;

    public ImageStorage(ImageType imageType) {
        switch (imageType) {
            case SYM_RU_LETTER_A:
                data = makeArray(4, "0000000001101001111110011001");
                break;
            case SYM_RU_LETTER_B:
                data = makeArray(4, "0000000011111000111010011110");
                break;
            case SYM_RU_LETTER_V:
                data = makeArray(4, "0000000011101001111010011110");
                break;
            case SYM_RU_LETTER_G:
                data = makeArray(3, "000000111100100100100");
                break;
            case SYM_RU_LETTER_D:
                data = makeArray(5, "0000000000001100101001010010101111110001");
                break;
            case SYM_RU_LETTER_YE:
                data = makeArray(4, "0000000011111000111010001111");
                break;
            case SYM_RU_LETTER_YO:
                data = makeArray(4, "1010000011111000111010001111");
                break;
            case SYM_RU_LETTER_J:
                data = makeArray(5, "00000000001010110101011101010110101");
                break;
            case SYM_RU_LETTER_Z:
                data = makeArray(4, "0000000011100001011000011110");
                break;
            case SYM_RU_LETTER_I:
                data = makeArray(4, "0000000010011001101111011001");
                break;
            case SYM_RU_LETTER_IKR:
                data = makeArray(4, "0110000010011001101111011001");
                break;
            case SYM_RU_LETTER_K:
                data = makeArray(4, "0000000010011010110010101001");
                break;
            case SYM_RU_LETTER_L:
                data = makeArray(4, "0000000000110101010101011001");
                break;
            case SYM_RU_LETTER_M:
                data = makeArray(5, "00000000001000111011101011000110001");
                break;
            case SYM_RU_LETTER_N:
                data = makeArray(4, "0000000010011001111110011001");
                break;
            case SYM_RU_LETTER_O:
                data = makeArray(4, "0000000001101001100110010110");
                break;
            case SYM_RU_LETTER_P:
                data = makeArray(4, "0000000011111001100110011001");
                break;
            case SYM_RU_LETTER_R:
                data = makeArray(4, "0000000011101001111010001000");
                break;
            case SYM_RU_LETTER_S:
                data = makeArray(4, "0000000001111000100010000111");
                break;
            case SYM_RU_LETTER_T:
                data = makeArray(3, "000000111010010010010");
                break;
            case SYM_RU_LETTER_U:
                data = makeArray(4, "0000000010011001011100011110");
                break;
            case SYM_RU_LETTER_F:
                data = makeArray(5, "00000000000111010101101010111000100");
                break;
            case SYM_RU_LETTER_H:
                data = makeArray(4, "0000000010011001011010011001");
                break;
            case SYM_RU_LETTER_C:
                data = makeArray(5, "0000000000100101001010010100101111100001");
                break;
            case SYM_RU_LETTER_CH:
                data = makeArray(4, "0000000010011001011100010001");
                break;
            case SYM_RU_LETTER_SHA:
                data = makeArray(5, "00000000001010110101101011010111111");
                break;
            case SYM_RU_LETTER_SCHA:
                data = makeArray(6, "000000000000101010101010101010101010111111000001");
                break;
            case SYM_RU_LETTER_SOFT:
                data = makeArray(4, "0000000010001000111010011110");
                break;
            case SYM_RU_LETTER_Y:
                data = makeArray(6, "000000000000100001100001111001100101111001");
                break;
            case SYM_RU_LETTER_HARD:
                data = makeArray(5, "00000000001100001000011100100101110");
                break;
            case SYM_RU_LETTER_E:
                data = makeArray(4, "0000000011100001011100011110");
                break;
            case SYM_RU_LETTER_YU:
                data = makeArray(6, "000000000000100110101001111001101001100110");
                break;
            case SYM_RU_LETTER_YA:
                data = makeArray(4, "0000000001111001011101011001");
                break;
            case SYM_EN_LETTER_D:
                data = makeArray(4, "0000000011101001100110011110");
                break;
            case SYM_EN_LETTER_F:
                data = makeArray(4, "0000000011111000111010001000");
                break;
            case SYM_EN_LETTER_G:
                data = makeArray(4, "0000000001111000101110010110");
                break;
            case SYM_EN_LETTER_I:
                data = makeArray(3, "000000111010010010111");
                break;
            case SYM_EN_LETTER_J:
                data = makeArray(4, "0000000001110010001010100100");
                break;
            case SYM_EN_LETTER_L:
                data = makeArray(3, "000000100100100100111");
                break;
            case SYM_EN_LETTER_N:
                data = makeArray(4, "0000000010011001110110111001");
                break;
            case SYM_EN_LETTER_Q:
                data = makeArray(4, "0000000001101001100110100101");
                break;
            case SYM_EN_LETTER_R:
                data = makeArray(4, "0000000011101001111010101001");
                break;
            case SYM_EN_LETTER_S:
                data = makeArray(4, "0000000001111000011000011110");
                break;
            case SYM_EN_LETTER_U:
                data = makeArray(4, "0000000010011001100110010110");
                break;
            case SYM_EN_LETTER_V:
                data = makeArray(5, "00000000001000110001100010101000100");
                break;
            case SYM_EN_LETTER_W:
                data = makeArray(5, "00000000001000110001101011101110001");
                break;
            case SYM_EN_LETTER_X:
                data = makeArray(5, "00000000001000101010001000101010001");
                break;
            case SYM_EN_LETTER_Y:
                data = makeArray(5, "00000000001000101010001000010000100");
                break;
            case SYM_EN_LETTER_Z:
                data = makeArray(4, "0000000011110001011010001111");
                break;
            case SYM_SYMBOL_DOT:
                data = makeArray(1, "0000001");
                break;
            case SYM_SYMBOL_COMMA:
                data = makeArray(2, "0000000000010110");
                break;
            case SYM_SYMBOL_COLON:
                data = makeArray(1, "0001010");
                break;
            case SYM_SYMBOL_EXCLAMATION:
                data = makeArray(1, "0011101");
                break;
            case SYM_SYMBOL_QUESTION:
                data = makeArray(4, "0000000001100001011000000100");
                break;
            case SYM_SYMBOL_SLASH:
                data = makeArray(3, "000001001010010100100");
                break;
            case SYM_SYMBOL_DASH:
                data = makeArray(2, "00000000110000");
                break;
            case SYM_SYMBOL_EQUALS:
                data = makeArray(2, "00000011001100");
                break;
            case SYM_SYMBOL_ASTERISK:
                data = makeArray(3, "000000000101010101000");
                break;
            case SYM_DIGIT_0:
                data = makeArray(3, "000000111101101101111");
                break;
            case SYM_DIGIT_1:
                data = makeArray(3, "000000110010010010111");
                break;
            case SYM_DIGIT_2:
                data = makeArray(3, "000000111001111100111");
                break;
            case SYM_DIGIT_3:
                data = makeArray(3, "000000111001111001111");
                break;
            case SYM_DIGIT_4:
                data = makeArray(3, "000000101101111001001");
                break;
            case SYM_DIGIT_5:
                data = makeArray(3, "000000111100111001111");
                break;
            case SYM_DIGIT_6:
                data = makeArray(3, "000000111100111101111");
                break;
            case SYM_DIGIT_7:
                data = makeArray(3, "000000111001001001001");
                break;
            case SYM_DIGIT_8:
                data = makeArray(3, "000000111101111101111");
                break;
            case SYM_DIGIT_9:
                data = makeArray(3, "000000111101111001111");
                break;
            case SYM_SYMBOL_SQ_BRACKET_L:
                data = makeArray(2, "0011101010101011");
                break;
            case SYM_SYMBOL_SQ_BRACKET_R:
                data = makeArray(2, "0011010101010111");
                break;
            case SYM_SYMBOL_APOSTROPHE:
                data = makeArray(1, "00110000");
                break;
            case SYM_SYMBOL_QUOTE:
                data = makeArray(3, "000000101101");
                break;
            case SYM_SYMBOL_SPACE:
                data = new int[1][3];
                break;
            default:
                data = new int[1][1];
                break;
        }
    }

    public Color[] getColors() {
        return new Color[]{NONE, WHITE};
    }

    public int[][] getData() {
        return data;
    }
}
