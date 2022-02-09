package main;

public interface State {
    void update(Window window, Input input, LoopTimer loopTimer);
    void render(Renderer r);

}
