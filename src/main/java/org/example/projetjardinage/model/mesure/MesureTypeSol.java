package org.example.projetjardinage.model.mesure;

import java.util.Objects;

public class MesureTypeSol extends Mesure {
    public enum Type {
        Argile, Calcaire, Caillouteux
    }

    private Type type;

    public MesureTypeSol(){this.type = Type.Caillouteux;}
    public MesureTypeSol(Type type){
        this.type = type;
    }

    public MesureTypeSol(String val){ this.setTypeLect(val);}

    public Type getValue() { return type; }
    public void setType(Type type) { this.type = type; }

    public void setTypeLect(String type){
        switch (type) {
            case "Ar" -> this.type = Type.Argile;
            case "Co" -> this.type = Type.Caillouteux;
            case "Ca" -> this.type = Type.Calcaire;
            default -> System.out.println("Erreur typage du sol a la lecture");
        }
    }

    public TypeMesure getType(){
        return TypeMesure.TypeSol;
    }
}
