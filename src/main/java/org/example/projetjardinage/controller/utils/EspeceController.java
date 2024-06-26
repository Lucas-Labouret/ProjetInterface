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
import org.example.projetjardinage.model.Specimen;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class EspeceController extends Observer {
    @FXML private AnchorPane espece;
    @FXML private ImageView image;
    @FXML private Label text;

    private Species species;
    private int especeid;
    private int specimen = -1;

    public EspeceController(int i) {;
        this.species = GlobalData.species.get(i);
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
                MainWindow.getInstance().getSpeciesController().switchSpecies(species);
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIES);
            } else if (e.getClickCount() == 1) {
                MainWindow.getInstance().getSpecimenController().switchSpecimen(species.getSpecimens().get(specimen));
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIMEN);
            }
        });
    }

    public Specimen getSpecimen() { return species.getSpecimens().get(specimen); }

    public void update() {
        if ( specimen == -1 ) text.setText(species.getName());
        else text.setText(species.getSpecimens().get(specimen).getName());
    }
}
