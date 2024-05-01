package org.example.projetjardinage.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task extends Observable {
    private LocalDate dueDate;
    private String name;
    private String description;
    private Task parent;
    private boolean done;

    private Integer recurrence = null;
    private final List<Task> subTasks = new ArrayList<>();

    private final List<Specimen> linkedSpecimens = new ArrayList<>();
    private final List<Species> linkedSpecies = new ArrayList<>();

    public Task(List<String> elem, List<Species> esp, List<Specimen> spe, Task surtache){
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

        if( !(Objects.equals(elem.get(4), "<N>") )){
            this.parent = surtache;
        }
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
        for(Task t :this.subTasks){
            t.setNextDate();
        }
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

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
        this.sendNotif();
    }

    public Task getParent() { return parent; }
    public void setParent(Task parent) {
        this.parent = parent;
        this.sendNotif();
    }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        this.sendNotif();
    }

    public boolean allSubTasksDone() {
        if (this.isDone()) return true;
        for (Task subTask : subTasks)
            if (!subTask.isDone()) return false;
        return true;
    }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        if (done) {
            this.done = true;
            for (Task t : subTasks) {
                t.setDone(true);
            }
        } else {
            this.done = false;
            if (this.parent != null) this.parent.setDone(false);
        }
        this.sendNotif();
    }

    public boolean isRecurrente() { return recurrence != null; }
    public Integer getRecurrence() { return recurrence; }
    public void setRecurrence(Integer recurrence) {
        this.recurrence = recurrence;
        this.sendNotif();
    }

    public List<Task> getSubTasks() { return subTasks; }
    public void addSubTasks(Task... t) {
        subTasks.addAll(List.of(t));
        this.sendNotif();
    }
    public void removeSubTasks(Task... t) {
        subTasks.removeAll(List.of(t));
        this.sendNotif();
    }

    public List<Species> getLinkedSpecies() { return linkedSpecies; }
    public void addLinkedSpecies(Species... s) {
        linkedSpecies.addAll(List.of(s));
        this.sendNotif();
    }
    public void removeLinkedSpecies(Species... s) {
        linkedSpecies.removeAll(List.of(s));
        this.sendNotif();
    }

    public List<Specimen> getLinkedSpecimens() { return linkedSpecimens; }
    public void addLinkedSpecimens(Specimen... s) {
        linkedSpecimens.addAll(List.of(s));
        this.sendNotif();
    }
    public void removeLinkedSpecimens(Specimen... s) {
        linkedSpecimens.removeAll(List.of(s));
        this.sendNotif();
    }

    public String getMonthName() {
        String month;
        switch (this.getDueDate().getMonthValue()){
            case 1 -> month = "Janvier";
            case 2 -> month = "Février";
            case 3 -> month = "Mars";
            case 4 -> month = "Avril";
            case 5 -> month = "Mai";
            case 6 -> month = "Juin";
            case 7 -> month = "Juillet";
            case 8 -> month = "Août";
            case 9 -> month = "Septembre";
            case 10 -> month = "Octobre";
            case 11 -> month = "Novembre";
            case 12 -> month = "Décembre";
            default -> throw new IllegalStateException("Unexpected value: " + this.getDueDate().getMonthValue());
        }
        return month;
    }
}
