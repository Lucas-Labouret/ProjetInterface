package org.example.projetjardinage.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.view.mainBody.*;

import java.io.IOException;

public class MainWindow {
    private static MainWindow instance;

    public enum View {
        TODO_LIST,
        SPECIES_LIST,
        SPECIES,
        SPECIMEN,
        GALLERY
    }
    private Body currentView;

    private final Scene scene;

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

    private MainWindow(double width, double height){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try { fxmlLoader.load(); }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        scene = new Scene(fullWindow);

        toDoButton.setText("To-Do List");
        speciesButton.setText("Espèces");
        galleryButton.setText("Galerie");

        toDoButton.setOnAction(e -> switchView(View.TODO_LIST));
        speciesButton.setOnAction(e -> switchView(View.SPECIES)); //TODO: change to SPECIES_LIST
        galleryButton.setOnAction(e -> switchView(View.GALLERY));

        currentView = SpeciesView.getInstance(new Species());
        body.getChildren().add(currentView.getBody());
    }

    public static MainWindow getInstance(double width, double height){
        if(instance == null){ instance = new MainWindow(width, height); }
        instance.updateWindowSize(width, height);
        return instance;
    }

    public static MainWindow getInstance(){
        if (instance == null)
            throw new ExceptionInInitializerError("Must call getInstance(double, double) at least once before getInstance()");
        return instance;
    }

    public Scene getMainWindow(){
        return scene;
    }

    public void switchView(View view){
        switch(view){
            case TODO_LIST:
                currentView = TodoListView.getInstance();
                break;
            case SPECIES_LIST:
                currentView = SpeciesListView.getInstance();
                break;
            case SPECIES:
                currentView = SpeciesView.getInstance();
                break;
            case SPECIMEN:
                currentView = SpecimenView.getInstance();
                break;
            case GALLERY:
                currentView = GalleryView.getInstance();
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
