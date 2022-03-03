package main.engine.structures.observer;

import main.engine.structures.features.Serializable;

public interface Observable extends Serializable
{
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
