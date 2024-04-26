package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;

public class SpecimenController implements BodyController {


    @FXML
    private TextField nom;

    @FXML
    private Button editName;

    @FXML
    private Button addMesureButton;

    @FXML
    private Label ageLabel;

    @FXML
    private Label arrosageLabel;

    @FXML
    private Label dateLastCoupe;

    @FXML
    private Label dateLastEntry;

    @FXML
    private Label dateLastPlantation;

    @FXML
    private Button editButton;

    @FXML
    private Label enTerreLabel;

    @FXML
    private Label espaceAuSolLabel;

    @FXML
    private Label expositionLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Button journalButton;

    @FXML
    private Label notesEntretien;

    @FXML
    private Label notesSpecimen;

    @FXML
    private Label phLabel;

    @FXML
    private Button speciesButton;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label widthLabel;


    private Specimen specimen;

    public void switchSpecimen(Specimen s){
        specimen = s;

        nom.setText(s.getName());
    }

    public SpecimenController(){}
    public void initialize(){
        editName.setOnAction(e -> {
            nom.setEditable(!nom.isEditable());
            });
            nom.setOnKeyTyped(e -> {
                specimen.setName(nom.getText());
            });
        }

    public void update() {}
    public void updateSize(double width, double height) {}
}
