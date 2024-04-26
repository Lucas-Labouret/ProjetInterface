package org.example.projetjardinage;

import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.enregistreur.Lecteur;

import java.util.*;

public class GlobalData {
    private GlobalData(){} //static class

    public static List<Species> plantes;
    public static List<Task> taches;

    public static HashMap<String , String> vieuxnoms , nomsvieux;

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
    }

}
