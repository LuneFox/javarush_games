package com.javarush.games.racer;

import com.javarush.engine.cell.Key;

public class InputEvent {
    private RacerGame game;

    public InputEvent(RacerGame game) {
        this.game = game;
    }

    public void keyPress(Key key) {
        switch (key) {
            case UP: {
                game.delorean.setVerticalDirection(Direction.UP);
                break;
            }
            case DOWN:
                game.delorean.setVerticalDirection(Direction.DOWN);
                break;
            case RIGHT:
                game.delorean.setHorizontalDirection(Direction.RIGHT);
                break;
            case LEFT:
                game.delorean.setHorizontalDirection(Direction.LEFT);
                break;
            case SPACE:
                if (game.isStopped && game.finishTimeOut <= 0) {
                    game.createGame();
                }
                break;
            default:
                break;
        }

    }

    public void keyRelease(Key key) {
        switch (key) {
            case UP:
                if (game.delorean.getVerticalDirection() == Direction.UP) {
                    game.delorean.setVerticalDirection(Direction.NONE);
                }
                break;
            case DOWN:
                if (game.delorean.getVerticalDirection() == Direction.DOWN) {
                    game.delorean.setVerticalDirection(Direction.NONE);
                }
                break;
            case RIGHT:
                if (game.delorean.getHorizontalDirection() == Direction.RIGHT) {
                    game.delorean.setHorizontalDirection(Direction.NONE);
                }
                break;
            case LEFT:
                if (game.delorean.getHorizontalDirection() == Direction.LEFT) {
                    game.delorean.setHorizontalDirection(Direction.NONE);
                }
                break;
            default:
                break;
        }
    }
}
