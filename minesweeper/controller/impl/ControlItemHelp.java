package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

public class ControlItemHelp implements ControlStrategy {

        @Override
        public void pressEsc() {
            Phase.setActive(Phase.SHOP);
        }
}
