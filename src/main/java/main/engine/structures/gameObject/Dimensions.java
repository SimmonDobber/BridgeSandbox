package main.engine.structures.gameObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimensions
{
    protected int w;
    protected int h;

    public Dimensions()
    {
        this.w = 0;
        this.h = 0;
    }

    public Dimensions(Dimensions dimensions)
    {
        this.w = dimensions.getW();
        this.h = dimensions.getH();
    }

    public Dimensions(int w, int h)
    {
        this.w = w;
        this.h = h;
    }

    public int getSize()
    {
        return w * h;
    }
}
