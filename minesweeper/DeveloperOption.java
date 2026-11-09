package com.javarush.games.minesweeper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Помечает методы, доступные только в режиме разработчика. */
@Retention(value = RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface DeveloperOption {

}
