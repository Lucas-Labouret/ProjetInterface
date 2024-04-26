package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TaskPopUp {
    private final Task task;
    private final Task dummy = new Task();
    private final Stage stage;

    private boolean creation = false;
    private boolean validated = false;

    @FXML private Button editButton;

    @FXML private TextField name;
    @FXML private TextArea description;
    @FXML private DatePicker datePicker;

    @FXML private MenuButton speciesMenu;
    @FXML private HBox speciesZone;

    @FXML private MenuButton specimenMenu;
    @FXML private HBox specimenZone;

    @FXML private RadioButton radioNo;
    @FXML private RadioButton radioYes;

    @FXML private Button ajouter;
    @FXML private Button supprimer;
    @FXML private Button valider;

    public TaskPopUp(Stage stage, Task task, boolean creation) {
        this.task = task;
        this.stage = stage;
        this.creation = creation;
        fillDummy();
    }

    public TaskPopUp(Stage stage, Task task) {
        this.task = task;
        this.stage = stage;
        fillDummy();
    }

    public TaskPopUp(Stage stage) {
        task = new Task();
        this.stage = stage;
        fillDummy();
    }

    private void refill(Task t1, Task t2) {
        t2.setParent(t1.getParent());
        t2.setName(t1.getName());
        t2.setDescription(t1.getDescription());
        t2.setDueDate(t1.getDueDate());
        t2.setDone(t1.isDone());
        t2.addLinkedSpecies(t1.getLinkedSpecies().toArray(new Species[0]));
        t2.addLinkedSpecimens(t1.getLinkedSpecimens().toArray(new Specimen[0]));
    }

    private void fillDummy() {
        refill(task, dummy);
    }

    private void validateChanges() {
        refill(dummy, task);
    }

    public void initialize() {
        name.setText(dummy.getName());
        description.setText(dummy.getDescription());
        datePicker.setValue(dummy.getDueDate());

        if (creation) {
            editButton.setDisable(true);
            editButton.setVisible(false);

            supprimer.setDisable(true);
            supprimer.setVisible(false);

            ajouter.setDisable(true);
            ajouter.setVisible(false);

            name.setEditable(true);
            description.setEditable(true);
            datePicker.setDisable(false);
            radioYes.setDisable(false);
            radioNo.setDisable(false);
        }

        ToggleGroup rec = new ToggleGroup();
        radioYes.setToggleGroup(rec);
        radioNo.setToggleGroup(rec);
        radioNo.setSelected(true);

        for (Species species : dummy.getLinkedSpecies()) {
            Button button = getSpeciesButton(species);
            speciesZone.getChildren().add(button);

            MenuItem item = getSpeciesItem(species);
            speciesMenu.getItems().add(item);
        }

        for (Specimen specimen : dummy.getLinkedSpecimens()) {
            Button button = getSpecimenButton(specimen);
            specimenZone.getChildren().add(button);

            MenuItem item = getSpecimenItem(specimen);
            specimenMenu.getItems().add(item);
        }

        editButton.setOnAction(e -> {
            name.setEditable(!name.isEditable());
            description.setEditable(!description.isEditable());
            datePicker.setDisable(!datePicker.isDisabled());
            radioYes.setDisable(!radioYes.isDisabled());
            radioNo.setDisable(!radioNo.isDisabled());
        });

        valider.setOnAction(e -> {
            if (!creation) {
                dummy.setName(name.getText());
                dummy.setDescription(description.getText());
                dummy.setDueDate(datePicker.getValue());
                dummy.setDone(radioYes.isSelected());
                validateChanges();
                validated = true;
                stage.close();
            } else{
                if (name.getText().isEmpty()){
                    GlobalData.newAlert("Veuillez renseigner un nom pour la tâche.");
                }
                else if (datePicker.getValue() == null){
                    GlobalData.newAlert("Veuillez renseigner une date pour la tâche.");
                }
                else {
                    if (task != null) task.addSubTasks(dummy);
                    else GlobalData.tasks.addTasks(dummy);
                    stage.close();
                }
            }
        });

        supprimer.setOnAction(e -> {
            if (task.getParent() != null) task.getParent().removeSubTasks(task);
            else GlobalData.tasks.removeTasks(task);
            stage.close();
        });

        ajouter.setOnAction(e -> {
            Stage newStage = new Stage();
            Task newTask = new Task(task);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/TaskPopUp.fxml"));
            TaskPopUp taskPopUp = new TaskPopUp(newStage, newTask, true);
            loader.setController(taskPopUp);

            Parent taskPopUpView = null;
            try { taskPopUpView = loader.load(); }
            catch (IOException ex) { ex.printStackTrace(); }
            if (taskPopUpView == null) throw new AssertionError();

            Scene scene = new Scene(taskPopUpView);
            newStage.setScene(scene);
            newStage.show();

            newStage.setOnHidden(f -> {
                if (taskPopUp.wasValidated()) {
                    task.addSubTasks(newTask);
                    dummy.addSubTasks(newTask);
                }
            });
        });
    }

    public boolean wasValidated() {
        return validated;
    }

    private Button getSpeciesButton(Species species) {
        Button button = new Button(species.getName());
        button.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                stage.close();
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIES);
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                dummy.removeLinkedSpecies(species);
                speciesZone.getChildren().remove(button);
            }
        });
        return button;
    }

    private MenuItem getSpeciesItem(Species species) {
        MenuItem item = new MenuItem(species.getName());
        item.setOnAction(e -> {
            dummy.addLinkedSpecies(species);
            speciesZone.getChildren().add(getSpeciesButton(species));
        });
        return item;
    }

    private Button getSpecimenButton(Specimen specimen) {
        Button button = new Button(specimen.getName());
        button.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                stage.close();
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIMEN);
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                dummy.removeLinkedSpecimens(specimen);
                specimenZone.getChildren().remove(button);
            }
        });
        return button;
    }

    private MenuItem getSpecimenItem(Specimen specimen) {
        MenuItem item = new MenuItem(specimen.getName());
        item.setOnAction(e -> {
            dummy.addLinkedSpecimens(specimen);
            specimenZone.getChildren().add(getSpecimenButton(specimen));
        });
        return item;
    }
}

