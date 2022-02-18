package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.Renderer;

@Getter
public class Rectangle implements Drawable
{
    private static final int DEFAULT_FRAME_THICKNESS = 2;
    private int w;
    private int h;
    private int fixed;
    private int color;
    private int frameColor;

    public Rectangle(int w, int h, int color, int frameColor, int fixed)
    {
        initializeRectangle(w, h, color, frameColor, fixed);
    }

    public Rectangle(int w, int h, int color, int fixed)
    {
        initializeRectangle(w, h, color, color, fixed);
    }
    private void initializeRectangle(int w, int h, int color, int frameColor, int fixed)
    {
        this.w = w;
        this.h = h;
        this.fixed = fixed;
        this.color = color;
        this.frameColor = frameColor;
    }

    @Override
    public void render(Renderer r, int x, int y, int id) {
        if(color != frameColor)
            renderFrame(r, x, y, id);
        r.drawRectangle(x, y, w, h, color, fixed, id);
    }

    private void renderFrame(Renderer r, int x, int y, int id)
    {
        r.drawRectangle(x, y, w, DEFAULT_FRAME_THICKNESS, frameColor, fixed, id);
        r.drawRectangle(x, y, DEFAULT_FRAME_THICKNESS, h, color, fixed, id);
        r.drawRectangle(x + w - DEFAULT_FRAME_THICKNESS, y, DEFAULT_FRAME_THICKNESS, h, color, fixed, id);
        r.drawRectangle(x, y + h - DEFAULT_FRAME_THICKNESS, w, DEFAULT_FRAME_THICKNESS, color, fixed, id);
    }
}
