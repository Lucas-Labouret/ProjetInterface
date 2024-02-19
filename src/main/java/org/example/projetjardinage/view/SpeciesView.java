package org.example.projetjardinage.view;

import org.example.projetjardinage.model.Species;

public class SpeciesView implements Observer {
    private final Species species;

    SpeciesView(Species s){
        species = s;
        this.subscribeTo(species);
    }

    public void update() {}
}
