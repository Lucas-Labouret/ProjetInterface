package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

import java.util.ArrayList;

public class JournalEntryController extends Observer {
    @FXML VBox mesureBox;
    @FXML TextArea notes;
    @FXML CheckBox rempotter;
    @FXML CheckBox couper;
    @FXML CheckBox recolter;
    @FXML HBox photoBox;


    private JournalEntry journalEntry;
    private ArrayList<MesureController> mesureControllers = new ArrayList<>();

    public JournalEntryController(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
        this.subscribeTo(journalEntry);
    }

    public void initialize() {
        notes.setText(journalEntry.getNotes());
        rempotter.setSelected(journalEntry.isRempoter());
        couper.setSelected(journalEntry.isCouper());
        recolter.setSelected(journalEntry.isRecolter());

        for (MesureHolder mesure : journalEntry.getMesures()) {
            switch (mesure.getType()){
                case Numeric -> {
                    FXMLLoader numTextLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureNumText.fxml"));
                    MesureNumController controller = new MesureNumController(mesure, journalEntry);
                    numTextLoader.setController(controller);
                    mesureControllers.add(controller);
                    try { mesureBox.getChildren().add(numTextLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Bool -> {
                    FXMLLoader boolLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureBool.fxml"));
                    MesureBoolController controller = new MesureBoolController(mesure, journalEntry);
                    boolLoader.setController(controller);
                    mesureControllers.add(controller);
                    try { mesureBox.getChildren().add(boolLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Scale -> {
                    FXMLLoader scaleLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureScale.fxml"));
                    MesureScaleController controller = new MesureScaleController(mesure, journalEntry);
                    scaleLoader.setController(controller);
                    mesureControllers.add(controller);
                    try { mesureBox.getChildren().add(scaleLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case List -> {
                    FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureList.fxml"));
                    MesureListController controller = new MesureListController(mesure, journalEntry);
                    menuLoader.setController(controller);
                    mesureControllers.add(controller);
                    try { mesureBox.getChildren().add(menuLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Text -> {
                    FXMLLoader numTextLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureNumText.fxml"));
                    MesureTextController controller = new MesureTextController(mesure, journalEntry);
                    numTextLoader.setController(controller);
                    mesureControllers.add(controller);
                    try { mesureBox.getChildren().add(numTextLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
            }


        }
    }

    public void update() {
        mesureBox.getChildren().clear();
        initialize();
    }
}
