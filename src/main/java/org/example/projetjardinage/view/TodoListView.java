package org.example.projetjardinage.view;

import javafx.scene.layout.Pane;

public class TodoListView {
    private static TodoListView instance;

    private TodoListView() {}

    public static TodoListView getInstance() {
        if (instance == null) instance = new TodoListView();
        return instance;
    }

    public Pane getTodoListView() {
        return new Pane();
    }
}
