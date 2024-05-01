package org.example.projetjardinage.model.enregistreur;


import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Lecteur {
    //prend le CSC de stockage et le lit
    private List <String> text;
    private final String path;

    private int bookmark;

    public Lecteur(String p)  {
        this.path = p;
        this.text = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String ligne = reader.readLine();
            while( ligne != null){
                String[] placeholder = ligne.split(",");
                ligne = reader.readLine();
                this.text.addAll(Arrays.asList(placeholder));
            }
            reader.close();

            this.clean();




        } catch (FileNotFoundException e) {
            System.out.println("Problème à la lecture du fichier.");
            throw new RuntimeException(e);

        } catch (IOException e) {
            System.out.println("Problème de lecture de ligne à la lecture du fichier.");
            throw new RuntimeException(e);
        }



    }

    public List<List<String>> getMesures(){
        List<List<String>> mes = new ArrayList<>();
        if  (!((this.text.get(0)).equals("MES") ) ){
            System.out.println("ERREUR");
        }
        int nb = Integer.parseInt(this.text.get(1));
        for(int i = 0; i < nb; i=i+4){
            List<String> cas = new ArrayList<> (4);
            //4 cases par mesures, NOM, DESC, UNITE, TYPE
            cas.add(this.text.get(2+i));
            cas.add(this.text.get(3+i));
            cas.add(this.text.get(4+i));
            cas.add(this.text.get(5+i));

            mes.add(cas);
        }
        this.bookmark = (nb*4)+2;
        return mes;
    }

    public List<List<String>> getEsp(){
        List<List<String>> esp = new ArrayList<>();

        if  (!((this.text.get(this.bookmark)).equals("ESP") ) ){
            System.out.println("ERREUR_ESP");
        }
        int nb = Integer.parseInt(this.text.get(this.bookmark + 1));
        this.bookmark = this.bookmark+2;
        for(int i = 0; i < nb; i=i+1){

            List<String> cas = new ArrayList<> (9);
            //9 cases par mesures, NOM, FAV, PIC, NOTES, 5 * MESURES, NBnouvelles mesures, Nouvelles mesures (3 cases chacunes)
            for(int j = 0; j < 9; j++){
                cas.add(this.text.get(this.bookmark));
                this.bookmark = this.bookmark+1;
            }

            int nbNouvellesMesures = Integer.parseInt(this.text.get(this.bookmark));
            cas.add(this.text.get(this.bookmark));
            this.bookmark = this.bookmark+1;
            for(int j = 0; j<nbNouvellesMesures;j++){
                cas.add(this.text.get(this.bookmark));
                cas.add(this.text.get(this.bookmark+1));
                cas.add(this.text.get(this.bookmark+2));
                this.bookmark=this.bookmark+3;
            }
            esp.add(cas);
        }

        return esp;
    }

    public List<List<String>> getSpe(){
        List<List<String>> spe = new ArrayList<>();
        if  (!((this.text.get(this.bookmark)).equals("SPE") ) ){
            System.out.println("ERREUR_SPE");
        }
        int nb = Integer.parseInt(this.text.get(this.bookmark + 1));
        this.bookmark = this.bookmark + 2;
        for(int i = 0; i < nb; i=i+1){
            List<String> cas = new ArrayList<> (7);
            //9 cases par mesures, NOM, DATE, ALIVE, PIC, SPE, NOTES1, NOTES2, JOURNAL
            for(int j = 0; j < 7; j++){
                cas.add(this.text.get(this.bookmark ));
                this.bookmark = this.bookmark+1;
            }

            int nbNouvellesMesures = Integer.parseInt(this.text.get(this.bookmark));
            this.bookmark = this.bookmark + 1;
            cas.add(this.text.get(this.bookmark));
            int nbEntries = Integer.parseInt(this.text.get(this.bookmark));
            this.bookmark = this.bookmark+1;

            for (int j = 0; j < nbEntries; j++) {
                int nbCases = 8 + nbNouvellesMesures + 3;
                int nbPhotos = Integer.parseInt(this.text.get(bookmark+nbCases+1));
                nbCases = nbCases + 1 + nbPhotos;
                List<String> entree = this.text.subList(this.bookmark, this.bookmark + nbCases + 1);
                cas.addAll(entree);
                this.bookmark = this.bookmark + nbCases +1 ;

            }

            spe.add(cas);

        }

        return spe;
    }

    public List<List<String>> getTasks(){
        List<List<String>> task = new ArrayList<>();

        if  (!((this.text.get(this.bookmark)).equals("TASK") ) ){
            System.out.println("ERREUR_TASK");
        }

        int nb = Integer.parseInt(this.text.get(this.bookmark+1));
        this.bookmark = this.bookmark +2;
        for(int i = 0; i < nb; i=i+1){
            List<String> cas = new ArrayList<> (5);
            //4 cases par mesures, NOM, DONE, DEsCR, DDATE, SURTASK, REP, SPE, ESP
            for(int j = 2; j <8; j++){ //décalage des 2 premiers blocs
                cas.add(this.text.get(this.bookmark));
                this.bookmark = this.bookmark+1;
            }

            String nbSpe = this.text.get(this.bookmark);
            int nb2 = Integer.parseInt(nbSpe);  //nombre d'espece
            cas.add(nbSpe);
            this.bookmark = this.bookmark +1;
            for(int j = 1; j <= nb2; j++){ //décalage des 2 premiers blocs
                cas.add(this.text.get(this.bookmark));
                this.bookmark = this.bookmark +1;
            }
            nbSpe = this.text.get(this.bookmark);
            nb2 = Integer.parseInt(nbSpe);  //nombre de specimen
            cas.add(nbSpe);
            this.bookmark=this.bookmark+1;
            for(int j = 1; j <= nb2; j++){ //décalage des 2 premiers blocs
                cas.add(this.text.get(this.bookmark));
                this.bookmark=this.bookmark+1;
            }

            task.add(cas);
        }

        return task;
    }

    private void clean (){
        //remet les COM en virgule
        for(int i = 0; i<this.text.size();i++){
            String current = this.text.get(i);
            for(int j = 0; j<current.length()-2; j++){

                if(current.startsWith("<C>", j)){
                    String tmp1;
                    if (j==0){
                        tmp1 = "";
                    } else {
                        tmp1 = current.substring(0, j);
                    }
                    String tmp2 = current.substring(j+3);

                    this.text.set(i, tmp1 + "," + tmp2);
                    current = this.text.get(i);
                }
            }
        }
    }

    private void addParents(List<Task> tasks) {
        for (Task task : tasks) {
            for (Task subTask : task.getSubTasks()) {
                subTask.setParent(task);
            }
            addParents(task.getSubTasks());
        }
    }

    public ArrayList<Object> recuperrageDesDonnees() {
        ArrayList<Species> plantes = new ArrayList<>();
        ArrayList<Task> taches = new ArrayList<>();

        List<List<String>> donnees;

        donnees = this.getEsp();
        Map<String, Integer> indexPlantes = new HashMap<>();

        for (List<String> esp : donnees) {

            Species test = new Species(esp);
            plantes.add(test);
            indexPlantes.put(test.getName(), plantes.size() - 1);

        }

        donnees = this.getSpe();
        Map<String, Specimen> indexSpecimen = new HashMap<>();

        for (List<String> spe : donnees) {
            Species esp = plantes.get(indexPlantes.get(spe.get(4)));
            List<List<String>> journ = new ArrayList<>();
            List<List<String>> photos = new ArrayList<>();
            int nbEntree = Integer.parseInt(spe.get(7));
            int nbMes = esp.getNbMesures() + 1+3; //+1 pour la date, +3 pour les booleans
            int cmpt = 0;
            for (int i = 0; i < nbEntree; i++) {
                journ.add(spe.subList(8+cmpt + i * nbMes, 8 +cmpt+ (i + 1) * nbMes));
                int nbPhotos = Integer.parseInt(spe.get(8 + (i + 1) * nbMes));
                photos.add(spe.subList( (8+cmpt + (i + 1) * nbMes)+1, (8 +cmpt+ (i + 1) * nbMes)+1 + nbPhotos));
                cmpt = nbPhotos+1;
            }
            System.out.println(journ);
            Specimen test = new Specimen(spe, esp, journ, photos);
            esp.addSpecimens(test);
            indexSpecimen.put(test.getName(), test);
        }

        donnees = this.getTasks();
        Map<String, Task> indexTask = new HashMap<>();

        for (List<String> tas : donnees) {
            List<Species> esp = new ArrayList<>();
            List<Specimen> spe = new ArrayList<>();
            //ajout des especes
            int nbEsp = Integer.parseInt(tas.get(6));
            for (int i = 0; i < nbEsp; i++) {
                Species current = plantes.get(indexPlantes.get(tas.get(7 + i)));
                esp.add(current);
            }
            //ajout des specimens
            int nbSpe = Integer.parseInt(tas.get(7 + nbEsp));

            for (int i = 1; i <= nbSpe; i++) {
                Specimen current = indexSpecimen.get(tas.get(7 + nbEsp + i));
                spe.add(current);
            }

            Task surTache = new Task();
            if (!(Objects.equals(tas.get(4), "<N>"))) {
                surTache = indexTask.get(tas.get(4));
            }


            Task task = new Task(tas, esp, spe, surTache);
            LocalDate hui = LocalDate.now();
            LocalDate passe = hui.minusDays(30);

            //avance les taches recursives si finies ET dans le passe
            if (task.isRec() && task.isDone() && hui.isAfter(task.getDueDate())) {
                task.setNextDate();
            }

            //enleve les taches qui sont à plus d'un mois dans le passe
            if (passe.isBefore(task.getDueDate()) || task.isRec()) {
                //avance les taches recursives jusqu'a moins d'un mois dans le passe
                while (task.isRec() && passe.isAfter(task.getDueDate())) {
                    task.setNextDate();
                }
                if (Objects.equals(tas.get(4), "<N>")) {
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
                if (!(Objects.equals(tas.get(4), "<N>"))) {
                    surTache.addSubTasks(task);
                }
            }


        }

        HashMap<String, String> vieuxnoms = new HashMap<>();
        HashMap<String, String> nomsvieux = new HashMap<>();
        for (Species species : plantes) {
            vieuxnoms.put(species.getName(), species.getName());
            nomsvieux.put(species.getName(), species.getName());
            for (Specimen specimen : species.getSpecimens()) {
                vieuxnoms.put(specimen.getName(), specimen.getName());
                nomsvieux.put(specimen.getName(), specimen.getName());
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
        taches.get(0).addSubTasks(
                new Task("SubTask 1", "SubDescription 1", LocalDate.of(2024, 1, 1)),
                new Task("SubTask 2", "SubDescription 2", LocalDate.of(2024, 1, 1)),
                new Task("SubTask 3", "SubDescription 3", LocalDate.of(2024, 1, 1))
        );
        taches.get(0).getSubTasks().get(taches.get(0).getSubTasks().size()-1).addSubTasks(
                new Task("SubSubTask 1", "SubSubDescription très très très très très très très très très très très très très très très très très très longue", LocalDate.of(2024, 1, 1)),
                new Task("SubSubTask 2", "SubSubDescription 2", LocalDate.of(2024, 1, 1)),
                new Task("SubSubTask 3", "SubSubDescription 3", LocalDate.of(2024, 1, 1))
        );
        taches.get(0).getSubTasks().get(taches.get(0).getSubTasks().size()-1).getSubTasks().get(taches.get(0).getSubTasks().get(taches.get(0).getSubTasks().size()-1).getSubTasks().size()-1).addSubTasks(
                new Task("SubSubSubTask 1", "SubSubSubDescription 1", LocalDate.of(2024, 1, 1))
        );

        addParents(taches);
        return new ArrayList<>(List.of(plantes, taches, vieuxnoms, nomsvieux));
    }
}
