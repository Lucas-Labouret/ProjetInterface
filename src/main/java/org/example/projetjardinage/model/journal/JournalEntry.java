package org.example.projetjardinage.model.journal;

import org.example.projetjardinage.model.Observable;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

import java.util.ArrayList;
import java.util.List;

public class JournalEntry extends Observable {
    private final Species species;
    private final Journal journal;

    private final ArrayList<MesureHolder> mesures = new ArrayList<>();
    private String notes = "";
    private boolean rempoter = false;
    private boolean couper = false;
    private boolean recolter = false;

    public boolean getRempot(){return rempoter;}
    public boolean getCoup(){return couper;}
    public boolean getRecolt(){return recolter;}


    private List<String> images = new ArrayList<>();

    JournalEntry(Species species, Journal journal) {
        this.species = species;
        this.journal = journal;
        notes = "";
    }

    public JournalEntry(PlageMesure infos, List<String> vals, Species species, List<Boolean> bools,List<String> photos, Journal journal){
        this.species = species;
        this.journal = journal;
        int nb = infos.getTaille();
        for(int i = 0; i < nb;i++){
            if(! (vals.get(i).equals("<N>")) ) {
                mesures.add(new MesureHolder(infos.getAll().get(i), vals.get(i)));
            }
        }
        this.rempoter = bools.get(0);
        this.couper = bools.get(1);
        this.recolter = bools.get(2);
        this.images = photos;
    }

    public JournalEntry(JournalEntry journalEntry) {
        species = journalEntry.getSpecies();
        journal = journalEntry.getJournal();
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
    public void deleteMesure(MesureHolder holder){
        deleteMesure(holder.getName());
    }

    public Journal getJournal() {return journal;}
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

    public List<String> getImages(){return images;}
    public void addImage(String path){
        images.add(path);
        sendNotif();
    }

    public MesureHolder getMesureInfo(InfoMesure info){
        MesureHolder mes = MesureHolder.newMesureText("Echec","dans le recuperage d'entrees");
        for(MesureHolder mesureHolder : mesures){
            if(mesureHolder.getName().equals(info.getName())){
                return mesureHolder;
            }
        }
        return mes;
    }

    public boolean contientMesure(InfoMesure info){
        for(MesureHolder mesureHolder : mesures){
            if(mesureHolder.getName().equals(info.getName())){
                return true;
            }
        }
        return false;
    }
}
