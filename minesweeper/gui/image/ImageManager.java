package com.javarush.games.minesweeper.gui.image;

import java.util.HashSet;
import java.util.Set;

public class ImageManager {
    public static final Set<Image> allImages = new HashSet<>();

    public static void addImage(Image image) {
        allImages.add(image);
    }

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
