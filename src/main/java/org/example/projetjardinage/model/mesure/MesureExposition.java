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

    public void setForceLect(String mes){
        switch (mes) {
            case "O" -> this.force = Force.Ombrage;
            case "SO" -> this.force = Force.SemiOmbrage;
            case "S" -> this.force = Force.Soleil;
            case "PS" -> this.force = Force.PleinSoleil;
            case null, default -> System.out.println("Erreur constructeur Expo");
        }
    }

    public Force getForce() { return force; }

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


}
