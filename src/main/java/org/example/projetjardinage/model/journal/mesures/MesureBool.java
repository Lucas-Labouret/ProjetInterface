package org.example.projetjardinage.model.journal.mesures;

public class MesureBool extends Mesure{
    private boolean value;


    public MesureBool(boolean value) {
        this.value = value;
    }

    public MesureBool(String val){
        this.setValueLect(val);
    }

    public boolean getValue() { return value; }
    public void setValue(boolean value) { this.value = value; }

    public void setValueLect(String val) {
        if(val.equals("1")){
            this.value = true;
        } else if (val.equals("0")){
            this.value = false;
        } else {
            System.out.println("Erreur dans la string de set d'une journal booleenne");
        }
    }


}
