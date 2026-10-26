package com.javarush.games.racer.controller;

import com.javarush.engine.cell.Key;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as a controller action and lists the keys that trigger it. Values are the keys that are used
 * to trigger this event.
 *
 * <p>The annotation is retained only in the source ({@link RetentionPolicy#SOURCE}) and does not perform any
 * routing at run time; it documents the binding between a key and a method. Actual key handling goes from
 * {@code RacerGame.onKeyPress} to {@link Controller}, so the annotation must be kept in sync with the
 * controller code by hand.</p>
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Control {
    /**
     * Returns the keys that trigger the annotated method.
     *
     * @return the keys mapped to the annotated action
     */
    Key[] value();
}
