package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.mesures.MesureScale;

public class MesureScaleController extends MesureController{
    @FXML private Label name;
    @FXML private Slider slider;

    public MesureScaleController(MesureHolder mesure, JournalEntry entry) {
        super(entry, mesure);
    }

    public void initialize() {
        setContextMenu();
        name.setText(mesure.getName());
        slider.setMin(((MesureScale) mesure.getMesure()).getMin());
        slider.setMax(((MesureScale) mesure.getMesure()).getMax());
        slider.setValue(((MesureScale) mesure.getMesure()).getNiveau());
        slider.valueProperty().addListener((obs, old, newValue) -> {
            ((MesureScale) mesure.getMesure()).setNiveau(newValue.intValue());
        });
    }
}
