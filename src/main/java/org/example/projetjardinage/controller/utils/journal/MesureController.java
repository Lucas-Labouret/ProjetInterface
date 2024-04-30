package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

public abstract class MesureController {
    @FXML protected AnchorPane mainPane;
    final ContextMenu contextMenu = new ContextMenu();

    protected final JournalEntry entry;
    protected final MesureHolder mesure;

    public MesureController(JournalEntry entry, MesureHolder mesure) {
        this.entry = entry;
        this.mesure = mesure;
    }

    protected void setContextMenu() {
        contextMenu.setOnShowing(e -> mainPane.setStyle("-fx-background-color: #e0e0e0"));
        contextMenu.setOnHidden(e -> mainPane.setStyle("-fx-background-color: #f0f0f0"));

        MenuItem delLocal = new MenuItem("Supprimer de cette entrée");
        delLocal.setOnAction(e -> System.out.println("About"));

        MenuItem delGlobal = new MenuItem("Supprimer partout");
        delGlobal.setOnAction(e -> System.out.println("Preferences"));

        contextMenu.getItems().addAll(delLocal, delGlobal);

        mainPane.setOnContextMenuRequested(e -> contextMenu.show(mainPane, e.getScreenX(), e.getScreenY()));

        mainPane.setOnMouseClicked(e -> contextMenu.hide());
    }

    public String getMesureName() {
        return mesure.getName();
    }
}
