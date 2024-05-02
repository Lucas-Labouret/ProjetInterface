package org.example.projetjardinage.controller.utils.journal.ajoutTypeMesure;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AjoutMesureListLine {
    @FXML private TextField typeField;
    @FXML private Button validate;

    private final AjoutMesureList parent;

    private boolean validated = false;
    private boolean visible = true;

    public AjoutMesureListLine(AjoutMesureList parent){
        this.parent = parent;
    }

    public void initialize(){
        validate.setText("✓");
        validate.setOnAction(e -> {
            validated = false;
            validate.setText("✗");
            validate.setOnAction(e2 -> {
                visible = false;
                validated = false;
                parent.update();
            });
            parent.update();
        });
    }

    public boolean isValidated(){ return validated; }
    public boolean isVisible(){ return visible; }

    public String getType(){ return typeField.getText(); }
}
