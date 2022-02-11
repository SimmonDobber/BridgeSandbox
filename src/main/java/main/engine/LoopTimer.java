package main.engine;

public class LoopTimer
{
    private double updateCap;
    private double currTime;
    private double prevTime;
    private double absoluteTime;
    private double passedTime;
    private double unprocessedTime;
    private double elapsedTime;
    private double frameTime;
    private double frames;
    private double fps;
    private boolean toUpdate;
    private boolean toRender;

    public LoopTimer(double updateCap)
    {
        this.updateCap = updateCap;
        this.elapsedTime = System.nanoTime() / 1000000000.0;
        this.prevTime = System.nanoTime() / 1000000000.0;;
        this.absoluteTime = System.nanoTime() / 1000000000.0;
        unprocessedTime = 0;
        frameTime = 0;
        frames = 0;
        fps = 0;
        toUpdate = false;
        toRender = false;
    }
    void update()
    {
        toRender = false;
        toUpdate = false;
        currTime = System.nanoTime() / 1000000000.0;
        passedTime = currTime - prevTime;
        prevTime = currTime;
        unprocessedTime += passedTime;
        frameTime += passedTime;
        while(unprocessedTime >= updateCap)
        {
            unprocessedTime -= updateCap;
            if(unprocessedTime < updateCap)
            {
                frames++;
                toRender = true;
            }
            elapsedTime = System.nanoTime() / 1000000000.0 - absoluteTime;
            toUpdate = true;
            if(frameTime >= 1.0)
            {
                elapsedTime++;
                fps = frames;
                frames = 0;
                frameTime = 0;
            }
        }
        if(!toRender)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isToUpdate() {
        return toUpdate;
    }

    public boolean isToRender() {
        return toRender;
    }

    public double getFps() {
        return fps;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }
}
