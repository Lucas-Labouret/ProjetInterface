package org.example.projetjardinage.model;

import org.example.projetjardinage.controller.Observer;

import java.util.ArrayList;

public abstract class Observable {
    private final ArrayList<Observer> OBSERVERS = new ArrayList<>();

    public void addObserver(Observer obs) { OBSERVERS.add(obs); }
    public void removeObserver(Observer obs) { OBSERVERS.remove(obs); }

    public void sendNotif() {
        for (Observer obs : OBSERVERS) obs.update();
    }
}
