package org.example.projetjardinage.model;

import org.example.projetjardinage.model.journal.OptimalHolder;
import org.example.projetjardinage.model.journal.PlageMesure;

import java.util.ArrayList;
import java.util.List;

public class Species extends Observable {
    private String name;
    private String oldName;
    private boolean favorite;
    private String profilePic;

    private String notes;
    private final ArrayList<Specimen> specimens = new ArrayList<>();
    private final ArrayList<Task> taskList = new ArrayList<>();

    private OptimalHolder mesuresOpti ;

    private PlageMesure mesuresPoss;

    public Species(String name) {
        this.name = name;
    }

    public Species(List<String> elem){
        this.name = elem.get(0);
        this.oldName = elem.get(0);
        int fav = Integer.parseInt((elem.get(1)));
        this.favorite = (fav != 0);
        this.profilePic = elem.get(2);
        this.notes = elem.get(3);
        List<String> opti = elem.subList(4,9);
        this.mesuresOpti = new OptimalHolder(opti);
        this.mesuresPoss = new PlageMesure();
        int nbNouvellesMesures = Integer.parseInt(elem.get(9));
        for(int i = 0; i<nbNouvellesMesures*3;i=i+3){
            this.mesuresPoss.addMesure(elem.get(10+i),elem.get(10+i+1),elem.get(10+i+2));
        }
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        sendNotif();
    }

    public String getOldName() {
        return oldName;
    }
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }



    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
    public boolean getFavorite() { return this.favorite; }
    public String getProfilePicURL() { return profilePic; }

    public void setProfilePicURL(String profilePic) { this.profilePic = profilePic; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getNbSpecimens() { return specimens.size(); }
    public void addSpecimens(Specimen... p){ specimens.addAll(List.of(p)); }
    public void removeSpecimens(Specimen... p){ specimens.removeAll(List.of(p)); }
    public ArrayList<Specimen> getSpecimens() { return specimens; }

    public void addTasks(Task... t) { taskList.addAll(List.of(t)); }
    public void removeTasks(Task... t) { taskList.removeAll(List.of(t)); }
    public ArrayList<Task> getRelatedTasks() {
        return taskList;
    }

    public OptimalHolder getMesuresOpti() { return mesuresOpti; }

    public PlageMesure getMesures(){ return this.mesuresPoss;}

    public int getNbMesures(){return this.mesuresPoss.getTaille();}

    public PlageMesure getMesuresInfos(){return this.mesuresPoss;}
}
