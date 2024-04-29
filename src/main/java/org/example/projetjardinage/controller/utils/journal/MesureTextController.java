package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.projetjardinage.model.mesure.MesureHolder;
import org.example.projetjardinage.model.mesure.MesureText;

public class MesureTextController {
    @FXML private Label name;
    @FXML private TextField field;
    @FXML private Label unit;

    private MesureHolder mesure;

    public MesureTextController(MesureHolder mesure) {
        this.mesure = mesure;
    }

    public void initialize() {
        name.setText(mesure.getName());
        unit.setVisible(false);
        field.setText(((MesureText) mesure.getMesure()).getText());
        field.textProperty().addListener((obs, old, newValue) -> {
            ((MesureText) mesure.getMesure()).setText(newValue);
        });
    }
}
