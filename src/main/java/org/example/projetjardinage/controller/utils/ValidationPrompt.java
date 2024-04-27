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

public class ValidationPrompt {
    public static ValidationPrompt newValidationPrompt(String msg) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(ValidationPrompt.class.getResource("/utils/ValidationPrompt.fxml"));
        ValidationPrompt validationPrompt = new ValidationPrompt(stage, msg);
        loader.setController(validationPrompt);
        try { stage.setScene(new Scene(loader.load())); }
        catch (IOException e) { e.printStackTrace(); }
        stage.show();
        return validationPrompt;
    }

    @FXML private Label message;
    @FXML private Button confirm;
    @FXML private Button cancel;

    private final Stage stage;
    private final String msg;
    private boolean result;

    public ValidationPrompt(Stage stage, String msg) {
        this.stage = stage;
        this.msg = msg;
        this.result = false;
    }

    public void initialize() {
        message.setText(msg);
        message.setPrefWidth(msg.length() * 7 + 20);
        stage.setWidth(msg.length() * 7 + 40);
        confirm.setOnAction(e -> {
            result = true;
            stage.close();
        });
        cancel.setOnAction(e -> stage.close());
    }

    public boolean getResult() {
        return result;
    }

    public Stage getStage() {
        return stage;
    }
}
