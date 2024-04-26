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
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.Species;

import java.io.IOException;
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

    private int especeid;

    private int specimen = -1;

    public EspeceController(int i) {;
        this.species = GlobalData.plantes.get(i);
        this.especeid = i;
        this.subscribeTo(species);
    }

    public EspeceController(Species species, int j ) {;
        this.species = species;
        this.specimen = j;
        this.subscribeTo(species);
    }

    public void initialize(){
        if(specimen==-1) {
            text.setText(species.getName());
            try {
                Paths.get(species.getProfilePicURL());
                image.setImage(new Image(getClass().getResourceAsStream(species.getProfilePicURL())));
            } catch (InvalidPathException | NullPointerException ex) {
                image.setImage(new Image(getClass().getResourceAsStream("/icons/267203.png")));
            }
        }else {
            text.setText(species.getSpecimens().get(specimen).getName());
            try {
                Paths.get(species.getProfilePicURL());
                image.setImage(new Image(getClass().getResourceAsStream(species.getSpecimens().get(specimen).getProfilePic())));
            } catch (InvalidPathException | NullPointerException ex) {
                image.setImage(new Image(getClass().getResourceAsStream("/icons/267203.png")));
            }
        }
           espece.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1 && specimen == -1) {
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIES);
                MainWindow.getInstance().getSpeciesController().switchSpecies(species);


            } else if (e.getClickCount() == 1) {
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIMEN);
                System.out.println(specimen);
                MainWindow.getInstance().getSpecimenController().switchSpecimen(species.getSpecimens().get(specimen));
                System.out.println(specimen);
            }
           });
    }

    public void update() {
        text.setText(species.getName());
    }
}
