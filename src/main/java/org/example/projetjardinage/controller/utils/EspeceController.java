package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.model.Species;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class EspeceController {

    @FXML
    private AnchorPane espece;

    @FXML
    private ImageView image;

    @FXML
    private Label text;

    private Species species;

    private Stage etage;

    private int id;



    public EspeceController(Stage stage , int i) {
        int id = i;
        this.etage = stage;
        this.species = GlobalData.getPlantes().get(id);
    }

    public void initialize(){
        System.out.println("hola");
        text.setText(species.getName());
        try {
            Paths.get(species.getProfilePicURL());
            image.setImage(new Image(getClass().getResourceAsStream(species.getProfilePicURL())));
        } catch (InvalidPathException | NullPointerException ex) {
            image.setImage(new Image(getClass().getResourceAsStream("/icons/267203.png")));
        }



           espece.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                System.out.println("lol");
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainBody/SpeciesBody.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    etage.setScene(new Scene(root1));
                    etage.show();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
}
