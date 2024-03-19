package org.example.projetjardinage.view;

import javafx.scene.layout.Pane;

public class GalleryView {
    private static GalleryView instance;

    private GalleryView() {}

    public static GalleryView getInstance() {
        if (instance == null) instance = new GalleryView();
        return instance;
    }

    public Pane getGalleryView() {
        return new Pane();
    }
}
