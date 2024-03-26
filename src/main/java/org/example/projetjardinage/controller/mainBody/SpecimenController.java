package org.example.projetjardinage.controller.mainBody;

import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Specimen;

public class SpecimenController implements BodyController {
    private Specimen specimen;

    public SpecimenController(){}
    private void initialize(){}

    public void switchSpecimen(Specimen p) {
        specimen = p;
    }

    public void updateSize(double width, double height) {}
}
