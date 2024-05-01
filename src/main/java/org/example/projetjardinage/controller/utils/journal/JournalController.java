package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.utils.ValidationPrompt;
import org.example.projetjardinage.model.journal.Journal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JournalController {
    @FXML private Label name;
    @FXML private DatePicker newDate;
    @FXML private Button addEntry;
    @FXML private MenuButton dateMenu;
    @FXML private Button delete;
    @FXML private Pane mainPanel;

    private Journal journal;
    private String specimenName;

    public JournalController(Journal journal, String specimenName){
        this.journal = journal;
        this.specimenName = specimenName;
    }

    public void initialize(){
        name.setText(specimenName);
        newDate.setValue(LocalDate.now());
        newDate.setConverter(GlobalData.getDateConverter());

        addEntry.setOnAction(e -> {
            if (journal.containsKey(newDate.getValue())){
                ValidationPrompt prompt = ValidationPrompt.newValidationPrompt(
                        "An entry already exists for this date. Do you want to overwrite it?"
                );
                prompt.getStage().setOnHidden(event -> {
                    if (prompt.getResult()){
                        journal.newEntry(newDate.getValue());
                    }
                });
            } else {
                journal.newEntry(newDate.getValue());
            }
            switchToEntry(newDate.getValue());
            addItemToMenu(newDate.getValue());
        });

        delete.setOnAction(e -> {
            ValidationPrompt prompt = ValidationPrompt.newValidationPrompt(
                    "Are you sure you want to delete this entry?"
            );
            prompt.getStage().setOnHidden(event -> {
                if (prompt.getResult()){
                    String[] date = dateMenu.getText().split("/");
                    int day = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int year = Integer.parseInt(date[2]);
                    LocalDate dateToDelete = LocalDate.of(year, month, day);
                    journal.remove(dateToDelete);
                    dateMenu.getItems().removeIf(item -> item.getText().equals(dateMenu.getText()));
                    if (journal.isEmpty()) mainPanel.getChildren().clear();
                    else switchToEntry(journal.getSortedDates().get(journal.getSortedDates().size() - 1));
                }
            });
        });

        for (LocalDate date: journal.getSortedDates().reversed()){
            addItemToMenu(date);
        }

        dateMenu.setText(journal.getSortedDates().get(journal.getSortedDates().size() - 1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        switchToEntry(journal.getSortedDates().get(journal.getSortedDates().size() - 1));
    }

    private void addItemToMenu(LocalDate date){
        String dateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        MenuItem item = new MenuItem(dateStr);
        item.setOnAction(e -> {
            dateMenu.setText(dateStr);
            switchToEntry(date);
        });
        dateMenu.getItems().add(item);
    }

    private void switchToEntry(LocalDate date){
        dateMenu.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/journal/JournalEntry.fxml"));
            loader.setController(new JournalEntryController(journal.get(date)));
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(loader.load());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
