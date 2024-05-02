package org.example.projetjardinage.model;

import org.example.projetjardinage.model.journal.InfoMesure;
import org.example.projetjardinage.model.journal.Journal;
import org.example.projetjardinage.model.journal.PlageMesure;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.mesures.MesureList;
import org.example.projetjardinage.model.journal.mesures.TypeMesure;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Specimen {
    private Species species;
    private String name;
    private String oldName;
    private boolean alive;
    private LocalDate miseEnTerre;

    private List<LocalDate> lesDates = new ArrayList<>(4);


    private String profilePic;

    private String noteSpecimen;
    private String noteEntretien;

    private Journal journal;

    private List<Task> taskList = new ArrayList<>();

    public Specimen(Species species, String name, LocalDate miseEnTerre) {
        this.species = species;
        this.name = name;
        this.alive = true;
        this.miseEnTerre = miseEnTerre;
        this.journal = new Journal(species);
        this.setLesDates();
    }

    private void setLesDates(){
        for(int i = 0; i<4; i++) {
            this.lesDates.add(this.miseEnTerre);
        }
    }

    public Specimen(List<String> elem, Species spe, List<List<String>> journ, List<List<String>> photos){
        this.name = elem.get(0);
        this.oldName = elem.get(0);
        String date = elem.get(1);
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(2,4));
        int year = Integer.parseInt(date.substring(4,8));
        miseEnTerre = LocalDate.of(year, Month.of(month),day);
        int al = Integer.parseInt(elem.get(2));
        this.alive = (al != 0);
        this.profilePic = elem.get(3);
        this.noteSpecimen = elem.get(5);
        this.noteEntretien = elem.get(6);
        this.species = spe;
        this.journal = new Journal(journ,photos, spe.getMesures(), spe);
        this.setLesDatesLect();
    }

    private void setLesDatesLect(){
        this.setLesDates();
        ArrayList<LocalDate> datesOrd = journal.getSortedDates();
        if(datesOrd.size() == 0){
            this.setLesDates();
        } else {
            this.lesDates.set(0,datesOrd.get(datesOrd.size() - 1));
            boolean Coupe = true;
            boolean Rempot = true;
            boolean Recolte = true;
            while (Coupe && Rempot && Recolte && datesOrd.size() > 0) {
                LocalDate date = datesOrd.get(datesOrd.size() - 1);
                if (journal.get(date).getRecolt()) {
                    this.lesDates.set(3, date);
                    Recolte = false;
                }
                if (journal.get(date).getRempot()) {
                    this.lesDates.set(1, date);
                    Rempot = false;
                }
                if (journal.get(date).getCoup()) {
                    this.lesDates.set(2, date);
                    Coupe = false;
                }
            }
            if (!Coupe) {
                this.lesDates.set(2, miseEnTerre);
            }
            if (!Rempot) {
                this.lesDates.set(1, miseEnTerre);
            }
            if (!Recolte) {
                this.lesDates.set(3, miseEnTerre);
            }
        }
    }

    public Species getSpecies() {
        return species;
    }

    public Journal getJournal() {
        return journal;
    }

    public PlageMesure getMesuresPoss(){
        return this.species.getMesures();
    }

    public void newJournalEntry(List<String> elem){
        int nb = Integer.parseInt(elem.get(0));


    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void resetProfilePic(){
        Path absolutePath = FileSystems.getDefault().getPath(
                "src/main/resources/galerie/" + species.getName() +
                        "/" + this.getName()
        ).toAbsolutePath();

        char[] a = absolutePath.toString().toCharArray();

        String t = "";

        for (char c : a) {
            t += c;
        }
        File dossier = new File(t);
        File[] listOfFiles = dossier.listFiles();
        Path oldpath = FileSystems.getDefault().getPath(
                    "src/main/resources/" + profilePic).toAbsolutePath();
        if(!Files.exists(oldpath)) {
            if (listOfFiles.length > 0) {
                profilePic = "/galerie/" + species.getName() + "/" + name +"/"+listOfFiles[0].getName();
                System.out.println(profilePic);
            } else{
            profilePic = "/icons/267203.png";
        }

    }
        System.out.println(name);
        System.out.println(profilePic);
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getProfilePic() {
        return profilePic;
    }
    public boolean isAlive(){ return alive; }
    public void setStatus(boolean alive){ this.alive = alive; }

    public void addJournalEntry(LocalDate date, org.example.projetjardinage.model.journal.JournalEntry entry){
        for (LocalDate d: journal.keySet())
            if (date.isEqual(d)) throw new RuntimeException("Date already has an associated entry");

        journal.put(date, entry);

        this.lesDates.set(0,LocalDate.now());
        if(entry.getRempot()){this.lesDates.set(1,LocalDate.now());}
        if(entry.getCoup()){this.lesDates.set(2,LocalDate.now());}
        if(entry.getRecolt()){this.lesDates.set(3,LocalDate.now());}
    }

    public void addTask(Task tache){this.taskList.add(tache);}

    public List<MesureHolder> getMoyenne(InfoMesure info){
        List <MesureHolder> mesures = new ArrayList<>();
        for(org.example.projetjardinage.model.journal.JournalEntry journ : this.journal.values()){
            MesureHolder mes = journ.getMesureInfo(info);
            if(!((mes.getMesure()).toString().equals("<Echec>"))){
                mesures.add(mes);
            }
        }
        return mesures;
    }

    public LocalDate getMiseEnTerre() {
        return miseEnTerre;
    }

    public String getNoteSpecimen(){
        return noteSpecimen;
    }

    public String getNoteEntretien(){return noteEntretien;}

    public LocalDate getDateCoupe(){  //rempoter, couper, recolyter
        return this.lesDates.get(2);
    }

    public MesureHolder moyenne(List<MesureHolder> mes, InfoMesure info){
        String newName = info.getName()+(" Moyenne");
        MesureHolder moy;
        switch(info.getType()){
            case Bool -> {
                int vrai = 0;
                int faux = 0;
                for (MesureHolder mesure : mes){
                    if((boolean)(mesure.getMesure().getValue())){
                        vrai = vrai +1;
                    } else {
                        faux = faux +1;
                    }
                }
                float res = (float)(100*vrai) / (float)(faux+vrai);

                moy = MesureHolder.newMesureNumerique(newName,res,"%");
            }
            case Numeric -> {
                float somme = 0;
                for(MesureHolder mesure : mes){
                    somme = somme + (float) mesure.getMesure().getValue();
                }
                moy = MesureHolder.newMesureNumerique(newName, somme/mes.size(), info.getUnit());
            }
            case Scale -> {
                int somme = 0;
                for(MesureHolder mesure : mes){
                    somme = somme + (int) mesure.getMesure().getValue();
                }
                String scale = info.getUnit();
                ArrayList<String> minMax = new ArrayList<>(List.of(scale.split("<SEP>")));
                int min = Integer.parseInt(minMax.get(0));
                int max = Integer.parseInt(minMax.get(1));
                moy = MesureHolder.newMesureScale(newName, somme/mes.size(), min ,max);
            }
            case List -> {
                HashMap<String, Integer> compt = new HashMap<>();
                System.out.println(info.getName());
                System.out.println(mes.get(0).getName());
                System.out.println(mes.get(0).getType());
                System.out.println(info.getType());
                MesureList test = (MesureList) mes.get(0).getMesure();
                List<String> types = test.getTypes();
                for (String typ : types){
                    compt.put(typ, 0);
                }
                for(MesureHolder mesure : mes){
                    String typ = (String) mesure.getMesure().getValue();
                    compt.replace(typ, compt.get(typ)+1);
                }
                String max = test.getValue().toString();
                for(String typ : compt.keySet()){
                    if(compt.get(typ) > compt.get(max)){
                        max = typ;
                    }
                }
                moy = MesureHolder.newMesureList(newName,max);
            }
            default -> moy = MesureHolder.newMesureBool("Echec", true);
        }
        return moy;
    }
    public List<MesureHolder> getMesuresMoyennes(){
        List<MesureHolder> moyennes = new ArrayList<>();
        for(InfoMesure info : species.getMesuresInfos().getAll()){
            List<MesureHolder> mesures = this.getMoyenne(info);
            moyennes.add(moyenne(mesures,info));
        }
        return moyennes;
    }

    public LocalDate getDateRempot(){
        return this.lesDates.get(1);
    }

    public LocalDate getDateRecolte(){
        return this.lesDates.get(3);
    }

    public LocalDate getDerniereEntree(){
        return this.lesDates.get(0);
    }
}