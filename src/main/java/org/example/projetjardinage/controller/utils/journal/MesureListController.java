package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.mesures.MesureList;

public class MesureListController extends MesureController {
    @FXML private Label name;
    @FXML private MenuButton menu;

    public MesureListController(MesureHolder mesure, JournalEntry entry) {
        super(entry, mesure);
    }

    public void initialize() {
        setContextMenu();
        name.setText(mesure.getName());
        menu.setText(((MesureList) mesure.getMesure()).getValue());
        for (String type : (((MesureList) mesure.getMesure()).getTypes())) {
            MenuItem item = new MenuItem(type);
            item.setOnAction(e -> {
                menu.setText(type);
                ((MesureList) mesure.getMesure()).setType(type);
            });
            menu.getItems().add(item);
        }
    }
}
