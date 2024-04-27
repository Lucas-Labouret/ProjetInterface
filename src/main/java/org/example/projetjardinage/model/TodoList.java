package org.example.projetjardinage.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TodoList extends Observable {
    @FunctionalInterface
    public interface Filter {
        boolean filter(Task t);
    }

    ArrayList<Task> tasks;
    ArrayList<Filter> filters;

    public TodoList() {
        tasks = new ArrayList<>();
        filters = new ArrayList<>();
    }

    public TodoList(ArrayList<Task> tasks) {
        this();
        this.tasks.addAll(tasks);
        this.tasks.sort(Comparator.comparing(Task::getDueDate));
    }

    public ArrayList<Task> getTasks() { return tasks; }
    public void addTasks(Task... t) {
        for (Task task : t) {
            boolean skip = false;
            for (Filter f : filters) { skip = (skip || !f.filter(task)); }
            if (!skip) tasks.add(task);
        }
        tasks.sort(Comparator.comparing(Task::getDueDate));
        sendNotif();
    }

    public void removeTasks(Task... t) {
        tasks.removeAll(List.of(t));
        sendNotif();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }
}
