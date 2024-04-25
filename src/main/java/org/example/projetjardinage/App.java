package org.example.projetjardinage;

import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.enregistreur.Lecteur;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.projetjardinage.controller.MainWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
    private final int minWidth = 600;
    private final int minHeight = 450;

    private static List<Species> plantes;
    private static List<Task> taches;
    public static void main(String[] args) {

        String test = "test.txt";
        recuperrageDesDonnees(test);

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
                //"/MesuresPopUp.fxml"
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


        Image image = new Image(getClass().getResourceAsStream("/icons/267203.png"));
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.show();**/
    }

    private static void recuperrageDesDonnees(String path){
        plantes = new ArrayList<>();
        taches = new ArrayList<>();
        Lecteur lec = new Lecteur(path);
        List<List<String>> donnees;

        donnees = lec.getEsp();
        Map<String, Integer> indexPlantes = new HashMap<>();

        for (List<String> esp : donnees) {
            if(esp.size()!=9){
                System.out.println("Probleme taille des especes lecture.");
            }

            Species test = new Species(esp);
            plantes.add(test);
            indexPlantes.put(test.getName(),plantes.size()-1);

        }

        donnees = lec.getSpe();
        for (List<String> spe : donnees) {
            Species esp = plantes.get(indexPlantes.get(spe.get(4)));
            Specimen test = new Specimen(spe, esp);
            esp.addSpecimen(test);
        }

    }
}
