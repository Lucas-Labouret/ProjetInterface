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
        if(Objects.equals(mes, "O")){
            this.force = Force.Ombrage;
        } else if (Objects.equals(mes, "SO")){
            this.force = Force.SemiOmbrage;
        } else if (Objects.equals(mes, "S")){
            this.force = Force.Soleil;
        } else if (Objects.equals(mes, "PS")){
            this.force = Force.PleinSoleil;
        }else {
            System.out.println("Erreur constructeur Expo");
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
