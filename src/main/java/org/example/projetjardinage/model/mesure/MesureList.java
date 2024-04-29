package org.example.projetjardinage.model.mesure;

import java.util.ArrayList;
import java.util.List;

public class MesureList extends Mesure {
    private ArrayList<String> types = new ArrayList<>();
    private String type;

    public MesureList(String types){
        ArrayList<String> typesList = new ArrayList<>(List.of(types.split("<SEP>")));
        if (typesList.size() == 1){
            this.types.addAll(List.of("Ca Ce Co".split(" ")));
        } else {
            String type = typesList.getLast();
            typesList.removeLast();
            this.types = typesList;
            this.setType(type);
        }
    }

    public MesureList(ArrayList<String> types){
        this.types = types;
        this.type = types.getFirst();
    }

    public MesureList(ArrayList<String> types, String type){
        this.types = types;
        if (!types.contains(type)) throw new IllegalArgumentException("Type invalid");
        this.type = type;
    }

    public String getType() { return type; }
    public void setType(String type) {
        if (!types.contains(type)) throw new IllegalArgumentException("Type invalid : " + type + " not in " + types);
        this.type = type;
    }

    public ArrayList<String> getTypes() { return types; }
    public void setTypes(ArrayList<String> types) { this.types = types; }
}
