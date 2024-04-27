package org.example.projetjardinage.model.Lists;

import org.example.projetjardinage.model.Task;

import java.util.ArrayList;
import java.util.Comparator;

public class TodoList extends ObservableList<Task> {
    public TodoList() {
        super();
    }

    public TodoList(ArrayList<Task> tasks) {
        super(tasks);
        this.elements.sort(Comparator.comparing(Task::getDueDate));
    }

    public void add(Task... task) {
        super.add(task);
        this.elements.sort(Comparator.comparing(Task::getDueDate));
    }
}
