package org.example.projetjardinage.model.mesure;

public class MesureExposition extends Mesure {
    public enum Force {
        Ombrage, SoleilSaMere
    }

    private Force force;

    public MesureExposition(Force force){
        this.force = force;
    }

    public Force getForce() { return force; }
    public void setForce(Force force) { this.force = force; }
}
