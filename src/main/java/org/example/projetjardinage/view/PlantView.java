package org.example.projetjardinage.view;

import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Plant;
import org.example.projetjardinage.model.Species;

public class PlantView {
    private static PlantView instance;

    private Plant plant;
    private Species species;

    private PlantView(Plant p){
        plant = p;
        species = p.getSpecies();
    }

    public static PlantView getInstance(Plant p) {
        if (instance == null) instance = new PlantView(p);
        return instance;
    }

    public void switchPlant(Plant p) {
        plant = p;
        species = p.getSpecies();
    }

    public Pane getPlantView() {
        return new Pane();
    }
}
