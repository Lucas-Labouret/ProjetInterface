package org.example.projetjardinage.model;

import org.example.projetjardinage.model.mesure.Mesure;
import org.example.projetjardinage.model.mesure.MesureHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Journal extends HashMap<LocalDate, JournalEntry> {
    public ArrayList<LocalDate> getSortedDates(){
        ArrayList<LocalDate> dateList = new ArrayList<>(this.keySet());
        dateList.sort(LocalDate::compareTo);
        return dateList;
    }

    public void updateMesure(LocalDate date, MesureHolder mesure){
        JournalEntry entry = this.get(date);
        ArrayList<MesureHolder> holders = null;
        if (entry != null) holders = entry.getMesures();
        else for (LocalDate d: this.keySet())
            if (d.isEqual(date)) holders = this.get(d).getMesures();

        if (holders == null) throw new RuntimeException("Date does not exists in journal");

        for (MesureHolder holder: holders)
            if (Objects.equals(holder.getName(), mesure.getName())){
                holder.setMesure(mesure.getMesure());
                return;
            }

        throw new RuntimeException("Cannot update measure because it does not exist");
    }
}
