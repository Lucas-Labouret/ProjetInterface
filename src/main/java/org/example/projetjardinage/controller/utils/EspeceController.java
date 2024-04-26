package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.Species;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class EspeceController extends Observer {

    @FXML
    private AnchorPane espece;

    @FXML
    private ImageView image;

    @FXML
    private Label text;

    private Species species;

    public EspeceController(int i) {;
        this.species = GlobalData.plantes.get(i);
        this.subscribeTo(species);
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
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIES);
                MainWindow.getInstance().getSpeciesController().switchSpecies(species);
            }
        });
    }

    public void update() {
        text.setText(species.getName());
    }
}
