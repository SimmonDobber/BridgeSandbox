package main.engine.structures.features;

import main.engine.Input;
import main.engine.display.Window;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

public interface Measurable
{
    Position getPos();
    Dimensions getDim();

    default boolean hasFocus(int id)
    {
        int mouseX = Input.getInput().getMouseX();
        int mouseY = Input.getInput().getMouseY();
        return isOnSurface(new Position(mouseX, mouseY), id) && isInBorders(mouseX, mouseY);
    }

    default boolean isInBorders(int x, int y)
    {
        return x >= getPos().getX() && x< getPos().getX() + getDim().getW() &&
                y >= getPos().getY() && y < getPos().getY() + getDim().getH();
    }

    default boolean isOnSurface(Position mousePos, int id)
    {
        int pixelId = mousePos.getX() + mousePos.getY() * (int)(Window.WIDTH * Window.SCALE);
        return Window.getWindow().getRenderer().getPOwner()[pixelId] == id;
    }

}
