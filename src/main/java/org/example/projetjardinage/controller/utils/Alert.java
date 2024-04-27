package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;

import java.io.IOException;

public class Alert {
    public static Alert newAlert(String msg) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(Alert.class.getResource("/utils/Alert.fxml"));
        Alert alert = new Alert(stage, msg);
        loader.setController(alert);
        try { stage.setScene(new Scene(loader.load())); }
        catch (IOException e) { e.printStackTrace(); }
        stage.show();
        return alert;
    }

    @FXML private Label message;
    @FXML private Button ok;

    private final Stage stage;
    private final String msg;

    public Alert(Stage stage, String msg) {
        this.stage = stage;
        this.msg = msg;
    }

    public void initialize() {
        message.setText(msg);
        message.setPrefWidth(msg.length() * 7 + 20);
        stage.setWidth(msg.length() * 7 + 40);
        ok.setOnAction(e -> stage.close());
    }
}
