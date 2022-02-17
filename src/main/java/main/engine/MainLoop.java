package main.engine;

import main.engine.display.Renderer;
import main.engine.display.Window;
import main.game.Table;

public class MainLoop implements Runnable
{

    private final double FPS = 60.0;
    private Thread thread;
    private LoopTimer loopTimer;
    private Window window;
    private Input input;
    private Table table;
    private boolean running;
    public MainLoop()
    {
        table = new Table(Window.WIDTH, Window.HEIGHT);
        loopTimer = new LoopTimer(1.0 / FPS);
        thread = new Thread(this);
        window = new Window();
        input = new Input(window);
        thread.run();
    }
    @Override
    public void run()
    {
        running = true;
        while(running)
        {
            loopTimer.update();
            if(loopTimer.isToUpdate())
                update();
            if(loopTimer.isToRender())
                render(window.getRenderer());
        }
    }
    public void update()
    {
        //System.out.println(loopTimer.getFps());
        table.update(window, input, loopTimer);
        window.getCamera().cameraControl(input);
        input.update();
    }
    public void render(Renderer r)
    {
        table.render(r);
        window.render();
    }
}
