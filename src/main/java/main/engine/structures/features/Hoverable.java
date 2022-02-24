package main.engine.structures.features;

import main.engine.display.renderer.Renderer;
import main.engine.structures.observer.Observer;

public interface Hoverable extends Observer, Measurable
{
    int HIGHLIGHT_COLOR = 0x220000FF;
    void onHover();
    void nonHover();
    default void hoverRender(Renderer r, boolean hovered, int id)
    {
        if(hovered)
            r.drawRectangle(getPos(), getDim(), HIGHLIGHT_COLOR, 1, id);
    }
}
