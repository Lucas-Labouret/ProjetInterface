package org.example.projetjardinage.controller.utils.journal.ajoutTypeMesure;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AjoutMesureList extends AjoutTypeMesure {
    @FXML private TextField nameField;
    @FXML private VBox mainPanel;

    private ArrayList<AjoutMesureListLine> lines = new ArrayList<>();
    private ArrayList<Parent> lineViews = new ArrayList<>();

    public void initialize(){
        showLines();
    }

    private void addLine(){
        AjoutMesureListLine line = new AjoutMesureListLine(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/journal/ajout/AjoutMesureListLine.fxml"));
        loader.setController(line);
        try {
            lineViews.add(loader.load());
            lines.add(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLines(){
        AjoutMesureListLine lastVisible = null;
        mainPanel.getChildren().clear();
        for (int i = 0; i < lines.size(); i++){
            if (lines.get(i).isVisible()){
                mainPanel.getChildren().add(lineViews.get(i));
                lastVisible = lines.get(i);
            }
        }
        if (lastVisible == null || lastVisible.isVisible()){
            addLine();
            mainPanel.getChildren().add(lineViews.get(lines.size() - 1));
        }
    }

    public void update(){
        showLines();
    }

    public String getName(){ return nameField.getText(); }
    public String getUnit(){
        String unit = "";
        for (AjoutMesureListLine line : lines){
            if (line.isVisible()){
                unit += line.getType() + "<SEP>";
            }
        }
        return unit.substring(0, unit.length() - 5);
    }
}
