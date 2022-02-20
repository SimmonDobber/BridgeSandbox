package main.engine.structures.features;

import main.engine.display.Renderer;
import main.engine.structures.gameObject.GameObject;

public interface Activable
{
    int INACTIVE_COLOR = 0x220000FF;
    default void inactiveRender(Renderer r)
    {
        if(!this.getClass().isInstance(GameObject.class))
            return;
        GameObject g = ((GameObject)(this));
        r.drawRectangle(g.getX(), g.getY(), g.getW(), g.getH(), INACTIVE_COLOR, 1, g.getId());
    }
}
