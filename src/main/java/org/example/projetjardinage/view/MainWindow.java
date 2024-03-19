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

    private final Scene scene;
    private final HBox buttons;
    private final VBox fullWindow;
    private final Pane body;

    private MainWindow(){
        buttons = new HBox();
        body = new Pane();
        fullWindow = new VBox();
        scene = new Scene(fullWindow);

        Button toDoButton = new Button("To-do List");
        Button speciesButton = new Button("Espèces");
        Button galleryButton = new Button("Galerie");
        buttons.getChildren().addAll(toDoButton, speciesButton, galleryButton);
        for(Button button : new Button[]{toDoButton, speciesButton, galleryButton}){
            button.setPrefWidth(fullWindow.getWidth() / 3);
        }

        body.getChildren().add(SpeciesView.getInstance(null).getBody());
        fullWindow.getChildren().addAll(buttons, body);
    }

    public static MainWindow getInstance(){
        if(instance == null){ instance = new MainWindow(); }
        return instance;
    }

    public Scene getMainWindow(){
        return scene;
    }

    public void switchView(View view){
        switch(view){
            case TODO_LIST:
                body.getChildren().clear();
                body.getChildren().add(TodoListView.getInstance().getBody());
                break;
            case SPECIES_LIST:
                body.getChildren().clear();
                body.getChildren().add(SpeciesListView.getInstance().getBody());
                break;
            case SPECIES:
                body.getChildren().clear();
                body.getChildren().add(SpeciesView.getInstance(null).getBody());
                break;
            case SPECIMEN:
                body.getChildren().clear();
                body.getChildren().add(SpecimenView.getInstance(null).getBody());
                break;
            case GALLERY:
                body.getChildren().clear();
                body.getChildren().add(GalleryView.getInstance().getBody());
                break;
        }
    }

    public void updateWindowSize(double width, double height) {
        fullWindow.resize(width, height);
        for(Node node : buttons.getChildren()){
            Button button = (Button) node;
            button.setPrefWidth(width / 3);
        }
    }
}
