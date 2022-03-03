package main.engine.structures.features;

import main.engine.display.Window;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

public interface Measurable
{
    Position getPos();
    Dimensions getDim();

    default boolean canBeChosen(int id) {
        return isOnSurface(Position.cursorPosition(), id) && isInBorders(Position.cursorPosition());
    }

    default boolean isInBorders(Position mousePos) {
        return mousePos.getX() >= getPos().getX() && mousePos.getX() < getPos().getX() + getDim().getW() &&
                mousePos.getY() >= getPos().getY() && mousePos.getY() < getPos().getY() + getDim().getH();
    }

    default boolean isOnSurface(Position mousePos, int id) {
        int pixelId = mousePos.getPositionId(Window.WIDTH);
        return Window.getWindow().getRenderer().getPOwner()[pixelId] == id;
    }

}
