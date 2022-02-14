package main.engine.structures;

import main.engine.Input;

public interface Clickable
{
    void onClick(State state);
    void onDoubleClick(State state);
    void onRelease(State state);
    void onHold(State state);
    void onHover(State state);
    void nonHover(State state);
    default boolean onSurface(Input input, int screenW, int owner, int[] pOwner)
    {
        if(pOwner[input.getMouseX() + input.getMouseY() * screenW] != owner)
            return false;
        return true;
    }
    default boolean inborders(Input input, int x, int y, int w, int h)
    {
        if(input.getMouseX() < x || input.getMouseX() >= x + w || input.getMouseY() < y || input.getMouseY() >= y + h)
            return false;
        return true;
    }
}
