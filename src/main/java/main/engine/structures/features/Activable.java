package main.engine.structures.features;

import main.engine.display.Renderer;
import main.engine.structures.gameObject.GameObject;

public interface Activable
{
    int INACTIVE_COLOR = 0x77777777;
    boolean isActive();
    void setActive(boolean active);
}
