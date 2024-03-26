package org.example.projetjardinage.controller.mainBody;

import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Species;

public class SpeciesController implements BodyController {
    Species species;

    public SpeciesController(){}
    private void initialize(){}

    public void switchSpecies(Species s){
        species = s;
    }

    public void updateSize(double width, double height) {}
}
