package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import org.example.projetjardinage.model.mesure.MesureBool;
import org.example.projetjardinage.model.mesure.MesureHolder;

public class MesureBoolController {
    @FXML private Label name;
    @FXML private CheckBox check;

    private MesureHolder mesure;

    public MesureBoolController(MesureHolder mesure) {
        this.mesure = mesure;
    }

    public void initialize() {
        name.setText(mesure.getName());
        check.selectedProperty().addListener((obs, old, newValue) -> {
            ((MesureBool) mesure.getMesure()).setValue(newValue);
        });
    }
}
