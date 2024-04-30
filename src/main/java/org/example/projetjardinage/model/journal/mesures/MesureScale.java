package org.example.projetjardinage.model.journal.mesures;

import java.util.ArrayList;
import java.util.List;

public class MesureScale extends Mesure {
    private int niveau;
    private int min;
    private int max;

    public MesureScale(int niveau, int min, int max){
        this.niveau = niveau;
        this.min = min;
        this.max = max;
    }

    public MesureScale(String val){ this.setNiveauLect(val); }

    public int getNiveau(){ return niveau; }
    public void setNiveau(int niveau) { this.niveau = niveau; }

    public int getMin(){ return min; }
    public void setMin(int min) { this.min = min; }

    public int getMax(){ return max; }
    public void setMax(int max) { this.max = max; }

    public void setNiveauLect(String niv){
        ArrayList<String> nivList = new ArrayList<>(List.of(niv.split("<SEP>")));
        if (nivList.size() == 1) {
            this.niveau = Integer.parseInt(nivList.getFirst());
            this.min = 0;
            this.max = 3;
        } else {
            this.min = Integer.parseInt(nivList.get(0));
            this.max = Integer.parseInt(nivList.get(1));
            this.niveau = Integer.parseInt(nivList.get(2));
        }
    }
}
