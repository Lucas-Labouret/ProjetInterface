package org.example.projetjardinage.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private LocalDate dueDate;
    private String name;
    private String description;
    private Task parent;
    private boolean done;

    private int recurrence;
    private final List<Task> subTasks = new ArrayList<>();

    private final List<Specimen> linkedSpecimens = new ArrayList<>();
    private final List<Species> linkedSpecies = new ArrayList<>();

    public Task(List<String> elem, List<Species> esp, List<Specimen> spe){
        this.name = elem.get(0);
        int done = Integer.parseInt(elem.get(1));
        this.done = (done != 0);
        this.description = elem.get(2);

        String date = elem.get(3);
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(2,4));
        int year = Integer.parseInt(date.substring(4,8));
        this.dueDate = LocalDate.of(year, Month.of(month),day);

        this.recurrence = Integer.parseInt(elem.get(5));

        this.linkedSpecies.addAll(esp);
        this.linkedSpecimens.addAll(spe);

    }

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

    public boolean isRec(){
        if(recurrence == 0){
            return false;
        } else {
            return true;
        }
    }

    private LocalDate nextDate(){
        return dueDate.plusDays(recurrence);
    }

    public void setNextDate(){
        this.dueDate = this.nextDate();
        this.done = false;
    }

    public Task() {
        this.dueDate = null;
        this.name = "";
        this.description = "";
        this.done = false;
        this.parent = null;
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

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Task> getSubTasks() { return subTasks; }
    public void addSubTasks(Task... t) { subTasks.addAll(List.of(t));}
    public void removeSubTasks(Task... t) { subTasks.removeAll(List.of(t)); }

    public List<Species> getLinkedSpecies() { return linkedSpecies; }
    public void addLinkedSpecies(Species... s) { linkedSpecies.addAll(List.of(s)); }
    public void removeLinkedSpecies(Species... s) { linkedSpecies.removeAll(List.of(s)); }

    public List<Specimen> getLinkedSpecimens() { return linkedSpecimens; }
    public void addLinkedSpecimens(Specimen... s) { linkedSpecimens.addAll(List.of(s)); }
    public void removeLinkedSpecimens(Specimen... s) { linkedSpecimens.removeAll(List.of(s)); }
}
