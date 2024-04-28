package org.example.projetjardinage;

import javafx.stage.Stage;

import org.example.projetjardinage.model.Lists.ObservableList;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.Lists.TodoList;
import org.example.projetjardinage.model.enregistreur.Lecteur;

import java.time.LocalDate;
import java.util.*;

public class GlobalData {
    private GlobalData(){} //static class

    public static ObservableList<Species> species;
    public static TodoList tasks;

    public static HashMap<String , String> vieuxnoms , nomsvieux;

    public static Stage primaryStage;

    public static void recuperrageDesDonnees(String path){
        Lecteur lecteur = new Lecteur(path);
        ArrayList<Object> data = lecteur.recuperrageDesDonnees();
        species = new ObservableList<>((ArrayList<Species>) data.get(0));
        tasks = new TodoList((ArrayList<Task>) data.get(1));
        vieuxnoms = (HashMap<String, String>) data.get(2);
        nomsvieux = (HashMap<String, String>) data.get(3);
    }
}
