package main.engine;

import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.State;
import main.game.solver.Solver;
import main.game.tablecontent.Table;

public class MainLoop implements Runnable
{
    public static final double FPS = 60.0;
    private final LoopTimer loopTimer;
    private final Window window;
    private final Input input;
    private final State table;

    public MainLoop()
    {
        table = new Table(Window.WIDTH, Window.HEIGHT);
        loopTimer = new LoopTimer(1.0 / FPS);
        window = Window.getWindow();
        input = Input.getInput();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {
        boolean running = true;
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
