package org.example.projetjardinage.model.mesure;

public class MesureNumerique extends Mesure {
    private float value;
    private String unit;


    public MesureNumerique(Float value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    public void setValueLect(String val) {this.value = Integer.parseInt(val);}

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
