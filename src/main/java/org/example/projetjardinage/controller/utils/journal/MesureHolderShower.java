package org.example.projetjardinage.controller.utils.journal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

import java.util.List;

public class MesureHolderShower {
    private final VBox mesureBox = new VBox();

    public MesureHolderShower(List<MesureHolder> mesureHolders) {
        mesureBox.setStyle("-fx-background-color: #e0e0e0;");
        for (MesureHolder mesure : mesureHolders) {
            switch (mesure.getType()){
                case Numeric -> {
                    FXMLLoader numTextLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureNumText.fxml"));
                    MesureNumController controller = new MesureNumController(mesure, null);
                    numTextLoader.setController(controller);
                    try { mesureBox.getChildren().add(numTextLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Bool -> {
                    FXMLLoader boolLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureBool.fxml"));
                    MesureBoolController controller = new MesureBoolController(mesure, null);
                    boolLoader.setController(controller);
                    try { mesureBox.getChildren().add(boolLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Text -> {
                    FXMLLoader textLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureText.fxml"));
                    MesureTextController controller = new MesureTextController(mesure, null);
                    textLoader.setController(controller);
                    try { mesureBox.getChildren().add(textLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case List -> {
                    FXMLLoader listLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureList.fxml"));
                    MesureListController controller = new MesureListController(mesure, null);
                    listLoader.setController(controller);
                    try { mesureBox.getChildren().add(listLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
                case Scale -> {
                    FXMLLoader scaleLoader = new FXMLLoader(getClass().getResource("/utils/journal/MesureScale.fxml"));
                    MesureScaleController controller = new MesureScaleController(mesure, null);
                    scaleLoader.setController(controller);
                    try { mesureBox.getChildren().add(scaleLoader.load()); }
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        }
    }

    public void setOnHover(Parent element){
        Scene scene = new Scene(mesureBox);
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        element.setOnMouseEntered(e -> {
            stage.setX(e.getScreenX() + 10);
            stage.setY(e.getScreenY() + 10);
            stage.show();
        });
        element.setOnMouseExited(e -> {
            stage.hide();
        });
    }
}
