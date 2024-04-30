package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import org.example.projetjardinage.model.journal.mesures.MesureBool;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.JournalEntry;

public class MesureBoolController extends MesureController {
    @FXML private Label name;
    @FXML private CheckBox check;


    public MesureBoolController(MesureHolder mesure, JournalEntry entry) {
        super(entry, mesure);
    }

    public void initialize() {
        setContextMenu();
        name.setText(mesure.getName());
        check.setSelected(((MesureBool) mesure.getMesure()).getValue());
        check.selectedProperty().addListener((obs, old, newValue) -> {
            ((MesureBool) mesure.getMesure()).setValue(newValue);
        });
    }
}
