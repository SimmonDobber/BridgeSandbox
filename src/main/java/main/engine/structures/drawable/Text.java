package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.Renderer;

@Getter
public class Text implements Drawable
{
    private String text;
    private int size;
    private int color;
    private int fixed;

    public Text(String text, int size, int color, int fixed)
    {
        this.text = text;
        this.size = size;
        this.color = color;
        this.fixed = fixed;
    }

    @Override
    public void render(Renderer r, int x, int y, int id)
    {

    }
}
