package com.javarush.games.minesweeper.gui.image;

import java.util.HashSet;
import java.util.Set;

/** Реестр долгоживущих изображений для перекрашивания. */
public class ImageManager {
    /** Все зарегистрированные изображения. */
    public static final Set<Image> allImages = new HashSet<>();

    /** Добавляет изображение в реестр. */
    public static void addImage(Image image) {
        allImages.add(image);
    }

    /** Перекрашивает все зарегистрированные изображения. */
    public static void reloadAllImagesColors() {
        for (Image image : allImages) {

            if (image.isColorLocked()) {
                continue;
            }

            ImageStorage storage = new ImageStorage(image.getType());
            image.setColors(storage.getColors());
        }
    }
}
