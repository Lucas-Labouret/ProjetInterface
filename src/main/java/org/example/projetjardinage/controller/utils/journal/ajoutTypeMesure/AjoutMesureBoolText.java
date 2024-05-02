package org.example.projetjardinage.controller.utils.journal.ajoutTypeMesure;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AjoutMesureBoolText extends AjoutTypeMesure {
    @FXML private TextField nameField;

    public void initialize(){}

    public String getName(){ return nameField.getText(); }
    public String getUnit(){ return ""; }
}
