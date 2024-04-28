package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.controller.utils.EspeceController;
import org.example.projetjardinage.model.Lists.ObservableList;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.Lists.TodoList;

import java.util.ArrayList;
import java.util.List;

public class SpeciesController extends Observer implements BodyController {
    private class ViewControllerPair {
        private final EspeceController controller;
        private final Parent view;

        public ViewControllerPair(EspeceController controller, Parent view) {
            this.controller = controller;
            this.view = view;
        }

        public EspeceController getController() { return controller; }
        public Parent getLoader() { return view; }
    }

    @FXML private VBox vBoxGallery;

    @FXML private TextField name;
    @FXML private Button editName;

    @FXML private Button heart;

    @FXML private TextField nbSpecimen;
    @FXML private Button addSpecimen;

    @FXML private Label moyenneLabel;
    @FXML private GridPane moyennePanel;
    @FXML private Label moyenne1;
    @FXML private Label moyenne2;
    @FXML private Label moyenne3;

    @FXML private Label notesLabel;
    @FXML private Button editNotes;
    @FXML private TextArea notes;

    @FXML private Label galleryLabel;
    @FXML private Button tous;
    @FXML private Button vivants;
    @FXML private Button morts;
    @FXML private ScrollPane galleryScrollPane;
    @FXML private TextField recherche;

    @FXML private Line sepLine;

    @FXML private ImageView speciesImage;

    @FXML private GridPane mesuresPanel;
    @FXML private Label mesure1;
    @FXML private Label mesure2;
    @FXML private Label mesure3;
    @FXML private Label mesure4;
    @FXML private Button mesuresEdit;

    @FXML private Label todoListLabel;
    @FXML private Pane taskPane;

    private Species species;
    private ObservableList<Specimen> plantes ;
    private ArrayList<ViewControllerPair> viewControllerPairs = new ArrayList<>();
    private TodoListController todoListController;

    public SpeciesController(){}

    public void initialize(){
        editName.setOnAction(e -> {
            name.setEditable(!name.isEditable());
        });
        name.textProperty().addListener(e -> {
            species.setName(name.getText());
        });

        editNotes.setOnAction(e -> {
            notes.setEditable(!notes.isEditable());

        });

        notes.setOnKeyTyped(e -> {
            species.setNotes(notes.getText());
        });

        heart.setOnAction(e -> {
            species.setFavorite(!species.getFavorite());
            if(species.getFavorite()){
                heart.setText("♥");
            }
            if(!species.getFavorite()){
                heart.setText("♡");
            }
        });

        tous.setOnAction(e ->{
            vBoxGallery.getChildren().clear();
            for (int i = 0; i < plantes.size(); i++) {
                showGalleryImage(i);
            }
        });
        vivants.setOnAction(e ->{
            vBoxGallery.getChildren().clear();
            int x = 0;
            for (int i = 0; i < plantes.size(); i++) {
                if (plantes.get(i).isAlive()) {
                    showGalleryImage(i);
                    x++;
                }
            }
        });

        morts.setOnAction(e ->{
            vBoxGallery.getChildren().clear();
            int x = 0;
            for (int i = 0; i < plantes.size(); i++) {
                if (!plantes.get(i).isAlive()) {
                    showGalleryImage(i);
                    x++;
                }
            }
        });
        recherche.textProperty().addListener(e -> {
            update();
        });

        reposition(0);
    }

    public void switchSpecies(Species s) {
        vBoxGallery.getChildren().clear();

        species = s;

        name.setText(s.getName());
        nbSpecimen.setText(String.valueOf(species.getSpecimens().size()));
        notes.setText(species.getNotes());
        if(species.getFavorite()){
            heart.setText("♥");
        }
        if(!species.getFavorite()){
            heart.setText("♡");
        }
        this.plantes = new ObservableList<>(species.getSpecimens());
        this.subscribeTo(plantes);
        for (int i = 0; i < plantes.size(); i++) showGalleryImage(i);

        TodoList todoList = new TodoList();
        todoList.addFilter(t -> t.getLinkedSpecies().contains(this.species));
        fillTodoList(todoList);

        todoListController = new TodoListController(todoList, species, false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainBody/TodoListBody.fxml"));
        loader.setController(todoListController);
        taskPane.getChildren().clear();
        try { taskPane.getChildren().add(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void fillTodoList(TodoList todoList) {
        ArrayList<Task> unexplored = new ArrayList<>();
        for (Task task: GlobalData.tasks.getElements()){
            if (task.getLinkedSpecies().contains(this.species))
                todoList.add(task);
            else unexplored.add(task);
        }
        _fillTodoList(todoList, unexplored);
    }
    private void _fillTodoList(TodoList todoList, List<Task> unexplored) {
        ArrayList<Task> u = new ArrayList<>();
        for (Task task: unexplored) for (Task subtask: task.getSubTasks()) {
            if (subtask.getLinkedSpecies().contains(this.species))
                todoList.add(subtask);
            else u.add(subtask);
        }
        if (!u.isEmpty()) _fillTodoList(todoList, u);
    }

    private void showGalleryImage(int i){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/Espece.fxml"));
        EspeceController especeControler = new EspeceController(species, i);
        loader.setController(especeControler);
        try {
            viewControllerPairs.add(new ViewControllerPair(especeControler, loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(){
        vBoxGallery.getChildren().clear();
        //TODO: SpeciesController.update()
    }

    private void reposition(double offset){
        name.setLayoutX(offset + 20);
        name.setLayoutY(10);
        name.setPrefHeight(30);

        editName.setLayoutY(12);
        editName.setPrefWidth(30);
        editName.setPrefHeight(30);

        heart.setPrefWidth(30);
        heart.setPrefHeight(30);
        heart.setLayoutY(10);

        nbSpecimen.setLayoutX(offset + 20);
        nbSpecimen.setLayoutY(50);
        nbSpecimen.setPrefHeight(20);

        addSpecimen.setLayoutY(50);
        addSpecimen.setPrefHeight(25);
        addSpecimen.setPrefWidth(25);

        moyenneLabel.setLayoutX(offset + 20);
        moyenneLabel.setLayoutY(80);
        moyenneLabel.setPrefHeight(20);

        moyennePanel.setLayoutX(offset + 160);
        moyennePanel.setLayoutY(75);

        notesLabel.setLayoutX(offset + 20);
        notesLabel.setLayoutY(180);
        notesLabel.setPrefHeight(20);
        notesLabel.setPrefWidth(60);

        editNotes.setLayoutX(offset + 80);
        editNotes.setLayoutY(180);
        editNotes.setPrefHeight(20);
        editNotes.setPrefWidth(20);

        notes.setLayoutX(offset + 20);
        notes.setLayoutY(210);
        notes.setPrefHeight(80);

        todoListLabel.setPrefHeight(20);
        todoListLabel.setLayoutX(offset + 20);
        todoListLabel.setPrefWidth(60);

        taskPane.setLayoutX(offset + 20);

        sepLine.setLayoutY(0);
        sepLine.setStartX(0);
        sepLine.setEndX(0);
        sepLine.setStartY(0);

        speciesImage.setLayoutY(20);

        mesuresPanel.setPrefHeight(80);

        mesuresEdit.setPrefWidth(20);
        mesuresEdit.setPrefHeight(20);

        galleryLabel.setPrefHeight(20);

        tous.setPrefHeight(20);

        vivants.setPrefHeight(20);

        morts.setPrefHeight(20);

        recherche.setPrefHeight(20);
    }

    public void updateSize(double width, double height) {
        double offset;
        if (width>height){
            offset = (width-height)/2;
            width = height;
        }
        else offset = 0;
        reposition(offset);

        name.setPrefWidth(2*width/5);

        editName.setLayoutX(offset + 30+2*width/5);

        heart.setLayoutX(offset + 2*width/3 - 40);

        nbSpecimen.setPrefWidth(width/3);

        addSpecimen.setLayoutX(offset + width/3 + 30);

        notes.setPrefWidth(2*width/3 - 40);
        notes.setPrefHeight(height/4);

        todoListLabel.setLayoutY(230 + height/4);

        taskPane.setLayoutY(260 + height/4);
        taskPane.setPrefWidth(2*width/3 - 40);
        taskPane.setPrefHeight(3*height/4 - 400);

        sepLine.setLayoutX(offset + 2*width/3);
        sepLine.setEndY(height);

        speciesImage.setLayoutX(offset + 2*width/3 + 20);
        speciesImage.setFitWidth(width/3 - 40);

        mesuresPanel.setLayoutX(offset + 2*width/3 + 20);
        mesuresPanel.setLayoutY(40 + speciesImage.getFitHeight());
        mesuresPanel.setPrefWidth(width/3 - 40);

        mesuresEdit.setLayoutX(offset + width - 40);
        mesuresEdit.setLayoutY(40 + speciesImage.getFitHeight());

        galleryLabel.setLayoutX(offset + 2*width/3 + 20);
        galleryLabel.setLayoutY(140 + speciesImage.getFitHeight());
        galleryLabel.setPrefWidth(width/3 - 40);

        tous.setLayoutY(160 + speciesImage.getFitHeight());
        tous.setLayoutX(offset + 2*width/3 + 20);
        tous.setPrefWidth(width/3 - 40);

        vivants.setLayoutY(190 + speciesImage.getFitHeight());
        vivants.setLayoutX(offset + 2*width/3 + 20);
        vivants.setPrefWidth((width/3 - 50)/2);

        morts.setLayoutY(190 + speciesImage.getFitHeight());
        morts.setLayoutX(offset + 2*width/3 + 20 + (width/3 - 50)/2 + 10);
        morts.setPrefWidth((width/3 - 50)/2);

        recherche.setLayoutY(220 + speciesImage.getFitHeight());
        recherche.setPrefWidth(width/3 - 40);
        recherche.setLayoutX(offset + 2*width/3 + 20);

        galleryScrollPane.setLayoutY(250 + speciesImage.getFitHeight());
        galleryScrollPane.setPrefWidth(width/3 - 40);
        galleryScrollPane.setPrefHeight(height - speciesImage.getFitHeight() - 250);
        galleryScrollPane.setLayoutX(offset + 2*width/3 + 20);

        todoListController.updateSize(2*width/3 - 40, 3*height/4 - 280);
    }
}