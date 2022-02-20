package main.engine.structures.gameObject;

import main.engine.Main;

public interface Serializable
{
    int getId();
    default int giveId()
    {
        return Main.objectNumber++;
    }
}
