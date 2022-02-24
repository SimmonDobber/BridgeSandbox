package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.renderer.Renderer;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

@Getter
public class Rectangle implements Drawable
{
    private static final int DEFAULT_FRAME_THICKNESS = 2;
    private Position pos;
    private Dimensions dim;
    private int fixed;
    private int color;
    private int frameColor;

    public Rectangle(Position pos, Dimensions dim, int color, int frameColor, int fixed)
    {
        initializeRectangle(pos, dim, color, frameColor, fixed);
    }

    public Rectangle(Position pos, Dimensions dim, int color, int fixed)
    {
        initializeRectangle(pos, dim, color, color, fixed);
    }
    private void initializeRectangle(Position pos, Dimensions dim, int color, int frameColor, int fixed)
    {
        this.pos = pos;
        this.dim = dim;
        this.fixed = fixed;
        this.color = color;
        this.frameColor = frameColor;
    }

    @Override
    public void render(Renderer r, Position absolutePos, int id) {
        r.drawRectangle(getAbsolutePos(absolutePos), dim, color, fixed, id);
        if(color != frameColor)
            renderFrame(r, getAbsolutePos(absolutePos), id);
    }

    private void renderFrame(Renderer r, Position absPos, int id)
    {
        Position framePosition;
        r.drawRectangle(absPos, new Dimensions(dim.getW(), DEFAULT_FRAME_THICKNESS), frameColor, fixed, id);
        r.drawRectangle(absPos, new Dimensions(DEFAULT_FRAME_THICKNESS, dim.getH()), frameColor, fixed, id);
        framePosition = new Position(absPos.getX() + dim.getW() - DEFAULT_FRAME_THICKNESS, absPos.getY());
        r.drawRectangle(framePosition, new Dimensions(DEFAULT_FRAME_THICKNESS, dim.getH()), frameColor, fixed, id);
        framePosition = new Position(absPos.getX(), absPos.getY() + dim.getH() - DEFAULT_FRAME_THICKNESS);
        r.drawRectangle(framePosition, new Dimensions(dim.getW(), DEFAULT_FRAME_THICKNESS), frameColor, fixed, id);
    }

    private Position getAbsolutePos(Position absolutePos)
    {
        return new Position(this.pos.getX() + absolutePos.getX(), this.pos.getY() + absolutePos.getY());
    }
}
