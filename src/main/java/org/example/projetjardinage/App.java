package org.example.projetjardinage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projetjardinage.view.MainWindow;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = MainWindow.getInstance();
        Scene scene = mainWindow.getMainWindow();

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            mainWindow.updateWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        };
        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);

        primaryStage.setTitle("Le projet UwU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
