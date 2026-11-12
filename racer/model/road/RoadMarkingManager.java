package com.javarush.games.racer.model.road;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.GameObjectManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Строит и обновляет пунктирную разметку полос. Управляет двумя рядами отметок (верхний на
 * y = 30, нижний на y = 70, нижний сдвинут вправо на длину отметки) с шагом в четыре длины
 * отметки и возвращает ушедшие за левый край отметки в правый конец их собственного ряда,
 * поэтому разметка не заканчивается и сохраняет шаг.
 */
public class RoadMarkingManager implements GameObjectManager {
    /** Шаг между соседними отметками в одной полосе: четыре длины отметки. */
    private static final double PITCH = 4 * RoadMarking.LENGTH;

    /** Все отметки обеих полос. */
    private final List<RoadMarking> markings = new ArrayList<>();

    /**
     * Создаёт менеджер и сразу заполняет оба ряда отметок.
     */
    public RoadMarkingManager() {
        createMarkings();
    }

    /**
     * Заполняет два ряда отметок от x = {@code WIDTH + LENGTH} до {@code -LENGTH} с шагом
     * {@link #PITCH}. Верхний ряд стоит на четверть ширины дороги выше центральной линии,
     * нижний — на столько же ниже и сдвинут вправо на длину отметки.
     */
    private void createMarkings() {
        final double len = RoadMarking.LENGTH;

        for (double i = RacerGame.WIDTH + len; i >= -len; i -= PITCH) {

            final double screenHalf = RacerGame.HEIGHT / 2.0;
            final double laneHalf = RoadManager.ROAD_WIDTH / 4.0;

            RoadMarking upperMarking = new RoadMarking(i, screenHalf - laneHalf);
            markings.add(upperMarking);

            RoadMarking lowerMarking = new RoadMarking(i + len, screenHalf + laneHalf);
            markings.add(lowerMarking);
        }
    }

    /**
     * Рисует все отметки в буфер отображения в порядке списка.
     */
    public void drawObjects() {
        markings.forEach(RoadMarking::draw);
    }

    /**
     * Двигает все отметки влево на {@code boost}, затем возвращает ушедшие за экран. Сначала
     * двигаются все отметки: правая граница для возврата должна быть уже сдвинутой, иначе
     * отметка, сдвинутая позже в этом же кадре, превратит зазор 20 px в 26 px. Отметка может
     * уйти за левый край на один кадр движения до возврата; отрицательный boost (машина едет
     * назад) сдвигал бы отметки вправо, и вернуть их было бы нечем.
     *
     * @param boost мировые пиксели за кадр, обычно текущая скорость машины
     */
    public void moveObjects(double boost) {
        for (RoadMarking marking : markings) {
            marking.x -= boost;
        }

        for (RoadMarking marking : markings) {
            if (marking.isOutsideScreen()) {
                transferBehindRightBorder(marking);
            }
        }
    }

    /**
     * Возвращает отметку за правый край её полосы: ставит её на шаг {@link #PITCH} правее
     * самой правой отметки той же полосы. Так сохраняется исходный шаг, и разметка не
     * расползается.
     *
     * @param marking отметка, ушедшая за левый край
     */
    private void transferBehindRightBorder(RoadMarking marking) {
        marking.x = getRightBorder(marking.y) + PITCH;
    }

    /**
     * Возвращает наибольшую x-координату среди отметок той же полосы.
     *
     * @param y ряд полосы
     * @return правая граница полосы, или 0, если в ней нет отметок
     */
    private double getRightBorder(double y) {
        return markings.stream()
                .filter(marking -> marking.y == y)
                .mapToDouble(marking -> marking.x)
                .max()
                .orElse(0);
    }
}