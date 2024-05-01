package org.example.projetjardinage.model;

import org.example.projetjardinage.model.journal.InfoMesure;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;
import org.example.projetjardinage.model.journal.PlageMesure;

import java.util.ArrayList;
import java.util.List;

public class JournalEntry {
    private final ArrayList<MesureHolder> mesures = new ArrayList<>();
    private String notes;

    JournalEntry() {
        notes = "";
    }

    JournalEntry(ArrayList<MesureHolder> m, String n){
        mesures.addAll(m);
        notes = n;
    }

    public JournalEntry(PlageMesure infos, List<String> vals){
        int nb = infos.getTaille();

        for(int i = 0; i < nb;i++){
            if(! (vals.get(i).equals("<N>")) ) {
                mesures.add(new MesureHolder(infos.getAll().get(i), vals.get(i)));
            }
        }
    }

    public ArrayList<MesureHolder> getMesures() {return mesures;}

    public String getNotes() {return notes;}
    public void setNotes(String n) {notes = n;}

    public void addMesure(MesureHolder holder){mesures.add(holder);}

    public MesureHolder getMesureInfo(InfoMesure info){
        for(MesureHolder mes : this.mesures){
            if (mes.getName().equals(info.getName())){
                return mes;
            }
        }
        return MesureHolder.newMesureTexte("<Echec>", "Pour get l'info");
    }
}
