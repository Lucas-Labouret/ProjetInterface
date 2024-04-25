package org.example.projetjardinage;

import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.enregistreur.Lecteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (List<String> spe : donnees) {
            Species esp = plantes.get(indexPlantes.get(spe.get(4)));
            Specimen test = new Specimen(spe, esp);
            esp.addSpecimens(test);
        }
    }
}
