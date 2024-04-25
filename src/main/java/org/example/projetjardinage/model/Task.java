package org.example.projetjardinage.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private LocalDate dueDate;
    private String name;
    private String description;
    private Task parent;
    private boolean done;
    private final ArrayList<Task> subTasks = new ArrayList<>();

    private final ArrayList<Specimen> linkedSpecimens = new ArrayList<>();
    private final ArrayList<Species> linkedSpecies = new ArrayList<>();

    public Task(Task parent) {
        this();
        this.parent = parent;
    }

    public Task(String name, String description) {
        this.dueDate = null;
        this.name = name;
        this.description = description;
        this.done = false;
    }

    public Task(String name, String description, LocalDate dueDate) {
        this.dueDate = dueDate;
        this.name = name;
        this.description = description;
        this.done = false;
        this.parent = null;
    }

    public Task() {
        this.dueDate = null;
        this.name = "";
        this.description = "";
        this.done = false;
        this.parent = null;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Task(String name, String description, LocalDate dueDate, boolean done, ArrayList<Specimen> Spe, ArrayList<Species> Esp){
        this.dueDate = dueDate;
        this.name = name;
        this.description = description;
        this.done = done;
        this.linkedSpecies.addAll(Esp);
        this.linkedSpecimens.addAll(Spe);
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

    public Task getParent() { return parent; }
    public void setParent(Task parent) { this.parent = parent; }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public ArrayList<Task> getSubTasks() { return subTasks; }
    public void addSubTasks(Task... t) { subTasks.addAll(List.of(t));}
    public void removeSubTasks(Task... t) { subTasks.removeAll(List.of(t)); }

    public ArrayList<Species> getLinkedSpecies() { return linkedSpecies; }
    public void addLinkedSpecies(Species... s) { linkedSpecies.addAll(List.of(s)); }
    public void removeLinkedSpecies(Species... s) { linkedSpecies.removeAll(List.of(s)); }

    public ArrayList<Specimen> getLinkedSpecimens() { return linkedSpecimens; }
    public void addLinkedSpecimens(Specimen... s) { linkedSpecimens.addAll(List.of(s)); }
    public void removeLinkedSpecimens(Specimen... s) { linkedSpecimens.removeAll(List.of(s)); }
}
