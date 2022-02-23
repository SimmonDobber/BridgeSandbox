package main.engine.structures;

import main.engine.Input;
import main.engine.LoopTimer;
import main.engine.display.Renderer;
import main.engine.display.Window;

public interface Scene {

    void update(double dt);
    void render(Renderer r);
    String getName();
}
