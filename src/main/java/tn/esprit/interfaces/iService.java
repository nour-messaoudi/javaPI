package tn.esprit.interfaces;

import javafx.collections.ObservableList;

public interface iService<T> {
    void add(T t);
    void update(T t);
    void delete(T t);
    ObservableList<T> getAll(); // Changé de List à ObservableList
    T getOne(int id);
}