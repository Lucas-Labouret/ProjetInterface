package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import org.example.projetjardinage.model.Species;

import static java.lang.Math.max;

public class SpeciesController implements BodyController {
    Species species;

    @FXML private TextField name;
    @FXML private Button editName;

    @FXML private Button heart;

    @FXML private TextField nbSpecimen;
    @FXML private Button addSpecimen;

    @FXML private Label moyenneLabel;
    @FXML private GridPane moyennePanel;
    @FXML private Label moyenne1;
    @FXML private Label moyenne2;
    @FXML private Label moyenne3;

    @FXML private Label notesLabel;
    @FXML private Button editNotes;
    @FXML private TextArea notes;

    @FXML private Label galleryLabel;
    @FXML private Button tous;
    @FXML private Button vivants;
    @FXML private Button morts;
    @FXML private ScrollPane galleryScrollPane;
    @FXML private TextField recherche;

    @FXML private Line sepLine;

    @FXML private ImageView speciesImage;

    @FXML private GridPane mesuresPanel;
    @FXML private Label mesure1;
    @FXML private Label mesure2;
    @FXML private Label mesure3;
    @FXML private Label mesure4;
    @FXML private Button mesuresEdit;

    @FXML private Button addTask;
    @FXML private ScrollPane taskScrollPane;

    public SpeciesController(){}

    public void initialize(){
        reposition(0);
    }

    public void switchSpecies(Species s){
        species = s;
    }

    public void update(){}

    private void reposition(double offset){
        name.setLayoutX(offset + 20);
        name.setLayoutY(10);
        name.setPrefHeight(30);

        editName.setLayoutY(12);
        editName.setPrefWidth(30);
        editName.setPrefHeight(30);

        heart.setPrefWidth(30);
        heart.setPrefHeight(30);

        heart.setLayoutY(10);

        nbSpecimen.setLayoutX(offset + 20);
        nbSpecimen.setLayoutY(50);
        nbSpecimen.setPrefHeight(20);
        nbSpecimen.setText("nbSpecimen");

        addSpecimen.setLayoutY(50);
        addSpecimen.setPrefHeight(25);
        addSpecimen.setPrefWidth(25);

        moyenneLabel.setLayoutX(offset + 20);
        moyenneLabel.setLayoutY(80);
        moyenneLabel.setPrefHeight(20);

        moyennePanel.setLayoutX(offset + 160);
        moyennePanel.setLayoutY(75);

        notesLabel.setLayoutX(offset + 20);
        notesLabel.setLayoutY(180);
        notesLabel.setPrefHeight(20);
        notesLabel.setPrefWidth(60);

        editNotes.setLayoutX(offset + 80);
        editNotes.setLayoutY(180);
        editNotes.setPrefHeight(20);
        editNotes.setPrefWidth(20);

        notes.setLayoutX(offset + 20);
        notes.setLayoutY(210);
        notes.setPrefHeight(80);

        galleryLabel.setLayoutX(offset + 20);
        galleryLabel.setPrefWidth(60);

        tous.setLayoutX(offset + 90);
        tous.setPrefWidth(70);
        tous.setPrefHeight(20);

        vivants.setLayoutX(offset + 170);
        vivants.setPrefWidth(70);
        vivants.setPrefHeight(20);

        morts.setLayoutX(offset + 250);
        morts.setPrefWidth(70);
        morts.setPrefHeight(20);

        recherche.setLayoutX(offset + 330);
        recherche.setPrefHeight(20);

        galleryScrollPane.setLayoutX(offset + 20);

        sepLine.setLayoutY(0);
        sepLine.setStartX(0);
        sepLine.setEndX(0);
        sepLine.setStartY(0);

        speciesImage.setLayoutY(20);

        mesuresPanel.setPrefHeight(80);

        mesuresEdit.setPrefWidth(20);
        mesuresEdit.setPrefHeight(20);

        addTask.setPrefHeight(30);
    }

    public void updateSize(double width, double height) {
        double offset;
        if (width>height){
            offset = (width-height)/2;
            width = height;
        }
        else offset = 0;
        reposition(offset);

        name.setPrefWidth(2*width/5);

        editName.setLayoutX(offset + 30+2*width/5);

        heart.setLayoutX(offset + 2*width/3 - 40);

        nbSpecimen.setPrefWidth(width/3);

        addSpecimen.setLayoutX(offset + width/3 + 30);

        notes.setPrefWidth(2*width/3 - 40);
        notes.setPrefHeight(height/4);

        galleryLabel.setLayoutY(230 + height/4);
        tous.setLayoutY(230 + height/4);
        vivants.setLayoutY(230 + height/4);
        morts.setLayoutY(230 + height/4);

        recherche.setLayoutY(230 + height/4);
        recherche.setPrefWidth(2*width/3 - 350);

        galleryScrollPane.setLayoutY(260 + height/4);
        galleryScrollPane.setPrefWidth(2*width/3 - 40);
        galleryScrollPane.setPrefHeight(3*height/4 - 330);

        sepLine.setLayoutX(offset + 2*width/3);
        sepLine.setEndY(height);

        speciesImage.setLayoutX(offset + 2*width/3 + 20);
        speciesImage.setFitWidth(width/3 - 40);

        mesuresPanel.setLayoutX(offset + 2*width/3 + 20);
        mesuresPanel.setLayoutY(40 + speciesImage.getFitHeight());
        mesuresPanel.setPrefWidth(width/3 - 40);

        mesuresEdit.setLayoutX(offset + width - 40);
        mesuresEdit.setLayoutY(40 + speciesImage.getFitHeight());

        addTask.setLayoutX(offset + 2*width/3 + 20);
        addTask.setLayoutY(140 + speciesImage.getFitHeight());
        addTask.setPrefWidth(width/3 - 40);

        taskScrollPane.setLayoutX(offset + 2*width/3 +20);
        taskScrollPane.setLayoutY(180 + speciesImage.getFitHeight());
        taskScrollPane.setPrefWidth(width/3 - 40);
        taskScrollPane.setPrefHeight(height - speciesImage.getFitHeight() - 250);
    }
}