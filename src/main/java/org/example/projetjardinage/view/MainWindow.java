package org.example.projetjardinage.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.model.Species;

public class MainWindow {
    private static MainWindow instance;

    public enum View {
        TODO_LIST,
        SPECIES,
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

        Button button1 = new Button("To-do List");
        Button button2 = new Button("Espèces");
        Button button3 = new Button("Galerie");
        buttons.getChildren().addAll(button1, button2, button3);
        for(Button button : new Button[]{button1, button2, button3}){
            button.setPrefWidth(fullWindow.getWidth() / 3);
        }

        body.getChildren().add(SpeciesView.getInstance(new Species()).getSpeciesView());
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
                body.getChildren().add(TodoListView.getInstance().getTodoListView());
                break;
            case SPECIES:
                body.getChildren().clear();
                body.getChildren().add(SpeciesView.getInstance(new Species()).getSpeciesView());
                break;
            case GALLERY:
                body.getChildren().clear();
                body.getChildren().add(GalleryView.getInstance().getGalleryView());
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
