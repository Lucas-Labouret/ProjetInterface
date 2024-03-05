package org.example.projetjardinage.model.mesure;

public class OptimalHolder {
    private MesureExposition exp;
    private MesureArrosage arr;
    private MesureTypeSol ts;
    private MesureNumerique ph;
    private MesureNumerique espaceAuSol;

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
