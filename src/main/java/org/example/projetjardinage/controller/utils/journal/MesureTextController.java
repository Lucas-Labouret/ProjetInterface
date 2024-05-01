package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.mesures.MesureText;

public class MesureTextController extends MesureController {
    @FXML private Label name;
    @FXML private TextField field;
    @FXML private Label unit;

    public MesureTextController(MesureHolder mesure, JournalEntry entry) {
        super(entry, mesure);
    }

    public void initialize() {
        setContextMenu();
        name.setText(mesure.getName());
        unit.setVisible(false);
        field.setText(((MesureText) mesure.getMesure()).getValue());
        field.textProperty().addListener((obs, old, newValue) -> {
            ((MesureText) mesure.getMesure()).setText(newValue);
        });
    }
}
