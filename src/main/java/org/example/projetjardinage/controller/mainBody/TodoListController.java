package org.example.projetjardinage.controller.mainBody;

import javafx.beans.value.ChangeListener;
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

public class TodoListController extends Observer implements BodyController {
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

        Integer day = null;
        Integer month = null;
        Integer year = null;
        for (Task task : tasks.getTasks()) {
            if (year == null || task.getDueDate().getYear() != year || task.getDueDate().getMonthValue() != month) {
                day = -1;

                year = task.getDueDate().getYear();
                month = task.getDueDate().getMonthValue();

                monthOpen.put(new MonthKey(year, month), false);
            }
            if (task.getDueDate().getDayOfMonth() != day) {
                day = task.getDueDate().getDayOfMonth();

                dayOpen.put(new DayKey(year, month, day), false);
            }

            FXMLLoader recursiveTaskLoader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
            RecursiveTask recursiveTask = new RecursiveTask(task, 0);
            recursiveTasks.add(recursiveTask);
            dayTasks.putIfAbsent(new DayKey(year, month, day), new ArrayList<>());
            dayTasks.get(new DayKey(year, month, day)).add(recursiveTask);
        }

        update();
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
        for (DayKey date: dates) {

            ArrayList<RecursiveTask> toRemove = new ArrayList<>();
            for (RecursiveTask recursiveTask : dayTasks.get(date)) {
                if (!tasks.getTasks().contains(recursiveTask.getTask())) {
                    toRemove.add(recursiveTask);
                }
            }
            for (RecursiveTask recursiveTask : toRemove) {
                dayTasks.get(date).remove(recursiveTask);
            }

            if (dayTasks.get(date).isEmpty()) {
                continue;
            }

            if (year == null || date.year != year || date.month != month) {
                year = date.year;
                month = date.month;

                monthBox = new VBox();
                monthPane = new TitledPane(date.month + " " + date.year, monthBox);
                monthPane.setExpanded(monthOpen.get(new MonthKey(year, month)));
                mainBox.getChildren().add(monthPane);

                int tmpYear = year;
                int tmpMonth = month;
                ChangeListener<Boolean> openedListener =
                        (observable, oldValue, newValue) -> monthPaneClicked(tmpYear, tmpMonth);
                monthPane.expandedProperty().addListener(openedListener);
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
                ChangeListener<Boolean> openedListener =
                        (observable, oldValue, newValue) -> dayPaneClicked(tmpYear, tmpMonth, tmpDay);
                dayPane.expandedProperty().addListener(openedListener);
            }

            for (RecursiveTask recursiveTask : dayTasks.get(date)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/RecursiveTask.fxml"));
                loader.setController(recursiveTask);
                try {dayBox.getChildren().add(loader.load()); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public void updateSize(double width, double height) {
        scroll.setPrefSize(width, height - 40);
        mainBox.setPrefWidth(width);

        for (RecursiveTask recursiveTask : recursiveTasks) {
            recursiveTask.updateSize(width, height);
        }
    }
}
