package org.example.projetjardinage.view.mainBody;

import javafx.scene.layout.Pane;

public class GalleryView implements Body {
    private static GalleryView instance;

    private GalleryView() {}

    public static GalleryView getInstance() {
        if (instance == null) instance = new GalleryView();
        return instance;
    }

    public Pane getBody() {
        return new Pane();
    }
}
