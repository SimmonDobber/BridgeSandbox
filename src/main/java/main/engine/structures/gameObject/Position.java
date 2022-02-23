package main.engine.structures.gameObject;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;

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

    public Position(Position position)
    {
        this.x = position.getX();
        this.y = position.getY();
    }

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public static Position cursorPosition()
    {
        return new Position(Input.getInput().getMouseX(), Input.getInput().getMouseY());
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
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
