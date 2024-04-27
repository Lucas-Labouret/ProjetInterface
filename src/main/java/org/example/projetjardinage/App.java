package org.example.projetjardinage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.model.Species;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        primaryStage.setOnHidden(e -> renameDirectories());
    }

    private void renameDirectories(){
        for (int i = 0; i < GlobalData.plantes.size(); i++) {
            for (int j = 0; j < GlobalData.plantes.get(i).getSpecimens().size(); j++) {
                Path absolutePath = FileSystems.getDefault().getPath(
                        "src/main/resources/galerie/"+GlobalData.plantes.get(i).getOldName()+
                                "/"+GlobalData.plantes.get(i).getSpecimens().get(j).getOldName()
                ).toAbsolutePath();

                char [] a = absolutePath.toString().toCharArray();

                String t = "";

                for(char c :a ){
                    t += c ;
                    if(i == '\\'){
                        t += c ;
                    }
                }
                File ancienDossier = new File(t);
                System.out.println(ancienDossier + "");

                File nouveauDossier = new File(
                        ancienDossier.getParentFile(), GlobalData.plantes.get(i).getSpecimens().get(j).getName()
                );

                System.out.println(nouveauDossier + "");

                if (ancienDossier.renameTo(nouveauDossier)) {
                    System.out.println("Le dossier a été renommé avec succès !");
                } else {
                    System.out.println("Le renommage du dossier a échoué.");
                }
            }

            Path absolutePath = FileSystems.getDefault().getPath(
                    "src/main/resources/galerie/"+GlobalData.plantes.get(i).getOldName()
            ).toAbsolutePath();
            //Path absolutePath = FileSystems.getDefault().getPath("src/main/resources/galerie/Kougoro").toAbsolutePath();

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

            //ArrayList<Character> path =new ArrayList<Character>( absolutePath.toString().toCharArray());
            //File ancienDossier = new File("C:\\Users\\Fran\\IdeaProjects\\ProjetInterface\\src\\main\\resources\\galerie\\Kougoro");
            File ancienDossier = new File(t);
            System.out.println(ancienDossier + "");

            File nouveauDossier = new File(ancienDossier.getParentFile(), GlobalData.plantes.get(i).getName());

            System.out.println(nouveauDossier + "");

            if (ancienDossier.renameTo(nouveauDossier)) {
                System.out.println("Le dossier a été renommé avec succès !");
            } else {
                System.out.println("Le renommage du dossier a échoué.");
            }
        }
    }
}
