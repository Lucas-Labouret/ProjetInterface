package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.utils.EspeceController;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;

import java.util.ArrayList;
import java.util.List;

public class SpeciesController implements BodyController {


    @FXML
    private VBox VBox1;

    @FXML
    private VBox VBox2;

    @FXML
    private VBox VBox3;

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


    private List<Specimen> plantes ;

    private List<EspeceController> controleurs = new ArrayList<EspeceController>();

    /**public SpeciesController(int i){
        species = GlobalData.plantes.get(i);
        plantes = species.getSpecimens();
        System.out.println("--------------------------------------");
        controleurs = new ArrayList<>();
    }**/
    public SpeciesController(){
        //plantes = species.getSpecimens();
        //controleurs = new ArrayList<>();
    }

    public void initialize(){
        editName.setOnAction(e -> {
            name.setEditable(!name.isEditable());

        });
        name.setOnKeyTyped(e -> {
            species.setName(name.getText());
        });

        editNotes.setOnAction(e -> {
            notes.setEditable(!notes.isEditable());

        });

        notes.setOnKeyTyped(e -> {
            species.setNotes(notes.getText());
        });

        heart.setOnAction(e -> {
            species.setFavorite(!species.getFavorite());
            if(species.getFavorite()){
                heart.setText("♥");
            }
            if(!species.getFavorite()){
                heart.setText("♡");
            }
        });

        tous.setOnAction(e ->{
            VBox1.getChildren().clear();
            VBox2.getChildren().clear();
            VBox3.getChildren().clear();
            for (int i = 0; i < plantes.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/Espece.fxml"));
                EspeceController especeControler = new EspeceController(species, i);
                controleurs.add(especeControler);
                loader.setController(especeControler);
                try {
                    switch (i % 3) {
                        case 0:
                            VBox1.getChildren().add(loader.load());
                            break;
                        case 1:
                            VBox2.getChildren().add(loader.load());
                            break;
                        case 2:
                            VBox3.getChildren().add(loader.load());
                            break;


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        vivants.setOnAction(e ->{
            VBox1.getChildren().clear();
            VBox2.getChildren().clear();
            VBox3.getChildren().clear();
            int x = 0;
            for (int i = 0; i < plantes.size(); i++) {
                if (plantes.get(i).isAlive()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/Espece.fxml"));
                    EspeceController especeControler = new EspeceController(species, i);
                    controleurs.add(especeControler);
                    loader.setController(especeControler);
                    try {
                        switch (x % 3) {
                            case 0:
                                VBox1.getChildren().add(loader.load());
                                break;
                            case 1:
                                VBox2.getChildren().add(loader.load());
                                break;
                            case 2:
                                VBox3.getChildren().add(loader.load());
                                break;


                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    x++;
                }
            }
        });

        morts.setOnAction(e ->{
            VBox1.getChildren().clear();
            VBox2.getChildren().clear();
            VBox3.getChildren().clear();
            int x = 0;
            for (int i = 0; i < plantes.size(); i++) {
                if (!plantes.get(i).isAlive()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/Espece.fxml"));
                    EspeceController especeControler = new EspeceController(species, i);
                    controleurs.add(especeControler);
                    loader.setController(especeControler);
                    try {
                        switch (x % 3) {
                            case 0:
                                VBox1.getChildren().add(loader.load());
                                break;
                            case 1:
                                VBox2.getChildren().add(loader.load());
                                break;
                            case 2:
                                VBox3.getChildren().add(loader.load());
                                break;


                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    x++;
                }
            }
        });
        reposition(0);


    }

    public void switchSpecies(Species s) {
        VBox1.getChildren().clear();
        VBox2.getChildren().clear();
        VBox3.getChildren().clear();

        species = s;
        name.setText(s.getName());
        nbSpecimen.setText(String.valueOf(species.getSpecimens().size()));
        notes.setText(species.getNotes());
        if(species.getFavorite()){
            heart.setText("♥");
        }
        if(!species.getFavorite()){
            heart.setText("♡");
        }
        this.plantes = species.getSpecimens();
        for (int i = 0; i < plantes.size(); i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/Espece.fxml"));
            EspeceController especeControler = new EspeceController(species, i);
            controleurs.add(especeControler);
            loader.setController(especeControler);
            try {
                switch (i % 3) {
                    case 0:
                        VBox1.getChildren().add(loader.load());
                        break;
                    case 1:
                        VBox2.getChildren().add(loader.load());
                        break;
                    case 2:
                        VBox3.getChildren().add(loader.load());
                        break;


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    /**public void switchSpecies(int i){
        species = GlobalData.plantes.get(i);

        name.setText(species.getName());

        notes.setText(species.getNotes());




    }**/

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