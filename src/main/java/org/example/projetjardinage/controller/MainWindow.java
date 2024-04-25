package org.example.projetjardinage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.controller.mainBody.*;
import org.example.projetjardinage.model.Task;

import java.util.List;

public class MainWindow {
    private static MainWindow instance;
    private MainWindow() {}
    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    public enum Display {
        TODO_LIST,
        SPECIES_LIST,
        SPECIES,
        SPECIMEN,
        GALLERY
    }

    private BodyController currentController;
    private TodoListController todoListController;
    private SpeciesListController speciesListController;
    private SpeciesController speciesController;
    private SpecimenController specimenController;
    private GalleryController galleryController;

    private Parent currentView;
    private Parent toDoListView;
    private Parent speciesListView;
    private Parent speciesView;
    private Parent specimenView;
    private Parent galleryView;

    double lastWidth;
    double lastHeight;

    @FXML private VBox fullWindow;
    @FXML HBox buttons;
    @FXML private Pane body;

    @FXML private Button toDoButton;
    @FXML private Button speciesButton;
    @FXML private Button galleryButton;

    public void initialize() {
        loadViewsControllers();

        toDoButton.setText("To-Do List");
        speciesButton.setText("Espèces");
        galleryButton.setText("Galerie");

        toDoButton.setOnAction(e -> switchController(Display.TODO_LIST));
        speciesButton.setOnAction(e -> switchController(Display.SPECIES)); //TODO: change to SPECIES_LIST
        galleryButton.setOnAction(e -> switchController(Display.GALLERY));

        switchController(Display.SPECIES);
    }

    private void loadViewsControllers(){
        FXMLLoader todoListLoader    = new FXMLLoader(getClass().getResource("/mainBody/TodoListBody.fxml"));
        FXMLLoader speciesListLoader = new FXMLLoader(getClass().getResource("/mainBody/SpeciesListBody.fxml"));
        FXMLLoader speciesLoader     = new FXMLLoader(getClass().getResource("/mainBody/SpeciesBody.fxml"));
        FXMLLoader specimenLoader    = new FXMLLoader(getClass().getResource("/mainBody/SpecimenBody.fxml"));
        FXMLLoader galleryLoader     = new FXMLLoader(getClass().getResource("/mainBody/GalleryBody.fxml"));

        try {
            toDoListView = todoListLoader.load();
            speciesListView = speciesListLoader.load();
            speciesView = speciesLoader.load();
            specimenView = specimenLoader.load();
            galleryView = galleryLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        todoListController = todoListLoader.getController();
        speciesListController = speciesListLoader.getController();
        speciesController = speciesLoader.getController();
        specimenController = specimenLoader.getController();
        galleryController = galleryLoader.getController();

        System.out.println(todoListController);
    }

    public void switchController(Display display){
        switch(display){
            case TODO_LIST:
                currentView = toDoListView;
                currentController = todoListController;
                break;
            case SPECIES_LIST:
                currentView = speciesListView;
                currentController = speciesListController;
                break;
            case SPECIES:
                currentView = speciesView;
                currentController = speciesController;
                break;
            case SPECIMEN:
                currentView = specimenView;
                currentController = specimenController;
                break;
            case GALLERY:
                currentView = galleryView;
                currentController = galleryController;
                break;
        }
        body.getChildren().clear();
        body.getChildren().add(currentView);
        currentController.updateSize(lastWidth, lastHeight);
    }

    public void update(){
        currentController.update();
    }

    public void updateWindowSize(double width, double height) {
        lastWidth = width;
        lastHeight = height;
        fullWindow.resize(width, height);
        for(Node node : buttons.getChildren()){
            Button button = (Button) node;
            button.setPrefWidth(width / 3);
        }
        currentController.updateSize(width, height);
    }
}
