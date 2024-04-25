package org.example.projetjardinage.controller.mainBody;


import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

public class SpeciesListController implements BodyController{
    @FXML private TextField RechercheEspece;
    @FXML private HBox HBox;
    @FXML private VBox VBox1;
    @FXML private VBox VBox2;
    @FXML private VBox VBox3;

    public SpeciesListController(){}

    public void update() {}
    public void updateSize(double width, double height) {}
}

