package org.example.projetjardinage.controller.mainBody;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GalleryController implements Body {
    public GalleryController() {}

    public Pane getBody() {
        Pane pane = new Pane();
        pane.getChildren().add(new Label("Galerie"));
        return pane;
    }

    public void updateSize(double width, double height) {}
}
