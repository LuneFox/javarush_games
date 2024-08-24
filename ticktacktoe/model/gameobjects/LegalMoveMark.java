package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.TicTacToeGame;
import com.javarush.games.ticktacktoe.view.shapes.Shape;

/**
 * Метка доступного хода
 *
 * @author LuneFox
 */
public class LegalMoveMark extends GameObject {

    public LegalMoveMark(double x, double y) {
        super(x, y);
        super.setStaticView(Shape.LEGAL_MOVE_MARK_BLACK);
    }

    /**
     * Установка цвета в зависимости от стороны текущего игрока
     */
    public void matchPlayerColor() {
        Side side = TicTacToeGame.getInstance().getCurrentPlayer();
        setStaticView(side == Side.BLACK ? Shape.LEGAL_MOVE_MARK_BLACK : Shape.LEGAL_MOVE_MARK_WHITE);
    }
}
