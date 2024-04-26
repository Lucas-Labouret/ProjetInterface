package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import org.example.projetjardinage.model.Task;

import java.io.IOException;
import java.util.ArrayList;

public class RecursiveTask {
    @FXML private CheckBox check;
    @FXML private TitledPane pane;
    @FXML private VBox box;
    @FXML private TextArea description;

    private Task task;
    int depth;
    boolean open = false;

    public RecursiveTask(Task task, int depth) {
        this.depth = depth;
        this.task = task;
    }

    public RecursiveTask(Task task, int depth, boolean open) {
        this.depth = depth;
        this.task = task;
        this.open = open;
    }

    private ArrayList<RecursiveTask> recursiveTasks;

    public void initialize() {
        recursiveTasks = new ArrayList<>();

        check.setSelected(task.isDone());
        pane.setText(task.getName());
        pane.setExpanded(open);
        description.setText(task.getDescription());

        check.setOnAction(e -> task.setDone(check.isSelected()));

        pane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                open = !open;
                System.out.println(open);
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                System.out.println("Right click " + open);
                pane.setExpanded(open);

                Stage stage = new Stage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/TaskPopUp.fxml"));
                TaskPopUp taskPopUp = new TaskPopUp(stage, task);
                loader.setController(taskPopUp);

                Parent taskPopUpView = null;
                try { taskPopUpView = loader.load(); }
                catch (IOException ex) { ex.printStackTrace(); }
                if (taskPopUpView == null) throw new AssertionError();

                Scene scene = new Scene(taskPopUpView);
                stage.setScene(scene);
                stage.show();
            }
        });

        for (Task subTask : task.getSubTasks()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
            RecursiveTask recursiveTask = new RecursiveTask(subTask, depth + 1);
            recursiveTasks.add(recursiveTask);
            loader.setController(recursiveTask);
            try { box.getChildren().add(loader.load()); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void updateSize(double width, double height) {
        pane.setPrefWidth(width - 80*depth -120);

        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.updateSize(width, height);
        }
    }
}
