package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.controller.utils.journal.JournalController;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.lists.TodoList;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpecimenController implements BodyController {


    @FXML private TextField nom;
    @FXML private Button editName;
    @FXML private Button editNotesSpecimen;


    @FXML private Button editNotesEntretien;

    @FXML private Pane taskPane;

    @FXML private Button ajouterImage;

    @FXML private Button addEntretienButton;

    @FXML private Button addMesureButton;
    @FXML private Label ageLabel;
    @FXML private Label arrosageLabel;
    @FXML private Label dateLastCoupe;
    @FXML private Label dateLastEntry;
    @FXML private Label dateLastPlantation;
    @FXML private Button editButton;
    @FXML private Label enTerreLabel;
    @FXML private Label espaceAuSolLabel;
    @FXML private Label expositionLabel;
    @FXML private Label heightLabel;
    @FXML private Button journalButton;
    @FXML private TextArea notesEntretien;
    @FXML private TextArea notesSpe;
    @FXML private Label phLabel;
    @FXML private Button speciesButton;
    @FXML private Label temperatureLabel;
    @FXML private Label widthLabel;
    @FXML private Label recolteLabel;
    @FXML private Button gallery;
    @FXML private ImageView speciesImage;



    private Specimen specimen;

    private TodoListController todoListController;
    public void switchSpecimen(Specimen s){
        specimen = s;

        nom.setText(s.getName());
        //heightLabel.setText(s.getHeight());
        //widthLabel.setText(s.getWidth());
        //ageLabel.setText(s.getAge());
        //dateLastPlantation.setText(s.getDateRempot().toString());
        //dateLastCoupe.setText(s.getDateCoupe().toString());
        //dateLastEntry.setText(s.getDerniereEntree().toString());
        //recolteLabel.setText(s.getDateRecolte().toString());
        notesSpe.setText(s.getNoteSpecimen());
        notesEntretien.setText(s.getNoteEntretien());
        speciesImage.setImage(new Image(getClass().getResourceAsStream(s.getProfilePic())));


        TodoList todoList = new TodoList();
        todoList.addFilter(t -> t.getLinkedSpecies().contains(this.specimen));
        fillTodoList(todoList);
        todoListController = new TodoListController(todoList, specimen, false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainBody/TodoListBody.fxml"));
        loader.setController(todoListController);
        taskPane.getChildren().clear();
        try { taskPane.getChildren().add(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }

    }

    private void fillTodoList(TodoList todoList) {
        ArrayList<Task> unexplored = new ArrayList<>();
        for (Task task: GlobalData.tasks.getElements()){
            if (task.getLinkedSpecies().contains(this.specimen))
                todoList.add(task);
            else unexplored.add(task);
        }
        _fillTodoList(todoList, unexplored);
    }
    private void _fillTodoList(TodoList todoList, List<Task> unexplored) {
        ArrayList<Task> u = new ArrayList<>();
        for (Task task: unexplored) for (Task subtask: task.getSubTasks()) {
            if (subtask.getLinkedSpecies().contains(this.specimen))
                todoList.add(subtask);
            else u.add(subtask);
        }
        if (!u.isEmpty()) _fillTodoList(todoList, u);
    }
    public SpecimenController(){}
    public void initialize(){
        editName.setOnAction(e -> {
            nom.setEditable(!nom.isEditable());
        });
        nom.setOnKeyTyped(e -> {
            specimen.setName(nom.getText());
        });
        editNotesSpecimen.setOnAction(e -> {
            notesSpe.setEditable(!notesSpe.isEditable());
            editNotesSpecimen.setText(notesSpe.isEditable() ? "✔" : "✎");
        });
        editNotesEntretien.setOnAction(e -> {
            notesEntretien.setEditable(!notesEntretien.isEditable());
            editNotesEntretien.setText(notesEntretien.isEditable() ? "✔" : "✎");
        });
        speciesButton.setOnAction(e -> {
            MainWindow.getInstance().getSpeciesController().switchSpecies(specimen.getSpecies());
            MainWindow.getInstance().switchController(MainWindow.Display.SPECIES);
        });

        journalButton.setOnAction(e -> {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/journal/Journal.fxml"));
            loader.setController(new JournalController(specimen.getJournal(), specimen.getName()));
            try {
                stage.setScene(new Scene(loader.load()));
                stage.show();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });
        gallery.setOnAction(e -> {
            MainWindow.getInstance().getGalleryController().switchSpecimen(specimen);
            MainWindow.getInstance().switchController(MainWindow.Display.GALLERY);
        });
        ajouterImage.setOnAction(e -> {
            FileChooser fC = new FileChooser();

            fC.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            List<File> f = fC.showOpenMultipleDialog(null);//stores files in f object list of type: File
            if (f != null){
                for (File file : f) {
                    //System.out.println(file.getAbsoluteFile());
                    /**BufferedImage img = null;
                    try {
                        img = ImageIO.read(file);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(img);
                    String uniqueID = UUID.randomUUID().toString();
                    Path newimg = FileSystems.getDefault().getPath(
                            "src/main/resources/galerie/"+specimen.getSpecies().getOldName()+"/"+specimen.getOldName()+"/"+uniqueID+".jpg");

                    System.out.println(newimg.toAbsolutePath());
                    try {
                        File fichier = new File(newimg.toAbsolutePath().toString());
                        fichier.createNewFile();
                        ImageIO.write(img, " jpg", fichier);
                    } catch (Exception ex){
                        ex.printStackTrace();
                        System.out.println("Error");
                    }
                }
            }**/
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(file);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    FileOutputStream ou = null;
                    String uniqueID = UUID.randomUUID().toString();
                    Path newimg = FileSystems.getDefault().getPath(
                            "src/main/resources/galerie/"+specimen.getSpecies().getOldName()+"/"+specimen.getOldName()+"/"+uniqueID+".jpg");
                    try {
                        ou = new FileOutputStream(newimg.toAbsolutePath().toString());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    BufferedInputStream bin = new BufferedInputStream(in);
                    BufferedOutputStream bou = new BufferedOutputStream(ou);
                    int b=0;
                    while(b!=-1){
                        try {
                            b=bin.read();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            bou.write(b);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    try {
                        bin.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        bou.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
                }
        });
    }

    public void updateSize(double width, double height) {}
}
