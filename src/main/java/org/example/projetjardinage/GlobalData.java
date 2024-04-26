package org.example.projetjardinage;

import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.enregistreur.Lecteur;

import java.time.LocalDate;
import java.util.*;

public class GlobalData {
    private GlobalData(){} //static class

    private static List<Species> plantes;
    private static List<Task> taches;

    public static void recuperrageDesDonnees(String path){
        plantes = new ArrayList<>();
        taches = new ArrayList<>();
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
             

            Task task = new Task(tas, esp, spe);
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

                taches.add(task);
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
                    Task surTache = indexTask.get(tas.get(4));
                    surTache.addSubTasks(task);
                }
            }


        }
    }
}
