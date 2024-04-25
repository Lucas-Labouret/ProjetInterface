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

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public void setTypeLect(String type){
        if (Objects.equals(type, "Ar")){
            this.type = Type.Argile;
        } else if (Objects.equals(type, "Co")){
            this.type = Type.Caillouteux;
        } else if (Objects.equals(type, "Ca")){
            this.type = Type.Calcaire;
        } else {
            System.out.println("Erreur typage du sol a la lecture");
        }
    }
}
