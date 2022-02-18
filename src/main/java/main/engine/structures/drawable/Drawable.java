package main.engine.structures.drawable;

import main.engine.display.Renderer;

public interface Drawable
{
    void render(Renderer r, int x, int y, int id);
}
