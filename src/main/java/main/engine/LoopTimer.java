package main.engine;

import lombok.Getter;

public class LoopTimer
{
    private double updateCap;
    private double prevTime;
    private double absoluteTime;
    private double unprocessedTime;
    @Getter
    private double passedTime;
    @Getter
    private double elapsedTime;
    private double frameTime;
    private double frames;
    @Getter
    private double fps;
    @Getter
    private boolean toUpdate;
    @Getter
    private boolean toRender;

    public LoopTimer(double updateCap)
    {
        initializeTime();
        initializeVariables(updateCap);
    }
    private void initializeTime()
    {
        this.elapsedTime = System.nanoTime() / 1000000000.0;
        this.prevTime = System.nanoTime() / 1000000000.0;
        this.absoluteTime = System.nanoTime() / 1000000000.0;
    }
    private void initializeVariables(double updateCap)
    {
        this.updateCap = updateCap;
        unprocessedTime = frameTime = frames = fps = 0;
        toUpdate = toRender = false;
    }
    void update()
    {
        toRender = false;
        toUpdate = false;
        updateTime();
        while(unprocessedTime >= updateCap)
        {
            processTime();
        }
        updateThread();
    }
    private void updateTime()
    {
        double currTime = System.nanoTime() / 1000000000.0;
        passedTime = currTime - prevTime;
        prevTime = currTime;
        unprocessedTime += passedTime;
        frameTime += passedTime;
        updateFps();
    }

    private void processTime()
    {
        unprocessedTime -= updateCap;
        if(unprocessedTime < updateCap)
        {
            frames++;
            toRender = true;
        }
        elapsedTime = System.nanoTime() / 1000000000.0 - absoluteTime;
        toUpdate = true;
    }

    private void updateFps()
    {
        if(frameTime >= 1.0)
        {
            elapsedTime++;
            fps = frames;
            frames = 0;
            frameTime = 0;
        }
    }

    private void updateThread()
    {
        if(!toRender)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
