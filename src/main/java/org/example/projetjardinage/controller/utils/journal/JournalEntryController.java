package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.model.JournalEntry;
import org.example.projetjardinage.model.mesure.MesureHolder;

public class JournalEntryController {
    @FXML VBox mesureBox;
    @FXML TextArea notes;
    @FXML CheckBox rempotter;
    @FXML CheckBox couper;
    @FXML CheckBox recolter;
    @FXML HBox photoBox;


    private JournalEntry journalEntry;

    public JournalEntryController(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }

    public void initialize() {
        for (MesureHolder mesure : journalEntry.getMesures()) {
            switch (mesure.getType()){
                case Numeric -> {}
            }
        }
    }
}
