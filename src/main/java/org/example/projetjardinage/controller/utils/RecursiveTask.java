package org.example.projetjardinage.controller.utils;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.Lists.TodoList;

import java.io.IOException;
import java.util.ArrayList;

public class RecursiveTask extends Observer {
    @FXML private CheckBox check;
    @FXML private TitledPane pane;
    @FXML private VBox box;
    @FXML private TextArea description;

    private final Task task;
    private final TodoList todoList;
    int depth;
    boolean open = false;

    double lastWidth;
    double lastHeight;

    private final ArrayList<RecursiveTask> recursiveTasks;

    public RecursiveTask(Task task, TodoList todoList,int depth) {
        recursiveTasks = new ArrayList<>();
        this.depth = depth;
        this.task = task;
        this.todoList = todoList;
        this.subscribeTo(task);
    }

    public Task getTask() { return task; }

    public void initialize() {
        pane.setExpanded(open);

        check.setOnAction(e -> setDone(!task.isDone()));

        ChangeListener<Boolean> openedListener =
                (observable, oldValue, newValue) -> open = newValue;
        pane.expandedProperty().addListener(openedListener);

        pane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                pane.setExpanded(true);
                TaskPopUp.newTaskPopUp(task, todoList);
            }
        });

        pane.setText(task.getName() + " - " + task.getDueDate().getDayOfMonth() + " " + task.getMonthName() + " " + task.getDueDate().getYear());
        check.setSelected(task.isDone());
        description.setText(task.getDescription());

        for (Task subTask : task.getSubTasks()) loadSubTask(subTask);

        updateSize(lastWidth, lastHeight);
    }

    public void setDone(boolean done) {
        if (done) {
            if (!task.allSubTasksDone()) {
                ValidationPrompt validationPrompt = ValidationPrompt.newValidationPrompt(
                        "Il reste des sous-tâches non terminées. Si vous continuez, elles seront toutes marquées comme terminées."
                );
                validationPrompt.getStage().setOnHidden(e -> {
                    if (validationPrompt.getResult()) {
                        task.setDone(true);
                    } else {
                        check.setSelected(false);
                    }
                });
            }
        } else {
            task.setDone(false);
        }
    }

    public void recursivelyCollapse() {
        pane.setExpanded(false);
        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.recursivelyCollapse();
        }
    }
    public void recursivelyExpand() {
        pane.setExpanded(true);
        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.recursivelyExpand();
        }
    }

    private void loadSubTask(Task subTask) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
        RecursiveTask recursiveTask = new RecursiveTask(subTask, todoList, depth + 1);
        loader.setController(recursiveTask);
        Parent view;
        try { view = loader.load(); }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        recursiveTasks.add(recursiveTask);
        box.getChildren().add(view);
    }

    public void updateSize(double width, double height) {
        lastWidth = width;
        lastHeight = height;
        pane.setPrefWidth(width - 80*depth - 120);
        description.setPrefWidth(width - 80*depth - 160);

        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.updateSize(width, height);
        }
    }

    public void update() {
        ArrayList<Task> knownTasks = new ArrayList<>();
        for (RecursiveTask recursiveTask : recursiveTasks) {
            knownTasks.add(recursiveTask.getTask());
        }
        for (Task subTask : task.getSubTasks()) {
            if (!knownTasks.contains(subTask)) loadSubTask(subTask);
        }

        if (depth == 0) pane.setText(task.getName());
        else pane.setText(task.getName() + " - " + task.getDueDate().getDayOfMonth() + " " + task.getMonthName() + " " + task.getDueDate().getYear());

        check.setSelected(task.isDone());
        description.setText(task.getDescription());

        TextArea description = (TextArea) box.getChildren().getFirst();
        box.getChildren().clear();
        box.getChildren().add(description);

        ArrayList<RecursiveTask> toRemove = new ArrayList<>();
        for (RecursiveTask recursiveTask : recursiveTasks) {
            if (!task.getSubTasks().contains(recursiveTask.getTask())) {
                toRemove.add(recursiveTask);
            }
        }
        recursiveTasks.removeAll(toRemove);

        for (RecursiveTask recursiveTask: recursiveTasks) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
            loader.setController(recursiveTask);
            try { box.getChildren().add(loader.load()); }
            catch (IOException e) { e.printStackTrace(); }
        }
        updateSize(lastWidth, lastHeight);
    }
}
