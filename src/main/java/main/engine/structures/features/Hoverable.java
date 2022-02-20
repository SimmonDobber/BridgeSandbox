package main.engine.structures.features;

import main.engine.display.Renderer;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.observer.Observer;

public interface Hoverable extends Observer
{
    int HIGHLIGHT_COLOR = 0x220000FF;
    void onHover();
    void nonHover();
    default void defaultHoveredRender(Renderer r)
    {
        if(!this.getClass().isInstance(GameObject.class))
            return;
        GameObject g = ((GameObject)(this));
        r.drawRectangle(g.getX(), g.getY(), g.getW(), g.getH(), HIGHLIGHT_COLOR, 1, g.getId());
    }
}
