package org.example.projetjardinage.model;

import org.example.projetjardinage.controller.Observer;

import java.util.ArrayList;

public interface Observable {
    ArrayList<Observer> observers = new ArrayList<>();

    default void addObserver(Observer obs) { observers.add(obs); }
    default void removeObserver(Observer obs) { observers.remove(obs); }

    default void sendNotif() {
        for (Observer obs : observers) obs.update();
    }
}
