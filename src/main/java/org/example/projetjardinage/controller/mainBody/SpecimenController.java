package org.example.projetjardinage.controller.mainBody;

import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Specimen;

public class SpecimenController implements Body {
    private Specimen specimen;

    public SpecimenController(Specimen p){
        specimen = p;
    }

    public void switchSpecimen(Specimen p) {
        specimen = p;
    }

    public Pane getBody() {
        return new Pane();
    }
    public void updateSize(double width, double height) {}
}
