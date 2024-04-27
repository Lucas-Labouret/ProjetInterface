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
        String test = "test.txt";
        GlobalData.recuperrageDesDonnees(test);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);

        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/MainWindow.fxml"
        ));
        fxmlLoader.setControllerFactory(c -> MainWindow.getInstance());
        try {root = fxmlLoader.load();}
        catch (IOException e) {throw new RuntimeException(e);}

        Scene scene = new Scene(root);

        MainWindow mainWindow = fxmlLoader.getController();
        mainWindow.updateWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        ChangeListener<Number> stageSizeListener =
                (observable, oldValue, newValue) ->
                mainWindow.updateWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);

        primaryStage.setTitle("Le projet UwU");

        Image image = new Image(getClass().getResourceAsStream("/icons/267203.png"));
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.show();

        GlobalData.primaryStage = primaryStage;
    }
}
