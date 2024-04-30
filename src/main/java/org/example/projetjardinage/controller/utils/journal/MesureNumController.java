package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.mesures.MesureNumerique;

public class MesureNumController extends MesureController {
    @FXML private Label name;
    @FXML private TextField field;
    @FXML private Label unit;

    public MesureNumController(MesureHolder mesure, JournalEntry entry) {
        super(entry, mesure);
    }

    public void initialize() {
        setContextMenu();
        name.setText(mesure.getName());
        unit.setText(((MesureNumerique) mesure.getMesure()).getUnit());
        field.setText(Double.toString(((MesureNumerique) mesure.getMesure()).getValue()));
        field.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getText();
            if (text.matches("-?[0-9]*")) return change;
            return null;
        }));
        field.setText(Double.toString(((MesureNumerique) mesure.getMesure()).getValue()));
        field.textProperty().addListener((obs, old, newValue) -> {
            ((MesureNumerique) mesure.getMesure()).setValue(Integer.parseInt(newValue));
        });
    }
}
