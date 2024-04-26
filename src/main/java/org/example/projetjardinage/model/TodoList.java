package org.example.projetjardinage.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TodoList implements Observable {
    ArrayList<Task> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
    }

    public TodoList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.tasks.sort(Comparator.comparing(Task::getDueDate));
    }

    public ArrayList<Task> getTasks() { return tasks; }
    public void addTasks(Task... t) {
        tasks.addAll(List.of(t));
        tasks.sort(Comparator.comparing(Task::getDueDate));
        sendNotif();
    }
    public void removeTasks(Task... t) {
        tasks.removeAll(List.of(t));
        sendNotif();
    }
}
