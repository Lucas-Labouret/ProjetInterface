package org.example.projetjardinage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.projetjardinage.controller.MainWindow;

import java.io.IOException;

public class App extends Application {
    private final int minWidth = 600;
    private final int minHeight = 450;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);

        Parent root;
        System.out.println("here");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/MainWindow.fxml"
        ));
        try {root = fxmlLoader.load();}
        catch (IOException e) {throw new RuntimeException(e);}

        System.out.println("Root: " + root);
        Scene scene = new Scene(root);

        MainWindow mainWindow = fxmlLoader.getController();
        ChangeListener<Number> stageSizeListener =
                (observable, oldValue, newValue) ->
                mainWindow.updateWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);

        primaryStage.setTitle("Le projet UwU");

        Image image = new Image("C:\\Users\\Fran\\IdeaProjects\\ProjetInterface\\src\\main\\ressource\\icons\\267203.png");
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
