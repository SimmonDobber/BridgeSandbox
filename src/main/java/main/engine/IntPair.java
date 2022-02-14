package main.engine;

public class IntPair
{
    public int x;
    public int y;
    public IntPair()
    {
        this.x = 0;
        this.y = 0;
    }
    public IntPair(IntPair p)
    {
        this.x = p.x;
        this.y = p.y;
    }
    public IntPair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

}
