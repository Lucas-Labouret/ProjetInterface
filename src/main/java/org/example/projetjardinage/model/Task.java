package org.example.projetjardinage.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task implements Observable {
    private LocalDate dueDate;
    private String name;
    private String description;
    private boolean done;
    private final ArrayList<Task> subTasks = new ArrayList<>();

    private final ArrayList<Specimen> linkedSpecimens = new ArrayList<>();
    private final ArrayList<Species> linkedSpecies = new ArrayList<>();

    public Task(String name, String description, LocalDate dueDate) {
        this.dueDate = dueDate;
        this.name = name;
        this.description = description;
        this.done = false;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void addSubTasks(Task... t) {
        subTasks.addAll(List.of(t));
    }
    public void removeSubTask(Task t) {
        subTasks.remove(t);
    }
    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }
}
