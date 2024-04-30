package org.example.projetjardinage.model.journal;

import org.example.projetjardinage.model.Observable;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

import java.util.ArrayList;
import java.util.List;

public class JournalEntry extends Observable {
    private final ArrayList<MesureHolder> mesures = new ArrayList<>();
    private final Species species;
    private String notes;
    private boolean rempoter = false;
    private boolean couper = false;
    private boolean recolter = false;

    private List<String> images = new ArrayList<>();

    JournalEntry(Species species) {
        this.species = species;
        notes = "";
    }

    public JournalEntry(PlageMesure infos, List<String> vals, Species species){
        this.species = species;
        int nb = infos.getTaille();
        for(int i = 0; i < nb;i++){
            if(! (vals.get(i).equals("<N>")) ) {
                mesures.add(new MesureHolder(infos.getAll().get(i), vals.get(i)));
            }
        }
    }

    public JournalEntry(JournalEntry journalEntry) {
        notes = "";
        species = journalEntry.getSpecies();
        for (MesureHolder holder: journalEntry.getMesures()){
            mesures.add(new MesureHolder(holder));
        }
    }

    public void deleteMesure(String name){
        for (MesureHolder holder: mesures){
            if (holder.getName().equals(name)){
                mesures.remove(holder);
                sendNotif();
                return;
            }
        }
    }

    public ArrayList<MesureHolder> getMesures() {return mesures;}

    public Species getSpecies() {return species;}

    public String getNotes() {return notes;}
    public void setNotes(String n) {notes = n;}

    public void addMesure(MesureHolder holder){mesures.add(holder);}

    public void setRempoter(boolean r){rempoter = r;}
    public boolean isRempoter(){return rempoter;}

    public void setCouper(boolean c){couper = c;}
    public boolean isCouper(){return couper;}

    public void setRecolter(boolean r){recolter = r;}
    public boolean isRecolter(){return recolter;}

    public void addImage(String path){
        images.add(path);
        sendNotif();
    }
}