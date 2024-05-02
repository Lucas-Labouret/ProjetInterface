package org.example.projetjardinage;

import javafx.stage.Stage;

import javafx.util.StringConverter;
import org.example.projetjardinage.model.lists.ObservableList;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.lists.TodoList;
import org.example.projetjardinage.model.enregistreur.Ecrivain;
import org.example.projetjardinage.model.enregistreur.Lecteur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GlobalData {
    private GlobalData(){} //static class

    public static ObservableList<Species> species;
    public static TodoList tasks;

    public static ArrayList<String> taskNames;

    public static HashMap<String , String> vieuxnoms , nomsvieux;

    public static Stage primaryStage;

    public static StringConverter<LocalDate> getDateConverter() {
        return new StringConverter<>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            public String toString(LocalDate localDate) {
                if (localDate == null) return "";
                return dateTimeFormatter.format(localDate);
            }

            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) return null;
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        };
    }

    public static void recuperrageDesDonnees(String path){
        Lecteur lecteur = new Lecteur(path);
        ArrayList<Object> data = lecteur.recuperrageDesDonnees();
        species = new ObservableList<>((ArrayList<Species>) data.get(0));
        tasks = new TodoList((ArrayList<Task>) data.get(1));
        vieuxnoms = (HashMap<String, String>) data.get(2);
        nomsvieux = (HashMap<String, String>) data.get(3);
        taskNames = (ArrayList<String>) data.get(4);
    }

    public static void enregistre(String path){
        Ecrivain ecrivain = new Ecrivain(path, species, tasks);
        ecrivain.enregistrer();
    }
}
