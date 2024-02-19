package org.example.projetjardinage.view;

import org.example.projetjardinage.model.Plant;
import org.example.projetjardinage.model.Species;

public class PlantView implements Observer {
    private final Plant plant;
    private final Species species;

    PlantView(Plant p){
        plant = p;
        species = p.getSpecies();
        this.subscribeTo(plant);
        this.subscribeTo(species);
    }

    public void update() {}
}
