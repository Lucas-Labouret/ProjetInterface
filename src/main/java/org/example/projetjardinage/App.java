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
        Map<String, Specimen> indexSpecimen = new HashMap<>();

        for (List<String> spe : donnees) {
            Species esp = plantes.get(indexPlantes.get(spe.get(4)));
            Specimen test = new Specimen(spe, esp);
            esp.addSpecimen(test);
            indexSpecimen.put(test.getName(),test);
        }

        donnees = lec.getTasks();
        Map<String, Task> indexTask = new HashMap<>();

        for(List<String> tas : donnees) {
            List<Species> esp = new ArrayList<>();
            List<Specimen> spe = new ArrayList<>();
            //ajout des especes  6
            int nbEsp = Integer.parseInt(tas.get(6));

            for (int i = 0; i <nbEsp; i++){
                    Species current = plantes.get(indexPlantes.get(tas.get(7+i)));
                    esp.add(current);
            }

            int nbSpe = Integer.parseInt(tas.get(7+nbEsp));

            for(int i = 0; i<nbSpe; i++){
                Specimen current = indexSpecimen.get(tas.get(7+nbEsp+i));
                spe.add(current);
            }
            //ajout des specimens  7

            Task task = new Task(tas, esp, spe);
            taches.add(task);
            indexTask.put(task.getName(),task);
            for(Species espece : esp){
                espece.addTask(task);
            }
            for(Specimen specimen : spe){
                specimen.addTask(task);
            }

            //sous-tache
            if( !( Objects.equals(tas.get(4), "<N>") )) {
                Task surTache = indexTask.get(tas.get(4));
                surTache.addSubTask(task);
            }
        }

    }
}
