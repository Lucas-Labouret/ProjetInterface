package org.example.projetjardinage.view.mainBody;

import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Specimen;

public class SpecimenView implements Body {
    private static SpecimenView instance;

    private Specimen specimen;

    private SpecimenView(Specimen p){
        specimen = p;
    }

    public static SpecimenView getInstance(Specimen p) {
        if (instance == null) instance = new SpecimenView(p);
        return instance;
    }

    public void switchSpecimen(Specimen p) {
        specimen = p;
    }

    public Pane getBody() {
        return new Pane();
    }
    public void updateSize(double width, double height) {}
}
