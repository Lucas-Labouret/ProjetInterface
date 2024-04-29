package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.example.projetjardinage.model.mesure.MesureHolder;
import org.example.projetjardinage.model.mesure.MesureList;

public class MesureListController {
    @FXML private Label name;
    @FXML private MenuButton menu;

    private MesureHolder mesure;

    public MesureListController(MesureHolder mesure) {
        this.mesure = mesure;
    }

    public void initialize() {
        name.setText(mesure.getName());
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
