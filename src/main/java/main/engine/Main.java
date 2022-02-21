package main.engine;

import main.game.tablecontent.Table;

public class Main
{
    public static int objectNumber = 0;
    public static void main(String[] args)
    {
        MainLoop mainLoop = MainLoop.getMainLoop();
        mainLoop.setCurrentScene(new Table());
    }
}
