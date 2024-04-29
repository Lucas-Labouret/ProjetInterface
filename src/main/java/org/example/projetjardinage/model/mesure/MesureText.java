package org.example.projetjardinage.model.mesure;

public class MesureText extends Mesure {
    private String texte;

    MesureText(String texte){ this.texte = texte; }

    public String getText(){ return texte; }
    public void setText(String texte){ this.texte = texte; }
}
