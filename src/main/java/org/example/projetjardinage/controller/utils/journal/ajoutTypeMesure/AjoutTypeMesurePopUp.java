package org.example.projetjardinage.controller.utils.journal.ajoutTypeMesure;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.projetjardinage.model.journal.mesures.TypeMesure;

public class AjoutTypeMesurePopUp extends AjoutTypeMesure {
    public static AjoutTypeMesurePopUp newAjoutTypeMesurePopUp(Stage stage){
        AjoutTypeMesurePopUp popUp = new AjoutTypeMesurePopUp(stage);

        FXMLLoader loader = new FXMLLoader(AjoutTypeMesurePopUp.class.getResource("/utils/journal/ajout/AjoutMesure.fxml"));
        loader.setController(popUp);

        Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        stage.setScene(scene);
        stage.show();

        return popUp;
    }

    private final Stage stage;

    @FXML private MenuButton typeMenu;
    @FXML private Pane mainPanel;
    @FXML private Button annuler;
    @FXML private Button valider;

    private AjoutTypeMesure current;

    private AjoutMesureList listController;
    private AjoutMesureScale scaleController;
    private AjoutMesureBoolText boolTextController;
    private AjoutMesureNum numController;

    private Parent listView;
    private Parent scaleView;
    private Parent boolTextView;
    private Parent numView;

    private TypeMesure type;
    private boolean validated = false;

    private AjoutTypeMesurePopUp(Stage stage){
        this.stage = stage;
        load();
    }

    private void load(){
        FXMLLoader loader;

        listController = new AjoutMesureList();
        loader = new FXMLLoader(getClass().getResource("/utils/journal/ajout/AjoutMesureList.fxml"));
        loader.setController(listController);
        try { listView = loader.load();}
        catch (Exception e) { e.printStackTrace(); }

        scaleController = new AjoutMesureScale();
        loader = new FXMLLoader(getClass().getResource("/utils/journal/ajout/AjoutMesureScale.fxml"));
        loader.setController(scaleController);
        try { scaleView = loader.load();}
        catch (Exception e) { e.printStackTrace(); }

        boolTextController = new AjoutMesureBoolText();
        loader = new FXMLLoader(getClass().getResource("/utils/journal/ajout/AjoutMesureBoolText.fxml"));
        loader.setController(boolTextController);
        try { boolTextView = loader.load();}
        catch (Exception e) { e.printStackTrace(); }

        numController = new AjoutMesureNum();
        loader = new FXMLLoader(getClass().getResource("/utils/journal/ajout/AjoutMesureNum.fxml"));
        loader.setController(numController);
        try { numView = loader.load();}
        catch (Exception e) { e.printStackTrace(); }
    }

    public void initialize(){
        MenuItem item;

        item = new MenuItem("Textuelle");
        item.setOnAction(e -> {
            type = TypeMesure.Text;
            typeMenu.setText("Textuelle");
            current = boolTextController;
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(boolTextView);
        });
        typeMenu.getItems().add(item);

        item = new MenuItem("Booléenne");
        item.setOnAction(e -> {
            type = TypeMesure.Bool;
            typeMenu.setText("Booléenne");
            current = boolTextController;
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(boolTextView);
        });
        typeMenu.getItems().add(item);

        item = new MenuItem("Numérique");
        item.setOnAction(e -> {
            type = TypeMesure.Numeric;
            typeMenu.setText("Numérique");
            current = numController;
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(numView);
        });
        typeMenu.getItems().add(item);

        item = new MenuItem("Liste");
        item.setOnAction(e -> {
            type = TypeMesure.List;
            typeMenu.setText("Liste");
            current = listController;
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(listView);
        });
        typeMenu.getItems().add(item);

        item = new MenuItem("Echelle");
        item.setOnAction(e -> {
            type = TypeMesure.Scale;
            typeMenu.setText("Echelle");
            current = scaleController;
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(scaleView);
        });
        typeMenu.getItems().add(item);

        annuler.setOnAction(e -> {
            validated = false;
            stage.close();
        });
        valider.setOnAction(e -> {
            validated = true;
            stage.close();
        });
    }

    public String getName() { return current.getName(); }
    public TypeMesure getType() { return type; }
    public String getUnit() { return current.getUnit(); }

    public boolean wasValidated() { return validated; }
}
