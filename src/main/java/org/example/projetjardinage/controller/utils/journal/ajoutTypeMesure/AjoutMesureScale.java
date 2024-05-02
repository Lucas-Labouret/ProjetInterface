package org.example.projetjardinage.controller.utils.journal.ajoutTypeMesure;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AjoutMesureScale extends AjoutTypeMesure {
    @FXML private TextField nameField;
    @FXML private TextField minField;
    @FXML private TextField maxField;

    public void initialize(){}

    public String getName(){ return nameField.getText(); }
    public String getUnit(){ return minField.getText() + "<SEP>" + maxField.getText(); }
}
