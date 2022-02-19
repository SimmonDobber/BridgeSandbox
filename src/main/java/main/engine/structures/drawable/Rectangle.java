package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.Renderer;

@Getter
public class Rectangle implements Drawable
{
    private static final int DEFAULT_FRAME_THICKNESS = 2;
    private int x;
    private int y;
    private int w;
    private int h;
    private int fixed;
    private int color;
    private int frameColor;

    public Rectangle(int x, int y, int w, int h, int color, int frameColor, int fixed)
    {
        initializeRectangle(x, y, w, h, color, frameColor, fixed);
    }

    public Rectangle(int x, int y, int w, int h, int color, int fixed)
    {
        initializeRectangle(x, y, w, h, color, color, fixed);
    }
    private void initializeRectangle(int x, int y,int w, int h, int color, int frameColor, int fixed)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.fixed = fixed;
        this.color = color;
        this.frameColor = frameColor;
    }

    @Override
    public void render(Renderer r, int x, int y, int id) {
        r.drawRectangle(getAbsoluteX(x), getAbsoluteY(y), w, h, color, fixed, id);
        if(color != frameColor)
            renderFrame(r, getAbsoluteX(x), getAbsoluteY(y), id);
    }

    private void renderFrame(Renderer r, int absX, int absY, int id)
    {
        r.drawRectangle(absX, absY, w, DEFAULT_FRAME_THICKNESS, frameColor, fixed, id);
        r.drawRectangle(absX, absY, DEFAULT_FRAME_THICKNESS, h, frameColor, fixed, id);
        r.drawRectangle(absX + w - DEFAULT_FRAME_THICKNESS, absY, DEFAULT_FRAME_THICKNESS, h, frameColor, fixed, id);
        r.drawRectangle(absX, absY + h - DEFAULT_FRAME_THICKNESS, w, DEFAULT_FRAME_THICKNESS, frameColor, fixed, id);
    }

    private int getAbsoluteX(int x)
    {
        return this.x + x;
    }
    private int getAbsoluteY(int y)
    {
        return this.y + y;
    }
}
