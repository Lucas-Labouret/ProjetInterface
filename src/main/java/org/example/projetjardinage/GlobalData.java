package org.example.projetjardinage;

import javafx.stage.Stage;

import org.example.projetjardinage.model.Lists.ObservableList;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
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
        ArrayList<Species> plantes = new ArrayList<>();
        ArrayList<Task> taches = new ArrayList<>();
        Lecteur lec = new Lecteur(path);
        List<List<String>> donnees;

        donnees = lec.getEsp();
        Map<String, Integer> indexPlantes = new HashMap<>();

        for (List<String> esp : donnees) {

            Species test = new Species(esp);
            plantes.add(test);
            indexPlantes.put(test.getName(),plantes.size()-1);

        }

        donnees = lec.getSpe();
        Map<String, Specimen> indexSpecimen = new HashMap<>();

        for (List<String> spe : donnees) {
            Species esp = plantes.get(indexPlantes.get(spe.get(4)));
            List<List<String>> journ = new ArrayList<>();
            int nbEntree = Integer.parseInt(spe.get(7));
            int nbMes = esp.getNbMesures()+1;
            for(int i = 0;i<nbEntree;i++){
                if(8+(i+1)*nbMes == spe.size()){
                    List<String> tmp = (spe.subList(8+i*nbMes,8+nbMes+(i*nbMes)));
                    journ.add(tmp);
                } else{
                    journ.add(spe.subList(8+i*nbMes,8+(i+1)*nbMes));
                }
            }
            Specimen test = new Specimen(spe, esp, journ);
            esp.addSpecimens(test);
            indexSpecimen.put(test.getName(),test);
        }

        donnees = lec.getTasks();
        Map<String, Task> indexTask = new HashMap<>();

        for(List<String> tas : donnees) {
            List<Species> esp = new ArrayList<>();
            List<Specimen> spe = new ArrayList<>();
            //ajout des especes
            int nbEsp = Integer.parseInt(tas.get(6));
            for (int i = 0; i <nbEsp; i++){
                Species current = plantes.get(indexPlantes.get(tas.get(7+i)));
                esp.add(current);
            }
            //ajout des specimens
            int nbSpe = Integer.parseInt(tas.get(7+nbEsp));

            for(int i = 1; i<=nbSpe; i++){
                Specimen current = indexSpecimen.get(tas.get(7+nbEsp+i));
                spe.add(current);
            }

            Task surTache = new Task();
            if( !(Objects.equals(tas.get(4), "<N>") )) {
                surTache = indexTask.get(tas.get(4));
            }


            Task task = new Task(tas, esp, spe, surTache);
            LocalDate hui = LocalDate.now();
            LocalDate passe = hui.minusDays(30);

            //avance les taches recursives si finies ET dans le passe
            if(task.isRec() && task.isDone() && hui.isAfter(task.getDueDate())) {
                task.setNextDate();
            }

            //enleve les taches qui sont à plus d'un mois dans le passe
            if (passe.isBefore(task.getDueDate())  || task.isRec()) {
                //avance les taches recursives jusqu'a moins d'un mois dans le passe
                while(task.isRec() && passe.isAfter(task.getDueDate())){
                    task.setNextDate();
                }
                if(Objects.equals(tas.get(4), "<N>")) {
                    taches.add(task);
                }

                indexTask.put(task.getName(), task);

                //ajout dans les especes
                for (Species espece : esp) {
                    espece.addTasks(task);
                }

                //ajout dans les specimens
                for (Specimen specimen : spe) {
                    specimen.addTask(task);
                }

                //sous-tache
                if( !(Objects.equals(tas.get(4), "<N>") )) {
                    surTache.addSubTasks(task);
                }
            }


        }

        vieuxnoms = new HashMap<String,String>();
        nomsvieux = new HashMap<String,String>();
        for (Species spece: plantes) {
            vieuxnoms.put(spece.getName(), spece.getName());
            nomsvieux.put(spece.getName(), spece.getName());
            for (Specimen specimen: spece.getSpecimens()) {
                vieuxnoms.put(spece.getName(), spece.getName());
                nomsvieux.put(spece.getName(), spece.getName());
            }
        }

        taches.addAll(List.of(
                new Task("Task 1", "Description 1", LocalDate.of(2024, 1, 1)),
                new Task("Task 2", "Description 2", LocalDate.of(2024, 1, 1)),
                new Task("Task 3", "Description 3", LocalDate.of(2024, 1, 1)),
                new Task("Task 4", "Description 4", LocalDate.of(2024, 1, 2)),
                new Task("Task 5", "Description 5", LocalDate.of(2024, 1, 3)),
                new Task("Task 6", "Description 6", LocalDate.of(2024, 2, 4)),
                new Task("Task 7", "Description 7", LocalDate.of(2025, 2, 5)),
                new Task("Task 8", "Description 8", LocalDate.of(2025, 2, 6))
        ));
        taches.getFirst().addSubTasks(
                new Task("SubTask 1", "SubDescription 1", LocalDate.of(2024, 1, 1)),
                new Task("SubTask 2", "SubDescription 2", LocalDate.of(2024, 1, 1)),
                new Task("SubTask 3", "SubDescription 3", LocalDate.of(2024, 1, 1))
        );
        taches.getFirst().getSubTasks().getLast().addSubTasks(
                new Task("SubSubTask 1", "SubSubDescription très très très très très très très très très très très très très très très très très très longue", LocalDate.of(2024, 1, 1)),
                new Task("SubSubTask 2", "SubSubDescription 2", LocalDate.of(2024, 1, 1)),
                new Task("SubSubTask 3", "SubSubDescription 3", LocalDate.of(2024, 1, 1))
        );
        taches.getFirst().getSubTasks().getLast().getSubTasks().getLast().addSubTasks(
                new Task("SubSubSubTask 1", "SubSubSubDescription 1", LocalDate.of(2024, 1, 1))
        );

        addParents(taches);
        tasks = new TodoList(taches);
        species = new ObservableList<>(plantes);
    }

    private static void addParents(List<Task> tasks) {
        for (Task task : tasks) {
            for (Task subTask : task.getSubTasks()) {
                subTask.setParent(task);
            }
            addParents(task.getSubTasks());
        }
    }
}
