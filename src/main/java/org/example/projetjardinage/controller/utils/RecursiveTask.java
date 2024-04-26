package org.example.projetjardinage.controller.utils;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.Task;

import java.io.IOException;
import java.util.ArrayList;

public class RecursiveTask extends Observer {
    @FXML private CheckBox check;
    @FXML private TitledPane pane;
    @FXML private VBox box;
    @FXML private TextArea description;

    private final Task task;
    int depth;
    boolean open = false;

    private ArrayList<RecursiveTask> recursiveTasks;

    public RecursiveTask(Task task, int depth) {
        this.depth = depth;
        this.task = task;
        this.subscribeTo(task);
        System.out.println("Creating task " + task.getName());
         if (task.getParent() != null) System.out.println(task.getName() + task.OBSERVERS + " " + this + " " + task.getParent().OBSERVERS);
    }

    public RecursiveTask(Task task, int depth, boolean open) {
        this(task, depth);
        this.open = open;
    }

    public Task getTask() { return task; }

    public void initialize() {
        recursiveTasks = new ArrayList<>();
        pane.setExpanded(open);

        check.setOnAction(e -> task.setDone(check.isSelected()));

        ChangeListener<Boolean> openedListener =
                (observable, oldValue, newValue) -> open = newValue;
        pane.expandedProperty().addListener(openedListener);

        pane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                pane.setExpanded(true);
                TaskPopUp.newTaskPopUp(task);
            }
        });

        check.setSelected(task.isDone());
        pane.setText(task.getName());
        description.setText(task.getDescription());

        for (Task subTask : task.getSubTasks()) loadSubTask(subTask);
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
        RecursiveTask recursiveTask = new RecursiveTask(subTask, depth + 1);
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
        pane.setPrefWidth(width - 80*depth - 120);

        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.updateSize(width, height);
        }
    }

    public void update() {
        System.out.println("Updating" + task.getName() + " " + task + " " + task.getParent());

        ArrayList<Task> knownTasks = new ArrayList<>();
        for (RecursiveTask recursiveTask : recursiveTasks) {
            knownTasks.add(recursiveTask.getTask());
        }
        for (Task subTask : task.getSubTasks()) {
            if (!knownTasks.contains(subTask)) loadSubTask(subTask);
        }

        check.setSelected(task.isDone());
        pane.setText(task.getName());
        description.setText(task.getDescription());

        TextArea description = (TextArea) box.getChildren().getFirst();
        box.getChildren().clear();
        box.getChildren().add(description);

        ArrayList<RecursiveTask> toRemove = new ArrayList<>();
        for (RecursiveTask recursiveTask : recursiveTasks) {
            if (!task.getSubTasks().contains(recursiveTask.getTask())) {
                System.out.println("Removing task");
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
    }
}
