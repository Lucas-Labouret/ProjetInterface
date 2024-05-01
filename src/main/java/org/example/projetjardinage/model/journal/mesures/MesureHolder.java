package org.example.projetjardinage.model.journal.mesures;

import org.example.projetjardinage.model.journal.InfoMesure;

import java.util.ArrayList;

public class MesureHolder {

    private String name;
    private TypeMesure type;
    private Mesure mesure;

    private MesureHolder(String name, TypeMesure type, Mesure mesure){
        this.name = name;
        this.type = type;
        this.mesure = mesure;
    }

    public MesureHolder(InfoMesure info, String val){
        this.name = info.getName();
        this.type = info.getType();
        switch(info.getType()) {
            case Bool -> mesure = new MesureBool(val);
            case Scale -> mesure = new MesureScale(val, info.getUnit());
            case Numeric -> mesure = new MesureNumerique(val, info.getUnit()) ;
            case Text -> mesure = new MesureText(val) ;
            case List -> mesure = new MesureList(val);
            default -> {
                mesure = new MesureText(val);
                System.out.println("Erreur constructeur MesureHolder");
            }
        }
    }

    public MesureHolder(MesureHolder holder) {
        this.name = holder.getName();
        this.type = holder.getType();
        switch(holder.getType()) {
            case Bool -> mesure = new MesureBool(((MesureBool) holder.getMesure()).getValue());
            case Scale -> {
                MesureScale scale = (MesureScale) holder.getMesure();
                mesure = new MesureScale(scale.getNiveau(), scale.getMin(), scale.getMax());
            }
            case Numeric -> {
                MesureNumerique num = (MesureNumerique) holder.getMesure();
                mesure = new MesureNumerique(num.getValue(), num.getUnit());
            }
            case Text -> mesure = new MesureText(((MesureText) holder.getMesure()).getText()) ;
            case List -> {
                MesureList list = (MesureList) holder.getMesure();
                mesure = new MesureList(list.getTypes(), list.getType());
            }
            default -> {
                mesure = new MesureText(((MesureText) holder.getMesure()).getText());
                System.out.println("Erreur constructeur MesureHolder");
            }
        }
    }

    public static MesureHolder newMesureText(String name, String texte){
        Mesure mesure = new MesureText(texte);
        return new MesureHolder(name, TypeMesure.Text, mesure);
    }

    public static MesureHolder newMesureBool(String name, boolean value){
        Mesure mesure = new MesureBool(value);
        return new MesureHolder(name, TypeMesure.Bool, mesure);
    }

    public static MesureHolder newMesureNumerique(String name, Float value, String unit){
        Mesure mesure = new MesureNumerique(value, unit);
        return new MesureHolder(name, TypeMesure.Numeric, mesure);
    }

    public static MesureHolder newMesureList(String name, String type) {
        Mesure mesure = new MesureList(type);
        return new MesureHolder(name, TypeMesure.List, mesure);
    }
    public static MesureHolder newMesureList(String name, ArrayList<String> types) {
        Mesure mesure = new MesureList(types);
        return new MesureHolder(name, TypeMesure.List, mesure);
    }

    public static MesureHolder newMesureScale(String name, int niveau, int min, int max){
        Mesure mesure = new MesureScale(niveau, min, max);
        return new MesureHolder(name, TypeMesure.Scale, mesure);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TypeMesure getType() { return type; }
    public void setType(TypeMesure type) { this.type = type; }

    public Mesure getMesure() { return mesure; }
    public void setMesure(Mesure mesure) { this.mesure = mesure; }
}
