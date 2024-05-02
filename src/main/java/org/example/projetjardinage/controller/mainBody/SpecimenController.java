package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.MainWindow;
import org.example.projetjardinage.controller.utils.journal.JournalController;
import org.example.projetjardinage.controller.utils.journal.MesureHolderShower;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.lists.TodoList;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SpecimenController implements BodyController {
    @FXML private Button addMesureButton;
    @FXML private Button ajouterImage;
    @FXML private Label dateLastCoupe;
    @FXML private Label dateLastRecolte;
    @FXML private Label dateLastRempotage;
    @FXML private Button editName;
    @FXML private Button editNotes;
    @FXML private Button editNotesEntretien;
    @FXML private Button gallery;
    @FXML private HBox imageBox;
    @FXML private Button journalButton;
    @FXML private TextField lastEntry;
    @FXML private TextField mesureMoyenne;
    @FXML private TextField nom;
    @FXML private TextArea notesEntretien;
    @FXML private TextArea notesSpe;
    @FXML private Button speciesButton;
    @FXML private ImageView speciesImage;
    @FXML private Pane taskPane;

    private Specimen specimen;

    private TodoListController todoListController;

    public void switchSpecimen(Specimen s){
        specimen = s;

        nom.setText(s.getName());
        notesSpe.setText(s.getNoteSpecimen());
        notesEntretien.setText(s.getNoteEntretien());

        TodoList todoList = new TodoList();
        todoList.addFilter(t -> t.getLinkedSpecimens().contains(this.specimen));
        fillTodoList(todoList);
        todoListController = new TodoListController(todoList, specimen, false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainBody/TodoListBody.fxml"));
        loader.setController(todoListController);
        taskPane.getChildren().clear();
        try {
            taskPane.getChildren().add(loader.load());
            todoListController.updateSize(300, 200);
        }
        catch (Exception e) { e.printStackTrace(); }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (LocalDate entry: specimen.getJournal().getSortedDates()){
            if (specimen.getJournal().get(entry).getCoup()){
                dateLastCoupe.setText(entry.format(formatter));
                break;
            }
            dateLastCoupe.setText("N/A");
        }
        for (LocalDate entry: specimen.getJournal().getSortedDates()){
            if (specimen.getJournal().get(entry).getRempot()){
                dateLastRecolte.setText(entry.format(formatter));
                break;
            }
            dateLastRecolte.setText(specimen.getMiseEnTerre().format(formatter));
        }
        for (LocalDate entry: specimen.getJournal().getSortedDates()){
            if (specimen.getJournal().get(entry).getRempot()){
                dateLastRempotage.setText(entry.format(formatter));
                break;
            }
            dateLastRempotage.setText("N/A");
        }

        MesureHolderShower mesureHolderShowerMoyenne = new MesureHolderShower(specimen.getMesuresMoyennes());
        mesureHolderShowerMoyenne.setOnHover(mesureMoyenne);

        MesureHolderShower mesureHolderShowerLastEntry = new MesureHolderShower(specimen.getJournal().getLastEntry().getMesures());
        mesureHolderShowerLastEntry.setOnHover(lastEntry);
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
        editName.setText("✎");
        editName.setOnAction(e -> {
            nom.setEditable(!nom.isEditable());
            editName.setText(nom.isEditable() ? "✔" : "✎");
        });
        nom.setOnKeyTyped(e -> {
            specimen.setName(nom.getText());
        });
        editNotes.setOnAction(e -> {
            notesSpe.setEditable(!notesSpe.isEditable());
            editNotes.setText(notesSpe.isEditable() ? "✔" : "✎");
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
    }

    public void updateSize(double width, double height) {}
}
