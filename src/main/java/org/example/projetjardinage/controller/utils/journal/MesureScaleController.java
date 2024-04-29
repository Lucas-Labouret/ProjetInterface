package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.example.projetjardinage.model.mesure.MesureHolder;
import org.example.projetjardinage.model.mesure.MesureScale;

public class MesureScaleController {
    @FXML private Label name;
    @FXML private Slider slider;

    private MesureHolder mesure;

    public MesureScaleController(MesureHolder mesure) {
        this.mesure = mesure;
    }

    public void initialize() {
        name.setText(mesure.getName());
        slider.setMin(((MesureScale) mesure.getMesure()).getMin());
        slider.setMax(((MesureScale) mesure.getMesure()).getMax());
        slider.valueProperty().addListener((obs, old, newValue) -> {
            ((MesureScale) mesure.getMesure()).setNiveau(newValue.intValue());
        });
    }
}
