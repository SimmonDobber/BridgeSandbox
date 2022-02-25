package main.engine.structures.gameObject;

import lombok.Getter;

@Getter
public class Scale
{
    public double x;
    public double y;

    public Scale() {
        this.x = 0;
        this.y = 0;
    }

    public Scale(Scale scale) {
        this.x = scale.getX();
        this.y = scale.getY();
    }

    public Scale(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
