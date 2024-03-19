package org.example.projetjardinage.view.mainBody;

import javafx.scene.layout.Pane;

public class TodoListView implements Body {
    private static TodoListView instance;

    private TodoListView() {}

    public static TodoListView getInstance() {
        if (instance == null) instance = new TodoListView();
        return instance;
    }

    public Pane getBody() {
        return new Pane();
    }
}
