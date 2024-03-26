package org.example.projetjardinage.controller.mainBody;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TodoListController implements Body {
    public TodoListController() {}

    public Pane getBody() {
        Pane pane = new Pane();
        pane.getChildren().add(new Label("To-do List"));
        return pane;
    }

    public void updateSize(double width, double height) {}
}
