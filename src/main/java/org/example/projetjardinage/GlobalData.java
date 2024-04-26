package org.example.projetjardinage;

import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.TodoList;
import org.example.projetjardinage.model.enregistreur.Lecteur;

import java.time.LocalDate;
import java.util.*;

public class GlobalData {
    private GlobalData(){} //static class

    public static List<Species> plantes;
    public static TodoList tasks;

    public static void recuperrageDesDonnees(String path){
        plantes = new ArrayList<>();
        ArrayList<Task> taches = new ArrayList<>();
        Lecteur lec = new Lecteur(path);
        List<List<String>> donnees;

        donnees = lec.getEsp();
        Map<String, Integer> indexPlantes = new HashMap<>();

        for (List<String> esp : donnees) {
            if(esp.size()!=9){
                System.out.println("Probleme taille des especes lecture.");
            }

            Species test = new Species(esp);
            plantes.add(test);
            indexPlantes.put(test.getName(),plantes.size()-1);

        }

        donnees = lec.getSpe();
        Map<String, Specimen> indexSpecimen = new HashMap<>();

        for (List<String> spe : donnees) {
            Species esp = plantes.get(indexPlantes.get(spe.get(4)));
            Specimen test = new Specimen(spe, esp);
            esp.addSpecimens(test);
            indexSpecimen.put(test.getName(),test);
        }

        donnees = lec.getTasks();
        Map<String, Task> indexTask = new HashMap<>();

        for(List<String> tas : donnees) {
            List<Species> esp = new ArrayList<>();
            List<Specimen> spe = new ArrayList<>();
            //ajout des especes  6
            int nbEsp = Integer.parseInt(tas.get(6));

            for (int i = 0; i <nbEsp; i++){
                Species current = plantes.get(indexPlantes.get(tas.get(7+i)));
                esp.add(current);
            }

            int nbSpe = Integer.parseInt(tas.get(7+nbEsp));

            for(int i = 0; i<nbSpe; i++){
                Specimen current = indexSpecimen.get(tas.get(7+nbEsp+i));
                spe.add(current);
            }
            //ajout des specimens  7

            Task task = new Task(tas, esp, spe);
            taches.add(task);
            indexTask.put(task.getName(),task);
            for(Species espece : esp){
                espece.addTasks(task);
            }
            for(Specimen specimen : spe){
                specimen.addTask(task);
            }

            //sous-tache
            if( !(Objects.equals(tas.get(4), "<N>") )) {
                Task surTache = indexTask.get(tas.get(4));
                surTache.addSubTasks(task);
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
        tasks = new TodoList(taches);
    }
}
