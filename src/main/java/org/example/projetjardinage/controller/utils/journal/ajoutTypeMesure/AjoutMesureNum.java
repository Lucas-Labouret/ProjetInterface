package org.example.projetjardinage.controller.utils.journal.ajoutTypeMesure;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AjoutMesureNum extends AjoutTypeMesure{
    @FXML private TextField nameField;
    @FXML private TextField unitField;

    public void initialize(){}

    public String getName(){ return nameField.getText(); }
    public String getUnit(){ return unitField.getText(); }
}
