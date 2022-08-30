package com.javarush.games.minesweeper.gui.image;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Theme;

import static com.javarush.engine.cell.Color.*;
import static com.javarush.games.minesweeper.gui.image.ImageCreator.*;

/**
 * All images' body data and colors are stored here. Each image corresponds a visual element.
 * To get matrix and colors you need to create a storage with a given element.
 */

public class ImageStorage {
    private Color[] colors;
    private final int[][] data;

    public ImageStorage(ImageType imageType) {
        switch (imageType) {
            /*
             * Big images
             */
            case PICTURE_MAIN_LOGO:
                setColors(WHITE, BLACK, DARKSLATEGRAY, SANDYBROWN, RED, YELLOW);
                data = makeArray(
                        "       1111111",
                        "     1111     11",
                        "    1111        1",
                        "   1111          1                              11  11",
                        "  1111       55                                 1    1",
                        "  111       5 55",
                        " 1111        6 5",
                        " 1111       1 5        11111     11  1111        11111     1111111",
                        " 1111      1          11    1    11 111111      11    1   111     1",
                        " 1111    22222       11      1   1111    11    11      1  111      1",
                        " 1111   2222222      11      1   111      1   111      1  111      1",
                        " 1111  222223222    111      1   111      1   111      1  111      1",
                        " 1111  222222322    111      1   111      1   111     11  111      1",
                        "  111  222222322  1 111      1   111      1   111111111   111      1",
                        "  1111 222222222  1 111      1   111      1   111         111      1",
                        "   1111222222222 1  111      1   111      1   111         111     11",
                        "    11112222222 1   1111     11  111      1   111      1  111111111",
                        "     11112222211     1111   111  111      11   111    1   1111111",
                        "       1111111        111111  11  11       11   111111    141",
                        "                                                          141",
                        "                                                          141",
                        "                                                          141",
                        "   111111111                                              141",
                        "  1222222221                                             1141",
                        " 12222222221                                            11411",
                        "122222223221111111111111111111111111111111111111111111111441",
                        "122333333222222244444444444444444444444444444444444444444411",
                        "12222222222222224444444444444444444444444444444444444444111",
                        "122222222221111111111111111111111111111111111111111111111",
                        " 12222222221",
                        "  1222222221",
                        "   111111111");
                break;
            case PICTURE_YELLOW_CAT_SMILE:
                colors = values();
                data = new int[][]{
                        {1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
                        {1, 59, 59, 59, 1, 1, 59, 59, 59, 59, 1, 1, 59, 59, 59, 1},
                        {1, 59, 80, 1, 59, 59, 3, 3, 3, 3, 59, 59, 1, 80, 59, 1},
                        {1, 59, 1, 59, 3, 3, 3, 3, 3, 3, 3, 3, 59, 1, 59, 1},
                        {1, 59, 59, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 59, 59, 1},
                        {0, 1, 59, 3, 1, 3, 1, 3, 3, 1, 3, 1, 3, 59, 1, 0},
                        {0, 1, 59, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 59, 1, 0},
                        {0, 1, 126, 126, 3, 3, 3, 3, 3, 3, 3, 3, 126, 126, 1, 0},
                        {0, 1, 4, 59, 3, 3, 3, 3, 3, 3, 3, 59, 59, 4, 1, 0},
                        {0, 0, 1, 126, 59, 1, 2, 1, 1, 2, 1, 59, 126, 1, 0, 0},
                        {0, 0, 1, 4, 59, 59, 1, 53, 53, 1, 59, 59, 4, 1, 0, 0},
                        {0, 0, 0, 1, 4, 4, 59, 59, 59, 59, 4, 4, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}};
                break;
            case PICTURE_YELLOW_CAT_SAD:
                colors = values();
                data = new int[][]{
                        {1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
                        {1, 59, 59, 59, 1, 1, 59, 59, 59, 59, 1, 1, 59, 59, 59, 1},
                        {1, 59, 80, 1, 59, 59, 3, 3, 3, 3, 59, 59, 1, 80, 59, 1},
                        {1, 59, 1, 59, 3, 3, 3, 3, 3, 3, 3, 3, 59, 1, 59, 1},
                        {1, 59, 59, 3, 1, 1, 3, 3, 3, 3, 1, 1, 3, 59, 59, 1},
                        {0, 1, 59, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 59, 1, 0},
                        {0, 1, 59, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 59, 1, 0},
                        {0, 1, 126, 126, 3, 3, 3, 3, 3, 3, 3, 3, 126, 126, 1, 0},
                        {0, 1, 4, 59, 3, 3, 1, 1, 1, 1, 3, 3, 59, 4, 1, 0},
                        {0, 0, 1, 126, 59, 1, 2, 19, 19, 2, 1, 59, 126, 1, 0, 0},
                        {0, 0, 1, 4, 59, 59, 19, 19, 19, 19, 59, 59, 4, 1, 0, 0},
                        {0, 0, 0, 1, 4, 4, 59, 59, 59, 59, 4, 4, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}};
                break;
            case SHOP_SHOWCASE_SHIELD:
                colors = values();
                data = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 50, 0, 0},
                        {0, 0, 9, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 50, 50, 0, 0},
                        {0, 0, 9, 57, 122, 83, 83, 122, 83, 83, 122, 83, 83, 122, 50, 50, 0, 0},
                        {0, 0, 9, 57, 83, 83, 122, 83, 83, 122, 83, 83, 122, 83, 50, 50, 0, 0},
                        {0, 0, 9, 57, 83, 122, 83, 83, 122, 83, 83, 122, 83, 83, 50, 50, 0, 0},
                        {0, 0, 9, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 50, 50, 0, 0},
                        {0, 0, 9, 57, 34, 34, 34, 34, 34, 34, 34, 34, 34, 9, 50, 50, 0, 0},
                        {0, 0, 9, 57, 34, 34, 34, 34, 34, 34, 34, 34, 34, 9, 50, 50, 0, 0},
                        {0, 0, 9, 57, 34, 9, 50, 34, 34, 34, 34, 9, 50, 9, 50, 50, 0, 0},
                        {0, 0, 9, 57, 132, 44, 44, 50, 34, 34, 9, 44, 44, 50, 50, 50, 0, 0},
                        {0, 0, 9, 57, 132, 44, 44, 50, 34, 34, 9, 44, 44, 50, 50, 50, 0, 0},
                        {0, 0, 9, 57, 34, 9, 50, 34, 34, 34, 34, 9, 50, 9, 50, 50, 0, 0},
                        {0, 0, 9, 57, 34, 34, 34, 34, 34, 34, 34, 34, 34, 9, 50, 50, 0, 0},
                        {0, 0, 0, 57, 34, 34, 34, 34, 34, 34, 34, 34, 34, 9, 50, 0, 0, 0},
                        {0, 0, 0, 50, 57, 34, 34, 34, 34, 34, 34, 34, 9, 9, 50, 0, 0, 0},
                        {0, 0, 0, 0, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SHOP_SHOWCASE_SCANNER:
                colors = values();
                data = new int[][]{new int[18],
                        {0, 0, 0, 126, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 1, 45, 45, 45, 45, 45, 45, 45, 45, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 1, 45, 33, 33, 33, 33, 33, 33, 45, 1, 1, 0, 0, 0},
                        {0, 0, 0, 66, 1, 45, 6, 5, 6, 6, 6, 6, 45, 1, 66, 0, 0, 0},
                        {0, 0, 0, 66, 1, 45, 33, 33, 33, 33, 33, 22, 45, 1, 66, 0, 0, 0},
                        {0, 0, 0, 66, 1, 45, 6, 6, 6, 6, 22, 6, 45, 1, 66, 0, 0, 0},
                        {0, 0, 0, 66, 1, 45, 33, 33, 33, 22, 33, 33, 45, 1, 66, 0, 0, 0},
                        {0, 0, 0, 66, 1, 45, 6, 6, 6, 6, 6, 6, 45, 1, 66, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1, 45, 45, 45, 45, 45, 45, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 1, 43, 134, 1, 111, 111, 1, 134, 43, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1, 1, 1, 53, 53, 1, 1, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}};
                break;
            case SHOP_SHOWCASE_FLAG:
                setColors(BLACK, Theme.FLAG_LIGHT.getColor(), Theme.FLAG_DARK.getColor(), YELLOW);
                data = makeArray(
                        "",
                        "  11",
                        "  1122",
                        "  112222",
                        "  11222222",
                        "  1124422222",
                        "  112442222222",
                        "  11222222222233",
                        "  112222222233",
                        "  1122222233",
                        "  11222233",
                        "  112233",
                        "  1133",
                        "  11",
                        "  11",
                        "  11",
                        "  11",
                        "  11");
                break;
            case SHOP_SHOWCASE_SHOVEL:
                colors = values();
                data = new int[][]{new int[18],
                        {0, 0, 0, 0, 0, 0, 59, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 59, 3, 59, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 59, 0, 0, 0, 0, 3, 59, 59, 59, 3, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 59, 59, 4, 59, 3, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 4, 59, 59, 3, 0, 0},
                        {0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 59, 4, 3, 59, 3, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 126, 131, 59, 0, 3, 0, 0, 0, 0},
                        {0, 0, 0, 59, 0, 0, 0, 0, 126, 131, 126, 0, 0, 0, 0, 0, 3, 0},
                        {0, 0, 59, 3, 59, 0, 0, 126, 131, 126, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 59, 0, 0, 126, 131, 126, 0, 0, 59, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 131, 126, 0, 0, 59, 3, 59, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 59, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 59, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1, 0, 0, 0, 59, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SHOP_SHOWCASE_DICE:
                setColors(DARKGRAY, LIGHTGRAY, RED, BLACK);
                data = makeArray(
                        "",
                        "",
                        "        11",
                        "      112211",
                        "    1122222211",
                        "  11222422422211",
                        "  12112222221121",
                        "  12221122112221",
                        "  12422211222221",
                        "  12222221222221",
                        "  12222421223221",
                        "  12422221222221",
                        "  12222221222221",
                        "  11222421222211",
                        "    1122212211",
                        "      112111",
                        "        11");
                break;
            case SHOP_SHOWCASE_BOMB:
                setColors(BLACK, GRAY, LIGHTGRAY, RED, YELLOW);
                data = makeArray(
                        "   5 5",
                        "    5",
                        "   5 433",
                        "       1311",
                        "     11113111",
                        "    1111111111",
                        "   111211111111",
                        "   112111111111",
                        "  11111111111111",
                        "  11111111111111",
                        "  11111111111111",
                        "  11211111111111",
                        "   111111111111",
                        "   111111111111",
                        "    1111111111",
                        "     11111111",
                        "       1111");
                break;
            case SHOP_HEADER_COIN:
                setColors(YELLOW, ORANGE);
                data = makeArray("011011121112111211220220", 4);
                break;
            case GUI_BUTTON:
                setColors(Theme.BUTTON_BG.getColor(), BLACK, Theme.BUTTON_BORDER.getColor());
                data = null; // it's dynamic, depends on text size, create a frame
                break;
            case GUI_ARROW:
                setColors(Theme.LABEL.getColor(), WHITE);
                data = makeArray("11000011000011000011001100110011000", 5);
                break;
            case GUI_DIFFICULTY_BAR:
                setColors(GREEN, BLACK);
                data = makeArray("020212212212212212020", 3);
                break;
            case GUI_BACKGROUND:
                setColors(Theme.MAIN_MENU_BG.getColor(), NONE, Theme.MAIN_MENU_BORDER.getColor());
                data = createFrame(100, 100, false, true);
                break;
            case SHOP_SHOWCASE_PANEL:
                setColors(Theme.SHOP_BG.getColor(), BLACK, Theme.SHOP_BORDER.getColor());
                data = createFrame(80, 80, true, true);
                break;
            case SHOP_HEADER_PANEL:
                setColors(Theme.SHOP_HEADER_FOOTER.getColor(), BLACK, Theme.SHOP_BORDER.getColor());
                data = createFrame(80, 12, false, true);
                break;
            case SHOP_SHOWCASE_FRAME:
                setColors(Theme.SHOP_ITEM_BG.getColor(), BLACK, GREEN);
                data = createFrame(20, 20, true, true);
                break;
            case SHOP_SHOWCASE_FRAME_PRESSED:
                setColors(Theme.SHOP_ITEM_BG.getColor(), BLACK, GREEN);
                data = createFrame(20, 20, false, true);
                break;
            case GUI_VICTORY_WINDOW:
                setColors(Theme.SHOP_HEADER_FOOTER.getColor(), BLACK, Theme.BUTTON_BG.getColor());
                data = createFrame(70, 35, true, true);
                break;
            case GUI_SWITCH_HANDLE:
                setColors(RED, BLACK, Theme.LABEL.getColor());
                data = createFrame(4, 7, false, true);
                break;
            case GUI_SWITCH_RAIL:
                setColors(BLACK, NONE, NONE);
                data = createFrame(12, 3, false, false);
                break;
            case GUI_SURROUND_FRAME:
                setColors(NONE, NONE, BLUE);
                data = createFrame(100, 100, false, true);
                break;
            case GUI_THEME_PALETTE:
                setColors(GRAY, WHITE, BLACK);
                data = createFrame(10, 10, false, true);
                break;
            case GUI_POPUP_MESSAGE:
                setColors(BLACK, BLACK, BLACK);
                data = createFrame(90, 11, false, false);
                break;
            case BOARD_DICE_1:
                setColors(WHITE, RED, BLACK);
                data = makeArray("1111111011111113111111131112111311111113111111131111111303333333", 8);
                break;
            case BOARD_DICE_2:
                setColors(WHITE, BLACK, BLACK);
                data = makeArray("1111111011111213111111131111111311111113121111131111111303333333", 8);
                break;
            case BOARD_DICE_3:
                setColors(WHITE, BLACK, BLACK);
                data = makeArray("1111111011111213111111131112111311111113121111131111111303333333", 8);
                break;
            case BOARD_DICE_4:
                setColors(WHITE, BLACK, BLACK);
                data = makeArray("1111111012111213111111131111111311111113121112131111111303333333", 8);
                break;
            case BOARD_DICE_5:
                setColors(WHITE, BLACK, BLACK);
                data = makeArray("1111111012111213111111131112111311111113121112131111111303333333", 8);
                break;
            case BOARD_DICE_6:
                setColors(WHITE, BLACK, BLACK);
                data = makeArray("1111111012111213111111131211121311111113121112131111111303333333", 8);
                break;
            case BOARD_SCANNER_FRAME:
                setColors(NONE, NONE, BLUE);
                data = createFrame(29, 29, false, true);
                break;
            case BOARD_BOMB_FRAME:
                colors = values();
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 111, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 111, 111, 111, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 5, 5, 0, 111, 0, 5, 5, 0, 0, 0, 0}, {0, 0, 0, 5, 0, 0, 0, 5, 0, 0, 0, 5, 0, 0, 0}, {0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0}, {0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0}, {0, 111, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 111, 0}, {111, 111, 111, 5, 5, 5, 0, 40, 0, 5, 5, 5, 111, 111, 111}, {0, 111, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 111, 0}, {0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0}, {0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0}, {0, 0, 0, 5, 0, 0, 0, 5, 0, 0, 0, 5, 0, 0, 0}, {0, 0, 0, 0, 5, 5, 0, 5, 0, 5, 5, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 111, 111, 111, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 111, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case PICTURE_PRIZE_CUP:
                setColors(GOLD, WHITE, MAROON, KHAKI);
                data = makeArray(
                        "   111111111",
                        "  11111112112",
                        " 1 111111211 2",
                        " 1 111111211 2",
                        "  11111112111",
                        "    1111121",
                        "    1111211",
                        "     11111",
                        "       1",
                        "       1",
                        "      112",
                        "     11112",
                        "    3333333",
                        "    3444423",
                        "   333333333");
                break;

            /*
              Sprites
             */
            case BOARD_0:
                setColors(NONE);
                data = makeSprite("011010111011101110110110", 4, 3, 4);
                break;
            case BOARD_1:
                setColors(DARKGREEN);
                data = makeSprite("001001101110011001101111", 4, 3, 4);
                break;
            case BOARD_2:
                setColors(DARKBLUE);
                data = makeSprite("011010110011011011001111", 4, 3, 4);
                break;
            case BOARD_3:
                setColors(DARKRED);
                data = makeSprite("111100110110001110110110", 4, 3, 4);
                break;
            case BOARD_4:
                setColors(PURPLE);
                data = makeSprite("001101111011111100110011", 4, 3, 4);
                break;
            case BOARD_5:
                setColors(DARKSLATEGRAY);
                data = makeSprite("111111001110001110110110", 4, 3, 4);
                break;
            case BOARD_6:
                setColors(DARKSLATEBLUE);
                data = makeSprite("011111001110110111010110", 4, 3, 4);
                break;
            case BOARD_7:
                setColors(FORESTGREEN);
                data = makeSprite("111100010010011001100110", 4, 3, 4);
                break;
            case BOARD_8:
                setColors(RED);
                data = makeSprite("011011010110110111010110", 4, 3, 4);
                break;
            case BOARD_9:
                setColors(BLACK);
                data = makeSprite("011010111011011100111110", 4, 3, 4);
                break;
            case BOARD_FLAG:
                setColors(BLACK, Theme.FLAG_LIGHT.getColor(), Theme.FLAG_DARK.getColor(), YELLOW);
                data = makeSprite("122001422212233133001000010000", 5, 2, 3);
                break;
            case BOARD_MINE:
                setColors(BLACK, RED, DARKGRAY, DARKSLATEGRAY, DARKRED, DIMGRAY);
                data = makeSprite("0001000000111100013446100142541111455410016446100011110000001000", 8, 2, 2);
                break;
            case BOARD_SHOP: {
                setColors(DIMGRAY, LIGHTSKYBLUE, LIGHTBLUE, DARKSLATEGRAY);
                data = makeSprite("111111111122312321123213221132212231122414321123213221132212231122312321111111111", 9, 1, 1);
                break;
            }
            case BOARD_DESTROYED:
                setColors(NONE, Theme.CELL_LIGHT.getColor(), Theme.CELL_SHADOW.getColor(),
                        BLACK, GRAY);
                data = makeArray(
                        "2222222222",
                        "2333333333",
                        "2351115115",
                        "2315415151",
                        "2351144141",
                        "2354544151",
                        "2315151511",
                        "2315415455",
                        "2315111511",
                        "2351111511");
                break;

            /*
             * Cells
             */
            case CELL_CLOSED:
                setColors(Theme.CELL_BG_UP.getColor(), Theme.CELL_LIGHT.getColor(), Theme.CELL_SHADOW.getColor());
                data = createCell(10, 10, true);
                break;
            case CELL_OPENED:
                setColors(Theme.CELL_BG_UP.getColor(), Theme.CELL_LIGHT.getColor(), Theme.CELL_SHADOW.getColor());
                data = createCell(10, 10, false);
                break;

            /*
             * Symbols
             */
            case SYM_RU_LETTER_A:
                data = makeArray("0000000001101001111110011001", 4);
                break;
            case SYM_RU_LETTER_B:
                data = makeArray("0000000011111000111010011110", 4);
                break;
            case SYM_RU_LETTER_V:
                data = makeArray("0000000011101001111010011110", 4);
                break;
            case SYM_RU_LETTER_G:
                data = makeArray("000000111100100100100", 3);
                break;
            case SYM_RU_LETTER_D:
                data = makeArray("0000000000001100101001010010101111110001", 5);
                break;
            case SYM_RU_LETTER_YE:
                data = makeArray("0000000011111000111010001111", 4);
                break;
            case SYM_RU_LETTER_YO:
                data = makeArray("1010000011111000111010001111", 4);
                break;
            case SYM_RU_LETTER_J:
                data = makeArray("00000000001010110101011101010110101", 5);
                break;
            case SYM_RU_LETTER_Z:
                data = makeArray("0000000011100001011000011110", 4);
                break;
            case SYM_RU_LETTER_I:
                data = makeArray("0000000010011001101111011001", 4);
                break;
            case SYM_RU_LETTER_IKR:
                data = makeArray("0110000010011001101111011001", 4);
                break;
            case SYM_RU_LETTER_K:
                data = makeArray("0000000010011010110010101001", 4);
                break;
            case SYM_RU_LETTER_L:
                data = makeArray("0000000000110101010101011001", 4);
                break;
            case SYM_RU_LETTER_M:
                data = makeArray("00000000001000111011101011000110001", 5);
                break;
            case SYM_RU_LETTER_N:
                data = makeArray("0000000010011001111110011001", 4);
                break;
            case SYM_RU_LETTER_O:
                data = makeArray("0000000001101001100110010110", 4);
                break;
            case SYM_RU_LETTER_P:
                data = makeArray("0000000011111001100110011001", 4);
                break;
            case SYM_RU_LETTER_R:
                data = makeArray("0000000011101001111010001000", 4);
                break;
            case SYM_RU_LETTER_S:
                data = makeArray("0000000001111000100010000111", 4);
                break;
            case SYM_RU_LETTER_T:
                data = makeArray("000000111010010010010", 3);
                break;
            case SYM_RU_LETTER_U:
                data = makeArray("0000000010011001011100011110", 4);
                break;
            case SYM_RU_LETTER_F:
                data = makeArray("00000000000111010101101010111000100", 5);
                break;
            case SYM_RU_LETTER_H:
                data = makeArray("0000000010011001011010011001", 4);
                break;
            case SYM_RU_LETTER_C:
                data = makeArray("0000000000100101001010010100101111100001", 5);
                break;
            case SYM_RU_LETTER_CH:
                data = makeArray("0000000010011001011100010001", 4);
                break;
            case SYM_RU_LETTER_SHA:
                data = makeArray("00000000001010110101101011010111111", 5);
                break;
            case SYM_RU_LETTER_SCHA:
                data = makeArray("000000000000101010101010101010101010111111000001", 6);
                break;
            case SYM_RU_LETTER_SOFT:
                data = makeArray("0000000010001000111010011110", 4);
                break;
            case SYM_RU_LETTER_Y:
                data = makeArray("000000000000100001100001111001100101111001", 6);
                break;
            case SYM_RU_LETTER_HARD:
                data = makeArray("00000000001100001000011100100101110", 5);
                break;
            case SYM_RU_LETTER_E:
                data = makeArray("0000000011100001011100011110", 4);
                break;
            case SYM_RU_LETTER_YU:
                data = makeArray("000000000000100110101001111001101001100110", 6);
                break;
            case SYM_RU_LETTER_YA:
                data = makeArray("0000000001111001011101011001", 4);
                break;
            case SYM_EN_LETTER_D:
                data = makeArray("0000000011101001100110011110", 4);
                break;
            case SYM_EN_LETTER_F:
                data = makeArray("0000000011111000111010001000", 4);
                break;
            case SYM_EN_LETTER_G:
                data = makeArray("0000000001111000101110010110", 4);
                break;
            case SYM_EN_LETTER_I:
                data = makeArray("000000111010010010111", 3);
                break;
            case SYM_EN_LETTER_J:
                data = makeArray("0000000001110010001010100100", 4);
                break;
            case SYM_EN_LETTER_L:
                data = makeArray("000000100100100100111", 3);
                break;
            case SYM_EN_LETTER_N:
                data = makeArray("0000000010011001110110111001", 4);
                break;
            case SYM_EN_LETTER_Q:
                data = makeArray("0000000001101001100110100101", 4);
                break;
            case SYM_EN_LETTER_R:
                data = makeArray("0000000011101001111010101001", 4);
                break;
            case SYM_EN_LETTER_S:
                data = makeArray("0000000001111000011000011110", 4);
                break;
            case SYM_EN_LETTER_U:
                data = makeArray("0000000010011001100110010110", 4);
                break;
            case SYM_EN_LETTER_V:
                data = makeArray("00000000001000110001100010101000100", 5);
                break;
            case SYM_EN_LETTER_W:
                data = makeArray("00000000001000110001101011101110001", 5);
                break;
            case SYM_EN_LETTER_X:
                data = makeArray("00000000001000101010001000101010001", 5);
                break;
            case SYM_EN_LETTER_Y:
                data = makeArray("00000000001000101010001000010000100", 5);
                break;
            case SYM_EN_LETTER_Z:
                data = makeArray("0000000011110001011010001111", 4);
                break;
            case SYM_SYMBOL_DOT:
                data = makeArray("0000001", 1);
                break;
            case SYM_SYMBOL_COMMA:
                data = makeArray("0000000000010110", 2);
                break;
            case SYM_SYMBOL_COLON:
                data = makeArray("0001010", 1);
                break;
            case SYM_SYMBOL_EXCLAMATION:
                data = makeArray("0011101", 1);
                break;
            case SYM_SYMBOL_QUESTION:
                data = makeArray("0000000001100001011000000100", 4);
                break;
            case SYM_SYMBOL_SLASH:
                data = makeArray("000001001010010100100", 3);
                break;
            case SYM_SYMBOL_DASH:
                data = makeArray("00000000110000", 2);
                break;
            case SYM_SYMBOL_EQUALS:
                data = makeArray("00000011001100", 2);
                break;
            case SYM_SYMBOL_ASTERISK:
                data = makeArray("000000000101010101000", 3);
                break;
            case SYM_DIGIT_0:
                data = makeArray("000000111101101101111", 3);
                break;
            case SYM_DIGIT_1:
                data = makeArray("000000110010010010111", 3);
                break;
            case SYM_DIGIT_2:
                data = makeArray("000000111001111100111", 3);
                break;
            case SYM_DIGIT_3:
                data = makeArray("000000111001111001111", 3);
                break;
            case SYM_DIGIT_4:
                data = makeArray("000000101101111001001", 3);
                break;
            case SYM_DIGIT_5:
                data = makeArray("000000111100111001111", 3);
                break;
            case SYM_DIGIT_6:
                data = makeArray("000000111100111101111", 3);
                break;
            case SYM_DIGIT_7:
                data = makeArray("000000111001001001001", 3);
                break;
            case SYM_DIGIT_8:
                data = makeArray("000000111101111101111", 3);
                break;
            case SYM_DIGIT_9:
                data = makeArray("000000111101111001111", 3);
                break;
            case SYM_SYMBOL_SQ_BRACKET_L:
                data = makeArray("0011101010101011", 2);
                break;
            case SYM_SYMBOL_SQ_BRACKET_R:
                data = makeArray("0011010101010111", 2);
                break;
            case SYM_SYMBOL_SPACE:
                data = new int[1][3];
                break;
            case NONE:
            default:
                setColors(WHITE);
                data = new int[1][1];
                break;
        }
    }

    private void setColors(Color... colors) {
        this.colors = new Color[colors.length + 1];
        this.colors[0] = NONE;
        System.arraycopy(colors, 0, this.colors, 1, colors.length);
    }

    public Color[] getColors() {
        return colors == null ? new Color[]{NONE, WHITE} : colors;
    }

    public int[][] getData() {
        return data;
    }
}
