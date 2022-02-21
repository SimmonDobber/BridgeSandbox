package main.engine;

import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.Scene;

public class MainLoop implements Runnable
{
    private static MainLoop MAIN_LOOP = null;

    public static MainLoop getMainLoop() {
        if(MAIN_LOOP == null)
            MAIN_LOOP = new MainLoop();
        return MAIN_LOOP;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public static final double FPS = 60.0;
    private final LoopTimer loopTimer;
    private final Window window;
    private final Input input;
    private Scene currentScene;

    private MainLoop()
    {
        loopTimer = new LoopTimer(1.0 / FPS);
        window = Window.getWindow();
        window.setRenderer(Renderer.getRenderer());
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

    private void update()
    {
        //System.out.println(loopTimer.getFps());
        if (currentScene != null) {
            currentScene.update(loopTimer.getPassedTime());
        }
        window.getCamera().cameraControl(input);
        input.update();
    }

    private void render(Renderer r)
    {
        if(currentScene != null) {
            currentScene.render(r);
        }
        window.render();
    }
}
