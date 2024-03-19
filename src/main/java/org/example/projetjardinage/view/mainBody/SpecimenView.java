package org.example.projetjardinage.view.mainBody;

import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Species;

public class SpecimenView {
    private static SpecimenView instance;

    private Specimen specimen;
    private Species species;

    private SpecimenView(Specimen p){
        specimen = p;
        species = p.getSpecies();
    }

    public static SpecimenView getInstance(Specimen p) {
        if (instance == null) instance = new SpecimenView(p);
        return instance;
    }

    public void switchPlant(Specimen p) {
        specimen = p;
        species = p.getSpecies();
    }

    public Pane getBody() {
        return new Pane();
    }
}
