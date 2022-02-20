package main.engine.structures.gameObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity implements Measurable
{
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    public Entity(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
