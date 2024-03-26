package org.example.projetjardinage.controller.mainBody;

import javafx.scene.layout.Pane;

public class SpeciesListController implements Body{
    public SpeciesListController(){}

    public Pane getBody(){
        return new Pane();
    }
    public void updateSize(double width, double height) {}
}
