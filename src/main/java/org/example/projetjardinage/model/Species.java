package org.example.projetjardinage.model;

import org.example.projetjardinage.model.mesure.OptimalHolder;

import java.util.ArrayList;
import java.util.List;

public class Species {
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public String getProfilePicURL() { return profilePic; }
    public void setProfilePicURL(String profilePic) { this.profilePic = profilePic; }

    int getNbSpecimens() { return specimens.size(); }
    void addSpecimens(Specimen... p){ specimens.addAll(List.of(p)); }
    void removeSpecimens(Specimen... p){ specimens.removeAll(List.of(p)); }
    public ArrayList<Specimen> getSpecimens() { return specimens; }

    public void addTasks(Task... t) { taskList.addAll(List.of(t)); }
    public void removeTasks(Task... t) { taskList.removeAll(List.of(t)); }
    public ArrayList<Task> getRelatedTasks() {
        return taskList;
    }

    public OptimalHolder getMesuresOpti() { return mesuresOpti; }
}
