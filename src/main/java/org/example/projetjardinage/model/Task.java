package org.example.projetjardinage.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task implements Observable {
    private LocalDate dueDate;
    private String name;
    private String description;
    private boolean done;

    private int recurrence;
    private final List<Task> subTasks = new ArrayList<>();

    private final List<Specimen> linkedSpecimens = new ArrayList<>();
    private final List<Species> linkedSpecies = new ArrayList<>();

    public Task(List<String> elem, List<Species> esp, List<Specimen> spe){
        this.name = elem.get(0);
        int done = Integer.parseInt(elem.get(1));
        if(done == 0){
            this.done = false;
        } else {
            this.done = true;
        }
        this.description = elem.get(2);

        String date = elem.get(3);
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(2,4));
        int year = Integer.parseInt(date.substring(4,8));
        this.dueDate.of(year, Month.of(month),day);

        this.recurrence = Integer.parseInt(elem.get(5));

        for(Species espece : esp){
            this.linkedSpecies.add(espece);
        }
        for(Specimen spec : spe){
            this.linkedSpecimens.add(spec);
        }

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
    }

    public Task(String name, String description, LocalDate dueDate, boolean done, ArrayList<Specimen> Spe, ArrayList<Species> Esp){
        this.dueDate = dueDate;
        this.name = name;
        this.description = description;
        this.done = done;
        for (Species species : Esp) {
            this.linkedSpecies.add(species);
        }
        for (Specimen specimen : Spe) {
            this.linkedSpecimens.add(specimen);
        }
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

    public void addSubTask(Task t) {
        subTasks.add(t);
    }
    public void removeSubTask(Task t) {
        subTasks.remove(t);
    }
    public List<Task> getSubTasks() {
        return subTasks;
    }
}
