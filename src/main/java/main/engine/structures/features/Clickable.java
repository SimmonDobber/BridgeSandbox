package main.engine.structures.features;

import main.engine.structures.gameObject.Measurable;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.engine.structures.gameObject.Serializable;
import main.engine.display.Window;

public interface Clickable extends Measurable, Serializable, Observer, Observable
{
    void onClick();
    void onRelease();
    void onHold();

    default boolean hasToBeNotified(int mouseX, int mouseY)
    {
        return onSurface(mouseX, mouseY) && inBorders(mouseX, mouseY);
    }

    default boolean onSurface(int mouseX, int mouseY)
    {
        int pixelId = mouseX + mouseY * Window.WIDTH;
        return Window.getWindow().getRenderer().getPOwner()[pixelId] == getId();
    }

    default boolean inBorders(int mouseX, int mouseY)
    {
        return mouseX >= getX() && mouseX < getX() + getW() && mouseY >= getY() && mouseY < getY() + getH();
    }
}
