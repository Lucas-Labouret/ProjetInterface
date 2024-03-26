package org.example.projetjardinage.controller;

import javafx.scene.control.Accordion;
import javafx.scene.layout.Pane;
import org.example.projetjardinage.model.Task;

import java.util.ArrayList;

public class TasksControllerFactory {
    public static Pane getView(ArrayList<Task> tasks){
        Accordion accordion = new Accordion();

        return new Pane();
    }
}
