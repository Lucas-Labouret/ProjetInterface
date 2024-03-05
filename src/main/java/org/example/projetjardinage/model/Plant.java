package org.example.projetjardinage.model;

import java.time.LocalDate;

public class Plant implements Observable {
    private final Species species;
    private String name;
    private boolean alive;
    private LocalDate miseEnTerre;

    private String noteSecimen;
    private String noteEntretien;
    
    public Plant(Species species, String name, LocalDate miseEnTerre) {
        this.species = species;
        this.name = name;
        this.alive = true;
        this.miseEnTerre = miseEnTerre;
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
