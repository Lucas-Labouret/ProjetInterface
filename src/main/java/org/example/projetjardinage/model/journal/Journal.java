package org.example.projetjardinage.model.journal;

import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.journal.mesures.MesureHolder;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Journal extends HashMap<LocalDate, JournalEntry> {
    private final Species species;

    public Journal(Species species){
        this.species = species;
    }

    public Journal(List<List<String>> val, List<List<String>> photos, PlageMesure plage, Species species){
        this.species = species;
        for(int i = 0; i<val.size();i++){
            List<String> cas = val.get(i);
            String da = cas.get(0);
            int day = Integer.parseInt(da.substring(0,2));
            int month = Integer.parseInt(da.substring(2,4));
            int year = Integer.parseInt(da.substring(4,8));
            LocalDate date = LocalDate.of(year, Month.of(month),day);

            List<String> vals = cas.subList(1,cas.size()-3);
            List<Boolean> bools = new ArrayList<>();
            for(String s : cas.subList(cas.size()-3, cas.size()) ){
                if(s.equals("0")){
                    bools.add(false);
                } else {
                    bools.add(true);
                }
            }
            JournalEntry entry = new JournalEntry(plage, vals, species, bools,photos.get(i));
            this.put(date, entry);
        }
    }

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

    public void newEntry(LocalDate value) {
        if (this.isEmpty()) this.put(value, new JournalEntry(species));
        else {
            LocalDate lastDate = this.getSortedDates().get(this.getSortedDates().size()-1);
            JournalEntry last = this.get(lastDate);
            this.remove(value);
            this.put(value, new JournalEntry(last));
        }
    }
}
