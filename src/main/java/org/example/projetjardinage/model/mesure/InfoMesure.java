package org.example.projetjardinage.model.mesure;

public class InfoMesure {
    private String name;
    private TypeMesure type;
    private String unit;

    public InfoMesure(String name, TypeMesure type, String unit){
        this.name = name;
        this.type = type;
        this.unit = unit;
    }

    public InfoMesure(String name, String type, String unit){
        this.name = name;
        this.unit = unit;
        switch (type) {
            case "N" -> this.type = TypeMesure.Numeric;
            case "T" -> this.type = TypeMesure.Text;
            case "B" -> this.type = TypeMesure.Bool;
            default -> System.out.println("Erreur constructeur InfoMesure");
        }

    }

    public String getName(){
        return this.name;
    }

    public TypeMesure getType(){
        return this.type;
    }

    public String getUnit(){
        return this.unit;
    }


}
