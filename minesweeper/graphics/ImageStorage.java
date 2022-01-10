package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;

/**
 * All images' body data and colors are stored here. Each image corresponds a visual element.
 */

public class ImageStorage {
    private Color[] colors;
    private final int[][] data;

    public ImageStorage(VisualElement visualElement) {
        switch (visualElement) {

            // BIG PICTURES

            case PIC_LOGO:
                setColors(Color.WHITE, Color.BLACK, Color.DARKSLATEGRAY, Color.SANDYBROWN, Color.RED, Color.YELLOW);
                data = ImageCreator.makeArray(
                        "00000001111111000000000000000000000000000000000000000000000000000000",
                        "00000111100000110000000000000000000000000000000000000000000000000000",
                        "00001111000000001000000000000000000000000000000000000000000000000000",
                        "00011110000000000100000000000000000000000000000011001100000000000000",
                        "00111100000005500000000000000000000000000000000010000100000000000000",
                        "00111000000050550000000000000000000000000000000000000000000000000000",
                        "01111000000006050000000000000000000000000000000000000000000000000000",
                        "01111000000010500000000111110000011001111000000001111100000111111100",
                        "01111000000100000000001100001000011011111100000011000010001110000010",
                        "01111000022222000000011000000100011110000110000110000001001110000001",
                        "01111000222222200000011000000100011100000010001110000001001110000001",
                        "01111002222232220000111000000100011100000010001110000001001110000001",
                        "01111002222223220000111000000100011100000010001110000011001110000001",
                        "00111002222223220010111000000100011100000010001111111110001110000001",
                        "00111102222222220010111000000100011100000010001110000000001110000001",
                        "00011112222222220100111000000100011100000010001110000000001110000011",
                        "00001111222222201000111100000110011100000010001110000001001111111110",
                        "00000111122222110000011110001110011100000011000111000010001111111000",
                        "00000001111111000000001111110011001100000001100011111100001410000000",
                        "00000000000000000000000000000000000000000000000000000000001410000000",
                        "00000000000000000000000000000000000000000000000000000000001410000000",
                        "00000000000000000000000000000000000000000000000000000000001410000000",
                        "00011111111100000000000000000000000000000000000000000000001410000000",
                        "00122222222100000000000000000000000000000000000000000000011410000000",
                        "01222222222100000000000000000000000000000000000000000000114110000000",
                        "12222222322111111111111111111111111111111111111111111111144100000000",
                        "12233333322222224444444444444444444444444444444444444444441100000000",
                        "12222222222222224444444444444444444444444444444444444444111000000000",
                        "12222222222111111111111111111111111111111111111111111111100000000000",
                        "01222222222100000000000000000000000000000000000000000000000000000000",
                        "00122222222100000000000000000000000000000000000000000000000000000000",
                        "00011111111100000000000000000000000000000000000000000000000000000000");
                break;
            case PIC_FACE_HAPPY:
                colors = Color.values();
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
            case PIC_FACE_SAD:
                colors = Color.values();
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
            case SHOP_ITEM_SHIELD:
                colors = Color.values();
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
            case SHOP_ITEM_SCANNER:
                colors = Color.values();
                data = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
                        {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SHOP_ITEM_FLAG:
                setColors(Color.BLACK, Color.RED, Color.DARKRED, Color.YELLOW);
                data = ImageCreator.makeArray(
                        "000000000000000000",
                        "001100000000000000",
                        "001122000000000000",
                        "001122220000000000",
                        "001122222200000000",
                        "001124422222000000",
                        "001124422222220000",
                        "001122222222223300",
                        "001122222222330000",
                        "001122222233000000",
                        "001122223300000000",
                        "001122330000000000",
                        "001133000000000000",
                        "001100000000000000",
                        "001100000000000000",
                        "001100000000000000",
                        "001100000000000000",
                        "001100000000000000");
                break;
            case SHOP_ITEM_SHOVEL:
                colors = Color.values();
                data = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
                        {0, 0, 1, 1, 1, 0, 0, 0, 59, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SHOP_ITEM_DICE:
                setColors(Color.DARKGRAY, Color.LIGHTGRAY, Color.RED, Color.BLACK);
                data = ImageCreator.makeArray(
                        "000000000000000000",
                        "000000000000000000",
                        "000000001100000000",
                        "000000112211000000",
                        "000011222222110000",
                        "001122242242221100",
                        "001211222222112100",
                        "001222112211222100",
                        "001242221122222100",
                        "001222222122222100",
                        "001222242122322100",
                        "001242222122222100",
                        "001222222122222100",
                        "001122242122221100",
                        "000011222122110000",
                        "000000112111000000",
                        "000000001100000000",
                        "000000000000000000");
                break;
            case SHOP_ITEM_BOMB:
                setColors(Color.BLACK, Color.GRAY, Color.LIGHTGRAY, Color.RED, Color.YELLOW);
                data = ImageCreator.makeArray(
                        "000505000000000000",
                        "000050000000000000",
                        "000504330000000000",
                        "000000013110000000",
                        "000001111311100000",
                        "000011111111110000",
                        "000111211111111000",
                        "000112111111111000",
                        "001111111111111100",
                        "001111111111111100",
                        "001111111111111100",
                        "001121111111111100",
                        "000111111111111000",
                        "000111111111111000",
                        "000011111111110000",
                        "000001111111100000",
                        "000000011110000000",
                        "000000000000000000");
                break;
            case SHOP_COIN:
                setColors(Color.YELLOW, Color.ORANGE);
                data = ImageCreator.makeArray(4, "011011121112111211220220");
                break;
            case MENU_ARROW:
                setColors(Color.YELLOW, Color.WHITE);
                data = ImageCreator.makeArray(5, "11000011000011000011001100110011000");
                break;
            case MENU_DIFFICULTY_BAR:
                setColors(Color.GREEN, Color.BLACK);
                data = ImageCreator.makeArray(3, "020212212212212212020");
                break;
            case WIN_MENU:
                setColors(Theme.MAIN_MENU_BG.getColor(), Color.NONE, Theme.MAIN_MENU_BORDER.getColor());
                data = ImageCreator.createWindowBitmap(100, 100, false, true);
                break;
            case WIN_SHOP:
                setColors(Theme.SHOP_BG.getColor(), Color.BLACK, Theme.SHOP_BORDER.getColor());
                data = ImageCreator.createWindowBitmap(80, 80, true, true);
                break;
            case WIN_SHOP_HEADER_FOOTER:
                setColors(Theme.SHOP_HEADER_FOOTER.getColor(), Color.BLACK, Theme.SHOP_BORDER.getColor());
                data = ImageCreator.createWindowBitmap(80, 12, false, true);
                break;
            case SHOP_ITEM_FRAME:
                setColors(Theme.SHOP_ITEM_BG.getColor(), Color.BLACK, Color.GREEN);
                data = ImageCreator.createWindowBitmap(20, 20, true, true);
                break;
            case SHOP_ITEM_FRAME_PRESSED:
                setColors(Theme.SHOP_ITEM_BG.getColor(), Color.BLACK, Color.GREEN);
                data = ImageCreator.createWindowBitmap(20, 20, false, true);
                break;
            case WIN_VICTORY:
            case WIN_GAME_OVER:
                setColors(Theme.MAIN_MENU_BG.getColor(), Color.BLACK, Theme.BUTTON_BG.getColor());
                data = ImageCreator.createWindowBitmap(70, 35, true, true);
                break;
            case MENU_SWITCH:
                setColors(Color.RED, Color.BLACK, Color.YELLOW);
                data = ImageCreator.createWindowBitmap(4, 7, false, true);
                break;
            case MENU_SWITCH_RAIL:
                setColors(Color.BLACK, Color.NONE, Color.NONE);
                data = ImageCreator.createWindowBitmap(12, 3, false, false);
                break;
            case WIN_BOARD_TRANSPARENT_FRAME:
                setColors(Color.NONE, Color.NONE, Color.BLUE);
                data = ImageCreator.createWindowBitmap(100, 100, false, true);
                break;
            case MENU_THEME_PALETTE:
                setColors(Color.GRAY, Color.BLACK, Color.WHITE);
                data = ImageCreator.createWindowBitmap(10, 10, false, true);
                break;
            case SHOP_DICE_1:
                setColors(Color.WHITE, Color.RED, Color.BLACK);
                data = ImageCreator.makeArray(8, "1111111011111113111111131112111311111113111111131111111303333333");
                break;
            case SHOP_DICE_2:
                setColors(Color.WHITE, Color.BLACK, Color.BLACK);
                data = ImageCreator.makeArray(8, "1111111011111213111111131111111311111113121111131111111303333333");
                break;
            case SHOP_DICE_3:
                setColors(Color.WHITE, Color.BLACK, Color.BLACK);
                data = ImageCreator.makeArray(8, "1111111011111213111111131112111311111113121111131111111303333333");
                break;
            case SHOP_DICE_4:
                setColors(Color.WHITE, Color.BLACK, Color.BLACK);
                data = ImageCreator.makeArray(8, "1111111012111213111111131111111311111113121112131111111303333333");
                break;
            case SHOP_DICE_5:
                setColors(Color.WHITE, Color.BLACK, Color.BLACK);
                data = ImageCreator.makeArray(8, "1111111012111213111111131112111311111113121112131111111303333333");
                break;
            case SHOP_DICE_6:
                setColors(Color.WHITE, Color.BLACK, Color.BLACK);
                data = ImageCreator.makeArray(8, "1111111012111213111111131211121311111113121112131111111303333333");
                break;
            case MENU_CUP:
                setColors(Color.GOLD, Color.WHITE, Color.MAROON, Color.KHAKI);
                data = ImageCreator.makeArray(
                        "000111111111000",
                        "001111111211200",
                        "010111111211020",
                        "010111111211020",
                        "001111111211100",
                        "000011111210000",
                        "000011112110000",
                        "000001111100000",
                        "000000010000000",
                        "000000010000000",
                        "000000112000000",
                        "000001111200000",
                        "000033333330000",
                        "000034444230000",
                        "000333333333000");
                break;

            // SPRITES

            case SPR_BOARD_0:
                setColors(Color.WHITE);
                data = ImageCreator.makeSprite("011010111011101110110110", 4, 3, 4);
                break;
            case SPR_BOARD_1:
                setColors(Color.DARKGREEN);
                data = ImageCreator.makeSprite("001001101110011001101111", 4, 3, 4);
                break;
            case SPR_BOARD_2:
                setColors(Color.DARKBLUE);
                data = ImageCreator.makeSprite("011010110011011011001111", 4, 3, 4);
                break;
            case SPR_BOARD_3:
                setColors(Color.DARKRED);
                data = ImageCreator.makeSprite("111100110110001110110110", 4, 3, 4);
                break;
            case SPR_BOARD_4:
                setColors(Color.PURPLE);
                data = ImageCreator.makeSprite("001101111011111100110011", 4, 3, 4);
                break;
            case SPR_BOARD_5:
                setColors(Color.DARKSLATEGRAY);
                data = ImageCreator.makeSprite("111111001110001110110110", 4, 3, 4);
                break;
            case SPR_BOARD_6:
                setColors(Color.DARKSLATEBLUE);
                data = ImageCreator.makeSprite("011111001110110111010110", 4, 3, 4);
                break;
            case SPR_BOARD_7:
                setColors(Color.FORESTGREEN);
                data = ImageCreator.makeSprite("111100010010011001100110", 4, 3, 4);
                break;
            case SPR_BOARD_8:
                setColors(Color.RED);
                data = ImageCreator.makeSprite("011011010110110111010110", 4, 3, 4);
                break;
            case SPR_BOARD_9:
                setColors(Color.BLACK);
                data = ImageCreator.makeSprite("011010111011011100111110", 4, 3, 4);
                break;
            case SPR_BOARD_FLAG:
                setColors(Color.BLACK, Color.RED, Color.DARKRED, Color.YELLOW);
                data = ImageCreator.makeSprite("122001422212233133001000010000", 5, 2, 3);
                break;
            case SPR_BOARD_MINE:
                setColors(Color.BLACK, Color.RED, Color.DARKGRAY, Color.DARKSLATEGRAY, Color.DARKRED, Color.DIMGRAY);
                data = ImageCreator.makeSprite("0001000000111100013446100142541111455410016446100011110000001000", 8, 2, 2);
                break;

            // CELLS

            case CELL_CLOSED:
                setColors(Theme.CELL_SHADOW.getColor(), Theme.CELL_LIGHT.getColor(), Theme.CELL_BG_UP.getColor(), Color.BLACK, Color.GRAY);
                data = ImageCreator.makeArray(
                        "2222222222",
                        "2333333331",
                        "2333333331",
                        "2333333331",
                        "2333333331",
                        "2333333331",
                        "2333333331",
                        "2333333331",
                        "2333333331",
                        "2111111111");
                break;
            case CELL_OPENED:
                setColors(Theme.CELL_SHADOW.getColor(), Theme.CELL_LIGHT.getColor(), Theme.CELL_BG_UP.getColor(), Color.BLACK, Color.GRAY);
                data = ImageCreator.makeArray(
                        "2222222222",
                        "2111111111",
                        "2133333333",
                        "2133333333",
                        "2133333333",
                        "2133333333",
                        "2133333333",
                        "2133333333",
                        "2133333333",
                        "2133333333");
                break;
            case CELL_DESTROYED:
                setColors(Theme.CELL_SHADOW.getColor(), Theme.CELL_LIGHT.getColor(), Theme.CELL_BG_UP.getColor(), Color.BLACK, Color.GRAY);
                data = ImageCreator.makeArray(
                        "2222222222",
                        "2111111111",
                        "2153335335",
                        "2135435353",
                        "2153344343",
                        "2154544353",
                        "2135353533",
                        "2135435455",
                        "2135333533",
                        "2153333533");
                break;

            // SYMBOLS

            case SYM_RU_LETTER_A:
                data = ImageCreator.makeArray(4, "0000000001101001111110011001");
                break;
            case SYM_RU_LETTER_B:
                data = ImageCreator.makeArray(4, "0000000011111000111010011110");
                break;
            case SYM_RU_LETTER_V:
                data = ImageCreator.makeArray(4, "0000000011101001111010011110");
                break;
            case SYM_RU_LETTER_G:
                data = ImageCreator.makeArray(3, "000000111100100100100");
                break;
            case SYM_RU_LETTER_D:
                data = ImageCreator.makeArray(5, "0000000000001100101001010010101111110001");
                break;
            case SYM_RU_LETTER_YE:
                data = ImageCreator.makeArray(4, "0000000011111000111010001111");
                break;
            case SYM_RU_LETTER_YO:
                data = ImageCreator.makeArray(4, "1010000011111000111010001111");
                break;
            case SYM_RU_LETTER_J:
                data = ImageCreator.makeArray(5, "00000000001010110101011101010110101");
                break;
            case SYM_RU_LETTER_Z:
                data = ImageCreator.makeArray(4, "0000000011100001011000011110");
                break;
            case SYM_RU_LETTER_I:
                data = ImageCreator.makeArray(4, "0000000010011001101111011001");
                break;
            case SYM_RU_LETTER_IKR:
                data = ImageCreator.makeArray(4, "0110000010011001101111011001");
                break;
            case SYM_RU_LETTER_K:
                data = ImageCreator.makeArray(4, "0000000010011010110010101001");
                break;
            case SYM_RU_LETTER_L:
                data = ImageCreator.makeArray(4, "0000000000110101010101011001");
                break;
            case SYM_RU_LETTER_M:
                data = ImageCreator.makeArray(5, "00000000001000111011101011000110001");
                break;
            case SYM_RU_LETTER_N:
                data = ImageCreator.makeArray(4, "0000000010011001111110011001");
                break;
            case SYM_RU_LETTER_O:
                data = ImageCreator.makeArray(4, "0000000001101001100110010110");
                break;
            case SYM_RU_LETTER_P:
                data = ImageCreator.makeArray(4, "0000000011111001100110011001");
                break;
            case SYM_RU_LETTER_R:
                data = ImageCreator.makeArray(4, "0000000011101001111010001000");
                break;
            case SYM_RU_LETTER_S:
                data = ImageCreator.makeArray(4, "0000000001111000100010000111");
                break;
            case SYM_RU_LETTER_T:
                data = ImageCreator.makeArray(3, "000000111010010010010");
                break;
            case SYM_RU_LETTER_U:
                data = ImageCreator.makeArray(4, "0000000010011001011100011110");
                break;
            case SYM_RU_LETTER_F:
                data = ImageCreator.makeArray(5, "00000000000111010101101010111000100");
                break;
            case SYM_RU_LETTER_H:
                data = ImageCreator.makeArray(4, "0000000010011001011010011001");
                break;
            case SYM_RU_LETTER_C:
                data = ImageCreator.makeArray(5, "0000000000100101001010010100101111100001");
                break;
            case SYM_RU_LETTER_CH:
                data = ImageCreator.makeArray(4, "0000000010011001011100010001");
                break;
            case SYM_RU_LETTER_SHA:
                data = ImageCreator.makeArray(5, "00000000001010110101101011010111111");
                break;
            case SYM_RU_LETTER_SCHA:
                data = ImageCreator.makeArray(6, "000000000000101010101010101010101010111111000001");
                break;
            case SYM_RU_LETTER_SOFT:
                data = ImageCreator.makeArray(4, "0000000010001000111010011110");
                break;
            case SYM_RU_LETTER_Y:
                data = ImageCreator.makeArray(6, "000000000000100001100001111001100101111001");
                break;
            case SYM_RU_LETTER_HARD:
                data = ImageCreator.makeArray(5, "00000000001100001000011100100101110");
                break;
            case SYM_RU_LETTER_E:
                data = ImageCreator.makeArray(4, "0000000011100001011100011110");
                break;
            case SYM_RU_LETTER_YU:
                data = ImageCreator.makeArray(6, "000000000000100110101001111001101001100110");
                break;
            case SYM_RU_LETTER_YA:
                data = ImageCreator.makeArray(4, "0000000001111001011101011001");
                break;
            case SYM_EN_LETTER_D:
                data = ImageCreator.makeArray(4, "0000000011101001100110011110");
                break;
            case SYM_EN_LETTER_F:
                data = ImageCreator.makeArray(4, "0000000011111000111010001000");
                break;
            case SYM_EN_LETTER_G:
                data = ImageCreator.makeArray(4, "0000000001111000101110010110");
                break;
            case SYM_EN_LETTER_I:
                data = ImageCreator.makeArray(3, "000000111010010010111");
                break;
            case SYM_EN_LETTER_J:
                data = ImageCreator.makeArray(4, "0000000001110010001010100100");
                break;
            case SYM_EN_LETTER_L:
                data = ImageCreator.makeArray(3, "000000100100100100111");
                break;
            case SYM_EN_LETTER_N:
                data = ImageCreator.makeArray(4, "0000000010011001110110111001");
                break;
            case SYM_EN_LETTER_Q:
                data = ImageCreator.makeArray(4, "0000000001101001100110100101");
                break;
            case SYM_EN_LETTER_R:
                data = ImageCreator.makeArray(4, "0000000011101001111010101001");
                break;
            case SYM_EN_LETTER_S:
                data = ImageCreator.makeArray(4, "0000000001111000011000011110");
                break;
            case SYM_EN_LETTER_U:
                data = ImageCreator.makeArray(4, "0000000010011001100110010110");
                break;
            case SYM_EN_LETTER_V:
                data = ImageCreator.makeArray(5, "00000000001000110001100010101000100");
                break;
            case SYM_EN_LETTER_W:
                data = ImageCreator.makeArray(5, "00000000001000110001101011101110001");
                break;
            case SYM_EN_LETTER_X:
                data = ImageCreator.makeArray(5, "00000000001000101010001000101010001");
                break;
            case SYM_EN_LETTER_Y:
                data = ImageCreator.makeArray(5, "00000000001000101010001000010000100");
                break;
            case SYM_EN_LETTER_Z:
                data = ImageCreator.makeArray(4, "0000000011110001011010001111");
                break;
            case SYM_SYMBOL_DOT:
                data = ImageCreator.makeArray(1, "0000001");
                break;
            case SYM_SYMBOL_COMMA:
                data = ImageCreator.makeArray(2, "0000000000010110");
                break;
            case SYM_SYMBOL_COLON:
                data = ImageCreator.makeArray(1, "0001010");
                break;
            case SYM_SYMBOL_EXCLAMATION:
                data = ImageCreator.makeArray(1, "0011101");
                break;
            case SYM_SYMBOL_QUESTION:
                data = ImageCreator.makeArray(4, "0000000001100001011000000100");
                break;
            case SYM_SYMBOL_SLASH:
                data = ImageCreator.makeArray(3, "000001001010010100100");
                break;
            case SYM_SYMBOL_DASH:
                data = ImageCreator.makeArray(2, "00000000110000");
                break;
            case SYM_SYMBOL_EQUALS:
                data = ImageCreator.makeArray(2, "00000011001100");
                break;
            case SYM_SYMBOL_ASTERISK:
                data = ImageCreator.makeArray(3, "000000000101010101000");
                break;
            case SYM_DIGIT_0:
                data = ImageCreator.makeArray(3, "000000111101101101111");
                break;
            case SYM_DIGIT_1:
                data = ImageCreator.makeArray(3, "000000110010010010111");
                break;
            case SYM_DIGIT_2:
                data = ImageCreator.makeArray(3, "000000111001111100111");
                break;
            case SYM_DIGIT_3:
                data = ImageCreator.makeArray(3, "000000111001111001111");
                break;
            case SYM_DIGIT_4:
                data = ImageCreator.makeArray(3, "000000101101111001001");
                break;
            case SYM_DIGIT_5:
                data = ImageCreator.makeArray(3, "000000111100111001111");
                break;
            case SYM_DIGIT_6:
                data = ImageCreator.makeArray(3, "000000111100111101111");
                break;
            case SYM_DIGIT_7:
                data = ImageCreator.makeArray(3, "000000111001001001001");
                break;
            case SYM_DIGIT_8:
                data = ImageCreator.makeArray(3, "000000111101111101111");
                break;
            case SYM_DIGIT_9:
                data = ImageCreator.makeArray(3, "000000111101111001111");
                break;
            case SYM_SYMBOL_SQ_BRACKET_L:
                data = ImageCreator.makeArray(2, "0011101010101011");
                break;
            case SYM_SYMBOL_SQ_BRACKET_R:
                data = ImageCreator.makeArray(2, "0011010101010111");
                break;
            case SYM_SYMBOL_SPACE:
                data = new int[1][3];
                break;
            case SPR_BOARD_NONE:
            case NONE:
            default:
                setColors(Color.WHITE);
                data = new int[1][1];
                break;
        }
    }

    private void setColors(Color... colors) {
        this.colors = new Color[colors.length + 1];
        this.colors[0] = Color.NONE;
        System.arraycopy(colors, 0, this.colors, 1, colors.length);
    }

    public Color[] getColors() {
        return colors == null ? new Color[]{Color.NONE, Color.WHITE} : colors;
    }

    public int[][] getData() {
        return data;
    }
}
