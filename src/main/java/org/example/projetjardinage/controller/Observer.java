package org.example.projetjardinage.controller;

import org.example.projetjardinage.model.Observable;

public interface Observer {
    default void subscribeTo(Observable obs) {
        obs.addObserver(this);
    }
    default void unsubscribeFrom(Observable obs) {
        obs.removeObserver(this);
    }

    void update();
}
