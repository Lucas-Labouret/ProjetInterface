package org.example.projetjardinage;

import org.example.projetjardinage.model.enregistreur.Lecteur;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projetjardinage.controller.MainWindow;

import java.io.IOException;

public class App extends Application {
    private final int minWidth = 600;
    private final int minHeight = 450;
    public static void main(String[] args) {

        String test = "test.txt";
        Lecteur AH = new Lecteur(test);
        System.out.println("Finito.");
        //Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /**primaryStage.setMinWidth(minWidth);
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
        primaryStage.setScene(scene);
        primaryStage.show();**/
    }
}
