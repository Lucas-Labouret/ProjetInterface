package org.example.projetjardinage.model;

import org.example.projetjardinage.model.mesure.OptimalHolder;

import java.util.ArrayList;
import java.util.List;

public class Species {
    private String name;
    private boolean favorite;
    private String profilePic;
    private final ArrayList<Specimen> specimens = new ArrayList<>();
    private final ArrayList<Task> taskList = new ArrayList<>();

    private final OptimalHolder mesuresOpti = new OptimalHolder();

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
