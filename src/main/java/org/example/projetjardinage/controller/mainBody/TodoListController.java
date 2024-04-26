package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.controller.utils.RecursiveTask;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.TodoList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodoListController implements Observer, BodyController {
    static final class MonthKey implements Comparable<MonthKey> {
        public int year, month;
        public MonthKey(int year, int month){
            this.year = year;
            this.month = month;
        }

        public int hashCode(){
            return (year << 10 ^ month);
        }

        public boolean equals(Object o){
            if(!(o instanceof MonthKey k))
                return false;

            return (k.year == year && k.month == month);
        }

        @Override
        public int compareTo(MonthKey k) {
            if(year != k.year) return year - k.year;
            return month - k.month;
        }
    }
    private static final class DayKey implements Comparable<DayKey> {
        public int year, month, day;
        public DayKey(int year , int month, int day){
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int hashCode(){
            return (year << 10 ^ month << 5 ^ day);
        }

        public boolean equals(Object o){
            if(!(o instanceof DayKey k))
                return false;

            return (k.year == year && k.month == month && k.day == day);
        }

        @Override
        public int compareTo(DayKey o) {
            if(year != o.year) return year - o.year;
            if(month != o.month) return month - o.month;
            return day - o.day;
        }
    }

    @FXML private ScrollPane scroll;
    @FXML private VBox mainBox;

    private final TodoList tasks;

    HashMap<MonthKey, Boolean> monthOpen = new HashMap<>();
    HashMap<DayKey, Boolean> dayOpen = new HashMap<>();

    HashMap<DayKey, ArrayList<RecursiveTask>> dayTasks = new HashMap<>();

    public TodoListController() {
        tasks = GlobalData.tasks;
        this.subscribeTo(tasks);
    }

    ArrayList<RecursiveTask> recursiveTasks;

    public void initialize() {
        recursiveTasks = new ArrayList<>();

        VBox dayBox = null;
        TitledPane monthPane = null;
        VBox monthBox = null;

        Integer day = null;
        Integer month = null;
        Integer year = null;
        for (Task task : tasks.getTasks()) {
            if (year == null || task.getDueDate().getYear() != year || task.getDueDate().getMonthValue() != month) {
                day = -1;

                year = task.getDueDate().getYear();
                month = task.getDueDate().getMonthValue();

                monthOpen.put(new MonthKey(year, month), false);

                monthBox = new VBox();
                monthPane = new TitledPane(task.getDueDate().getMonth().toString() + " " + year, monthBox);
                mainBox.getChildren().add(monthPane);

                int tmpYear = year;
                int tmpMonth = month;
                monthPane.setOnMouseClicked(e -> monthPaneClicked(tmpYear, tmpMonth));
            }
            if (task.getDueDate().getDayOfMonth() != day) {
                day = task.getDueDate().getDayOfMonth();

                dayOpen.put(new DayKey(year, month, day), false);

                dayBox = new VBox();
                TitledPane dayPane = new TitledPane(String.valueOf(day), dayBox);
                monthBox.getChildren().add(dayPane);
                day = task.getDueDate().getDayOfMonth();

                int tmpYear = year;
                int tmpMonth = month;
                int tmpDay = day;
                dayPane.setOnMouseClicked(e -> dayPaneClicked(tmpYear, tmpMonth, tmpDay));
            }

            FXMLLoader recursiveTaskLoader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
            RecursiveTask recursiveTask = new RecursiveTask(task, 0);
            recursiveTasks.add(recursiveTask);
            dayTasks.putIfAbsent(new DayKey(year, month, day), new ArrayList<>());
            dayTasks.get(new DayKey(year, month, day)).add(recursiveTask);
            recursiveTaskLoader.setController(recursiveTask);
            try { dayBox.getChildren().add(recursiveTaskLoader.load()); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void monthPaneClicked(int year, int month) {
        monthOpen.put(new MonthKey(year, month), !monthOpen.get(new MonthKey(year, month)));
    }
    private void dayPaneClicked(int year, int month, int day) {
        dayOpen.put(new DayKey(year, month, day), !dayOpen.get(new DayKey(year, month, day)));
    }

    public void update() {
        mainBox.getChildren().clear();

        ArrayList<DayKey> dates = new ArrayList<>(List.of(dayTasks.keySet().toArray(new DayKey[0])));
        dates.sort(DayKey::compareTo);

        VBox dayBox = null;
        TitledPane monthPane = null;
        VBox monthBox = null;

        Integer year = null;
        Integer month = null;
        Integer day = null;
        for (DayKey date: dayTasks.keySet().toArray(new DayKey[0])) {
            if (year == null || date.year != year || date.month != month) {
                year = date.year;
                month = date.month;

                monthBox = new VBox();
                monthPane = new TitledPane(date.month + " " + date.year, monthBox);
                monthPane.setExpanded(monthOpen.get(new MonthKey(year, month)));
                mainBox.getChildren().add(monthPane);

                int tmpYear = year;
                int tmpMonth = month;
                monthPane.lookup(".title").setOnMouseClicked(e -> monthPaneClicked(tmpYear, tmpMonth));
            }
            if (day == null || date.day != day) {
                day = date.day;

                dayBox = new VBox();
                TitledPane dayPane = new TitledPane(String.valueOf(day), dayBox);
                dayPane.setExpanded(dayOpen.get(new DayKey(year, month, day)));
                monthBox.getChildren().add(dayPane);

                int tmpYear = year;
                int tmpMonth = month;
                int tmpDay = day;
                dayPane.lookup(".title").setOnMouseClicked(e -> dayPaneClicked(tmpYear, tmpMonth, tmpDay));
            }

            for (RecursiveTask recursiveTask : dayTasks.get(date)) {
                FXMLLoader recursiveTaskLoader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
                recursiveTaskLoader.setController(recursiveTask);
                try { dayBox.getChildren().add(recursiveTaskLoader.load()); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public void updateSize(double width, double height) {
        scroll.setPrefSize(width, height);
        mainBox.setPrefWidth(width);

        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.updateSize(width, height);
        }
    }
}
