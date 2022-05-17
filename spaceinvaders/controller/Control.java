package com.javarush.games.spaceinvaders.controller;

import com.javarush.engine.cell.Key;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is for marking methods that are accessed by the controller.
 * Values are keys that are used to trigger this event.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Control {
    Key[] value();
}
