package org.example.projetjardinage.model.mesure;

import java.util.ArrayList;
import java.util.List;

public class PlageMesure {

    private List<String> noms = new ArrayList<>();
    private List<InfoMesure> condition = new ArrayList<>();
    private List<InfoMesure> mesures = new ArrayList<>();

    public PlageMesure(){
        this.condition.add(new InfoMesure("Exposition", TypeMesure.Exposition, ""));
        this.condition.add(new InfoMesure("Arrosage", TypeMesure.Arrosage, ""));
        this.condition.add(new InfoMesure("Type de sol", TypeMesure.TypeSol, ""));
        this.condition.add(new InfoMesure("Espace au sol", TypeMesure.Numerique, "cm2"));
        this.condition.add(new InfoMesure("Ph", TypeMesure.Numerique, ""));
        this.condition.add(new InfoMesure("En terre", TypeMesure.Bool, ""));

        this.mesures.add(new InfoMesure("Hauteur", TypeMesure.Numerique, "cm"));
        this.mesures.add(new InfoMesure("Largeur", TypeMesure.Numerique, "cm"));
        for(InfoMesure m : this.getAll()){
            this.noms.add(m.getName());
        }
    }

    public void addMesure(String name, String type, String unit){

        if( !(this.noms.contains(name)) ){
            this.mesures.add(new InfoMesure(name, type , unit));
            this.noms.add(name);
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

}
