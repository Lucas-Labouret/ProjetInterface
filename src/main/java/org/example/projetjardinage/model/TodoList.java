package org.example.projetjardinage.model;

import java.util.ArrayList;

public class TodoList implements Observable {
    ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void removeTask(Task t) {
        tasks.remove(t);
    }
}
