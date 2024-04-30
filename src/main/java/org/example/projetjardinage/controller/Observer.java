package org.example.projetjardinage.controller;

import org.example.projetjardinage.model.Observable;

public abstract class Observer {
    public void subscribeTo(Observable obs) {
        obs.addObserver(this);
    }
    public void unsubscribeFrom(Observable obs) {
        obs.removeObserver(this);
    }

    public abstract void update();
}
