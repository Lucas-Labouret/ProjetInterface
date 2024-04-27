package org.example.projetjardinage.model.Lists;

import org.example.projetjardinage.model.Observable;

import java.util.ArrayList;
import java.util.List;

public class ObservableList<T> extends Observable {
    @FunctionalInterface
    public interface Filter<T> {
        boolean filter(T t);
    }

    protected List<T> elements;
    protected List<Filter<T>> filters;

    public ObservableList() {
        elements = new ArrayList<>();
        filters = new ArrayList<>();
    }

    public ObservableList(List<T> elements) {
        this();
        this.elements.addAll(elements);
    }

    public List<T> getElements() { return elements; }
    public T get(int i) { return elements.get(i); }

    public void add(T... elements) {
        for (T element : elements) {
            boolean skip = false;
            for (Filter<T> f : filters) { skip = (skip || !f.filter(element)); }
            if (!skip) this.elements.add(element);
        }
        sendNotif();
    }

    public void removeTasks(T... t) {
        elements.removeAll(List.of(t));
        sendNotif();
    }

    public void addFilter(Filter<T> f) {
        filters.add(f);
    }

    public int size() { return elements.size(); }
}
