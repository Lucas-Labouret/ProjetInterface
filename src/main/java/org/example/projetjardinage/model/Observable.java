package org.example.projetjardinage.model;

import org.example.projetjardinage.controller.Observer;

import java.util.ArrayList;

public interface Observable {
    ArrayList<Observer> observers = new ArrayList<>();

    default void subscribe(Observer o) {
        observers.add(o);
    }

    default void sendNotif() {
        for (Observer o : observers) { o.update(); }
    }
}
