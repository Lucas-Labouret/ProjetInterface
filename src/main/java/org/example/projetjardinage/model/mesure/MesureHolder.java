package org.example.projetjardinage.model.mesure;

public class MesureHolder {
    public enum Type{
        Texte,     //string
        Numerique, //float
        TypeSol,   //enum
        Arrosage,  //enum
        Exposition //enum
    }

    private String name;
    private Type type;
    private Mesure mesure;

    private MesureHolder(String name, Type type, Mesure mesure){
        this.name = name;
        this.type = type;
        this.mesure = mesure;
    }

    public static MesureHolder newTexteMesure(String name, String texte){
        Mesure mesure = new MesureTexte(texte);
        return new MesureHolder(name, Type.Texte, mesure);
    }


}
