package org.example.projetjardinage.model.journal;

import org.example.projetjardinage.model.journal.mesures.TypeMesure;

import java.util.ArrayList;
import java.util.List;

public class PlageMesure {

    private final List<String> noms = new ArrayList<>();
    private final List<InfoMesure> condition = new ArrayList<>();
    private final List<InfoMesure> mesures = new ArrayList<>();

    public PlageMesure(){
        this.condition.add(new InfoMesure("Exposition", TypeMesure.Scale, "1<SEP>4<SEP>1"));
        this.condition.add(new InfoMesure("Arrosage", TypeMesure.Scale, "1<SEP>4<SEP>1"));
        this.condition.add(new InfoMesure("Type de sol", TypeMesure.List, "Ca<SEP>Ce<SEP>Co<SEP>Ca"));
        this.condition.add(new InfoMesure("Espace au sol", TypeMesure.Numeric, "cm2"));
        this.condition.add(new InfoMesure("Ph", TypeMesure.Numeric, ""));
        this.condition.add(new InfoMesure("En terre", TypeMesure.Bool, ""));

        this.mesures.add(new InfoMesure("Hauteur", TypeMesure.Numeric, "cm"));
        this.mesures.add(new InfoMesure("Largeur", TypeMesure.Numeric, "cm"));
        updateNames();
    }

    private void updateNames(){
        this.noms.clear();
        for(InfoMesure m : this.getAll()){
            this.noms.add(m.getName());
        }
    }

    public boolean addMesure(String name, String type, String unit){
        if( !(this.noms.contains(name)) ){
            this.mesures.add(new InfoMesure(name, type , unit));
            this.noms.add(name);
            updateNames();
            return true;
        }
        return false;
    }

    public boolean addMesure(String name, TypeMesure type, String unit){
        if( !(this.noms.contains(name)) ){
            this.mesures.add(new InfoMesure(name, type , unit));
            this.noms.add(name);
            updateNames();
            return true;
        }
        return false;
    }

    public void removeMesure(String name){
        int index = this.noms.indexOf(name);
        if(index != -1){
            this.noms.remove(index);
            this.mesures.remove(index);
        }
    }

    public List<InfoMesure> getConditions(){
        return this.condition;
    }

    public List<InfoMesure> getMesures(){
        return this.mesures;
    }

    public List<InfoMesure> getAll(){
        List<InfoMesure> all = new ArrayList<>();
        all.addAll(this.condition);
        all.addAll(this.mesures);
        return all;
    }

    public int getTaille(){
        return this.noms.size();
    }

    public TypeMesure getType(int ind){
        return this.mesures.get(ind).getType();
    }

    public List<InfoMesure> getNvMesures(){
        List<InfoMesure> nv = new ArrayList<>(this.mesures);
        nv.remove(0);
        nv.remove(0);
        return nv;

    }

}
