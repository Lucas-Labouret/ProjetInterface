package org.example.projetjardinage.model;

import org.example.projetjardinage.model.mesure.OptimalHolder;

import java.util.ArrayList;

public class Species implements Observable {
    private String name;
    private boolean favorite;
    private String profilePic;
    private final ArrayList<Plant> specimens = new ArrayList<>();
    private final ArrayList<Task> taskList = new ArrayList<>();

    private final OptimalHolder mesuresOpti = new OptimalHolder();

    int getNbSpecimens() { return specimens.size(); }
    void addSpecimen(Plant p){ specimens.add(p); }
}
