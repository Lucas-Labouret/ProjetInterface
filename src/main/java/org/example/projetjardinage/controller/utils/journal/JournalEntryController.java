package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.journal.InfoMesure;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

import java.io.InputStream;
import java.util.ArrayList;

public class JournalEntryController extends Observer {
    @FXML VBox mesureBox;
    @FXML MenuButton addMesure;
    @FXML
    Button deleteAll;
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

            deleteAll.setOnAction(e -> {
                journalEntry.getMesures().clear();
                update();
            });

            for (String image : journalEntry.getImages()) {
                InputStream img = getClass().getResourceAsStream(image);
                if (img == null) continue;
                ImageView imageView = new ImageView(new Image(img));
                try { photoBox.getChildren().add(imageView); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }

        MenuItem nouvelle = new MenuItem("Nouvelle mesure");
        nouvelle.setOnAction(e -> {
            System.out.println("Pas implémenté :3");
        });
        addMesure.getItems().add(nouvelle);

        ArrayList<String> noms = new ArrayList<>();
        for (MesureController mesureController : mesureControllers) {
            noms.add(mesureController.getMesureName());
        }
        for (InfoMesure info: journalEntry.getSpecies().getMesuresInfos().getAll()) {
            if (!noms.contains(info.getName())) {
                MenuItem item = getMenuItem(info);
                addMesure.getItems().add(item);
            }
        }
    }

    private  MenuItem getMenuItem(InfoMesure info) {
        MenuItem item = new MenuItem();
        item.setText(info.getName());
        item.setOnAction(e -> {
            switch (info.getType()){
                case Numeric -> journalEntry.addMesure(MesureHolder.newMesureNumerique(info.getName(), (float) 0, info.getUnit()));
                case Bool -> journalEntry.addMesure(MesureHolder.newMesureBool(info.getName(), false));
                case Scale -> {
                    ArrayList<String> specificInfos = info.getSpecificInfos();
                    journalEntry.addMesure(MesureHolder.newMesureScale(
                            info.getName(),
                            Integer.parseInt(specificInfos.get(2)),
                            Integer.parseInt(specificInfos.get(0)),
                            Integer.parseInt(specificInfos.get(1))
                    ));
                }
                case List -> {
                    ArrayList<String> specificInfos = info.getSpecificInfos();
                    journalEntry.addMesure(MesureHolder.newMesureList(
                            info.getName(),
                            specificInfos
                    ));
                }
                case Text -> journalEntry.addMesure(MesureHolder.newMesureText(info.getName(), ""));
            }
            update();
        });
        return item;
    }

    public void update() {
        mesureBox.getChildren().clear();
        addMesure.getItems().clear();
        mesureControllers.clear();
        initialize();
    }
}
