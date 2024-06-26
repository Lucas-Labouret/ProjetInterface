package org.example.projetjardinage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.projetjardinage.controller.MainWindow;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public class App extends Application {
    private final int minWidth = 600;
    private final int minHeight = 600;

    public static void main(String[] args) {
        String test = "Données.txt";
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
        primaryStage.setOnHidden(e -> {
            renameDirectories();
            for (int i = 0; i < GlobalData.species.size(); i++) {
                for (int j = 0; j < GlobalData.species.get(i).getSpecimens().size(); j++) {
                    GlobalData.species.get(i).getSpecimens().get(j).resetProfilePic();
                }
                GlobalData.species.get(i).resetProfilePic();
            }
            GlobalData.enregistre("Données.txt");
            Platform.exit();
        });
    }

    private void renameDirectories(){
        for (int i = 0; i < GlobalData.species.size(); i++) {
            for (int j = 0; j < GlobalData.species.get(i).getSpecimens().size(); j++) {
                Path absolutePath = FileSystems.getDefault().getPath(
                        "src/main/resources/galerie/"+GlobalData.species.get(i).getOldName()+
                                "/"+GlobalData.species.get(i).getSpecimens().get(j).getOldName()
                ).toAbsolutePath();

                char [] a = absolutePath.toString().toCharArray();

                String t = "";

                for(char c :a ){
                    t += c ;
                }
                File ancienDossier = new File(t);

                File nouveauDossier = new File(
                        ancienDossier.getParentFile(), GlobalData.species.get(i).getSpecimens().get(j).getName()
                );


                if (ancienDossier.renameTo(nouveauDossier)) {
                    //System.out.println("Le dossier a été renommé avec succès !");
                } else {
                    //System.out.println("Le renommage du dossier a échoué.");
                }
            }

            Path absolutePath = FileSystems.getDefault().getPath(
                    "src/main/resources/galerie/"+GlobalData.species.get(i).getOldName()
            ).toAbsolutePath();

            char [] a = absolutePath.toString().toCharArray();

            String t = "";

            ArrayList<Character> path =new ArrayList<Character>();
            for(char c :a ){
                path.add(c);
                t += c ;
                if(i == '\\'){
                    path.add(c);
                    t += c ;
                }
            }
            File ancienDossier = new File(t);

            File nouveauDossier = new File(ancienDossier.getParentFile(), GlobalData.species.get(i).getName());


            if (ancienDossier.renameTo(nouveauDossier)) {
                //System.out.println("Le dossier a été renommé avec succès !");
            } else {
                //System.out.println("Le renommage du dossier a échoué.");
            }
        }
    }
}
