package org.example.projetjardinage.view.mainBody;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TodoListView implements Body {
    private static TodoListView instance;

    private TodoListView() {}

    public static TodoListView getInstance() {
        if (instance == null) instance = new TodoListView();
        return instance;
    }

    public Pane getBody() {
        Pane pane = new Pane();
        pane.getChildren().add(new Label("To-do List"));
        return pane;
    }

    public void updateSize(double width, double height) {}
}
