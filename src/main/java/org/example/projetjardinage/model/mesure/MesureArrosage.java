package org.example.projetjardinage.model.mesure;

public class MesureArrosage extends Mesure {
    public enum Niveau { A1, A2, A3, A4 }

    private Niveau niveau;

    public MesureArrosage(Niveau niveau) {
        this.niveau = niveau;
    }

    public Niveau getNiveau(){ return niveau; }
    public void setNiveau(Niveau niveau) { this.niveau = niveau; }
}
