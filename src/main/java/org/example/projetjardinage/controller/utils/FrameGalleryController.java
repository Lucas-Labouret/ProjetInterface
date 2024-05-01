package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class FrameGalleryController extends Observer {

    @FXML
    private AnchorPane espece;

    @FXML
    private ImageView image;

    @FXML
    private Label text;

    private Species species;

    private int especeid;

    private int specimen = -1;

    String img ;

    public FrameGalleryController(int i) {
        this.species = GlobalData.species.get(i);
        this.especeid = i;
        this.subscribeTo(species);
    }


    public FrameGalleryController(Species species) {
        this.species = species;
        this.subscribeTo(species);
    }


    public FrameGalleryController(Species species, int j, String ig ) {;
        this.species = species;
        this.specimen = j;
        this.img = ig;
        //this.image = new ImageView();
        //.image.setImage(new Image(getClass().getResourceAsStream(ig)));
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
                image.setImage(new Image(getClass().getResourceAsStream(img)));
            } catch (InvalidPathException | NullPointerException ex) {
                image.setImage(new Image(getClass().getResourceAsStream("/icons/267203.png")));
            }
        }
        espece.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1 && specimen == -1) {
                MainWindow.getInstance().getGalleryController().switchSpecies(species);
                MainWindow.getInstance().switchController(MainWindow.Display.GALLERY);
                espece.setStyle("-fx-background-color: #e0e0e0;");
            } else if (e.getClickCount() == 1 && e.getButton() == MouseButton.PRIMARY) {
                MainWindow.getInstance().getSpecimenController().switchSpecimen(species.getSpecimens().get(specimen));
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIMEN);
            }
            else if (e.getClickCount() == 1 && e.getButton() == MouseButton.SECONDARY) {
                MainWindow.getInstance().getSpeciesController().switchSpecies(species);
                MainWindow.getInstance().switchController(MainWindow.Display.SPECIES);
            }
        });
    }

    public Specimen getSpecimen() { return species.getSpecimens().get(specimen); }

    public void resetBackground(){
        espece.setStyle("-fx-background-color: #f0f0f0;");
    }
    public void update() {
        if ( specimen == -1 ) text.setText(species.getName());
        else text.setText(species.getSpecimens().get(specimen).getName());

    }
}
