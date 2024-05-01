package org.example.projetjardinage.model.journal.mesures;

public class MesureNumerique extends Mesure {
    private float value;
    private String unit;


    public MesureNumerique(Float value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public MesureNumerique(String value, String unit) {
        this.value = Float.parseFloat(value);
        this.unit = unit;
    }

    public Float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    public void setValueLect(String val) {this.value = Float.parseFloat(val);}

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public TypeMesure getType(){
        return TypeMesure.Numeric;
    }
}
