package org.example.projetjardinage.model;

import org.example.projetjardinage.model.mesure.OptimalHolder;

import java.util.ArrayList;

public class Species implements Observable {
    private String name;
    private boolean favorite;
    private String profilePic;
    private final ArrayList<Specimen> specimens = new ArrayList<>();
    private final ArrayList<Task> taskList = new ArrayList<>();

    private final OptimalHolder mesuresOpti = new OptimalHolder();

    int getNbSpecimens() { return specimens.size(); }
    void addSpecimen(Specimen p){ specimens.add(p); }

    public ArrayList<Task> getRelatedTasks() {
        return taskList;
    }
}
