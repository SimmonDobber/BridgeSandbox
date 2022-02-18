package main.engine.structures;

import main.engine.Input;
import main.engine.LoopTimer;
import main.engine.display.Renderer;
import main.engine.display.Window;

public interface Scene {

//    Zmiana na klasę która zawiera listę game object i wywoŁuje na nich update render

    void update(Window window, Input input, LoopTimer loopTimer);
    void render(Renderer r);

}
