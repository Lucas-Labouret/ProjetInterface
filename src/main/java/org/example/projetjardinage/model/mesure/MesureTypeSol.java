package org.example.projetjardinage.model.mesure;

public class MesureTypeSol extends Mesure {
    public enum Type {
        Argile, Calcaire, Caillouteux
    }

    private Type type;

    public MesureTypeSol(Type type){
        this.type = type;
    }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}
