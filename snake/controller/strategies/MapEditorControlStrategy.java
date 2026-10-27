package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.view.impl.MapEditorView;

/** Управление редактором карты. */
public class MapEditorControlStrategy implements ControlStrategy {
    private static MapEditorControlStrategy instance;

    /** Возвращает единственный экземпляр. */
    public static MapEditorControlStrategy getInstance() {
        if (instance == null) instance = new MapEditorControlStrategy();
        return instance;
    }

    /** Кладёт выбранный образец местности. */
    @Override
    public void leftClick(int x, int y) {
        if (SnakeGame.isOutOfBounds(x, y)) return;

        MapEditorView.getInstance().drawTerrain(x, y);
        Phase.proceed(Phase.MAP_EDITOR);
    }

    /** Копирует местность под курсором в образец. */
    @Override
    public void rightClick(int x, int y) {
        if (SnakeGame.isOutOfBounds(x, y)) return;

        final MapEditorView view = MapEditorView.getInstance();
        view.printCoordinate(x, y);
        view.copyTerrain(x, y);
        Phase.proceed(Phase.MAP_EDITOR);
    }

    /** Выбирает следующий образец. */
    @Override
    public void pressUp() {
        pressRight();
    }

    /** Выбирает предыдущий образец. */
    @Override
    public void pressDown() {
        pressLeft();
    }

    /** Выбирает следующий образец. */
    @Override
    public void pressRight() {
        MapEditorView.getInstance().selectNextSample();
        Phase.proceed(Phase.MAP_EDITOR);
    }

    /** Выбирает предыдущий образец. */
    @Override
    public void pressLeft() {
        MapEditorView.getInstance().brushPrevious();
        Phase.proceed(Phase.MAP_EDITOR);
    }

    /** Печатает карту в консоль. */
    @Override
    public void pressEnter() {
        MapEditorView.getInstance().printTerrain();
    }

    /** Возвращает в главное меню. */
    @Override
    public void pressEscape() {
        MenuSelector.loadLastPointerPosition();
        Phase.proceed(Phase.MAIN_MENU);
    }
}
