package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.renderer.Renderer;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

@Getter
public class Rectangle extends DrawableEntity
{
    private static final int DEFAULT_FRAME_THICKNESS = 2;
    private Dimensions dim;
    private int color;
    private int frameColor;

    public Rectangle(Position pos, Dimensions dim, int color, int frameColor, int fixed) {
        super(pos, fixed);
        initializeRectangle(dim, color, frameColor);
    }

    public Rectangle(Position pos, Dimensions dim, int color, int fixed) {
        super(pos, fixed);
        initializeRectangle(dim, color, color);
    }
    private void initializeRectangle(Dimensions dim, int color, int frameColor) {
        this.dim = dim;
        this.color = color;
        this.frameColor = frameColor;
    }

    @Override
    public void render(Renderer r, Position relativePos, int id) {
        r.drawRectangle(getAbsolutePos(relativePos), dim, color, fixed, id);
        if(color != frameColor)
            renderFrame(r, getAbsolutePos(relativePos), id);
    }

    private void renderFrame(Renderer r, Position relativePos, int id) {
        renderTopFrame(r, relativePos, id);
        renderBottomFrame(r, relativePos, id);
        renderLeftFrame(r, relativePos, id);
        renderRightFrame(r, relativePos, id);
    }

    private void renderTopFrame(Renderer r, Position relativePos, int id) {
        int frameX = relativePos.x;
        int frameY = relativePos.y;
        Position framePos = new Position(frameX, frameY);
        r.drawRectangle(framePos, new Dimensions(dim.w, DEFAULT_FRAME_THICKNESS), frameColor, fixed, id);
    }

    private void renderBottomFrame(Renderer r, Position relativePos, int id){
        int frameX = relativePos.x;
        int frameY = relativePos.y + dim.h - DEFAULT_FRAME_THICKNESS;
        Position framePos = new Position(frameX, frameY);
        r.drawRectangle(framePos, new Dimensions(dim.w, DEFAULT_FRAME_THICKNESS), frameColor, fixed, id);
    }

    private void renderLeftFrame(Renderer r, Position relativePos, int id){
        int frameX = relativePos.x;
        int frameY = relativePos.y;
        Position framePos = new Position(frameX, frameY);
        r.drawRectangle(framePos, new Dimensions(DEFAULT_FRAME_THICKNESS, dim.h), frameColor, fixed, id);
    }

    private void renderRightFrame(Renderer r, Position relativePos, int id){
        int frameX = relativePos.x + dim.w - DEFAULT_FRAME_THICKNESS;
        int frameY = relativePos.y;
        Position framePos = new Position(frameX, frameY);
        r.drawRectangle(framePos, new Dimensions(DEFAULT_FRAME_THICKNESS, dim.h), frameColor, fixed, id);
    }
}
