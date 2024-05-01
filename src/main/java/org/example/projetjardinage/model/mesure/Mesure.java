package org.example.projetjardinage.model.mesure;

public abstract class Mesure {
    private TypeMesure type;
    public abstract TypeMesure getType();
    public abstract Object getValue();

}
