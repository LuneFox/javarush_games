package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;

public class ImageDataStorage {
    private final Color[] colors;
    private final int[][] data;

    public ImageDataStorage(Bitmap bitmap) {
        switch (bitmap) {
            case PIC_LOGO:
                colors = new Color[]{Color.NONE, Color.WHITE, Color.BLACK, Color.DARKSLATEGRAY, Color.SANDYBROWN, Color.RED, Color.YELLOW};
                data = ImageDataCreator.stringsToArray("00000001111111000000000000000000000000000000000000000000000000000000", "00000111100000110000000000000000000000000000000000000000000000000000", "00001111000000001000000000000000000000000000000000000000000000000000", "00011110000000000100000000000000000000000000000011001100000000000000", "00111100000005500000000000000000000000000000000010000100000000000000", "00111000000050550000000000000000000000000000000000000000000000000000", "01111000000006050000000000000000000000000000000000000000000000000000", "01111000000010500000000111110000011001111000000001111100000111111100", "01111000000100000000001100001000011011111100000011000010001110000010", "01111000022222000000011000000100011110000110000110000001001110000001", "01111000222222200000011000000100011100000010001110000001001110000001", "01111002222232220000111000000100011100000010001110000001001110000001", "01111002222223220000111000000100011100000010001110000011001110000001", "00111002222223220010111000000100011100000010001111111110001110000001", "00111102222222220010111000000100011100000010001110000000001110000001", "00011112222222220100111000000100011100000010001110000000001110000011", "00001111222222201000111100000110011100000010001110000001001111111110", "00000111122222110000011110001110011100000011000111000010001111111000", "00000001111111000000001111110011001100000001100011111100001410000000", "00000000000000000000000000000000000000000000000000000000001410000000", "00000000000000000000000000000000000000000000000000000000001410000000", "00000000000000000000000000000000000000000000000000000000001410000000", "00011111111100000000000000000000000000000000000000000000001410000000", "00122222222100000000000000000000000000000000000000000000011410000000", "01222222222100000000000000000000000000000000000000000000114110000000", "12222222322111111111111111111111111111111111111111111111144100000000", "12233333322222224444444444444444444444444444444444444444441100000000", "12222222222222224444444444444444444444444444444444444444111000000000", "12222222222111111111111111111111111111111111111111111111100000000000", "01222222222100000000000000000000000000000000000000000000000000000000", "00122222222100000000000000000000000000000000000000000000000000000000", "00011111111100000000000000000000000000000000000000000000000000000000");
                break;
            case PIC_FACE_HAPPY:
                colors = new Color[]{Color.NONE, Color.YELLOW, Color.BLACK};
                data = ImageDataCreator.stringsToArray("2222002222002222", "2111221111221112", "2112111111112112", "2121111111111212", "2111121111211112", "0211212112121120", "0211111111111120", "0211111111111120", "0211112112111120", "0021112222111200", "0021111221111200", "0002111111112000", "0000221111220000", "0000002222000000");
                break;
            case PIC_FACE_SAD:
                colors = new Color[]{Color.NONE, Color.YELLOW, Color.BLACK};
                data = ImageDataCreator.stringsToArray("2222002222002222", "2111221111221112", "2112111111112112", "2121212112121212", "2111121111211112", "0211212112121120", "0211111111111120", "0211111221111120", "0211112222111120", "0021112112111200", "0021111111111200", "0002111111112000", "0000221111220000", "0000002222000000");
                break;
            case SHOP_ITEM_SHIELD:
                colors = new Color[]{Color.NONE, Color.DARKGRAY, Color.LIGHTBLUE, Color.LIGHTGRAY, Color.GRAY};
                data = ImageDataCreator.stringsToArray("000000000000000000", "004400000000004400", "004344444444444400", "004311111111114400", "004311111111114400", "004312222222214400", "004312222222214400", "004313333333314400", "004311111111114400", "004311111111114400", "004311111111114400", "004311111111114400", "004311111111114400", "004311111111114400", "004311111111114400", "000431111111144000", "000044444444440000", "000000000000000000");
                break;
            case SHOP_ITEM_SCANNER:
                colors = new Color[]{Color.NONE, Color.BLACK, Color.GRAY, Color.MEDIUMPURPLE, Color.RED, Color.WHITE};
                data = ImageDataCreator.stringsToArray("000000000000000000", "000100000000000000", "000100000000000000", "000111111111111000", "000111111111111000", "000112222222211000", "000112522224211000", "000112252222211000", "000112225222211000", "000112222222211000", "000111111111111000", "000113131131311000", "000111111111111000", "000111111111111000", "000111111111111000", "000111111111111000", "000011111111110000", "000000000000000000");
                break;
            case SHOP_ITEM_FLAG:
                colors = new Color[]{Color.NONE, Color.BLACK, Color.RED, Color.DARKRED, Color.YELLOW};
                data = ImageDataCreator.stringsToArray("000000000000000000", "001100000000000000", "001122000000000000", "001122220000000000", "001122222200000000", "001124422222000000", "001124422222220000", "001122222222223300", "001122222222330000", "001122222233000000", "001122223300000000", "001122330000000000", "001133000000000000", "001100000000000000", "001100000000000000", "001100000000000000", "001100000000000000", "001100000000000000");
                break;
            case SHOP_ITEM_SHOVEL:
                colors = new Color[]{Color.NONE, Color.DARKORANGE, Color.WHITE, Color.YELLOW, Color.ORANGE, Color.SADDLEBROWN};
                data = ImageDataCreator.stringsToArray("000000000000000000", "000000000000000000", "000002222222220000", "000223333333332000", "002333344444442000", "023334444444442000", "023344444444442222", "023443333444444555", "023444444443444555", "024444444443444555", "024443333444444555", "024444444444441111", "014444444444441000", "001444444444441000", "000114444444441000", "000001111111110000", "000000000000000000", "000000000000000000");
                break;
            case SHOP_ITEM_DICE:
                colors = new Color[]{Color.NONE, Color.DARKGRAY, Color.LIGHTGRAY, Color.RED, Color.BLACK};
                data = ImageDataCreator.stringsToArray("000000000000000000", "000000000000000000", "000000001100000000", "000000112211000000", "000011222222110000", "001122242242221100", "001211222222112100", "001222112211222100", "001242221122222100", "001222222122222100", "001222242122322100", "001242222122222100", "001222222122222100", "001122242122221100", "000011222122110000", "000000112111000000", "000000001100000000", "000000000000000000");
                break;
            case SHOP_ITEM_BOMB:
                colors = new Color[]{Color.NONE, Color.BLACK, Color.GRAY, Color.LIGHTGRAY, Color.RED, Color.YELLOW};
                data = ImageDataCreator.stringsToArray("000505000000000000", "000050000000000000", "000504330000000000", "000000013110000000", "000001111311100000", "000011111111110000", "000111211111111000", "000112111111111000", "001111111111111100", "001111111111111100", "001111111111111100", "001121111111111100", "000111111111111000", "000111111111111000", "000011111111110000", "000001111111100000", "000000011110000000", "000000000000000000");
                break;
            case SHOP_COIN:
                colors = new Color[]{Color.NONE, Color.YELLOW, Color.ORANGE};
                data = ImageDataCreator.stringsToArray("0110", "1112", "1112", "1112", "1122", "0220");
                break;
            case MENU_ARROW:
                colors = new Color[]{Color.NONE, Color.YELLOW, Color.WHITE};
                data = ImageDataCreator.stringsToArray("11000", "01100", "00110", "00011", "00110", "01100", "11000");
                break;
            case MENU_DIFFICULTY_BAR:
                colors = new Color[]{Color.NONE, Color.GREEN, Color.BLACK};
                data = ImageDataCreator.stringsToArray("020", "212", "212", "212", "212", "212", "020");
                break;
            case WIN_MENU:
                colors = new Color[]{Color.NONE, Theme.MAIN_MENU_BG.getColor(), Color.NONE, Theme.MAIN_MENU_BORDER.getColor()};
                data = ImageDataCreator.createWindowBitmap(100, 100, false, true);
                break;
            case WIN_SHOP:
                colors = new Color[]{Color.NONE, Theme.SHOP_BG.getColor(), Color.BLACK, Theme.SHOP_BORDER.getColor()};
                data = ImageDataCreator.createWindowBitmap(80, 80, true, true);
                break;
            case WIN_SHOP_HEADER_FOOTER:
                colors = new Color[]{Color.NONE, Theme.SHOP_HEADER_FOOTER.getColor(), Color.BLACK, Theme.SHOP_BORDER.getColor()};
                data = ImageDataCreator.createWindowBitmap(80, 12, false, true);
                break;
            case SHOP_ITEM_FRAME:
                colors = new Color[]{Color.NONE, Theme.SHOP_ITEM_BG.getColor(), Color.BLACK, Color.GREEN};
                data = ImageDataCreator.createWindowBitmap(20, 20, true, true);
                break;
            case SHOP_ITEM_FRAME_PRESSED:
                colors = new Color[]{Color.NONE, Theme.SHOP_ITEM_BG.getColor(), Color.BLACK, Color.GREEN};
                data = ImageDataCreator.createWindowBitmap(20, 20, false, true);
                break;
            case WIN_VICTORY:
            case WIN_GAME_OVER:
                colors = new Color[]{Color.NONE, Theme.MAIN_MENU_BG.getColor(), Color.BLACK, Theme.BUTTON_BG.getColor()};
                data = ImageDataCreator.createWindowBitmap(70, 35, true, true);
                break;
            case BUTTON_OK:
                colors = new Color[]{Color.NONE, Color.SADDLEBROWN, Color.BLACK, Color.BURLYWOOD};
                data = ImageDataCreator.createWindowBitmap(13, 9, true, true);
                break;
            case BUTTON_CLOSE:
                colors = new Color[]{Color.NONE, Color.SADDLEBROWN, Color.BLACK, Color.BURLYWOOD};
                data = ImageDataCreator.createWindowBitmap(9, 9, true, true);
                break;
            case MENU_SWITCH:
                colors = new Color[]{Color.NONE, Color.RED, Color.BLACK, Color.YELLOW};
                data = ImageDataCreator.createWindowBitmap(4, 7, false, true);
                break;
            case MENU_SWITCH_RAIL:
                colors = new Color[]{Color.NONE, Color.BLACK, Color.NONE, Color.NONE};
                data = ImageDataCreator.createWindowBitmap(12, 3, false, false);
                break;
            case WIN_BOARD_TRANSPARENT_FRAME:
                colors = new Color[]{Color.NONE, Color.NONE, Color.NONE, Color.BLUE};
                data = ImageDataCreator.createWindowBitmap(100, 100, false, true);
                break;
            case MENU_THEME_PALETTE:
                colors = new Color[]{Color.NONE, Color.GRAY, Color.BLACK, Color.WHITE};
                data = ImageDataCreator.createWindowBitmap(10, 10, false, true);
                break;
            case SHOP_DICE_1:
                colors = new Color[]{Color.NONE, Color.WHITE, Color.RED, Color.BLACK};
                data = ImageDataCreator.stringsToArray("11111110", "11111113", "11111113", "11121113", "11111113", "11111113", "11111113", "03333333");
                break;
            case SHOP_DICE_2:
                colors = new Color[]{Color.NONE, Color.WHITE, Color.BLACK, Color.BLACK};
                data = ImageDataCreator.stringsToArray("11111110", "11111213", "11111113", "11111113", "11111113", "12111113", "11111113", "03333333");
                break;
            case SHOP_DICE_3:
                colors = new Color[]{Color.NONE, Color.WHITE, Color.BLACK, Color.BLACK};
                data = ImageDataCreator.stringsToArray("11111110", "11111213", "11111113", "11121113", "11111113", "12111113", "11111113", "03333333");
                break;
            case SHOP_DICE_4:
                colors = new Color[]{Color.NONE, Color.WHITE, Color.BLACK, Color.BLACK};
                data = ImageDataCreator.stringsToArray("11111110", "12111213", "11111113", "11111113", "11111113", "12111213", "11111113", "03333333");
                break;
            case SHOP_DICE_5:
                colors = new Color[]{Color.NONE, Color.WHITE, Color.BLACK, Color.BLACK};
                data = ImageDataCreator.stringsToArray("11111110", "12111213", "11111113", "11121113", "11111113", "12111213", "11111113", "03333333");
                break;
            case SHOP_DICE_6:
                colors = new Color[]{Color.NONE, Color.WHITE, Color.BLACK, Color.BLACK};
                data = ImageDataCreator.stringsToArray("11111110", "12111213", "11111113", "12111213", "11111113", "12111213", "11111113", "03333333");
                break;
            case MENU_CUP:
                colors = new Color[]{Color.NONE, Color.GOLD, Color.WHITE, Color.MAROON, Color.KHAKI};
                data = ImageDataCreator.stringsToArray("000111111111000", "001111111211200", "010111111211020", "010111111211020", "001111111211100", "000011111210000", "000011112110000", "000001111100000", "000000010000000", "000000010000000", "000000112000000", "000001111200000", "000033333330000", "000034444230000", "000333333333000");
                break;

            case SPR_BOARD_0:
                colors = new Color[]{Color.NONE, Color.WHITE};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_1:
                colors = new Color[]{Color.NONE, Color.DARKGREEN};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_2:
                colors = new Color[]{Color.NONE, Color.DARKBLUE};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_3:
                colors = new Color[]{Color.NONE, Color.DARKRED};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_4:
                colors = new Color[]{Color.NONE, Color.PURPLE};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_5:
                colors = new Color[]{Color.NONE, Color.DARKSLATEGRAY};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_6:
                colors = new Color[]{Color.NONE, Color.DARKSLATEBLUE};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_7:
                colors = new Color[]{Color.NONE, Color.FORESTGREEN};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_8:
                colors = new Color[]{Color.NONE, Color.RED};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_9:
                colors = new Color[]{Color.NONE, Color.BLACK};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_FLAG:
                colors = new Color[]{Color.NONE, Color.BLACK, Color.RED, Color.DARKRED, Color.YELLOW};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 2, 2, 0, 0, 0, 0}, {0, 0, 0, 1, 4, 2, 2, 2, 0, 0}, {0, 0, 0, 1, 2, 2, 3, 3, 0, 0}, {0, 0, 0, 1, 3, 3, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case SPR_BOARD_MINE:
                colors = new Color[]{Color.NONE, Color.BLACK, Color.RED, Color.DARKGRAY, Color.DARKSLATEGRAY, Color.DARKRED, Color.DIMGRAY};
                data = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 1, 3, 4, 4, 6, 1, 0}, {0, 0, 0, 1, 4, 2, 5, 4, 1, 1}, {0, 0, 1, 1, 4, 5, 5, 4, 1, 0}, {0, 0, 0, 1, 6, 4, 4, 6, 1, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},};
                break;
            default:
                colors = new Color[]{Color.NONE};
                data = new int[1][1];
        }
    }

    public Color[] getColors() {
        return colors;
    }

    public int[][] getData() {
        return data;
    }
}