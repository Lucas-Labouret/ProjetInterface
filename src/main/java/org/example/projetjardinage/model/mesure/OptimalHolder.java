package org.example.projetjardinage.model.mesure;

import java.util.List;

public class OptimalHolder {
    private MesureExposition exp = new MesureExposition();
    private MesureArrosage arr = new MesureArrosage();
    private MesureTypeSol ts = new MesureTypeSol();
    private MesureNumerique ph = new MesureNumerique((float)0, "");
    private MesureNumerique espaceAuSol = new MesureNumerique((float)0, "cm2") ;

    public OptimalHolder(List<String> elem){


        exp.setForceLect(elem.get(0));
        arr.setNiveauLect(elem.get(1));
        ts.setTypeLect(elem.get(2));
        ph.setValueLect(elem.get(3));
        espaceAuSol.setValueLect(elem.get(4));
    }

    public MesureExposition getExp() { return exp; }
    public void setExposition(MesureExposition.Force force) { exp.setForce(force); }

    public MesureArrosage getArrosage() { return arr; }
    public void setArrosage(MesureArrosage.Niveau niveau) {arr.setNiveau(niveau); }

    public MesureTypeSol getTypeSol() { return ts; }
    public void setTypeSol(MesureTypeSol.Type type) { ts.setType(type); }

    public MesureNumerique getPh() { return ph;}
    public void setPh(float value) { ph.setValue(value); }

    public MesureNumerique getEspaceAuSol() { return espaceAuSol; }
    public void setEspaceAuSol(float value) { espaceAuSol.setValue(value); }
}
