package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.model.Species;

import java.io.IOException;

public class AddSpeciesPopUp {
    public static AddSpeciesPopUp newAddSpeciesPopUp() {
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(GlobalData.primaryStage);

        FXMLLoader loader = new FXMLLoader(TaskPopUp.class.getResource("/utils/AddSpeciesPopUp.fxml"));
        AddSpeciesPopUp addSpeciesPopUp = new AddSpeciesPopUp(stage);
        loader.setController(addSpeciesPopUp);

        Parent addSpeciesPopUpView = null;
        try { addSpeciesPopUpView = loader.load(); }
        catch (IOException ex) { ex.printStackTrace(); }
        if (addSpeciesPopUpView == null) throw new AssertionError();

        Scene scene = new Scene(addSpeciesPopUpView);
        stage.setScene(scene);
        stage.show();

        return addSpeciesPopUp;
    }

    @FXML private TextField nameField;
    @FXML private Button annuler;
    @FXML private Button valider;

    private final Stage stage;

    public AddSpeciesPopUp(Stage stage){
        this.stage = stage;
    }

    public void initialize(){
        annuler.setOnAction(e -> stage.close());

        valider.setOnAction(e -> {
            Species species = new Species(nameField.getText());
            GlobalData.species.add(species);
            MainWindow.getInstance().getSpeciesController().switchSpecies(species);
            MainWindow.getInstance().switchController(MainWindow.Display.SPECIES);
            stage.close();
        });
    }
}
