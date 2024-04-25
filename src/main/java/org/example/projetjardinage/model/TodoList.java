package org.example.projetjardinage.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
    ArrayList<Task> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
    }

    public TodoList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() { return tasks; }
    public void addTasks(Task... t) { tasks.addAll(List.of(t)); }
    public void removeTasks(Task... t) { tasks.removeAll(List.of(t)); }
}
