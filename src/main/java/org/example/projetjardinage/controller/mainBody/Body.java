package org.example.projetjardinage.controller.mainBody;

import javafx.scene.layout.Pane;

public interface Body {
    Pane getBody();
    void updateSize(double width, double height);
}
