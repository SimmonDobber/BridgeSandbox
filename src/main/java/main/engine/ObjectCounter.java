package main.engine;

public class ObjectCounter
{
    private static int OBJECT_COUNTER = 1;
    public static int getNextId()
    {
        return ++OBJECT_COUNTER;
    }
}
