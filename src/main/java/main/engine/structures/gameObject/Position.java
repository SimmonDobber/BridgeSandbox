package main.engine.structures.gameObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position
{
    protected int x;
    protected int y;

    public Position()
    {
        this.x = 0;
        this.y = 0;
    }

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void incX(int x)
    {
        this.x += x;
    }

    public void incY(int y)
    {
        this.y += y;
    }
}
