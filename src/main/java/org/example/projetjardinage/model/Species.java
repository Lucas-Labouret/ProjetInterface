package org.example.projetjardinage.model;

import org.example.projetjardinage.model.mesure.OptimalHolder;

import java.util.ArrayList;
import java.util.List;

public class Species implements Observable {
    private String name;
    private boolean favorite;
    private String profilePic;

    private String notes;
    private final ArrayList<Specimen> specimens = new ArrayList<>();
    private final ArrayList<Task> taskList = new ArrayList<>();

    private OptimalHolder mesuresOpti ;

    public Species(List<String> elem){
        this.name = elem.get(0);
        int fav = Integer.parseInt((elem.get(1)));
        if(fav == 0){
            this.favorite = false;
        } else {
            this.favorite = true;
        }
        this.profilePic = elem.get(2);
        this.notes = elem.get(3);
        List<String> opti = elem.subList(4,8);
        opti.add(elem.get(8));
        this.mesuresOpti = new OptimalHolder(opti);
    }

    int getNbSpecimens() { return specimens.size(); }
    public void addSpecimen(Specimen p){ specimens.add(p); }

    public List<Specimen> getSpe(){return this.specimens;}

    public String getName(){return name;}

    public ArrayList<Task> getRelatedTasks() {
        return taskList;
    }
}
