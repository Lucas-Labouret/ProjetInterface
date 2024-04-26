package org.example.projetjardinage.controller.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Alert {
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
        message.setPrefWidth(msg.length() * 10 + 20);
        stage.setWidth(msg.length() * 10 + 40);
        ok.setOnAction(e -> stage.close());
    }
}
