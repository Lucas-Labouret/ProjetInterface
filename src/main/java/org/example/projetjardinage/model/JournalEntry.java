package org.example.projetjardinage.model;

import org.example.projetjardinage.model.mesure.MesureHolder;

import java.util.ArrayList;

public class JournalEntry {
    private final ArrayList<MesureHolder> mesures;
    private String notes;

    JournalEntry() {
        mesures = new ArrayList<>();
        notes = "";
    }

    JournalEntry(ArrayList<MesureHolder> m, String n){
        mesures = m;
        notes = n;
    }

    public ArrayList<MesureHolder> getMesures() {return mesures;}

    public String getNotes() {return notes;}
    public void setNotes(String n) {notes = n;}

    public void addMesure(MesureHolder holder){mesures.add(holder);}
}
