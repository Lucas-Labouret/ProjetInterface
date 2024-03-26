package org.example.projetjardinage.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.controller.mainBody.*;

public class MainWindow {
    public enum Controller {
        TODO_LIST,
        SPECIES_LIST,
        SPECIES,
        SPECIMEN,
        GALLERY
    }
    private Body currentView;
    private TodoListController todoListController;
    private SpeciesListController speciesListController;
    private SpeciesController speciesController;
    private SpecimenController specimenController;
    private GalleryController galleryController;

    @FXML
    private VBox fullWindow;
    @FXML
    HBox buttons;
    @FXML
    private Pane body;

    @FXML
    private Button toDoButton;
    @FXML
    private Button speciesButton;
    @FXML
    private Button galleryButton;

    public MainWindow(){}

    public void initialize() {
        todoListController = new TodoListController();
        speciesListController = new SpeciesListController();
        speciesController = new SpeciesController(new Species());
        specimenController = new SpecimenController(null);
        galleryController = new GalleryController();

        toDoButton.setText("To-Do List");
        speciesButton.setText("Espèces");
        galleryButton.setText("Galerie");

        toDoButton.setOnAction(e -> switchController(Controller.TODO_LIST));
        speciesButton.setOnAction(e -> switchController(Controller.SPECIES)); //TODO: change to SPECIES_LIST
        galleryButton.setOnAction(e -> switchController(Controller.GALLERY));

        switchController(Controller.SPECIES);
    }

    public void switchController(Controller controller){
        switch(controller){
            case TODO_LIST:
                currentView = todoListController;
                break;
            case SPECIES_LIST:
                currentView = speciesListController;
                break;
            case SPECIES:
                currentView = speciesController;
                break;
            case SPECIMEN:
                currentView = specimenController;
                break;
            case GALLERY:
                currentView = galleryController;
                break;
        }
        body.getChildren().clear();
        body.getChildren().add(currentView.getBody());
    }

    public void updateWindowSize(double width, double height) {
        fullWindow.resize(width, height);
        for(Node node : buttons.getChildren()){
            Button button = (Button) node;
            button.setPrefWidth(width / 3);
        }
        currentView.updateSize(width, height);
    }
}
