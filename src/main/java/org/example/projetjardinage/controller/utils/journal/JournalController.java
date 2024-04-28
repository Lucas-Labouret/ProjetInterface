package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.control.MenuItem;

import org.example.projetjardinage.model.Journal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JournalController {
    @FXML private Label name;
    @FXML private Button addEntry;
    @FXML private MenuButton dateMenu;
    @FXML private Pane mainPanel;

    private Journal journal;
    private String specimenName;

    public JournalController(Journal journal, String specimenName){
        this.journal = journal;
        this.specimenName = specimenName;
    }

    public void initialize(){
        name.setText(specimenName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (LocalDate date: journal.getSortedDates()){
            String dateStr = date.format(formatter);
            MenuItem item = new MenuItem(dateStr);
            item.setOnAction(e -> {
                dateMenu.setText(dateStr);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/journal/JournalEntry.fxml"));
                    loader.setController(new JournalEntryController(journal.get(date)));
                    mainPanel.getChildren().clear();
                    mainPanel.getChildren().add(loader.load());
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            });
            dateMenu.getItems().add(item);
        }
    }
}
