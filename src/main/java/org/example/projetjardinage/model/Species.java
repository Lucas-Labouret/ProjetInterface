package org.example.projetjardinage.model;

import org.example.projetjardinage.model.journal.InfoMesure;
import org.example.projetjardinage.model.journal.OptimalHolder;
import org.example.projetjardinage.model.journal.PlageMesure;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.mesures.MesureList;
import org.example.projetjardinage.model.journal.mesures.TypeMesure;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Species extends Observable {
    private String name;
    private String oldName;
    private boolean favorite = false;
    private String profilePic = "";

    private String notes = "";
    private final ArrayList<Specimen> specimens = new ArrayList<>();
    private final ArrayList<Task> taskList = new ArrayList<>();

    private OptimalHolder mesuresOpti ;

    private PlageMesure mesuresPoss;

    public Species(String name) {
        this.name = name;
    }

    public Species(List<String> elem){
        this.name = elem.get(0);
        this.oldName = elem.get(0);
        int fav = Integer.parseInt((elem.get(1)));
        this.favorite = (fav != 0);
        this.profilePic = elem.get(2);
        this.notes = elem.get(3);
        List<String> opti = elem.subList(4,10);
        if(!(elem.get(4).equals("<N>"))){
            this.mesuresOpti = new OptimalHolder(opti);
        }
        this.mesuresPoss = new PlageMesure();
        int nbNouvellesMesures = Integer.parseInt(elem.get(10));
        for(int i = 0; i<nbNouvellesMesures*3;i=i+3){
            this.mesuresPoss.addMesure(elem.get(11+i),elem.get(11+i+1),elem.get(11+i+2));
        }
    }

    public boolean hasMes(){
        return this.mesuresOpti != null;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        sendNotif();
    }

    public String getOldName() {
        return oldName;
    }
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }



    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
    public boolean getFavorite() { return this.favorite; }
    public String getProfilePicURL() { return profilePic; }

    public void setProfilePicURL(String profilePic) { this.profilePic = profilePic; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getNbSpecimens() { return specimens.size(); }
    public void addSpecimens(Specimen... p){ specimens.addAll(List.of(p)); }
    public void removeSpecimens(Specimen... p){ specimens.removeAll(List.of(p)); }
    public ArrayList<Specimen> getSpecimens() { return specimens; }

    public void addTasks(Task... t) { taskList.addAll(List.of(t)); }
    public void removeTasks(Task... t) { taskList.removeAll(List.of(t)); }
    public ArrayList<Task> getRelatedTasks() {
        return taskList;
    }

    public OptimalHolder getMesuresOpti() { return mesuresOpti; }

    public PlageMesure getMesures(){ return this.mesuresPoss;}




    public void resetProfilePic(){
        if(specimens.size() == 0){
            profilePic = "/icons/267203.png";
            return;
        }
        Path absolutePath = FileSystems.getDefault().getPath(
                "src/main/resources/galerie/" + getName() +
                        "/" + getSpecimens().get(0).getName()
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
        if(!Files.exists(oldpath) || profilePic == "/icons/267203.png"){
            if (listOfFiles.length > 0) {
                profilePic = "/galerie/" + getName() + "/" + getSpecimens().get(0).getName() +"/"+listOfFiles[0].getName();
                System.out.println(profilePic);
            } else{
                profilePic = "/icons/267203.png";
                System.out.println(profilePic);
            }

        }
    }

    public int getNbMesures() {
        if (this.mesuresPoss == null) {
            return 8;
        } else {
            return this.mesuresPoss.getTaille();
        }
    }

    public PlageMesure getMesuresInfos(){return this.mesuresPoss;}

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
        for(int i = 0; i< this.mesuresPoss.getTaille();i++){
            InfoMesure info = this.mesuresPoss.getAll().get(i);
            TypeMesure type = info.getType();
            List<MesureHolder> mes = new ArrayList<>();
            for(Specimen spe : this.specimens){
                List<MesureHolder> speMoy = spe.getMoyenne(info);
                if(!speMoy.isEmpty()){
                    mes.add(this.moyenne(speMoy,info));
                }
            }
            if (info.getType() == TypeMesure.Bool) {
                info = new InfoMesure(info.getName(), TypeMesure.Numeric, "%");
            }
            if (!mes.isEmpty()) moyennes.add(this.moyenne(mes, info));
        }
        return moyennes;
    }
}
