package org.example.projetjardinage.model.mesure;

import org.example.projetjardinage.model.journal.mesures.TypeMesure;

public abstract class Mesure {
    private TypeMesure type;
    public abstract TypeMesure getType();
    public abstract Object getValue();

}
