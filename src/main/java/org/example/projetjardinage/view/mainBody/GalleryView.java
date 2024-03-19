package org.example.projetjardinage.view.mainBody;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GalleryView implements Body {
    private static GalleryView instance;

    private GalleryView() {}

    public static GalleryView getInstance() {
        if (instance == null) instance = new GalleryView();
        return instance;
    }

    public Pane getBody() {
        Pane pane = new Pane();
        pane.getChildren().add(new Label("Galerie"));
        return pane;
    }

    public void updateSize(double width, double height) {}
}
