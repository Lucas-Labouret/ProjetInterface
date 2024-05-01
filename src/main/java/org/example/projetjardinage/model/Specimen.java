package org.example.projetjardinage.model;

import org.example.projetjardinage.model.journal.InfoMesure;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.PlageMesure;
import org.example.projetjardinage.model.journal.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

public class Specimen {
    private Species species;
    private String name;
    private String oldName;
    private boolean alive;
    private LocalDate miseEnTerre;



    private String profilePic;

    private String noteSpecimen;
    private String noteEntretien;

    private Journal journal;

    private List<Task> taskList = new ArrayList<>();

    public Specimen(Species species, String name, LocalDate miseEnTerre) {
        this.species = species;
        this.name = name;
        this.alive = true;
        this.miseEnTerre = miseEnTerre;
        this.journal = new Journal(species);
    }

    public Specimen(List<String> elem, Species spe, List<List<String>> journ, List<List<String>> photos){
        this.name = elem.get(0);
        this.oldName = elem.get(0);
        String date = elem.get(1);
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(2,4));
        int year = Integer.parseInt(date.substring(4,8));
        miseEnTerre = LocalDate.of(year, Month.of(month),day);
        int al = Integer.parseInt(elem.get(2));
        this.alive = (al != 0);
        this.profilePic = elem.get(3);
        this.noteSpecimen = elem.get(5);
        this.noteEntretien = elem.get(6);
        this.species = spe;
        this.journal = new Journal(journ,photos, spe.getMesures(), spe);
    }

    public Species getSpecies() {
        return species;
    }

    public Journal getJournal() {
        return journal;
    }

    public PlageMesure getMesuresPoss(){
        return this.species.getMesures();
    }

    public void newJournalEntry(List<String> elem){
        int nb = Integer.parseInt(elem.get(0));


    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getProfilePic() {
        return profilePic;
    }
    public boolean isAlive(){ return alive; }
    public void setStatus(boolean alive){ this.alive = alive; }

    public void addJournalEntry(LocalDate date, org.example.projetjardinage.model.journal.JournalEntry entry){
        for (LocalDate d: journal.keySet())
            if (date.isEqual(d)) throw new RuntimeException("Date already has an associated entry");

        journal.put(date, entry);
    }

    public void addTask(Task tache){this.taskList.add(tache);}

    public List<MesureHolder> getMoyenne(InfoMesure info){
        List <MesureHolder> mesures = new ArrayList<>();
        for(org.example.projetjardinage.model.journal.JournalEntry journ : this.journal.values()){
            MesureHolder mes = journ.getMesureInfo(info);
            if(!((mes.getMesure()).toString().equals("<Echec>"))){
                mesures.add(mes);
            }
        }
        return mesures;
    }
}
