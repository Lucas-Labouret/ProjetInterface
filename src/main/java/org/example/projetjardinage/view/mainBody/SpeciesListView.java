package org.example.projetjardinage.view.mainBody;

import javafx.scene.layout.Pane;

public class SpeciesListView implements Body{
    private static SpeciesListView instance;

    private SpeciesListView(){}

    public static SpeciesListView getInstance(){
        if(instance == null){ instance = new SpeciesListView(); }
        return instance;
    }

    public Pane getBody(){
        return new Pane();
    }
}
