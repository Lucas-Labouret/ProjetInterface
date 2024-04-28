package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.controller.utils.AddSpeciesPopUp;
import org.example.projetjardinage.controller.utils.EspeceController;
import org.example.projetjardinage.model.Lists.ObservableList;
import org.example.projetjardinage.model.Species;

import java.util.ArrayList;
import java.util.List;

public class SpeciesListController extends Observer implements BodyController{
    private static class SpeciesViewPair {
        private final Species species;
        private final Parent view;

        public SpeciesViewPair(Species species, Parent view) {
            this.species = species;
            this.view = view;
        }

        public Species getSpecies() { return species; }
        public Parent getView() { return view; }
    }

    @FXML private Button addSpecies;
    @FXML private TextField rechercheEspece;
    @FXML private ToggleButton fav;

    @FXML private HBox mainPanel;
    ArrayList<VBox> vBoxes = new ArrayList<>();


    private final ObservableList<Species> plantes;
    private final List<SpeciesViewPair> controllerViewPairs = new ArrayList<>();

    private int nbColumns = 3;

    boolean favSelected = false;

    public SpeciesListController(){
        plantes = GlobalData.species;
        this.subscribeTo(plantes);
    }

    public void initialize()  {
        update();

        rechercheEspece.textProperty().addListener(e -> update());

        fav.setOnAction(e -> {
            favSelected = !favSelected;
            update();
        });

        addSpecies.setOnAction(e -> {
            AddSpeciesPopUp.newAddSpeciesPopUp();
        });
    }

    private void loadImage(int i, int x){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/Espece.fxml"));
        EspeceController especeControler = new EspeceController(i);
        loader.setController(especeControler);

        Parent especeView;
        try {
            especeView = loader.load();
            controllerViewPairs.add(new SpeciesViewPair(plantes.get(i), especeView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        //TODO: gérer la suppression des plantes
        mainPanel.getChildren().clear();
        vBoxes.clear();

        for (int i = 0; i < nbColumns; i++) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBoxes.add(vBox);
            mainPanel.getChildren().add(vBox);
        }

        ArrayList<Species> knownPlants = new ArrayList<>();
        for (SpeciesViewPair pair : controllerViewPairs) knownPlants.add(pair.getSpecies());
        for (int i = 0; i < plantes.size(); i++) {
            if (!knownPlants.contains(plantes.get(i))) {
                loadImage(i, 0);
            }
        }

        int x = 0;
        for(SpeciesViewPair pair : controllerViewPairs) {
            if (favSelected && !pair.getSpecies().getFavorite()) continue;
            if (!pair.getSpecies().getName().toLowerCase().contains(rechercheEspece.getText().toLowerCase())) continue;
            vBoxes.get(x % nbColumns).getChildren().add(pair.getView());
            x++;
        }
    }
    public void updateSize(double width, double height) {
        width -= 40;
        height -= 40;

        nbColumns = (int) (width / 150);
        update();

        addSpecies.setPrefWidth(width);
        rechercheEspece.setPrefWidth(width - 90);
        fav.setLayoutX(width - 50);

        mainPanel.setPrefSize(width, height - 130);
        for (VBox vBox : vBoxes) vBox.setPrefSize(width / nbColumns, height - 130);
    }
}

