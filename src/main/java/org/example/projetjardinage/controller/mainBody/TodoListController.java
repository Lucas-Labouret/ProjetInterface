package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
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

    private TodoList todoList;
    private ArrayList<Task> tasks;

    public TodoListController() {
        tasks = new ArrayList<>(List.of(
                new Task("Task 1", "Description 1", LocalDate.of(2024, 1, 1)),
                new Task("Task 2", "Description 2", LocalDate.of(2024, 1, 1)),
                new Task("Task 3", "Description 3", LocalDate.of(2024, 1, 1)),
                new Task("Task 4", "Description 4", LocalDate.of(2024, 1, 2)),
                new Task("Task 5", "Description 5", LocalDate.of(2024, 1, 3)),
                new Task("Task 6", "Description 6", LocalDate.of(2024, 2, 4)),
                new Task("Task 7", "Description 7", LocalDate.of(2025, 2, 5)),
                new Task("Task 8", "Description 8", LocalDate.of(2025, 2, 6))
        ));
        tasks.getFirst().addSubTasks(
                new Task("SubTask 1", "SubDescription 1", LocalDate.of(2024, 1, 1)),
                new Task("SubTask 2", "SubDescription 2", LocalDate.of(2024, 1, 1)),
                new Task("SubTask 3", "SubDescription 3", LocalDate.of(2024, 1, 1))
        );
        tasks.getFirst().getSubTasks().getLast().addSubTasks(
                new Task("SubSubTask 1", "SubSubDescription très très très très très très très très très très très très très très très très très très longue", LocalDate.of(2024, 1, 1)),
                new Task("SubSubTask 2", "SubSubDescription 2", LocalDate.of(2024, 1, 1)),
                new Task("SubSubTask 3", "SubSubDescription 3", LocalDate.of(2024, 1, 1))
        );
        this.tasks.sort(Comparator.comparing(Task::getDueDate));
        todoList = new TodoList(tasks);
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
