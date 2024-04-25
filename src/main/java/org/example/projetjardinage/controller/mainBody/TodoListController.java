package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.utils.RecursiveTask;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.TodoList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TodoListController implements BodyController {
    @FXML private ScrollPane scroll;
    @FXML private VBox mainBox;

    private List<Task> tasks;

    public TodoListController() {
        tasks = GlobalData.taches;
    }

    ArrayList<RecursiveTask> recursiveTasks;

    public void initialize() {
        recursiveTasks = new ArrayList<>();

        int day = -1;
        int month = -1;
        int year = -1;
        VBox dayBox = null;
        TitledPane monthPane = null;
        VBox monthBox = null;
        for (Task task : tasks) {
            //if (task.getDueDate().isBefore(LocalDate.now())) continue;

            if (task.getDueDate().getYear() != year || task.getDueDate().getMonthValue() != month) {
                day = -1;

                year = task.getDueDate().getYear();
                month = task.getDueDate().getMonthValue();

                monthBox = new VBox();
                monthPane = new TitledPane(task.getDueDate().getMonth().toString() + " " + year, monthBox);
                monthPane.setExpanded(false);
                mainBox.getChildren().add(monthPane);
            }

            if (task.getDueDate().getDayOfMonth() != day) {
                day = task.getDueDate().getDayOfMonth();
                dayBox = new VBox();
                TitledPane dayPane = new TitledPane(String.valueOf(day), dayBox);
                monthBox.getChildren().add(dayPane);
                day = task.getDueDate().getDayOfMonth();
            }

            FXMLLoader recursiveTaskLoader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
            RecursiveTask recursiveTask = new RecursiveTask(task, 0);
            recursiveTasks.add(recursiveTask);
            recursiveTaskLoader.setController(recursiveTask);
            try { dayBox.getChildren().add(recursiveTaskLoader.load()); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    public void update() {
        initialize();
    }

    public void updateSize(double width, double height) {
        scroll.setPrefSize(width, height);
        mainBox.setPrefWidth(width);

        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.updateSize(width, height);
        }
    }
}
