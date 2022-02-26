package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.renderer.Renderer;
import main.engine.structures.gameObject.Position;

@Getter
public class Text extends DrawableEntity
{
    private String text;
    private int size;
    private int color;

    public Text(String text, Position pos, int size, int color, int fixed)
    {
        super(pos, fixed);
        this.text = text;
        this.size = size;
        this.color = color;
    }

    @Override
    public void render(Renderer r, Position pos, int id) {
        r.drawText(this, getAbsolutePos(pos), id);
    }

    public int getTextLength() {
        return text.length();
    }
}
