package main.engine.structures.features;

import main.engine.Input;
import main.engine.display.Window;

public interface Measurable
{
    int getX();
    int getY();
    int getW();
    int getH();

    default boolean hasFocus(int id)
    {
        int mouseX = Input.getInput().getMouseX();
        int mouseY = Input.getInput().getMouseY();
        return isOnSurface(mouseX, mouseY, id) && isInBorders(mouseX, mouseY);
    }

    default boolean isInBorders(int x, int y)
    {
        return x >= getX() && x< getX() + getW() && y >= getY() && y < getY() + getH();
    }

    default boolean isOnSurface(int mouseX, int mouseY, int id)
    {
        int pixelId = mouseX + mouseY * (int)(Window.WIDTH * Window.SCALE);
        return Window.getWindow().getRenderer().getPOwner()[pixelId] == id;
    }

}
