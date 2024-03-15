package org.example.projetjardinage.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainWindow {
    private static MainWindow instance;

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

        fullWindow.getChildren().addAll(buttons, body);
    }

    public static MainWindow getInstance(){
        if(instance == null){ instance = new MainWindow(); }
        return instance;
    }

    public Scene getMainWindow(){
        Label label = new Label("Hello World!");
        body.getChildren().add(label);
        return scene;
    }

    public void updateWindowSize(double width, double height) {
        fullWindow.resize(width, height);
        for(Node node : buttons.getChildren()){
            Button button = (Button) node;
            button.setPrefWidth(width / 3);
        }
    }
}
