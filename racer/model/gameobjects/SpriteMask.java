package com.javarush.games.racer.model.gameobjects;

class SpriteMask {
    private int step;
    private State state;
    private int width;

    private enum State {
        IN, OUT, READY, STOP
    }

    SpriteMask() {
        this.state = State.READY;
    }

    void nextStep(int spriteWidth) {
        changeWidth();
        limitGrowth(spriteWidth);
    }

    private void changeWidth() {
        if (state == State.IN) {
            width += step;
        } else if (state == State.OUT) {
            width -= step;
        }
    }

    private void limitGrowth(int spriteWidth) {
        if (width < 0) {
            width = 0;
            state = State.STOP;
        } else if (width > spriteWidth) {
            width = spriteWidth;
            state = State.STOP;
        }
    }

    void startIn(int step) {
        if (state != State.READY) return;

        this.step = step;
        this.width = 0;
        this.state = State.IN;
    }

    void startOut(int step, int spriteWidth) {
        if (state != State.READY) return;

        this.step = step;
        this.width = spriteWidth;
        this.state = State.OUT;
    }

    public int getWidth() {
        return width;
    }
}
