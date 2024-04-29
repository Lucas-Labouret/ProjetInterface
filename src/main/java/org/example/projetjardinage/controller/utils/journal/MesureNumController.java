package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.example.projetjardinage.model.mesure.MesureHolder;
import org.example.projetjardinage.model.mesure.MesureNumerique;

public class MesureNumController {
    @FXML private Label name;
    @FXML private TextField field;
    @FXML private Label unit;

    private MesureHolder mesure;

    public MesureNumController(MesureHolder mesure) {
        this.mesure = mesure;
    }

    public void initialize() {
        name.setText(mesure.getName());
        unit.setText(((MesureNumerique) mesure.getMesure()).getUnit());
        field.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getText();
            if (text.matches("-?[0-9]*")) return change;
            return null;
        }));
        field.textProperty().addListener((obs, old, newValue) -> {
            ((MesureNumerique) mesure.getMesure()).setValue(Integer.parseInt(newValue));
        });
    }
}
