package main.engine.structures.drawable;

import main.engine.display.Renderer;
import main.engine.structures.gameObject.Position;

public interface Drawable
{
    void render(Renderer r, Position pos, int id);
}
