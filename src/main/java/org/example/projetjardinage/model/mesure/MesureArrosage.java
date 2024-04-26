package org.example.projetjardinage.model.mesure;

public class MesureArrosage extends Mesure {
    public enum Niveau { A1, A2, A3, A4 }

    private Niveau niveau;

    public MesureArrosage(){this.niveau = Niveau.A4;}
    public MesureArrosage(Niveau niveau) {
        this.niveau = niveau;
    }

    public MesureArrosage(String val){ this.setNiveauLect(val);}

    public Niveau getNiveau(){ return niveau; }
    public void setNiveau(Niveau niveau) { this.niveau = niveau; }
    public void setNiveauLect(String niv){
        int niveau = Integer.parseInt(niv);
        if(niveau ==1){
            this.niveau = Niveau.A1;
        } else if (niveau ==2){
            this.niveau = Niveau.A2;
        } else if (niveau ==3){
            this.niveau = Niveau.A3;
        } else if (niveau == 4){
            this.niveau = Niveau.A4;
        } else {
            System.out.println("Erreur dans le typage de l'arrosage a la lecture.");
        }
    }
}
