package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.structures.gameObject.Position;

@Getter
public abstract class DrawableEntity implements Drawable
{
    protected Position pos;
    protected int fixed;
    public DrawableEntity(Position pos, int fixed)
    {
        this.pos = pos;
        this.fixed = fixed;
    }
    protected Position getAbsolutePos(Position relativePos) {
        int relativeX = pos.x + relativePos.x;
        int relativeY = pos.y + relativePos.y;
        return new Position(relativeX, relativeY);
    }
}
