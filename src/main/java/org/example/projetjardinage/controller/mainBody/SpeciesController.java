package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Species;

public class SpeciesController implements BodyController {
    Species species;

    @FXML
    private Button NameEdit;

    @FXML
    private Label name;

    @FXML
    private Button heart;

    @FXML
    private ImageView speciesimage;

    @FXML
    private HBox mesureshbox;

    @FXML
    private GridPane mesurespanel;

    @FXML
    private Label mesure1;

    @FXML
    private Label mesure2;

    @FXML
    private Label mesure3;

    @FXML
    private Label mesure4;

    @FXML
    private Button mesuresedit;

    @FXML
    private TextArea notes;

    @FXML
    private Button editNotes;

    @FXML
    private Button addSpecimen;

    @FXML
    private Button gallery;

    @FXML
    private HBox moyenneshbox;

    @FXML
    private GridPane moyennespanel;

    @FXML
    private Label moyenne1;

    @FXML
    private Label moyenne2;

    @FXML
    private Label moyenne3;

    @FXML
    private Button addTask;

    @FXML
    private ScrollPane galleryScrollPane;

    @FXML
    private AnchorPane galleryAnchorPane;

    @FXML
    private ImageView galleryImageView;

    @FXML
    private Button tous;

    @FXML
    private Button vivants;

    @FXML
    private Button morts;

    @FXML
    private Button recherche;
    public SpeciesController(){}
    private void initialize(){}

    public void switchSpecies(Species s){
        species = s;
    }

    @Override
    public void updateSize(double width, double height) {

    }

    /** public void updateSize(double width, double height) {
        fullWindow.resize(width, height);
        for(Node node : buttons.getChildren()){
            Button button = (Button) node;
            button.setPrefWidth(width / 3);
        }
        currentController.updateSize(width, height);
    }**/
}
