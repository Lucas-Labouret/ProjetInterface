package org.example.projetjardinage.view;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.model.Species;

public class SpeciesView {
    private static SpeciesView instance;

    private final HBox fullView;

    private final VBox leftSide;
    private final HBox nameAndFavorite;
    private final HBox nbSpecimens;
    private final HBox mesuresMoyenne;
    private final VBox mesures;
    private final VBox notes;
    private final HBox notesModif;
    private final VBox gallery;
    private final HBox galleryModif;
    private final HBox gallerySelect;
    private final GridPane galleryPhotos;

    private final VBox rightSide;
    private final HBox mesuresRecommendation;
    private final VBox mesuresAffichage;
    private final TasksViewer relatedTasks;

    private SpeciesView(Species s){
        fullView = new HBox();

        leftSide = new VBox();
        nameAndFavorite = new HBox();
        nbSpecimens = new HBox();
        mesuresMoyenne = new HBox();
        mesures = new VBox();
        notes = new VBox();
        notesModif = new HBox();
        gallery = new VBox();
        galleryModif = new HBox();
        gallerySelect = new HBox();
        galleryPhotos = new GridPane();

        rightSide = new VBox();
        mesuresRecommendation = new HBox();
        mesuresAffichage = new VBox();
        relatedTasks = new TasksViewer();

        fullView.getChildren().addAll(leftSide, rightSide);

        leftSide.getChildren().addAll(nameAndFavorite, nbSpecimens, mesuresMoyenne, notes, gallery);
        mesuresMoyenne.getChildren().addAll(new Label("Mesures moyennes : "), mesures);
        notes.getChildren().addAll(notesModif, new Label("voici des notes"));
        notesModif.getChildren().addAll(new Label("Notes : "), new Label("voici un bouton modifier"));
        gallery.getChildren().addAll(galleryModif, gallerySelect, galleryPhotos);
        galleryModif.getChildren().addAll(new Label("Galerie : "), new Label("voici un bouton modifier"));
        gallerySelect.getChildren().addAll(new Label("bouton 1"), new Label("bouton 2"), new Label("bouton 3"));
        galleryPhotos.add(new Label("photo 1"), 0, 0);
        galleryPhotos.add(new Label("photo 2"), 0, 2);
        galleryPhotos.add(new Label("photo 3"), 0, 3);
        galleryPhotos.add(new Label("photo 4"), 0, 4);
        galleryPhotos.add(new Label("photo 5"), 1, 0);

        rightSide.getChildren().addAll(new Label("photo"), mesuresRecommendation, mesuresAffichage, new Label("bouton ajout tache") ,relatedTasks);
    }

    public static SpeciesView getInstance(Species s) {
        if (instance == null) instance = new SpeciesView(s);
        return instance;
    }

    public Pane getSpeciesView() {
        return fullView;
    }
}
