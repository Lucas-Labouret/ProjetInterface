package org.example.projetjardinage.model.enregistreur;

import org.example.projetjardinage.model.Lists.ObservableList;
import org.example.projetjardinage.model.Lists.TodoList;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;
import org.example.projetjardinage.model.Task;
import org.example.projetjardinage.model.journal.InfoMesure;
import org.example.projetjardinage.model.journal.JournalEntry;
import org.example.projetjardinage.model.journal.PlageMesure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ecrivain {
    private List<String> texte;

    public static ObservableList<Species> species;
    public static TodoList tasks;

    private String path;


    public Ecrivain(String path, ObservableList<Species> species,  TodoList tasks ){
        this.path = path;
        this.species = species;
        this.tasks = tasks;
    }

    private void setEsp(List<String> species){
        texte.add("ESP");
        texte.add(String.valueOf(species.size()));
        texte.addAll(species);
    }
    private List<String> ajoutEsp() {
        List<String> speciesEcr = new ArrayList<>();
        for (Species spe : species.getElements()) {
            //9 cases par espece, NOM, FAV, PIC, NOTES, 8 * MESURES, NBnouvelles mesures, Nouvelles mesures (3 cases chacunes)
            speciesEcr.add(spe.getName());
            speciesEcr.add(String.valueOf(spe.getFavorite()));
            speciesEcr.add(spe.getProfilePicURL());
            speciesEcr.add(spe.getNotes());
            speciesEcr.addAll(getMesures(spe));
            speciesEcr.add(String.valueOf(8 - spe.getNbMesures()));
            for (int i = 0; i < 8 - spe.getNbMesures(); i++) {
                List<InfoMesure> plage = spe.getMesures().getNvMesures();
                for (InfoMesure info : plage) {
                    speciesEcr.add(info.getName());
                    speciesEcr.add(info.getStringType());
                    speciesEcr.add(info.getUnit());
                }
            }
        }
        return speciesEcr;
    }
    private void setSpe(List<String> specimens){
        texte.add("SPE");
        texte.add(String.valueOf(specimens.size()));
        texte.addAll(specimens);
    }

    private String StringOF(LocalDate date){
        String d = "";
        String day = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonthValue());
        int year = date.getYear();
        if(day.length()==1){
            day = "0"+day;
        }
        if(month.length()==1){
            month = "0"+month;
        }
        return d + day + month + year;
    }
    private List<String> JournalEcr(JournalEntry entree, PlageMesure plage){
        List<String> entreeEcr = new ArrayList<>();
        for(InfoMesure info : plage.getAll()){
            if(entree.contientMesure(info)){
                entreeEcr.add(entree.getMesureInfo(info).getMesureString());
            } else {
                entreeEcr.add("<N>");
            }
        }
        return entreeEcr;
    }
    private List<String> ajoutSpe(){
        List<String> speEcr = new ArrayList<>();
        for(Species esp : species.getElements()){
            for(Specimen spe : esp.getSpecimens()){
                //9 cases par mesures, NOM, DATE, ALIVE, PIC, SPE, NOTES1, NOTES2, JOURNAL
                speEcr.add(spe.getName());
                speEcr.add(StringOF(spe.getMiseEnTerre()));
                speEcr.add(String.valueOf(spe.isAlive()));
                speEcr.add(spe.getProfilePic());
                speEcr.add(esp.getName());
                speEcr.add(spe.getNoteSpecimen());
                speEcr.add(spe.getNoteEntretien());

                //JOURNAL
                speEcr.add(String.valueOf(8-esp.getNbMesures())); //nb nouvelles mesures
                speEcr.add(String.valueOf(spe.getJournal().size())); //nb d'entrees
                for(LocalDate date : spe.getJournal().keySet()){
                    speEcr.add(StringOF(date));
                    speEcr.addAll(JournalEcr(spe.getJournal().get(date), esp.getMesuresInfos()));
                }
            }

        }
        return speEcr;
    }
    private void setTask(List<String> taches){
        texte.add("TASK");
        texte.add(String.valueOf(taches.size()));
        texte.addAll(taches);
    }

    private List<String> getMesures(Species spe){
        List<String> mes = new ArrayList<>();
        return mes;
    }

    private List<String> ajoutTaskRec(List<Task> taches){
        List<String> taskEcr = new ArrayList<>();

        for(Task t : taches){
            taskEcr.add(t.getName());
            taskEcr.add(String.valueOf(t.isDone()));
            taskEcr.add(String.valueOf(t.getDescription()));
            taskEcr.add(StringOF(t.getDueDate()));
            if(t.getParent()==null){
                taskEcr.add("<N>");
            } else {
                taskEcr.add(t.getParent().getName());
            }
            taskEcr.add(String.valueOf(t.getRecurrence()));
            List<Species> especesConcernees = t.getLinkedSpecies();
            taskEcr.add(String.valueOf(especesConcernees.size()));
            for(Species esp : especesConcernees){
                taskEcr.add(esp.getName());
            }
            List<Specimen> specimenConcernees = t.getLinkedSpecimens();
            taskEcr.add(String.valueOf(specimenConcernees.size()));
            for(Specimen spe : specimenConcernees){
                taskEcr.add(spe.getName());
            }

            //sous-taches
            taskEcr.addAll(ajoutTaskRec(t.getSubTasks()));
        }
        return taskEcr;
    }
    private List<String> ajoutTask(){
        return ajoutTaskRec(tasks.getElements());
    }
    public void enregistrer(){

        //Ajout des especes
        setEsp(ajoutEsp());

        //Ajout des specimens
        setSpe(ajoutSpe());

        //Ajout des tasks;
        setTask(ajoutTask());
    }






}

