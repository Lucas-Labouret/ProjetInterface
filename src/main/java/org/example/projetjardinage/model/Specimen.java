package org.example.projetjardinage.model;

import java.time.LocalDate;

public class Specimen {
    private final Species species;
    private String name;
    private boolean alive;
    private LocalDate miseEnTerre;

    private String noteSecimen;
    private String noteEntretien;

    private Journal journal;
    
    public Specimen(Species species, String name, LocalDate miseEnTerre) {
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

    public void addJournalEntry(LocalDate date, JournalEntry entry){
        for (LocalDate d: journal.keySet())
            if (date.isEqual(d)) throw new RuntimeException("Date already has an associated entry");

        journal.put(date, entry);
    }
}
