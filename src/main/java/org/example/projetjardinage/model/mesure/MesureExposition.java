package org.example.projetjardinage.model.mesure;

import java.util.Objects;

public class MesureExposition extends Mesure {
    public enum Force {
        Ombrage, SemiOmbrage, Soleil, PleinSoleil
    }

    private Force force;

    public MesureExposition(){this.force = Force.PleinSoleil;}
    public MesureExposition(Force force){
        this.force = force;
    }

    public MesureExposition(String val){
        this.setForceLect(val);
    }

    public void setForceLect(String mes){
        switch (mes) {
            case "O" -> this.force = Force.Ombrage;
            case "SO" -> this.force = Force.SemiOmbrage;
            case "S" -> this.force = Force.Soleil;
            case "PS" -> this.force = Force.PleinSoleil;
            default -> System.out.println("Erreur constructeur Expo");
        }
    }

    public Force getValue() { return force; }

    public String getEcritForce() {
        if (this.force == Force.Ombrage){
            return "O";
        } else if (this.force == Force.SemiOmbrage){
            return "SO";
        } else if (this.force == Force.Soleil){
            return "S";
        } else {
            return "PS";
        }
    }
    public void setForce(Force force) { this.force = force; }

    public TypeMesure getType(){
        return TypeMesure.Exposition;
    }
}
