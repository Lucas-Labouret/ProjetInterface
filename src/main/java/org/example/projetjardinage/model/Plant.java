package org.example.projetjardinage.model;

public class Plant implements Observable {
    private final Species species;
    private String name;
    
    public Plant(Species species, String name) {
        this.species = species;
        this.name = name;
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
}
