package main;

public interface Clickable
{
    void onClick(State table);
    void onDoubleClick(State table);
    void onRelease(State table);
    void onHold(State table);
    void onHover(State table);
    void nonHover(State table);
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
