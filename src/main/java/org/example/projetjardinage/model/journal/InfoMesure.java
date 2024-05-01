package org.example.projetjardinage.model.journal;

import org.example.projetjardinage.model.journal.mesures.TypeMesure;

import java.util.ArrayList;
import java.util.List;

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
            case "L" -> this.type = TypeMesure.List;
            case "S" -> this.type = TypeMesure.Scale;
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

    public ArrayList<String> getSpecificInfos(){
        if (this.type == TypeMesure.List || this.type == TypeMesure.Scale){
            return new ArrayList<>(List.of(this.unit.split("<SEP>")));
        } else {
            return null;
        }
    }

    public String getStringType(){
        String ty ="";
        switch (type) {
            case Numeric -> ty = "N" ;
            case Text -> ty = "T" ;
            case Bool -> ty = "B" ;
            case Scale -> ty = "S" ;
            case List -> ty = "L";
            default -> System.out.println("Erreur constructeur InfoMesure");
        }
        return ty;
    }
}
