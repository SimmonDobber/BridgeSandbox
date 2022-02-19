package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.Renderer;

@Getter
public class Text implements Drawable
{
    private String text;
    private int x;
    private int y;
    private int size;
    private int color;
    private int fixed;

    public Text(String text, int x, int y, int size, int color, int fixed)
    {
        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.fixed = fixed;
    }

    @Override
    public void render(Renderer r, int x, int y, int id)
    {
        r.drawText(this, getAbsoluteX(x), getAbsoluteY(y), id);
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
