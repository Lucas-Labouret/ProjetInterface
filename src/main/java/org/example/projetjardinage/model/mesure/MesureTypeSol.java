package org.example.projetjardinage.model.mesure;

import java.util.ArrayList;
import java.util.Objects;

public class MesureTypeSol extends Mesure {
    private ArrayList<String> types = new ArrayList<>();

    private String type;

    public MesureTypeSol(){
    }

    public MesureTypeSol(ArrayList<String> types, String type){
        this.types = types;
        if (!types.contains(type)) throw new IllegalArgumentException("Type invalid");
        this.type = type;
    }

    public String getType() { return type; }
    public void setType(String type) {
        if (!types.contains(type)) throw new IllegalArgumentException("Type invalid");
        this.type = type;
    }

    public ArrayList<String> getTypes() { return types; }
    public void setTypes(ArrayList<String> types) { this.types = types; }
}
