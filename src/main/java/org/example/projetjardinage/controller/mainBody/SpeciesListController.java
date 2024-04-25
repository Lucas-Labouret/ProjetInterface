package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.utils.EspeceController;
import org.example.projetjardinage.controller.utils.RecursiveTask;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpeciesListController implements BodyController{
    @FXML private TextField RechercheEspece;
    @FXML private HBox HBox;
    @FXML private VBox VBox1;
    @FXML private VBox VBox2;
    @FXML private VBox VBox3;

    private List<Species> plantes ;

    private List<EspeceController> controleurs;

    public SpeciesListController(){
        plantes = GlobalData.plantes;
        controleurs = new ArrayList<>();
    }

    public void initialize()  {
        for( int i = 0; i < plantes.size(); i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/Espece.fxml"));
            EspeceController especeControler = new EspeceController(i);
            controleurs.add(especeControler);
            loader.setController(especeControler);
            try{
            switch (i%3){
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
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    public void update() {initialize();}
    public void updateSize(double width, double height) {}
}

