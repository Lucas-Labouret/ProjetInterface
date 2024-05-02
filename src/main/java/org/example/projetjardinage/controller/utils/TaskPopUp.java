package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import org.example.projetjardinage.model.lists.TodoList;

import java.io.IOException;

public class TaskPopUp {
    public static TaskPopUp newTaskPopUp(Task task, TodoList todoList) {
        return newTaskPopUp(new Stage(), task, todoList, false);
    }

    public static TaskPopUp newTaskPopUp(Stage stage, Task task, TodoList todoList) {
        return newTaskPopUp(stage, task, todoList,false);
    }

    public static TaskPopUp newTaskPopUp(Stage stage, Task task, TodoList todoList,boolean creation) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(GlobalData.primaryStage);

        FXMLLoader loader = new FXMLLoader(TaskPopUp.class.getResource("/utils/TaskPopUp.fxml"));
        TaskPopUp taskPopUp = new TaskPopUp(stage, task, todoList, creation);
        loader.setController(taskPopUp);

        Parent taskPopUpView = null;
        try { taskPopUpView = loader.load(); }
        catch (IOException ex) { ex.printStackTrace(); }
        if (taskPopUpView == null) throw new AssertionError();

        Scene scene = new Scene(taskPopUpView);
        stage.setScene(scene);
        stage.show();

        return taskPopUp;
    }

    private final Task task;
    private final TodoList todoList;
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
    @FXML private TextField recField;
    @FXML private Label recJours;

    @FXML private Button ajouter;
    @FXML private Button supprimer;
    @FXML private Button valider;

    public TaskPopUp(Stage stage, Task task, TodoList todoList,boolean creation) {
        this.task = task;
        this.todoList = todoList;
        this.stage = stage;
        this.creation = creation;
        fillDummy();
    }

    private void refill(Task t1, Task t2) {
        t2.setParent(t1.getParent());
        t2.setName(t1.getName().split("<SEP>")[0]);
        t2.setDescription(t1.getDescription());
        t2.setDueDate(t1.getDueDate());
        t2.setDone(t1.isDone());
        t2.addLinkedSpecies(t1.getLinkedSpecies().toArray(new Species[0]));
        t2.addLinkedSpecimens(t1.getLinkedSpecimens().toArray(new Specimen[0]));
        t2.setRecurrence(t1.getRecurrence());
    }

    private void fillDummy() {
        refill(task, dummy);
    }

    private void validateChanges() {
        refill(dummy, task);
        int i = 0;
        while (true) {
            String newName = task.getName() + "<SEP>" + i;
            boolean found = false;
            for (String name : GlobalData.taskNames) {
                if (newName.equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                task.setName(newName);
                break;
            }
        }
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

        datePicker.setConverter(GlobalData.getDateConverter());

        ToggleGroup rec = new ToggleGroup();
        radioYes.setToggleGroup(rec);
        radioNo.setToggleGroup(rec);
        radioYes.setSelected(task.isRecurrente());
        recJours.setVisible(task.isRecurrente());
        recField.setVisible(task.isRecurrente());
        recField.setText((task.getRecurrence() != null)? task.getRecurrence().toString() : "");
        recField.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) return change;
            return null;
        }));
        rec.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            recJours.setVisible(radioYes.isSelected());
            recField.setVisible(radioYes.isSelected());
        });
        recField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) dummy.setRecurrence(Integer.parseInt(newValue));
            else dummy.setRecurrence(null);
        });

        for (Species species : dummy.getLinkedSpecies()) {
            Button button = getSpeciesButton(species);
            speciesZone.getChildren().add(button);
        }
        for (Species species : GlobalData.species.getElements()) {
            MenuItem item = getSpeciesItem(species);
            speciesMenu.getItems().add(item);
        }

        for (Specimen specimen : dummy.getLinkedSpecimens()) {
            Button button = getSpecimenButton(specimen);
            specimenZone.getChildren().add(button);
        }
        for (Species species : GlobalData.species.getElements()) for (Specimen specimen : species.getSpecimens()){
            MenuItem item = getSpecimenItem(specimen);
            specimenMenu.getItems().add(item);
        }

        editButton.setText("✎");
        editButton.setOnAction(e -> {
            name.setEditable(!name.isEditable());
            description.setEditable(!description.isEditable());
            datePicker.setDisable(!datePicker.isDisabled());
            radioYes.setDisable(!radioYes.isDisabled());
            radioNo.setDisable(!radioNo.isDisabled());
            recField.setEditable(!recField.isEditable());
            editButton.setText(name.isEditable() ? "✔" : "✎");
        });

        valider.setOnAction(e -> {
            dummy.setName(name.getText());
            dummy.setDescription(description.getText());
            dummy.setDueDate(datePicker.getValue());
            if (radioYes.isSelected()) dummy.setRecurrence(
                    recField.getText().isEmpty() ? null : Integer.parseInt(recField.getText())
            );
            validateChanges();
            validated = true;

            if (!creation) {
                stage.close();
                return;
            }

            if (name.getText().isEmpty())
                Alert.newAlert("Veuillez renseigner un nom pour la tâche.");
            else if (datePicker.getValue() == null)
                Alert.newAlert("Veuillez renseigner une date pour la tâche.");
            else {
                if (task.getParent() != null) task.getParent().addSubTasks(task);
                else {
                    GlobalData.tasks.add(task);
                    if (todoList != GlobalData.tasks) todoList.add(task);
                }
                stage.close();
            }
        });

        supprimer.setOnAction(e -> {
            ValidationPrompt validationPrompt = ValidationPrompt.newValidationPrompt(
                    "Voulez-vous vraiment supprimer \""  + task.getName().split("<sep>")[0] + "\" ?"
            );
            validationPrompt.getStage().setOnHidden(f -> {
                if (validationPrompt.getResult()) {
                    if (task.getParent() == null){
                        GlobalData.tasks.removeTasks(task);
                        todoList.removeTasks(task);
                    } else task.getParent().removeSubTasks(task);
                    stage.close();
                }
            });
        });

        ajouter.setOnAction(e -> {
            Stage newStage = new Stage();
            Task newTask = new Task(task);

            TaskPopUp taskPopUp = TaskPopUp.newTaskPopUp(newStage, newTask, todoList,true);

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
            dummy.removeLinkedSpecies(species);
            speciesZone.getChildren().remove(button);
        });
        return button;
    }

    private MenuItem getSpeciesItem(Species species) {
        MenuItem item = new MenuItem(species.getName());
        item.setOnAction(e -> {
            if (!dummy.getLinkedSpecies().contains(species)) {
                dummy.addLinkedSpecies(species);
                speciesZone.getChildren().add(getSpeciesButton(species));
            }
        });
        return item;
    }

    private Button getSpecimenButton(Specimen specimen) {
        Button button = new Button(specimen.getName());
        button.setOnMouseClicked(e -> {
            dummy.removeLinkedSpecimens(specimen);
            specimenZone.getChildren().remove(button);
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

