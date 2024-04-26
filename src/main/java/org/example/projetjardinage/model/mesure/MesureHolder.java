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
            case Exposition -> mes = new MesureExposition(val);
            case Arrosage -> mes = new MesureArrosage(val);
            case Numerique -> mes = new MesureNumerique((float)Integer.parseInt(val),info.getUnit()) ;
            case Texte -> mes = new MesureTexte(val) ;
            case TypeSol -> mes = new MesureTypeSol(val);
            default -> {
                mes = new MesureTexte(val);
                System.out.println("Erreur constructeur MesureHolder");
            }
        }


    }

    public static MesureHolder newMesureTexte(String name, String texte){
        Mesure mesure = new MesureTexte(texte);
        return new MesureHolder(name, TypeMesure.Texte, mesure);
    }

    public static MesureHolder newMesureBool(String name, boolean value){
        Mesure mesure = new MesureBool(value);
        return new MesureHolder(name, TypeMesure.Bool, mesure);
    }

    public static MesureHolder newMesureNumerique(String name, Float value, String unit){
        Mesure mesure = new MesureNumerique(value, unit);
        return new MesureHolder(name, TypeMesure.Numerique, mesure);
    }

    public static MesureHolder newMesureTypeSol(String name, MesureTypeSol.Type type) {
        Mesure mesure = new MesureTypeSol(type);
        return new MesureHolder(name, TypeMesure.TypeSol, mesure);
    }

    public static MesureHolder newMesureArrosage(String name, MesureArrosage.Niveau niveau){
        Mesure mesure = new MesureArrosage(niveau);
        return new MesureHolder(name, TypeMesure.Arrosage, mesure);
    }

    public static MesureHolder newMesureExposition(String name, MesureExposition.Force force){
        Mesure mesure = new MesureExposition(force);
        return new MesureHolder(name, TypeMesure.Exposition, mesure);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TypeMesure getType() { return type; }
    public void setType(TypeMesure type) { this.type = type; }

    public Mesure getMesure() { return mesure; }
    public void setMesure(Mesure mesure) { this.mesure = mesure; }
}
