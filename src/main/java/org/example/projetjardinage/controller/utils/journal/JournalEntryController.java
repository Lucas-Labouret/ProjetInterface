package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.model.JournalEntry;
import org.example.projetjardinage.model.mesure.*;

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
        FXMLLoader boolLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureBool.fxml"));
        FXMLLoader scaleLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureScale.fxml"));
        FXMLLoader numTextLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureNumText.fxml"));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureList.fxml"));
        for (MesureHolder mesure : journalEntry.getMesures()) {
            switch (mesure.getType()){
                case Numeric -> {
                    numTextLoader.setController(new MesureNumController(mesure));
                    try { mesureBox.getChildren().add(numTextLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Bool -> {
                    boolLoader.setController(new MesureBoolController(mesure));
                    try { mesureBox.getChildren().add(boolLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Scale -> {
                    scaleLoader.setController(new MesureScaleController(mesure));
                    try { mesureBox.getChildren().add(scaleLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case List -> {
                    menuLoader.setController(new MesureListController(mesure));
                    try { mesureBox.getChildren().add(menuLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Text -> {
                    numTextLoader.setController(new MesureTextController(mesure));
                    try { mesureBox.getChildren().add(numTextLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        }
    }
}
