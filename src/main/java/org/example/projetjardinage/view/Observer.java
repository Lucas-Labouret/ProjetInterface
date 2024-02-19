package org.example.projetjardinage.view;

import org.example.projetjardinage.model.Observable;

public interface Observer {
    default void subscribeTo(Observable o) {
        o.subscribe(this);
    }

    void update();
}
