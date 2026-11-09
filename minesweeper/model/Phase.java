package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Click;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.view.View;
import com.javarush.games.minesweeper.view.ViewFactory;
import com.javarush.games.minesweeper.view.impl.ViewShop;

import java.util.*;

/** Фаза игры, определяющая текущий экран и управление. */
public enum Phase {
    /** Экран «Об игре». */
    ABOUT,
    /** Экран игрового поля. */
    BOARD,
    /** Экран завершения партии. */
    GAME_OVER,
    /** Экран справки о предмете. */
    ITEM_HELP,
    /** Главное меню. */
    MAIN,
    /** Экран настроек. */
    OPTIONS,
    /** Экран рекордов. */
    RECORDS,
    /** Экран детализации счёта. */
    SCORE,
    /** Экран магазина. */
    SHOP;
    /** Ссылка на игру. */
    private static MinesweeperGame game;
    /** Экраны по фазам. */
    private static Map<Phase, View> viewMap;
    /** Ожидающий экран. */
    private static View pendingView;
    /** Текущий экран. */
    private static View currentView;
    /** Список фаз в порядке приоритета. */
    private static List<Phase> PHASES;
    /** Позиция активной фазы в списке. */
    private static final int ACTIVE_PHASE_POSITION = 0;

    /** Инициализирует фазы и экраны. */
    public static void setUp(MinesweeperGame game) {
        Phase.game = game;
        PHASES = new LinkedList<>(Arrays.asList(Phase.values()));
        viewMap = new HashMap<>();
        ViewFactory viewFactory = new ViewFactory(game);
        PHASES.forEach(phase -> viewMap.put(phase, viewFactory.createView(phase)));
    }

    /**
     * Привязывает слоты магазина к экрану магазина.
     */
    public static void linkShopSlots(List<? extends InteractiveObject> slots) {
        ViewShop view = (ViewShop) getView(Phase.SHOP);
        slots.forEach(view::linkObject);
    }

    /** Делает фазу активной. */
    public static void setActive(Phase phase) {
        PHASES.remove(phase);
        PHASES.add(ACTIVE_PHASE_POSITION, phase);
        pendingView = viewMap.get(phase);
    }

    /** Обновляет текущий экран. */
    public static void updateView() {
        if (currentView == null) {
            if (pendingView == null) return;
            changeCurrentView();
        } else {
            changeCurrentViewIfNeeded();
        }
        currentView.update();
    }

    /** Переключает экран, когда анимация завершена. */
    private static void changeCurrentViewIfNeeded() {
        if (Button.isAnimationFinished() && isAnotherViewPending()) {
            changeCurrentView();
        } else {
            Button.continueAnimation();
        }
    }

    /** Сообщает, ожидает ли другой экран. */
    private static boolean isAnotherViewPending() {
        return pendingView != currentView;
    }

    /** Переключает текущий экран. */
    private static void changeCurrentView() {
        currentView = pendingView;
        onViewChange();
    }

    /** Выполняет действия при смене экрана. */
    private static void onViewChange() {
        game.setInterlacedEffect(false);
        ((ViewShop) getView(Phase.SHOP)).skipMoneyAnimation();
        PageSelector.resetAllSelectors();
    }

    /** Возвращает активную фазу. */
    public static Phase getActive() {
        return PHASES.get(ACTIVE_PHASE_POSITION);
    }

    /** Сообщает, активна ли фаза. */
    public static boolean isActive(Phase phase) {
        return (PHASES.get(ACTIVE_PHASE_POSITION) == phase);
    }

    /** Возвращает экран фазы. */
    public static View getView(Phase phase) {
        return viewMap.get(phase);
    }

    /** Передаёт левый клик текущему экрану. */
    public static void leftClickOnCurrentView(int x, int y) {
        if (currentView == null) return;
        currentView.click(x, y, Click.LEFT);
    }

    /** Передаёт правый клик текущему экрану. */
    public static void rightClickOnCurrentView(int x, int y) {
        if (currentView == null) return;
        currentView.click(x, y, Click.RIGHT);
    }
}
