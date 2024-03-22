package org.example.projetjardinage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.projetjardinage.view.MainWindow;

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
        MainWindow mainWindow = MainWindow.getInstance(primaryStage.getWidth(), primaryStage.getHeight());
        Scene scene = mainWindow.getMainWindow();

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
