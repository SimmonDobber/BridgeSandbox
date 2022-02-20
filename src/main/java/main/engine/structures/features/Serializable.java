package main.engine.structures.features;

import main.engine.Main;

public interface Serializable
{
    int getId();
    default int giveId()
    {
        return Main.objectNumber++;
    }
}
