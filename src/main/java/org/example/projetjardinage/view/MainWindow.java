package org.example.projetjardinage.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.view.mainBody.*;

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
    private final HBox buttons;
    private final VBox fullWindow;
    private final Pane body;

    private MainWindow(double width, double height){
        buttons = new HBox();
        body = new Pane();
        fullWindow = new VBox();
        scene = new Scene(fullWindow);

        Button toDoButton = new Button("To-do List");
        Button speciesButton = new Button("Espèces");
        Button galleryButton = new Button("Galerie");
        buttons.getChildren().addAll(toDoButton, speciesButton, galleryButton);

        toDoButton.setOnAction(e -> switchView(View.TODO_LIST));
        speciesButton.setOnAction(e -> switchView(View.SPECIES)); //TODO: change to SPECIES_LIST
        galleryButton.setOnAction(e -> switchView(View.GALLERY));

        currentView = SpeciesView.getInstance(null);
        body.getChildren().add(currentView.getBody());
        fullWindow.getChildren().addAll(buttons, body);

        updateWindowSize(width, height);
    }

    public static MainWindow getInstance(double width, double height){
        if(instance == null){ instance = new MainWindow(width, height); }
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
                currentView = SpeciesView.getInstance(null);
                break;
            case SPECIMEN:
                currentView = SpecimenView.getInstance(null);
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
