package org.example.projetjardinage.model.mesure;

import java.util.ArrayList;
import java.util.List;

public class OptimalHolder {
    private MesureScale exp;
    private MesureScale arr;
    private MesureList ts;
    private MesureNumerique ph = new MesureNumerique((float)0, "");
    private MesureNumerique espaceAuSol = new MesureNumerique((float)0, "cm2") ;

    public OptimalHolder(List<String> elem){
        exp = new MesureScale(elem.get(0));
        arr = new MesureScale(elem.get(1));
        ts = new MesureList(elem.get(2));
        ph.setValueLect(elem.get(3));
        espaceAuSol.setValueLect(elem.get(4));
    }

    public MesureScale getExp() { return exp; }
    public void setExposition(int force) { exp.setNiveau(force); }

    public MesureScale getArrosage() { return arr; }
    public void setArrosage(int niveau) {arr.setNiveau(niveau); }

    public MesureList getTypeSol() { return ts; }
    public void setTypeSol(String type) { ts.setType(type); }

    public MesureNumerique getPh() { return ph;}
    public void setPh(float value) { ph.setValue(value); }

    public MesureNumerique getEspaceAuSol() { return espaceAuSol; }
    public void setEspaceAuSol(float value) { espaceAuSol.setValue(value); }
}
