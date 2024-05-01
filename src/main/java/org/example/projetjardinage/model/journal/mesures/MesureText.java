package org.example.projetjardinage.model.journal.mesures;

public class MesureText extends Mesure {
    private String texte;

    MesureText(String texte){ this.texte = texte; }

    public String getValue(){ return texte; }
    public void setText(String texte){ this.texte = texte; }
}
