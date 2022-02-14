package main.engine.structures;

import main.engine.Input;
import main.engine.LoopTimer;
import main.engine.display.Drawable;
import main.engine.display.Renderer;
import main.engine.display.Window;

public interface State extends Drawable {
    void update(Window window, Input input, LoopTimer loopTimer);
    void render(Renderer r);

}
