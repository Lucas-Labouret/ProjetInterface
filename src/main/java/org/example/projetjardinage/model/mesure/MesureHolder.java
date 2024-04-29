package org.example.projetjardinage.model.mesure;

public class MesureHolder {

    private String name;
    private TypeMesure type;
    private Mesure mesure;

    private MesureHolder(String name, TypeMesure type, Mesure mesure){
        this.name = name;
        this.type = type;
        this.mesure = mesure;
    }

    public MesureHolder(InfoMesure info, String val){
        Mesure mes;
        switch(info.getType()) {
            case Bool -> mes = new MesureBool(val);
            case Scale -> mes = new MesureScale(val);
            case Numeric -> mes = new MesureNumerique((float)Integer.parseInt(val),info.getUnit()) ;
            case Text -> mes = new MesureText(val) ;
            case List -> mes = new MesureList(val);
            default -> {
                mes = new MesureText(val);
                System.out.println("Erreur constructeur MesureHolder");
            }
        }
    }

    public static MesureHolder newMesureTexte(String name, String texte){
        Mesure mesure = new MesureText(texte);
        return new MesureHolder(name, TypeMesure.Text, mesure);
    }

    public static MesureHolder newMesureBool(String name, boolean value){
        Mesure mesure = new MesureBool(value);
        return new MesureHolder(name, TypeMesure.Bool, mesure);
    }

    public static MesureHolder newMesureNumerique(String name, Float value, String unit){
        Mesure mesure = new MesureNumerique(value, unit);
        return new MesureHolder(name, TypeMesure.Numeric, mesure);
    }

    public static MesureHolder newMesureList(String name, String type) {
        Mesure mesure = new MesureList(type);
        return new MesureHolder(name, TypeMesure.List, mesure);
    }

    public static MesureHolder newMesureScale(String name, int niveau, int min, int max){
        Mesure mesure = new MesureScale(niveau, min, max);
        return new MesureHolder(name, TypeMesure.Scale, mesure);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TypeMesure getType() { return type; }
    public void setType(TypeMesure type) { this.type = type; }

    public Mesure getMesure() { return mesure; }
    public void setMesure(Mesure mesure) { this.mesure = mesure; }
}
