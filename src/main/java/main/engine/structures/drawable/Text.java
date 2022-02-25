package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.renderer.Renderer;
import main.engine.structures.gameObject.Position;

@Getter
public class Text implements Drawable
{
    private String text;
    private Position pos;
    private int size;
    private int color;
    private int fixed;

    public Text(String text, Position pos, int size, int color, int fixed)
    {
        this.text = text;
        this.pos = pos;
        this.size = size;
        this.color = color;
        this.fixed = fixed;
    }

    @Override
    public void render(Renderer r, Position pos, int id)
    {
        r.drawText(this, getAbsolutePos(pos), id);
    }

    private Position getAbsolutePos(Position pos)
    {
        return new Position(this.pos.getX() + pos.getX(), this.pos.getY() + pos.getY());
    }

    public int getTextLength()
    {
        return text.length();
    }
}
