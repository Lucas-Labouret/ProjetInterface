package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
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

    public RecursiveTask(Task task, int depth) {
        this.depth = depth;
        this.task = task;
    }

    private ArrayList<RecursiveTask> recursiveTasks;

    public void initialize() {
        recursiveTasks = new ArrayList<>();

        check.setSelected(task.isDone());
        pane.setText(task.getName());
        description.setText(task.getDescription());

        check.setOnAction(e -> task.setDone(check.isSelected()));

        pane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
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
/*
        description.toFront();
        description.setVisible(false);
        boolean[] hover = {false, false};
        pane.hoverProperty().addListener((a, b, newValue) -> hover[0] = newValue);
        boxAnchor.hoverProperty().addListener((a, b, newValue) -> hover[1] = newValue);
        pane.setOnMouseMoved(e -> {
            description.setVisible(hover[0] ^ hover[1]);
            description.setLayoutX(e.getX() + description.getWidth()/2 + 10);
            description.setLayoutY(e.getY() - description.getHeight()/2 - 10);
        });
        pane.setOnMouseExited(e -> description.setVisible(false));
 */
    }

    public void updateSize(double width, double height) {
        pane.setPrefWidth(width - 80*depth -120);

        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.updateSize(width, height);
        }
    }
}
