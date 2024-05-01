package org.example.projetjardinage.model.mesure;

public class MesureTexte extends Mesure {
    private String texte;

    MesureTexte(String texte){ this.texte = texte; }

    public String getValue(){ return texte; }
    void setTexte(String texte){ this.texte = texte; }


    public TypeMesure getType(){
        return TypeMesure.Texte;
    }
}
