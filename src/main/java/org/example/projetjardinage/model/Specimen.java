package org.example.projetjardinage.model;

import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class  Specimen implements Observable {
    private Species species;
    private String name;
    private boolean alive;
    private LocalDate miseEnTerre;

    private String profilePic;

    private String noteSpecimen;
    private String noteEntretien;
    
    public Specimen(Species species, String name, LocalDate miseEnTerre) {
        this.species = species;
        this.name = name;
        this.alive = true;
        this.miseEnTerre = miseEnTerre;
    }

    public Specimen(List<String> elem, Species spe){
        this.name = elem.get(0);
        String date = elem.get(1);
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(2,4));
        int year = Integer.parseInt(date.substring(4,8));
        miseEnTerre.of(year, Month.of(month),day);
        int al = Integer.parseInt(elem.get(2));
        if(al == 0){
            this.alive = false;
        } else {
            this.alive = true;
        }
        this.profilePic = elem.get(3);
        this.noteSpecimen = elem.get(5);
        this.noteEntretien = elem.get(6);
        this.species = spe;
    }

    
    public Species getSpecies() {
        return species;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive(){ return alive; }
    public void setStatus(boolean alive){ this.alive = alive; }
}
